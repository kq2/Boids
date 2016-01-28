package model.update;

import java.awt.Point;
import java.awt.geom.Point2D;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * A physically correct elastic two-body collision model. 
 * @author Stephen Wong
 *
 */
public class CollideExactStrategy implements IUpdateStrategy {
	
	@Override
	public void init(Ball ball) {
		// no-op
	}

	@Override
	public void update(final Ball ball, IDispatcher<IBallCmd> dispatcher) {
		dispatcher.dispatch((otherBall, disp) -> {
			if (otherBall != ball) {
				double minSeparation = ball.getRadius() + otherBall.getRadius();
				double collisionTime = collisionTime(ball.getLoc(), otherBall.getLoc(), ball.getVel(), otherBall.getVel(), minSeparation);
				if (-1.0 <= collisionTime && collisionTime < 0.0) {
					double distance = ball.distance(otherBall);
					double reducedMass = reducedMass(Math.pow(ball.getRadius(), 2), Math.pow(otherBall.getRadius(), 2));
					Point2D.Double imp = impulse(
							ball.getLoc(), ball.getVel(), 
							otherBall.getLoc(), otherBall.getVel(), 
							reducedMass, distance, minSeparation - distance);
					updateCollision(ball, otherBall, imp.x, imp.y, disp, collisionTime);
					updateCollision(otherBall, ball, -imp.x, -imp.y, disp, collisionTime);
				}
			}
		});
	}

	/**
	 * Calculates the time of the first contact between two balls. A negative returned time means that the balls 
	 * collided in the past.  A positive time value means that either the balls will collide in the future,
	 * or if equal to Double.MAX_VALUE, they will never collide, i.e. are traveling parallel to each other. 
	 * The contact time is returned in units of timer ticks.
	 * 
	 * IMPORTANT NOTE:  Only negative time values should be used for collision detection because negative times
	 * indicate that the balls collided in the past.   However, any time less than -1.0 means that the collision 
	 * occurred during the previous tick cycle and thus should be ignored.  
	 * 
	 * @param p1 The location of the first ball
	 * @param p2 The location of the second ball
	 * @param v1 The velocity of the first ball
	 * @param v2 The velocity of the second ball
	 * @param minSeparation The minimum separation, i.e. contact distance between the balls.
	 * @return The first possible contact time between the two balls.
	 */
	private double collisionTime(Point p1, Point p2, Point v1, Point v2, double minSeparation) {
		Point deltaP = deltaVec(p1, p2);
		Point deltaV = deltaVec(v1, v2);
		double deltaP2 = deltaP.distanceSq(0.0, 0.0);
		double deltaV2 = deltaV.distanceSq(0.0, 0.0);
		double R2 = minSeparation*minSeparation;
		double dvdx = deltaV.x*deltaP.x + deltaV.y+deltaP.y;

		double root2 = dvdx*dvdx - deltaV2*(deltaP2-R2);

		if (root2 < 0.0) {
			return Double.MAX_VALUE;   // no solution for t
		}

		double t = (-dvdx - Math.sqrt(root2))/deltaV2;   // want most negative time solution, i.e. first contact

		return t;
	}

	/**
	 * Returns the vector that points from point p1 to point p2 
	 * @param p1 The first point
	 * @param p2 The second point
	 * @return The difference vector between p1 and p2
	 */
	private Point deltaVec(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y-p1.y);
	}
	/**
	 * Returns the reduced mass of the two balls (m1*m2)/(m1+m2) Gives correct
	 * result if one of the balls has infinite mass.
	 * 
	 * @param mSource
	 *            Mass of the source ball
	 * @param mTarget
	 *            Mass of the target ball
	 */
	protected double reducedMass(double mSource, double mTarget) {
		if (mSource == Double.POSITIVE_INFINITY)
			return mTarget;
		if (mTarget == Double.POSITIVE_INFINITY)
			return mSource;
		else
			return (mSource * mTarget) / (mSource + mTarget);
	}


	/**
	 * Calculates the impulse (change in momentum) of the collision in the
	 * direction from the source to the target This method calculates the
	 * impulse on the source ball. The impulse on the target ball is the
	 * negative of the result. 
	 * 
	 * @param lSource
	 *            Location of the source ball
	 * @param vSource
	 *            Velocity of the source ball
	 * @param lTarget
	 *            Location of the target ball
	 * @param vTarget
	 *            Velocity of the target ball
	 * @param reducedMass
	 *            Reduced mass of the two balls
	 * @param distance
	 *            Distance between the two balls.
	 * @param deltaR
	 *            The minimum allowed separation(sum of the ball radii) minus the actual separation(distance between ball centers). Should be a
	 *            positive value.  This is the amount of overlap of the balls as measured along the line between their centers.
	 * @return
	 */
	protected Point2D.Double impulse(Point lSource, Point vSource,
			Point lTarget, Point vTarget, double reducedMass, double distance,
			double deltaR) {
		// Calculate the normal vector, from source to target
		double nx = ((double) (lTarget.x - lSource.x)) / distance;
		double ny = ((double) (lTarget.y - lSource.y)) / distance;

		// delta velocity (speed, actually) in normal direction, source to
		// target
		double dvn = (vTarget.x - vSource.x) * nx + (vTarget.y - vSource.y)
				* ny;


		return new Point2D.Double(2.0 * reducedMass * dvn * nx, 2.0
				* reducedMass * dvn * ny);

	}

	/**
	 * Updates the velocity and position of the source ball (context), given an impulse, then uses the
	 * context's interactWith method to determine the post collision behavior, from the context
	 * ball's perspective. The change in velocity is the impulse divided by the (source) ball's mass. To change
	 * the velocity of the target ball, switch the source and target input
	 * parameters and negate the impulse values.   This will also run the post collision behavior from 
	 * the other perspective.
	 * 
	 * @param context
	 *            The source ball to update
	 * @param target
	 *            The ball being collided with
	 * @param impX
	 *            x-coordinate of the impulse
	 * @param impY
	 *            y-coordinate of the impulse
	 * @param tContact The first ball contact time in ticks.  Should be a negative number.
	 */
	protected void updateCollision(Ball context, Ball target, double impX,
			double impY, IDispatcher<IBallCmd> disp, double tContact) {
		int mContext = context.getRadius() * context.getRadius();

		double dx = context.getVel().x*tContact;
		double dy = context.getVel().y*tContact;
		context.getVel().translate((int) Math.round(impX / mContext),(int) Math.round(impY / mContext));
		dx += -context.getVel().x*tContact;
		dy += -context.getVel().y*tContact;
		context.getLoc().translate((int)Math.round(dx), (int)Math.round(dy));

		context.interactWith(target, disp);
	}

}

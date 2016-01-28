package model.update;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * This strategy implements the Boids algorithm to simulate flocking
 * @author kq2
 *
 */
public class FlockingStrategy implements IUpdateStrategy {

	/**
	 * The fixed radius of this ball
	 */
	private final int RADIUS = 10;
	/**
	 * The maximum speed that ball can reach
	 */
	private final double MAX_SPEED = 8;
	/**
	 * The maximum radius that ball can sense
	 */
	private final double RANGE_SENSE = RADIUS * 20.0;
	/**
	 * Ball moves away from others inside this range. 
	 */
	private final double RANGE_BOUNCE = RADIUS * 4.0;

	@Override
	public void init(Ball host) {
		host.setRadius(RADIUS);
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		ArrayList<Ball> neighbors = new ArrayList<Ball>();
		disp.dispatch((other, dispatcher) -> {
			if (ball != other) {
				if (ball.distance(other) < RANGE_SENSE) {
					neighbors.add(other);
				} 
			}
		});
		if (!neighbors.isEmpty()) boidsMove(ball, neighbors);
	}

	/**
	 * Update the ball's velocity based on the three rules of Boids algorithm. 
	 * @param ball The ball we want to move 
	 * @param neighbors The local ball-mates around this ball within some distance
	 */
	private void boidsMove(Ball ball, ArrayList<Ball> neighbors) {
		
		// the accelerations we will use to change ball's velocity
		Point2D accChase = new Point2D.Double();
		Point2D accCohesion = new Point2D.Double();
		Point2D accAlignment = new Point2D.Double();
		Point2D accSeparation = new Point2D.Double();
		
		double minDist = Double.MAX_VALUE;
		Point2D meanLoc = new Point2D.Double();
		Point2D meanVel = new Point2D.Double();
		
		for (Ball neighbor : neighbors) {
			double dist = ball.distance(neighbor);
			
			// neighbor is not a boid, chase it!
			if (ball.getUpdateStrategy().getClass() != neighbor.getUpdateStrategy().getClass()) {
				if (dist < minDist) {
					minDist = dist; // find the nearest neighbor
					accChase = sub(ball.getLoc(), neighbor.getLoc()); // set accChase
				}
				
			// neighbor is also boid, apply boids three rules!
			} else {
				meanLoc = add(meanLoc, neighbor.getLoc()); // sum values
				meanVel = add(meanVel, neighbor.getVel()); // sum values
				if (dist < RANGE_BOUNCE) {
					Point2D repel = sub(neighbor.getLoc(), ball.getLoc());
					repel = div(normalize(repel), dist); // the nearer the stronger
					accSeparation = add(accSeparation, repel); // set accSeparation
				}
			}
		}
		meanLoc = div(meanLoc, neighbors.size()); // get the mean value
		meanVel = div(meanVel, neighbors.size()); // get the mean value
		accCohesion = sub(ball.getLoc(), meanLoc); // set accCohesion
		accAlignment = sub(ball.getVel(), meanVel); // set accAlignment
		
		// tune each acceleration's magnitude
		accChase = div(accChase, RANGE_SENSE/2);
		accCohesion = div(accCohesion, RANGE_SENSE/2);
		accAlignment = div(accAlignment, RANGE_SENSE/2);
		accSeparation = mul(accSeparation, RANGE_BOUNCE/2);
		
		Point2D newVel = add(add(add(add(ball.getVel(), 
				accChase), 
				accCohesion), 
				accAlignment), 
				accSeparation);
		newVel = limit(newVel, MAX_SPEED);
		ball.setVel(newVel.getX(), newVel.getY());
		
	}
	
	/**
	 * Addition of vectors
	 * @param p First vector
	 * @param q Second vector
	 * @return Sum of two vectors
	 */
	private Point2D.Double add(Point2D p, Point2D q) {
		return new Point2D.Double(p.getX() + q.getX(), p.getY() + q.getY());
	}
	
	/**
	 * Subtraction of vectors
	 * @param p First vector
	 * @param q Second vector
	 * @return A vector from p to q
	 */
	private Point2D.Double sub(Point2D p, Point2D q) {
		return new Point2D.Double(q.getX() - p.getX(), q.getY() - p.getY());
	}
	
	/**
	 * Multiplication of a vector by a factor
	 * @param p A vector
	 * @param factor A factor
	 * @return New scaled vector
	 */
	private Point2D.Double mul(Point2D p, double factor) {
		return new Point2D.Double(p.getX() * factor, p.getY() * factor);
	}

	/**
	 * Division of a vector by a factor
	 * @param p A vector
	 * @param factor A factor
	 * @return New scaled vector
	 */
	private Point2D.Double div(Point2D p, double factor) {
		return new Point2D.Double(p.getX() / factor, p.getY() / factor);
	}
	
	/**
	 * Normalize a vector to length 1. 
	 * @param p A vector
	 * @return A scaled p of length 1
	 */
	private Point2D.Double normalize(Point2D p) {
		double factor = Math.sqrt(p.distanceSq(0, 0));
		return div(p, factor);
	}
	
	/**
	 * Limit a vector to a maximum length
	 * @param p A vector
	 * @param max A maximum length
	 * @return A scaled p no longer than max. 
	 */
	private Point2D limit(Point2D p, double max) {
		double sq = p.distanceSq(0, 0);
		if (sq > max*max) {
			double factor = max / Math.sqrt(sq);
			return mul(p, factor);
		}
		return p;
	}
}

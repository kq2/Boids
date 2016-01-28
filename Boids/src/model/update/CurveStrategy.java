package model.update;

import java.awt.Point;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;

/**
 * This strategy changes direction of velocity every update
 * @author kq2
 * 
 */
public class CurveStrategy implements IUpdateStrategy {
	/**
	 * The factory that produces all kinds of random data
	 */
	private Randomizer rand = Randomizer.Singleton;
	/**
	 * The velocity's rotating angle in each update
	 */
	private double angle =  (Math.random() < 0.5 ? 1.0 : -1.0) * Math.PI / 180.0 * rand.randomDouble(2.0, 6.0); 
	/**
	 * Hold cos(angle)
	 */
	private double cosA = Math.cos(angle);
	/**
	 * Hold sin(angle)
	 */
	private double sinA = Math.sin(angle);
	
	@Override
	public void init(Ball ball) {
		// no-op
	}
	
	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		Point vel = ball.getVel();
		ball.setVel(vel.x * cosA - vel.y * sinA, 
				    vel.y * cosA + vel.x * sinA);
	}

}

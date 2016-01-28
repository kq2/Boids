package model.update;

import java.awt.Point;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * This strategy that remove the move()
 * @author kq2
 * 
 */
public class HoldStrategy implements IUpdateStrategy {
	
	@Override
	public void init(Ball ball) {
		// no-op
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		Point vel = ball.getVel();
		ball.setVel(-vel.x, -vel.y);
		ball.move();
		ball.setVel(vel.x, vel.y);
	}

}

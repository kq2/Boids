package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * This strategy that randomly changes direction every update
 * @author kq2
 * 
 */
public class WanderStrategy implements IUpdateStrategy {
	
	@Override
	public void init(Ball ball) {
		// no-op
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		IUpdateStrategy strategy = new CurveStrategy();
		strategy.update(ball, disp);
	}

}

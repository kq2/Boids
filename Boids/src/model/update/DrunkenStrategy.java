package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * This strategy does all kinds of updates randomly. 
 * @author kq2
 * 
 */
public class DrunkenStrategy implements IUpdateStrategy {
	/**
	 * The breathing-strategy that born with
	 */
	private IUpdateStrategy breathingStrategy = new BreathingStrategy();
	
	@Override
	public void init(Ball ball) {
		// no-op
	}
	
	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		IUpdateStrategy strategy = Math.random() < 0.5 ? new CurveStrategy() : new ColorStrategy();
		strategy.update(ball, disp);
		breathingStrategy.update(ball, disp);
	}

}

package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * @author kq2
 * The strategy that can set to a new strategy. (initial is straight)
 */
public class SwitcherStrategy implements IUpdateStrategy {
	/**
	 * The initial strategy (StraightStrategy)
	 */
	private IUpdateStrategy _strategy = new StraightStrategy(); 
	
	@Override
	public void init(Ball ball) {
		// no-op
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		_strategy.update(ball, disp);
	}
	/**
	 * Switch to a new strategy
	 * @param newStrategy A new update-strategy 
	 */
	public void setStrategy(IUpdateStrategy newStrategy) {
		_strategy = newStrategy; 
	}

}

package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The strategy that holds multiple strategies
 * @author kq2
 *
 */
public class MultiStrategy implements IUpdateStrategy {
	/**
	 * The first strategy, could also be a MultiStrategy
	 */
	private IUpdateStrategy _s1;
	/**
	 * The second strategy, could also be a MultiStrategy
	 */
	private IUpdateStrategy _s2;
	
	/**
	 * Creates a strategy that holds two strategies
	 * @param s1 The 1st strategy
	 * @param s2 The 2nd strategy
	 */
	public MultiStrategy(IUpdateStrategy s1, IUpdateStrategy s2) {
		_s1 = s1; 
		_s2 = s2; 
	}
	
	@Override
	public void init(Ball ball) {
		_s1.init(ball);
		_s2.init(ball);
	}
	
	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		_s1.update(ball, disp); 
		_s2.update(ball, disp);
	}

}

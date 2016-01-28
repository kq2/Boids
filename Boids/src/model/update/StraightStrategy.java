package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * This strategy does nothing
 * @author kq2
 * 
 */
public class StraightStrategy implements IUpdateStrategy {
	
	@Override
	public void init(Ball ball) {
		// no-op
	}
	
	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		// do nothing
	}

}

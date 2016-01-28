package model;

import util.IDispatcher;

/**
 * A strategy defines how ball should update
 * @author kq2
 *
 */
public interface IUpdateStrategy {
	
	/**
	 * Initialize the strategy and host ball. 
	 * @param host a ball who has this strategy
	 */
	void init(Ball host);	
	
	/**
	 * Updates given ball's states. e.g. color
	 * @param ball The given context to update
	 */
	public void update(Ball ball, IDispatcher<IBallCmd> disp);
	
	/**
	 * No-op singleton implementation of IUpdateStrategy 
	 */
	public static final IUpdateStrategy NULL_OBJECT = new IUpdateStrategy () {
		@Override
		public void init(Ball host) {}
		@Override
		public void update(Ball ball, IDispatcher<IBallCmd> disp) {}
	};

}

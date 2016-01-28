package model;

import java.awt.Graphics;

/**
 * @author kq2
 * The IPaintStrategy defines what a painting strategy can do
 */
public interface IPaintStrategy {
	
	/**
	 * Initialize the strategy and host ball. 
	 * @param host a ball who has this strategy
	 */
	public void init(Ball host);
	
	/**
	 * Paints the host onto the given Graphics context. 
	 * @param g The Graphics context that will be paint on
	 * @param host The host Ball that the required information will be pulled from
	 */
	public void paint(Graphics g, Ball host);
	
	/**
	 * The singleton null object instance for this interface, which does nothing.
	 */
	static final IPaintStrategy NULL_OBJECT = new IPaintStrategy() {

		@Override
		public void init(Ball host) {			
		}

		@Override
		public void paint(Graphics g, Ball host) {			
		}
		
	};

}

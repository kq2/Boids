package model;

import java.awt.Component;

/**
 * The communications interface the model uses to talk to the view for 
 * non-repetitive control tasks such as getting information for the 
 * instantiation of a ball.
 * @author kq2
 *
 */
public interface IViewCntrlAdapter {
	
	/**
	 * Return a Component that represents the surface upon which the balls exist.
	 * @return A Component object
	 */
	public Component getCanvas();
	
}

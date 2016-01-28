package model.paint.strategy;

import model.paint.UprightImagePaintStrategy;

/**
 * Paint strategy that paints a running Super Mario as its image.
 * @author kq2
 *
 */
public class MarioPaintStrategy extends UprightImagePaintStrategy {
	
	/**
	 * Constructor loads the Super Mario image
	 */
	public MarioPaintStrategy() {
		super("images/supermario.gif", 0.80);
	}
	
}

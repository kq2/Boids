package model.paint.strategy;

import java.awt.Graphics;

import model.Ball;
import model.paint.UprightImagePaintStrategy;

/**
 * Paint strategy that paints a swimming clownfish as its image.
 * @author kq2
 *
 */
public class ClownfishPaintStrategy extends UprightImagePaintStrategy {
	
	/**
	 * Constructor loads the clownfish image
	 */
	public ClownfishPaintStrategy() {
		super("images/clownfish.gif", 0.90);
	}

	@Override
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		at.scale(-1.0, 1.0); // flip clownfish image to face right
	}
	
}

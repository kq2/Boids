package model.paint.strategy;

import java.awt.geom.AffineTransform;

/**
 * Paint a circle on canvas
 * @author kq2
 *
 */
public class BallPaintStrategy extends EllipsePaintStrategy {
	
	/**
	 * Create a circle paint strategy
	 */
	public BallPaintStrategy() {
		super(new AffineTransform(), 0, 0, 1, 1);
	}
	
}

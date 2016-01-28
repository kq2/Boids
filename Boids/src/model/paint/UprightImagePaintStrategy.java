package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * An image painting strategy that adds a paintCfg override that keeps the image upright no matter which way it is going.
 * @author kq2
 *
 */
public class UprightImagePaintStrategy extends ImagePaintStrategy {
	
	/**
	 * Constructor for an image painting strategy
	 * @param filename Fully qualified filename of the image file RELATIVE TO THIS PACKAGE, using a forward slash as a directory divider.
	 * @param fillFactor The ratio of the effective diameter (hit circle) of the image to the average of its width and height.
	 */
	public UprightImagePaintStrategy(String filename, double fillFactor) {
		super(new AffineTransform(), filename, fillFactor);
	}
	
	/**
	 * {@inheritDoc}
	 * Override paintCfg to add the transform needed to keep the fish upright at all times.
	 * @param g The Graphics context that will be drawn upon.
	 * @param host The Ball to be painted.
	 */
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if(Math.abs(Math.atan2(host.getVel().y, host.getVel().x))> Math.PI/2.0) {
			at.scale(1.0, -1.0);
		}        
	}

}

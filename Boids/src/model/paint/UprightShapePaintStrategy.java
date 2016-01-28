package model.paint;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * A shape painting strategy that adds a paintCfg override that keeps the shape upright no matter which direction it is going.
 * @author kq2
 *
 */
public class UprightShapePaintStrategy extends ShapePaintStrategy {
	
	/**
	 * Constructor that takes both a Shape and an affine transform
	 * @param at The affine transform object to use
	 * @param shape The prototype shape to use
	 */
	public UprightShapePaintStrategy(AffineTransform at, Shape shape) {
		super(at, shape);
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

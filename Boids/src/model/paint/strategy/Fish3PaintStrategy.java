package model.paint.strategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;
import model.paint.FixedColorDecoratorPaintStrategy;
import model.paint.MultiPaintStrategy;

/**
 * Subclass of MultiPaintStrategy that uses one EllipsePaintStrategy to draw body
 * and one EllipsePaintStrategy to draw eye and one PolygonPaintStrategy to draw tail. 
 * @author kq2
 *
 */
public class Fish3PaintStrategy extends MultiPaintStrategy {
	
	/**
	 * Constructor that uses an externally supplied CompositeFishPaintStrategy for internal use.
	 * @param at The AffineTransform to use
	 */
	public Fish3PaintStrategy(AffineTransform at) {
		super(
			at, 
			new EllipsePaintStrategy(), 
			new EllipsePaintStrategy(new AffineTransform(), -4.0/3.0, 0, 0.6, 0.8), 
			new FixedColorDecoratorPaintStrategy(
				new EllipsePaintStrategy(new AffineTransform(), 0.7, 0.2, 0.22, 0.22),
				Color.BLACK
			)
		);
	}
	
	/**
	 * No-parameter constructor that instantiates the AffineTransform for internal use.
	 */
	public Fish3PaintStrategy() {
		this(new AffineTransform());
	}

	/**
	 * {@inheritDoc}
	 * Override paintCfg to add the transform needed to keep the fish upright at all times.
	 * @param g The Graphics context that will be drawn upon.
	 * @param host The Ball to be painted.
	 */
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if(Math.abs(Math.atan2(host.getVel().y, host.getVel().x)) < Math.PI/2.0) {
			at.scale(1.0, -1.0);
		}        
	}
	
}

package model.paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author kq2
 * This class abstracts out the shape aspects into an Shape object transformed by an affine transform.   
 */
public class ShapePaintStrategy extends APaintStrategy {
	
	/**
	 * The Shape (prototype) to be painted
	 */
	private Shape shape;
	
	/**
	 * Constructor that allocates a new AffineTransform for internal use.
	 * @param shape a shape to paint
	 */
	public ShapePaintStrategy(Shape shape) {
		this(new AffineTransform(), shape);
	}
	
	/**
	 * Constructor that uses a supplied AffineTransform for internal use.
	 * @param at The given affine transform
	 * @param shape The given shape to paint
	 */
	public ShapePaintStrategy(AffineTransform at, Shape shape) {
		super(at);
		this.shape = shape;
	}

	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		((Graphics2D) g).fill(at.createTransformedShape(shape));
	}

}

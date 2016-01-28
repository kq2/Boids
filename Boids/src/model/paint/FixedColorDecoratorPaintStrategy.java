package model.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * Decorator paint strategy that decorates another IPaintStrategy and causes 
 * the painted color to always be a specified, fixed color. Useful when you 
 * want a shape that is a specific color and not the color of the host Ball, 
 * for example, the black colored eye on a fish. Note that this class only 
 * works in situations where paintXfrm is called, because if paint is called, 
 * the call is delegated to the decoree, which will then delegate to its own 
 * paintXfrm method, not this decorator's method. Thus, this class is best used 
 * when being composed with other paint strategies inside a MultiPaintStrategy.
 * @author kq2
 *
 */
public class FixedColorDecoratorPaintStrategy extends ADecoratorPaintStrategy {
	/**
	 * The color that will be painted.
	 */
	private Color color;
	
	/**
	 * Constructor that takes the fixed color and the decoree strategy
	 * @param color The fixed color to use
	 * @param decoree The decoree strategy whose color is overridden
	 */
	public FixedColorDecoratorPaintStrategy(APaintStrategy decoree, Color color) {
		super(decoree);
		this.color = color;
	}
	
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		g.setColor(color);
		super.paintXfrm(g, host, at);
	}

}

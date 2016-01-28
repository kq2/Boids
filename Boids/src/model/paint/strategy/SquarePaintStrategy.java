package model.paint.strategy;

import java.awt.Graphics;

import model.Ball;
import model.IPaintStrategy;

/**
 * Paint strategy that paints a filled square with the Ball's radius.
 * This functionality is duplicated by the RectanglePaintStrategy.
 * The class demonstrates a direct implementation of IPaintStrategy.
 */
public class SquarePaintStrategy implements IPaintStrategy {

	/** 
	 * No parameter constructor for the class
	 */
	public SquarePaintStrategy() {
	}
	
	@Override
	public void init(Ball context){
		// By default, do nothing for initialization.
	} 
	
	@Override
	public void paint(Graphics g, Ball host) {
		int halfSide = host.getRadius();
		g.setColor(host.getColor());
		g.fillRect(host.getLoc().x-halfSide, host.getLoc().y-halfSide, 2*halfSide, 2*halfSide);
	}
	
}

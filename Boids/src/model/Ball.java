package model;

import java.util.function.IntConsumer;

import util.IDispatcher;
import util.IObserver;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;


/**
 * The Ball class defines a sprite that can be updated by Observable
 * @author kq2
 */
public class Ball implements IObserver<IBallCmd> {

	/**
	 * The radius of this ball
	 */
	private int radius;
	/**
	 * The color of this ball
	 */
	private Color color;
	/**
	 * The center location of this ball
	 */
	private Point center;
	/**
	 * The velocity of this ball
	 */
	private Point vel;
	/**
	 * The canvas that paints this ball
	 */
	private Component canvas;
	/**
	 * The strategy this sprite uses to update itself
	 */
	private IUpdateStrategy updateStrat;
	/**
	 * The strategy this sprite uses to interact with other
	 */
	private IInteractStrategy interactStrat;
	/**
	 * The strategy this sprite uses to paint itself
	 */
	private IPaintStrategy paintStrat;
	/**
	 * The initial radius of ball, used in breathing strategy
	 */
	private final int initRadius;

	/**
	 * Create a ball that can move bounce update and paint
	 * @param radius The radius of this ball
	 * @param color The color of this ball
	 * @param center The center location of this ball
	 * @param vel The velocity of this ball
	 * @param canvas The canvas contains this ball
	 * @param updateStrat The update strategy of this ball
	 * @param paintStrat The paint strategy of this ball
	 */
	public Ball(int radius, Color color, Point center, Point vel, Component canvas, IUpdateStrategy updateStrat, IPaintStrategy paintStrat) {
		this.radius = radius;
		this.color = color;
		this.center = center;
		this.vel = vel;
		this.canvas = canvas;
		this.setInteractStrategy(IInteractStrategy.NULL_STRATEGY);
		this.setUpdateStrategy(updateStrat);
		this.setPaintStrategy(paintStrat);
		initRadius = radius;
	}
	
	/**
	 * Interact with another ball
	 * @param target Another ball
	 * @param disp A dispatcher that dispatch IBallCmd
	 */
	public void interactWith(Ball target, IDispatcher<IBallCmd> disp) {
		getInteractStrategy().interact(this, target, disp);
	}

	/**
	 * The update method called by the main ball Dispatcher to notify all the balls to perform the given command.
	 * The given command is executed.
	 * @param disp A dispatcher that dispatch IBallCmd
	 */
	public void update(IDispatcher<IBallCmd> disp) {
		getUpdateStrategy().update(this, disp);
	}

	/**
	 * Paint this sprite on canvas, implemented in concrete class. 
	 * @param g a graphics object for paint
	 */
	public void paint(Graphics g) {
		getPaintStrategy().paint(g, this);
	}

	/**
	 * Move to a new location
	 */
	public void move() {
		center.translate(vel.x, vel.y);
	}

	/**
	 * Bounce in one dimension
	 * @param curLoc a current location
	 * @param radius a radius in this dimension
	 * @param minBound The minimum location in canvas
	 * @param maxBound The maximum location in canvas
	 * @param bounceFn A lambda function for either horizontal or vertical bounce
	 */
	public void bounce1D(int curLoc, int radius, int minBound, int maxBound, IntConsumer bounceFn) {
		// out of minimum bound
		if (curLoc - radius < minBound) {
			int newLoc = curLoc + 2 * (minBound - curLoc + radius);
			bounceFn.accept(newLoc);		
			// out of maximum bound
		} else if (curLoc + radius > maxBound) {
			int newLoc = curLoc - 2 * (curLoc - maxBound + radius);
			bounceFn.accept(newLoc);	
			// still inside bounds
		} else {
			// no bounce
		}
	}

	/**
	 * Bounce this sprite against canvas borders
	 */
	public void bounce() {
		// bounce horizontally
		bounce1D(center.x, radius, 0, canvas.getWidth(), 
				(x) -> {center.x = x; vel.x *= -1;});
		// bounce vertically
		bounce1D(center.y, radius, 0, canvas.getHeight(), 
				(y) -> {center.y = y; vel.y *= -1;});
	}
	
	/**
	 * Get the distance to the other ball
	 * @param other The other ball
	 * @return The distance to the other ball
	 */
	public double distance(Ball other) {
		return center.distance(other.getLoc());
	}
	
	/**
	 * Returns the center location of this sprite
	 * @return the center location of this ball
	 */
	public Point getLoc() {
		return center;
	}

	/**
	 * Set the center location of this sprite
	 * @param x a horizontal location
	 * @param y a vertical location
	 */
	public void setLoc(double x, double y) {
		center.setLocation(x, y);
	}

	/**
	 * Returns the radius of this sprite
	 * @return the radius of this ball
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Set the radius of this sprite
	 * @param radius the new radius of this sprite
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Get the velocity of this sprite
	 * @return the velocity of this ball
	 */
	public Point getVel() {
		return vel;
	}

	/**
	 * Set the velocity of this sprite
	 * @param u a horizontal velocity
	 * @param v a vertical velocity
	 */
	public void setVel(double u, double v) {
		vel.setLocation(u, v);
	}
	
	/**
	 * Get the canvas that paints this sprite
	 * @return the canvas that paints this ball
	 */
	public Component getCanvas() {
		return canvas;
	}

	/**
	 * Set a new canvas that paints this sprite
	 * @param newCanvas a new canvas
	 */
	public void setCanvas(Component newCanvas) {
		canvas = newCanvas;
	}

	/**
	 * Get the color of this ball
	 * @return the new color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the color of this ball
	 * @param color initial vertical radius
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the initial radius of this ball
	 * @return the initial radius
	 */
	public int getInitRadius() {
		return initRadius;
	}

	/**
	 * Get the strategy of updating this sprite
	 * @return the strategy of updating this sprite
	 */
	public IUpdateStrategy getUpdateStrategy() {
		return updateStrat;
	}

	/**
	 * Set update-strategy to a new one
	 * @param strategy a new strategy
	 */
	public void setUpdateStrategy(IUpdateStrategy strategy) {
		this.updateStrat = strategy;
		this.updateStrat.init(this);
	}

	/**
	 * Get the paint-strategy of this ball
	 * @return the paint-strategy of this ball
	 */
	public IPaintStrategy getPaintStrategy() {
		return paintStrat;
	}

	/**
	 * Set the paint-strategy of this ball, and initialize the strategy with the ball
	 * @param strategy A new paint strategy
	 */
	public void setPaintStrategy(IPaintStrategy strategy) {
		this.paintStrat = strategy;
		this.paintStrat.init(this);
	}
	
	/**
	 * Get the interact strategy
	 * @return The interact strategy
	 */
	public IInteractStrategy getInteractStrategy() {
		return interactStrat;
	}
	
	/**
	 * Set the interact strategy for this ball
	 * @param strategy A new interact strategy
	 */
	public void setInteractStrategy(IInteractStrategy strategy) {
		this.interactStrat = strategy;
	}
	
	@Override
	public void execute(IDispatcher<IBallCmd> disp, IBallCmd cmd) {
		cmd.apply(this, disp);
	}

}

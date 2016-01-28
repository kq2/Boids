package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * A composite design pattern extension of APaintStrategy that paints a set of paint strategies. 
 * Note: This paint strategy cannot be used directly by the BallWorld system because it lacks a no-parameter constructor.
 * @author kq2
 *
 */
public abstract class MultiPaintStrategy extends APaintStrategy {
	
	/**
	 * The set of paint strategies to paint
	 */
	private APaintStrategy[] pstrats;
	/**
	 * Constructor that takes the paint strategies that will part of the composite.
	 * @param at The AffineTransform to use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... pstrats) {
		super(at);
		this.pstrats = pstrats;
	}
	
	/**
	 * Constructor that takes the paint strategies that will part of the composite. 
	 * An AffineTransform is instantiated for internal use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public MultiPaintStrategy(APaintStrategy... pstrats) {
		this(new AffineTransform(), pstrats);
	}
	
	@Override
	public void init(Ball host) {
		for (APaintStrategy strat : pstrats) {
			strat.init(host);
		}
	}
	
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		for (APaintStrategy strat : pstrats) {
			strat.paintXfrm(g, host, getAT());
		}
	}

}

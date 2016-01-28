package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;
import model.IPaintStrategy;

/**
 * @author kq2
 * This class provides the basic affine transform services that its 
 * subclasses will use to resize, translate and rotate their prototype images 
 * into their proper current locations and orientations on the screen. 
 */
public abstract class APaintStrategy implements IPaintStrategy {
	/**
	 * The affine transform used by this paint strategy to translate, scale and rotate the image.
	 */
	protected AffineTransform at;
	
	/**
	 * Create a class designed to be the root class for all strategies 
	 * that use affine transforms to create their visual representations.
	 * @param at The affine transform used by this paint strategy to translate, scale and rotate the image.
	 */
	public APaintStrategy(AffineTransform at) {
		this.at = at;
	}
	
	/**
	 * Protected accessor for the internal affine transform
	 * @return This instance's affine transform
	 */
	protected AffineTransform getAT() {
		return at;
	}

	@Override
	public void init(Ball host) {
		// By default, do nothing for initialization.
	}

	@Override
	public void paint(Graphics g, Ball host) {
		double scale = host.getRadius();
		at.setToTranslation(host.getLoc().x, host.getLoc().y);
		at.scale(scale, scale);
		at.rotate(Math.atan2(host.getVel().y, host.getVel().x));
		g.setColor(host.getColor());    
		paintCfg(g, host);
		paintXfrm(g, host, at);
	}
	
	/**
	 * This method allows the subclass to inject additional processing 
	 * into the paint method process before the final transformations are performed. 
	 * Since this method is "protected", it is only available for use 
	 * by the subclasses and not other types of objects.
	 * @param g The Graphics context that will be paint on
	 * @param host The host Ball that the required information will be pulled from
	 */
	protected void paintCfg(Graphics g, Ball host) {
		// The paintCfg method is set to be a concrete no-op that the subclasses may or may not override. 
	}
	
	/**
	 * Use supplied affine transformation to paint. 
	 * @param g The Graphics context that will be paint on
	 * @param host The host Ball that the required information will be pulled from
	 * @param at The supplied affine transformation
	 */
	public abstract void paintXfrm(Graphics g, Ball host, AffineTransform at);
}

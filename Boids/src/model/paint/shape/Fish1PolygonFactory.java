package model.paint.shape;

import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * Concrete PolygonFactory that creates fish-shaped Polygons that have a longer tail and an open mouth.
 * @author kq2
 *
 */
public class Fish1PolygonFactory extends PolygonFactory {
	
	/**
	 * Constructor that calls the PolygonFactory superclass constructor with the scale factor and polygon points that define the fish shape.
	 */
	public Fish1PolygonFactory() {
		super(new AffineTransform(), 0.5, 
				new Point(2, 1), new Point(1, 2), new Point(-1, 1), new Point(-2, 2), 
				new Point(-2, -2), new Point(-1, -1), new Point(1, -2), new Point(2, -1));
	}
	
	/**
	 * Singleton pattern
	 */
	public static final Fish1PolygonFactory Singleton = new Fish1PolygonFactory() {};

}

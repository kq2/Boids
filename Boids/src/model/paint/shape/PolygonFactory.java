package model.paint.shape;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * Concrete IShapeFactory that provides the invariant behavior to instantiate a Shape that is a Polygon. 
 * This class can be instantiated and used simply by supplying the desired points in its constructor, 
 * or sub-classed an the constructor overridden. Note that this class cannot be used directly by the 
 * BallWar system because it does not have a no-parameter constructor.
 * @author kq2
 *
 */
public class PolygonFactory implements IShapeFactory {
	/**
	 * The AffineTransform used for internal calculations
	 */
	private AffineTransform at;
	/**
	 * The Polygon shape to use as the prototype.
	 */
	private Polygon poly;
	/**
	 * Scale factor that scales the integer Point-defined Polygon to a unit size, which requires doubles.
	 */
	private double scaleFactor;

	/**
	 * Constructor that uses an externally defined AffineTransform for internal use 
	 * plus takes the defining points of the prototype Polygon and a scale factor 
	 * to scale the given points to the desired unit size. Since Polygons require 
	 * Point objects for their definition, a prototype Polygon cannot be defined 
	 * of arbitrary size because Points are defined on an integer grid. Thus, a 
	 * double scale factor is also provided that is used to scale the Polygon 
	 * via the affine transform into a Shape of the desired size.
	 * @param at The AffineTransform to use.
	 * @param scaleFactor The ratio of the desired unit size to the defined size of the prototype Polygon.
	 * @param pts Vararg parameters that are the Points that define the Polygon around the origin as its center.
	 */
	public PolygonFactory(AffineTransform at, double scaleFactor, Point... pts) {
		this.at = at;
		this.scaleFactor = scaleFactor;
		this.poly = new Polygon();
		for (int i=0; i<pts.length; i++) {
			poly.addPoint(pts[i].x, pts[i].y);
		}		
	}

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		at.setToTranslation(x, y);
		at.scale(xScale*scaleFactor, yScale*scaleFactor);  // optional rotation can be added as well
		return at.createTransformedShape(poly);
	}
}

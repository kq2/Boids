package model.paint.shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Concrete shape factory that instantiates Ellipse2D.Double shapes.
 * @author kq2
 *
 */
public class EllipseShapeFactory implements IShapeFactory {
	
	/**
	 * 
	 */
	private EllipseShapeFactory() {}

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return null;
	}
	
	/**
	 * Singleton pattern
	 */
	public static final EllipseShapeFactory Singleton = new EllipseShapeFactory() {
		@Override
		public Shape makeShape(double x, double y, double xScale, double yScale) {
			return new Ellipse2D.Double(x-xScale, y-yScale, xScale*2, yScale*2);
		}
	};
	
}

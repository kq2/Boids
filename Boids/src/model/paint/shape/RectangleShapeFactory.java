package model.paint.shape;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Concrete IShapeFactory that instantiates Rectangle2d.Double shapes.
 * @author kq2
 *
 */
public class RectangleShapeFactory implements IShapeFactory  {
	
	/**
	 * 
	 */
	private RectangleShapeFactory() {}

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return null;
	}

	/**
	 * Singleton pattern
	 */
	public static final RectangleShapeFactory Singleton = new RectangleShapeFactory() {
		@Override
		public Shape makeShape(double x, double y, double xScale, double yScale) {
			return new Rectangle2D.Double(x-xScale, y-yScale, xScale*2, yScale*2);
		}
	};
}

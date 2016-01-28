package view;

import java.awt.Graphics;

/**
 * The interface of the adapter from the view to the model that enables the view to talk to the model.
 */
public interface IModelPaintAdapter {
	
	/**
	 * Paint graphics onto canvas
	 * @param g A graphics object
	 */
	public void paint(Graphics g);
	
	/**
	 * No-op singleton implementation of IView2ModelAdapter 
	 * See the web page on the Null Object Design Pattern at http://cnx.org/content/m17227/latest/
	 */
	public static final IModelPaintAdapter NullObject = new IModelPaintAdapter () {		
		@Override
	    public void paint(Graphics g) {}
	};

}

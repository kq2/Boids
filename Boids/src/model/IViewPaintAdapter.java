package model;

/**
 * Interface that goes from the model to the view that enables the model to talk to the view
 */
public interface IViewPaintAdapter {
	
	/**
	 * The method that tells the view to update
	 */
	public void paint();
	
	/**
	 * No-op "null" adapter
	 * See the web page on the Null Object Design Pattern at http://cnx.org/content/m17227/latest/
	 */
	public static final IViewPaintAdapter NULL_OBJECT = new IViewPaintAdapter() {
		public void paint() {
		}
	};
}

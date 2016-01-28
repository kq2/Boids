package model;

/**
 * Interface from the model to the view that enables the model to update the view
 * @author kq2
 *
 */
public interface IViewUpdateAdapter {
	
	/**
	 * Ask view to update
	 */
	public void update();
	
	/**
	 * Null adapter
	 */
	public static final IViewUpdateAdapter NULL_OBJECT = new IViewUpdateAdapter() {

		@Override
		public void update() {			
		}
		
	};

}

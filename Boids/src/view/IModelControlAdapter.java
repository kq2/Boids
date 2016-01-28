package view;

/**
 * Adapter that the view uses to communicate to the model for non-repetitive control tasks such as manipulating strategies.
 * @param <TUpdateDropListItem> The type of objects put into the view's drop lists.
 * @param <TPaintDropListItem> The type of objects put into the paint drop list.
 * @author swong
 *
 */
public interface IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> {

    /**
     * Take the given short strategy name and return a corresponding something to put onto both drop lists.
     * @param className The name of paint strategy
     * @return Something to put onto both the drop lists.
     */
    public TPaintDropListItem addPaintStrat(String className);

    /**
     * Take the given short strategy name and return a corresponding something to put onto both drop lists.
     * @param className  The shortened class name of the desired strategy
     * @return Something to put onto both the drop lists.
     */
    public TUpdateDropListItem addUpdateStrat(String className);
    
    /**
     * Return a new object to put on both lists, given two items from the lists.
     * @param selectedItem1  An object from one drop list
     * @param selectedItem2 An object from the other drop list
     * @return An object to put back on both lists.
     */
    public TUpdateDropListItem combineUpdateStrats(TUpdateDropListItem selectedItem1, TUpdateDropListItem selectedItem2);
    
    /**
     * Make a ball with the selected short strategy name.
     * @param selectedItem1  A shorten class name for the desired strategy
     * @param selectedItem2  A shorten class name for the desired strategy
     */
    public void makeBall(TPaintDropListItem selectedItem1, TUpdateDropListItem selectedItem2);
    
    /**
     * Make a ball that can switch to a new strategy
     * @param selectedItem  A shorten class name for the desired strategy
     */
    public void makeSwitcherBall(TPaintDropListItem selectedItem);
    
    /**
     * Switch all switcher ball to selected strategy
     * @param selectedItem The current selected name in the first drop-list
     */
    public void switchBalls(TUpdateDropListItem selectedItem);

	/**
	 * clear balls from ball model
	 */
	public void clearBalls();
	
	/**
	 * Check if the model is running
	 * @return True if is running else false
	 */
	public boolean isRunning();
	
	/**
	 * Stop the update timer in model
	 */
	public void stop();
	
	/**
	 * Start the update timer in model
	 */
	public void start();

}

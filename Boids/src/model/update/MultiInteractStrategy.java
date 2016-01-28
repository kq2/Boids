package model.update;

import model.Ball;
import model.IBallCmd;
import model.IInteractStrategy;
import util.IDispatcher;

/**
 * A combined interact strategy with multiple interact Strategies
 */
public class MultiInteractStrategy implements IInteractStrategy {
	/**
	 * The first interact strategy to combine
	 */
	private IInteractStrategy strat1;
	/**
	 * The second interact strategy to combine
	 */
	private IInteractStrategy strat2;

	/**
	 * Constructor to instantiate a combined multiInteract strategy with given strategies
	 * @param s1 The first interact strategy to combine
	 * @param s2 The second interact strategy to combine
	 */
	public MultiInteractStrategy(IInteractStrategy strat1, IInteractStrategy strat2) {
		this.strat1 = strat1;
		this.strat2 = strat2;
	}

	/**
	 * Interact with two strategies
	 * @param context the host ball
	 * @param target the target to be interacted with
	 * @param disp  The Dispatcher who sent the command that is calling through to here
	 */
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
		strat1.interact(context, target, disp);
		strat2.interact(context, target, disp);
	}

}

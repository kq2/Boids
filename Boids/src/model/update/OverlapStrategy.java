package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * A strategy that determines if it senses another balls. If so, invoke interact strategy of the ball.
 *
 */
public class OverlapStrategy implements IUpdateStrategy {

	@Override
	public void init(Ball context){	
	}

	@Override
	/**
	 * Check if there is overlap between host ball and other balls
	 * @param context  The context (host) Ball whose state is to be updated
	 * @param dispatcher  The Dispatcher who sent the command that is calling through to here
	 */

	public void update(final Ball ball, IDispatcher<IBallCmd> dispatcher) {
		dispatcher.dispatch((other, disp) -> {
			if (ball != other) {
				double dist = ball.distance(other);
				if (dist < ball.getRadius() + other.getRadius()) {
					ball.interactWith(other, disp);
				} 
			}
		});
	}
	
}

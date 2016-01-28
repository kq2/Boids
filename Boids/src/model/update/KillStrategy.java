package model.update;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IInteractStrategy;
import model.IUpdateStrategy;
import util.IDispatcher;

/**
 * The strategy that kills the colliding ball
 * @author kq2
 *
 */
public class KillStrategy implements IUpdateStrategy {

	@Override
	public void init(Ball context) {
		context.setColor(Color.BLACK);
		context.setInteractStrategy(new MultiInteractStrategy(
				context.getInteractStrategy(), new IInteractStrategy() {

					@Override
					public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
						disp.deleteObserver(target);
					}

				}));
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		// No-op
	}

}

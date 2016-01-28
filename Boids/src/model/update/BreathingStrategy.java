package model.update;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;
import util.SineMaker;

/**
 * This strategy changes size every update
 * @author kq2
 * 
 */
public class BreathingStrategy implements IUpdateStrategy {

	/**
	 * The factory that produces all kinds of random data
	 */
	private Randomizer rand = Randomizer.Singleton;
	/**
	 * Factories that produce sine value
	 */
	private SineMaker sine = new SineMaker(0.8, 1.2, Math.PI/180 * rand.randomInt(1, 9));
	
	@Override
	public void init(Ball ball) {
		// no-op
	}

	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		int radius = (int) (ball.getInitRadius() * sine.getDblVal());
		ball.setRadius(radius);
	}

}

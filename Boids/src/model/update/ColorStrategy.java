package model.update;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import util.IDispatcher;
import util.Randomizer;

/**
 * This strategy changes color every update
 * @author kq2
 * 
 */
public class ColorStrategy implements IUpdateStrategy {
	/**
	 * The factory that produces all kinds of random data
	 */
	private Randomizer randomizer = Randomizer.Singleton;
	
	@Override
	public void init(Ball ball) {
		// no-op
	}
	
	@Override
	public void update(Ball ball, IDispatcher<IBallCmd> disp) {
		Color color = randomizer.randomColor();
		ball.setColor(color);
	}

}

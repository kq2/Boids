package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Component;

import javax.swing.Timer;

import util.*;
import model.update.*;

/**
 * @author kq2
 * The model side of ball world. 
 */
public class BallModel {
	/**
	 * Adapter to the view for paint tasks
	 */
	private IViewPaintAdapter viewPaintAdpt = IViewPaintAdapter.NULL_OBJECT;
	/**
	 * Adapter to the view for control tasks.
	 */
	private IViewCntrlAdapter viewCntrlAdpt;
	/**
	 * Adapter to the view for update tasks.
	 */
	private IViewUpdateAdapter viewUpdateAdpt = IViewUpdateAdapter.NULL_OBJECT;
	/**
	 * The dispatcher that updates all the balls.
	 */
	private IDispatcher<IBallCmd> dispatcher = new SetDispatcherSequential<IBallCmd>();

	/**
	 * The paint time interval between timer ticks, in milliseconds
	 */
	private int paintTimeSlice = 40;
	/**
	 * The update time interval between timer ticks, in milliseconds
	 */
	private int updateTimeSlice = 30;
	/**
	 * The timer that controls when the balls are periodically painted.
	 */
	private Timer paintTimer = new Timer(paintTimeSlice, (e) -> viewPaintAdpt.paint());
	/**
	 * The timer that updates this ball model, which is independent from view. 
	 */
	private Timer updateTimer = new Timer(updateTimeSlice, (e) -> viewUpdateAdpt.update());
	/**
	 * A factory for a painting error strategy that paint a error message on canvas
	 */
	private IPaintStrategyFac errorPaintStratFac = new IPaintStrategyFac() {
		@Override
		/**
		 * Make the beeping error strategy
		 * @return  An instance of a beeping error strategy
		 */
		public IPaintStrategy make() {
			return new IPaintStrategy() {
				@Override
				public void init(Ball host) {
				}
				/**
				 * Paint an error message on screen
				 */
				@Override
				public void paint(Graphics g, Ball host) {
					g.setColor(Color.RED);
					g.drawString("INVALID PAINT STRATEGY!", 0, 0);
				}
			};
		}
	};
	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 */
	private IUpdateStrategyFac errorUpdateStratFac = new IUpdateStrategyFac() {
		@Override
		/**
		 * Make the beeping error strategy
		 * @return  An instance of a beeping error strategy
		 */
		public IUpdateStrategy make() {
			return new IUpdateStrategy() {
				private int count = 0; // update counter
				
				@Override
				public void init(Ball ball) {
					// no-op
				}
				
				@Override
				/**
				 * Beep the speaker every 25 updates
				 */
				public void update(Ball context, IDispatcher<IBallCmd> disp) {
					if(25 < count++){
						java.awt.Toolkit.getDefaultToolkit().beep(); 
						count = 0;
					}
				}
			};
		}
	};
	/**
	 * The one switcher strategy instance in the system.
	 */
	private SwitcherStrategy switcherStrategy = new SwitcherStrategy();

	/**
	 * The minimum/maximum radius for a new ball
	 */
	int minRadius = 8, maxRadius = 16;
	/**
	 * The bounds for the velocity of a new ball
	 */
	Rectangle rangeVel = new Rectangle(5, 5, 10, 10);
	/**
	 * The factory that produces all kinds of random data
	 */
	private Randomizer rand = Randomizer.Singleton;

	/**
	 * Create a model of ball world
	 * @param viewPaintAdpt The instance of model-to-view adaptor
	 */
	public BallModel(IViewPaintAdapter viewPaintAdpt, IViewCntrlAdapter viewCntrlAdpt, IViewUpdateAdapter viewUpdateAdpt) {
		this.viewPaintAdpt = viewPaintAdpt;
		this.viewCntrlAdpt = viewCntrlAdpt;
		this.viewUpdateAdpt = viewUpdateAdpt;
	}

	/**
	 * Start the update timer
	 */
	public void start() {
		paintTimer.start();
		updateTimer.start();
	}
	
	/**
	 * Check if update timer is running
	 */
	public boolean isRunning() {
		return updateTimer.isRunning();
	}
	
	/**
	 * Stop the update timer
	 */
	public void stopUpdate() {
		updateTimer.stop();
	}
	
	/**
	 * Start the update timer
	 */
	public void startUpdate() {
		updateTimer.start();
	}
	
	/**
	 * Add a ball (sprite) to this ball world (dispatcher). 
	 * @param strategy A strategy that ball should use to update
	 * @param canvas A canvas that all balls will be paint on
	 * @param paintStrat A paint strategy
	 */
	public void makeBall(IPaintStrategy paintStrat, IUpdateStrategy updateStrat) {
		Component canvas = viewCntrlAdpt.getCanvas();
		Color color = rand.randomColor();
		Point vel = rand.randomVel(rangeVel);
		int radius = rand.randomInt(minRadius, maxRadius);
		Point center = rand.randomLoc(new Rectangle(radius, radius, canvas.getWidth()-radius, canvas.getHeight()-radius));
		dispatcher.addObserver(new Ball(radius, color, center, vel, canvas, updateStrat, paintStrat));
	}

	/**
	 * Clear all balls in this ball world (dispatcher)
	 */
	public void clearBalls() {
		dispatcher.deleteObservers();
	}

	/**
	 * Used by adapter to view's repaint, paints balls in given graphics. 
	 * @param g The Graphics object from the view's paintComponent() call.
	 */
	public void paint(Graphics g) {
		dispatcher.dispatch((context, disp) -> {
			context.paint(g);
		});		
	}

	/**
	 * Update all the balls in the system, passing the given input parameter.
	 */
	public void update() {
		dispatcher.dispatch((context, disp) -> {
			context.move();
			context.bounce();
			context.update(disp);
		});		
	}

	/**
	 * Uses dynamic class loading to load and instantiate an IPaintStrategy implementation
	 * @param className A fully qualified className of a strategy
	 * @return An instance of the supplied class. 
	 */
	public IPaintStrategy loadPaintStrat(String className) {
		try {
			Object[] args = new Object[] {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(className).getConstructors(); 
			java.lang.reflect.Constructor<?> c = null; 
			for(int i=0;i < cs.length; i++) {
				if(args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			return (IPaintStrategy) c.newInstance(args); 
		}
		catch(Exception ex) {
			System.err.println("Class "+className+" failed to load. \nException = \n"+ ex);
			ex.printStackTrace();
			return IPaintStrategy.NULL_OBJECT;
		}
	}

	/**
	 * Uses dynamic class loading to load and instantiate an IUpdateStrategy implementation
	 * @param className A fully qualified className of a strategy
	 * @return An instance of the supplied class. 
	 */
	public IUpdateStrategy loadUpdateStrat(String className) {
		try {
			Object[] args = new Object[] {};
			java.lang.reflect.Constructor<?> cs[] = Class.forName(className).getConstructors();  // get all the constructors
			java.lang.reflect.Constructor<?> c = null; 
			for(int i=0;i < cs.length; i++) {  // find the first constructor with the right number of input parameters
				if(args.length == (cs[i]).getParameterTypes().length) {
					c = cs[i];
					break;
				}
			}
			return (IUpdateStrategy) c.newInstance(args);   // Call the constructor. Will throw a null ptr exception if no constructor with the right number of input parameters was found.
		}
		catch(Exception ex) {
			System.err.println("Class "+className+" failed to load. \nException = \n"+ ex);
			ex.printStackTrace();  // print the stack trace to help in debugging.
			return IUpdateStrategy.NULL_OBJECT;
		}
	}

	/**
	 * Returns an IPaintStrategy that can instantiate the strategy specified by className. 
	 * @param className  Shortened name of desired strategy
	 * @return A paint strategy
	 */
	public IPaintStrategyFac makePaintStratFac(final String className) {
		if (null == className) return errorPaintStratFac;
		return new IPaintStrategyFac() {
			@Override
			public IPaintStrategy make() {
				return loadPaintStrat("model.paint.strategy."+className+"PaintStrategy");
			}
			@Override
			public String toString() {
				return className;
			}
		};
	}

	/**
	 * Returns an IUpdateStrategyFac that can instantiate the strategy specified by className.
	 * @param className  Shortened name of desired strategy
	 * @return A factory to make that strategy
	 */
	public IUpdateStrategyFac makeUpdateStratFac(final String className) {
		if (null == className) return errorUpdateStratFac;
		return new IUpdateStrategyFac() {
			@Override
			public IUpdateStrategy make() {
				return loadUpdateStrat("model.update."+className+"Strategy");
			}
			@Override
			public String toString() {
				return className;
			}
		};
	}

	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the two strategies made by the two given IUpdateStrategyFac objects.
	 * @param stratFac1 An IStrategyFac for a strategy
	 * @param stratFac2 An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IUpdateStrategyFac combineUpdateStratFacs(final IUpdateStrategyFac stratFac1, final IUpdateStrategyFac stratFac2) {
		if (null == stratFac1 || null == stratFac2) return errorUpdateStratFac;
		return new IUpdateStrategyFac() {
			/**
			 * Instantiate a new MultiStrategy with the strategies from the given strategy factories
			 * @return A MultiStrategy instance
			 */
			public IUpdateStrategy make() {
				return new MultiStrategy(stratFac1.make(), stratFac2.make());
			}

			/**
			 * Return a string that is the toString()'s of the given strategy factories concatenated with a "-"
			 */
			public String toString() {
				return stratFac1.toString() + "-" + stratFac2.toString();
			}
		};
	}

	/**
	 * Get current switcher-balls's strategy
	 * @return Current switcher-balls's strategy
	 */
	public IUpdateStrategy getSwitcherStrategy() {
		return switcherStrategy;
	}

	/**
	 * Set switcher-balls's strategy to a new one
	 * @param strategy A new strategy
	 */
	public void switchUpdateStrat(IUpdateStrategy strategy) {
		switcherStrategy.setStrategy(strategy);
	}

}

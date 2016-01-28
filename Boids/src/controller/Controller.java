package controller;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import view.*;
import model.*;

/**
 * MVC Controller for the system
 */
public class Controller {

	/**
	 * starts off null but will be fine when the constructor is finished.
	 */
	private BallModel model; 
	/**
	 * starts off null but will be fine when the constructor is finished.
	 */
	private BallGUI<IUpdateStrategyFac, IPaintStrategyFac> view; 

	/**
	 * Controller constructor builds the system
	 */
	public Controller() {
		/**
		 * Set the model field. 
		 * Here the model is shown being constructed first then the view 
		 * but it could easily be the other way around if needs dictated it.
		 */
		model = new BallModel(
				new IViewPaintAdapter() {
					// In the adapter code, one can reference the view field above because it is in scope.
					// It's OK that the view field is currently null because it will be set below. Just don't start the model yet!
					@Override
					public void paint() {
						view.paint();
					}
				}, 
				new IViewCntrlAdapter() {
					@Override
					public Component getCanvas() {
						return view.getCanvas();
					}
				}, 
				new IViewUpdateAdapter() {
					@Override
					public void update() {
						view.update();
					}
				});

		/**
		 * Set the view field. 
		 * At this point, both the model and view are instantiated as well as both adapters, 
		 * and both adapters reference non-null model and view fields.
		 * NEITHER MODEL NOR VIEW SHOULD BE STARTED YET!
		 */
		view = new BallGUI<IUpdateStrategyFac, IPaintStrategyFac>(
				new IModelPaintAdapter() {
					@Override
					public void paint(Graphics g) {
						model.paint(g);
					}
				},
				new IModelUpdateAdapter() {
					@Override
					public void update() {
						model.update();
					}
				}, 
				new IModelControlAdapter<IUpdateStrategyFac, IPaintStrategyFac>() {
					@Override
					public IPaintStrategyFac addPaintStrat(String className) {
						return model.makePaintStratFac(className);
					}
					@Override
					public IUpdateStrategyFac addUpdateStrat(String className) {
						return model.makeUpdateStratFac(className);
					}
					@Override
					public IUpdateStrategyFac combineUpdateStrats(IUpdateStrategyFac updateFac1, IUpdateStrategyFac updateFac2) {
						return model.combineUpdateStratFacs(updateFac1, updateFac2);
					}
					@Override
					public void makeBall(IPaintStrategyFac paintFac, IUpdateStrategyFac updateFac) {
						if (null != updateFac && null != paintFac) {
							model.makeBall(paintFac.make(), updateFac.make()); 
						}
					}
					@Override
					public void makeSwitcherBall(IPaintStrategyFac paintFac) {
						model.makeBall(paintFac.make(), model.getSwitcherStrategy());
					}
					@Override
					public void switchBalls(IUpdateStrategyFac selectedItem) {
						model.switchUpdateStrat(selectedItem.make());
					}
					@Override
					public void clearBalls() {
						model.clearBalls();
					}
					@Override
					public boolean isRunning() {
						return model.isRunning();
					}
					@Override
					public void stop() {
						model.stopUpdate();
					}
					@Override
					public void start() {
						model.startUpdate();
					}
				});
	}

	/**
	 * Start the system. 
	 */
	public void start() {
		model.start(); // It is usually better to start the model first but not always.
		view.start();
	}

	/**
	 * Launch the application.
	 * @param args Arguments given by the system or command line.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { // Java specs say that the system must be constructed on the GUI event thread.
			public void run() {
				try {
					Controller controller = new Controller(); // instantiate the system
					controller.start(); // start the system
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * The GUI of our ball world. The View side in Model-View-Controller
 * @param <TUpdateDropListItem> The type of objects put into the update drop lists.
 * @param <TPaintDropListItem> The type of objects put into the paint drop list. 
 * @author kq2
 */
public class BallGUI<TUpdateDropListItem, TPaintDropListItem> extends JFrame {
	/**
	 * The auto generated UID
	 */
	private static final long serialVersionUID = 7433562622438212833L;

	/**
	 * The view-to-model adapter that asks model to paint
	 */
	private IModelPaintAdapter _modelPaintAdpt = IModelPaintAdapter.NullObject;
	/**
	 * The view-to-model adapter that asks model to update
	 */
	private IModelUpdateAdapter _modelUpdateAdpt = IModelUpdateAdapter.NullObject;
	/**
	 * The view-to-model adapter that controls the elements in model
	 */
	private IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> _modelControlAdpt;

	/**
	 * The main GUI frame
	 */
	private JPanel contentPane;
	/**
	 * The canvas panel with an overridden paintComponent method.
	 */
	private final JPanel pnlCenter = new JPanel() {
		/**
		 * The auto generated UID
		 */
		private static final long serialVersionUID = -6952656931251224807L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // clear the panel and redo the background
			_modelPaintAdpt.paint(g); // call back to the model to paint the sprites
		}
	};
	/**
	 * The north panel with text filed and buttons
	 */
	private final JPanel pnlNorth = new JPanel();
	/**
	 * The north panel with text filed and buttons
	 */
	private final JPanel pnlUpdate = new JPanel();
	/**
	 * The panel with two drop-lists
	 */
	private final JPanel pnlUpdateList = new JPanel(); 
	/**
	 * The panel with make-ball button and combine-strategy button
	 */
	private final JPanel pnlSwitch = new JPanel();
	/**
	 * The panel to clear/stop/start balls
	 */
	private final JPanel pnlClear = new JPanel();
	/**
	 * The panel that can add new paint strategy
	 */
	private final JPanel pnlPaint = new JPanel();

	/**
	 * The text field to type in ball classes
	 */
	private final JTextField txfUpdate = new JTextField();
	/**
	 * The text field to type in paint strategy
	 */
	private final JTextField txfPaint = new JTextField();

	/**
	 * button to add a new update strategy to drop list
	 */
	private final JButton btnAddUpdate = new JButton("Add Strategy");
	/**
	 * button to make a ball
	 */
	private final JButton btnMakeBall = new JButton("Make Ball");
	/**
	 * button to make a ball
	 */
	private final JButton btnMakeSwitcher = new JButton("Make Switcher");
	/**
	 * button to make a ball
	 */
	private final JButton btnSwitch = new JButton("Switch!");
	/**
	 * button to make a ball
	 */
	private final JButton btnCombine = new JButton("Combine!");
	/**
	 * button to clear everything on canvas
	 */
	private final JButton btnClearAll = new JButton("Clear All");
	/**
	 * The button to pause/resume frame (timer)
	 */
	private final JButton btnPause = new JButton("Pause");
	/**
	 * The button that adds a new paint strategy to drop list
	 */
	private final JButton btnAddPaint = new JButton("Add Strategy");

	/**
	 * The top drop list, used to select what strategy to use in a new ball and
	 * to switch the switcher to.
	 */
	private JComboBox<TUpdateDropListItem> lstUpdate1 = new JComboBox<TUpdateDropListItem>();
	/**
	 * Bottom drop list, used for combining with the top list selection.
	 */
	private JComboBox<TUpdateDropListItem> lstUpdate2 = new JComboBox<TUpdateDropListItem>(); 
	/**
	 * The drop list used for adding paint strategy when making a ball
	 */
	private JComboBox<TPaintDropListItem> lstPaint = new JComboBox<TPaintDropListItem>(); 

	/**
	 * Constructor is supplied with an instance of the model adapter. 
	 * @param modelPaintAdpt A adapter makes model to paint
	 * @param modelUpdateAdpt A adapter makes model to update
	 * @param modelControlAdpt A adapter controls the elements of model
	 */
	public BallGUI(
			IModelPaintAdapter modelPaintAdpt, 
			IModelUpdateAdapter modelUpdateAdpt, 
			IModelControlAdapter<TUpdateDropListItem, TPaintDropListItem> modelControlAdpt) {
		_modelPaintAdpt = modelPaintAdpt;
		_modelUpdateAdpt = modelUpdateAdpt;  
		_modelControlAdpt = modelControlAdpt;
		initGUI();
	}

	/**
	 * Set up GUI components on canvas
	 */
	public void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		setContentPane(contentPane);

		pnlCenter.setToolTipText("Center Panel");
		pnlCenter.setBackground(Color.WHITE);

		pnlNorth.setToolTipText("North Panel");
		pnlNorth.setBackground(Color.ORANGE);
		pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlNorth.add(pnlUpdate);
		pnlNorth.add(pnlUpdateList);
		pnlNorth.add(pnlSwitch);
		pnlNorth.add(pnlClear);
		pnlNorth.add(pnlPaint);

		pnlUpdate.setToolTipText("Input field and button to add strategy to droplist. ");
		pnlUpdate.setLayout(new BorderLayout(0, 0));
		pnlUpdate.add(txfUpdate);
		pnlUpdate.add(btnAddUpdate, BorderLayout.SOUTH);	

		pnlUpdateList.setToolTipText("Contains two drop lists of strategies. ");
		pnlUpdateList.setLayout(new GridLayout(4, 1));
		pnlUpdateList.add(btnMakeBall);
		pnlUpdateList.add(lstUpdate1);
		pnlUpdateList.add(lstUpdate2);
		pnlUpdateList.add(btnCombine);

		pnlSwitch.setToolTipText("Contains two buttons. ");
		pnlSwitch.setLayout(new GridLayout(2, 1));
		pnlSwitch.add(btnMakeSwitcher);
		pnlSwitch.add(btnSwitch);
		
		pnlClear.setToolTipText("clear/stop/start");
		pnlClear.setLayout(new GridLayout(2, 1));
		pnlClear.add(btnClearAll);
		pnlClear.add(btnPause);

		pnlPaint.setBorder(new TitledBorder("Paint Strategy"));
		pnlPaint.setToolTipText("Paint Strategy");
		pnlPaint.setLayout(new GridLayout(3, 1));
		pnlPaint.add(txfPaint);
		pnlPaint.add(btnAddPaint);
		pnlPaint.add(lstPaint);

		txfUpdate.setToolTipText("Color/Curve/Breathing/Drunken/Wander/Straight/Hold/Collide/Kill/Overlap/Bounce/Flocking");
		txfUpdate.setText("Flocking");

		txfPaint.setToolTipText("Ball/Ellipse/Square/Fish1/Fish3/Clownfish/Mario");
		txfPaint.setText("Clownfish");

		lstUpdate1.setToolTipText("Select an update strategy");
		lstUpdate2.setToolTipText("Select an update strategy");
		lstPaint.setToolTipText("Select a paint strategy");

		btnAddUpdate.setToolTipText("Add update strategy to lists. ");
		btnAddUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TUpdateDropListItem o = _modelControlAdpt.addUpdateStrat(txfUpdate.getText());
				if (null == o) return; // just in case
				lstUpdate1.insertItemAt(o, 0);
				lstUpdate2.insertItemAt(o, 0);
				lstUpdate1.setSelectedItem(o);
				lstUpdate2.setSelectedItem(o);
			}
		});

		btnAddPaint.setToolTipText("Add paint strategy to list");
		btnAddPaint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TPaintDropListItem o = _modelControlAdpt.addPaintStrat(txfPaint.getText());
				if (null == o) return; // just in case
				lstPaint.insertItemAt(o, 0);
				lstPaint.setSelectedItem(o);
			}

		});

		btnMakeBall.setToolTipText("Make a new ball");
		btnMakeBall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_modelControlAdpt.makeBall(
						lstPaint.getItemAt(lstPaint.getSelectedIndex()), 
						lstUpdate1.getItemAt(lstUpdate1.getSelectedIndex()));
			}
		});

		btnCombine.setToolTipText("Combine the selected two strategies. ");
		btnCombine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TUpdateDropListItem strategy1 = lstUpdate1.getItemAt(lstUpdate1.getSelectedIndex());
				TUpdateDropListItem strategy2 = lstUpdate2.getItemAt(lstUpdate2.getSelectedIndex());
				TUpdateDropListItem combinedStrategy = _modelControlAdpt.combineUpdateStrats(strategy1, strategy2);
				lstUpdate1.insertItemAt(combinedStrategy, 0);
				lstUpdate2.insertItemAt(combinedStrategy, 0);
				lstUpdate1.setSelectedItem(combinedStrategy);
			}
		});

		btnMakeSwitcher.setToolTipText("Make a straight ball that can switch to current strategy. ");
		btnMakeSwitcher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_modelControlAdpt.makeSwitcherBall(lstPaint.getItemAt(lstPaint.getSelectedIndex()));
			}
		});

		btnSwitch.setToolTipText("Switch to current strategy. ");
		btnSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_modelControlAdpt.switchBalls(lstUpdate1.getItemAt(lstUpdate1.getSelectedIndex()));
			}
		});

		btnClearAll.setToolTipText("Clear all balls");
		btnClearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_modelControlAdpt.clearBalls();
			}
		});
		
		btnPause.setToolTipText("Pause/Resume the frame");
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_modelControlAdpt.isRunning()) {
					_modelControlAdpt.stop();
					btnPause.setText("Resume");
				} else {
					_modelControlAdpt.start();
					btnPause.setText("Pause");			
				}
			}
		});		
	}

	/**
	 * Start the GUI
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Repaint the center canvas
	 */
	public void paint() {
		pnlCenter.repaint();
	}
	
	/**
	 * Get the center canvas
	 * @return the center canvas
	 */
	public Component getCanvas() {
		return pnlCenter;
	}

	/**
	 * Ask adapter to make model update. 
	 */
	public void update() {
		_modelUpdateAdpt.update();
	}

}

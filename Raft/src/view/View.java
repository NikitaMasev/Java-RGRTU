package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ObjectInfo;
import model.Raft;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.KeyListener;
import model.listeners.Listener;
import model.listeners.Sender;
import model.listeners.frame.EventDataFrame;
import model.listeners.frame.ListenerFrame;
 /**
  * Class View is a part of pattern MVC.<br> It' allow to use graphical representation.
  * {@link Model}
  * {@link Controller}
  * @author Masev Nikita
  *
  */
public class View extends JFrame implements Listener, ListenerFrame {
	private static final long serialVersionUID = 1L;
	/** 
	 * It's necessary for call methods from class model 
	 */
	private Controller controller;
	/** 
	 * it's a main game field(a graphical representation),<br> in particular, means terra 
	 */
	private PaintorTexture gameFieldTerra = new PaintorTexture(
			BufferizerImage.getImage(ViewData.NAME_TEXTURE_GAMEFIELD));
	/** 
	 * Graphical representation of player (ball) 
	 */
	private PaintorTexture ball = new PaintorTexture(
			BufferizerImage.getImage(ViewData.NAME_TEXTURE_BALL));
	/** 
	 * Animated texture , that will be see when player loose 
	 */
	private AnimatorTexture negativeGameOver = new AnimatorTexture(
			ViewData.COUNT_NEGATIVE_GAME_OVER_FRAMES,
			ViewData.NAME_NEGATIVE_GAME_OVER_FRAMES, ViewData.FRAMES_EXTENSION,
			ViewData.DELAY_NEGATIVE_GAME_OVER_FRAMES);
	/** 
	 * Animated texture , that will be see when player win 
	 */
	private AnimatorTexture positiveGameOver = new AnimatorTexture(
			ViewData.COUNT_POSITIVE_GAME_OVER_FRAMES,
			ViewData.NAME_POSITIVE_GAME_OVER_FRAMES, ViewData.FRAMES_EXTENSION,
			ViewData.DELAY_POSITIVE_GAME_OVER_FRAMES);
	/** Relates controller in there with main controller, <br> figure out add listener.
	 * @see initializeFrame
	 */
	public View(Controller controller) {
		this.controller = controller;
		this.controller.addListener(this);
		this.controller.addListenerFrame(this);
		initializeFrame();
	}
	/**
	 * Initialize main windows of application
	 * @see View
	 */
	private void initializeFrame() {
		this.setLocation(
				ViewData.screen.width - ViewData.screen.width / 2
						- ViewData.SCREEN_LOCATION_Y,
				ViewData.screen.height - ViewData.screen.height / 2
						- ViewData.SCREEN_LOCATION_X);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ViewData.WINDOW_TITLE);
		this.setSize(ViewData.GAME_FRAME_WIDTH, ViewData.GAME_FRAME_HEIGHT);
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(exitItem);
		menuBar.add(gameMenu);
		this.setJMenuBar(menuBar);
		this.addKeyListener(new KeyListener(controller));
		this.add(ball);
		this.add(gameFieldTerra);
		this.setVisible(true);
		this.setResizable(false);
	}
	/**
	 * Initialize game over window, when player loose
	 * @see handleEvent
	 * @param GameOverPanel it's necessary because <br> of there are two likes frames,
	 * that differ one of multiple thing.
	 */
	public void initializeGameOverFrame(AnimatorTexture GameOverPanel) {
		JFrame gameOverFrame = new JFrame();
		gameOverFrame.setLocation(
				ViewData.screen.width - ViewData.screen.width / 2
						- ViewData.SCREEN_LOCATION_Y,
				ViewData.screen.height - ViewData.screen.height / 2
						- ViewData.SCREEN_LOCATION_X);
		JButton closeButton = new JButton();
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		closeButton.setText(ViewData.GAME_OVER_BUTTON_TEXT);
		gameOverFrame.add(GameOverPanel, BorderLayout.CENTER);
		gameOverFrame.add(closeButton, BorderLayout.SOUTH);
		gameOverFrame.setTitle(ViewData.GAME_OVER_FRAME_TITLE);
		gameOverFrame
				.setPreferredSize(new Dimension(ViewData.GAME_OVER_FRAME_WIDTH,
						ViewData.GAME_OVER_FRAME_HEIGHT));
		gameOverFrame.setVisible(true);
		gameOverFrame.pack();
	}
	/**
	 * It's method means, that this class is listener, in particular, <br>
	 * it's perform function of main game manager events.
	 */
	@Override
	public void handleEvent(EventData eventData) {
		Sender sender = eventData.getSender();
		Event event = eventData.getEvent();
		Object data = eventData.getData();
		if (sender == Sender.GAME_FIELD_TERRA && event == Event.INITIALIZE) {
			initializeGameFieldTerra((ObjectInfo) data);
		}
		if (sender == Sender.GAME_FIELD_RIVER && event == Event.DEATH) {
			initializeGameOverFrame(negativeGameOver);
		}
		if (sender == Sender.GAME_FIELD_TERRA && event == Event.WIN) {
			initializeGameOverFrame(positiveGameOver);
		}
		if (sender == Sender.BALL && event == Event.MOVE) {
			initializeBall((ObjectInfo) data);
		}
		if (sender == Sender.BALL && event == Event.INITIALIZE) {
			initializeBall((ObjectInfo) data);
		}
		if (sender == Sender.RAFT && event == Event.ADD_RAFT_PANEL) {
			initializeRaftPanel(data);
		}
	}
	/**
	 * It's method means, that this class is listener of frame, in particular, <br>
	 * it's perform function of change frame for events.
	 */
	@Override
	public void handleChangeFrame(EventDataFrame eventDataFrame) {
		Sender sender = eventDataFrame.getSender();
		Event event = eventDataFrame.getEvent();
		if (sender == Sender.GAME_FIELD_RIVER && event == Event.CHANGE_FRAME) {
			this.repaint();
		}
	}
	/**
	 * Initialize graphical representation of raft for events <br> from class Model
	 * @see handleEvent
	 * @param data is a object of raft classes
	 */
	public void initializeRaftPanel(Object data) {
		Raft raft = (Raft) data;
		RaftPanel raftPanel = new RaftPanel(raft, ViewData.NAME_TEXTURE_RAFT);
		raft.addRaftListener(raftPanel);
		this.gameFieldTerra.add(raftPanel);
	}
	/**
	 * Initialize the main game field in game for <br> events from class Model
	 * @see handleEvent
	 * @param data is info about gameFieldTerra object
	 */
	private void initializeGameFieldTerra(ObjectInfo data) {
		gameFieldTerra.setSize(data.getDimension());
	}
	/**
	 * Initialize player(Ball) in graphical representation
	 * @see handleEvent
	 * @param data is info about Ball object
	 */
	private void initializeBall(ObjectInfo data) {
		ball.setSize(data.getDimension());
		ball.setLocation(data.getLocation());
	}
}

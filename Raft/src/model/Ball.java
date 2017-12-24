package model;

import java.awt.Dimension;
import java.awt.Point;
import model.listeners.*;
/**
 * It's class that describe player in the game.
 * @author Masev Nikita
 *
 */
public class Ball implements Runnable {
	/**
	 * This is pixel's step of player.
	 */
	public static final int BALL_SHIFT = 15;
	/**
	 * It's information about Ball's (player) object.
	 */
	private ObjectInfo objectInfo;
	/**
	 * It's necessary that know dimension of the game field.
	 */
	private GameField gameFieldTerra;
	/**
	 * Movement X
	 */
	private int dx = BALL_SHIFT;
	/**
	 * Movement Y
	 */
	private int dy = BALL_SHIFT;
	/**
	 * Common list of main listeners,<br> than allow do logic for events.
	 */
	private ListenersList listeners;
	/**
	 * It's permit to launch object of class<br> at the separate thread.
	 */
	private Thread ballThread = null;
	/**
	 * It's need that thread knew in what way<br> it could move a player (Ball).
	 */
	private byte numOperation = -1;
	/**
	 * Install communication between {@link listeners},<br> {@link objectInfo}, {@link gameFieldTerra}
	 * with <br> the same object in {@link Model}
	 * @param objectInfo is information about player (Ball)
	 * @param gameField is object of the game field
	 * @param listeners is a common listener from {@link Model}
	 */
	public Ball(ObjectInfo objectInfo, GameField gameField,
			ListenersList listeners) {
		super();
		this.listeners = listeners;
		this.objectInfo = objectInfo;
		this.gameFieldTerra = gameField;
		this.setObjectInfo(objectInfo);

	}
	/**
	 * It's allow to set number of operation from {@link Model}
	 * @param numOperation
	 */
	public void setNumOperation(byte numOperation) {
		this.numOperation = numOperation;
	}
	/**
	 * This is admit to get information about Ball (player)
	 * @return object, that have information about this class
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}
	/**
	 * Launch object of Ball (player) in the single thread
	 * @see run
	 */
	public void startThreadBall() {
		this.ballThread = new Thread(this);
		this.ballThread.start();
	}
	/**
	 * Is forced stop of thread
	 */
	@SuppressWarnings("deprecation")
	public void stopThreadBall() {
		this.ballThread.stop();
	}
	/**
	 * Allow to get object of game field
	 * @return object of game field
	 */
	public GameField getGameField() {
		return gameFieldTerra;
	}
	/**
	 * Admit to set link on object of game field <br> from {@link Model}
	 * @param gameField is object of game filed
	 */
	public void setGameField(GameField gameField) {
		this.gameFieldTerra = gameField;
	}
	/**
	 * It's set information about player (Ball)<br> and generate event for display 
	 * player on frame.<br> Event will be process in {@link View}
	 * @param objectInfo is information about player (Ball)
	 */
	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData eventData = new EventData(Sender.BALL, Event.INITIALIZE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * Movement player to up and generation event for {@link View}
	 */
	public void goUp() {
		Point location = this.objectInfo.getLocation();
		location.y -= dy;
		EventData eventData = new EventData(Sender.BALL, Event.MOVE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * Movement player to down and generation event for {@link View}
	 */
	public void goDown() {
		Point location = this.objectInfo.getLocation();
		Dimension dimension = this.objectInfo.getDimension();
		Dimension gameFieldDimension = this.gameFieldTerra.getObjectInfo()
				.getDimension();
		location.y += dy;
		if (location.y >= gameFieldDimension.height + dimension.height) {
			location.y = gameFieldDimension.height - dimension.height;
		}
		EventData eventData = new EventData(Sender.BALL, Event.MOVE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * Movement player to left and generation event for {@link View}
	 */
	public void goLeft() {
		Point location = this.objectInfo.getLocation();
		Dimension dimension = this.objectInfo.getDimension();
		Dimension gameFieldDimension = this.gameFieldTerra.getObjectInfo()
				.getDimension();
		location.x -= dx;
		if (location.x <= 0) {
			location.x = gameFieldDimension.width - dimension.width;
		}
		EventData eventData = new EventData(Sender.BALL, Event.MOVE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * Movement player to right and generation event for {@link View}
	 */
	public void goRight() {
		Point location = this.objectInfo.getLocation();
		Dimension dimension = this.objectInfo.getDimension();
		Dimension gameFieldDimension = this.gameFieldTerra.getObjectInfo()
				.getDimension();
		location.x += dx;
		if (location.x >= gameFieldDimension.width + dimension.width) {
			location.x = dx;
		}
		EventData eventData = new EventData(Sender.BALL, Event.MOVE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * It's main method of this class, that unite other methods. <br>
	 * It allow to launch task of player (Ball) as a thread <br>
	 * Depending from {@link numOperation} thread decide <br> in what way move player
	 * @see startThreadBall
	 */
	@Override
	public void run() {
		switch (numOperation) {
		case 0: {
			goUp();
			break;
		}
		case 1: {
			goDown();
			break;
		}
		case 2: {
			goLeft();
			break;
		}
		case 3: {
			goRight();
			break;
		}
		default:
			break;
		}
	}

}

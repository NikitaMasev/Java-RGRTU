package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import audio.AudioPlayer;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.ListenersList;
import model.listeners.Sender;
import model.listeners.frame.EventDataFrame;
import model.listeners.frame.ListenerFrame;
import model.listeners.frame.ListenerFrameList;
/**
 * Class Model is a part of pattern MVC and it's provide program's logic work.
 * {@link View}
 * {@link Controller}
 * @author Masev Nikita
 * @version 1.3
 *
 */
public class Model {
	/** 
	 * Flag which don't allows death pass condition some many time
	 */
	private boolean alreadyOnRiver = false;
	/** 
	 * Flag which don't allows win pass condition many time
	 */
	private boolean alreadyWin = false;
	/** 
	 * It's value define death for all game and if it true, that breaks main game cycle 
	 */
	private boolean death = false;
	/** 
	 * Flag is define - ball(player) on raft or ball(player) out raft 
	 */
	private boolean onRaft = false;
	/** 
	 * It's need for game field value's 
	 */
	private GameField gameFieldTerra;
	/** 
	 * It's a player in game
	 */
	private Ball ball;
	/** 
	 * it's a object of raft in game 
	 */
	private Raft raft;
	/** 
	 * It's a background music 
	 */
	private AudioPlayer audioMainPlayer = new AudioPlayer(
			ModelData.NAME_MAIN_AUDIO);
	/** 
	 * It start when player loose 
	 */
	private AudioPlayer audioNegativeGameOverPlayer = new AudioPlayer(
			ModelData.NAME_NEGATIVE_GAME_OVER_AUDIO);
	/** 
	 * It start when player win
	 */
	private AudioPlayer audioPositiveGameOverPlayer = new AudioPlayer(
			ModelData.NAME_POSITIVE_GAME_OVER_AUDIO);
	/** 
	 * Serve for interaction between classes, that couldn't know each other. 
	 */
	private ListenersList listeners = new ListenersList();
	/** 
	 * List store a some amount of the thread's object raft 
	 */
	private List<Raft> raftList = new ArrayList<Raft>();
	/** 
	 * It's should for change frame in class {@link View} <br> 
	 * after sticking object Ball to object Raft
	 */
	private ListenerFrameList listenerFrame = new ListenerFrameList();
	/** 
	 * Object on which will be occure synchronization
	 * @see checkBallOnRaft
	 * @see checkBallOutRaft
	 * {@link Raft.removeRaft}
	 * {@link Raft.initBornRaft}
	 */
	private Object sync = new Object();
	/** 
	 * Constructor of class Model without initialization, <br>
	 * because of initialize is devided on some method's
	 */
	public Model() {

	}
	/**
	 * Initialize main characteristic that create object Ball (player)
	 */
	private void initialize() {
		Dimension gameFieldTerraDimension = new Dimension(
				ModelData.GAMEFIELD_WIDTH_TERRA,
				ModelData.GAMEFIELD_HEIGHT_TERRA);
		ObjectInfo gameFieldTerraInfo = new ObjectInfo(gameFieldTerraDimension,
				ModelData.GAMEFIELD_TERRA_COLOR, null);
		this.gameFieldTerra = new GameFieldTerra(gameFieldTerraInfo,
				this.listeners);

		Dimension ballDimension = new Dimension(ModelData.BALL_WIDTH,
				ModelData.BALL_HEIGHT);
		Point ballLocation = new Point(ModelData.BALL_LOCATION_X,
				ModelData.BALL_LOCATION_Y);
		ObjectInfo ballInfo = new ObjectInfo(ballDimension,
				ModelData.BALL_COLOR, ballLocation);
		this.ball = new Ball(ballInfo, gameFieldTerra, this.listeners);
	}
	/**
	 * Initialize characteristic (dimension,location, information of object raft)<br> for raft,
	 *  create raft as thread, start thread's raft and it premise in list. 
	 * @see start
	 * @param i need for multiple call this method's from cycle
	 */
	public void initializeRaft(int i) {
		Dimension raftDimension = new Dimension(ModelData.RAFT_WIDTH,
				ModelData.RAFT_HEIGHT);
		Point raftLocation = new Point(
				ModelData.RAFT_LOCATION_X + ModelData.raftPointX[i],
				ModelData.RAFT_LOCATION_Y
						- (i * ModelData.STEP_CREATING_RAFT_LOCATION_Y));
		ObjectInfo raftInfo = new ObjectInfo(raftDimension,
				ModelData.RAFT_COLOR, raftLocation);
		this.raft = new Raft(raftInfo, gameFieldTerra, this.listeners,
				ModelData.dataSleepThreads[i], raftList, sync);
		this.raftList.add(raft);
		Thread raftThread = new Thread(raft);
		raftThread.start();
		EventData eventData = new EventData(Sender.RAFT, Event.ADD_RAFT_PANEL,
				this.raft);
		listeners.notify(eventData);
	}
	/**
	 * It's define where locate player: player on raft or not
	 * @see checkBallOnRaft
	 * @param raft is a one of sets raft from list
	 * @return true mean's player on raft, false mean's - not
	 */
	public boolean isOnRaft(Raft raft) {
		Rectangle rectBall = new Rectangle(
				this.ball.getObjectInfo().getLocation().x,
				this.ball.getObjectInfo().getLocation().y,
				this.ball.getObjectInfo().getDimension().width,
				this.ball.getObjectInfo().getDimension().height);
		Rectangle rectRaft = new Rectangle(raft.getObjectInfo().getLocation().x,
				raft.getObjectInfo().getLocation().y,
				raft.getObjectInfo().getDimension().width,
				raft.getObjectInfo().getDimension().height);
		if (rectRaft.intersects(rectBall)) {
			return true;
		}
		return false;
	}
	/**
	 * It's define where locate player: player on river or not
	 * @see checkBallOutRaft
	 * @return if true - player on river, false -  not
	 */
	public boolean isOnRiver() {
		if (this.ball.getObjectInfo()
				.getLocation().y < ModelData.BOTTOM_LINE_RIVER
				&& this.ball.getObjectInfo()
						.getLocation().y > ModelData.TOP_LINE_RIVER) {
			return true;
		}
		return false;
	}
	/**
	 * Determines that the player won
	 * @see checkBallWin
	 * @return true means player win, false - not
	 */
	public boolean isWin() {
		if (this.ball.getObjectInfo()
				.getLocation().y < ModelData.TOP_LINE_RIVER) {
			return true;
		}
		return false;
	}
	/**
	 * This synchronized methods check that player locate on raft.<br>
	 * If player on raft it's mean that will be send change frame event's.<br>
	 * Synchronization performed on raft's methods.
	 * @see start
	 */
	public void checkBallOnRaft() {
		synchronized (sync) {
			for (Raft raft : raftList) {
				if (isOnRaft(raft)) {
					this.ball.getObjectInfo()
							.setLocation(new Point(
									raft.getObjectInfo().getLocation().x,
									raft.getObjectInfo().getLocation().y));
					EventData eventData = new EventData(Sender.BALL, Event.MOVE,
							this.ball.getObjectInfo());
					EventDataFrame eventDataFrame = new EventDataFrame(
							Sender.GAME_FIELD_RIVER, Event.CHANGE_FRAME);
					listeners.notify(eventData);
					listenerFrame.notify(eventDataFrame);
				}
			}
		}
	}
	/**
	 * This synchronized methods check that player locate out raft.<br>
	 * And if a true that it'l be generate death event's. <br>
	 * Synchronization performed on raft's methods.<br>
	 * @see start
	 */
	public void checkBallOutRaft() {
		synchronized (sync) {
			for (Raft rafttmp : raftList) {
				if (!isOnRaft(rafttmp) && isOnRiver()) {
					alreadyOnRiver = true;
				} else if (isOnRiver()) {
					onRaft = true;
				}
			}
		}
		if (alreadyOnRiver && !onRaft) {
			EventData eventData = new EventData(Sender.GAME_FIELD_RIVER,
					Event.DEATH, this.ball.getObjectInfo());
			listeners.notify(eventData);
			audioMainPlayer.stopAudioThread();
			audioNegativeGameOverPlayer.startAudioThread();
			death = true;
		}
		onRaft = false;
		alreadyOnRiver = false;
	}
	/**
	 * This method check that player is win
	 * @see start
	 */
	public void checkBallWin() {
		if (isWin() && !alreadyWin) {
			alreadyWin = true;
			EventData eventData = new EventData(Sender.GAME_FIELD_TERRA,
					Event.WIN, null);
			listeners.notify(eventData);
			audioMainPlayer.stopAudioThread();
			audioPositiveGameOverPlayer.startAudioThread();
		}
	}
	/**
	 * Define motion ball(player) to up
	 */
	public void moveBallUp() {
		this.ball.setNumOperation((byte) 0);
		this.ball.startThreadBall();
	}
	/**
	 * Define motion ball(player) to down
	 */
	public void moveBallDown() {
		this.ball.setNumOperation((byte) 1);
		this.ball.startThreadBall();
	}
	/**
	 * Define motion ball(player) to left
	 */
	public void moveBallLeft() {
		this.ball.setNumOperation((byte) 2);
		this.ball.startThreadBall();
	}
	/**
	 * Define motion ball(player) to right
	 */
	public void moveBallRight() {
		this.ball.setNumOperation((byte) 3);
		this.ball.startThreadBall();
	}
	/**
	 * It's a main method of game. It's occure call all <br> other methods that perform
	 * initialization.<br> Also there is main cycle in game,<br> that figure out call methods
	 * throughout game.
	 * @see initialize
	 * @see initializeRaft
	 * @see checkBallOnRaft
	 * @see checkBallOutRaft
	 * @see checkBallWin
	 */
	public void start() {
		initialize();
		audioMainPlayer.startAudioThread();

		for (int i = 0; i < ModelData.RAFT_COUNT; i++) {
			initializeRaft(i);
		}

		while (!death) {
			try {
				Thread.sleep(0, ModelData.STANDART_THREAD_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			checkBallOnRaft();
			checkBallOutRaft();
			checkBallWin();
		}
	}
	/**
	 * There is perform addition listener to all listeners.<br>
	 * @param listener it should be object of class,<br> that implements listener
	 */
	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}
	/**
	 * There is perform addition frame listener's to all frame listeners.
	 * @param listenerFrame
	 */
	public void addListenerFrame(ListenerFrame listenerFrame) {
		this.listenerFrame.add(listenerFrame);
	}
}

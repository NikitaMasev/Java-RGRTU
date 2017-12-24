package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.ListenersList;
import model.listeners.Sender;
/**
 * Is a class, that describe raft in the game.
 * All interaction between rafts and ball (player)<br> are located in the {@link Model} 
 * @author Masev Nikita
 *
 */
public class Raft implements Runnable {
	/**
	 * This is pixel's step of raft.
	 */
	public static final int RAFT_SHIFT = 2;
	/**
	 * It's information about Raft's object.
	 */
	private ObjectInfo objectInfo;
	/**
	 * It's necessary that know dimension of the game field.
	 */
	private GameField GameFieldTerra;
	/**
	 * Movement X, because rafts move just horizontally.
	 */
	private int dx = RAFT_SHIFT;
	/**
	 * Common list of main listeners,<br> than allow do logic for events.
	 */
	private ListenersList listeners;
	/**
	 * List of raft listeners, that send events to {@link RaftPanel}.<br>
	 * It's provide motion and remove of panel's raft.
	 */
	private ListenersList raftListeners = new ListenersList();
	/**
	 * It's permit to launch object of class at the separate thread.
	 */
	private Thread raftThread = null;
	/** 
	 * Object on which will be occure synchronization.
	 * {@link Raft.initBornRaft} <br>
	 * {@link removeRaft} <br>
	 * {@link Model.checkBallOnRaft} <br>
	 * {@link Model.checkBallOutRaft} <br>
	 */
	private Object sync;
	/**
	 * Is a thread sleep, what means delay between every task of Raft
	 */
	private int threadSleep = 50;
	/**
	 * Define - launched the thread of raft or not
	 */
	private boolean runRaft = true;
	/** 
	 * List store a some amount of the thread's object raft 
	 */
	private List<Raft> raftList;
	/**
	 * It's object have link to this object
	 */
	private Raft raft;
	/**
	 * Install communication between {@link listeners}, {@link objectInfo}, 
	 * {@link gameFieldTerra}, {@link raftList} with the same object in {@link Model},
	 * also set to object of synchronization.
	 * @param objectInfo is a information about Raft
	 * @param GameFieldTerra is object of game field
	 * @param listeners is a common listener from {@link Model}
	 * @param threadSleep delay between tasks of the thread
	 * @param raftList store thread's object raft
	 * @param sync object of the synchronization
	 */
	public Raft(ObjectInfo objectInfo, GameField GameFieldTerra,
			ListenersList listeners, int threadSleep, List<Raft> raftList,
			Object sync) {
		super();
		this.objectInfo = objectInfo;
		this.GameFieldTerra = GameFieldTerra;
		this.listeners = listeners;
		this.threadSleep = threadSleep;
		this.raftList = raftList;
		this.sync = sync;
		setObjectInfo(objectInfo);
		this.raft = this;
	}
	/**
	 * Allot to get in what state is thread of raft
	 * @return true means that thread is launched
	 */
	public boolean isRunRaft() {
		return runRaft;
	}
	/**
	 * Admit to get number of thread's delay
	 * @return number of milliseconds
	 */
	public int getThreadSleep() {
		return threadSleep;
	}
	/**
	 * Set delay for raft's thread
	 * @param threadSleep number of milliseconds
	 */
	public void setThreadSleep(int threadSleep) {
		this.threadSleep = threadSleep;
	}
	/**
	 * Set to state of raft's thread
	 * @param runRaft true means threads will prepare to launch,<br> false - not
	 */
	public void setRunRaftThread(boolean runRaft) {
		this.runRaft = runRaft;
	}
	/**
	 * There is perform addition listener to raft listeners.
	 * @param e listener it should be object of class, that implements listener
	 * @return true if addition proceeded successfully, false - not
	 */
	public boolean addRaftListener(Listener e) {
		return raftListeners.add(e);
	}
	/**
	 * This is admit to get information about Raft 
	 * @return object, that have information about this class
	 */
	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}
	/**
	 * Launch object of Raft in the single thread. <br>
	 * Synchronization need for threads,<br> that wait each other and
	 * will be launched in series
	 * @see run
	 */
	public synchronized void startThreadRaft() {
		this.raftThread = new Thread(this);
		raftThread.start();
	}
	/**
	 * It's set information about Raft and generate <br> event for display 
	 * raft on frame.<br> Event will be process in {@link View}
	 * @param objectInfo is information about Raft
	 */
	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData eventData = new EventData(Sender.RAFT, Event.INITIALIZE,
				this.objectInfo);
		listeners.notify(eventData);
	}
	/**
	 * Allow to get object of game field
	 * @return object of game field
	 */
	public GameField getGameField() {
		return GameFieldTerra;
	}
	/**
	 * Admit to set link on object of game field from {@link Model}
	 * @param gameField is object of game filed
	 */
	public void setGameField(GameField gameField) {
		this.GameFieldTerra = gameField;
	}
	/**
	 * It's admit to remove raft's threads from raft list.<br>
	 * Also it generate event for {@link RaftPanel} to remove 
	 * graphical representation of the raft.<br>
	 * This method is synchronized with <br> {@link Model.checkBallOutRaft},<br>
	 * {@link Model.checkBallOnRaft}
	 */
	private void removeRaft() {
		synchronized (sync) {
			runRaft = false;
			raftList.remove(raft);
			EventData eventData = new EventData(Sender.RAFT, Event.DEATH_RAFT,
					this);
			raftListeners.notify(eventData);
		}
	}
	/**
	 * It's admit to initialize raft's threads, add it to raft list and <br>
	 * generate event for {@link RaftPanel} to add panel's raft <br> on frame.
	 * This method is synchronized with <br> {@link Model.checkBallOutRaft},<br>
	 * {@link Model.checkBallOnRaft}
	 */
	private void initBornRaft() {
		synchronized (sync) {
			raft = new Raft(this.objectInfo, this.GameFieldTerra,
					this.listeners, this.threadSleep, this.raftList, this.sync);
			raftList.add(raft);
			runRaft = true;
			startThreadRaft();
			EventData eventData = new EventData(Sender.RAFT,
					Event.ADD_RAFT_PANEL, this);
			listeners.notify(eventData);
		}
	}
	/**
	 * It's provide movement for all raft's thread from list.<br>
	 * There is carried control of raft's coordinate for X <br>
	 * and from here depending from X do calling <br>{@link removeRaft} <br>
	 * {@link initBornRaft} <br>
	 * Also this method generate event for {@link RaftPanel}<br> to move raft
	 */
	private void move() {
		Point location = this.objectInfo.getLocation();
		Dimension dimension = this.objectInfo.getDimension();
		Dimension gameFieldRiverDimension = this.GameFieldTerra.getObjectInfo()
				.getDimension();
		location.x += dx;
		if (location.x>= gameFieldRiverDimension.width) {
			location.x = dx;
			removeRaft();
			initBornRaft();
		}
		EventData eventData = new EventData(Sender.RAFT, Event.MOVE,
				this.objectInfo);
		raftListeners.notify(eventData);
	}
	/**
	 * It's main method of this class, that unite other methods.<br>
	 * It allow to launch task of Raft as a thread.<br>
	 * There is calling method {@link move}.
	 * @see startThreadRaft
	 */
	@Override
	public synchronized void run() {
		while (runRaft) {
			move();
			try {
				Thread.sleep(threadSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	/**
	 * Is forced stop of thread
	 */
	@SuppressWarnings("deprecation")
	public void stopThreadRaft() {
		raftThread.stop();
	}
	/**
	 * There is perform add listener to all listeners.
	 * @param listener it should be object of class,<br> that implements listener
	 */
	public boolean addListener(Listener e) {
		return listeners.add(e);
	}

}

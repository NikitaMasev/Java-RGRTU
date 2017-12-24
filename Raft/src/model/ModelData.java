package model;

import java.awt.Color;

public class ModelData {
	/**
	 * Multiple of start points for raft
	 */
	public static int[] raftPointX = { 100, 20, 200, 70, 700, 550, 320, 450, 50,
			90, 630, 400 };
	/**
	 * Multiple of thread sleep for raft threads
	 */
	public static int[] dataSleepThreads = { 50, 46, 27, 51, 25, 85, 32, 75, 49,
			52, 28, 8 };
	/**
	 * Width of game field
	 */
	public static final int GAMEFIELD_WIDTH_TERRA = 800;
	/**
	 * Height of game field
	 */
	public static final int GAMEFIELD_HEIGHT_TERRA = 463;
	/**
	 * Default color for game field
	 */
	public static final Color GAMEFIELD_TERRA_COLOR = Color.DARK_GRAY;
	/**
	 * Width of raft
	 */
	public static final int RAFT_WIDTH = 40;
	/**
	 * Height of raft
	 */
	public static final int RAFT_HEIGHT = 25;
	/**
	 * Default color for raft
	 */
	public static final Color RAFT_COLOR = Color.LIGHT_GRAY;
	/**
	 * Default start position of raft for X
	 */
	public static final int RAFT_LOCATION_X = 2;
	/**
	 * Default start position of raft for Y
	 */
	public static final int RAFT_LOCATION_Y = 380;
	/**
	 * Width of ball (player)
	 */
	public static final int BALL_WIDTH = 15;
	/**
	 * Height of ball (player)
	 */
	public static final int BALL_HEIGHT = 15;
	/**
	 * Default color for ball (player)
	 */
	public static final Color BALL_COLOR = Color.RED;
	/**
	 * Start position of ball (player) for X
	 */
	public static final int BALL_LOCATION_X = 400;
	/**
	 * Start position of ball (player) for X
	 */
	public static final int BALL_LOCATION_Y = 420;
	/**
	 * standard delay for main cycle in game.<br> It's necessary for
	 * correctly keystroke processing.
	 */
	public static final int STANDART_THREAD_DELAY = 1;
	/**
	 * It's need for correctly initialize raft threads on frame
	 */
	public static final int STEP_CREATING_RAFT_LOCATION_Y = 29;
	/**
	 * This is raft count
	 */
	public static final int RAFT_COUNT = 12;
	/**
	 * Define a bottom line river depending<br> from raft location for Y and
	 * raft height
	 */
	public static final int BOTTOM_LINE_RIVER = RAFT_LOCATION_Y
			+ RAFT_HEIGHT / 2;
	/**
	 * Define a top line river depending from<br> dimension of raft and raft count.
	 */
	public static final int TOP_LINE_RIVER = RAFT_LOCATION_Y
			- STEP_CREATING_RAFT_LOCATION_Y * RAFT_COUNT + RAFT_HEIGHT; 
	/**
	 * Path to file for background music
	 */
	public static String NAME_MAIN_AUDIO = "res\\Moby - LA1.wav";
	/**
	 * Path to file for negative music (when player loose)
	 */
	public static String NAME_NEGATIVE_GAME_OVER_AUDIO = "res\\Metal Gear Solid - Game over sound.wav";
	/**
	 * Path to file for positive music (when player win)
	 */
	public static String NAME_POSITIVE_GAME_OVER_AUDIO = "res\\audioWin.wav";
}

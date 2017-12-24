package view;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ViewData {
	/**
	 * Name of window main frame 
	 */
	public static final String WINDOW_TITLE = "Raft";
	/**
	 * Name of game over frame
	 */
	public static final String GAME_OVER_FRAME_TITLE = "GAME OVER";
	/**
	 * Name of button on game over frame
	 */
	public static final String GAME_OVER_BUTTON_TEXT = "Close";
	/**
	 * Width of game over frame
	 */
	public static int GAME_OVER_FRAME_WIDTH = 450;
	/**
	 * Height of game over frame
	 */
	public static int GAME_OVER_FRAME_HEIGHT = 350;
	/**
	 * Width of main frame
	 */
	public static int GAME_FRAME_WIDTH = 800;
	/**
	 * Height of main frame
	 */
	public static int GAME_FRAME_HEIGHT = 535;
	/**
	 * Path to file of ball's (player) texture
	 */
	public static String NAME_TEXTURE_BALL = "res\\ball.png";
	/**
	 * Path to file of raft's texture
	 */
	public static String NAME_TEXTURE_RAFT = "res\\woodLow.jpg";
	/**
	 * Path to file of game field's texture
	 */
	public static String NAME_TEXTURE_GAMEFIELD = "res\\gamefield.png";
	/**
	 * Frames extension for animation
	 */
	public static String FRAMES_EXTENSION = ".png";
	/**
	 * Count frames for negative game over
	 */
	public static int COUNT_NEGATIVE_GAME_OVER_FRAMES = 5;
	/**
	 * Path to frame's files for animation<br> negative game over
	 */
	public static String NAME_NEGATIVE_GAME_OVER_FRAMES = "res\\gameover_n";
	/**
	 * Delay between change frames for animation<br> negative game over
	 */
	public static int DELAY_NEGATIVE_GAME_OVER_FRAMES = 100;
	/**
	 * Count frames for positive game over
	 */
	public static int COUNT_POSITIVE_GAME_OVER_FRAMES = 23;
	/**
	 * Path to frame's files for animation<br> positive game over
	 */
	public static String NAME_POSITIVE_GAME_OVER_FRAMES = "res\\gameover_p";
	/**
	 * Delay between change frames for animation<br> positive game over
	 */
	public static int DELAY_POSITIVE_GAME_OVER_FRAMES = 65;
	/**
	 * Default screen position Y for correctly display 
	 */
	public static int SCREEN_LOCATION_Y = 500;
	/**
	 * Default screen position X for correctly display 
	 */
	public static int SCREEN_LOCATION_X = 300;
	/**
	 * Store a dimension of screen
	 */
	public static Dimension screen = Toolkit.getDefaultToolkit()
			.getScreenSize();
}

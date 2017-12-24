package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import model.listeners.Event;
import model.listeners.Sender;
import model.listeners.frame.EventDataFrame;
import model.listeners.frame.ListenerFrame;
import model.listeners.frame.ListenerFrameList;
/**
 * A class that to use animated texture for<br> graphical representation of object
 * @author Masev Nikita
 *
 */
public class AnimatorTexture extends JPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Total number of frames
	 */
	private int countFrames;
	/**
	 * Name of first frame
	 */
	private String nameFrames;
	/**
	 * Extension of file's frame
	 */
	private String framesExtension;
	/**
	 * It's necessary for frame's management, in particular,<br> delay between frame rates
	 */
	private Timer timer;
	/**
	 * Object of image, that will be use for premise in <br>
	 * {@link bufferImage} 
	 */
	private Image image;
	/**
	 * It's store some multiple of frames for animation
	 */
	private Image[] bufferImage;
	/**
	 * It's counter of frames, need for frame rates
	 */
	private int counterFrame = 0;
	/**
	 * Listener's list of frames, necessary for {@link View} <br>
	 * when happens motion of objects on frame(form). 
	 */
	private ListenerFrameList listenerFrame = new ListenerFrameList();
	/**
	 * It's perform function of installing new means.<br>
	 * Also proceed initialization of {@link timer}<br> and
	 * calling buffering method. 
	 * @param countFrames number of frames
	 * @param nameFrame name(path) of first frame
	 * @param frameExtension name of frame extension
	 * @param delay delay between frame rates(millisecond)
	 */
	public AnimatorTexture(int countFrames, String nameFrame,
			String frameExtension, int delay) {
		this.countFrames = countFrames;
		this.nameFrames = nameFrame;
		this.framesExtension = frameExtension;
		this.bufferImage = new Image[this.countFrames];
		timer = new Timer();
		timer.schedule(animate, 0, delay);
		initializateBufferFrame();
	}
	/**
	 * There is perform addition listener to frame listeners.
	 * @param e listener it should be object of class, that implements frame listener
	 * @return true if addition proceeded successfully, false - not
	 */
	public void addListenerFrame(ListenerFrame listenerFrame) {
		this.listenerFrame.add(listenerFrame);
	}
	/**
	 * It's figure out buffering frames for animation
	 */
	public void initializateBufferFrame() {
		for (int i = 0; i < countFrames; i++) {
			bufferImage[i] = BufferizerImage
					.getImage(nameFrames + i + framesExtension);
		}
	}
	/**
	 * This method admit to change frames through <br>
	 * determined times. <br>
	 * Also animation will be operate in the separate thread.
	 */
	TimerTask animate = new TimerTask() {

		@Override
		public void run() {
			image = bufferImage[counterFrame];
			counterFrame++;
			if (counterFrame == countFrames) {
				counterFrame = 0;
			}
			repaint();
			EventDataFrame eventDataFrame = new EventDataFrame(
					Sender.GAME_FIELD_RIVER, Event.CHANGE_FRAME);
			listenerFrame.notify(eventDataFrame);
		}
	};
	/**
	 * It's allow to use image for standard panels
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

}

package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Class AudioPlayer allows play audio in format .wav
 * @author Masev Nikita
 *
 */
public class AudioPlayer implements Runnable {
	/**
	 * Path to file music including name of file and extension
	 */
	private String source = "";
	/**
	 * It's need for play audio in separate thread
	 */
	private Thread audioThread = null;
	/**
	 * It's necessary for manage audio
	 */
	private Clip clip;
	/**
	 * Install path to audio file
	 * @param source is a path to audio file
	 */
	public AudioPlayer(String source) {
		super();
		this.source = source;
	}
	/**
	 * Launch audio thread
	 * @see audioRunner
	 */
	public void startAudioThread() {
		this.audioThread = new Thread(this);
		audioThread.start();
	}
	/**
	 * It's allow to stop audio thread compulsory 
	 */
	@SuppressWarnings("deprecation")
	public void stopAudioThread() {
		clip.stop();
		clip.close();
		this.audioThread.stop();
	}
	/**
	 * Main method of class AudioPlayer,<br> that start play audio to end. <br>
	 *(if will be not calling of stopAudioThread)
	 *@see stopAudioThread
	 */
	public void audioRunner() {
		try {
			File soundFile = new File(source);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setFramePosition(0);
			clip.start();
			Thread.sleep(clip.getMicrosecondLength() / 1000);
			clip.stop();
			clip.close();
		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException exc) {
			exc.printStackTrace();
		} catch (InterruptedException exc) {
		}

	}
	/**
	 * Start main method of playing audio in separate thread.
	 * @see audioRunner
	 */
	@Override
	public void run() {
		audioRunner();
	}

}

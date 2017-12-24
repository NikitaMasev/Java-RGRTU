package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import model.ObjectInfo;
import model.Raft;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Sender;

public class PaintorTexture extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;

	public PaintorTexture(Image image) {
		super();
		this.setBackground(new Color(0, 0, 0, 0));
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

}

package model;

import java.awt.*;

public class ObjectInfo {
	private Dimension dimension;
	private Color color;
	private Point location;

	public ObjectInfo(Dimension dimension, Color color, Point location) {
		super();
		this.dimension = dimension;
		this.color = color;
		this.location = location;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public synchronized Point getLocation() {
		return location;
	}

	public synchronized void setLocation(Point location) {
		this.location = location;
	}

}

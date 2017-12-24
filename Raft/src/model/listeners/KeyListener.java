package model.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.Controller;

public class KeyListener extends KeyAdapter {
	private Controller controller;

	public KeyListener(Controller controller) {
		super();
		this.controller = controller;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: {
			controller.moveObjectUp();
			break;
		}
		case KeyEvent.VK_DOWN: {
			controller.moveObjectDown();
			break;
		}
		case KeyEvent.VK_LEFT: {
			controller.moveObjectLeft();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			controller.moveObjectRight();
			break;
		}
		}
	}

}

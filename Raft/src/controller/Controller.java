package controller;

import model.Model;
import model.listeners.Listener;
import model.listeners.frame.ListenerFrame;
/**
 * Class Controller is a part of pattern MVC.<br> It's provide communication between 
 * {@link Model} and {@link View}
 * @author Masev Nikita
 *
 */
public class Controller {
	/**
	 * It's necessary for calling methods from Model
	 */
	private Model model;
	/**
	 * Related model in this and main Model
	 * @param model is object of class Model
	 */
	public Controller(Model model) {
		super();
		this.model = model;
	}
	/**
	 * It's perform add listener to all listeners
	 * @param listener object of class, that implements listener
	 */
	public void addListener(Listener listener) {
		model.addListener(listener);
	}
	/**
	 * It's perform add frame listener to all frame listeners
	 * @param listenerFrame object of class, that implements listener frame
	 */
	public void addListenerFrame(ListenerFrame listenerFrame) {
		model.addListenerFrame(listenerFrame);
	}
	/**
	 * Provides motion player to up
	 */
	public void moveObjectUp() {
		model.moveBallUp();
	}
	/**
	 * Provides motion player to down
	 */
	public void moveObjectDown() {
		model.moveBallDown();
	}
	/**
	 * Provides motion player to left
	 */
	public void moveObjectLeft() {
		model.moveBallLeft();
	}
	/**
	 * Provides motion player to right
	 */
	public void moveObjectRight() {
		model.moveBallRight();
	}
}

package model.listeners.frame;

import model.listeners.Event;
import model.listeners.Sender;

public class EventDataFrame {
	private Sender sender;
	private Event event;

	public EventDataFrame(Sender sender, Event event) {
		super();
		this.sender = sender;
		this.event = event;
	}

	public Sender getSender() {
		return sender;
	}

	public Event getEvent() {
		return event;
	}
}

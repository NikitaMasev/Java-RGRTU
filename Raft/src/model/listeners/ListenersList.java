package model.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListenersList {

	private List<Listener> listeners = new ArrayList<Listener>();

	public boolean add(Listener e) {
		return listeners.add(e);
	}

	public boolean remove(Object o) {
		return listeners.remove(o);
	}

	public void notify(EventData eventData) {

		ListIterator<Listener> iterator = listeners.listIterator();

		while (iterator.hasNext()) {
			Listener listener = iterator.next();
			listener.handleEvent(eventData);
		}
	}
}

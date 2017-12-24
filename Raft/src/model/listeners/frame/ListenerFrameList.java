package model.listeners.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListenerFrameList {
	private List<ListenerFrame> listenersFrame = new ArrayList<ListenerFrame>();

	public boolean add(ListenerFrame e) {
		return listenersFrame.add(e);
	}

	public boolean remove(Object o) {
		return listenersFrame.remove(o);
	}

	public void notify(EventDataFrame eventDataFrame) {

		ListIterator<ListenerFrame> iterator = listenersFrame.listIterator();

		while (iterator.hasNext()) {
			ListenerFrame listenersFrame = iterator.next();
			listenersFrame.handleChangeFrame(eventDataFrame);
		}
	}
}

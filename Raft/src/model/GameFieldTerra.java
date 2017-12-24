package model;

import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.ListenersList;
import model.listeners.Sender;

public class GameFieldTerra extends GameField {

	public GameFieldTerra(ObjectInfo objectInfo, ListenersList listeners) {
		super(objectInfo, listeners);
	}

	@Override
	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
		EventData eventData = new EventData(Sender.GAME_FIELD_TERRA,
				Event.INITIALIZE, this.objectInfo);
		listeners.notify(eventData);
	}
}

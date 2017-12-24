package model;

import model.listeners.ListenersList;

public abstract class GameField {
	protected ObjectInfo objectInfo;
	protected ListenersList listeners;

	public GameField(ObjectInfo objectInfo, ListenersList listeners) {
		this.listeners = listeners;
		this.setObjectInfo(objectInfo);
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	abstract void setObjectInfo(ObjectInfo objectInfo);

}

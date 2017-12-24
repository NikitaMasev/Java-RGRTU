package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import model.ObjectInfo;
import model.Raft;
import model.listeners.Event;
import model.listeners.EventData;
import model.listeners.Listener;
import model.listeners.Sender;
	/**
	 * It's extend class {@link PaintorTexture}, that inherited from standard {@link JPanel}.
	 * <br>This class is listener of raft and serves for correctly displays<br> of 
	 * raft's graphical representation when model generate events<br> on move or death
	 * @author Masev Nikita
	 *
	 */
public class RaftPanel extends PaintorTexture implements Listener {
	private static final long serialVersionUID = 1L;
	/**
	 * Initialize texture for raft panel,<br> and install characteristic for it.
	 * @param raft object of raft class from class View
	 * @param source path to texture's file
	 */
	public RaftPanel(Raft raft, String source) {
		super(BufferizerImage.getImage(source));
		ObjectInfo raftInfo = raft.getObjectInfo();
		Dimension raftDimension = raftInfo.getDimension();
		Color raftColor = raftInfo.getColor();
		Point location = raftInfo.getLocation();
		this.setSize(raftDimension);
		this.setBackground(raftColor);
		this.setLocation(location);
	}
	/**
	 * It's method manage events than generate in class {@link Model}
	 */
	@Override
	public void handleEvent(EventData eventData) {
		Sender sender = eventData.getSender();
		Event event = eventData.getEvent();
		Object data = eventData.getData();
		if (sender == Sender.RAFT && event == Event.MOVE) {
			moveRaft((ObjectInfo) data);
		}
		if (sender == Sender.RAFT && event == Event.DEATH_RAFT) {
			this.remove(this);
		}
	}
	/**
	 * Method perform motion of raft panel<br> in graphical representation
	 * @see handleEvent
	 * @param data is info about raft object
	 */
	private void moveRaft(ObjectInfo data) {
		ObjectInfo raftInfo = (ObjectInfo) data;
		Point location = raftInfo.getLocation();
		this.setLocation(location);
		this.setSize(data.getDimension());
	}

}
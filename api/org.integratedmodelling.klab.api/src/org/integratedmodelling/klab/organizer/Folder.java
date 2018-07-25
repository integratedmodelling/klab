package org.integratedmodelling.klab.organizer;

import java.util.ArrayList;
import java.util.List;

public class Folder extends TypedContainer {

	private static final long serialVersionUID = -3353176948068564352L;
	
	private List<Item> items = new ArrayList<>();
	private Organizer organizer;
	private boolean hidden;
	private boolean closed;
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Organizer getOrganizer() {
		return this.organizer;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	
}

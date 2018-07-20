package org.integratedmodelling.klab.organizer;

import java.util.ArrayList;
import java.util.List;

public class Folder extends TypedContainer {

	private static final long serialVersionUID = -3353176948068564352L;
	
	private List<Item> items = new ArrayList<>();

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	
}

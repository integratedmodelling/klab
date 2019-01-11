package org.integratedmodelling.klab.organizer;

import java.util.ArrayList;
import java.util.List;

public class Organizer extends TypedContainer {

	private static final long serialVersionUID = 5998940280636370538L;

	public Organizer() {}
	
	public Organizer(String name) {
		this.setName(name);
	}
	
	private List<Folder> folders = new ArrayList<>();

	public List<Folder> getFolders() {
		return folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public Item findItem(String id) {
		return null;
	}
	
}

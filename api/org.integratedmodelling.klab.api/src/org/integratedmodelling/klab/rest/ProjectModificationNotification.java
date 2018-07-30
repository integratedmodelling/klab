package org.integratedmodelling.klab.rest;

import java.io.File;

public class ProjectModificationNotification {

	public enum Type {
		CHANGE, ADDITION, DELETION
	}

	private Type type;
	private File file;

	public ProjectModificationNotification() {
	}

	public ProjectModificationNotification(Type type, File file) {
		this.type = type;
		this.file = file;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}

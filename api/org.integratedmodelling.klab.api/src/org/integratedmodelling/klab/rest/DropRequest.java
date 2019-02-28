package org.integratedmodelling.klab.rest;

public class DropRequest {

	private String dropId;
	// name of dropped object, e.g. file name
	private String dropContent;
	private long dropSize;

	public String getDropId() {
		return dropId;
	}

	public void setDropId(String dropId) {
		this.dropId = dropId;
	}

	public String getDropContent() {
		return dropContent;
	}

	public void setDropContent(String dropContent) {
		this.dropContent = dropContent;
	}

	public long getDropSize() {
		return dropSize;
	}

	public void setDropSize(long dropSize) {
		this.dropSize = dropSize;
	}

}

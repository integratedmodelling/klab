package org.integratedmodelling.klab.rest;

import java.util.List;

import org.integratedmodelling.klab.utils.Triple;

public class ObservationChange {

	private String id;
	private String contextId;
	private long timestamp;

	// each new attribute gets Semantics, Label and Description
	private List<Triple<String, String, String>> newAttributes;
	private ScaleReference newScale;
	private String newName;
	private String newSemantics;
	private int newSize;
	private boolean newMainStatus;
	private boolean newValues;
	private boolean terminated;

	public List<Triple<String, String, String>> getNewAttributes() {
		return newAttributes;
	}

	public void setNewAttributes(List<Triple<String, String, String>> newAttributes) {
		this.newAttributes = newAttributes;
	}

	public ScaleReference getNewScale() {
		return newScale;
	}

	public void setNewScale(ScaleReference newScale) {
		this.newScale = newScale;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewSemantics() {
		return newSemantics;
	}

	public void setNewSemantics(String newSemantics) {
		this.newSemantics = newSemantics;
	}

	public int getNewSize() {
		return newSize;
	}

	public void setNewSize(int newSize) {
		this.newSize = newSize;
	}

	public boolean isNewValues() {
		return newValues;
	}

	public void setNewValues(boolean newValues) {
		this.newValues = newValues;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isNewMainStatus() {
		return newMainStatus;
	}

	public void setNewMainStatus(boolean newMainStatus) {
		this.newMainStatus = newMainStatus;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


}

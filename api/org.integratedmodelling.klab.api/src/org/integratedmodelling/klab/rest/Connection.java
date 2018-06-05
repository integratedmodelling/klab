package org.integratedmodelling.klab.rest;

/**
 * Simple connection between a source and a target, identified by string IDs.
 * 
 * @author ferdinando.villa
 *
 */
public class Connection {
	
	private String sourceId;
	private String targetId;
	
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	

}

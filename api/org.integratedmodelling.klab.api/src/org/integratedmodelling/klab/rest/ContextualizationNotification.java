package org.integratedmodelling.klab.rest;

/**
 * Formerly known as DataflowReference, simply notifies of the presence or the
 * change of a target document related to a context, now articulated as either
 * dataflow or provenance.
 * 
 * @author Ferd
 *
 */
public class ContextualizationNotification {

	public enum Target {
		DATAFLOW, PROVENANCE
	}

	private String contextId;
	private Target target;

	public ContextualizationNotification() {
	}

	public ContextualizationNotification(String contextId, Target target) {
		this.setContextId(contextId);
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

}

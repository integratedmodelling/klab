package org.integratedmodelling.klab.rest;

/**
 * Sent by front-end whenever the user wants to (re)contextualize to either a
 * URN specifying one or more contexts, a query (both may require users to
 * choose one), or a specified observation ID. The last use case normally
 * happens with a parentContext set, when users want to select a sub-context of
 * the current.
 * 
 * @author Ferd
 *
 */
public class ContextualizationRequest {

	private String contextUrn;
	private String contextId;
	private String parentContext;
	private String contextQuery;

	public String getContextUrn() {
		return contextUrn;
	}

	public void setContextUrn(String contextUrn) {
		this.contextUrn = contextUrn;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public String getParentContext() {
		return parentContext;
	}

	public void setParentContext(String parentContext) {
		this.parentContext = parentContext;
	}

	public String getContextQuery() {
		return contextQuery;
	}

	public void setContextQuery(String contextQuery) {
		this.contextQuery = contextQuery;
	}

	@Override
	public String toString() {
		return "ContextualizationRequest [contextUrn=" + contextUrn + ", contextId=" + contextId + ", parentContext="
				+ parentContext + ", contextQuery=" + contextQuery + "]";
	}
	
	

}

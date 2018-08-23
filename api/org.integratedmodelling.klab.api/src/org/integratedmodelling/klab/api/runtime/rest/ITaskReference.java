package org.integratedmodelling.klab.api.runtime.rest;

public interface ITaskReference {

	// TODO provenance info - agent, cause etc
	String getId();

	String getParentId();

	String getUrn();

	String getDescription();

	String getError();
	
	/**
	 * Tasks that are computing a root context have this as null. All other
	 * tasks have the context observation ID.
	 * 
	 * @return
	 */
	String getContextId();

}
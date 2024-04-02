package org.integratedmodelling.klab.api.runtime.rest;

public interface ITaskReference {

	/**
	 * Used at runtime but not included in the task description itself.
	 * 
	 * @author Ferd
	 *
	 */
	enum Status {
		Started,
		Finished,
		Aborted,
		Interrupted
	}
	
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
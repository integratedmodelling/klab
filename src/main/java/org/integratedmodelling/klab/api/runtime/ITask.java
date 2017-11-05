package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;

public interface ITask extends ITaskIdentity {

	/**
	 * Block until the task if finished, then return the context this is part of.
	 * 
	 * @return
	 */
    IContext finish();
    
}

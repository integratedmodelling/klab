package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;

public interface IEngine extends IEngineIdentity, IServer {

	/**
	 * 
	 * @return
	 */
	ICapabilities getCapabilities();
   
	/**
	 * 
	 * @param user
	 * @return
	 */
    ISession createSession(IEngineUserIdentity user);

    /**
     * Return the user that runs the engine. Other users may be authenticated by the engine
     * in any way it wishes, with the same privileges or less.
     * 
     * @return
     */
	IEngineUserIdentity getUser();
    
}

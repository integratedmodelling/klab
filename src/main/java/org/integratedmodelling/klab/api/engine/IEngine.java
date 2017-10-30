package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;

public interface IEngine extends IEngineIdentity, IServer {

    ICapabilities getCapabilities();
   
    ISession createSession(IUserIdentity user);
    
}

package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.auth.IUserIdentity;

public interface IServer {

    IUserIdentity authenticateUser(IUserCredentials credentials);
    
}

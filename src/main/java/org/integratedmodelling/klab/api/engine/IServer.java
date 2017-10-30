package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IUserIdentity;

public interface IServer {

    IUserIdentity authenticateUser(/* TODO cert or login*/);
    
}

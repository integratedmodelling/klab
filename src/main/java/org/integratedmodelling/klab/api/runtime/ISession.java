package org.integratedmodelling.klab.api.runtime;

import java.io.Closeable;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;

public interface ISession extends IEngineSessionIdentity, Closeable {

    ITask observe(String urn);
    
}

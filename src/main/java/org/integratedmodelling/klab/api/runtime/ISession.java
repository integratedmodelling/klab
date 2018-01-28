package org.integratedmodelling.klab.api.runtime;

import java.io.Closeable;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface ISession extends IEngineSessionIdentity, Closeable {

    /**
     * The observation action called on ISession always creates a new context, and the URN
     * must specify an observer.
     * 
     * @param urn specifying a local or remote observer
     * @return the task that is observing the URN.
     * @throws KlabException
     */
    Future<IContext> observe(String urn) throws KlabException;
    
}

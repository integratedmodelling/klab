package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * A redesign of the engine API and its subcomponents, eventually available as separate services.
 * Unimplemented and only for brainstorming at the moment.
 * 
 * @author Ferd
 *
 */
public interface IEngineService {

    interface ResourceManager {

    }

    interface Reasoner {

    }

    interface Resolver {

        IDataflow<?> resolve(Object observable, IObservationScope scope);

    }

    interface Runtime {

        // ObservationScope createContextScope(IUserIdentity user, ObservationScope scope);

        <T extends IArtifact> T run(IDataflow<T> dataflow, IObservationScope scope);

    }

    /**
     * Login through an authenticated user identity and return the root scope for that user. The
     * scope for the user should be stored: if the user was logged in previously, the previously
     * logged in scope should be returned..
     * 
     * @param user
     * @return
     */
    IObservationScope login(IUserIdentity user);

}

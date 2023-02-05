package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * A redesign of the engine API and its subcomponents, eventually available as separate services.
 * Unimplemented and only for brainstorming at the moment.
 * 
 * @author Ferd
 *
 */
public interface IEngineService {

    /**
     * Scope for any observation operation. A root scope is given for the user upon authentication
     * and more scopes can be asked from it to handle sessions, applications and contexts. This
     * should eventually carry all user states and substitute ISessionState. Scopes are hierarchical
     * and there is a scope per application, context and session (which becomes transparently
     * handled).
     * 
     */
    interface ObservationScope {

        /**
         * 
         * @return
         */
        ISession getSession();
        /**
         * 
         * @return
         */
        IObservation getContext();
        /**
         * 
         * @return
         */
        IObserver<?> getObserver();
    }

    interface Resources {

    }

    interface Reasoner {

    }

    interface Resolver {

        IDataflow<?> resolve(Object observable, ObservationScope scope);

    }

    interface Runtime {

        // ObservationScope createContextScope(IUserIdentity user, ObservationScope scope);

        <T extends IArtifact> T run(IDataflow<T> dataflow, ObservationScope scope);

    }

}

package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
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

    /**
     * Scope for any observation operation. A root scope is given for the user upon authentication
     * and more scopes can be asked from it to handle sessions, applications and contexts. This
     * should eventually carry all user states and substitute ISessionState. Scopes are hierarchical
     * and there is a scope per application, context and session (which becomes transparently
     * handled).
     * 
     */
    interface ObservationScope {

        Reasoner getReasoner();
        
        Resolver getResolver();
        
        Runtime getRuntime();
        
        Resources getResources();

        IEngineService getEngine();
        
        /**
         * 
         * @return
         */
        IObservation getContext();
        /**
         * 
         * @return
         */
        IActorIdentity<?> getObserver();

        /**
         * Never null. User scopes have empty geometry; context scopes have the geometry of the
         * context or the union of the contexts if >1 acknowledgements have been set.
         * 
         * @return
         */
        IGeometry getGeometry();

        /**
         * 
         * @param scenarios
         * @return
         */
        ObservationScope withScenarios(String... scenarios);

        /**
         * 
         * @param scenarios
         * @return
         */
        ObservationScope withObserver(IActorIdentity<?> observer);

        /**
         * 
         * @param scenarios
         * @return
         */
        ObservationScope withContext(IDirectObservation context);
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

    /**
     * Login through an authenticated user identity and return the root scope.
     * 
     * @param user
     * @return
     */
    ObservationScope login(IUserIdentity user);

}

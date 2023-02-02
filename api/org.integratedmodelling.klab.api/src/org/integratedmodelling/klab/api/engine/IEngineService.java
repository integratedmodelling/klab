package org.integratedmodelling.klab.api.engine;

import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.runtime.ISession;

/**
 * A redesign of the engine API and its subcomponents, eventually available as separate services.
 * Unimplemented and only for brainstorming at the moment.
 * 
 * @author Ferd
 *
 */
public interface IEngineService {

    /**
     * Scope for any observation operation. The destination of the observation depends on the scope.
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

    }

    interface Runtime {

        ObservationScope createContextScope(IUserIdentity user, ObservationScope scope);

    }

}

package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KScope;
import org.integratedmodelling.klab.api.lang.kim.KKimModelStatement;
import org.integratedmodelling.klab.api.services.runtime.KDataflow;

public interface KResolver {

    /**
     * All services publish capabilities and have a call to obtain them.
     * 
     * @author Ferd
     *
     */
    interface Capabilities {

    }

    /**
     * Get the service capabilities.
     * 
     * @return
     */
    Capabilities getCapabilities();

    /**
     * The main function of the resolver. Returns a dataflow that must be executed by a runtime
     * service. Observable may be or resolve to any knowledge compatible with the observation scope.
     * If the scope is a session scope, the observable must be an acknowledgement unless the scope
     * has a set scale, in which case it can be a subject concept.
     * 
     * @param observable
     * @param scope
     * @return the dataflow that will create the observation in a runtime.
     */
    KDataflow<?> resolve(Object observable, KScope scope);

    interface Admin {

        /**
         * The "port" to ingest a model. Order of ingestion must be such that all knowledge and
         * constraints are resolved. Automatically stores the model in the local k.Box.
         * 
         * @param statement
         * @return
         */
        KConcept addModel(KKimModelStatement statement);
    }

}

package org.integratedmodelling.klab.api;

import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;

public interface API {
    /**
     * Base for many, but still not all, endpoints. TODO must use everywhere.
     */
    public static final String API_BASE = "/api/v2";

    /**
     * API for reasoner service.
     * 
     * @author ferd
     *
     */
    public interface REASONER {

        static String REASONER_BASE = API_BASE + "/reasoner";

        /**
         * Resolve a concept definition, returning a unique ID for the reasoner, the normalized URN
         * form and any metadata.
         * 
         * @protocol GET
         * @service 
         * @produces {@link KConcept}
         */
        public static String RESOLVE_CONCEPT = REASONER_BASE + "/resolve/concept/{definition}";

        /**
         * 
         * @protocol GET
         * @produces {@link KObservable}
         */
        public static String RESOLVE_OBSERVABLE = REASONER_BASE + "/resolve/observable/{definition}";

    }
}

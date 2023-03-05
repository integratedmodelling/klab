package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.mediation.KUnit;

public interface KObservable extends KSemantics {

    enum Resolution {
        /**
         * Makes the observable specify "any" child or itself, normally excluding the abstract ones
         * or those with children.
         */
        Any,
        /**
         * Makes the observable specify all children and itself, normally excluding the abstract
         * ones.
         */
        All,
        /**
         * Ensures the observable specifies only itself in contexts where it would normally specify
         * subclasses too.
         */
        Only
    }
    
    KUnit getUnit();
    
    KUnit getCurrency();

}

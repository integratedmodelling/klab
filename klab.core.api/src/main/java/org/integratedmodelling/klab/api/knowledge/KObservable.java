package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.mediation.KUnit;

public interface KObservable extends KSemantics {
    
    KUnit getUnit();
    
    KUnit getCurrency();

}

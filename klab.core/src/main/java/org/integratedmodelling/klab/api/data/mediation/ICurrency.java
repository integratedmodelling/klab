package org.integratedmodelling.klab.api.data.mediation;

import org.integratedmodelling.kim.api.IValueMediator;

public interface ICurrency extends IValueMediator {
    
    /**
     * @return true if monetary
     */
    boolean isMonetary();

}

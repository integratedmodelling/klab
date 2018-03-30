package org.integratedmodelling.kim.api;

import java.util.List;

/**
 * The bean that incarnates a valid 'observe' statement. Its children can
 * only be other IKimObserver.
 *  
 * @author fvilla
 *
 */
public interface IKimObserver extends IKimActiveStatement {
    
    /**
     * Mandatory name for the resulting observation.
     * 
     * @return
     */
    String getName();
    
    /**
     * The type of the stated observation.
     * 
     * @return
     */
    IKimObservable getObservable();

    /**
     * Any states declared for the object. These observables are only
     * legal if they are pre-resolved to values.
     * 
     * @return
     */
    List<IKimObservable> getStates();

}

package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;

/**
 * The bean that incarnates a valid 'observe' statement. Its children can
 * only be other IKimObserver.
 *  
 * @author fvilla
 *
 */
public interface KKimAcknowledgement extends KKimActiveStatement {
    
	/**
	 * Optional URN to retrieve the observer from.
	 * 
	 * @return
	 */
	String getUrn();
	
    /**
     * Mandatory name for the resulting observation.
     * 
     * @return the name
     */
    String getName();
    
    /**
     * The type of the stated observation.
     * 
     * @return the observable
     */
    KKimObservable getObservable();

    /**
     * Any states declared for the object. These observables are only
     * legal if they are pre-resolved to values.
     * 
     * @return the states
     */
    List<KKimObservable> getStates();

    /**
     * Docstring if any.
     * 
     * @return the docstring
     */
    String getDocstring();

}

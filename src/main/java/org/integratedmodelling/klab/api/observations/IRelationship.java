package org.integratedmodelling.klab.api.observations;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IRelationship  extends IDirectObservation {

    /**
     * 
     * @return
     */
    ISubject getSource();
    
    /**
     * 
     * @return
     */
    ISubject getTarget();
    
}

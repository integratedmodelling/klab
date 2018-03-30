package org.integratedmodelling.klab.api.observations;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IRelationship  extends ICountableObservation {

    /**
     * 
     * @return the source subject
     */
    ISubject getSource();
    
    /**
     * 
     * @return the target subject
     */
    ISubject getTarget();
    
}

package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IRelationship  extends IDirectObservation, IArtifact<IRelationship> {

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

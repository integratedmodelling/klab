package org.integratedmodelling.klab.api.observations;

public interface IRelationship  extends IDirectObservation {

    ISubject getSource();
    
    ISubject getTarget();
    
}

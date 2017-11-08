package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept;

public interface ISubject  extends IDirectObservation {
    
    Collection<IEvent> getEvents();
    
    Collection<IProcess> getProcesses();
    
    Collection<ISubject> getSubjects();
    
    Collection<IRelationship> getRelationships();
    
    Collection<IRelationship> getIncomingRelationships(ISubject subject);
    
    Collection<IRelationship> getOutgoingRelationships(ISubject subject);
    
    Map<IKimConcept, IConfiguration> getConfigurations();


}

package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.kim.api.IConcept;

public interface ISubject  extends IDirectObservation {
    
    Collection<IEvent> getEvents();
    
    Collection<IProcess> getProcesses();
    
    Collection<ISubject> getSubjects();
    
    Map<IConcept, IConfiguration> getConfigurations();

    // TODO we use a structure as before, or not?
//    Collection<I>

}

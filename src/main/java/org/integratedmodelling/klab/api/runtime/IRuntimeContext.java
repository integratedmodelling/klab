package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;

public interface IRuntimeContext {

  IProvenance getProvenance();

  IEventBus getEventBus();

  IConfigurationDetector getConfigurationDetector();
  
  Collection<IRelationship> getOutgoingRelationships(ISubject observation);

  Collection<IRelationship> getIncomingRelationships(ISubject observation);

  Collection<ISubject> getAllSubjects();
  
  ISubject getRoot();
  
  /*
   * Must be exposed for a runtime provider to be able to set the root subject.
   * @param subject
   */
  void setRootSubject(ISubject subject);
  
}

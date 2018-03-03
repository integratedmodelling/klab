package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;

/**
 * The runtime context holds all information about the computation being run. It is passed to
 * dataflows and down to actuators and contextualizers, appropriately customized to reflect names
 * and locators that each actuator expects.
 * 
 * @author Ferd
 *
 */
public interface IComputationContext {

  /**
   * The namespace of reference in this context. Usually that of the running model or observer.
   * 
   * @return the namespace of reference. Never null in normal circumstances, although it may be null
   *         in remote services that have no semantic awareness.
   */
  INamespace getNamespace();

  /**
   * 
   * @return
   */
  IProvenance getProvenance();

  /**
   * 
   * @return
   */
  IEventBus getEventBus();

  /**
   * 
   * @param observation
   * @return
   */
  Collection<IRelationship> getOutgoingRelationships(ISubject observation);

  /**
   * 
   * @param observation
   * @return
   */
  Collection<IRelationship> getIncomingRelationships(ISubject observation);

  /**
   * 
   * @return
   */
  Collection<ISubject> getAllSubjects();

  /**
   * The subject that provides the context for this computation. It is null only when the root
   * subject hasn't been resolved yet, which is not a situation that API users will normally
   * encounter.
   * 
   * @return
   */
  ISubject getSubject();

}

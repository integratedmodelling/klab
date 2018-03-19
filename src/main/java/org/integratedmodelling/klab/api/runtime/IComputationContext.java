package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;

/**
 * The runtime context holds all information about the computation being run. It is passed to
 * dataflows and down to actuators and contextualizers, appropriately customized to reflect names
 * and locators that each actuator expects.
 * 
 * The {@link IParameters} methods access user-defined parameters, including any passed to the
 * calling functions or URNs and, if appropriate, context-localized POD values for states (e.g. the
 * specific value at the point of computation). The actual data objects are always available through
 * {@link #getData(String)}.
 * 
 * @author Ferd
 *
 */
public interface IComputationContext extends IParameters {

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
   * Get the resolved {@link IObservationData object} corresponding to the passed local name. Use
   * {@link #get(String)} to retrieve contextualized values for states or parameters.
   * 
   * @param localName
   * @return
   */
  IObservationData getData(String localName);

  /**
   * The data for the subject that provides the context for this computation. It is null only when
   * the root subject hasn't been resolved yet, which is not a situation that API users will
   * normally encounter. The data are wrapped into a semantic ISubject after the computation has
   * ended.
   * 
   * @return
   */
  IObjectData getSubjectData();

}

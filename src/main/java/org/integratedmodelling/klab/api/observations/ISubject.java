package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.Map;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.services.IOntologyService;

/**
 * A subject can be inside a root observation or (unique among observation) <i>be</i> a root
 * observation itself. As identity, all Subjects in a session have the parent {@link ISession} as
 * the parent identity. As observations, their lineage is accessible through the
 * {@link IObservation} API. Only subjects can have events, processes, relationships, configurations
 * and other subjects as children. In addition, subjects and all other direct observations can have
 * states as children.
 * 
 * @author ferdinando.villa
 *
 */
public interface ISubject extends ICountableObservation {

  /**
   * 
   * @return
   */
  Collection<IEvent> getEvents();

  /**
   * 
   * @return
   */
  Collection<IProcess> getProcesses();

  /**
   * 
   * @return
   */
  Collection<ISubject> getSubjects();

  /**
   * 
   * @return
   */
  Collection<IRelationship> getRelationships();

  /**
   * 
   * @param subject
   * @return
   */
  Collection<IRelationship> getIncomingRelationships(ISubject subject);

  /**
   * 
   * @param subject
   * @return
   */
  Collection<IRelationship> getOutgoingRelationships(ISubject subject);

  /**
   * 
   * @return
   */
  Map<IConcept, IConfiguration> getConfigurations();

  /*
   * -------------------------------------------------------------------------------------- Runtime
   * functions below
   * --------------------------------------------------------------------------------------
   */

  /**
   * Observe the passed URN, which may specify a concept, a model or an observer. Countable concepts
   * will trigger instantiation (the instantiated objects will be available through
   * {@link ITask#getObservations()}. Non-countable will create the observation and resolve it,
   * unless a suitable resolution strategy cannot be identified.
   * 
   * @param urn
   * @param scenarios IDs of any scenarios to use in resolution
   * @return the future IObservation.
   */
  ITask<IObservation> observe(String urn, String... scenarios);

  /**
   * Call this on the root observation to create the logical peers of an observation tree in the
   * passed ontology.
   * 
   * @param ontology an ontology, usually empty (may be created with
   *        {@link IOntologyService#require(String)}).
   * @return the individual corresponding to this subject.
   */
  IIndividual instantiate(IOntology ontology);

}

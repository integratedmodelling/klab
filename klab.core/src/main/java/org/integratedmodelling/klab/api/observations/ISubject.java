/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
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

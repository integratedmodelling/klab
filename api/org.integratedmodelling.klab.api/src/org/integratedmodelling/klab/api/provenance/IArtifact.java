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
package org.integratedmodelling.klab.api.provenance;

import java.util.Collection;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IModelArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An Artifact can be any of the first-class products of a k.LAB task: a non-semantic
 * {@link org.integratedmodelling.klab.api.data.artifacts.IDataArtifact} or {@link org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact}, a semantic {@link org.integratedmodelling.klab.api.observations.IObservation} (as produced by
 * most activities in k.LAB) or a {@link IKimModel k.IM model description} when the model has been
 * produced by an observation activity, such as a learning model.
 * <p>
 * By implementing {@link java.lang.Iterable}, we also allow Artifacts to represent groups of artifacts (e.g.
 * all the {@link ISubject subjects} instantiated by resolving a subject {@link IObservable
 * observable}). This enables simpler handling of provenance, as each observation activity returns
 * one artifact, possibly iterable as a group.
 * <p>
 * Each artifact exposes the provenance graph it's part of, allowing all k.LAB tasks to simply
 * return an {@code IArtifact} and provide full information on what happened.
 * <p>
 * The API to use a {@link org.integratedmodelling.kim.api.IKimModel} as an artifact ({@link org.integratedmodelling.klab.api.data.artifacts.IModelArtifact}) is not fully
 * developed yet.
 * <p>
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IArtifact extends Node, Iterable<IArtifact> {

  /**
   * The geometry linked to the observation. Observational artifacts will specialize this as IScale.
   *
   * @return the geometry
   */
  IGeometry getGeometry();

  /**
   * Metadata. Never null, possibly empty.
   *
   * @return the metadata
   */
  IMetadata getMetadata();

  /**
   * <p>getUrn.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  String getUrn();

  /**
   * <p>getConsumer.</p>
   *
   * @return a {@link org.integratedmodelling.klab.api.provenance.IAgent} object.
   */
  IAgent getConsumer();

  /**
   * <p>getOwner.</p>
   *
   * @return a {@link org.integratedmodelling.klab.api.provenance.IAgent} object.
   */
  IAgent getOwner();

  /**
   * <p>getAntecedents.</p>
   *
   * @return a {@link java.util.Collection} object.
   */
  Collection<IArtifact> getAntecedents();

  /**
   * <p>getConsequents.</p>
   *
   * @return a {@link java.util.Collection} object.
   */
  Collection<IArtifact> getConsequents();

  /**
   * Trace the nearest artifact of the passed concept (or with the passed role/trait) up the
   * provenance chain.
   *
   * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
   * @return a {@link org.integratedmodelling.klab.api.provenance.IArtifact} object.
   */
  IArtifact trace(IConcept concept);

  /**
   * Collect all artifacts of the passed concept (or with the passed role/trait) up the provenance
   * chain.
   *
   * @param concept a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
   * @return a {@link java.util.Collection} object.
   */
  Collection<IArtifact> collect(IConcept concept);

  /**
   * Trace the nearest artifact with the passed role within the passed observation up the provenance
   * chain.
   *
   * @param role
   * @param roleContext a {@link org.integratedmodelling.klab.api.observations.IDirectObservation} object.
   * @return a {@link org.integratedmodelling.klab.api.provenance.IArtifact} object.
   */
  IArtifact trace(IConcept role, IDirectObservation roleContext);

  /**
   * Collect all artifacts with the passed role within the passed observation up the provenance
   * chain.
   *
   * @param role
   * @param roleContext a {@link org.integratedmodelling.klab.api.observations.IDirectObservation} object.
   * @return a {@link java.util.Collection} object.
   */
  Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext);

  /**
   * The size of the group that this artifact is part of. Any artifact is part of
   * a group including at least itself.
   *
   * @return 1 or more
   */
  int groupSize();
  

  /**
   * Any observation that exists has provenance. Call this on the root observation for the entire
   * graph.
   *
   * @return the provenance record leading to this
   */
  IProvenance getProvenance();


}

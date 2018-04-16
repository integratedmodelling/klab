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

import java.util.Optional;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.auth.IArtifactIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * The Interface IObservation.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IObservation extends IArtifactIdentity, IArtifact {

  /**
   * The subject observation that contextualized this observation. This is not the same as the
   * context observation: it allows recording different viewpoints on observations that are
   * contextual to the same observable - e.g. qualities of the same subject seen by different child
   * subjects in it. If null, this was made by the "root subject" that represents the session user.
   *
   * We may eventually create a subject to represent the session user for consistency, but as of the
   * current version this is not done.
   *
   * @return the subject that provides the viewpoint for this observation, or empty if this was a
   *         user-made observation.
   */
  Optional<ISubject> getObserver();

  /**
   * Return the observable.
   *
   * @return the observation's observable
   */
  IObservable getObservable();

  /**
   * Return the scale seen by this object, merging all the extents declared for the subject in the
   * observation context. This could simply override {@link org.integratedmodelling.klab.api.provenance.IArtifact#getGeometry()} as a
   * {@link org.integratedmodelling.klab.api.observations.scale.IScale} is a {@link org.integratedmodelling.kim.api.data.IGeometry}, and in a standard implementation should do just that,
   * but a {@link org.integratedmodelling.klab.api.observations.scale.IScale} is important enough to deserve its own accessor.
   *
   * @return the observation's scale
   */
  IScale getScale();

  /**
   * Observation may have been made in the context of another direct observation. This will always
   * return non-null in indirect observations, and may return null in direct ones when they
   * represent the "root" context.
   *
   * @return the context for the observation, if any.
   */
  IDirectObservation getContext();

  /**
   * True if our scale has an observation of space with more than one state value.
   *
   * @return true if distributed in space
   */
  boolean isSpatiallyDistributed();

  /**
   * True if our scale has an observation of time with more than one state value.
   *
   * @return true if distributed in time.
   */
  boolean isTemporallyDistributed();

  /**
   * True if our scale has any implementation of time.
   *
   * @return if time is known
   */
  boolean isTemporal();

  /**
   * True if our scale has any implementation of space.
   *
   * @return if space is known
   */
  boolean isSpatial();

  /**
   * Return the spatial extent, or null.
   *
   * @return the observation of space
   */
  ISpace getSpace();

}

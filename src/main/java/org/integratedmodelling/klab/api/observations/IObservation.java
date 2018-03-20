package org.integratedmodelling.klab.api.observations;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;

public interface IObservation extends IObservationIdentity, Serializable, IArtifact {

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
   * observation context.
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
   * The topmost ISubject that acts as a context for everything. This subject's getRoot() will
   * return null.
   * 
   * @return the root subject. Never null.
   */
  ISubject getRoot();

  /**
   * True if our scale has an observation of space with more than one state
   * value.
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

  /**
   * All observations wrap a raw data object, which dataflows and contextualizers operate upon. The
   * data object also mirrors any chained artifacts' content through its own iterator API.
   * 
   * @return the observation data. Never null.
   */
  IObservationData getData();

  /**
   * Any observation that exists has provenance. Call this on the root observation for the entire
   * graph.
   * 
   * @return the provenance record leading to this
   */
  IProvenance getProvenance();

  /**
   * Retrieve the observation following this in a group of artifacts. The return value has no
   * relationship with the observation structure; it only describes the provenance, i.e. the
   * grouping of observations that were created within the same activity (observation task).
   * 
   * Overrides the {@link Iterator#next()} from {@link IArtifact} to return a {@code IObservation}.
   * 
   * @return the next observation in a group, if {@link #hasNext()} returns true.
   * 
   */
  @Override
  IObservation next();
}

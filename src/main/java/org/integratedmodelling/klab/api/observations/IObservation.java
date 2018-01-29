package org.integratedmodelling.klab.api.observations;

import java.io.Serializable;
import java.util.Optional;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IProvenance;

public interface IObservation extends IObservationIdentity, Serializable {

  /**
   * The subject observation that contextualized this observation. This is not the same as the
   * context observation: it allows recording different viewpoints on observations that are
   * contextual to the same observable - e.g. qualities of the same subject seen by different child
   * subjects in it. If null, this was made by the "root subject" that represents the session user.
   * Later we may create a subject to represent the session user.
   * 
   * @return the subject that provides the viewpoint for this observation, or null if this was a
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

  // /**
  // * The overall "world" that this observation is part of, with the root subject at the
  // * top and all records of resolution, provenance and semantics.
  // *
  // * @return
  // */
  // IContext getContext();

  /**
   * True if the owning ISubject has an observation of space with more than one state value.
   * 
   * @return true if distributed in space
   */
  boolean isSpatiallyDistributed();

  /**
   * True if the owning ISubject has an observation of time with more than one state value.
   * 
   * @return true if distributed in time.
   */
  boolean isTemporallyDistributed();

  /**
   * True if the owning ISubject has any implementation of time.
   * 
   * @return if time is known
   */
  boolean isTemporal();

  /**
   * True if the owning ISubject has any implementation of space.
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
   * Any observation that exists has provenance. Call this on the root observation for
   * the entire graph.
   *  
   * @return the provenance record leading to this
   */
  IProvenance getProvenance();

  /**
   * Open a k.EXPLORER window on the root observation, focused on this.
   */
  void explore();
}

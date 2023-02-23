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
package org.integratedmodelling.klab.api.knowledge.observation;

import org.integratedmodelling.klab.api.geometry.ILocator;
import org.integratedmodelling.klab.api.identities.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.observation.scale.IScale;
import org.integratedmodelling.klab.api.knowledge.observation.scale.space.ISpace;

/**
 * The interface IObservation, which is the semantic equivalent of an IArtifact
 * and once created in a k.LAB session, can be made reactive by supplementing it
 * with a behavior. Models may bind instantiated observations to actor files
 * that will provide behaviors for their instances (or a subset thereof). Once
 * made reactive, they can interact with each other and the system.
 * <p>
 * An observation
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IObservation extends IIdentity, IArtifact {

	/**
	 * Return the observable.
	 *
	 * @return the observation's observable
	 */
	IObservable getObservable();

	/**
	 * The observer that/who made the observation. Never null.
	 * 
	 * @return
	 */
	IIdentity getObserver();

	/**
	 * Return the scale seen by this object, merging all the extents declared for
	 * the subject in the observation context. This could simply override
	 * {@link org.integratedmodelling.klab.api.provenance.IArtifact#getGeometry()}
	 * as a {@link org.integratedmodelling.klab.api.observations.scale.IScale} is a
	 * {@link org.integratedmodelling.klab.api.data.IGeometry}, and in a standard
	 * implementation should do just that, but a
	 * {@link org.integratedmodelling.klab.api.observations.scale.IScale} is
	 * important enough to deserve its own accessor.
	 *
	 * @return the observation's scale
	 */
	IScale getScale();

	/**
	 * Return a view of this observation restricted to the passed locator, which is
	 * applied to the scale to obtain a new scale, used as a filter to obtain the
	 * view. The result should be able to handle both conformant scaling (e.g. fix
	 * one dimension) and non-conformant (i.e. one state maps to multiple ones with
	 * irregular extent coverage) in both reading and writing.
	 * 
	 * @param locator
	 * @return a rescaled view of this observation
	 * @throws IllegalArgumentException if the locator is unsuitable for the
	 *                                  observation
	 */
	IObservation at(ILocator locator);

	/**
	 * Observation may have been made in the context of another direct observation.
	 * This will always return non-null in indirect observations, and may return
	 * null in direct ones when they represent the "root" context.
	 *
	 * @return the context for the observation, if any.
	 */
	IDirectObservation getContext();

	/**
	 * The top-level context. If this observation is the top-level context, return
	 * itself.
	 * 
	 * @return the top-level context for the observation. Never null.
	 */
	IDirectObservation getRootContext();

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

	/**
	 * Return true if this observation has changes that happened after
	 * initialization. Note that it is not guaranteed that a dynamic observation
	 * knows it's dynamic before changes are reported, so observations may start
	 * static and become dynamic later.
	 * 
	 * @return
	 */
	boolean isDynamic();

	/**
	 * Time of creation. If the context has no time, this is equal to the
	 * {@link IArtifact#getTimestamp()}; otherwise it is the time reported by the
	 * scheduler at the moment of construction.
	 * 
	 * @return the time of creation
	 */
	long getCreationTime();

	/**
	 * Time of exit. If the context has no time or the object is current, this is
	 * -1L; otherwise it is the time reported by
	 * {@link IScheduler#getSliceOffsetInBackend()} at the moment of exit.
	 * 
	 * @return the time of exit
	 */

	long getExitTime();

	/**
	 * All times when the observation was modified, corresponding to the end of any
	 * temporal transitions that changed it.This will always contain at least a 0
	 * for initialization, except for events, event groups and qualities that have
	 * no initial value because they describe a process.
	 * 
	 * @return
	 */
	long[] getUpdateTimestamps();

}

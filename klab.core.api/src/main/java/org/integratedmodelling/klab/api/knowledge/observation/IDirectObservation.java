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

import java.util.Collection;

import org.integratedmodelling.klab.api.geometry.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * The Interface IDirectObservation, i.e. an observation that can be
 * acknowledged on its own without referencing another. Basically every
 * observation except qualities (and maybe processes, if so departing from the
 * BFO 'dependent' inheritance - we may say that processes are not dependent but
 * their observations are).
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface IDirectObservation extends IObservation, IObjectArtifact {

	/**
	 * <p>
	 * All direct observations have naming dignity.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * Direct observations may have children. This is a convenience method to find a
	 * particular child artifact.
	 * 
	 * @param observable
	 * @return
	 */
	IObservation getChildObservation(IObservable observable);

	/**
	 * <p>
	 * Direct observations may have states of their own.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IState> getStates();

	@Override
	IDirectObservation at(ILocator locator);

	/**
	 * If the observation derives from an emerging pattern, return it here.
	 * 
	 * @return
	 */
	IPattern getOriginatingPattern();

	/**
	 * Direct observations may die in action.
	 * 
	 * @return true if alive, i.e. receiving events from scheduler and context.
	 */
	boolean isActive();

}

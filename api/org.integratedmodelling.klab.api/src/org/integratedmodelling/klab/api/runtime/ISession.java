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
package org.integratedmodelling.klab.api.runtime;

import java.io.Closeable;
import java.net.URL;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Any observation made in k.LAB must be done within a valid user session.
 * Sessions are obtained from a running {@link IEngine} using
 * {@link IEngine#createSession()} or
 * {@link IEngine#createSession(IEngineUserIdentity)}.
 * <p>
 * Sessions must be properly closed when not needed anymore. A ISession is a
 * {@link java.io.Closeable}, so a typical usage is
 *
 * <pre>
 * try (ISession session = engine.createSession()) {
 * 	// do things
 * } catch (KlabException e) {
 * 	// complain
 * }
 * </pre>
 *
 * A session is also an {@link org.integratedmodelling.klab.api.auth.IIdentity},
 * and its token must authenticate those engine API calls that are
 * session-aware. All sessions have a {@link IUserIdentity} as parent.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ISession extends IEngineSessionIdentity, Closeable {

	/**
	 * The observation action called on ISession always creates a new root subject.
	 * The URN must specify a
	 * {@link org.integratedmodelling.klab.api.model.IObserver} unless
	 * {@link #getRegionOfInterest()} returns a geometry that can be used as
	 * context.
	 *
	 * @param urn
	 *            specifying a (local or remote) observer
	 * @param scenarios
	 *            names of any scenario namespaces to use in resolution
	 * @return a Future that is observing the URN.
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	Future<ISubject> observe(String urn, String... scenarios);


	/**
	 * Retrieve a live observation if available, or return null.
	 * <p>
	 * Live observations are part of active contexts and have "live" peers in the
	 * engine. They should be available in sessions during their contextualization,
	 * and possibly after that, for a time that depends on configuration and
	 * possibly on settings relative to persistence and garbage collection.
	 * Persisted observations should be available in all sessions belonging to the
	 * user that persisted them.
	 * <p>
	 * Retrieving an observation at any level in the hierarchy should be a fast
	 * operation, although observations may be many.
	 * 
	 * @param observationId
	 * @return the observation, or null.
	 */
	IObservation getObservation(String observationId);

	/**
	 * Retrieve a task being executed.
	 * <p>
	 * Tasks should only be retrievable when they are being executed. The main
	 * reasons to retrieve a task are checking its status and interrupting it. Tasks
	 * should be disposed of after they end.
	 * 
	 * @param taskId
	 * @return the task being executed, or null.
	 */
	<T extends Future<?>> T getTask(String taskId, Class<T> cls);
	
	/**
	 * Run the content of a URL as a script, returning the future that will compute
	 * its result (often null). The {@link IEngine} has a similar function that
	 * automatically opens a new session.
	 * 
	 * @param url
	 * @return the running script
	 * @throws KlabException
	 */
	IScript run(URL url);

	/**
	 * The geometry of interest depends on user actions and starts empty. As the
	 * user interacts with the session, this may reflect actions such as zooming in
	 * on particular spatial and/or temporal extents, which can be used when
	 * observations are made without stating a context.
	 * 
	 * @return the region of interest, or the empty geometry
	 */
	IGeometry getRegionOfInterest();

}

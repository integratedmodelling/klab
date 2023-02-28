/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.runtime;

import java.io.Closeable;
import java.net.URL;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Any observation made in k.LAB must be done within a valid user session. Sessions are obtained
 * from a running {@link IEngine} using {@link IEngine#createSession()} or
 * {@link IEngine#createSession(IEngineUserIdentity)}.
 * <p>
 * Sessions must be properly closed when not needed anymore. A ISession is a
 * {@link java.io.Closeable}, so a typical usage is
 *
 * <pre>
 * try (ISession session = engine.createSession()) {
 *     // do things
 * } catch (KlabException e) {
 *     // complain
 * }
 * </pre>
 *
 * A session is also an {@link org.integratedmodelling.klab.api.auth.IIdentity}, and its token must
 * authenticate those engine API calls that are session-aware. All sessions have a
 * {@link IUserIdentity} as parent.
 * <p>
 * If a session has a behavior associated (bound in the connection REST call by name), it becomes an
 * actor and implements it by setting priorities, views and whatever else the behavior specifies.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ISession extends IEngineSessionIdentity, Closeable, IObserver<ISubject> {

    // TODO flesh out
    public interface Listener {

        void onClose(ISession session);
    }

    /**
     * Observe a new context of the passed type, returning an asynchronous future. This is the
     * primary, asynchronous context observation method for the k.LAB modeling API using the URN of
     * a known observer instead of an observable and scale. Interactive sessions and applications
     * use the ISessionState to organize and schedule observations. When this method is used, the
     * task should not have started, so the user has a chance to apply listeners, scenarios and
     * other options.
     * 
     * @param observable
     * @param geometry
     * @return
     */
    ITask<ISubject> observe(String observerUrn);

    /**
     * Retrieve a live observation if available, or return null.
     * <p>
     * Live observations are part of active contexts and have "live" peers in the engine. They
     * should be available in sessions during their contextualization, and possibly after that, for
     * a time that depends on configuration and possibly on settings relative to persistence and
     * garbage collection. Persisted observations should be available in all sessions belonging to
     * the user that persisted them.
     * <p>
     * Retrieving an observation at any level in the hierarchy should be a fast operation, although
     * observations may be many.
     * 
     * @param observationId
     * @return the observation, or null.
     */
    IObservation getObservation(String observationId);

    /**
     * Retrieve a task being executed.
     * <p>
     * Tasks should only be retrievable when they are being executed. The main reasons to retrieve a
     * task are checking its status and interrupting it. Tasks should be disposed of after they end.
     * 
     * @param taskId
     * @return the task being executed, or null.
     */
    <T extends Future<?>> T getTask(String taskId, Class<T> cls);

    /**
     * Run the content of a URL as a script, returning the future that will compute its result
     * (often null). The {@link IEngine} has a similar function that automatically opens a new
     * session.
     * 
     * @param url
     * @return the running script
     * @throws KlabException
     */
    IScript run(URL url);

    /**
     * Interactive sessions have a human at the other end of the line and can ask her questions.
     * 
     * @return true if the human has set the session to interactive.
     */
    boolean isInteractive();

    /**
     * The session promotes its state to a structured {@link ISessionState} that can be saved or
     * restored, as well as used to build observation contexts with successive atomic operations.
     */
    @Override
    ISessionState getState();

    /**
     * Interrupt all observation tasks that are running at the moment of calling.
     */
    void interruptAllTasks();

    /**
     * The authenticated user that owns the session. The observer returned by {@link #getObserver()}
     * is an actor identity that represents it. TODO we should merge these two eventually.
     * 
     * @return
     */
    IUserIdentity getUser();

    void addListener(Listener listener);

    boolean isDefault();

    /**
     * All session have an observer, representing the user owning the session. This is the default
     * observer for all observations made directly through the API or a client. Observation actions
     * come from the observer, not the session (TODO this is not how the current API is designed).
     * The observer's behavior may be defined using the ~/.klab/user.kactors file.
     * 
     * @return
     */
    IObserver<?> getObserver();

//    /**
//     * Load a specified behavior in a specified runtime scope. Returns a string
//     * identifying the running behavior, which can be passed to
//     * {@link #stop(String)}.
//     * 
//     * @param behavior
//     * @param scope
//     */
//    String load(IBehavior behavior, IContextualizationScope scope);
//
//    boolean stop(String behaviorId);
}

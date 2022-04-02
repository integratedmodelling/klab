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

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;

/**
 * A ITask computes an observational artifact, delegating to a Java Future that returns it when
 * available. The task will first resolve its resolvable object, then if successful compile the
 * resolution tree into a dataflow which will be sent to the configured runtime to produce the
 * artifact.
 * <p>
 * In API usage, the first task that creates the context subject is obtained by calling
 * {@link ISession#observe(IObservable, IGeometry)}. Afterwards, more observations in the context
 * may be made by using {@link ISubject#observe(IObservable, IGeometry)}. These functions return
 * paused tasks that are started at get() or can be started explicitly using start().
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T> the type of observation being resolved
 */
public interface ITask<T extends IArtifact> extends ITaskIdentity, Future<T> {

    /**
     * The "resolvable" unit that this task is dedicated to resolving (not necessarily
     * successfully). May be an observable, a concept, a model or an observer.
     * 
     * @return
     */
    IResolvable getResolvable();

    /**
     * Add a listener to call when the artifact is produced. For consistent results this must be
     * called before the task is started (the ISubject and ISession observe() functions return lazy
     * tasks that only start when get() is called).
     * 
     * @param listener
     */
    void addObservationListener(BiConsumer<ITask<?>, T> listener);

    /**
     * Add a listener to call whenever an exception is produced. For consistent results this must be
     * called before the task is started (the ISubject and ISession observe() functions return lazy
     * tasks that only start when get() is called).
     * 
     * @param listener
     */
    void addErrorListener(BiConsumer<ITask<?>, Throwable> listener);

    /**
     * Add any number of scenarios to use in resolution. For consistent results this must be called
     * before the task is started (the ISubject and ISession observe() functions return lazy tasks
     * that only start when get() is called).
     * 
     * @param scenarios
     */
    void addScenarios(Collection<String> scenarios);

    /**
     * Call start() if you want to control execution without calling get(). Implementations may
     * provide autostarting tasks where this call has no effect.
     * 
     * @return
     */
    boolean start();

}

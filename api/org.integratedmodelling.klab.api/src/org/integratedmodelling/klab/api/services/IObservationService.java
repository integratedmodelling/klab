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
package org.integratedmodelling.klab.api.services;

import java.io.File;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The {@code IObservationService} handles the main task in k.LAB, resolving user-specified semantic
 * assets to computations that build the corresponding observational artifacts in a context of
 * choice. The context is itself an observational artifact, built by user acknowledgement.
 * <p>
 * This service also manages observations stored locally (as IObserver specifications) and provides
 * an API to query and retrieve those stored on the k.LAB network.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @since 0.10.0
 */
public interface IObservationService {

    /**
     * Resolve the URN for a top-level resolvable to the computation that will produce the
     * corresponding observation when run. Unsuccessful resolution is indicated by a dataflow with
     * empty coverage, which will produce an empty observation when run.
     * <p>
     * The {@link org.integratedmodelling.klab.api.runtime.ISession#observe(String, String...)}
     * method calls this function and runs the dataflow in a
     * {@link org.integratedmodelling.klab.api.runtime.ITask}.
     * <p>
     * The dataflow can be run right away to produce a
     * {@link org.integratedmodelling.klab.api.observations.IObservation} artifact or serialized
     * using {@link org.integratedmodelling.klab.api.runtime.dataflow.IDataflow#getKdlCode()} to be
     * loaded and run another time. It will include a specification of its total context of
     * applicability if any exists.
     * <p>
     *
     * @param urn the identifier for a top-level observation (describing a IObserver or a remote
     *        context).
     * @param session a valid engine session
     * @param scenarios zero or more scenario IDs to affect the resolution
     * @return the computation to observe the URN. Never null, possibly empty.
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios) throws KlabException;

    /**
     * Resolve the passed URN to to the computation that will produce the corresponding observation
     * in the context of the passed {@link ISubject subject}. Unsuccessful resolution is indicated
     * by a dataflow with empty coverage, which will produce an empty observation when run.
     * <p>
     * The resolution is done in the {@link org.integratedmodelling.klab.api.runtime.ISession} that
     * owns the passed observation.
     * <p>
     * The {@link org.integratedmodelling.klab.api.observations.ISubject#observe(String, String...)}
     * method calls this function and runs the dataflow in a
     * {@link org.integratedmodelling.klab.api.runtime.ITask}.
     * <p>
     * The dataflow can be run right away to produce a
     * {@link org.integratedmodelling.klab.api.observations.IObservation} artifact or serialized
     * using {@link org.integratedmodelling.klab.api.runtime.dataflow.IDataflow#getKdlCode()} to be
     * loaded and run another time. It will include a specification of its total context of
     * applicability if any exists.
     * <p>
     *
     * @param urn a {@link java.lang.String} object.
     * @param context a {@link org.integratedmodelling.klab.api.observations.ISubject} object.
     * @param scenarios zero or more scenario IDs to affect the resolution
     * @return the computation to observe the URN in the passed context. Never null, possibly empty.
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios) throws KlabException;

    /**
     * Index passed observation definition for retrieval.
     *
     * @param observer a {@link org.integratedmodelling.klab.api.model.IAcknowledgement} object.
     * @param monitor a {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor} object.
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    void index(IAcknowledgement observer, IMonitor monitor) throws KlabException;

    /**
     * Get a state that represents a view of another state seen through a different scale. The
     * resulting state is read/write, i.e. any setting of values will propagate to the underlying
     * storage according to what the scale mapping requires.
     * 
     * @param state
     * @param scale
     * @param scope
     * @return the state view
     */
    IState getStateView(IState state, IScale scale, IContextualizationScope scope);

    /**
     * Get a state that represents a specified observable through the values of another state with a
     * potentially different observable, seen through a different scale. The different observable
     * may be the same minus a data reduction trait, or a completely different one. The resulting
     * state is read/write, i.e. any setting of values will propagate to the underlying storage
     * according to what the scale mapping requires.
     * 
     * @param observable
     * @param state
     * @param scale
     * @param scope
     * @return the state view
     */
    IState getStateViewAs(IObservable observable, IState state, IScale scale, IContextualizationScope scope);

    /**
     * Export an observation to a file using adapter export capabilities.
     * 
     * @param observation
     * @param locator
     * @param file
     * @param outputFormat
     * @param adapterId may be null.
     * @param monitor
     * @param options key-value pairs for any options to set into the importer
     * @return the same file passed, or a different one if a change was needed (e.g. multiple files
     *         in a zip).
     */
    File export(IObservation observation, ILocator locator, File file, String outputFormat, String adapterId, IMonitor monitor,
            Object... options);

}

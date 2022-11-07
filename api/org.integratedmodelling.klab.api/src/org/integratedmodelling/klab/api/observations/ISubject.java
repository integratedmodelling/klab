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
package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.api.runtime.ITask;

/**
 * A subject can be inside a root observation or (unique among observation) <i>be</i> a root
 * observation itself. As identity, all Subjects in a session have the parent
 * {@link org.integratedmodelling.klab.api.runtime.ISession} as the parent identity. As
 * observations, their lineage is accessible through the
 * {@link org.integratedmodelling.klab.api.observations.IObservation} API. Only subjects can have
 * events, processes, relationships, configurations and other subjects as children. In addition,
 * subjects and all other direct observations can have states as children.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ISubject extends IDirectObservation, IObserver<IObservation> {

    /**
     * <p>
     * getEvents.
     * </p>
     *
     * @return a {@link java.util.Collection} object.
     */
    Collection<IEvent> getEvents();

    /**
     * <p>
     * getProcesses.
     * </p>
     *
     * @return a {@link java.util.Collection} object.
     */
    Collection<IProcess> getProcesses();

    /**
     * <p>
     * getSubjects.
     * </p>
     *
     * @return a {@link java.util.Collection} object.
     */
    Collection<ISubject> getSubjects();

    /**
     * <p>
     * getRelationships.
     * </p>
     *
     * @return a {@link java.util.Collection} object.
     */
    Collection<IRelationship> getRelationships();

    /**
     * <p>
     * getIncomingRelationships.
     * </p>
     *
     * @param subject a {@link org.integratedmodelling.klab.api.observations.ISubject} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<IRelationship> getIncomingRelationships(ISubject subject);

    /**
     * <p>
     * getOutgoingRelationships.
     * </p>
     *
     * @param subject a {@link org.integratedmodelling.klab.api.observations.ISubject} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<IRelationship> getOutgoingRelationships(ISubject subject);

    /**
     * <p>
     * getConfigurations.
     * </p>
     *
     * @return a {@link java.util.Map} object.
     */
    Map<IConcept, IConfiguration> getConfigurations();

    /**
     * Observe the passed observable in this context, returning an asynchronous future that will
     * produce an observation. This is the primary, asynchronous context observation method for the
     * k.LAB modeling API. Interactive sessions and applications use the {@link ISessionState} to
     * organize and schedule observations. When this method is used, the task returned is only
     * started when get() or start() are called on it manually, so scenarios and listeners can be
     * added to the task after it is returned.
     * 
     * @param observable an observable that is compatible with this subject's semantics as its
     *        context.
     * @return a paused task to optionally configure and use by calling get() or start().
     */
    ITask<IObservation> observe(IObservable observable);

    /*
     * ----------------------------------------------------------------------------- ---------
     * Runtime functions below
     * ----------------------------------------------------------------------------- ---------
     */

//    /**
//     * Call this on the root observation to create the logical peers of an observation tree in the
//     * passed ontology.
//     *
//     * @param ontology an ontology, usually empty (may be created with
//     *        {@link org.integratedmodelling.klab.api.services.IOntologyService#require(String)}).
//     * @return the individual corresponding to this subject.
//     */
//    IIndividual instantiate(IOntology ontology);

}

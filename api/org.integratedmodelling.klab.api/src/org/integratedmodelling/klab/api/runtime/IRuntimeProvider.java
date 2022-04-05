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

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IRuntimeProvider. Responsible for most runtime tasks like creating observations,
 * computing dataflows and defining any contextualizers used internally to access resources or
 * mediate values. Instead of returning the actual contextualizers, returns function calls that can
 * be encoded in k.DL to be executed by dataflows, so that dataflows can be serialized, loaded and
 * run when needed.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IRuntimeProvider {

    /**
     * The main executor for a k.LAB dataflow. Each call returns a new Future artifact. The
     * scheduler should be created and run automatically in here if needed.
     *
     * @param dataflow the dataflow to run
     * @param scale the scale in which to compute
     * @param scope the resolution scope for the computation
     * @param context the context observation for the computation. Can be null.
     * @param monitor the monitor with the identity carrying out the computation
     * @return a future that is computing the final artifact for the actuator.
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    Future<IArtifact> compute(IDataflow<? extends IArtifact> dataflow, IScale scale, IContextualizationScope scope)
            throws KlabException;

    /**
     * Get a service call that, once executed, will turn the passed specification for a resource
     * into a suitable contextualizer that runs on this runtime.
     *
     * @param resource a {@link org.integratedmodelling.kim.api.IContextualizable} object.
     * @param observable
     * @param session
     * 
     * @return the service call encoding the resource
     */
    IServiceCall getServiceCall(IContextualizable resource, IObservable observable, ISession session);

    /**
     * Distribute the computation of the passed state resolver over the passed scale.
     *
     * @param resolver the state contextualizer, which will be called as many times as scale.size().
     * @param mainTarget the observation being computed. This may be a state if the computation is
     *        resolving one, but also a process where the specific contextualizable targets a state.
     *        According to the context of computation, the state may or may not contain initialized
     *        values.
     * @param resource the computation to be distributed, which has generated the resolver. This is
     *        passed because in case the main target is a process, it should be used to retrieve the
     *        state that is the ultimate target of the computation.
     * @param scope the context before distribution - i.e., all states in it will be whole states
     *        and need to be contextualized to each extent before computation happens (the resolver
     *        expects individual values at each call). The current version of the target artifact
     *        will be set in it as 'self' if it exists.
     * @param scale the scale, already set to the geometry needed for this computation so that all
     *        of its states are computed.
     * 
     * @return the computed result - return the same object passed as main target whenever possible.
     *         If a different one is collected, the original one will be garbage collected. If the
     *         passed observation is a process (change), return the process, not the resource target
     *         state.
     * 
     * @throws org.integratedmodelling.klab.exceptions.KlabException
     */
    IObservation distributeComputation(IStateResolver resolver, IObservation mainTarget, IContextualizable resource,
            IContextualizationScope scope, ILocator locator) throws KlabException;

    /**
     * The "empty" observation must contain the observable and the scale. It is returned when an
     * instantiator is run with no error and produces no instances, to add to the context notifying
     * that no instances were produced but the observation was made. The observation must adhere to
     * the contract of an empty artifact, i.e. its
     * {@link org.integratedmodelling.klab.api.provenance.IArtifact#isEmpty()} method must return
     * true and it must produce no artifacts when iterated.
     *
     * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
     * @param scope context for the observation, which must be correct.
     * @return a {@link org.integratedmodelling.klab.api.observations.IObservation} object.
     */
    IObservation createEmptyObservation(IObservable observable, IContextualizationScope scope);

    /**
     * Create a state to be used for intermediate computations or temporary storage of the specified
     * type. The state should not be registered with the context or the provenance; such details are
     * left to the logics that use it. The independently installed {@link IStorageProvider storage
     * provider} should be used to produce the state storage.
     * 
     * @param observable
     * @param type
     * @param scale
     * @param scope
     * @return a new state
     */
    IState createState(IObservable observable, IArtifact.Type type, IScale scale, IContextualizationScope scope);

    /**
     * If the runtime provides a computation that can turn the passed artifact type into the desired
     * observation, return it. Otherwise return {@code null}. This is used for indirect
     * computations: the usual requests from the default resolver are {@link Type#COUNTABLE} to
     * {@link Type#DISTANCE}, {@link Type#PRESENCE} and {@link Type#NUMEROSITY} when the context of
     * computation is compatible with their resolution.
     * 
     * @param availableType the type of the alternative observable we have
     * @param resolutionMode the mode of the desired resolution. Always RESOLUTION this far.
     * @param desiredObservation the type of the observable we want to obtain
     * @return null (not an empty list) if this computation cannot be done; otherwise the list of
     *         needed computations, possibly empty. The empty list will be interpreted as "no
     *         computation needed", not as "no strategy found".
     */
    List<IContextualizable> getComputation(IObservable availableType, IResolutionScope.Mode resolutionMode,
            IObservable desiredObservation);

    /**
     * Runtime systems may create thread pools and memory mapped files. This method will be called
     * at system shutdown so that those may be cleaned up.
     */
    void shutdown();

    /**
     * If the source type can be cast to the target type, return a resolver that will perform the
     * cast.
     * 
     * @param sourceType
     * @param targetType
     * @return a resolver or null
     */
    IContextualizable getCastingResolver(IArtifact.Type sourceType, IArtifact.Type targetType);

    /**
     * Return a resolver that will collect the artifacts of the passed distributing concept, find
     * their dependent artifact of type inherent, and produce a merged artifact collecting them in
     * the parent context. This is meant to support "trans-reification" (if there is such a word),
     * meaning producing an observation that collects the values of observations made after
     * distributing their observables over their legitimate context, such as "height of tree within
     * region".
     * 
     * @param distributingType the type of the artifacts over which the inherent type is distributed
     * @param inherentType the type of the inherent artifacts to collect
     * @param targetType the type of the resulting dereified observation
     * @return
     */
    IContextualizable getDereifyingResolver(IConcept distributingType, IConcept inherentType, IArtifact.Type targetType);

    /**
     * Return a computation that will apply the passed operator and operand to transform a state as
     * requested.
     * 
     * @param classifiedObservable the quality observable
     * @param operator
     * @param operand
     * @return
     */
    IContextualizable getOperatorResolver(IObservable classifiedObservable, ValueOperator operator, Object operand,
            Set<ValueOperator> modifiers);

    /**
     * Return a process computation that will pick the appropriate temporal slice from a resource to
     * resolve the passed change process by contextualizing the appropriate values in the changing
     * observable.
     * 
     * @param changeObservable
     * @param mergedResource
     * @return
     * @deprecated must use the regular resource contextualization mechanism and just compile in an
     *             urn resolver
     */
    IContextualizable getChangeResolver(IObservable changeObservable, IResource mergedResource);

    /**
     * Return a void contextualizer that will resolve a view and send the compiled results to the
     * monitor.
     * 
     * @param view
     * @return
     */
    IContextualizable getViewResolver(IViewModel view);

}

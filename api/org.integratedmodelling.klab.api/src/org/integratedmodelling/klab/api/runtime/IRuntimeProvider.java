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

import java.util.List;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IRuntimeProvider.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IRuntimeProvider {

	/**
	 * The main executor for a k.LAB dataflow. Each call returns a new Future
	 * artifact.
	 *
	 * @param actuator a top-level actuator that has no dependencies on external
	 *                 ones.
	 * @param dataflow the dataflow to which the actuator belongs
	 * @param scale    the scale in which to compute
	 * @param scope    the resolution scope for the computation
	 * @param context  the context observation for the computation. Can be null.
	 * @param monitor  the monitor with the identity carrying out the computation
	 * @return a future that is computing the final artifact for the actuator.
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	Future<IArtifact> compute(IActuator actuator, IDataflow<? extends IArtifact> dataflow, IScale scale,
			IResolutionScope scope, IDirectObservation context, IMonitor monitor) throws KlabException;

	/**
	 * Create an empty runtime context for the dataflow that will build the context
	 * subject. The context will also create the subject itself according to the
	 * runtime's expectations.
	 *
	 * @param actuator a
	 *                 {@link org.integratedmodelling.klab.api.runtime.dataflow.IActuator}
	 *                 object.
	 * @param scope    a
	 *                 {@link org.integratedmodelling.klab.api.resolution.IResolutionScope}
	 *                 object.
	 * @param scale    the scale for the contextualization (must be compatible with
	 *                 scope.getScale() but can be different)
	 * @param monitor  a
	 *                 {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor}
	 *                 object.
	 * @return a new runtime context.
	 */
	IContextualizationScope createRuntimeContext(IActuator actuator, IResolutionScope scope, IScale scale,
			IMonitor monitor);

	/**
	 * Get a service call that, once executed, will turn the passed specification
	 * for a resource into a suitable contextualizer that runs on this runtime.
	 *
	 * @param resource a {@link org.integratedmodelling.kim.api.IComputableResource}
	 *                 object.
	 * @param actuator the actuator providing the context for the computation.
	 * 
	 * @return the service call encoding the resource
	 */
	IServiceCall getServiceCall(IComputableResource resource, IActuator actuator);

	/**
	 * Distribute the computation of the passed state resolver over the passed
	 * scale.
	 *
	 * @param resolver the state contextualizer, which will be called as many times
	 *                 as scale.size().
	 * @param data     the data being computed (receiver of results). According to
	 *                 the context of computation it may or may not contain
	 *                 initialized values.
	 * @param context  the context before distribution - i.e., all states in it will
	 *                 be whole states and need to be contextualized to each extent
	 *                 before computation happens (the resolver expects individual
	 *                 values at each call). The current version of the target
	 *                 artifact will be set in it as 'self' if it exists.
	 * @param scale    the scale, already set to the geometry needed for this
	 *                 computation so that all of its states are computed.
	 * 
	 * @return the computed result - return the same object passed as data whenever
	 *         possible. If a different one is collected, the original one will be
	 *         garbage collected.
	 * 
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 */
	IDataArtifact distributeComputation(IStateResolver resolver, IState data, IContextualizationScope context,
			ILocator locator) throws KlabException;

	/**
	 * The "empty" observation must contain the observable and the scale. It is
	 * returned when an instantiator is run with no error and produces no instances,
	 * to add to the context notifying that no instances were produced but the
	 * observation was made. The observation must adhere to the contract of an empty
	 * artifact, i.e. its
	 * {@link org.integratedmodelling.klab.api.provenance.IArtifact#isEmpty()}
	 * method must return true and it must produce no artifacts when iterated.
	 *
	 * @param observable a
	 *                   {@link org.integratedmodelling.klab.api.knowledge.IObservable}
	 *                   object.
	 * @param context    context for the observation, which must be correct.
	 * @return a {@link org.integratedmodelling.klab.api.observations.IObservation}
	 *         object.
	 */
	IObservation createEmptyObservation(IObservable observable, IContextualizationScope context);

	/**
	 * Create a state to be used for intermediate computations or temporary storage
	 * of the specified type. The state should not be registered with the context or
	 * the provenance; such details are left to the logics that use it. The
	 * independently installed {@link IStorageProvider storage provider} should be
	 * used to produce the state storage.
	 * 
	 * @param observable
	 * @param type
	 * @param scale
	 * @param context
	 * @return a new state
	 */
	IState createState(IObservable observable, IArtifact.Type type, IScale scale, IContextualizationScope context);

	/**
	 * If the runtime provides a computation that can turn the passed artifact type
	 * into the desired observation, return it. Otherwise return {@code null}. This
	 * is used for indirect computations: the usual requests from the default
	 * resolver are {@link Type#COUNTABLE} to {@link Type#DISTANCE},
	 * {@link Type#PRESENCE} and {@link Type#NUMEROSITY} when the context of
	 * computation is compatible with their resolution.
	 * 
	 * @param availableType      the type of the alternative observable we have
	 * @param resolutionMode     the mode of the desired resolution. Always
	 *                           RESOLUTION this far.
	 * @param desiredObservation the type of the observable we want to obtain
	 * @return null (not an empty list) if this computation cannot be done;
	 *         otherwise the list of needed computations, possibly empty. The empty
	 *         list will be interpreted as "no computation needed", not as "no
	 *         strategy found".
	 */
	List<IComputableResource> getComputation(IObservable availableType, IResolutionScope.Mode resolutionMode,
			IObservable desiredObservation);

	/**
	 * Runtime systems may create thread pools and memory mapped files. This method
	 * will be called at system shutdown so that those may be cleaned up.
	 */
	void shutdown();

	/**
	 * If the source type can be cast to the target type, return a resolver that
	 * will perform the cast.
	 * 
	 * @param sourceType
	 * @param targetType
	 * @return a resolver or null
	 */
	IComputableResource getCastingResolver(IArtifact.Type sourceType, IArtifact.Type targetType);

	/**
	 * Return a computation that will apply the passed operator and operand to
	 * transform a state as requested.
	 * 
	 * @param classifiedObservable the quality observable
	 * @param operator
	 * @param operand
	 * @return
	 */
	IComputableResource getOperatorResolver(IObservable classifiedObservable, ValueOperator operator, Object operand);

	/*
	 * Called on a computation returned by getComputation() to change the target ID
	 * after creation. FIXME this is ugly and unstable - needs a different logic and
	 * removal
	 * 
	 * @param resource
	 * 
	 * @param targetId
	 */
	void setComputationTargetId(IComputableResource resource, String targetId);

}

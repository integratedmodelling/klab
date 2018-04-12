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
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IRuntimeProvider.
 */
public interface IRuntimeProvider {

  /**
   * The main executor for a k.LAB dataflow. Each call returns a new Future that has been started.
   * 
   * @param actuator a top-level actuator that has no dependencies on external ones.
   * @param context an appropriate context for the computation (see
   *        {@link #createRuntimeContext(IActuator, IResolutionScope, IScale, IMonitor)}) containing
   *        the target observation.
   * @param monitor
   * @return a future that is computing the final artifact for the actuator.
   * @throws KlabException
   */
  Future<IArtifact> compute(IActuator actuator, IComputationContext context) throws KlabException;

  /**
   * Create an empty runtime context for the dataflow that will build the context subject. The
   * context will also create the subject itself according to the runtime's expectations.
   * 
   * @param actuator
   * @param scope
   * @param scale the scale for the contextualization (must be compatible with scope.getScale() but
   *        can be different)
   * @param monitor
   * 
   * @return a new runtime context.
   */
  IComputationContext createRuntimeContext(IActuator actuator, IResolutionScope scope, IScale scale,
      IMonitor monitor);

  /**
   * Get a service call that, once executed, will turn the passed specification for a resource into
   * a suitable contextualizer that runs on this runtime.
   * 
   * @param resource
   * @return the service call encoding the resource
   */
  IServiceCall getServiceCall(IComputableResource resource);

  /**
   * Distribute the computation of the passed state resolver over the passed scale.
   * 
   * @param resolver the state contextualizer, which will be called as many times as scale.size().
   * @param data the data being computed (receiver of results). According to the context of
   *        computation it may or may not contain initialized values.
   * @param context the context before distribution - i.e., all states in it will be whole states
   *        and need to be contextualized to each extent before computation happens (the resolver
   *        expects individual values at each call).
   * @param scale the scale, already set to the slice needed for this computation
   * @return the computed result - return the same object passed as data whenever possible. If a
   *         different one is collected, the original one will be garbage collected.
   * @throws KlabException
   */
  IDataArtifact distributeComputation(IStateResolver resolver, IState data, IComputationContext context,
      IScale scale) throws KlabException;

  /**
   * The "empty" observation must contain the observable and the scale. It is returned when an
   * instantiator is run with no error and produces no instances, to add to the context notifying
   * that no instances were produced but the observation was made. The observation must adhere to
   * the contract of an empty artifact, i.e. its {@link IArtifact#isEmpty()} method must return true
   * and it must produce no artifacts when iterated.
   * 
   * @param observable
   * @param scale
   * @return
   */
  IObservation createEmptyObservation(IObservable observable, IScale scale);

  /**
   * Return the computable that will merge the artifacts produced by the passed models, each
   * indicated by the model ID it is paired with. Normally a service call implemented in the runtime
   * provider. 
   * 
   * @param observable the observable corresponding to the artifact produced and passed
   * @param modelIds the IDs that will identify the artifacts when the function is called.
   * @return a computable that will merge artifacts over their different coverages.
   */
  IComputableResource getMergeArtifactServiceCall(IObservable observable, List<String> modelIds);

}

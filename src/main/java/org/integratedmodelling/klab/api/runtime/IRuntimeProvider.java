package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import javax.annotation.Nullable;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IRuntimeProvider {

  /**
   * The main executor for a k.LAB dataflow. Each call returns a new Future that has been started.
   * 
   * @param actuator a top-level actuator that has no dependencies on external ones.
   * @param context an appropriate context for the computation (see {@link #createRuntimeContext()})
   * @param monitor
   * @return a future that is computing the final artifact for the actuator.
   * @throws KlabException
   */
  Future<IObservationData> compute(IActuator actuator, IComputationContext context,
      IMonitor monitor) throws KlabException;

  /**
   * Create an empty runtime context for the dataflow that will build the context subject.
   * 
   * @param rootSubject
   * @return a new runtime context.
   */
  IComputationContext createRuntimeContext();

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
   */
  IStorage<?> distributeComputation(IStateResolver resolver, IStorage<?> data,
      IRuntimeContext context, IScale scale);

}

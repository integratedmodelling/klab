package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
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
  Future<IArtifact> compute(IActuator actuator, IComputationContext context, IMonitor monitor)
      throws KlabException;

  /**
   * Create an empty runtime context for the dataflow that will build the context subject.
   * 
   * @param rootSubject
   * @return a new runtime context.
   */
  IComputationContext createRuntimeContext();

  /**
   * Get a service call that, once executed, will turn the passed specification for a resource into a
   * suitable contextualizer that runs on this runtime.
   * 
   * @param resource
   * @return
   */
  IServiceCall getServiceCall(IComputableResource resource);

}

package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

/**
 * A ITask computes an observational artifact, delegating to a Java Future that returns it when
 * available. If the task is observing a countable concept, the returned observation will be their
 * context, and the new observations made will be available through {@link #getObservations()}.
 * 
 * @author ferdinando.villa
 * @param <T> the type of observation being resolved
 *
 */
public interface ITask<T extends IObservation> extends ITaskIdentity, Future<T> {

  /**
   * Return the dataflow built to resolve the observable. This should only be called after the
   * dataflow notification has been sent to the task monitor.
   * 
   * @return the dataflow that is resolving the observable.
   */
  IDataflow getDataflow();

}

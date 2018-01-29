package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * A ITask computes an observation, delegating to a Java Future that returns
 * it when available.
 * 
 * @author ferdinando.villa
 * @param <T> the specific type of IObservation that will be observed by this task.
 *
 */
public interface ITask<T extends IObservation> extends ITaskIdentity, Future<T> {

}

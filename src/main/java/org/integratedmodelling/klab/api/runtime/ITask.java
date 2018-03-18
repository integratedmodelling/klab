package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * A ITask computes an observational artifact, delegating to a Java Future that returns it when
 * available.
 * 
 * @author ferdinando.villa
 * @param <T> the type of observation being resolved
 *
 */
public interface ITask<T extends IObservation> extends ITaskIdentity, Future<T> {
  
}

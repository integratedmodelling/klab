package org.integratedmodelling.klab.engine.runtime;

import java.util.concurrent.Callable;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.engine.Engine.Monitor;

/**
 * Utility class implementing {@link Callable} with automatic notification
 * of start, stop and exceptions.
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public abstract class MonitoredCallable<T> implements Callable<T> {

  IRuntimeIdentity task;

  public MonitoredCallable(IRuntimeIdentity task) {
    this.task = task;
  }

  @Override
  public final T call() throws Exception {
    
    ((Monitor) task.getMonitor()).notifyStart();
    T result = null;
    try {
      result = run();
    } catch (Throwable e) {
      task.getMonitor().error(e);
      throw e;
    } finally {
      ((Monitor) task.getMonitor()).notifyEnd();
    }
      
    return result;
  }

  public abstract T run() throws Exception;

}

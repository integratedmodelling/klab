package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.observation.Subject;

/**
 * A ITask that creates a root subject within a ISession.
 * 
 * @author ferdinando.villa
 *
 */
public class RootTask implements ITask<ISubject> {

  Monitor          monitor;
  Subject          subject;
  Future<ISubject> delegate;

  public RootTask(Session session, IObserver urn) {
    
    Engine engine = session.getParent(Engine.class);
    
    // TODO put all this in a runnable (maybe subject creation can be outside for
    // consistency, as it should be fast)
    // TODO create the subject according to semantics
    // TODO create resolver delegate and execute it
    
  }

  @Override
  public String getToken() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean is(Type type) {
    return type == Type.TASK;
  }

  @Override
  public <T extends IIdentity> T getParent(Class<T> type) {
      return IIdentity.findParent(this, type);
  }
  
  @Override
  public IObservationIdentity getParentIdentity() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return delegate.cancel(mayInterruptIfRunning);
  }

  @Override
  public boolean isCancelled() {
    return delegate.isCancelled();
  }

  @Override
  public boolean isDone() {
    return delegate.isDone();
  }

  @Override
  public ISubject get() throws InterruptedException, ExecutionException {
    return delegate.get();
  }

  @Override
  public ISubject get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return delegate.get(timeout, unit);
  }

  @Override
  public IDataflow getDataflow() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<ISubject> getObservations() {
    return Collections.singleton(subject);
  }

}

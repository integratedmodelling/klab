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
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
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
  public <I extends IIdentity> I getParentIdentity(Class<? extends IIdentity> type) {
    // TODO Auto-generated method stub
    return null;
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
  public Collection<IObservation> getObservations() {
    return Collections.singleton(subject);
  }

}

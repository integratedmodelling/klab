package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.observation.Observation;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * A ITask that creates one or more Observations within a context subject.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveInContextTask implements ITask<IObservation> {

  Monitor     monitor;
  Observation context;
  Future<IObservation>   delegate;
  String id = NameGenerator.shortUUID();

  public ObserveInContextTask(Subject context, String urn) {
    this.context = context;
    this.monitor = context.getRoot().getMonitor().get(this);
    // TODO create resolver delegate and execute it
  }

  @Override
  public String getToken() {
    return id;
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
    return context;
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
  public IObservation get() throws InterruptedException, ExecutionException {
    return delegate.get();
  }

  @Override
  public IObservation get(long timeout, TimeUnit unit)
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
    // TODO Auto-generated method stub
    return null;
  }

}

package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IObservationIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * A ITask that creates one or more Observations within a context subject.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveInContextTask implements ITask<IObservation> {

  Monitor                  monitor;
  Subject                  context;
  FutureTask<IObservation> delegate;
  String                   token = NameGenerator.shortUUID();

  public ObserveInContextTask(Subject context, String urn) {
    this.context = context;
    this.monitor = context.getRoot().getMonitor().get(this);
    delegate = new FutureTask<IObservation>(new MonitoredCallable<IObservation>(this) {

      @Override
      public IObservation run() throws Exception {

        ResolutionScope scope = new ResolutionScope(context);
        
        /*
         * obtain the resolvable object corresponding to the URN - either 
         * a concept or a model
         */
        
        /*
         * resolve it appropriately
         */
        
//        if (Resolver.INSTANCE.resolve(ret, scope).isRelevant()) {
//          engine.run(Dataflows.INSTANCE.compile(scope));
//        }

        /*
         * instantiation returns the context (FIXME ok?), resolution the new observation
         */
        return null;
      }
    });

    context.getParent(Engine.class).getTaskExecutor().execute(delegate);
  }

  @Override
  public String getToken() {
    return token;
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
    return context.getRoot();
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

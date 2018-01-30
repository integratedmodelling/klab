package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observations;
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
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * A ITask that creates a root subject within a Session.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveContextTask implements ITask<ISubject> {

  Monitor              monitor;
  Subject              subject;
  FutureTask<ISubject> delegate;
  String               id = NameGenerator.shortUUID();

  public ObserveContextTask(Session session, IObserver observer) {

    Engine engine = session.getParent(Engine.class);
    this.monitor = (session.getMonitor()).get(this);

    delegate = new FutureTask<ISubject>(new Callable<ISubject>() {

      @Override
      public ISubject call() throws Exception {

        Subject ret = Observations.INSTANCE.createSubject(observer, monitor);
        ResolutionScope scope = new ResolutionScope(ret);
        if (Resolver.INSTANCE.resolve(ret, scope).isRelevant()) {
            engine.run(Dataflows.INSTANCE.compile(scope));
        }
        
        return ret;
      }
    });

    engine.getTaskExecutor().execute(delegate);


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
    return subject;
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

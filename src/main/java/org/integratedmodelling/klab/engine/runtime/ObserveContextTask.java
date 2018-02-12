package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.model.Observer;
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
  Dataflow             dataflow;
  FutureTask<ISubject> delegate;
  String               token = NameGenerator.shortUUID();
  Session              session;

  public ObserveContextTask(Session session, Observer observer, Collection<String> scenarios) {

    Engine engine = session.getParent(Engine.class);
    try {

      this.monitor = (session.getMonitor()).get(this);
      this.session = session;
      delegate = new FutureTask<ISubject>(new MonitoredCallable<ISubject>(this) {

        @Override
        public ISubject run() throws Exception {

          ResolutionScope scope = Resolver.INSTANCE.resolve(observer, monitor, scenarios);
          if (scope.isRelevant()) {
            dataflow = Dataflows.INSTANCE.compile(scope);
            subject = (Subject) engine.run(dataflow);
          }

          return subject;
        }
      });

      engine.getTaskExecutor().execute(delegate);
    } catch (Throwable e) {
      monitor.error("error initializing context task: " + e.getMessage());
    }
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
  public IEngineSessionIdentity getParentIdentity() {
    return session;
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
  public Dataflow getDataflow() {
    return dataflow;
  }

}

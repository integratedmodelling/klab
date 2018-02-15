package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.resolution.IResolvable;
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
 * A ITask that creates one or more Observations within a context subject.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveInContextTask implements ITask<IObservation> {

  Monitor                  monitor;
  Subject                  context;
  FutureTask<IObservation> delegate;
  Session                  session;
  String                   token           = NameGenerator.shortUUID();
  String[]                 scenarios;
  String                   taskDescription =
      "<uninitialized contextual observation task " + token + ">";

  public ObserveInContextTask(Subject context, String urn, Collection<String> scenarios) {

    this.context = context;
    this.monitor = context.getRoot().getMonitor().get(this);
    this.session = context.getParent(Session.class);
    this.taskDescription =
        "<task " + token + ": observation of " + urn + " within " + context + ">";

    delegate = new FutureTask<IObservation>(new MonitoredCallable<IObservation>(this) {

      @Override
      public IObservation run() throws Exception {

        IObservation ret = null;
        /*
         * obtain the resolvable object corresponding to the URN - either a concept or a model
         */
        IResolvable resolvable = Resources.INSTANCE.getResolvableResource(urn);

        if (resolvable == null) {
          // TODO should be an empty artifact
          return null;
        }

        /*
         * resolve and run
         */
        ResolutionScope scope = Resolver.INSTANCE.resolve(resolvable,
            ResolutionScope.create(context, monitor, scenarios));
        if (scope.isRelevant()) {
          ret = Dataflows.INSTANCE
              .compile(scope, Observables.INSTANCE.getObservationClass(resolvable)).run(monitor);
        }

        return ret;
      }
    });

    context.getParent(Engine.class).getTaskExecutor().execute(delegate);
  }

  public String toString() {
    return taskDescription;
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
}

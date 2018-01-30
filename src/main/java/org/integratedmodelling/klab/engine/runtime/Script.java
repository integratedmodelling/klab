package org.integratedmodelling.klab.engine.runtime;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NameGenerator;

public class Script implements IScript {

  URL                scriptUrl;
  FutureTask<Object> delegate;
  IMonitor           monitor;
  Session            session;
  String token = NameGenerator.shortUUID();

  public Script(Engine engine, URL resource) {

    this.scriptUrl = resource;
    delegate = new FutureTask<Object>(new Callable<Object>() {

      @Override
      public Object call() throws Exception {

        Object ret = null;
        try (Session session = engine.createSession()) {
          Script.this.session = session;
          Script.this.monitor = (session.getMonitor()).get(Script.this);
          /* ret = */ Models.INSTANCE.load(resource, monitor);
        } catch (Exception e) {
          throw e instanceof KlabException ? (KlabException) e : new KlabException(e);
        }
        return ret;
      }
    });

    engine.getScriptExecutor().execute(delegate);
  }

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public boolean is(Type type) {
    return type == Type.SCRIPT;
  }

  @Override
  public <T extends IIdentity> T getParent(Class<T> type) {
    return IIdentity.findParent(this, type);
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
  public Object get() throws InterruptedException, ExecutionException {
    return delegate.get();
  }

  @Override
  public Object get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return delegate.get(timeout, unit);
  }

  @Override
  public IEngineSessionIdentity getParentIdentity() {
    return session;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

}

package org.integratedmodelling.klab.engine.runtime;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;

public class Script implements IScript {

    URL scriptUrl;
    FutureTask<ISubject> delegate;
    IMonitor monitor;
    Session session;
    String token = NameGenerator.shortUUID();
    IParameters<String> globalState = Parameters.create();

    @Override
    public IParameters<String> getState() {
        return globalState;
    }

    public Script(Session session, URL resource) {

        this.scriptUrl = resource;
        final Engine engine = session.getParentIdentity(Engine.class);
        delegate = new FutureTask<ISubject>(new Callable<ISubject>(){

            @Override
            public ISubject call() throws Exception {

                /*
                 * Unregister the task
                 */
                session.unregisterTask(Script.this);

                ISubject ret = null;
                try {
                    ListenerImpl listener = new ListenerImpl();
                    Script.this.session = session;
                    Script.this.monitor = (session.getMonitor()).get(Script.this);
                    ((Monitor) Script.this.monitor).addListener(listener);
                    Models.INSTANCE.load(resource, Script.this.monitor);
                    /*
                     * Unregister the task
                     */
                    session.unregisterTask(Script.this);
                    ret = listener.subject;

                } catch (Exception e) {
                    throw e instanceof KlabException ? (KlabException) e : new KlabException(e);
                }
                return ret;
            }
        });

        engine.getScriptExecutor().execute(delegate);
    }

    public Script(Engine engine, URL resource) {

        this.scriptUrl = resource;
        delegate = new FutureTask<ISubject>(new Callable<ISubject>(){

            @Override
            public ISubject call() throws Exception {

                ISubject ret = null;
                try (Session session = engine.createSession()) {
                    ListenerImpl listener = new ListenerImpl();
                    Script.this.session = session;
                    Script.this.monitor = (session.getMonitor()).get(Script.this);
                    ((Monitor) Script.this.monitor).addListener(listener);
                    Models.INSTANCE.load(resource, monitor);
                    ret = listener.subject;
                } catch (Exception e) {
                    throw e instanceof KlabException ? (KlabException) e : new KlabException(e);
                }
                return ret;
            }
        });

        engine.getScriptExecutor().execute(delegate);
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.SCRIPT;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.SCRIPT;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
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
    public ISubject get() throws InterruptedException, ExecutionException {
        return delegate.get();
    }

    @Override
    public ISubject get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
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

    class ListenerImpl implements IMonitor.Listener {
        ISubject subject;
        @Override
        public void notifyRootContext(ISubject subject) {
            this.subject = subject;
        }
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

}

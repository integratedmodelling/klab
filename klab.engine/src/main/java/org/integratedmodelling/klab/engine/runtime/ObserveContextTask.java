package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.AbstractRuntimeScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * A ITask that creates a root subject within a Session.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveContextTask extends AbstractTask<IArtifact> {

    FutureTask<ISubject> delegate;
    String taskDescription = "<uninitialized observation task " + token + ">";
    IParameters<String> globalState = Parameters.create();
    AtomicReference<ISubject> result = new AtomicReference<>();
    AtomicBoolean disaster = new AtomicBoolean(Boolean.FALSE);

    @Override
    public IParameters<String> getState() {
        return globalState;
    }

    public ObserveContextTask(ObserveContextTask parent, String description) {
        super(parent);
        this.delegate = parent.delegate;
        this.taskDescription = description;
        this.autostart = parent.autostart;
    }

    /**
     * Use to control autostart sequencing so that listeners and scenarios may be added before
     * calling start().
     * 
     * @param session
     * @param observer
     * @param autostart
     * @return
     */
    public static ObserveContextTask create(Session session, Acknowledgement observer, boolean autostart) {
        return new ObserveContextTask(session, observer, null, autostart);
    }

    private ObserveContextTask(Session session, Acknowledgement observer, Collection<String> scenarios, boolean autostart) {
        this(session, observer, session, scenarios, null, null, session.getParentIdentity(Engine.class).getTaskExecutor(), null,
                autostart);
    }

    public ObserveContextTask(Session session, Acknowledgement observer, Collection<String> scenarios) {
        this(session, observer, session, scenarios, null, null, session.getParentIdentity(Engine.class).getTaskExecutor(), null,
                true);
    }

    @Override
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.TASK;
    }

    /**
     * Listener consumers are called as things progress. The observation listener is first called
     * with null as a parameter when starting, then (if no error occurs) another time with the
     * observation as argument. The observation may be empty. If an exception is thrown, the error
     * listener is called with the exception as argument.
     * 
     * @param session
     * @param observer
     * @param scenarios
     * @param observationListener
     * @param errorListener
     */
    public ObserveContextTask(Session session, Acknowledgement observer, IObserver<?> observingAgent,
            Collection<String> scenarioList, Collection<BiConsumer<ITask<?>, IArtifact>> oListeners,
            Collection<BiConsumer<ITask<?>, Throwable>> eListeners, Executor executor, SessionActivity activityDescriptor,
            boolean autostart) {

        try {

            this.monitor = (session.getMonitor()).get(this);
            this.session = session;
            this.taskDescription = "Observation of " + observer.getId();
            this.resolvable = observer;
            this.executor = executor;
            this.autostart = autostart;

            if (scenarioList != null && !scenarioList.isEmpty()) {
                this.scenarios.addAll(scenarioList);
            }

            if (oListeners != null) {
                observationListeners.addAll(oListeners);
            }
            if (eListeners != null) {
                errorListeners.addAll(eListeners);
            }

            session.touch();

            delegate = new FutureTask<ISubject>(new MonitoredCallable<ISubject>(this){

                @Override
                public ISubject run() throws Exception {

                    ISubject ret = null;

                    try {

                        /*
                         * register the task so it can be interrupted and inquired about
                         */
                        notifyStart();

                        if (observationListeners != null) {
                            for (BiConsumer<ITask<?>, IArtifact> observationListener : observationListeners) {
                                observationListener.accept((ITask<?>) this.task, null);
                            }
                        }

                        ResolutionScope scope = Resolver.create(null).resolve(observer, monitor, scenarios);
                        /*
                         * create the root contextualization scope for the context
                         */
                        IRuntimeScope runtimeScope = RuntimeScope.rootScope(scope, observingAgent);

                        runtimeScope.getStatistics().defineTarget(observer);
                        
                        if (scope.getCoverage().isRelevant()) {

                            Dataflow dataflow = Dataflows.INSTANCE
                                    .compile("local:task:" + session.getId() + ":" + token, scope, null).setPrimary(true);

                            dataflow.setDescription(taskDescription);

                            IRuntimeScope ctx = runtimeScope.getChild(ObserveContextTask.this);
                            
                            /*
                             * make a copy of the coverage so that we ensure it's a scale, behaving
                             * properly at merge. FIXME this must be the entire scale now - each
                             * actuator creates its artifacts, then initialization is handled when
                             * computing.
                             */
                            ret = (ISubject) dataflow.run(scope.getCoverage().asScale().copy(), ctx);

                            if (ret != null) {

                                ((AbstractRuntimeScope) runtimeScope).setRootDataflow(dataflow, ret.getId());
                                ((AbstractRuntimeScope) runtimeScope).notifyDataflowChanges(ctx);

                                setContext((Subject) ret);
                                getDescriptor().setContextId(ret.getId());
                                /*
                                 * load any behaviors and schedule repeating actions
                                 */
                                Actors.INSTANCE.instrument(observer.getAnnotations(), (Observation) ret);

                                /*
                                 * Register the observation context with the session. It will be
                                 * disposed of and/or persisted by the session itself.
                                 */
                                session.registerObservationContext(((Observation) ret).getScope());

                                /*
                                 * tell the scope to notify internal listeners (for actors and the
                                 * like)
                                 */
                                ((Observation) ret).getScope().notifyListeners((IObservation) ret);

                                runtimeScope.getStatistics().notifyContextCreated(ret);
                            }


                            ctx.getStatistics().success();
                            
                            runtimeScope.getStatistics().success();
                            
                            Klab.INSTANCE.addActivity(scope.getSession(), runtimeScope.getStatistics());
                            
                            getActivity().finished();

                        }

                        if (observationListeners != null) {
                            for (BiConsumer<ITask<?>, IArtifact> observationListener : observationListeners) {
                                observationListener.accept((ITask<?>) this.task, ret);
                            }
                        }

                        notifyEnd();

                    } catch (Throwable e) {

                        if (errorListeners != null) {
                            for (BiConsumer<ITask<?>, Throwable> errorListener : errorListeners) {
                                errorListener.accept((ITask<?>) this.task, e);
                            }
                        }

                        disaster.set(true);

                        throw notifyAbort(e);

                    }

                    result.set(ret);

                    return ret;
                }

            });

            if (autostart) {
                started = true;
                executor.execute(delegate);
            }

        } catch (Throwable e) {
            monitor.error("error initializing context task: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return taskDescription;
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.TASK;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public IIdentity getParentIdentity() {
        return parentTask == null ? session : parentTask;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        monitor.interrupt();
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
        if (!started) {
            start();
        }
        return delegate.get();
        // while (result.get() == null && !disaster.get()) {
        // Thread.sleep(400);
        // }
        // return disaster.get() ? null : result.get();
    }

    @Override
    public ISubject get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!started) {
            start();
        }

        return delegate.get(timeout, unit);
    }

    @Override
    public ITaskTree<IArtifact> createChild(String description) {
        return new ObserveContextTask(this, description);
    }

    @Override
    protected String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean stop() {
        if (started) {
            cancel(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean start() {
        if (autostart || monitor.isInterrupted()) {
            return false;
        }
        executor.execute(delegate);
        return true;
    }

}

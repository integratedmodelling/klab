package org.integratedmodelling.klab.engine.runtime;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.AbstractRuntimeScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ContextualizationNotification;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.graph.Graphs;

/**
 * A ITask that creates one or more Observations within a context subject. TODO add option to choose
 * when to start the observation and implement remaining task options and listeners.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveInContextTask extends AbstractTask<IArtifact> {

    FutureTask<IObservation> delegate;
    String taskDescription = "<uninitialized contextual observation task " + token + ">";
    IParameters<String> globalState = Parameters.create();

    @Override
    public IParameters<String> getState() {
        return globalState;
    }

    /**
     * Use to control the automatic start so that listeners and scenarios may be added before
     * calling start().
     * 
     * @param context
     * @param urn
     * @param scenarios
     * @param autostart
     * @return
     */
    public static ObserveInContextTask create(Subject context, String urn, boolean autostart) {
        return new ObserveInContextTask(context, urn, null, autostart);
    }

    private ObserveInContextTask(Subject context, String urn, Collection<String> scenarios, boolean autostart) {
        this(context, urn, scenarios, null, null, context.getParentIdentity(Engine.class).getTaskExecutor(), null, autostart);
    }

    public ObserveInContextTask(ObserveInContextTask parent, String description) {
        super(parent);
        this.delegate = parent.delegate;
        this.taskDescription = description;
    }

    public ObserveInContextTask(Subject context, String urn, Collection<String> scenarios) {
        this(context, urn, scenarios, null, null, context.getParentIdentity(Engine.class).getTaskExecutor(), null, true);
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
     * @param context
     * @param urn
     * @param scenarios
     * @param observationListener
     * @param errorListener
     */
    public ObserveInContextTask(Subject context, String urn, Collection<String> scenarioList,
            Collection<BiConsumer<ITask<?>, IArtifact>> oListeners, Collection<BiConsumer<ITask<?>, Throwable>> eListeners,
            Executor executor, SessionActivity activityDescriptor, boolean autostart) {

        this.context = context;
        this.monitor = context.getMonitor().get(this);
        this.session = context.getParentIdentity(Session.class);
        this.taskDescription = "Observation of {" + urn + "} in " + context.getName();
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

        delegate = new FutureTask<IObservation>(new MonitoredCallable<IObservation>(this){

            @Override
            public IObservation run() throws Exception {

                IObservation ret = null;

                try {

                    notifyStart();

                    if (observationListeners != null) {
                        for (BiConsumer<ITask<?>, IArtifact> observationListener : observationListeners) {
                            observationListener.accept((ITask<?>) this.task, null);
                        }
                    }

                    /*
                     * obtain the resolvable object corresponding to the URN - either a concept or a
                     * model
                     */
                    resolvable = Resources.INSTANCE.getResolvableResource(urn, context.getScale());
                    IObservable observable = Observable.promote(OWL.INSTANCE.getNothing());

                    if (resolvable instanceof IModel) {
                        resolvable = Observable.promote((IModel) resolvable);
                        observable = (IObservable) resolvable;
                    } else if (resolvable instanceof IViewModel) {
                        resolvable = Observable.promote((IViewModel) resolvable);
                        observable = (IObservable) resolvable;
                    }

                    if (resolvable == null) {
                        throw new KlabIllegalArgumentException("URN " + urn + " does not represent a resolvable entity");
                    }

                    /*
                     * resolve and run
                     */
                    ResolutionScope scope = Resolver.create(null).resolve(resolvable,
                            ResolutionScope.create(context, monitor, scenarios));

                    if (scope.getCoverage().isRelevant()) {

                        Dataflow dataflow = Dataflows.INSTANCE
                                .compile("local:task:" + session.getId() + ":" + token, scope, context.getScope().getActuator())
                                .setPrimary(parentTask == null);

                        dataflow.setDescription(taskDescription);

                        if (Configuration.INSTANCE.isEchoEnabled()) {
                            System.out.println(dataflow.getKdlCode());
                        }

                        RuntimeScope ctx = (RuntimeScope)((Observation) context).getScope().getChild(ObserveInContextTask.this);
                        ((AbstractRuntimeScope) ctx).notifyDataflowChanges(ctx);

                        ctx.getStatistics().defineTarget(urn, context);
                        
                        /*
                         * pass the first actuator in the father context as the parent for this
                         * resolution - this must be at primary level, i.e. the root is the first
                         * (and only) actuator in the root context
                         */
                        Actuator actuator = (Actuator) (ctx.getDataflow().getActuators().isEmpty()
                                ? null
                                : ctx.getDataflow().getActuators().get(0));
                        IArtifact result = dataflow.run(scope.getCoverage().asScale(), actuator, ctx);
                        if (result instanceof IObservation) {
                            ret = (IObservation) result;
                        } else {
                            ret = Observation.empty((IObservable) resolvable, ctx);
                        }

                        /*
                         * The actuator has sent this already, but we send the final artifact a
                         * second time to bring it to the foreground for the listeners
                         */
                        if (dataflow.isPrimary()) {

                            monitor.info("observation completed with "
                                    + NumberFormat.getPercentInstance().format(scope.getCoverage().getCoverage())
                                    + " context coverage");
                            
                            notifyEnd();
                            ctx.getStatistics().success();
                            
                            Klab.INSTANCE.addActivity(scope.getSession().getUser(), ctx.getStatistics());
                            
                        }

                        if (!Configuration.INSTANCE.getProperty("visualize", "false").equals("false")
                                && ctx.getProvenance().getSimplifiedGraph().vertexSet().size() > 1) {
                            Graphs.show(ctx.getProvenance().getSimplifiedGraph(), "Provenance (simplified)");
                        }

                        if (!silent) {
                            scope.getSession().getMonitor()
                                    .send(Message.create(scope.getSession().getId(), IMessage.MessageClass.TaskLifecycle,
                                            IMessage.Type.ProvenanceChanged,
                                            new ContextualizationNotification(ctx.getRootSubject().getId(),
                                                    ContextualizationNotification.Target.PROVENANCE)));
                        }

                    } else {
                        monitor.warn("could not build dataflow: observation unsuccessful");
                        notifyEnd();
                        ret = Observation.empty(observable, context.getScope());
                    }


                    if (observationListeners != null) {
                        for (BiConsumer<ITask<?>, IArtifact> observationListener : observationListeners) {
                            observationListener.accept((ITask<?>) this.task, ret);
                        }
                    }


                } catch (Throwable e) {

                    if (errorListeners != null) {
                        for (BiConsumer<ITask<?>, Throwable> errorListener : errorListeners) {
                            errorListener.accept((ITask<?>) this.task, e);
                        }
                    }

                    throw notifyAbort(e);
                }

                return ret;
            }
        });

        if (autostart) {
            started = true;
            executor.execute(delegate);
        }
    }

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
    public IObservation get() throws InterruptedException, ExecutionException {
        if (!started) {
            start();
        }
        return delegate.get();
    }

    @Override
    public IObservation get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!started) {
            start();
        }
        return delegate.get(timeout, unit);
    }

    @Override
    public ITaskTree<IArtifact> createChild(String description) {
        return new ObserveInContextTask(this, description);
    }

    @Override
    protected String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean start() {
        if (autostart || monitor.isInterrupted()) {
            return false;
        }
        started = true;
        executor.execute(delegate);
        return true;
    }

    @Override
    public boolean stop() {
        if (started) {
            cancel(true);
            return true;
        }
        return false;
    }
}

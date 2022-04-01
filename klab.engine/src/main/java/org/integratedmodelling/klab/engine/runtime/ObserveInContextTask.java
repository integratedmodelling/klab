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
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.AbstractRuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * A ITask that creates one or more Observations within a context subject. TODO
 * add option to choose when to start the observation and implement remaining
 * task options and listeners.
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

	public ObserveInContextTask(ObserveInContextTask parent, String description) {
		super(parent);
		this.delegate = parent.delegate;
		this.taskDescription = description;
	}

	public ObserveInContextTask(Subject context, String urn, Collection<String> scenarios) {
		this(context, urn, scenarios, null, null, context.getParentIdentity(Engine.class).getTaskExecutor(), null);
	}

	/**
	 * Listener consumers are called as things progress. The observation listener is
	 * first called with null as a parameter when starting, then (if no error
	 * occurs) another time with the observation as argument. The observation may be
	 * empty. If an exception is thrown, the error listener is called with the
	 * exception as argument.
	 * 
	 * @param context
	 * @param urn
	 * @param scenarios
	 * @param observationListener
	 * @param errorListener
	 */
	public ObserveInContextTask(Subject context, String urn, Collection<String> scenarioList,
			Collection<BiConsumer<ITask<?>, IArtifact>> oListeners,
			Collection<BiConsumer<ITask<?>, Throwable>> eListeners, Executor executor,
			SessionActivity activityDescriptor) {

		this.context = context;
		this.monitor = context.getMonitor().get(this);
		this.session = context.getParentIdentity(Session.class);
		this.activity.setActivityDescriptor(activityDescriptor);
		this.taskDescription = "Observation of " + urn + " in " + context.getName();
		this.executor = executor;

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

		delegate = new FutureTask<IObservation>(new MonitoredCallable<IObservation>(this) {

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
						throw new KlabIllegalArgumentException(
								"URN " + urn + " does not represent a resolvable entity");
					}

					/*
					 * resolve and run
					 */
					ResolutionScope scope = Resolver.create(null).resolve(resolvable,
							ResolutionScope.create(context, monitor, scenarios));

					if (scope.getCoverage().isRelevant()) {

						Dataflow dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + token,
								scope, context.getScope().getActuator()).setPrimary(parentTask == null);

						dataflow.setDescription(taskDescription);

						if (Configuration.INSTANCE.isEchoEnabled()) {
							System.out.println(dataflow.getKdlCode());
						}
						if (activity.getActivityDescriptor() != null) {
							activity.getActivityDescriptor().setDataflowCode(dataflow.getKdlCode());
						}

						IRuntimeScope ctx = ((Observation) context).getScope().getChild(ObserveInContextTask.this);
						((AbstractRuntimeScope) ctx).notifyDataflowChanges(ctx);

						// make a copy of the coverage so that we ensure it's a scale, behaving
						// properly
						// at merge.
						/*
						 * pass the first actuator in the father context as the parent for this
						 * resolution - this must be at primary level, i.e. the root is the first (and
						 * only) actuator in the root context
						 */
						Actuator actuator = (Actuator) (ctx.getDataflow().getActuators().isEmpty() ? null
								: ctx.getDataflow().getActuators().get(0));
						IArtifact result = dataflow.run(scope.getCoverage(), actuator, ctx);
						if (result instanceof IObservation) {
							ret = (IObservation) result;
						} else {
							ret = Observation.empty((IObservable) resolvable, ctx);
						}

						// task is done, record for provenance
						getActivity().finished();

						/*
						 * The actuator has sent this already, but we send the final artifact a second
						 * time to bring it to the foreground for the listeners
						 */
						if (dataflow.isPrimary()) {

							monitor.info("observation completed with "
									+ NumberFormat.getPercentInstance().format(scope.getCoverage().getCoverage())
									+ " context coverage");
						}

					} else {
						monitor.warn("could not build dataflow: observation unsuccessful");
						ret = Observation.empty(observable, context.getScope());
					}

					notifyEnd();

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
		return delegate.get();
	}

	@Override
	public IObservation get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
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
        if (autostart) {
            return false;
        }
        executor.execute(delegate);
        return true;
    }

}

package org.integratedmodelling.klab.engine.runtime;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * A ITask that creates one or more Observations within a context subject.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveInContextTask extends AbstractTask<IObservation> {

	FutureTask<IObservation> delegate;
	String taskDescription = "<uninitialized contextual observation task " + token + ">";

	public ObserveInContextTask(ObserveInContextTask parent) {
		super(parent);
		this.delegate = parent.delegate;
		this.taskDescription = parent.taskDescription;
	}

	public ObserveInContextTask(Subject context, String urn, Collection<String> scenarios) {

		this.context = context;
		this.monitor = context.getMonitor().get(this);
		this.session = context.getParentIdentity(Session.class);
		this.taskDescription = "Observation of " + urn + " in " + context.getName();

		session.touch();

		delegate = new FutureTask<IObservation>(new MonitoredCallable<IObservation>(this) {

			@Override
			public IObservation run() throws Exception {

				IObservation ret = null;

				try {

					notifyStart();

					/*
					 * obtain the resolvable object corresponding to the URN - either a concept or a
					 * model
					 */
					IResolvable resolvable = Resources.INSTANCE.getResolvableResource(urn, context.getScale());

					if (resolvable instanceof IModel) {
						resolvable = Observable.promote((IModel) resolvable);
					}

					if (resolvable == null) {
						throw new IllegalArgumentException("URN " + urn + " does not represent a resolvable entity");
					}

					/*
					 * resolve and run
					 */
					ResolutionScope scope = Resolver.create(null).resolve(resolvable,
							ResolutionScope.create(context, monitor, scenarios));
					if (scope.getCoverage().isRelevant()) {

						Dataflow dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + token,
								scope, null);

						dataflow.setDescription(taskDescription);

						System.out.println(dataflow.getKdlCode());

						IRuntimeScope ctx = ((Observation) context).getRuntimeScope();
						ctx.getContextualizationStrategy().add(dataflow);

						session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
								IMessage.Type.DataflowCompiled, new DataflowReference(token, dataflow.getKdlCode(),
										ctx.getContextualizationStrategy().getElkGraph())));

						// make a copy of the coverage so that we ensure it's a scale, behaving properly
						// at merge.
						/*
						 * pass the first actuator in the father context as the parent for this
						 * resolution - this must be at primary level, i.e. the root is the first (and
						 * only) actuator in the root context
						 */
						Actuator actuator = (Actuator) (ctx.getDataflow().getActuators().isEmpty() ? null
								: ctx.getDataflow().getActuators().get(0));
						IArtifact result = dataflow.run(scope.getCoverage(), actuator, monitor);
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
					}

					notifyEnd();

				} catch (Throwable e) {

					throw notifyAbort(e);
				}

				return ret;
			}
		});

		context.getParentIdentity(Engine.class).getTaskExecutor().execute(delegate);
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
	public ITaskTree<IObservation> createChild() {
		return new ObserveInContextTask(this);
	}

	@Override
	protected String getTaskDescription() {
		return taskDescription;
	}

}

package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.DataflowReference;

/**
 * A ITask that creates a root subject within a Session.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveContextTask extends AbstractTask<ISubject> {

	FutureTask<ISubject> delegate;
	String taskDescription = "<uninitialized observation task " + token + ">";

	public ObserveContextTask(ObserveContextTask parent, String description) {
		super(parent);
		this.delegate = parent.delegate;
		this.taskDescription = description;
	}

	public ObserveContextTask(Session session, Observer observer, Collection<String> scenarios) {

		Engine engine = session.getParentIdentity(Engine.class);
		try {

			this.monitor = (session.getMonitor()).get(this);
			this.session = session;
			this.taskDescription = "Observation of " + observer.getId();

			session.touch();

			delegate = new FutureTask<ISubject>(new MonitoredCallable<ISubject>(this) {

				@Override
				public ISubject run() throws Exception {

					ISubject ret = null;

					try {

						/*
						 * register the task so it can be interrupted and inquired about
						 */
						notifyStart();

						// TODO put all this logics in the resolver, call it from within Observations
						// and use that here.
						ResolutionScope scope = Resolver.create(null).resolve(observer, monitor, scenarios);
						if (scope.getCoverage().isRelevant()) {

							Dataflow dataflow = Dataflows.INSTANCE
									.compile("local:task:" + session.getId() + ":" + token, scope, null);

							dataflow.setDescription(taskDescription);

							/*
							 * Instantiate a preliminary contextualization strategy as there is no context
							 * yet.
							 */
							ContextualizationStrategy contextualizationStrategy = new ContextualizationStrategy();
							contextualizationStrategy.add(dataflow);

							// context will take it from the task identity when it's created
							setContextualizationStrategy(contextualizationStrategy);

							session.getMonitor()
									.send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
											IMessage.Type.DataflowCompiled, new DataflowReference(token,
													dataflow.getKdlCode(), contextualizationStrategy.getElkGraph())));
							/*
							 * make a copy of the coverage so that we ensure it's a scale, behaving properly
							 * at merge. FIXME this must be the entire scale now - each actuator creates its
							 * artifacts, then initialization is handled when computing.
							 */
							ret = (ISubject) dataflow.run(scope.getCoverage().copy().initialization(), monitor);

							if (ret != null) {
								setContext((Subject)ret);
								getDescriptor().setContextId(ret.getId());
							}
							
							/*
							 * load any behaviors and schedule repeating actions
							 */
							Actors.INSTANCE.instrument(observer.getAnnotations(), (Observation) ret);

							getActivity().finished();

							/*
							 * Register the observation context with the session. It will be disposed of
							 * and/or persisted by the session itself.
							 */
							session.registerObservationContext(((Observation) ret).getScope());

						}

						notifyEnd();

					} catch (Throwable e) {

						throw notifyAbort(e);

					}
					return ret;
				}

			});

			engine.getTaskExecutor().execute(delegate);
			
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
		return delegate.get();
	}

	@Override
	public ISubject get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return delegate.get(timeout, unit);
	}

	@Override
	public ITaskTree<ISubject> createChild(String description) {
		return new ObserveContextTask(this, description);
	}

	@Override
	protected String getTaskDescription() {
		return taskDescription;
	}

}

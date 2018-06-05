package org.integratedmodelling.klab.engine.runtime;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.TaskReference;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * A ITask that creates a root subject within a Session.
 * 
 * @author ferdinando.villa
 *
 */
public class ObserveContextTask implements ITask<ISubject> {

	Monitor monitor;
	FutureTask<ISubject> delegate;
	String token = "t" + NameGenerator.shortUUID();
	Session session;
	String taskDescription = "<uninitialized observation task " + token + ">";
	private TaskReference descriptor;

	public ObserveContextTask(Session session, Observer observer, Collection<String> scenarios) {

		Engine engine = session.getParentIdentity(Engine.class);
		try {

			this.monitor = (session.getMonitor()).get(this);
			this.session = session;
			this.taskDescription = "<task " + token + ": observation of " + observer + ">";

			this.descriptor = new TaskReference();
			this.descriptor.setId(token);
			this.descriptor.setDescription(this.taskDescription);

			session.touch();

			delegate = new FutureTask<ISubject>(new MonitoredCallable<ISubject>(this) {

				@Override
				public ISubject run() throws Exception {

					ISubject ret = null;

					try {

						session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
								IMessage.Type.TaskStarted, ObserveContextTask.this.descriptor));

						// TODO put all this logics in the resolver, call it from within Observations
						// and use that here.
						ResolutionScope scope = Resolver.INSTANCE.resolve(observer, monitor, scenarios);
						if (scope.getCoverage().isRelevant()) {

							Dataflow dataflow = Dataflows.INSTANCE
									.compile("local:task:" + session.getId() + ":" + token, scope);

							session.getMonitor()
									.send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
											IMessage.Type.DataflowCompiled,
											new DataflowReference(token, dataflow.getKdlCode())));

							// make a copy of the coverage so that we ensure it's a scale, behaving properly
							// at merge.
							ret = (ISubject) dataflow.run(scope.getCoverage().copy(), monitor);

						}
						session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
								IMessage.Type.TaskFinished, ObserveContextTask.this.descriptor));

					} catch (Throwable e) {

						ObserveContextTask.this.descriptor.setError(e.getLocalizedMessage());
						session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
								IMessage.Type.TaskAborted, ObserveContextTask.this.descriptor));
						throw e;

					}
					return ret;
				}
			});

			engine.getTaskExecutor().execute(delegate);
		} catch (

		Throwable e) {
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
	public ISubject get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return delegate.get(timeout, unit);
	}

}

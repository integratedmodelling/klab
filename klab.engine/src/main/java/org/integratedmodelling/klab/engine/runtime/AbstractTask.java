package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IActivity;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.provenance.Activity;
import org.integratedmodelling.klab.rest.TaskReference;
import org.integratedmodelling.klab.utils.NameGenerator;

public abstract class AbstractTask<T extends IArtifact> implements ITaskTree<T> {

	/**
	 * Specialized exception wrapper to avoid cascade notifications
	 * 
	 * @author Ferd
	 *
	 */
	public static class KlabTaskException extends KlabException {

		private static final long serialVersionUID = 461213337593957416L;

		/**
		 * Instantiates a new klab contextualization exception.
		 */
		public KlabTaskException() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * Instantiates a new klab contextualization exception.
		 *
		 * @param arg0 the arg 0
		 */
		public KlabTaskException(String arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}

		/**
		 * Instantiates a new klab contextualization exception.
		 *
		 * @param e the e
		 */
		public KlabTaskException(Throwable e) {
			super(e);
		}

	}

	AbstractTask() {
		this.activity = new Activity(token);
	}

	AbstractTask(AbstractTask<T> parent) {
		this.context = parent.context;
		this.session = parent.session;
		this.scenarios = parent.scenarios;
		this.parentTask = parent;
		this.token = parent.getId() + "." + this.token;
		this.activity = new Activity(token);
		this.monitor = parent.monitor.get(this);
		this.executor = parent.executor;
		this.observationListeners = parent.observationListeners;
		this.errorListeners = parent.errorListeners;
        this.activityBuilder = org.integratedmodelling.klab.rest.Activity.builder();

	}

	Activity activity;
	Monitor monitor;
	Subject context;
	protected IResolvable resolvable;
	protected boolean autostart = true;
	protected boolean started = false;
	protected boolean silent = false;
	protected IActivity.Builder activityBuilder;

	Session session;
	String token = "t" + NameGenerator.shortUUID();
	Set<String> scenarios = new HashSet<>();
	AbstractTask<T> parentTask = null;
	TaskReference descriptor;
	Long start;
	Long end;
	protected List<BiConsumer<ITask<?>, T>> observationListeners = new ArrayList<>();
	protected List<BiConsumer<ITask<?>, Throwable>> errorListeners = new ArrayList<>();
	protected Executor executor;

	@Override
	public boolean isChildTask() {
		return parentTask != null;
	}

	public Activity getActivity() {
		return activity;
	}

	@Override
	public String getContextId() {
		return this.context == null ? null : this.context.getId();
	}

	public void notifyStart() {
		this.start = System.currentTimeMillis();
		session.registerTask(this);
		if (!silent) {
			session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
				IMessage.Type.TaskStarted, getReference()));
		}
	}

	protected TaskReference getReference() {
		if (this.descriptor == null) {
			this.descriptor = new TaskReference();
			this.descriptor.setId(token);
			this.descriptor.setParentId(parentTask == null ? null : parentTask.getId());
			this.descriptor.setDescription(getTaskDescription());
			this.descriptor.setContextId(this.context == null ? null : this.context.getId());
			this.descriptor
					.setRootContextId(this.context == null ? null : this.context.getScope().getRootSubject().getId());
			this.descriptor.setStart(this.start);
			this.descriptor.setEnd(this.end);
		}
		return this.descriptor;
	}

	protected abstract String getTaskDescription();

	public void notifyEnd() {
		this.end = System.currentTimeMillis();
		session.unregisterTask(this);
		getReference().setStatus(Status.Finished);
		getReference().setEnd(this.end);
		if (!silent) {
			session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
					IMessage.Type.TaskFinished, getReference()));
		}
	}

	/**
	 * Return an exception to throw. Uses a private exception wrapper to limit the
	 * exception notification to the innermost causing thread.
	 * 
	 * @param e
	 * @return
	 */
	public KlabTaskException notifyAbort(Throwable e) {
		this.end = System.currentTimeMillis();
		getReference().setError(e.getLocalizedMessage());
		if (!(e instanceof KlabTaskException || e instanceof InterruptedException)) {
			monitor.error(e);
			if (!silent) {
				session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
					IMessage.Type.TaskAborted, getReference()));
			}
		} else if (e instanceof InterruptedException) {
			// you never know
			((Monitor) monitor).interrupt();
		} else {
			((Monitor) monitor).setError(e);
		}
		return e instanceof KlabTaskException ? (KlabTaskException) e : new KlabTaskException(e);
	}

	public Subject getContext() {
		return context;
	}

	public void setContext(Subject context) {
		this.context = context;
	}

	public Session getSession() {
		return session;
	}

	public String getToken() {
		return token;
	}

	public Collection<String> getScenarios() {
		return scenarios;
	}

	public AbstractTask<T> getParentTask() {
		return parentTask;
	}

	public TaskReference getDescriptor() {
		return descriptor;
	}

	@Override
	public String load(IBehavior behavior, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean stop(String behaviorId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop() {
		return false;
	}

	@Override
	public IResolvable getResolvable() {
		return this.resolvable;
	}

	@Override
	public void addObservationListener(BiConsumer<ITask<?>, T> listener) {
		observationListeners.add(listener);
	}

	@Override
	public void addErrorListener(BiConsumer<ITask<?>, Throwable> listener) {
		errorListeners.add(listener);
	}

	@Override
	public void addScenarios(Collection<String> scenarios) {
		this.scenarios.addAll(scenarios);
	}

	@Override
	public void set(Options option, Object value) {
		if (option == Options.SILENT && value instanceof Boolean) {
			this.silent = (Boolean) value;
		}
	}

}

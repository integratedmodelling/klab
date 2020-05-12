package org.integratedmodelling.klab.engine.runtime;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.provenance.Activity;
import org.integratedmodelling.klab.rest.TaskReference;
import org.integratedmodelling.klab.utils.NameGenerator;

public abstract class AbstractTask<T extends IObservation> implements ITaskTree<T> {

	private ContextualizationStrategy contextualizationStrategy;

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
	}

	Activity activity;
	Monitor monitor;
	Subject context;
	Session session;
	String token = "t" + NameGenerator.shortUUID();
	String[] scenarios;
	AbstractTask<T> parentTask = null;
	private TaskReference descriptor;

	@Override
	public boolean isChildTask() {
		return parentTask != null;
	}

	public Activity getActivity() {
		return activity;
	}

	/**
	 * This is needed for the first runtime context to pick its strategy before
	 * anything is run.
	 * 
	 * @return
	 */
	public ContextualizationStrategy getContextualizationStrategy() {
		return this.contextualizationStrategy;
	}

	public void setContextualizationStrategy(ContextualizationStrategy contextualizationStrategy) {
		this.contextualizationStrategy = contextualizationStrategy;
	}

	@Override
	public String getContextId() {
		return this.context == null ? null : this.context.getId();
	}

	public void notifyStart() {
		session.registerTask(this);
		session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
				IMessage.Type.TaskStarted, getReference()));
	}

	protected TaskReference getReference() {
		if (this.descriptor == null) {
			this.descriptor = new TaskReference();
			this.descriptor.setId(token);
			this.descriptor.setParentId(parentTask == null ? null : parentTask.getId());
			this.descriptor.setDescription(getTaskDescription());
		}
		return this.descriptor;
	}

	protected abstract String getTaskDescription();

	public void notifyEnd() {
		session.unregisterTask(this);
		session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
				IMessage.Type.TaskFinished, getReference()));
	}

	/**
	 * Return an exception to throw. Uses a private exception wrapper to limit the
	 * exception notification to the innermost causing thread.
	 * 
	 * @param e
	 * @return
	 */
	public KlabTaskException notifyAbort(Throwable e) {
		getReference().setError(e.getLocalizedMessage());
		if (!(e instanceof KlabTaskException)) {
			monitor.error(e);
			session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
					IMessage.Type.TaskAborted, getReference()));
		} else {
			((Monitor)monitor).setError(e);
		}
		return e instanceof KlabTaskException ? (KlabTaskException) e : new KlabTaskException(e);
	}

}

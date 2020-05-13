package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * A {@link ContextMonitor} that also connects tasks, dataflows and
 * notifications to each context.
 * 
 * @author Ferd
 *
 */
public abstract class SessionMonitor extends ContextMonitor {

	static private Object lock = new Object();
	AtomicLong notificationCounter = new AtomicLong(0);

	public class ContextDescriptor {

		String contextId;
		ContextGraph observationGraph;

		// all notifications, tasks and observations go in a map
		Map<String, Object> beans = new HashMap<>();
		/*
		 * we keep the structure here
		 */
		Graph<String, DefaultEdge> structure = new DefaultDirectedGraph<>(DefaultEdge.class);
		// list of top-level tasks registered in order of appearance
		List<String> observationTasks = new ArrayList<>();
		boolean dataflowChanged = false;

		public ObservationReference getRoot() {
			return (ObservationReference) beans.get(contextId);
		}
	}

	public interface Listener {
		/**
		 * Called whenever an object is added to a task (the hierarchy is only made of
		 * tasks so the parent is always a task). The added object may be a task, an
		 * observation or a notification.
		 * 
		 * @param rootContext  the root observation
		 * @param added
		 * @param objectParent
		 */
		void onStructureChange(ObservationReference rootContext, Object added, TaskReference objectParent);

		/**
		 * Called when the status of a task changes
		 * 
		 * @param rootContext
		 * @param task
		 * @param status
		 */
		void onTaskStatusChange(ObservationReference rootContext, TaskReference task, Status status);

		/**
		 * System notifications
		 * 
		 * @param notification
		 */
		void onSystemNotification(Notification notification);

		/**
		 * Called when the dataflow changes.
		 * 
		 * @param root
		 */
		void onDataflowChange(ObservationReference root, DataflowReference dataflow);
	}

	Map<String, ContextDescriptor> contexts = new HashMap<>();
	Map<String, ContextDescriptor> contextsByTask = new HashMap<>();
	List<Notification> systemNotifications = new ArrayList<>();

	List<Listener> listeners = new ArrayList<>();

	/*
	 * temporary storage for tasks when they arrive w/o a context. Notification
	 * starts when the context observation arrives and the task is moved to the
	 * correspondent context descriptor.
	 */
	Map<String, TaskReference> orphanTasks = new HashMap<>();

	public void addListener(Listener listener) {
//		listeners.add(listener);
	}

	public void register(TaskReference task) {
		synchronized (lock) {

			System.out.println("REGISTERING TASK " + task.getRootContextId() + " " + task);
			if (task.getRootContextId() == null) {
				// observation will come later, don't do anything for the time being.
				orphanTasks.put(task.getId(), task);
			} else {
				ContextDescriptor context = contexts.get(task.getRootContextId());
				context.beans.put(task.getId(), task);
				context.structure.addVertex(task.getId());
				if (task.getParentId() != null) {
					context.structure.addEdge(task.getId(), task.getParentId());
				}
				contextsByTask.put(task.getId(), context);
			}
		}
	}

	@Override
	protected void notifyObservation(ObservationReference observation) {

		synchronized (lock) {

			if (observation.getParentArtifactId() == null) {
				/*
				 * its task must exist; if it's a root observation, it's an orphan task
				 */
				TaskReference task = orphanTasks.get(observation.getTaskId());
				if (task != null) {
					ContextDescriptor context = new ContextDescriptor();
					context.observationGraph = this.getGraph(observation.getId());
					context.dataflowChanged = false;
					context.structure.addVertex(observation.getId());
					context.structure.addVertex(task.getId());
					context.structure.addEdge(observation.getId(), task.getId());
					context.beans.put(task.getId(), task);
					context.beans.put(observation.getId(), observation);
					orphanTasks.remove(observation.getTaskId());
					contextsByTask.put(task.getId(), context);
					contexts.put(observation.getId(), context);
					for (Listener listener : listeners) {
						listener.onStructureChange((ObservationReference) context.beans.get(context.contextId),
								observation, (TaskReference) context.beans.get(observation.getTaskId()));
					}
				} else {
					// internal error, shouldn't happen but might during debugging, just leaving
					// this to triple-check it doesn't happen at production
					System.out.println("INTERNAL ERROR - ORPHAN TASK MISSING on " + observation);
				}

			} else {
				ContextDescriptor context = contexts.get(observation.getRootContextId());
				if (context != null) {
					context.structure.addVertex(observation.getId());
					context.structure.addEdge(observation.getId(), observation.getTaskId());
					context.beans.put(observation.getId(), observation);
					for (Listener listener : listeners) {
						listener.onStructureChange((ObservationReference) context.beans.get(context.contextId),
								observation, (TaskReference) context.beans.get(observation.getTaskId()));
					}
				} else {
					// internal error, shouldn't happen but might during debugging, just leaving
					// this to triple-check it doesn't happen at production
					System.out.println("INTERNAL ERROR - CONTEXT MISSING on " + observation);
				}
			}
		}
	}

	@Override
	protected void notifyObservationChange(ObservationReference observation, ObservationChange change) {
	}

	public void register(Notification notification) {
		synchronized (lock) {
			if (notification.getIdentity().startsWith("t")) {
				ContextDescriptor context = contextsByTask.get(notification.getIdentity());
				String notificationId = notification.getIdentity() + "_N" + notificationCounter.incrementAndGet();
				context.beans.put(notificationId, notification);
				context.structure.addVertex(notificationId);
				context.structure.addEdge(notificationId, notification.getIdentity());
				for (Listener listener : listeners) {
					listener.onStructureChange((ObservationReference) context.beans.get(context.contextId),
							notification, (TaskReference) context.beans.get(notification.getIdentity()));
				}
			} else if (notification.getIdentity().startsWith("o")) {
				// Ehm. Shouldn't happen but I don't feel like storing all observation IDs
			} else {
				systemNotifications.add(notification);
				for (Listener listener : listeners) {
					listener.onSystemNotification(notification);
				}
			}
		}
	}

	public List<TaskReference> getTasks(String contextId, Level notificationLevel) {
		List<TaskReference> ret = new ArrayList<>();
		synchronized (lock) {
		}
		return ret;
	}

	public String getDataflow(String contextId) {
		synchronized (lock) {

		}
		return "";
	}

	public void update(TaskReference task, Status status) {
		synchronized (lock) {
			ContextDescriptor context = contexts.get(task.getRootContextId());
			TaskReference tref = (TaskReference) context.beans.get(task.getId());
			tref.setStatus(status);
			for (Listener listener : listeners) {
				listener.onTaskStatusChange(context.getRoot(), tref, status);
			}
		}
	}

	public void register(DataflowReference dataflow) {
		synchronized (lock) {
//			System.out.println("REGISTERING DATAFLOW " + dataflow);
			ContextDescriptor context = contextsByTask.get(dataflow.getTaskId());
			if (context != null) {
				context.dataflowChanged = true;
				for (Listener listener : listeners) {
					listener.onDataflowChange(context.getRoot(), dataflow);
				}
			}
		}
	}

	public List<Notification> getSystemNotifications(Level systemLogLevel) {
		List<Notification> ret = new ArrayList<>();
		return ret;
	}

	public ContextDescriptor getContextDescriptor(ObservationReference rootContext) {
		return contexts.get(rootContext.getId());
	}

}

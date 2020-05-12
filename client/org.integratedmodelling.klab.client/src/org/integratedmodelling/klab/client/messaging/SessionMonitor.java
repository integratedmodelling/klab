package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	class ContextDescriptor {
		
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
	}

	Map<String, ContextDescriptor> contexts = new HashMap<>();

	public void register(TaskReference task) {
		synchronized (lock) {
			
			System.out.println("REGISTERING TASK " + task);
			
//			ContextDescriptor context = contexts.get(task.getContextId());
//			if (context == null) {
//				context = new ContextDescriptor();
//				context.structure.addVertex(task.getId());
//			}
		}
	}

	@Override
	protected void notifyObservation(ObservationReference observation) {
		
		System.out.println("REGISTERING OBSERVATION " + observation);

//		if (observation.getParentArtifactId() == null) {
//			ContextDescriptor context = new ContextDescriptor();
//			context.observationGraph = this.getGraph(observation.getId());
//			context.dataflowChanged = false;
//			context.structure.addVertex(observation.getId());
//			
//		} else {
//			ContextDescriptor context = contexts.get(observation.getRootContextId());
//			context.structure.addEdge(observation.getId(), observation.getTaskId());
//		}
	}



	@Override
	protected void notifyObservationChange(ObservationReference observation, ObservationChange change) {
	}

	public void register(Notification notification) {
		synchronized (lock) {
			System.out.println("REGISTERING NOTIFICATION " + notification);
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
			System.out.println("UPDATING TASK " + status + ": " + task);
		}
	}

	public void register(DataflowReference dataflow) {
		synchronized (lock) {
			System.out.println("REGISTERING DATAFLOW " + dataflow);
		}
	}

	public Status getStatus(TaskReference element) {
		// TODO Auto-generated method stub
		return Status.Finished;
	}

	public List<Notification> getSystemNotifications(Level systemLogLevel) {
		List<Notification> ret = new ArrayList<>();
		return ret;
	}

}

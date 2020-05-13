package org.integratedmodelling.klab.rest;

/**
 * Sent around when runtime events happen, optimized for quickly finding out
 * what has happened. For the time being its use is limited to the UI.
 * 
 * @author Ferd
 *
 */
public class RuntimeEvent {

	public enum Type {
		TaskStatusChanged, NotificationAdded, ObservationAdded, TaskAdded, SystemNotification, DataflowChanged
	}

	Type type;
	ObservationReference rootContext;
	Notification notification;
	TaskReference task;
	TaskReference parentTask;
	ObservationReference observation;
	DataflowReference dataflow;

	public RuntimeEvent() {
	}

	public RuntimeEvent(ObservationReference rootContext2, TaskReference task2,
			org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status status) {
		this.type = Type.TaskStatusChanged;
		this.rootContext = rootContext2;
		this.task = task2;
	}

	public RuntimeEvent(Notification notification2) {
		this.type = Type.SystemNotification;
		this.notification = notification2;
	}

	public RuntimeEvent(ObservationReference rootContext2, Object added, TaskReference objectParent) {
		this.rootContext = rootContext2;
		this.parentTask = objectParent;
		if (added instanceof Notification) {
			this.type = Type.NotificationAdded;
			this.notification = (Notification) added;
		} else if (added instanceof TaskReference) {
			this.type = Type.TaskAdded;
			this.task = (TaskReference) added;
		} else if (added instanceof ObservationReference) {
			this.type = Type.ObservationAdded;
			this.observation = (ObservationReference) added;
		}
	}

	public RuntimeEvent(ObservationReference rootContext2, DataflowReference dataflow) {
		this.type = Type.DataflowChanged;
		this.dataflow = dataflow;
		this.rootContext = rootContext2;
	}

	public Type getType() {
		return type;
	}

	public ObservationReference getRootContext() {
		return rootContext;
	}

	public Notification getNotification() {
		return notification;
	}

	public TaskReference getTask() {
		return task;
	}

	public ObservationReference getObservation() {
		return observation;
	}

	public DataflowReference getDataflow() {
		return dataflow;
	}

	public TaskReference getParentTask() {
		return parentTask;
	}

//	/**
//	 * This object has all the knowledge about a context, including the task and
//	 * observation graphs.
//	 * 
//	 * @return
//	 */
//	public ContextDescriptor getRoot() {
//		return Activator.session().getContextMonitor().getContextDescriptor(rootContext);
//	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setRootContext(ObservationReference rootContext) {
		this.rootContext = rootContext;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public void setTask(TaskReference task) {
		this.task = task;
	}

	public void setParentTask(TaskReference parentTask) {
		this.parentTask = parentTask;
	}

	public void setObservation(ObservationReference observation) {
		this.observation = observation;
	}

	public void setDataflow(DataflowReference dataflow) {
		this.dataflow = dataflow;
	}

	@Override
	public String toString() {
		return "RuntimeEvent [type=" + type + ", rootContext=" + rootContext + ", notification=" + notification
				+ ", task=" + task + ", parentTask=" + parentTask + ", observation=" + observation + "]";
	}

}
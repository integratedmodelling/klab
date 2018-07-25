package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.jobs.JobStatus;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * Augmented {@link TaskReference} for runtime bookkeeping.
 * 
 * @author ferdinando.villa
 *
 */
public class ETaskReference extends TaskReference {

	private DataflowReference dataflow = null;
	private List<Notification> notifications = new ArrayList<>();
	private ObservationReference artifact;
	// one of the three pertaining to a task's status
	private IMessage.Type status = IMessage.Type.TaskStarted;

	public ETaskReference() {}
	
	public ETaskReference(TaskReference other) {
		super(other);
	}
	
	public DataflowReference getDataflow() {
		return dataflow;
	}

	public void setDataflow(DataflowReference dataflow) {
		this.dataflow = dataflow;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public ObservationReference getArtifact() {
		return artifact;
	}

	public void setArtifact(ObservationReference artifact) {
		this.artifact = artifact;
	}

	public IMessage.Type getStatus() {
		return status;
	}

	public void setStatus(IMessage.Type status) {
		this.status = status;
	}

	

}

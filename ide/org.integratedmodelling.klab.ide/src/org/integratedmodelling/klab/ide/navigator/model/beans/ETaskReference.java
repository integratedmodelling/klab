package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * Augmented {@link TaskReference} for runtime bookkeeping.
 * 
 * @author ferdinando.villa
 *
 */
public class ETaskReference implements ITaskReference, ERuntimeObject {

	private ITaskReference delegate;
	
	public String getId() {
		return delegate.getId();
	}

	public String getParentId() {
		return delegate.getParentId();
	}

	public String getUrn() {
		return delegate.getUrn();
	}

	public String getDescription() {
		return delegate.getDescription();
	}

	public String getError() {
		return delegate.getError();
	}

	private EDataflowReference dataflow = null;
	private List<ENotification> notifications = new ArrayList<>();
	private IMessage.Type status = IMessage.Type.TaskStarted;
	private List<EObservationReference> observations = new ArrayList<>();
	private ERuntimeObject parent;
	
	public ETaskReference() {}
	
	public ETaskReference(TaskReference other, ERuntimeObject parent) {
		this.delegate = other;
		this.parent = parent;
	}
	
	public EDataflowReference getDataflow() {
		return dataflow;
	}

	public void setDataflow(EDataflowReference dataflow) {
		this.dataflow = dataflow;
	}

	public List<ENotification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<ENotification> notifications) {
		this.notifications = notifications;
	}

	public IMessage.Type getStatus() {
		return status;
	}

	public void setStatus(IMessage.Type status) {
		this.status = status;
	}

	@Override
	public ERuntimeObject getEParent() {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority priority) {
		List<ERuntimeObject> ret = new ArrayList<>();
		if (dataflow != null) {
			ret.add(dataflow);
		}
		if (priority == DisplayPriority.TASK_FIRST) {
			ret.addAll(observations);
		}
		ret.addAll(notifications);
		return ret.toArray(new ERuntimeObject[ret.size()]);
	}

	public void addCreated(EObservationReference obs) {
		observations.add(obs);
	}

	@Override
	public String getContextId() {
		return delegate.getContextId();
	}

	public void addNotification(ENotification enote) {
		notifications.add(enote);
	}

}

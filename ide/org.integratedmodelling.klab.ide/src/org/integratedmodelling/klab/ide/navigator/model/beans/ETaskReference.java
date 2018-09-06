package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.TaskReference;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Augmented {@link TaskReference} for runtime bookkeeping.
 * 
 * @author ferdinando.villa
 *
 */
public class ETaskReference implements ITaskReference, ERuntimeObject {

	private TaskReference delegate;

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
	private List<String> observations = new ArrayList<>();
	private List<ETaskReference> tasks = new ArrayList<>();
	private String parentTaskId;
	private String parentArtifactId;

	public ETaskReference() {
	}

	public ETaskReference(TaskReference other) {
		this.delegate = other;
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

	public ERuntimeObject getEParent(DisplayPriority priority) {
		return priority == DisplayPriority.ARTIFACTS_FIRST ? Activator.session().getObservation(parentArtifactId)
				: Activator.session().getTask(parentTaskId);
	}

	@Override
	public synchronized ERuntimeObject[] getEChildren(DisplayPriority priority, Level level) {

		List<ERuntimeObject> ret = new ArrayList<>();
		if (dataflow != null) {
			ret.add(dataflow);
		}
		if (priority == DisplayPriority.TASK_FIRST) {
			for (String s : observations) {
				ret.add(Activator.session().getObservation(s));
			}
		}
		for (int i = tasks.size() - 1; i >= 0; i--) {
			ret.add(tasks.get(i));
		}
		for (ENotification notification : notifications) {
			if (level.intValue() <= Level.parse(notification.getLevel()).intValue())
				ret.add(notification);
		}
		return ret.toArray(new ERuntimeObject[ret.size()]);
	}

	public void addCreated(EObservationReference obs) {
		observations.add(obs.getId());
		obs.setCreatorTaskId(this.getId());
	}

	@Override
	public List<Pair<String, String>> getProperties() {
		List<Pair<String, String>> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public String getContextId() {
		return delegate.getContextId();
	}

	public void addNotification(ENotification enote) {
		enote.setParent(this);
		notifications.add(enote);
	}

	@Override
	public String toString() {
		return "[TASK " + getDescription() + "context = " + getContextId() + "]";
	}

	public void setContextId(String id) {
		delegate.setContextId(id);
	}

	public void addChildTask(ETaskReference task) {
		task.parentTaskId = this.getId();
		tasks.add(task);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof ETaskReference && ((ETaskReference) o).getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	public void removeObservation(String id) {
		observations.remove(id);
	}

}

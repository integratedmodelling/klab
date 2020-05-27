package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ResourceOperationResponse {

	private String urn;
	private String operation;
	private List<Notification> notifications = new ArrayList<>();

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		return "ResourceOperationResponse [urn=" + urn + ", operation=" + operation + ", notifications=" + notifications
				+ "]";
	}

}

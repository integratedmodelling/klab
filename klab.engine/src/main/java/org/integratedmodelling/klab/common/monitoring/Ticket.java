package org.integratedmodelling.klab.common.monitoring;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.ITicket;

public class Ticket implements ITicket {

	String id;
	Date postDate;
	Date resolutionDate;
	Status status = Status.OPEN;
	Type type;
	Map<String, String> data = new HashMap<>();
	String statusMessage;

	public static Ticket create(String id, Object... objects) {
		Ticket ret = new Ticket();
		ret.id = id;
		ret.postDate = new Date(System.currentTimeMillis());
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] instanceof Status) {
					ret.status = (Status) objects[i];
				} else if (objects[i] instanceof Type) {
					ret.type = (Type) objects[i];
				} else {
					ret.data.put(objects[i].toString(), objects[++i].toString());
				}
			}
		}
		return ret;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Date getPostDate() {
		return postDate;
	}

	@Override
	public Date getResolutionDate() {
		return resolutionDate;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Map<String, String> getData() {
		return data;
	}

	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}

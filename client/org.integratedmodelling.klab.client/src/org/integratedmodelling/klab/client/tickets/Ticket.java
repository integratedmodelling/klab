package org.integratedmodelling.klab.client.tickets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.ITicket;

public class Ticket implements ITicket {

	transient TicketManager manager;
	
	private String id;
	private Date postDate;
	private Date resolutionDate;
	private Status status = Status.OPEN;
	private Type type;
	private Map<String, String> data = new HashMap<>();
	private String statusMessage;
	private boolean seen = false;

	static Ticket create(String id, Object... objects) {
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
					Object key = objects[i];
					Object value = objects[++i];
					ret.data.put(key.toString(), value == null ? null : value.toString());
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

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean matches(Object[] selectors) {
		if (selectors != null) {
			for (int i = 0; i < selectors.length; i++) {
				if (selectors[i] instanceof Status) {
					if (status != (Status) selectors[i]) {
						return false;
					}
				} else if (selectors[i] instanceof Type) {
					if (type != (Type) selectors[i]) {
						return false;
					}
				} else {
					String dat = data.get(selectors[i]);
					Object match = selectors[++i];
					if (!(dat == null && match == null) || match == null || dat != null
							|| !dat.equals(match.toString())) {
						return false;
					}
				}
			}
		}
		return selectors != null;
	}

	@Override
	public void refresh() {
		Ticket t = (Ticket) manager.getTicket(this.id);
		copy(t);
	}

	@Override
	public void update(Object...objects) {
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] instanceof Status) {
					this.status = (Status) objects[i];
				} else if (objects[i] instanceof Type) {
					this.type = (Type) objects[i];
				} else {
					Object key = objects[i];
					Object value = objects[++i];
					this.data.put(key.toString(), value == null ? null : value.toString());
				}
			}
		}
		this.manager.put(this);
	}
	
	private void copy(Ticket t) {
		this.data.clear();
		this.data.putAll(t.data);
		this.seen = t.seen;
		this.postDate = t.postDate;
		this.resolutionDate = t.resolutionDate;
		this.status = t.status;
		this.type = t.type;
		this.id = t.id;
	}

	@Override
	public void delete() {
		manager.remove(this);
	}

	@Override
	public void resolve(Object...data) {
		update(data);
		this.status = Status.RESOLVED;
		update();
	}

	@Override
	public void error(String status) {
		this.status = Status.ERROR;
		if (status != null) {
			this.statusMessage = status;
		}
		update();
	}


}

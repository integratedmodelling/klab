package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.ITicket;

public class TicketRequest {

	private String ticketId;

	private ITicket.Status status;
	private ITicket.Type type;
	private Map<String, String> queryData = new HashMap<>();
	private long after;
	private long before;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public ITicket.Status getStatus() {
		return status;
	}

	public void setStatus(ITicket.Status status) {
		this.status = status;
	}

	public ITicket.Type getType() {
		return type;
	}

	public void setType(ITicket.Type type) {
		this.type = type;
	}

	public Map<String, String> getQueryData() {
		return queryData;
	}

	public void setQueryData(Map<String, String> queryData) {
		this.queryData = queryData;
	}

	public long getAfter() {
		return after;
	}

	public void setAfter(long after) {
		this.after = after;
	}

	public long getBefore() {
		return before;
	}

	public void setBefore(long before) {
		this.before = before;
	}

}

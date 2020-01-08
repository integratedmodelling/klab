package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.api.runtime.ITicket.Type;

public class TicketResponse {

	public static class Ticket {

		private String id;
		private long postDate;
		private long resolutionDate;
		private Status status = Status.OPEN;
		private Type type;
		private Map<String, String> data = new HashMap<>();
		private String statusMessage;
		private boolean seen = false;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public long getPostDate() {
			return postDate;
		}

		public void setPostDate(long postDate) {
			this.postDate = postDate;
		}

		public long getResolutionDate() {
			return resolutionDate;
		}

		public void setResolutionDate(long resolutionDate) {
			this.resolutionDate = resolutionDate;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public Map<String, String> getData() {
			return data;
		}

		public void setData(Map<String, String> data) {
			this.data = data;
		}

		public String getStatusMessage() {
			return statusMessage;
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

	}

	private List<Ticket> tickets = new ArrayList<>();

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}

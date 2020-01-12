package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A picture of network currently accessible to the engine user. Should only
 * include online nodes at the time of calling; carries the most up to date
 * JWT token to talk to them.
 * 
 * @author Ferd
 *
 */
public class NetworkReference {

	private List<String> resources = new ArrayList<>();
	private List<String> publishing = new ArrayList<>();
	private List<String> searchable = new ArrayList<>();
	private HubReference hub;
	private Map<String, NodeReference> nodes = new HashMap<>();
	private List<String> connections = new ArrayList<>();
	private List<TicketResponse.Ticket> resolvedTickets = new ArrayList<>();

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public HubReference getHub() {
		return hub;
	}

	public void setHub(HubReference hub) {
		this.hub = hub;
	}

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(List<String> connections) {
		this.connections = connections;
	}

	public List<String> getPublishing() {
		return publishing;
	}

	public void setPublishing(List<String> publishing) {
		this.publishing = publishing;
	}

	public List<String> getSearchable() {
		return searchable;
	}

	public void setSearchable(List<String> searchable) {
		this.searchable = searchable;
	}

	public Map<String, NodeReference> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, NodeReference> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		return "NetworkReference [publishing=" + publishing + ", hub=" + hub + ", nodes=" + nodes + "]";
	}

	public List<TicketResponse.Ticket> getResolvedTickets() {
		return resolvedTickets;
	}

	public void setResolvedTickets(List<TicketResponse.Ticket> resolvedTickets) {
		this.resolvedTickets = resolvedTickets;
	}
}

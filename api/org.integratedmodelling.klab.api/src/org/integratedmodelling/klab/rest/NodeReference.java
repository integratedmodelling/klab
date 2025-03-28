package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;

public class NodeReference {

	public static enum Permission {
		PUBLISH, QUERY
	}

	private String id;
	private Set<Permission> permissions = new HashSet<>();
	private IdentityReference partner;
	private List<String> urls = new ArrayList<>();
	private boolean online;
	private int retryPeriodMinutes;
	private int loadFactor;
	private Set<String> adapters = new HashSet<>();
//	private Set<String> resources = new HashSet<>();
	private Set<String> namespaces = new LinkedHashSet<>();
	private Set<String> catalogs = new LinkedHashSet<>();
	private List<String> incomingConnections = new ArrayList<>();
	private List<String> outgoingConnections = new ArrayList<>();
	private IIdentity.Type identityType;

	public NodeReference() {

	}
	
	public NodeReference(INodeIdentity node) {
		this.id = node.getName();
		this.permissions.addAll(node.getPermissions());
		this.namespaces.addAll(node.getNamespaceIds());
		this.catalogs.addAll(node.getCatalogIds());
//		this.resources.addAll(node.getResources());
		this.urls.addAll(node.getUrls());
		this.online = node.isOnline();
		this.identityType = node.getIdentityType();
	}

	public NodeReference(NodeCapabilities capabilities) {
		for (ResourceAdapterReference adapter : capabilities.getResourceAdapters()) {
			this.adapters.add(adapter.getName());
		}
		this.id = capabilities.getName();
		this.catalogs.addAll(capabilities.getResourceCatalogs());
		this.namespaces.addAll(capabilities.getResourceNamespaces());
//		this.resources.addAll(capabilities.getResourceUrns());
		this.online = capabilities.isOnline();
		if (capabilities.isAcceptSubmission()) {
			this.permissions.add(Permission.PUBLISH);
		}
		if (capabilities.isAcceptQueries()) {
			this.permissions.add(Permission.QUERY);
		}
		this.identityType = capabilities.getIdentityType();
		// TODO authorities
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public IdentityReference getPartner() {
		return partner;
	}

	public void setPartner(IdentityReference partner) {
		this.partner = partner;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public int getRetryPeriodMinutes() {
		return retryPeriodMinutes;
	}

	public void setRetryPeriodMinutes(int retryPeriodMinutes) {
		this.retryPeriodMinutes = retryPeriodMinutes;
	}

	public int getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(int loadFactor) {
		this.loadFactor = loadFactor;
	}

	public List<String> getIncomingConnections() {
		return incomingConnections;
	}

	public void setIncomingConnections(List<String> incomingConnections) {
		this.incomingConnections = incomingConnections;
	}

	public List<String> getOutgoingConnections() {
		return outgoingConnections;
	}

	public void setOutgoingConnections(List<String> outgoingConnections) {
		this.outgoingConnections = outgoingConnections;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identityType == null) ? 0 : identityType.hashCode());
		result = prime * result + ((incomingConnections == null) ? 0 : incomingConnections.hashCode());
		result = prime * result + loadFactor;
		result = prime * result + (online ? 1231 : 1237);
		result = prime * result + ((outgoingConnections == null) ? 0 : outgoingConnections.hashCode());
		result = prime * result + ((partner == null) ? 0 : partner.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
		result = prime * result + retryPeriodMinutes;
		result = prime * result + ((urls == null) ? 0 : urls.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeReference other = (NodeReference) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identityType == null) {
            if (other.identityType != null)
                return false;
        } else if (!identityType.equals(other.identityType))
            return false;
		if (incomingConnections == null) {
			if (other.incomingConnections != null)
				return false;
		} else if (!incomingConnections.equals(other.incomingConnections))
			return false;
		if (loadFactor != other.loadFactor)
			return false;
		if (online != other.online)
			return false;
		if (outgoingConnections == null) {
			if (other.outgoingConnections != null)
				return false;
		} else if (!outgoingConnections.equals(other.outgoingConnections))
			return false;
		if (partner == null) {
			if (other.partner != null)
				return false;
		} else if (!partner.equals(other.partner))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		if (retryPeriodMinutes != other.retryPeriodMinutes)
			return false;
		if (urls == null) {
			if (other.urls != null)
				return false;
		} else if (!urls.equals(other.urls))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeReference [id=" + id + ", type=" + identityType + ", permissions=" + permissions + ", partner=" + partner + ", urls=" + urls
				+ ", online=" + online + ", retryPeriodMinutes=" + retryPeriodMinutes + ", loadFactor=" + loadFactor
				+ ", incomingConnections=" + incomingConnections + ", outgoingConnections=" + outgoingConnections + "]";
	}

	public Set<String> getAdapters() {
		return adapters;
	}

	public void setAdapters(Set<String> adapters) {
		this.adapters = adapters;
	}

	public Set<String> getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(Set<String> namespaces) {
		this.namespaces = namespaces;
	}

	public Set<String> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<String> catalogs) {
		this.catalogs = catalogs;
	}

	public IIdentity.Type getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IIdentity.Type identityType) {
        this.identityType = identityType;
    }

}

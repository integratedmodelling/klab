package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class ResourceReference {
	String urn;
	String version;
	String adapterType;
	String localPath;
	String geometry;
	IArtifact.Type type;
	long resourceTimestamp;
	Map<String,String> metadata = new HashMap<>();
	Map<String,String> parameters = new HashMap<>();
	List<String> localPaths = new ArrayList<>();
	List<ResourceReference> history = new ArrayList<>();
	List<Notification> notifications = new ArrayList<>();
	
	public String getUrn() {
		return urn;
	}
	public void setUrn(String urn) {
		this.urn = urn;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAdapterType() {
		return adapterType;
	}
	public void setAdapterType(String adapterType) {
		this.adapterType = adapterType;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public IArtifact.Type getType() {
		return type;
	}
	public void setType(IArtifact.Type type) {
		this.type = type;
	}
	public long getResourceTimestamp() {
		return resourceTimestamp;
	}
	public void setResourceTimestamp(long resourceTimestamp) {
		this.resourceTimestamp = resourceTimestamp;
	}
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public List<String> getLocalPaths() {
		return localPaths;
	}
	public void setLocalPaths(List<String> localPaths) {
		this.localPaths = localPaths;
	}
	public List<ResourceReference> getHistory() {
		return history;
	}
	public void setHistory(List<ResourceReference> history) {
		this.history = history;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	
}

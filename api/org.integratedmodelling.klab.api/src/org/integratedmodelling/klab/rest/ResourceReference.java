package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class ResourceReference {

    private String urn;
    private String version;
    private String adapterType;
    private String localPath;
    private String geometry;
    // only set in local resources
    private String projectName;
    // only in local resources, the short name for k.IM
    private String localName;
    private IArtifact.Type type;
    private long resourceTimestamp;
    private Map<String, String> metadata = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    private List<String> localPaths = new ArrayList<>();
    private List<ResourceReference> history = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();

    public ResourceReference() {
    }

    public ResourceReference(ResourceReference other) {
		this.urn = other.urn;
		this.version = other.version;
		this.adapterType = other.adapterType;
		this.localPath = other.localPath;
		this.geometry = other.geometry;
		this.projectName = other.projectName;
		this.localName = other.localName;
		this.type = other.type;
		this.resourceTimestamp = other.resourceTimestamp;
		this.metadata = other.metadata;
		this.parameters = other.parameters;
		this.localPaths = other.localPaths;
		this.history = other.history;
		this.notifications = other.notifications;
	}



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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

}

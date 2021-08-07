package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator.Operation;
import org.integratedmodelling.klab.api.provenance.IArtifact;

public class ResourceReference {

    public static class OperationReference implements Operation {

        private String name;
        private String description;
        private boolean shouldConfirm;

        public OperationReference() {
        }

        public OperationReference(String name, String description, boolean shouldConfirm) {
            this.name = name;
            this.description = description;
            this.shouldConfirm = shouldConfirm;
        }

        @Override
        public boolean isShouldConfirm() {
            return shouldConfirm;
        }

        public void setShouldConfirm(boolean shouldConfirm) {
            this.shouldConfirm = shouldConfirm;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    /**
     * One of these can be added by a resource adapter to check on the availability before use. If
     * availability is DELAYED, the client should check the wait time and decide according to user
     * workflow chosen.
     * 
     * @author Ferd
     *
     */
    public static class AvailabilityReference {

        private Availability availability;
        private int retryTimeSeconds;
        private String message;

        public Availability getAvailability() {
            return availability;
        }
        public void setAvailability(Availability availability) {
            this.availability = availability;
        }
        public int getRetryTimeSeconds() {
            return retryTimeSeconds;
        }
        public void setRetryTimeSeconds(int retryTimeSeconds) {
            this.retryTimeSeconds = retryTimeSeconds;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

    }

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
    private List<AttributeReference> attributes = new ArrayList<>();
    private SpatialExtent spatialExtent;
    private List<AttributeReference> dependencies = null;
    private List<AttributeReference> outputs = null;
    private List<String> categorizables = new ArrayList<>();

    /**
     * This will never be stored in a catalog: it's only for real-time operations such as
     * contextualization of the resource before data are extracted.
     */
    private AvailabilityReference availability = null;

    public List<AttributeReference> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<AttributeReference> outputs) {
        this.outputs = outputs;
    }

    private Map<String, String> exportFormats = new LinkedHashMap<>();

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
        this.metadata.putAll(other.metadata);
        this.parameters.putAll(other.parameters);
        this.localPaths.addAll(other.localPaths);
        this.history.addAll(other.history);
        this.spatialExtent = other.spatialExtent;
        this.notifications.addAll(other.notifications);
        this.attributes.addAll(other.attributes);
        this.dependencies = other.dependencies == null ? null : new ArrayList<>(other.dependencies);
        this.outputs = other.outputs == null ? null : new ArrayList<>(other.outputs);
        this.exportFormats.putAll(other.exportFormats);
        this.categorizables.addAll(other.categorizables);
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

    public SpatialExtent getSpatialExtent() {
        return spatialExtent;
    }

    public void setSpatialExtent(SpatialExtent spatialExtent) {
        this.spatialExtent = spatialExtent;
    }

    public List<AttributeReference> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeReference> attributes) {
        this.attributes = attributes;
    }

    public List<AttributeReference> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<AttributeReference> dependencies) {
        this.dependencies = dependencies;
    }

    public Map<String, String> getExportFormats() {
        return exportFormats;
    }

    public void setExportFormats(Map<String, String> exportFormats) {
        this.exportFormats = exportFormats;
    }

    public List<String> getCategorizables() {
        return categorizables;
    }

    public void setCategorizables(List<String> categorizables) {
        this.categorizables = categorizables;
    }

    public AvailabilityReference getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityReference availability) {
        this.availability = availability;
    }

}

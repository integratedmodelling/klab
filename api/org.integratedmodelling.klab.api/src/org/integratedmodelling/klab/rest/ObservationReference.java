package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;

public class ObservationReference implements IObservationReference {

    /**
     * Export formats for each observation.
     * 
     * @author ferdinando.villa
     *
     */
    public static class ExportFormat {

        private String label;
        private String value;
        private String adapter;
        private String extension;

        public ExportFormat() {
        }

        public ExportFormat(String label, String value, String adapter, String extension) {
            this.label = label;
            this.value = value;
            this.adapter = adapter;
            this.extension = extension;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getAdapter() {
            return adapter;
        }

        public void setAdapter(String adapter) {
            this.adapter = adapter;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    public enum ObservationType {
        PROCESS, STATE, SUBJECT, CONFIGURATION, EVENT, RELATIONSHIP, GROUP, VIEW
    }

    /**
     * The value of this enum defines the type of values this observation contains. All non-quality
     * observations have value type VOID.
     * 
     * @author ferdinando.villa
     *
     */
    public enum ValueType {
        VOID, NUMBER, BOOLEAN, CATEGORY, DISTRIBUTION
    }

    /**
     * The value of this enum represents both the "nature" of the data representation and its
     * natural geometry, specifying a way for an observation to be encoded when any representation
     * of it is requested.
     * 
     * TODO the name is really not right. At the moment it is part of observation bean methods so
     * changing it is a little complex. It should probably be called DataEncoding or something like
     * that.
     * 
     * @author ferdinando.villa
     *
     */
    public enum GeometryType {

        /**
         * A grid raster map with a number, boolean or category value type.
         */
        RASTER,

        /**
         * A single shape, of type determined by {@link ObservationReference#getShapeType()}. May be
         * providing spatial context for a timeseries or other value, so not necessarily void.
         */
        SHAPE,

        /**
         * A scalar value with no temporal or spatial representation. In this case,
         * {@link ObservationReference#getLiteralValue()} returns a string representation of the
         * scalar value.
         */
        SCALAR,

        /**
         * Observation is distributed in time. It may or may not be located in space, in which case
         * {@link ObservationReference#getGeometryTypes()} will contain also the spatial type). An
         * observation may be a scalar at initialization and be turned into a timeseries after time
         * transitions. The value type is never void if this is returned.
         */
        TIMESERIES,

        /**
         * Observation is a structure of relationships connecting subjects.
         * {@link ObservationReference#getStructure()} will return all vertices and connections The
         * spatial and temporal character of the observations linked and linking will determine the
         * best way of displaying the connections.
         */
        NETWORK,

        /**
         * One possible "other" representations for derived products to be defined later. No worries
         * about this now, for later use.
         */
        PROPORTIONS,

        /**
         * Used in requests to get the colormap instead of the image for an observation with
         * distributed values.
         */
        COLORMAP,

        /**
         * Used in requests to get the values in tabular form instead of another representation.
         */
        TABLE,

        /**
         * Used in request to get the "raw" export data paired with an output format.
         */
        RAW,

        /**
         * Corresponding to geometry #... - a folder, empty or ready to receive other observations.
         * Communicated always with childrenCount == 0, children may arrive later.
         */
        GROUP
    }

    /**
     * This is set only when the geometry types contain SHAPE.
     */
    private IShape.Type shapeType = IShape.Type.EMPTY;
    private String encodedShape;
    private String spatialProjection;
    private String id;
    private String rootContextId;
    private String label;
    private String observable;
    private String exportLabel;
    private ValueType valueType;
    private ObservationType observationType;
    private Set<IKimConcept.Type> semantics = new HashSet<>();
    private Set<GeometryType> geometryTypes = new HashSet<>();
    private String literalValue;
    private List<String> traits = new ArrayList<>();
    private Map<String, String> metadata = new HashMap<>();
    private String taskId;
    private String contextId;
    private boolean empty;
    private Style style;
    private boolean primary;
    private DataSummary dataSummary;
    private List<ExportFormat> exportFormats = new ArrayList<>();
    // only non-null in views
    private String originalGroupId;
    private boolean alive;
    private boolean main;
    private boolean dynamic;

    /*
     * Only sent when the observation redefines the scale (new context)
     */
    private ScaleReference scaleReference;

    /**
     * Number of children, some or all of which may be in the children array according to request
     * parameters.
     */
    private int childrenCount;

    /**
     * All roles adopted by this observation, either through the semantics or by model/user action.
     */
    private List<String> roles = new ArrayList<>();

    /**
     * The main observable type for the observation.
     */
    public IKimConcept.Type observableType;

    /**
     * ID of parent observation. Not null only in root observation.
     */
    private String parentId;

    /**
     * ID of physical parent artifact. Either the logical parentId, a group ID if the observation is
     * part of a group, or the ID of a view if the observation was returned as part of a
     * getChildren() request on a view.
     */
    private String parentArtifactId;

    /**
     * Child observations. This may be empty despite the existence of children, or only partially
     * filled, according to the type of call that generated the object.
     */
    private List<ObservationReference> children = new ArrayList<>();

    /**
     * Actions connected with each observation
     */
    private List<ActionReference> actions = new ArrayList<>();

    /**
     * Structure pertaining to the observation in case the observation is a network configuration.
     */
    private List<Connection> structure = new ArrayList<>();

    /**
     * The time last seen by this observation; -1 in non-temporal contexts, beginning of
     * initialization transition before transitions are started.
     */
    private long contextTime = -1;

    /**
     * Timestamp of creation of the observation.
     */
    private long creationTime;

    /**
     * Full URN of the observation.
     */
    private String urn;

    /**
     * Number of values for states; 0 otherwise
     */
    private long valueCount;

    /**
     * True <b>after</b> a notification was made of this observation.
     */
    private boolean previouslyNotified;

    /**
     * Set to true when descriptor is generated after full contextualization.
     */
    private boolean contextualized;

    /**
     * Only updated for root contexts; contains the timestamp of last update for the entire
     * observation structure.
     */
    private long lastUpdate;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDataSummary(DataSummary dataSummary) {
        this.dataSummary = dataSummary;
    }

    @Override
    public String getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(String literalValue) {
        this.literalValue = literalValue;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<Connection> getStructure() {
        return structure;
    }

    public void setStructure(List<Connection> structure) {
        this.structure = structure;
    }

    @Override
    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public long getContextTime() {
        return contextTime;
    }

    public void setContextTime(long contextTime) {
        this.contextTime = contextTime;
    }

    @Override
    public Set<GeometryType> getGeometryTypes() {
        return geometryTypes;
    }

    public void setGeometryTypes(Set<GeometryType> geometryTypes) {
        this.geometryTypes = geometryTypes;
    }

    @Override
    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public IKimConcept.Type getObservableType() {
        return observableType;
    }

    public void setObservableType(IKimConcept.Type observableType) {
        this.observableType = observableType;
    }

    @Override
    public IShape.Type getShapeType() {
        return shapeType;
    }

    public void setShapeType(IShape.Type shapeType) {
        this.shapeType = shapeType;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    @Override
    public String getUrn() {
        return this.urn;
    }

    @Override
    public String getObservable() {
        return observable;
    }

    public void setObservable(String observable) {
        this.observable = observable;
    }

    @Override
    public String getEncodedShape() {
        return encodedShape;
    }

    @Override
    public String getSpatialProjection() {
        return spatialProjection;
    }

    public void setSpatialProjection(String spatialProjection) {
        this.spatialProjection = spatialProjection;
    }

    public void setEncodedShape(String encodedShape) {
        this.encodedShape = encodedShape;
    }

    @Override
    public List<ObservationReference> getChildren() {
        return children;
    }

    public void setChildren(List<ObservationReference> children) {
        this.children = children;
    }

    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public Set<IKimConcept.Type> getSemantics() {
        return semantics;
    }

    public void setSemantics(Set<IKimConcept.Type> semantics) {
        this.semantics = semantics;
    }

    @Override
    public ObservationType getObservationType() {
        return observationType;
    }

    public void setObservationType(ObservationType observationType) {
        this.observationType = observationType;
    }

    @Override
    public String toString() {
        return "ObservationReference [type =  " + observationType + ", id=" + id + ", observable=" + observable + ", semantics="
                + semantics + ", childrenCount=" + childrenCount + ", parent = " + parentId + "]";
    }

    public void setValueCount(long size) {
        this.valueCount = size;
    }

    @Override
    public long getValueCount() {
        return this.valueCount;
    }

    /**
     * Set the task ID for observations that are created by the runtime, as opposed to those
     * retrieved from previous computations, network or databases.
     * 
     * @param taskId
     * @return
     */
    public ObservationReference withTaskId(String taskId) {
        this.setTaskId(taskId);
        return this;
    }

    public ObservationReference withContextId(String contextId) {
        this.setContextId(contextId);
        return this;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<ActionReference> getActions() {
        return actions;
    }

    public void setActions(List<ActionReference> actions) {
        this.actions = actions;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public DataSummary getDataSummary() {
        return dataSummary;
    }

    public List<ExportFormat> getExportFormats() {
        return exportFormats;
    }

    public void setExportFormats(List<ExportFormat> exportFormats) {
        this.exportFormats = exportFormats;
    }

    public String getRootContextId() {
        return rootContextId;
    }

    public void setRootContextId(String rootContextId) {
        this.rootContextId = rootContextId;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isPreviouslyNotified() {
        return previouslyNotified;
    }

    public void setPreviouslyNotified(boolean previouslyNotified) {
        this.previouslyNotified = previouslyNotified;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public ScaleReference getScaleReference() {
        return scaleReference;
    }

    public void setScaleReference(ScaleReference scaleReference) {
        this.scaleReference = scaleReference;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getParentArtifactId() {
        return parentArtifactId;
    }

    public void setParentArtifactId(String parentArtifactId) {
        this.parentArtifactId = parentArtifactId;
    }

    public String getExportLabel() {
        return exportLabel;
    }

    public void setExportLabel(String exportLabel) {
        this.exportLabel = exportLabel;
    }

    public String getOriginalGroupId() {
        return originalGroupId;
    }

    public void setOriginalGroupId(String originalGroupId) {
        this.originalGroupId = originalGroupId;
    }

    /**
     * True if there is a live actor associated.
     * 
     * @return
     */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        ObservationReference other = (ObservationReference) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /**
     * Apply a change to a previously created observation and modify it.
     * 
     * @param change
     */
    public void applyChange(ObservationChange change) {
        switch(change.getType()) {
        case AttributeChange:
            break;
        case NameChange:
            break;
        case SpatialChange:
            break;
        case SpatialTranslation:
            break;
        case StructureChange:
            this.childrenCount = change.getNewSize();
            break;
        case Termination:
            this.setAlive(false);
            break;
        case ValueChange:
            break;
        case BringForward:
            this.setMain(true);
            break;
        case SemanticsChange:
            // the semantic flags should not have changed
            this.setObservable(change.getNewSemantics());
            break;
        case ContextualizationCompleted:
            break;
        }
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isContextualized() {
        return contextualized;
    }

    public void setContextualized(boolean contextualized) {
        this.contextualized = contextualized;
    }

}
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

	public enum ObservationType {
		PROCESS, STATE, SUBJECT, CONFIGURATION, EVENT, RELATIONSHIP
	}

	/**
	 * The value of this enum defines the type of values this observation contains.
	 * All non-quality observations have value type VOID.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public enum ValueType {
		VOID, NUMBER, BOOLEAN, CATEGORY, DISTRIBUTION
	}

	/**
	 * The value of this enum determines the way the observation should be
	 * visualized.
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
		 * A single shape, of type determined by
		 * {@link ObservationReference#getShapeType()}. May be providing spatial context
		 * for a timeseries or other value, so not necessarily void.
		 */
		SHAPE,

		/**
		 * A scalar value with no temporal or spatial representation. In this case,
		 * {@link ObservationReference#getLiteralValue()} returns a string
		 * representation of the scalar value.
		 */
		SCALAR,

		/**
		 * Observation is distributed in time. It may or may not be located in space, in
		 * which case {@link ObservationReference#getGeometryTypes()} will contain also
		 * the spatial type). An observation may be a scalar at initialization and be
		 * turned into a timeseries after time transitions. The value type is never void
		 * if this is returned.
		 */
		TIMESERIES,

		/**
		 * Observation is a structure of relationships connecting subjects.
		 * {@link ObservationReference#getStructure()} will return all connections. The
		 * spatial and temporal character of the observations linked and linking will
		 * determine the best way of displaying the connections.
		 */
		NETWORK,

		/**
		 * One possible "other" representations for derived products to be defined
		 * later. No worries about this now, for later use.
		 */
		PROPORTIONS
	}

	/**
	 * This is set only when the geometry types contain SHAPE.
	 */
	private IShape.Type shapeType = IShape.Type.EMPTY;
	private String encodedShape;
	private String spatialProjection;
	private String id;
	private String label;
	private String observable;
	private ValueType valueType;
	private ObservationType observationType;
	private Set<IKimConcept.Type> semantics = new HashSet<>();
	private Set<GeometryType> geometryTypes = new HashSet<>();
	private String literalValue;
	// private List<ObservationReference> siblings = new ArrayList<>();
	private List<String> traits = new ArrayList<>();
	private Map<String, String> metadata = new HashMap<>();
	private String taskId;
	private boolean empty;
	private Style style;
	private boolean main;
	private DataSummary dataSummary;
	
	public void setDataSummary(DataSummary dataSummary) {
		this.dataSummary = dataSummary;
	}

	/**
	 * The observation may have more sibling than are found in the sibling list.
	 * This contains the number of children so it can be reported and lazy calls for
	 * the full list are possible.
	 */
	private int siblingCount;

	/**
	 * If this observation is part of a group with >1 siblings, this will be set to
	 * a unique ID for the group so that the siblings with the same ID can be
	 * matched and a folder created.
	 */
	private String folderId;

	/**
	 * If this observation is part of a group with >1 siblings, each sibling will
	 * have the display label of the folder that should contain them.
	 */
	private String folderLabel;

	/**
	 * All roles adopted by this observation, either through the semantics or by
	 * model/user action.
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
	 * Child observations. This may be empty despite the existence of children, or
	 * only partially filled, according to the type of call that generated the
	 * object.
	 */
	private List<ObservationReference> children = new ArrayList<>();

	/**
	 * Sibling observations. This may be empty despite the existence of siblings, or
	 * only partially filled, according to the type of call that generated the
	 * object.
	 */
	private List<ObservationReference> siblings = new ArrayList<>();

	/**
	 * Actions connected with each observation
	 */
	private List<ActionReference> actions = new ArrayList<>();

	/**
	 * Structure pertaining to the observation in case the observation is a network
	 * configuration.
	 */
	private List<Connection> structure = new ArrayList<>();

	/**
	 * The time last seen by this observation; -1 in non-temporal contexts,
	 * beginning of initialization transition before transitions are started.
	 */
	private long contextTime = -1;

	/**
	 * Full URN of the observation.
	 */
	private String urn;

	/**
	 * Number of values for states; 0 otherwise
	 */
	private long valueCount;

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getLiteralValue()
	 */
	@Override
	public String getLiteralValue() {
		return literalValue;
	}

	public void setLiteralValue(String literalValue) {
		this.literalValue = literalValue;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getParentId()
	 */
	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getStructure()
	 */
	@Override
	public List<Connection> getStructure() {
		return structure;
	}

	public void setStructure(List<Connection> structure) {
		this.structure = structure;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getValueType()
	 */
	@Override
	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getContextTime()
	 */
	@Override
	public long getContextTime() {
		return contextTime;
	}

	public void setContextTime(long contextTime) {
		this.contextTime = contextTime;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getGeometryTypes()
	 */
	@Override
	public Set<GeometryType> getGeometryTypes() {
		return geometryTypes;
	}

	public void setGeometryTypes(Set<GeometryType> geometryTypes) {
		this.geometryTypes = geometryTypes;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getTraits()
	 */
	@Override
	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getRoles()
	 */
	@Override
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getObservableType()
	 */
	@Override
	public IKimConcept.Type getObservableType() {
		return observableType;
	}

	public void setObservableType(IKimConcept.Type observableType) {
		this.observableType = observableType;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getShapeType()
	 */
	@Override
	public IShape.Type getShapeType() {
		return shapeType;
	}

	public void setShapeType(IShape.Type shapeType) {
		this.shapeType = shapeType;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getSiblingCount()
	 */
	@Override
	public int getSiblingCount() {
		return siblingCount;
	}

	public void setSiblingCount(int siblingCount) {
		this.siblingCount = siblingCount;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getUrn()
	 */
	@Override
	public String getUrn() {
		return this.urn;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getObservable()
	 */
	@Override
	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getEncodedShape()
	 */
	@Override
	public String getEncodedShape() {
		return encodedShape;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getSpatialProjection()
	 */
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

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getFolderId()
	 */
	@Override
	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getFolderLabel()
	 */
	@Override
	public String getFolderLabel() {
		return folderLabel;
	}

	public void setFolderLabel(String folderLabel) {
		this.folderLabel = folderLabel;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getChildren()
	 */
	@Override
	public List<ObservationReference> getChildren() {
		return children;
	}

	public void setChildren(List<ObservationReference> children) {
		this.children = children;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getMetadata()
	 */
	@Override
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getSiblings()
	 */
	@Override
	public List<ObservationReference> getSiblings() {
		return siblings;
	}

	public void setSiblings(List<ObservationReference> siblings) {
		this.siblings = siblings;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getSemantics()
	 */
	@Override
	public Set<IKimConcept.Type> getSemantics() {
		return semantics;
	}

	public void setSemantics(Set<IKimConcept.Type> semantics) {
		this.semantics = semantics;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getObservationType()
	 */
	@Override
	public ObservationType getObservationType() {
		return observationType;
	}

	public void setObservationType(ObservationType observationType) {
		this.observationType = observationType;
	}

	@Override
	public String toString() {
		return "ObservationReference [id=" + id + ", observable=" + observable + ", semantics=" + semantics
				+ ", siblingCount=" + siblingCount + "]";
	}

	public void setValueCount(long size) {
		this.valueCount = size;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getValueCount()
	 */
	@Override
	public long getValueCount() {
		return this.valueCount;
	}

	/**
	 * Set the task ID for observations that are created by the runtime, as opposed
	 * to those retrieved from previous computations, network or databases.
	 * 
	 * @param taskId
	 * @return
	 */
	public IObservationReference withTaskId(String taskId) {
		this.setTaskId(taskId);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getTaskId()
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getActions()
	 */
	@Override
	public List<ActionReference> getActions() {
		return actions;
	}

	public void setActions(List<ActionReference> actions) {
		this.actions = actions;
	}

	/* (non-Javadoc)
	 * @see org.integratedmodelling.klab.rest.IObservationReference#isEmpty()
	 */
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

	/**
	 * Observations tagged main should be brought to the user's attention preferentially.
	 * 
	 * @return
	 */
	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@Override
	public DataSummary getDataSummary() {
		return dataSummary;
	}

}
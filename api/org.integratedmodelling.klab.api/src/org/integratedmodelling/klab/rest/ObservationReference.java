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
		PROCESS, STATE, SUBJECT, CONFIGURATION, EVENT, RELATIONSHIP, GROUP
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
	 * The value of this enum represents both the "nature" of the data
	 * representation and its natural geometry, specifying a way for an observation
	 * to be encoded when any representation of it is requested.
	 * 
	 * TODO the name is really not right. At the moment it is part of observation
	 * bean methods so changing it is a little complex. It should probably be called
	 * DataEncoding or something like that.
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
		 * {@link ObservationReference#getStructure()} will return all vertices and
		 * connections The spatial and temporal character of the observations linked and
		 * linking will determine the best way of displaying the connections.
		 */
		NETWORK,

		/**
		 * One possible "other" representations for derived products to be defined
		 * later. No worries about this now, for later use.
		 */
		PROPORTIONS,

		/**
		 * Used in requests to get the colormap instead of the image for an observation
		 * with distributed values.
		 */
		COLORMAP,

		/**
		 * Used in requests to get the values in tabular form instead of another
		 * representation.
		 */
		TABLE,

		/**
		 * Used in request to get the "raw" export data paired with an output format.
		 */
		RAW,

		/**
		 * Corresponding to geometry #... - a folder, empty or ready to receive other
		 * observations. Communicated always with childrenCount == 0, children may
		 * arrive later.
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
	private boolean empty;
	private Style style;
	private boolean main;
	private boolean primary;
	private DataSummary dataSummary;
	private List<ExportFormat> exportFormats = new ArrayList<>();
	/*
	 * Only sent when the observation redefines the scale (new context)
	 */
	private ScaleReference scaleReference;

	public void setDataSummary(DataSummary dataSummary) {
		this.dataSummary = dataSummary;
	}

	// /**
	// * The observation may have more sibling than are found in the sibling list.
	// * This contains the number of children so it can be reported and lazy calls
	// for
	// * the full list are possible.
	// */
	// private int siblingCount;

	/**
	 * Number of children, some or all of which may be in the children array
	 * according to request parameters.
	 */
	private int childrenCount;

	// /**
	// * If this observation is part of a group with >1 siblings, this will be set
	// to
	// * a unique ID for the group so that the siblings with the same ID can be
	// * matched and a folder created.
	// */
	// private String folderId;
	//
	// /**
	// * If this observation is part of a group with >1 siblings, each sibling will
	// * have the display label of the folder that should contain them. The folder
	// * will have been previously communicated as a group in case .
	// */
	// private String folderLabel;

	// /**
	// * If the observation is part of a group, this will be the ID of the group.
	// Note
	// * that folderLabel will be the label of the group; on the other hand, not all
	// * with folderLabel != null will be in a group.
	// */
	// private String groupId;

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
	 * ID of physical parent artifact. Either the logical parentId or a group ID if
	 * the observation is part of a group.
	 */
	private String parentArtifactId;

	/**
	 * Child observations. This may be empty despite the existence of children, or
	 * only partially filled, according to the type of call that generated the
	 * object.
	 */
	private List<ObservationReference> children = new ArrayList<>();

	// /**
	// * Sibling observations. This may be empty despite the existence of siblings,
	// or
	// * only partially filled, according to the type of call that generated the
	// * object.
	// */
	// private List<ObservationReference> siblings = new ArrayList<>();

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
	 * Only updated for root contexts; contains the timestamp of last update for the
	 * entire observation structure.
	 */
	private long lastUpdate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getLiteralValue()
	 */
	@Override
	public String getLiteralValue() {
		return literalValue;
	}

	public void setLiteralValue(String literalValue) {
		this.literalValue = literalValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getParentId()
	 */
	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getStructure()
	 */
	@Override
	public List<Connection> getStructure() {
		return structure;
	}

	public void setStructure(List<Connection> structure) {
		this.structure = structure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getValueType()
	 */
	@Override
	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getContextTime()
	 */
	@Override
	public long getContextTime() {
		return contextTime;
	}

	public void setContextTime(long contextTime) {
		this.contextTime = contextTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getGeometryTypes()
	 */
	@Override
	public Set<GeometryType> getGeometryTypes() {
		return geometryTypes;
	}

	public void setGeometryTypes(Set<GeometryType> geometryTypes) {
		this.geometryTypes = geometryTypes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getTraits()
	 */
	@Override
	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getRoles()
	 */
	@Override
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getObservableType()
	 */
	@Override
	public IKimConcept.Type getObservableType() {
		return observableType;
	}

	public void setObservableType(IKimConcept.Type observableType) {
		this.observableType = observableType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getShapeType()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getUrn()
	 */
	@Override
	public String getUrn() {
		return this.urn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getObservable()
	 */
	@Override
	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getEncodedShape()
	 */
	@Override
	public String getEncodedShape() {
		return encodedShape;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getSpatialProjection(
	 * )
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

	// /*
	// * (non-Javadoc)
	// *
	// * @see org.integratedmodelling.klab.rest.IObservationReference#getFolderId()
	// */
	// @Override
	// public String getFolderId() {
	// return folderId;
	// }
	//
	// public void setFolderId(String folderId) {
	// this.folderId = folderId;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// org.integratedmodelling.klab.rest.IObservationReference#getFolderLabel()
	// */
	// @Override
	// public String getFolderLabel() {
	// return folderLabel;
	// }
	//
	// public void setFolderLabel(String folderLabel) {
	// this.folderLabel = folderLabel;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getChildren()
	 */
	@Override
	public List<ObservationReference> getChildren() {
		return children;
	}

	public void setChildren(List<ObservationReference> children) {
		this.children = children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getMetadata()
	 */
	@Override
	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see org.integratedmodelling.klab.rest.IObservationReference#getSiblings()
	// */
	// @Override
	// public List<ObservationReference> getSiblings() {
	// return siblings;
	// }
	//
	// public void setSiblings(List<ObservationReference> siblings) {
	// this.siblings = siblings;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getSemantics()
	 */
	@Override
	public Set<IKimConcept.Type> getSemantics() {
		return semantics;
	}

	public void setSemantics(Set<IKimConcept.Type> semantics) {
		this.semantics = semantics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.integratedmodelling.klab.rest.IObservationReference#getObservationType()
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
		return "ObservationReference [type =  " + observationType + ", id=" + id + ", observable=" + observable
				+ ", semantics=" + semantics + ", childrenCount=" + childrenCount + ", parent = " + parentId + "]";
	}

	public void setValueCount(long size) {
		this.valueCount = size;
	}

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getTaskId()
	 */
	@Override
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.integratedmodelling.klab.rest.IObservationReference#getActions()
	 */
	@Override
	public List<ActionReference> getActions() {
		return actions;
	}

	public void setActions(List<ActionReference> actions) {
		this.actions = actions;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * Observations tagged main should be brought to the user's attention
	 * preferentially.
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
	//
	// public String getGroupId() {
	// return groupId;
	// }
	//
	// public void setGroupId(String groupId) {
	// this.groupId = groupId;
	// }

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

}
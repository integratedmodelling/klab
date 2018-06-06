package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;

public class ObservationReference {

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
		 * turned into a timeseries after time transitions. The value type is never
		 * void if this is returned.
		 */
		TIMESERIES,

		/**
		 * One possible "other" representations for derived products to be
		 * defined later. No worries about this now, for later use.
		 */
		PROPORTIONS
	}

	/**
	 * This is set only when the geometry types contain SHAPE.
	 */
	private IShape.Type shapeType = IShape.Type.EMPTY;
	private String encodedShape;
	private String id;
	private String label;
	private String observable;
	private ValueType valueType;
	private Set<GeometryType> geometryTypes = new HashSet<>();
	private String literalValue;
	// private List<ObservationReference> siblings = new ArrayList<>();
	private List<String> traits = new ArrayList<>();

	/**
	 * The observation may have more sibling than are found in the sibling list.
	 * This contains the number of children so it can be reported and lazy calls for
	 * the full list are possible.
	 */
	private int siblingCount;

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
	 * Structure pertaining to the observation in case the observation is a network
	 * configuration.
	 */
	private List<Connection> structure = new ArrayList<>();

	/**
	 * When the observation has multiple values, this is set to the percentage of
	 * those that are no-data.
	 */
	private double nodataPercentage;

	/**
	 * When the observation has multiple numeric values, this is set to the minimum
	 * value of their range.
	 */
	private double minValue = Double.NaN;

	/**
	 * When the observation has multiple numeric values, this is set to the maximum
	 * value of their range.
	 */
	private double maxValue = Double.NaN;

	/**
	 * The time last seen by this observation; -1 in non-temporal contexts,
	 * beginning of initialization transition before transitions are started.
	 */
	private long contextTime = -1;

	/**
	 * Full URN of the observation.
	 */
	private String urn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLiteralValue() {
		return literalValue;
	}

	public void setLiteralValue(String literalValue) {
		this.literalValue = literalValue;
	}

	// public List<ObservationReference> getSiblings() {
	// return siblings;
	// }
	//
	// public void setChildren(List<ObservationReference> siblings) {
	// this.siblings = siblings;
	// }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Connection> getStructure() {
		return structure;
	}

	public void setStructure(List<Connection> structure) {
		this.structure = structure;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public double getNodataPercentage() {
		return nodataPercentage;
	}

	public void setNodataPercentage(double nodataPercentage) {
		this.nodataPercentage = nodataPercentage;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public long getContextTime() {
		return contextTime;
	}

	public void setContextTime(long contextTime) {
		this.contextTime = contextTime;
	}

	public Set<GeometryType> getGeometryTypes() {
		return geometryTypes;
	}

	public void setGeometryTypes(Set<GeometryType> geometryTypes) {
		this.geometryTypes = geometryTypes;
	}

	public List<String> getTraits() {
		return traits;
	}

	public void setTraits(List<String> traits) {
		this.traits = traits;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public IKimConcept.Type getObservableType() {
		return observableType;
	}

	public void setObservableType(IKimConcept.Type observableType) {
		this.observableType = observableType;
	}

	public IShape.Type getShapeType() {
		return shapeType;
	}

	public void setShapeType(IShape.Type shapeType) {
		this.shapeType = shapeType;
	}

	public int getSiblingCount() {
		return siblingCount;
	}

	public void setSiblingCount(int siblingCount) {
		this.siblingCount = siblingCount;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getUrn() {
		return this.urn;
	}

	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

	/**
	 * If {@link #getShapeType()} returns anything other than VOID, this will return a 
	 * string-codified shape (WKT or WKB).
	 * 
	 * @return the codified shape
	 */
	public String getEncodedShape() {
		return encodedShape;
	}

	public void setEncodedShape(String encodedShape) {
		this.encodedShape = encodedShape;
	}

}

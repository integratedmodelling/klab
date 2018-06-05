package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class ObservationReference {
	
	public enum DataType {
		NUMBER,
		BOOLEAN,
		CATEGORY
	}
	
	public enum GeometryType {
		RASTER,
		SHAPE,
		SCALAR,
		/**
		 * Observation is simply a grouping of other observations with a common type
		 * or label. In this case the ID will be null.
		 */
		FOLDER,
		TIMESERIES,
		PROPORTIONS
	}
		
	private String id;
	private String label;
	private DataType dataType;
	private GeometryType geometryType;
	private String literalValue;
	private List<ObservationReference> children;
	private String parentId;
	private List<Connection> structure = new ArrayList<>();
	private double nodataPercentage;
	private double minValue;
	private double maxValue;
	
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
	public List<ObservationReference> getChildren() {
		return children;
	}
	public void setChildren(List<ObservationReference> children) {
		this.children = children;
	}
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
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public GeometryType getGeometryType() {
		return geometryType;
	}
	public void setGeometryType(GeometryType geometryType) {
		this.geometryType = geometryType;
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

}

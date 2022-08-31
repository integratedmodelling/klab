package org.integratedmodelling.klab.api.runtime.rest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.rest.ActionReference;
import org.integratedmodelling.klab.rest.Connection;
import org.integratedmodelling.klab.rest.DataSummary;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.ObservationReference.ObservationType;
import org.integratedmodelling.klab.rest.ObservationReference.ValueType;

public interface IObservationReference {

	String getId();

	String getLabel();

	String getLiteralValue();

	String getParentId();

	List<Connection> getStructure();

	ValueType getValueType();

	long getContextTime();

	Set<GeometryType> getGeometryTypes();

	List<String> getTraits();

	List<String> getRoles();

	IKimConcept.Type getObservableType();

	IShape.Type getShapeType();

	int getChildrenCount();

	String getUrn();

	String getObservable();

	/**
	 * If {@link #getShapeType()} returns anything other than VOID, this will return
	 * a string-codified shape (WKT or WKB).
	 * 
	 * @return the codified shape
	 */
	String getEncodedShape();

	String getSpatialProjection();

	List<ObservationReference> getChildren();

	Map<String, String> getMetadata();

	Set<IKimConcept.Type> getSemantics();

	ObservationType getObservationType();

	long getValueCount();

	String getTaskId();

	List<ActionReference> getActions();

	boolean isEmpty();
	
	DataSummary getDataSummary();

}
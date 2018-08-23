package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.rest.ActionReference;
import org.integratedmodelling.klab.rest.Connection;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.ObservationReference.ObservationType;
import org.integratedmodelling.klab.rest.ObservationReference.ValueType;

public class EObservationReference implements IObservationReference, ERuntimeObject {

	private IObservationReference delegate;
	private ERuntimeObject parent;
	private List<EObservationReference> children = new ArrayList<>();
	private ETaskReference creator;

	public EObservationReference(ObservationReference observationReference) {
		this.delegate = observationReference;
	}

	public EObservationReference(ObservationReference observationReference, ERuntimeObject parent) {
		this.delegate = observationReference;
		this.parent = parent;
	}

	@Override
	public ERuntimeObject getEParent() {
		return parent;
	}

	@Override
	public ERuntimeObject[] getEChildren(DisplayPriority displayPriority) {
		List<ERuntimeObject> ret = new ArrayList<>();
		if (displayPriority == DisplayPriority.TASK_FIRST && creator != null) {
			ret.add(creator);
		} else {
			ret.addAll(children);
		}
		return ret.toArray(new ERuntimeObject[ret.size()]);
	}

	// --- model

	public void setCreator(ETaskReference task) {

	}

	// --- delegate methods

	public String getId() {
		return delegate.getId();
	}

	public String getLabel() {
		return delegate.getLabel();
	}

	public String getLiteralValue() {
		return delegate.getLiteralValue();
	}

	public String getParentId() {
		return delegate.getParentId();
	}

	public List<Connection> getStructure() {
		return delegate.getStructure();
	}

	public ValueType getValueType() {
		return delegate.getValueType();
	}

	public double getNodataPercentage() {
		return delegate.getNodataPercentage();
	}

	public double getMinValue() {
		return delegate.getMinValue();
	}

	public double getMaxValue() {
		return delegate.getMaxValue();
	}

	public long getContextTime() {
		return delegate.getContextTime();
	}

	public Set<GeometryType> getGeometryTypes() {
		return delegate.getGeometryTypes();
	}

	public List<String> getTraits() {
		return delegate.getTraits();
	}

	public List<String> getRoles() {
		return delegate.getRoles();
	}

	public Type getObservableType() {
		return delegate.getObservableType();
	}

	public org.integratedmodelling.klab.api.observations.scale.space.IShape.Type getShapeType() {
		return delegate.getShapeType();
	}

	public int getSiblingCount() {
		return delegate.getSiblingCount();
	}

	public String getUrn() {
		return delegate.getUrn();
	}

	public String getObservable() {
		return delegate.getObservable();
	}

	public String getEncodedShape() {
		return delegate.getEncodedShape();
	}

	public String getSpatialProjection() {
		return delegate.getSpatialProjection();
	}

	public String getFolderId() {
		return delegate.getFolderId();
	}

	public String getFolderLabel() {
		return delegate.getFolderLabel();
	}

	public List<ObservationReference> getChildren() {
		return delegate.getChildren();
	}

	public Map<String, String> getMetadata() {
		return delegate.getMetadata();
	}

	public List<ObservationReference> getSiblings() {
		return delegate.getSiblings();
	}

	public Set<Type> getSemantics() {
		return delegate.getSemantics();
	}

	public ObservationType getObservationType() {
		return delegate.getObservationType();
	}

	public long getValueCount() {
		return delegate.getValueCount();
	}

	public String getTaskId() {
		return delegate.getTaskId();
	}

	public List<ActionReference> getActions() {
		return delegate.getActions();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

}

package org.integratedmodelling.klab.ide.navigator.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.ActionReference;
import org.integratedmodelling.klab.rest.Connection;
import org.integratedmodelling.klab.rest.DataSummary;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.ObservationReference.ObservationType;
import org.integratedmodelling.klab.rest.ObservationReference.ValueType;
import org.integratedmodelling.klab.utils.Pair;

public class EObservationReference implements IObservationReference, ERuntimeObject {

    private IObservationReference delegate;
    private String                parentTaskId;
    private String                parentArtifactId;
    private List<String>          childObservations = new ArrayList<>();
    // if true, the observation was sent a second time, i.e. it's the main target of its task
	private boolean focal = false;
	private String mainArtifactId; // set = I depend on mainArtifact
	boolean main; // true = I am mainArtifact

    public EObservationReference(ObservationReference observationReference) {
        this.delegate = observationReference;
    }

    public EObservationReference(ObservationReference observationReference, String parentTask,
            String parentArtifact) {
        this.delegate = observationReference;
        this.parentTaskId = parentTask;
        this.parentArtifactId = parentArtifact;
    }

    @Override
    public ERuntimeObject getEParent(DisplayPriority priority) {
    	if (mainArtifactId != null) {
    		return Activator.session().getObservation(mainArtifactId);
    	}
    	return priority == DisplayPriority.ARTIFACTS_FIRST
                ? Activator.session().getObservation(parentArtifactId)
                : Activator.session().getTask(parentTaskId);
    }

    @Override
    public ERuntimeObject[] getEChildren(DisplayPriority displayPriority, Level level) {
        List<ERuntimeObject> ret = new ArrayList<>();
        if (displayPriority == DisplayPriority.ARTIFACTS_FIRST || main) {
            for (String child : childObservations) {
                ret.add(Activator.session().getObservation(child));
            }
        }
        return ret.toArray(new ERuntimeObject[ret.size()]);
    }

    public void addChildObservationId(String id) {
        this.childObservations.add(id);
    }

    @Override
    public List<Pair<String, String>> getProperties() {
        List<Pair<String, String>> ret = new ArrayList<>();
        ret.add(new Pair<>("Observable", delegate.getObservable()));
        if (delegate.getGeometryTypes().contains(GeometryType.RASTER)) {
            ret.add(new Pair<>("Minimum value", "" + delegate.getDataSummary().getMinValue()));
            ret.add(new Pair<>("Maximum value", "" + delegate.getDataSummary().getMaxValue()));
            ret.add(new Pair<>("% no-data values", "" + delegate.getDataSummary().getNodataProportion()));
        }
        return ret;
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
        return delegate.getDataSummary().getNodataProportion();
    }

    public double getMinValue() {
        return delegate.getDataSummary().getMinValue();
    }

    public double getMaxValue() {
        return delegate.getDataSummary().getMaxValue();
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

//    public int getSiblingCount() {
//        return delegate.getSiblingCount();
//    }

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

//    public String getFolderId() {
//        return delegate.getFolderId();
//    }
//
//    public String getFolderLabel() {
//        return delegate.getFolderLabel();
//    }

    public List<ObservationReference> getChildren() {
        return delegate.getChildren();
    }

    public Map<String, String> getMetadata() {
        return delegate.getMetadata();
    }

//    public List<ObservationReference> getSiblings() {
//        return delegate.getSiblings();
//    }

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

    public boolean isFocal() {
    	return this.focal;
    }
    
    @Override
    public String toString() {
        return "[OBSERVATION " + getObservationType() + " " + getLabel() + "]";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EObservationReference && ((EObservationReference) o).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public void setCreatorTaskId(String id) {
        this.parentTaskId = id;
    }

	public void setFocal(boolean b) {
		this.focal  = true;
	}

	/**
	 * State that this observation is dependent on another, and set the parent ID so that
	 * we can show it under it.
	 * 
	 * @param obs
	 */
	public void setDependentTo(EObservationReference obs) {
		this.mainArtifactId = obs.getId();
		obs.childObservations.add(this.getId());
		obs.main = true;
		ETaskReference task = Activator.session().getTask(obs.getTaskId());
		task.removeObservation(this.getId());
	}

	@Override
	public DataSummary getDataSummary() {
		return delegate.getDataSummary();
	}

	@Override
	public int getChildrenCount() {
		return delegate.getChildrenCount();
	}

}

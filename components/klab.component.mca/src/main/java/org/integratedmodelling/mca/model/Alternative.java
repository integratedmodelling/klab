package org.integratedmodelling.mca.model;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier.MapClass;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;

public class Alternative implements IAlternative {

	private MapClass mapClass;
	private String id;
	private String observationId;
	private Map<String, Double> values = new HashMap<>();
	private double score;
	boolean valid = true;
	
	public Alternative(IDirectObservation observation) {
		this.observationId = observation.getId();
	}

	public Alternative(MapClass mapClass) {
		this.mapClass = mapClass;
		this.id = this.mapClass.toString();
//		this.valid = mapClass.isValid();
	}

	@Override
	public IDirectObservation getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id == null ? observationId : id;
	}

//	@Override
	public double getValue(String name) {
		if (mapClass != null) {
			return mapClass.getValueOf(name);
		}
		return Double.NaN;
	}

    @Override
    public double getValue(ICriterion criterion) {
        Double ret = values.get(criterion.getName());
        return ret == null ? Double.NaN : ret;
    }
    
    public void setValue(ICriterion criterion, double value) {
        values.put(criterion.getName(), value);
//        if (Double.isNaN(value)) {
//        	valid = false;
//        }
    }

    @Override
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

	@Override
	public boolean isValid() {
		return valid;
	}

}

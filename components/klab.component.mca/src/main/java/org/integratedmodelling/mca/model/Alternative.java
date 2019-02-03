package org.integratedmodelling.mca.model;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier.MapClass;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;

public class Alternative implements IAlternative {

	private MapClass mapClass;
	private String id;
	private String observationId;
	
	public Alternative(IDirectObservation observation) {
		this.observationId = observation.getId();
	}

	public Alternative(MapClass mapClass) {
		this.mapClass = mapClass;
		this.id = this.mapClass.toString();
	}

	@Override
	public IDirectObservation getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

//	@Override
	public double getValue(String name) {
		if (mapClass != null) {
			return mapClass.getValueOf(name);
		}
		return 0;
	}

    @Override
    public double getValue(ICriterion criterion) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public void setValue(ICriterion criterion, double value) {
        // TODO
    }

}

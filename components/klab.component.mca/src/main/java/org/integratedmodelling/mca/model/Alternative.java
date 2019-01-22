package org.integratedmodelling.mca.model;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.components.geospace.processing.MapClassifier.MapClass;
import org.integratedmodelling.mca.api.IAlternative;

public class Alternative implements IAlternative {

	public Alternative(IDirectObservation observation) {
		
	}

	public Alternative(MapClass observation) {
		
	}

	@Override
	public boolean isDistributed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getValueOf(IConcept k, ILocator offset, IDirectObservation offsetContext) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasCriterion(IConcept observable) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDirectObservation getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}

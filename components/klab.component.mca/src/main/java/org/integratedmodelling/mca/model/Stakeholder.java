package org.integratedmodelling.mca.model;

import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.IStakeholder;

public class Stakeholder implements IStakeholder {


	public Stakeholder(ISubject subject) {
		
	}
	
	public Stakeholder(IObservable observable) {
		
	}
	
	@Override
	public IDirectObservation getSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAlternative> getAlternatives(ILocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rankAlternatives(ILocator transition) throws KlabException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canValue(IAlternative alternative) {
		// TODO Auto-generated method stub
		return false;
	}

}

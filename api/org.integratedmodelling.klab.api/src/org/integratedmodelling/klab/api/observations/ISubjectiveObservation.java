package org.integratedmodelling.klab.api.observations;

public abstract interface ISubjectiveObservation extends IObservation {

	void setObserver(IDirectObservation observer);
	
}

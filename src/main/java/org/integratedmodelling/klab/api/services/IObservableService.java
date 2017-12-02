package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.klab.api.knowledge.IConcept;

public interface IObservableService {

	interface Builder {
		IConcept build();
	}

	Builder declare(IConcept main);
	
}

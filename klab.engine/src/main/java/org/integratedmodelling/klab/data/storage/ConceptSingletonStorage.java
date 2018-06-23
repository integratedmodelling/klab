package org.integratedmodelling.klab.data.storage;

import org.integratedmodelling.kim.api.IPrototype.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.scale.Scale;

public class ConceptSingletonStorage extends AbstractSingletonStorage<IConcept> {

	public ConceptSingletonStorage(IObservable observable, Scale scale) {
		super(observable, scale);
	}

	@Override
	protected IConcept setValue(Object value) {
		return (IConcept) value;
	}

	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

}

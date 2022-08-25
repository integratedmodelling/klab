package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IPattern;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Relationship extends CountableObservation implements IRelationship {

	public Relationship(String name, Observable observable, Scale scale, IRuntimeScope context) {
		super(name, observable, scale, context);
	}
	
	protected Relationship(Relationship other) {
		super(other);
	}

	@Override
	public Subject getSource() {
		return (Subject) getScope().getSourceSubject(this);
	}

	@Override
	public Subject getTarget() {
		return (Subject) getScope().getTargetSubject(this);
	}

}

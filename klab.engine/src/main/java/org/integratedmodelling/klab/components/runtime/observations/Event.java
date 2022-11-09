package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IPattern;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Range;

public class Event extends CountableObservation implements IEvent {

	public Event(String name, Observable observable, Scale scale, IRuntimeScope context) {
		super(name, observable, scale, context);
	}
	
	protected Event(Event other) {
		super(other);
	}

	public IArtifact locate(Range timespan) {
		// TODO Auto-generated method stub
		return null;
	}

}

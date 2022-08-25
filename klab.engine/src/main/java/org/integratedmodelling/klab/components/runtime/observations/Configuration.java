package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Configuration extends DirectObservation implements IConfiguration {

	public Configuration(String name, Observable observable, Scale scale, RuntimeScope context) {
		super(name, observable, scale, context);
	}
	
	protected Configuration(Configuration other) {
		super(other);
	}

}

package org.integratedmodelling.klab.components.runtime.actors.extensions;

import org.integratedmodelling.klab.utils.Parameters;

import groovy.lang.GroovyObjectSupport;

public class Space extends GroovyObjectSupport implements IValueProxy {

	Parameters<String> parameters = Parameters.create();

	public Space() {
	}

	@Override
	public void setProperty(String key, Object value) {
		parameters.put(key, value);
	}

	@Override
	public Object getValue() {
		return new org.integratedmodelling.klab.components.geospace.services.Space().eval(parameters, null);
	}

}

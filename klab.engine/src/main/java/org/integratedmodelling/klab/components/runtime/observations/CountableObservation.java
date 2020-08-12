package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * A countable observation is a direct observation that is instantiated by
 * models, so resolving the observable is optional (the simple acknowledgement
 * is already an observation). This intermediate class simply ensures that the
 * observable is optional.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class CountableObservation extends DirectObservation {

	protected CountableObservation(CountableObservation other) {
		super(other);
	}

	protected CountableObservation(String name, Observable observable, Scale scale, IRuntimeScope context) {
		super(name, observable, scale, context);
		// resolving the observable is always optional
		observable.setOptional(true);
	}

}

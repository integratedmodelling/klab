package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * A countable observation is a direct observation that is instantiated by models, so resolving the
 * observable is optional (the simple acknowledgement is already an observation). This intermediate
 * class simply ensures that the observable is optional.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class CountableObservation extends DirectObservation {

  protected CountableObservation(String name, Observable observable, Scale scale, RuntimeContext context) {
    super(name, observable, scale, context);
    // resolving the observable is always optional
    observable.setOptional(true);
  }

}

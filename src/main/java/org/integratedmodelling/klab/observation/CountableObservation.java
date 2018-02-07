package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

/**
 * A countable observation is a direct observation that is instantiated by models, so resolving the
 * observable is optional (the simple acknowledgement is already an observation). This intermediate
 * class simply ensures that the observable is optional.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class CountableObservation extends DirectObservation {

  protected CountableObservation(String name, Observable observable, Scale scale,
      IMonitor monitor) {
    super(name, observable, scale, monitor);
    // resolving the observable is always optional
    observable.setOptional(true);
  }

  private static final long serialVersionUID = 3713632802098415506L;

}

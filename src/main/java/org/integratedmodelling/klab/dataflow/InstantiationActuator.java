package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class InstantiationActuator<T extends ICountableObservation> extends Actuator<T> {

  T observation;
  IInstantiator<?> instantiator;
  
  public InstantiationActuator() {
    // TODO Auto-generated constructor stub
  }

  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {
    return null;
  }
}

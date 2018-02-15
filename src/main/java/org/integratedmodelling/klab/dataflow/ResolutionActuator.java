package org.integratedmodelling.klab.dataflow;

import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.observation.DirectObservation;

public class ResolutionActuator<T extends IObservation> extends Actuator<T> {

  T            observation;
  IResolver<?> resolver;

  public ResolutionActuator() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public T compute(DirectObservation context, IMonitor monitor) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

}

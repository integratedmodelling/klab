package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public class Event extends CountableObservation implements IEvent {

  public Event(String name, Observable observable, Scale scale, RuntimeContext context) {
    super(name, observable, scale, context);
  }

  private static final long serialVersionUID = -5518029878668042674L;

  @Override
  public Event next() {
    return (Event)next();
  }

}

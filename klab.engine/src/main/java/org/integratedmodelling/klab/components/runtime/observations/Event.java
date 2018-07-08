package org.integratedmodelling.klab.components.runtime.observations;

import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public class Event extends CountableObservation implements IEvent {

  public Event(String name, Observable observable, Scale scale, RuntimeContext context) {
    super(name, observable, scale, context);
  }
}

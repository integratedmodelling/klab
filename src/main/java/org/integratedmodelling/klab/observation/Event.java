package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public class Event extends CountableObservation implements IEvent {

  private Event(String name, Observable observable, Scale scale, IObjectData data, Subject context, IMonitor monitor) {
    super(name, observable, scale, data, monitor);
    setContextObservation(context);
  }

  private static final long serialVersionUID = -5518029878668042674L;

  public static Event create(String name, Observable observable, Scale scale, IObjectData data, Subject context, IMonitor monitor) {
    return new Event(name, observable, scale, data, context, monitor);
  }

  @Override
  public Event next() {
    return (Event)getNext();
  }

}

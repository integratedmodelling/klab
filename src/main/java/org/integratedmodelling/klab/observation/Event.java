package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

public class Event extends CountableObservation implements IEvent {

    private Event(String name, Observable observable, Scale scale, IMonitor monitor) {
        super(name, observable, scale, monitor);
    }

    private static final long serialVersionUID = -5518029878668042674L;

    public static Event create(String name, IObservable observable, IScale scale, ISubject context) {
        return null;
    }

}

package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.owl.Observable;

public class Event extends DirectObservation implements IEvent {

    private Event(String name, Observable observable, Scale scale) {
        super(name, observable, scale);
    }

    private static final long serialVersionUID = -5518029878668042674L;

    public static Event create(String name, IObservable observable, IScale scale, ISubject context) {
        return null;
    }

}

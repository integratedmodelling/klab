package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContext;

public class Event extends DirectObservation implements IEvent {

    private Event(String name, IObservable observable, IScale scale, IContext context) {
        super(name, observable, scale, context);
    }

    private static final long serialVersionUID = -5518029878668042674L;

    public static Event create(String name, IObservable observable, IScale scale, ISubject context) {
        return null;
    }

}

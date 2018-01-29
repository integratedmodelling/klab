package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.owl.Observable;

public class Relationship extends DirectObservation implements IRelationship {

    Subject source;
    Subject target;
    
    private Relationship(String name, Observable observable, Scale scale) {
        super(name, observable, scale);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -3655402547302227307L;

    public static Relationship create(String name, IObservable observable, IScale scale, ISubject context, ISubject source, ISubject target) {
        return null;
    }

    @Override
    public Subject getSource() {
        return source;
    }

    @Override
    public Subject getTarget() {
        return target;
    }

}

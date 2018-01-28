package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContext;

public class Relationship extends DirectObservation implements IRelationship {

    private Relationship(String name, IObservable observable, IScale scale, IContext context) {
        super(name, observable, scale, context);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = -3655402547302227307L;

    public static Relationship create(String name, IObservable observable, IScale scale, ISubject context, ISubject source, ISubject target) {
        return null;
    }

    @Override
    public ISubject getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISubject getTarget() {
        // TODO Auto-generated method stub
        return null;
    }

}

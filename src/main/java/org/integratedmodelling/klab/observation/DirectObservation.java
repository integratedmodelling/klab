package org.integratedmodelling.klab.observation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IContext;

public abstract class DirectObservation extends Observation implements IDirectObservation {

    String name;
    List<IState> states = new ArrayList<>();
    
    protected DirectObservation(String name, IObservable observable, IScale scale, IContext context) {
        super(observable, scale, context);
        this.name = name;
    }

    private static final long serialVersionUID = -3783226787593004279L;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<IState> getStates() {
        return states;
    }

}

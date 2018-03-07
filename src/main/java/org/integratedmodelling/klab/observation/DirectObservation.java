package org.integratedmodelling.klab.observation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.ObjectData;
import org.integratedmodelling.klab.owl.Observable;

public abstract class DirectObservation extends Observation implements IDirectObservation {

    String name;
    List<IState> states = new ArrayList<>();
    IObjectData objectData = null;
    
    private static final long serialVersionUID = -3783226787593004279L;
    
    protected DirectObservation(String name, Observable observable, Scale scale, IMonitor monitor) {
        super(observable, scale, monitor);
        this.name = name;
        this.objectData = new ObjectData(name, observable);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<IState> getStates() {
        return states;
    }
    
    public IObjectData getData() {
        return objectData;
    }
    
}

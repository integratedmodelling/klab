package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

public abstract class DirectObservation extends Observation implements IDirectObservation {

    String name;
    
    protected DirectObservation(String name, Observable observable, Scale scale, RuntimeContext context) {
        super(observable, scale, context);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Collection<IState> getStates() {
      return getRuntimeContext().getChildren(this, IState.class);
    }

    @Override
    public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
        return getRuntimeContext().getChildren(this, cls);
    }
    
    @Override
    public IArtifact.Type getType() {
    	return IArtifact.Type.OBJECT;
    }
}

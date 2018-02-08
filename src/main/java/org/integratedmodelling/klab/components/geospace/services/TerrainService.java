package org.integratedmodelling.klab.components.geospace.services;

import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.provenance.Artifact;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class TerrainService implements IStateResolver {

    public TerrainService() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void resolve(IState observation, IDirectObservation context, Locator locator) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setRuntimeContext(IActuator actuator, Artifact provenance, IMonitor monitor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IState createObservation(IScale scale) {
        // TODO Auto-generated method stub
        return null;
    }

}

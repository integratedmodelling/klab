package org.integratedmodelling.klab.components.geospace.services;

import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
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
    public void setRuntimeContext(IActuator actuator, IArtifact provenance, IMonitor monitor) {
        // TODO Auto-generated method stub
        
    }

}

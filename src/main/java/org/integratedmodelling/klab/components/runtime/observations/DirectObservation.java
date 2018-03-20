package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;

public abstract class DirectObservation extends Observation implements IDirectObservation {

    String name;
    
    private static final long serialVersionUID = -3783226787593004279L;
    
    protected DirectObservation(String name, Observable observable, Scale scale, IObjectData data, IMonitor monitor) {
        super(observable, scale, monitor);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    
    @Override
    public Collection<IState<?>> getStates() {
      // TODO collect states from structure
      return null;
    }
    
    @Override
    public IGeometry getGeometry() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public IMetadata getMetadata() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public IObjectData getParent() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Collection<IObservationData> getChildren() {
      // TODO Auto-generated method stub
      return null;
    }

}

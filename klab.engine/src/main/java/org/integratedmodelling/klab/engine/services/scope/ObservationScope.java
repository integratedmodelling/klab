package org.integratedmodelling.klab.engine.services.scope;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.engine.IEngineService.ResourceManager;
import org.integratedmodelling.klab.api.engine.IObservationScope;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;

public class ObservationScope implements IObservationScope {

    IActorIdentity<?> observer;
    IDirectObservation context;
    Set<String> scenarios;
    ObservationScope parent;
    IGeometry geometry;
    String token;
        
    public IEngineService.Reasoner getReasoner() {
        return null;
    }
    
    public IEngineService.Resolver getResolver() {
        return null;
    }
    
    public IEngineService.Runtime getRuntime() {
        return null;
    }
    
    public ResourceManager getResources() {
        return null;
    }
    
    public IEngineService getEngine() {
        return null;
    }
    
    private ObservationScope(ObservationScope parent) {
        this.parent = parent;
        this.observer = parent.observer;
        this.context = parent.context;
    }

    @Override
    public IObservation getContext() {
        return this.context;
    }

    @Override
    public IActorIdentity<?> getObserver() {
        return this.observer;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public ObservationScope withScenarios(String... scenarios) {
        ObservationScope ret = new ObservationScope(this);
        if (scenarios == null) {
            ret.scenarios = null;
        }
        this.scenarios = new HashSet<>();
        for (String scenario : scenarios) {
            ret.scenarios.add(scenario);
        }
        return ret;
    }

    @Override
    public ObservationScope withObserver(IActorIdentity<?> observer) {
        ObservationScope ret = new ObservationScope(this);
        ret.observer = observer;
        return ret;
    }

    @Override
    public ObservationScope within(IDirectObservation context) {
        ObservationScope ret = new ObservationScope(this);
        ret.context = context;
        return ret;
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObservationScope run(IBehavior behavior) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Future<IObservation> observe(Object... observables) {
        // TODO Auto-generated method stub
        return null;
    }

}

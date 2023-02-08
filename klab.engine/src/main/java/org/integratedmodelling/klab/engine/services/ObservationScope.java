package org.integratedmodelling.klab.engine.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IEngineService;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

public class ObservationScope implements IEngineService.ObservationScope {

    enum Type {
        USER, // root-level scope
        SCRIPT, // session-level scope
        API, // session for the REST API through a client
        APPLICATION, // session for an application, including the Explorer
        SESSION, // raw session for direct use within Java code
        CONTEXT // context, on which observe() can be called
    }

    IObserver<?> observer;
    IDirectObservation context;
    Set<String> scenarios;
    ObservationScope parent;
    IGeometry geometry;
    
    public IEngineService.Reasoner getReasoner() {
        return null;
    }
    
    public IEngineService.Resolver getResolver() {
        return null;
    }
    
    public IEngineService.Runtime getRuntime() {
        return null;
    }
    
    public IEngineService.Resources getResources() {
        return null;
    }
    
    public IEngineService getEngine() {
        return null;
    }
    
    /**
     * The agent we talk to. Can be a user agent, a session agent (of any kind) or a context agent,
     * nested in this order.
     */
    IActorIdentity<?> identity;

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
    public IObserver<?> getObserver() {
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
    public ObservationScope withObserver(IObserver<?> observer) {
        ObservationScope ret = new ObservationScope(this);
        ret.observer = observer;
        return ret;
    }

    @Override
    public ObservationScope withContext(IDirectObservation context) {
        ObservationScope ret = new ObservationScope(this);
        ret.context = context;
        return ret;
    }

}

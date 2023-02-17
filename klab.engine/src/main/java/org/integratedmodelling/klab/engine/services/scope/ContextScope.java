package org.integratedmodelling.klab.engine.services.scope;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;

public class ContextScope extends SessionScope implements IContextScope {

    IIdentity observer;
    IDirectObservation context;
    Set<String> scenarios;
    ContextScope parent;
    IGeometry geometry;
    String token;

    ContextScope(SessionScope parent) {
        super(parent);
        this.observer = parent.getUser();
    }

    private ContextScope(ContextScope parent) {
        super(parent);
        this.parent = parent;
        this.observer = parent.observer;
        this.context = parent.context;
    }

    @Override
    public IDirectObservation getContextObservation() {
        return this.context;
    }

    @Override
    public IIdentity getObserver() {
        return this.observer;
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public ContextScope withScenarios(String... scenarios) {
        ContextScope ret = new ContextScope(this);
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
    public ContextScope withObserver(IActorIdentity<?> observer) {
        ContextScope ret = new ContextScope(this);
        ret.observer = observer;
        return ret;
    }

    @Override
    public ContextScope within(IDirectObservation context) {
        ContextScope ret = new ContextScope(this);
        ret.context = context;
        return ret;
    }

    @Override
    public Future<IObservation> observe(Object... observables) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IProvenance getProvenance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IReport getReport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDataflow<?> getDataflow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDirectObservation getParentOf(IObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IObservation> getChildrenOf(IObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IRelationship> getOutgoingRelationships(IDirectObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IRelationship> getIncomingRelationships(IDirectObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

}

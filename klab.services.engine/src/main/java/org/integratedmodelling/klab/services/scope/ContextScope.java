package org.integratedmodelling.klab.services.scope;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.knowledge.observation.KDirectObservation;
import org.integratedmodelling.klab.api.knowledge.observation.KObservation;
import org.integratedmodelling.klab.api.knowledge.observation.KRelationship;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.provenance.KProvenance;
import org.integratedmodelling.klab.api.services.runtime.KDataflow;
import org.integratedmodelling.klab.api.services.runtime.KReport;

public class ContextScope extends SessionScope implements KContextScope {

    private static final long serialVersionUID = -3241953358893122142L;

    KIdentity observer;
    KDirectObservation context;
    Set<String> scenarios;
    ContextScope parent;
    KGeometry geometry;
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
    public KDirectObservation getContextObservation() {
        return this.context;
    }

    @Override
    public KIdentity getObserver() {
        return this.observer;
    }

    @Override
    public KGeometry getGeometry() {
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
    public ContextScope withObserver(KIdentity observer) {
        ContextScope ret = new ContextScope(this);
        ret.observer = observer;
        return ret;
    }

    @Override
    public ContextScope within(KDirectObservation context) {
        ContextScope ret = new ContextScope(this);
        ret.context = context;
        return ret;
    }

    @Override
    public Future<KObservation> observe(Object... observables) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KProvenance getProvenance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KReport getReport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KDataflow<?> getDataflow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KDirectObservation getParentOf(KObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KObservation> getChildrenOf(KObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KRelationship> getOutgoingRelationships(KDirectObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KRelationship> getIncomingRelationships(KDirectObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

}

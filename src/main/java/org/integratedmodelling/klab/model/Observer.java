package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IBehavior;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Observer extends KimObject implements IObserver {

    private static final long serialVersionUID = 2777161073171784334L;
    
    private IObservable observable;
    private String name;
    private INamespace namespace;
    private IBehavior behavior;
    private List<IObservable> states = new ArrayList<>();

    public Observer(IKimObserver statement, INamespace namespace, IMonitor monitor) {
        super(statement);
        this.observable = Observables.INSTANCE.declare(statement.getObservable(), monitor);
        this.namespace = namespace;
        this.name = statement.getName();
        this.behavior = new Behavior(statement.getBehavior(), this);
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public String getName() {
        return namespace.getId() + "." + getId();
    }

    @Override
    public INamespace getNamespace() {
        return namespace;
    }

    @Override
    public IBehavior getBehavior() {
        return behavior;
    }

    @Override
    public IObservable getObservable() {
        return observable;
    }

    @Override
    public List<IObservable> getStates() {
        return states;
    }
}

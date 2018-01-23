package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Observer extends KimObject implements IObserver {

    private static final long serialVersionUID = 2777161073171784334L;
    
    private IConcept observable;
    private String name;
    private INamespace namespace;

    public Observer(IKimObserver statement, IMonitor monitor) {
        super(statement);
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
}

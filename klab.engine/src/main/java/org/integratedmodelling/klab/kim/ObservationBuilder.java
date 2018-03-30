package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;

public enum ObservationBuilder {
    
    INSTANCE;
    
    /**
     * Build an observer from its parsed, syntax-error-free representation coming
     * from the k.IM validator. May add logical errors, which are reported back when this
     * is called from an intelligent editor.
     * 
     * @param observer
     * @param namespace 
     * @param monitor 
     * @return
     */
    public Observer build(IKimObserver observer, Namespace namespace, Monitor monitor) {
        return new Observer(observer, namespace, monitor);
    }
}

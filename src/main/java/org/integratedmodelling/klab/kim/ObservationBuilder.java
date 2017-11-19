package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public enum ObservationBuilder {
    
    INSTANCE;
    
    /**
     * Build a model from its parsed, syntax-error-free representation coming
     * from the Kim validator. May add logical errors, which are reported back when this
     * is called from an intelligent editor.
     * 
     * @param model
     * @return
     */
    public IObserver build(IKimObserver model, INamespace namespace, IMonitor monitor) {
        return null;
    }
}

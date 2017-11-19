package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public enum ModelBuilder {
    
    INSTANCE;
    
    /**
     * Build a model from its parsed, syntax-error-free representation coming
     * from the Kim validator. May add logical errors, which are reported back when this
     * is called from an intelligent editor.
     * 
     * @param model
     * @return
     */
    public IModel build(IKimModel model, INamespace namespace, IMonitor monitor) {
        return null;
    }
    
}

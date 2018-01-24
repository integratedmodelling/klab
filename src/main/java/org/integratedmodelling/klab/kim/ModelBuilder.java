package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.model.Model;

public enum ModelBuilder {

    INSTANCE;

    /**
     * Build a model from its parsed, syntax-error-free representation coming
     * from the Kim validator. May add logical errors, which are reported back 
     * through the monitor.
     * 
     * @param model
     * @param namespace 
     * @param monitor 
     * @return a built model
     */
    public IModel build(IKimModel model, INamespace namespace, IMonitor monitor) {
        return new Model(model, namespace, monitor);
    }

}

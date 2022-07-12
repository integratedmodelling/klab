package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Acknowledgement;

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
     * @return a new observer
     */
    public Acknowledgement build(IKimAcknowledgement observer, Namespace namespace, Monitor monitor) {
        return new Acknowledgement(observer, namespace, monitor);
    }
}

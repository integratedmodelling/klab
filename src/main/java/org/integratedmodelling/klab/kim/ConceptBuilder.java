package org.integratedmodelling.klab.kim;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public enum ConceptBuilder {
    
    INSTANCE;
    
    public IConcept build(IKimConceptStatement concept, INamespace namespace, IMonitor monitor) {
        
        IConcept main = null;
        
        if (concept.getName() != null) {
            
        }
        
        return null;
    }
    
    public IConcept declare(IKimConcept concept, IMonitor monitor) {
        
        IConcept main = null;
        
        if (concept.getName() != null) {
            
        }
        
        return null;
    }
}

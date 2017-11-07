package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.INamespace;
import org.integratedmodelling.kim.model.KimNamespace;
import org.integratedmodelling.klab.api.services.INamespaceService;

public enum Namespaces implements INamespaceService {
    INSTANCE;
    
    public INamespace build(KimNamespace namespace) {
        return null;
    }
    
}

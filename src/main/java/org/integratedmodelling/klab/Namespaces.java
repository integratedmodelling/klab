package org.integratedmodelling.klab;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.INamespaceService;

public enum Namespaces implements INamespaceService {
    
    INSTANCE;
    
    private Map<String,INamespace> namespaces = new HashMap<>();
    
    public INamespace build(IKimNamespace namespace) {
        return null;
    }

    @Override
    public INamespace getNamespace(String namespaceId) {
        return namespaces.get(namespaceId);
    }

    /*
     * Non-API
     */
    public void registerNamespace(INamespace namespace) {
        namespaces.put(namespace.getName(), namespace);
    }

    
}

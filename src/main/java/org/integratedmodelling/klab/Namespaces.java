package org.integratedmodelling.klab;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.model.KimNamespace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.INamespaceService;
import org.integratedmodelling.klab.model.Namespace;

public enum Namespaces implements INamespaceService {
    
    INSTANCE;
    
    private Map<String,Namespace> namespaces = new HashMap<>();
    
    public IKimNamespace build(KimNamespace namespace) {
        return null;
    }

    @Override
    public INamespace getNamespace(String namespaceId) {
        return namespaces.get(namespaceId);
    }

    /*
     * Non-API
     */
    public void registerNamespace(Namespace namespace) {
        namespaces.put(namespace.getName(), namespace);
    }

    
}

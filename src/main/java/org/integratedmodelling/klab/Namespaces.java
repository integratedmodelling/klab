package org.integratedmodelling.klab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.services.INamespaceService;

public enum Namespaces implements INamespaceService {

    INSTANCE;

    private Map<String, INamespace> namespaces = Collections.synchronizedMap(new HashMap<>());

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

    public void release(String name) {

        Models.INSTANCE.releaseNamespace(name);
        Observations.INSTANCE.releaseNamespace(name);

        INamespace ns = namespaces.get(name);
        if (ns != null) {
            if (ns.getOntology() != null) {
                Ontologies.INSTANCE.release(ns.getOntology());
            }
            namespaces.remove(name);
        }
    }

}

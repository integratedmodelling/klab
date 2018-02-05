package org.integratedmodelling.klab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INamespaceService;
import org.integratedmodelling.klab.exceptions.KlabException;

public enum Namespaces implements INamespaceService {

    INSTANCE;

    private Map<String, INamespace> namespaces = Collections.synchronizedMap(new HashMap<>());
    
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

    /**
     * Non-API. Release the named namespace, de-indexing any indexed objects it contained. 
     * 
     * @param name
     * @param monitor
     * @throws KlabException
     */
    public void release(String name, IMonitor monitor) throws KlabException {

        Models.INSTANCE.releaseNamespace(name, monitor);
        Observations.INSTANCE.releaseNamespace(name, monitor);

        INamespace ns = namespaces.get(name);
        if (ns != null) {
            if (ns.getOntology() != null) {
                Ontologies.INSTANCE.release(ns.getOntology());
            }
            namespaces.remove(name);
        }
    }

}

package org.integratedmodelling.klab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.INamespaceService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;

public enum Namespaces implements INamespaceService {

  INSTANCE;

  private Map<String, Namespace> namespaces = Collections.synchronizedMap(new HashMap<>());

  @Override
  public Namespace getNamespace(String namespaceId) {
    return namespaces.get(namespaceId);
  }

  /*
   * Non-API
   */
  public void registerNamespace(Namespace namespace, IMonitor monitor) {
    Models.INSTANCE.finalizeNamespace(namespace, monitor);
    namespaces.put(namespace.getName(), namespace);
  }

  /**
   * Non-API. Release the named namespace, de-indexing any indexed objects it contained.
   * 
   * @param namespace
   * @param monitor
   * @throws KlabException
   */
  public void release(INamespace namespace, IMonitor monitor) throws KlabException {

    Models.INSTANCE.releaseNamespace(namespace, monitor);
    Observations.INSTANCE.releaseNamespace(namespace, monitor);

    INamespace ns = namespaces.get(namespace.getName());
    if (ns != null) {
      if (ns.getOntology() != null) {
        Ontologies.INSTANCE.release(ns.getOntology());
      }
      namespaces.remove(namespace.getName());
    }
  }

}

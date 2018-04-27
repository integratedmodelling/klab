package org.integratedmodelling.kim.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.integratedmodelling.kim.kim.Import;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.utils.DirectedGraph;
import org.integratedmodelling.klab.utils.KimCircularDependencyException;
import org.integratedmodelling.klab.utils.TopologicalSort;

/**
 * Ingests Kim Xtext resources and returns them topologically sorted based on
 * their internal dependencies.
 * 
 * @author fvilla
 *
 */
public class ResourceSorter {

    DirectedGraph<String> graph = new DirectedGraph<>();
    Map<String, Resource> resources = new HashMap<>();
    KimWorkspace workspace;

    // available in order of dependency after getResources() is called
    List<String> namespaceIds;

    public ResourceSorter(KimWorkspace workspace) {
        this.workspace = workspace;
    }

    public ResourceSorter() {
    }

    public List<Resource> getResources() {

        if (namespaceIds != null) {
            throw new UnsupportedOperationException("internal: ResourceSorter: getResources() can only be called once");
        }

        List<Resource> ret = new ArrayList<>();
        namespaceIds = new ArrayList<>();
        try {
            for (String ns : TopologicalSort.sort(graph)) {
                ret.add(resources.get(ns));
                namespaceIds.add(ns);
            }
        } catch (IllegalArgumentException e) {
            throw new KimCircularDependencyException(
                    "Workspace " + (workspace == null ? "" : workspace.getName()) + " has circular dependencies");
        }
        return ret;
    }

    public void add(Resource resource) {

        if (namespaceIds != null) {
            throw new UnsupportedOperationException(
                    "internal: ResourceSorter: getResources() has already been called: cannot add new elements");
        }

        if (resource instanceof LazyLinkingResource) {
            Model model = (Model) resource.getContents().get(0);
            Namespace ns = model.getNamespace();
            String nsName = ns.getName();
            graph.addNode(nsName);
            resources.put(nsName, resource);
            for (Import dio : ns.getImported()) {
                // crazy logics to extract the actual namespace ID from the linked reference,
                // and while this works, the whole system still manages to not find references
                // when run standalone, so I'll just brute-force everything and skip the xtext
                // cross-reference mechanism.
//              EObject proxy = dio.getName();
//              /*
//               * the following is null if the NS has been already loaded, i.e. it's not a
//               * proxy anymore. TODO I guess there's a more idiomatic way to know if the
//               * object is a proxy or not.
//               */
//              String nsId = proxy instanceof Namespace ? ((Namespace) proxy).getName() : null;
//              URI proxyURI = ((InternalEObject) proxy).eProxyURI();
//              if (nsId == null && proxyURI != null) {
//                  Triple<EObject, EReference, INode> data = ((LazyLinkingResource) resource).getEncoder()
//                          .decode(resource, proxyURI.fragment());
//                  nsId = NodeModelUtils.getTokenText(data.getThird());
//              }
                String nsId = dio.getName();
                if (nsId != null) {
                    graph.addNode(nsId);
                    graph.addEdge(nsId, nsName);
                }
            }
        }
    }

    public List<String> getSortedNamespaceIds() {
        if (namespaceIds == null) {
            throw new UnsupportedOperationException(
                    "internal: ResourceSorter: getResources() has not been called: cannot return namespace order");
        }
        return namespaceIds;
    }

}
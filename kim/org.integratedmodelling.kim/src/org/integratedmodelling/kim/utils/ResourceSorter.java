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
import org.integratedmodelling.kim.model.Kim;
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
			if (resource.getContents() != null && resource.getContents().size() > 0) {
				Model model = (Model) resource.getContents().get(0);
				Namespace ns = model.getNamespace();
				String nsName = Kim.getNamespaceId(ns);
				graph.addNode(nsName);
				resources.put(nsName, resource);
				for (Import dio : ns.getImported()) {
					String nsId = dio.getName();
					if (nsId != null) {
						graph.addNode(nsId);
						graph.addEdge(nsId, nsName);
					}
				}
			}
		} else {
			// TODO should add logging and/or some kind of user notification besides printing
			System.err.println("k.IM resource " + resource + " has no k.IM content and will not be included in the k.LAB workspace");
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
package org.integratedmodelling.kactors.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.klab.utils.DirectedGraph;
import org.integratedmodelling.klab.utils.KimCircularDependencyException;
import org.integratedmodelling.klab.utils.TopologicalSort;

/**
 * Ingests k.Actors Xtext resources and returns them topologically sorted based on
 * their internal dependencies.
 * 
 * @author fvilla
 *
 */
public class KActorsResourceSorter {

	DirectedGraph<String> graph = new DirectedGraph<>();
	Map<String, Resource> resources = new HashMap<>();
	List<File> workspace;

	// available in order of dependency after getResources() is called
	List<String> namespaceIds;

	public KActorsResourceSorter(List<File> workspace) {
		this.workspace = workspace;
	}

	public KActorsResourceSorter() {
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
			throw new KimCircularDependencyException("Workspace behaviors have circular dependencies");
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
				String ns = model.getPreamble().getName();
				graph.addNode(ns);
				resources.put(ns, resource);
				for (String nsId : model.getPreamble().getImports()) {
					if (nsId != null) {
						graph.addNode(nsId);
						graph.addEdge(nsId, ns);
					}
				}
			}
		} else {
			// TODO should add logging and/or some kind of user notification besides
			// printing
			System.err.println("k.Actors resource " + resource
					+ " has no content and will not be included in the k.LAB workspace");
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
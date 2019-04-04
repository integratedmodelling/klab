package org.integratedmodelling.kim.utils;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.contrib.jgrapht.alg.cycle.CycleDetector;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;

public class DependencyGraph extends DefaultDirectedGraph<String, DefaultEdge> {

	private static final long serialVersionUID = -1721002483284504740L;

	public DependencyGraph() {
		super(DefaultEdge.class);
	}

	public DependencyGraph copy() {
		DependencyGraph ret = new DependencyGraph();
		for (String s : vertexSet()) {
			ret.addVertex(s);
		}
		for (DefaultEdge e : edgeSet()) {
			ret.addEdge(getEdgeSource(e), getEdgeTarget(e));
		}
		return ret;
	}

	/**
	 * Check if the graph has any circular dependencies.
	 * 
	 * @return
	 */
	public boolean hasCycles() {
		CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<String, DefaultEdge>(this);
		if (cycleDetector.detectCycles()) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the graph has any circular dependency involving any of the passed
	 * namespace.
	 * 
	 * @param namespace
	 */
	public boolean hasCycles(String... namespaces) {
		Set<String> nss = new HashSet<>();
		for (String n : namespaces) {
			nss.add(n);
		}
		CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<String, DefaultEdge>(this);
		if (cycleDetector.detectCycles()) {
			for (String ns : cycleDetector.findCycles()) {
				if (nss.contains(ns)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if importing a namespace would cause circular dependencies
	 * 
	 * @param importing
	 * @param imported
	 */
	public boolean canImport(String importing, String imported) {
		DependencyGraph temp = copy();
		temp.addDependency(importing, imported);
		return !temp.hasCycles();
	}

	/**
	 * 
	 * @param importing
	 * @param imported
	 */
	public void addDependency(String importing, String imported) {
		// we just trust that no file in catalog means the dependency is on a core
		// ontology.
		addVertex(imported);
		addVertex(importing);
		addEdge(imported, importing, new DefaultEdge() {
			private static final long serialVersionUID = 1L;

			@Override
			public String toString() {
				return "";
			}
		});
	}

}

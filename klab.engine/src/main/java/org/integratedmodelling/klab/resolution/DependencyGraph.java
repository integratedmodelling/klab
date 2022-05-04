package org.integratedmodelling.klab.resolution;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;

/**
 * Dependency graph. A wrapper for a JGraphT graph that iterates in order of
 * dependency and has some additional methods.
 * 
 * @author Ferd
 *
 */
public class DependencyGraph extends DefaultDirectedGraph<IObservedConcept, DefaultEdge>
		implements Iterable<IObservedConcept> {

	private static final long serialVersionUID = 1132784190007279107L;

	public DependencyGraph() {
		super(DefaultEdge.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator<IObservedConcept> iterator() {
		return new TopologicalOrderIterator(this);
	}

	@Override
	public DefaultEdge addEdge(IObservedConcept sourceVertex, IObservedConcept targetVertex) {

		DefaultEdge edge = new DefaultEdge() {

			private static final long serialVersionUID = 621035439555537518L;

			@Override
			public String toString() {
				return "";
			}
		};
		if (super.addEdge(sourceVertex, targetVertex, edge)) {
			return edge;
		}
		return null;
	}

	/**
	 * True if the passed observable depends on any of those passed in the
	 * precursors set.
	 * 
	 * @param concept
	 * @param precursors
	 * @return
	 */
	public boolean dependsOn(IObservedConcept concept, Set<IObservedConcept> precursors) {
		Set<IObservedConcept> deps = new HashSet<>();
		collectPrecursors(concept, deps);
		return Sets.intersection(deps, precursors).size() > 0;
	}

	private void collectPrecursors(IObservedConcept concept, Set<IObservedConcept> deps) {
		deps.add(concept);
		for (DefaultEdge edge : incomingEdgesOf(concept)) {
			collectPrecursors(getEdgeSource(edge), deps);
		}
	}
	
	public void show() {
		Graphs.show(this, "Dependency graph");
	}

}

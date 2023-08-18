package org.integratedmodelling.ml.legacy.riskwiz.graph;

import java.util.HashSet;
import java.util.Set;

public class RiskDirectedGraph<V, E> extends RiskGraph<V, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2599507208814161629L;

	public RiskDirectedGraph(Class<? extends E> ef, boolean allowMultipleEdges, boolean allowLoops) {
		super(ef, allowMultipleEdges, allowLoops);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a new simple directed beliefNetwork.
	 * 
	 * @param edgeClass class on which to base factory for edges
	 */
	public RiskDirectedGraph(Class<? extends E> edgeClass) {
		this(edgeClass, false, true);
	}

//	/**
//	 * Creates a new simple directed beliefNetwork with the specified edge factory.
//	 * 
//	 * @param ef the edge factory of the new beliefNetwork.
//	 */
//	public RiskDirectedGraph(EdgeFactory<V, E> ef) {
//		super(ef, false, false);
//	}

	public Set<V> getParents(V vertex) {
		Set<E> edges = this.incomingEdgesOf(vertex);
		Set<V> parents = new HashSet<V>();

		for (E e : edges) {
			parents.add(this.getEdgeSource(e));
		}
		return parents;
	}

	public Set<V> getChildren(V vertex) {
		Set<E> edges = this.outgoingEdgesOf(vertex);
		Set<V> children = new HashSet<V>();

		for (E e : edges) {
			children.add(this.getEdgeTarget(e));
		}
		return children;
	}

	public boolean hasEdgeBetween(V sourceVertex, V targetVertex) {
		return !getAllEdges(sourceVertex, targetVertex).isEmpty();
	}

}

package org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm;

import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskDirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

public class RemoveDirectionality<V, E> extends Algorithm<V, E> {

	public RemoveDirectionality(Class<? extends E> cls) {
		super(cls);
	}

	public RiskUndirectedGraph<V, E> execute(RiskDirectedGraph<V, E> srcGarph) {

		RiskUndirectedGraph<? super V, ? super E> targetGraph = new RiskUndirectedGraph<V, E>(this.edgeClass);

		Graphs.addGraph(targetGraph, (Graph<V, E>)srcGarph);
		return (RiskUndirectedGraph<V, E>) targetGraph;
	}

}

package org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm;


import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskDirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.jgrapht.Graphs;


public class RemoveDirectionality<V, E > {
	
    public RiskUndirectedGraph<V, E>  execute(RiskDirectedGraph<V, E> srcGarph) {		
		 
        RiskUndirectedGraph<V, E> targetGraph = new RiskUndirectedGraph<V, E>(
                srcGarph.getEdgeFactory());
		  
        Graphs.addGraph(targetGraph, srcGarph);
        return   targetGraph;
    }

}

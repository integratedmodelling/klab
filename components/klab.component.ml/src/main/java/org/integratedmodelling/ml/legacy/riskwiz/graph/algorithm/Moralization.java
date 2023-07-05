package org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskDirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.jgrapht.Graphs;

 
public class Moralization<V, E > extends Algorithm<V, E > {

    public Moralization(Class<? extends E> cls) {// TODO Auto-generated constructor stub
    	super(cls);
    }

    public RiskUndirectedGraph<V, E> execute(RiskDirectedGraph<V, E> srcGarph) {
        // add moral edges
		
        RiskUndirectedGraph<V, E> copyGraph = new RiskUndirectedGraph<V, E>(this.edgeClass);
		  
        Graphs.addGraph(copyGraph, srcGarph);
		 
        Set<V> vertexes = copyGraph.vertexSet();

        for (V v : vertexes) {
            Set<E> edges = srcGarph.incomingEdgesOf(v);

            for (E e1 : edges) {
                for (E e2 : edges) {
                    if (e1 != e2) {
                        V v1 = srcGarph.getEdgeSource(e1);
                        V v2 = srcGarph.getEdgeSource(e2);

                        if (!srcGarph.areConnected(v1, v2)
                                && !srcGarph.areConnected(v2, v1)) {
                            copyGraph.addEdge(v1, v2);
                        }
                    }
					
                }
            }
			 
        }
		
        return  copyGraph;
    }
	
    // private void  addMoralEdges(V v, RiskDirectedGraph<V, E> g) {		
    // Set<E> edges= g.incomingEdgesOf(v);
    //
    // for (E e1 : edges) {
    // for (E e2 : edges) {
    // if(e1!=e2){
    // V v1= g.getEdgeSource(e1);
    // V v2= g.getEdgeSource(e2);
    // if(!  g.areConnected(v1, v2)&& !g.areConnected(v2, v1)){
    // g.addEdge(v1, v2);
    // }
    // }
    //
    // }
    // }
    //
    // }

}

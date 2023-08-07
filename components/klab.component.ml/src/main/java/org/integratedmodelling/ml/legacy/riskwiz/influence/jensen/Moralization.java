package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskDirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm.RemoveDirectionality;
import org.jgrapht.Graphs;

 
public class Moralization {

    public Moralization() {// TODO Auto-generated constructor stub
    }

    public RiskUndirectedGraph<BNNode, BNEdge>   execute(RiskDirectedGraph<BNNode, BNEdge>  srcGarph) {
        // add moral edges
		
        RiskDirectedGraph<BNNode, BNEdge>  copyGraph = new RiskDirectedGraph<BNNode, BNEdge>(BNEdge.class);
		  
        Graphs.addGraph(copyGraph, srcGarph);		 
        removeInformationLinks(copyGraph);
		
        Set<BNNode> vertexes = copyGraph.vertexSet();

        for (BNNode v : vertexes) {
            addMoralNodes(v, copyGraph);
        }
		
        removeUtilityNodes(copyGraph);
		
        RemoveDirectionality<BNNode, BNEdge>  RD = new RemoveDirectionality<BNNode, BNEdge>(BNEdge.class);
		
        // remove directionality
        return RD.execute(copyGraph);
    }
	
    private void removeInformationLinks(RiskDirectedGraph<BNNode, BNEdge>  graph) {
        Set<BNEdge> edges = graph.edgeSet();

        for (BNEdge edge : edges) {
            if (edge.isInformationEdge()) {
                graph.removeEdge(edge);
            }
        }
    }
	
    private void removeUtilityNodes(RiskDirectedGraph<BNNode, BNEdge>  graph) {
        Set<BNNode> vertexes = graph.vertexSet();
        Set<BNNode> utilities = new HashSet<BNNode>();

        for (BNNode v : vertexes) {
            if (v.isUtility()) {
                utilities.add(v);
				
            }
        }
        graph.removeAllVertices(utilities);
    }
	
    private void  addMoralNodes(BNNode v, RiskDirectedGraph<BNNode, BNEdge>  g) {		
        Set<BNEdge> edges = g.incomingEdgesOf(v);
		
        for (BNEdge e1 : edges) {
            for (BNEdge e2 : edges) {
                if (e1 != e2) {
                    BNNode v1 = g.getEdgeSource(e1);
                    BNNode v2 = g.getEdgeSource(e2);

                    if (!g.areConnected(v1, v2) && !g.areConnected(v2, v1)) {
                        g.addEdge(v1, v2);
                    }
                }
				
            }
        }
		
    }

}

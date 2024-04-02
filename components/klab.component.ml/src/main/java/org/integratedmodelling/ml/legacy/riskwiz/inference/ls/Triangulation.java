package org.integratedmodelling.ml.legacy.riskwiz.inference.ls;


import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm.Algorithm;
import org.jgrapht.Graphs;


public class Triangulation extends Algorithm<BNNode, BNEdge> {

    private Set<JTVertexHugin> cliques;

    public Triangulation() {
    	super(BNEdge.class);
    }

    /*
     * Returns triangulated beliefNetwork (do we really need it), create a set of cliques of that beliefNetwork which
     * can be retrived by getCliques() , which is probably all what we need
     */
    public RiskUndirectedGraph<BNNode, BNEdge> execute(
            RiskUndirectedGraph<BNNode, BNEdge> graph) {
        cliques = new HashSet<JTVertexHugin>();
        RiskUndirectedGraph<BNNode, BNEdge> graphCopy = new RiskUndirectedGraph<BNNode, BNEdge>(BNEdge.class);

        // copy beliefNetwork
        Graphs.addGraph(graphCopy, graph);

        // create priority queue(binary heap) of vertexes
        Set<BNNode> vertexes = graphCopy.vertexSet();
		 
        PriorityQueue<BNNode> pqueue = new PriorityQueue<BNNode>(
                vertexes.size() + 10, new BNVertexComparator(graphCopy));

        pqueue.addAll(vertexes);
        // main loop
        while (!pqueue.isEmpty()) {			 
            // get the top (minimum) element but do not remove it from the queue
            BNNode vtx = pqueue.poll();
            Set<BNNode> neighbors = graphCopy.getNeighbors(vtx);

            for (BNNode vertex1 : neighbors) {
                for (BNNode vertex2 : neighbors) {
                    if ((!graphCopy.areConnected(vertex1, vertex2))
                            && (!vertex1.equals(vertex2))) {
                        graphCopy.addEdge(vertex1, vertex2);
                        graph.addEdge(vertex1, vertex2);
                    }
                }
            }
			
            neighbors.add(vtx);
            saveClique(neighbors);			 
            graphCopy.removeVertex(vtx);
            // this should remove the same vertex from the queue and  cause recalculating priority queue after beliefNetwork vertex has been removed
            // pqueue.poll();
			
            // the following most likely is not necessary, depends on implementation of PriorityQueue
            // TODO look at PriorityQueue code and find out if this call is needed
            // recalculatePriorityQueue(pqueue, neighbors);
			   

        }

        return graph;
    }

    public Set<JTVertexHugin> getCliques() {
        return cliques;
    }

    private boolean saveClique(Set<BNNode> clique) {
		 
        for (JTVertexHugin jtv : cliques) {
            Set<BNNode> alreadySavedClique = jtv.getClique();

            if (alreadySavedClique.containsAll(clique)) {
				 
                return false;
            }
        }
		 
        cliques.add(new JTVertexHugin(clique));
		 
        return true;
		 
    }

    private void recalculatePriorityQueue(PriorityQueue<BNNode> pqueue,
            Set<BNNode> neighbors) {
		
        for (BNNode vertex : neighbors) {			
            pqueue.remove(vertex);
        }
        for (BNNode vertex : neighbors) {
            pqueue.offer(vertex);
        }

    }

    private class BNVertexComparator implements Comparator<BNNode> {
		
        RiskUndirectedGraph<BNNode, BNEdge> graph;
		
        private BNVertexComparator(RiskUndirectedGraph<BNNode, BNEdge> g) {
            this.graph = g;
			
        }
		
        /*
         *  minimum should will be on the top of PriorityQueue, 
         *  therefore comparator  should be providing inverse of the comparator 
         *  specified in Kjaerulff algorithm which is as follows
         *  vt1>vt2 (vt1 is better than vt2)  if vt1 needs less number of edges to be insrted or 
         *  induces a cluster with the smalest weight
         *  in our case (better than) is (less than) therefore
         *  
         *  vt1 better than vt2 implies that it should be which vt1 < vt2 implies that output is negative
         */		
		
        @Override
		public int compare(BNNode vt1, BNNode vt2) {
			
            int insertedEdges1 = insertionsEstimate(vt1);
            int insertedEdges2 = insertionsEstimate(vt2); 
			
            // if insertedEdges1 is less than insertedEdges2, then insertedEdges1 is better
            if (insertedEdges1 != insertedEdges2) {
                return insertedEdges1 - insertedEdges2;
            } else {
                int weight1 = clusterWeightEstimate(vt1);
                int weight2 = clusterWeightEstimate(vt2);

                // cluster that induces smallest weight is better (it should be smaller)
                return weight1 - weight2;
            }
				
        }
		
        /*
         * returns number of insertions needed multiplied by 2 (but this 2 does not matter)
         */
        private int insertionsEstimate(BNNode vt) {
            int insertions = 0;
            Set<BNNode> neighbors = graph.getNeighbors(vt);

            for (BNNode vertex1 : neighbors) {
                for (BNNode vertex2 : neighbors) {
                    if (vertex1 != vertex2) {
                        if (!graph.areConnected(vertex1, vertex2)) {
                            insertions++;
                        }
                    }
                }
            }
			
            return insertions;
        }
		
        private int clusterWeightEstimate(BNNode vt) {
            int weight = 1;
            Set<BNNode> neighbors = graph.getNeighbors(vt);

            for (BNNode vertex : neighbors) {
                weight *= vertex.getWeight();
            }
            return weight;
        }

    }

}

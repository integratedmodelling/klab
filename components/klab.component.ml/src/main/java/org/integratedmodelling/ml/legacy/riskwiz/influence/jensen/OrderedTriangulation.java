package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm.Algorithm;
import org.jgrapht.Graphs;


public class OrderedTriangulation extends Algorithm<BNNode, BNEdge> {

    private SortedSet<SJTVertex> cliques;
    int counter = 0;

    public OrderedTriangulation() {
    	super(BNEdge.class);
    }

    /*
     * Returns triangulated beliefNetwork  , create a set of cliques of that beliefNetwork which
     * can be retrived by getCliques() , which is probably all what we need
     */
    public RiskUndirectedGraph<BNNode, BNEdge> execute(
            RiskUndirectedGraph<BNNode, BNEdge> graph, 
            Vector<Set<BNNode>> oredredSets,
            Vector< BNNode> oredredDecisionNodes) {
        cliques = new TreeSet<SJTVertex>();
        RiskUndirectedGraph<BNNode, BNEdge> graphCopy = new RiskUndirectedGraph<BNNode, BNEdge>(BNEdge.class);

        // copy beliefNetwork
        Graphs.addGraph(graphCopy, graph);

        // create priority queue(binary heap) of vertexes
        counter = graphCopy.vertexSet().size();
		 
        // pqueue.addAll(vertexes);
        // main loop
        for (int i = oredredSets.size() - 1; i > 0; i--) {
            PriorityQueue<BNNode> pqueue = new PriorityQueue<BNNode>(
                    oredredSets.elementAt(i).size() + 10,
                    new BNVertexComparator(graphCopy));

            pqueue.addAll(oredredSets.elementAt(i));
            removeNodeSet(pqueue, graphCopy, graph);			
            removeNode(oredredDecisionNodes.elementAt(i - 1), graphCopy, graph);
			
        }
        PriorityQueue<BNNode> pqueue = new PriorityQueue<BNNode>(
                oredredSets.elementAt(0).size() + 10,
                new BNVertexComparator(graphCopy));

        pqueue.addAll(oredredSets.elementAt(0));
        removeNodeSet(pqueue, graphCopy, graph);

        return graph;
    }
	
    private void removeNodeSet(PriorityQueue<BNNode> pqueue, 
            RiskUndirectedGraph<BNNode, BNEdge> graphCopy,
            RiskUndirectedGraph<BNNode, BNEdge> graph) {
        while (!pqueue.isEmpty()) {			 
            // get the top (minimum) element but do not remove it from the queue
            BNNode vtx = pqueue.peek();

            removeNode(vtx, graphCopy, graph);
            // this should remove the same vertex from the queue and  cause recalculating priority queue after beliefNetwork vertex has been removed
            pqueue.poll();
			
            // the following most likely is not necessary, depends on implementation of PriorityQueue
            // TODO look at PriorityQueue code and find out if this call is needed
            // recalculatePriorityQueue(pqueue, neighbors);

        }
    }
	
    private void removeNode(BNNode vtx, RiskUndirectedGraph<BNNode, BNEdge> graphCopy,
            RiskUndirectedGraph<BNNode, BNEdge> graph) {
		 
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
        vtx.setCount(counter);
        counter--;

    }

    public SortedSet<SJTVertex> getCliques() {
        return cliques;
    }

    private boolean saveClique(Set<BNNode> clique) {
		 
        for (SJTVertex jtv : cliques) {
            Set<BNNode> alreadySavedClique = jtv.getClique();

            if (alreadySavedClique.containsAll(clique)) {
                jtv.setCount(counter);
                return false;
            }
        }
		 
        cliques.add(new SJTVertex(clique, counter));
		 
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

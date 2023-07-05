package org.integratedmodelling.ml.legacy.riskwiz.graph;


import java.util.HashSet;
import java.util.Set;


public class RiskUndirectedGraph<V, E > extends RiskGraph<V, E> {

    /**
     * 
     */
    private static final long serialVersionUID = 5140788770511048359L;

    public RiskUndirectedGraph(Class<? extends E> ef,
            boolean allowMultipleEdges, boolean allowLoops) {
        super(ef, allowMultipleEdges, allowLoops);
        // TODO Auto-generated constructor stub
    }
	
    /**
     * Creates a new simple beliefNetwork with the specified edge factory.
     *
     * @param ef the edge factory of the new beliefNetwork.
     */
    public RiskUndirectedGraph(Class<? extends E> ef) {
        this(ef, false, false);
    }

//    /**
//     * Creates a new simple beliefNetwork.
//     *
//     * @param edgeClass class on which to base factory for edges
//     */
//    public RiskUndirectedGraph(Class<? extends E> edgeClass) {
//        this(new ClassBasedEdgeFactory<V, E>(edgeClass));
//    }
    
    public Set<V> getNeighbors(V vertex) {
        Set<V> neighbors = new HashSet<V>();
    	
        Set<E> edges = this.edgesOf(vertex);
    	
        for (E e : edges) {
            if (this.getEdgeSource(e) != vertex) {
                neighbors.add(this.getEdgeSource(e));				
            } else {
                neighbors.add(this.getEdgeTarget(e));
            }
        }
    	
        return neighbors;
    }

}

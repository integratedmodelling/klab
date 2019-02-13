package org.integratedmodelling.ml.legacy.riskwiz.graph;


import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;

 
public class RiskGraph<V, E > extends AbstractBaseGraph<V, E > {

    /**
     * 
     */
    private static final long serialVersionUID = -1648493104509202405L;

    public RiskGraph(EdgeFactory<V, E> ef, boolean allowMultipleEdges,
            boolean allowLoops) {
        super(ef, allowMultipleEdges, allowLoops);
        // TODO Auto-generated constructor stub
    }
	
    /* ! Applies a Graph Visitor to the beliefNetwork
     * \param[in] GV the visitor
     * \param[in] donodes - should the nodes be visited?
     * \param[in] doedges - should the edges be visited?
     */
    public void apply(Visitor<V, E>  GV, boolean donodes, boolean doedges) {
        if (donodes) {
            Set<V> vertexes = vertexSet();

            for (V v : vertexes) {
                GV.onVertex(v);
            }			
			
        }
		
        if (doedges) {
            Set<E> edges = edgeSet();			

            for (E e : edges) {
                GV.onEdge(e);
            }
        }
    }
	
    public boolean areConnected(V v1, V v2) {    	 
	    	
        Set<E> edges = getAllEdges(v1, v2);

        if (!edges.isEmpty()) {
            return  true;
        }
	    	
        return false;
    }

}

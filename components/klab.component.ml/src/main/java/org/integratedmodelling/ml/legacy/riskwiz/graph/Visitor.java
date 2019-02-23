package org.integratedmodelling.ml.legacy.riskwiz.graph;

 
public interface Visitor<V, E> {
	
    /* ! visiting a vertex
     * \param[in] v the incoming vertex
     * \return true -> keep going, false -> stop
     */
    public boolean onVertex(V v);

    /* ! visiting an edge
     * \param[in] e the incoming edge
     * \return true -> keep going, false -> stop
     */
    public boolean onEdge(E e);

}

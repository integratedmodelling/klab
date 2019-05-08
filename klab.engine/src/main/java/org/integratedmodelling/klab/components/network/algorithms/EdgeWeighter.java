package org.integratedmodelling.klab.components.network.algorithms;

public interface EdgeWeighter<E> {

    /**
     * Returns the weight for the associated edge.
     *
     * @param e The edge whose weight to return.
     *
     * @return The weight of the edge.
     */
    double getWeight(E e);

    /**
     * The weight for connecting the graph at the distance dist
     * @param dist distance to connect to the graph
     * @return
     */
    double getToGraphWeight(double dist);
    
}
/*
 * (C) Copyright 2018, by Lukas Harzenetter and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.integratedmodelling.contrib.jgrapht.graph;

import java.io.Serializable;
import java.util.Objects;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.GraphType;

/**
 * Provides an unweighted view on a graph.
 *
 * Algorithms designed for unweighted graphs should also work on weighted graphs. This class
 * emulates an unweighted graph based on a weighted one by returning <code>Graph.DEFAULT_EDGE_WEIGHT
 * </code> for each edge weight. The underlying weighted graph is provided at the constructor.
 * Modifying operations (adding/removing vertexes/edges) are also passed through to the underlying
 * weighted graph. As edge weight, Graph.DEFAULT_EDGE_WEIGHT is used. Setting an edge weight is not
 * supported. The edges are not modified. So, if an edge is asked for, the one from the underlying
 * weighted graph is returned. In case the underlying graph is serializable, this one is
 * serializable, too.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class AsUnweightedGraph<V, E>
    extends GraphDelegator<V, E>
    implements Serializable, Graph<V, E>
{

    private static final long serialVersionUID = -5186421272597767751L;
    private static final String EDGE_WEIGHT_IS_NOT_SUPPORTED = "Edge weight is not supported";

    /**
     * Constructor for AsUnweightedGraph.
     *
     * @param g the backing directed graph over which an undirected view is to be created.
     * @throws NullPointerException if the graph is null
     */
    public AsUnweightedGraph(Graph<V, E> g)
    {
        super(Objects.requireNonNull(g));
    }

    @Override public double getEdgeWeight(E e)
    {
        return Graph.DEFAULT_EDGE_WEIGHT;
    }

    @Override public void setEdgeWeight(E e, double weight)
    {
        throw new UnsupportedOperationException(EDGE_WEIGHT_IS_NOT_SUPPORTED);
    }

    @Override public GraphType getType()
    {
        return super.getType().asUnweighted();
    }
}

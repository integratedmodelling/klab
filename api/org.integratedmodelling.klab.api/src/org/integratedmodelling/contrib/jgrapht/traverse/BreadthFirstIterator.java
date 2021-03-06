/*
 * (C) Copyright 2003-2018, by Barak Naveh and Contributors.
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
package org.integratedmodelling.contrib.jgrapht.traverse;

import java.util.*;

import org.integratedmodelling.contrib.jgrapht.*;

/**
 * A breadth-first iterator for a directed or undirected graph.
 * 
 * <p>
 * For this iterator to work correctly the graph must not be modified during iteration. Currently
 * there are no means to ensure that, nor to fail-fast. The results of such modifications are
 * undefined.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Barak Naveh
 * @since Jul 19, 2003
 */
public class BreadthFirstIterator<V, E>
    extends
    CrossComponentIterator<V, E, BreadthFirstIterator.SearchNodeData<E>>
{
    private Deque<V> queue = new ArrayDeque<>();

    /**
     * Creates a new breadth-first iterator for the specified graph.
     *
     * @param g the graph to be iterated.
     */
    public BreadthFirstIterator(Graph<V, E> g)
    {
        this(g, (V) null);
    }

    /**
     * Creates a new breadth-first iterator for the specified graph. Iteration will start at the
     * specified start vertex and will be limited to the connected component that includes that
     * vertex. If the specified start vertex is <code>null</code>, iteration will start at an
     * arbitrary vertex and will not be limited, that is, will be able to traverse all the graph.
     *
     * @param g the graph to be iterated.
     * @param startVertex the vertex iteration to be started.
     */
    public BreadthFirstIterator(Graph<V, E> g, V startVertex)
    {
        super(g, startVertex);
    }

    /**
     * Creates a new breadth-first iterator for the specified graph. Iteration will start at the
     * specified start vertices and will be limited to the connected component that includes those
     * vertices. If the specified start vertices is <code>null</code>, iteration will start at an
     * arbitrary vertex and will not be limited, that is, will be able to traverse all the graph.
     *
     * @param g the graph to be iterated.
     * @param startVertices the vertices iteration to be started.
     */
    public BreadthFirstIterator(Graph<V, E> g, Iterable<V> startVertices)
    {
        super(g, startVertices);
    }

    /**
     * @see CrossComponentIterator#isConnectedComponentExhausted()
     */
    @Override
    protected boolean isConnectedComponentExhausted()
    {
        return queue.isEmpty();
    }

    /**
     * @see CrossComponentIterator#encounterVertex(Object, Object)
     */
    @Override
    protected void encounterVertex(V vertex, E edge)
    {
        int depth = (edge == null ? 0
            : getSeenData(Graphs.getOppositeVertex(graph, edge, vertex)).depth + 1);
        putSeenData(vertex, new SearchNodeData<>(edge, depth));
        queue.add(vertex);
    }

    /**
     * @see CrossComponentIterator#encounterVertexAgain(Object, Object)
     */
    @Override
    protected void encounterVertexAgain(V vertex, E edge)
    {
    }

    /**
     * Returns the parent node of vertex $v$ in the BFS search tree, or null if $v$ is the root
     * node. This method can only be invoked on a vertex $v$ once the iterator has visited vertex
     * $v$!
     * 
     * @param v vertex
     * @return parent node of vertex $v$ in the BFS search tree, or null if $v$ is a root node
     */
    public V getParent(V v)
    {
        assert getSeenData(v) != null;
        E edge = getSeenData(v).edge;
        if (edge == null)
            return null;
        else
            return Graphs.getOppositeVertex(graph, edge, v);
    }

    /**
     * Returns the edge connecting vertex $v$ to its parent in the spanning tree formed by the BFS
     * search, or null if $v$ is a root node. This method can only be invoked on a vertex $v$ once
     * the iterator has visited vertex $v$!
     * 
     * @param v vertex
     * @return edge connecting vertex $v$ in the BFS search tree to its parent, or null if $v$ is a
     *         root node
     */
    public E getSpanningTreeEdge(V v)
    {
        assert getSeenData(v) != null;
        return getSeenData(v).edge;
    }

    /**
     * Returns the depth of vertex $v$ in the search tree. The depth of a vertex $v$ is defined as
     * the number of edges traversed on the path from the root of the BFS tree to vertex $v$. The
     * root of the search tree has depth 0. This method can only be invoked on a vertex $v$ once the
     * iterator has visited vertex $v$!
     * 
     * @param v vertex
     * @return depth of vertex $v$ in the search tree
     */
    public int getDepth(V v)
    {
        assert getSeenData(v) != null;
        return getSeenData(v).depth;
    }

    /**
     * @see CrossComponentIterator#provideNextVertex()
     */
    @Override
    protected V provideNextVertex()
    {
        return queue.removeFirst();
    }

    static class SearchNodeData<E>
    {
        /**
         * Edge to parent
         */
        final E edge;
        /**
         * Depth of node in search tree
         */
        final int depth;

        SearchNodeData(E edge, int depth)
        {
            this.edge = edge;
            this.depth = depth;
        }
    }
}

// End BreadthFirstIterator.java

/*
 * (C) Copyright 2007-2018, by Vinayak R Borkar and Contributors.
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
package org.integratedmodelling.contrib.jgrapht.alg;

import java.util.*;

import org.integratedmodelling.contrib.jgrapht.*;
import org.integratedmodelling.contrib.jgrapht.graph.*;
import org.integratedmodelling.contrib.jgrapht.traverse.*;

/**
 * Constructs the transitive closure of the input graph.
 *
 * @author Vinayak R. Borkar
 * @since May 5, 2007
 */
public class TransitiveClosure
{
    /**
     * Singleton instance.
     */
    public static final TransitiveClosure INSTANCE = new TransitiveClosure();

    /**
     * Private Constructor.
     */
    private TransitiveClosure()
    {
    }

    /**
     * Computes the transitive closure of the given graph.
     *
     * @param graph - Graph to compute transitive closure for.
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     */
    public <V, E> void closeSimpleDirectedGraph(SimpleDirectedGraph<V, E> graph)
    {
        Set<V> vertexSet = graph.vertexSet();

        Set<V> newEdgeTargets = new HashSet<>();

        // At every iteration of the outer loop, we add a path of length 1
        // between nodes that originally had a path of length 2. In the worst
        // case, we need to make floor(log |V|) + 1 iterations. We stop earlier
        // if there is no change to the output graph.

        int bound = computeBinaryLog(vertexSet.size());
        boolean done = false;
        for (int i = 0; !done && (i < bound); ++i) {
            done = true;
            for (V v1 : vertexSet) {
                newEdgeTargets.clear();

                for (E v1OutEdge : graph.outgoingEdgesOf(v1)) {
                    V v2 = graph.getEdgeTarget(v1OutEdge);
                    for (E v2OutEdge : graph.outgoingEdgesOf(v2)) {
                        V v3 = graph.getEdgeTarget(v2OutEdge);

                        if (v1.equals(v3)) {
                            // Its a simple graph, so no self loops.
                            continue;
                        }

                        if (graph.getEdge(v1, v3) != null) {
                            // There is already an edge from v1 ---> v3, skip;
                            continue;
                        }

                        newEdgeTargets.add(v3);
                        done = false;
                    }
                }

                for (V v3 : newEdgeTargets) {
                    graph.addEdge(v1, v3);
                }
            }
        }
    }

    /**
     * Computes floor($\log_2 (n)$) $+ 1$
     */
    private int computeBinaryLog(int n)
    {
        assert n >= 0;

        int result = 0;
        while (n > 0) {
            n >>= 1;
            ++result;
        }

        return result;
    }

    /**
     * Computes the transitive closure of a directed acyclic graph in $O(nm)$
     *
     * @param graph - Graph to compute transitive closure for.
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     */
    public <V, E> void closeDirectedAcyclicGraph(DirectedAcyclicGraph<V, E> graph)
    {
        Deque<V> orderedVertices = new ArrayDeque<>(graph.vertexSet().size());
        new TopologicalOrderIterator<>(graph).forEachRemaining(orderedVertices::addFirst);

        for (V vertex : orderedVertices) {
            for (V successor : Graphs.successorListOf(graph, vertex)) {
                for (V closureVertex : Graphs.successorListOf(graph, successor)) {
                    graph.addEdge(vertex, closureVertex);
                }
            }
        }
    }

}

// End TransitiveClosure.java

/*
 * (C) Copyright 2018-2018, by Alexandru Valeanu and Contributors.
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
package org.integratedmodelling.contrib.jgrapht.alg.interfaces;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.GraphPath;
import org.integratedmodelling.contrib.jgrapht.graph.GraphWalk;
import org.integratedmodelling.contrib.jgrapht.util.ArrayUnenforcedSet;

/**
 * An algorithm which computes a decomposition into disjoint paths for a given tree/forest
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public interface TreeToPathDecompositionAlgorithm<V, E> {
    /**
     * Computes a path decomposition.
     *
     * @return a path decomposition
     */
    PathDecomposition<V, E> getPathDecomposition();

    /**
     * A path decomposition.
     *
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     */
    interface PathDecomposition<V, E> {
        /**
         * Set of edges of the path decomposition.
         * 
         * @return edge set of the path decomposition
         */
        Set<E> getEdges();

        /**
         * Set of disjoint paths of the decomposition
         *
         * @return list of vertex paths
         */
        Set<GraphPath<V, E>> getPaths();

        /**
         * @return number of paths in the decomposition
         */
        default int numberOfPaths(){
            return getPaths().size();
        }
    }

    /**
     * Default implementation of the path decomposition interface.
     *
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     */
    class PathDecompositionImpl<V, E> implements PathDecomposition<V, E>, Serializable {

        private static final long serialVersionUID = 8468626434814461297L;
        private final Set<E> edges;
        private final Set<GraphPath<V, E>> paths;

        /**
         * Construct a new path decomposition.
         *
         * @param graph the graph
         * @param edges the edges
         * @param paths the vertex paths
         */
        public PathDecompositionImpl(Graph<V, E> graph, Set<E> edges, List<List<V>> paths) {
            this.edges = edges;

            Set<GraphPath<V, E>> arrayUnenforcedSet = paths.stream()
                    .map(path -> new GraphWalk<>(graph, path, path.size()))
                    .collect(Collectors.toCollection(ArrayUnenforcedSet::new));

            this.paths = Collections.unmodifiableSet(arrayUnenforcedSet);
        }

        @Override
        public Set<E> getEdges() {
            return edges;
        }

        @Override
        public Set<GraphPath<V, E>> getPaths() {
            return paths;
        }

        @Override
        public String toString() {
            return "Path-Decomposition [edges=" + edges + "," + "paths=" + getPaths() + "]";
        }
    }

}

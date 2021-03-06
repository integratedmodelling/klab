/*
 * (C) Copyright 2007-2018, by France Telecom and Contributors.
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
package org.integratedmodelling.contrib.jgrapht.alg.shortestpath;

import java.util.*;

import org.integratedmodelling.contrib.jgrapht.*;

/**
 * List of paths <code>AbstractPathElement</code> with same target vertex.
 *
 * @since July 5, 2007
 */
abstract class AbstractPathElementList<V, E, T extends AbstractPathElement<V, E>>
    extends
    AbstractList<T>
{
    protected Graph<V, E> graph;

    /**
     * Max number of stored paths.
     */
    protected int maxSize;

    /**
     * Stored paths, list of <code>AbstractPathElement</code>.
     */
    protected ArrayList<T> pathElements = new ArrayList<>();

    /**
     * Target vertex of the paths.
     */
    protected V vertex;

    /**
     * Creates paths obtained by concatenating the specified edge to the specified paths.
     *
     * @param maxSize maximum number of paths the list is able to store.
     * @param elementList paths, list of <code>AbstractPathElement</code>.
     * @param edge edge reaching the end vertex of the created paths.
     *
     * @throws NullPointerException if the specified prevPathElementList or edge is
     *         <code>null</code>.
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or $0$.
     */
    protected AbstractPathElementList(
        Graph<V, E> graph, int maxSize, AbstractPathElementList<V, E, T> elementList, E edge)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize is negative or 0");
        }
        if (elementList == null) {
            throw new NullPointerException("elementList is null");
        }
        if (edge == null) {
            throw new NullPointerException("edge is null");
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex = Graphs.getOppositeVertex(graph, edge, elementList.getVertex());
    }

    /**
     * Creates a list with an empty path. The list size is $1$.
     *
     * @param maxSize maximum number of paths the list is able to store.
     *
     * @throws NullPointerException if the specified path-element is <code>
     * null</code>.
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or 0.
     * @throws IllegalArgumentException if <code>pathElement</code> is not empty.
     */
    protected AbstractPathElementList(Graph<V, E> graph, int maxSize, T pathElement)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize is negative or 0");
        }
        if (pathElement == null) {
            throw new NullPointerException("pathElement is null");
        }
        if (pathElement.getPrevEdge() != null) {
            throw new IllegalArgumentException("path must be empty");
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex = pathElement.getVertex();

        this.pathElements.add(pathElement);
    }

    /**
     * Creates an empty list. The list size is $0$.
     *
     * @param maxSize maximum number of paths the list is able to store.
     *
     * @throws IllegalArgumentException if <code>maxSize</code> is negative or $0$.
     */
    protected AbstractPathElementList(Graph<V, E> graph, int maxSize, V vertex)
    {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize is negative or 0");
        }

        this.graph = graph;
        this.maxSize = maxSize;
        this.vertex = vertex;
    }

    /**
     * Returns path <code>AbstractPathElement</code> stored at the specified index.
     */
    @Override
    public T get(int index)
    {
        return this.pathElements.get(index);
    }

    /**
     * Returns target vertex.
     */
    public V getVertex()
    {
        return this.vertex;
    }

    /**
     * Returns the number of paths stored in the list.
     */
    @Override
    public int size()
    {
        return this.pathElements.size();
    }
}

// End AbstractPathElementList.java

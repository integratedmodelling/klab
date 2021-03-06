/*
 * (C) Copyright 2003-2018, by John V Sichi and Contributors.
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
package org.integratedmodelling.contrib.jgrapht.generate;

import java.util.*;
import java.util.function.*;

import org.integratedmodelling.contrib.jgrapht.*;
import org.integratedmodelling.contrib.jgrapht.graph.*;

/**
 * Generates a <a href="http://mathworld.wolfram.com/WheelGraph.html">wheel graph</a> of any size.
 * Reminding a bicycle wheel, a wheel graph has a hub vertex in the center and a rim of vertices
 * around it that are connected to each other (as a ring). The rim vertices are also connected to
 * the hub with edges that are called "spokes".
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author John V. Sichi
 * @since Sep 16, 2003
 */
public class WheelGraphGenerator<V, E>
    implements
    GraphGenerator<V, E, V>
{
    /**
     * Role for the hub vertex.
     */
    public static final String HUB_VERTEX = "Hub Vertex";

    private boolean inwardSpokes;
    private int size;

    /**
     * Creates a new WheelGraphGenerator object. This constructor is more suitable for undirected
     * graphs, where spokes' direction is meaningless. In the directed case, spokes will be oriented
     * from rim to hub.
     *
     * @param size number of vertices to be generated.
     */
    public WheelGraphGenerator(int size)
    {
        this(size, true);
    }

    /**
     * Construct a new WheelGraphGenerator.
     *
     * @param size number of vertices to be generated.
     * @param inwardSpokes if <code>true</code> and graph is directed, spokes are oriented from rim
     *        to hub; else from hub to rim.
     *
     * @throws IllegalArgumentException in case the number of vertices is negative
     */
    public WheelGraphGenerator(int size, boolean inwardSpokes)
    {
        if (size < 0) {
            throw new IllegalArgumentException("must be non-negative");
        }

        this.size = size;
        this.inwardSpokes = inwardSpokes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateGraph(Graph<V, E> target, Map<String, V> resultMap)
    {
        if (size < 1) {
            return;
        }

        // A little trickery to intercept the rim generation. This is
        // necessary since target may be initially non-empty, meaning we can't
        // rely on its vertex set after the rim is generated.
        final Collection<V> rim = new ArrayList<>();
        final Supplier<V> initialSupplier = target.getVertexSupplier();
        Supplier<V> rimVertexSupplier = () -> {
            V vertex = initialSupplier.get();
            rim.add(vertex);
            return vertex;
        };

        Graph<V, E> targetWithRimVertexSupplier =
            new GraphDelegator<>(target, rimVertexSupplier, null);

        new RingGraphGenerator<V, E>(size - 1)
            .generateGraph(targetWithRimVertexSupplier, resultMap);

        V hubVertex = target.addVertex();

        if (resultMap != null) {
            resultMap.put(HUB_VERTEX, hubVertex);
        }

        for (V rimVertex : rim) {
            if (inwardSpokes) {
                target.addEdge(rimVertex, hubVertex);
            } else {
                target.addEdge(hubVertex, rimVertex);
            }
        }
    }
}

// End WheelGraphGenerator.java

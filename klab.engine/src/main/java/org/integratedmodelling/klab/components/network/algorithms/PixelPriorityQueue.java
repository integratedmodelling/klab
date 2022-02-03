/*
 * Copyright (C) 2019 Laboratoire ThéMA - UMR 6049 - CNRS / Université de Franche-Comté
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.integratedmodelling.klab.components.network.algorithms;

import java.util.Arrays;

/**
 *
 * @author gvuidel
 */
public class PixelPriorityQueue {

    private static final int INITIAL_CAPACITY = 100;

    /**
     * Priority queue represented as a balanced binary heap: the two
     * children of queue[n] are queue[2*n+1] and queue[2*(n+1)].  The
     * priority queue is ordered by comparator, or by the elements'
     * natural ordering, if comparator is null: For each node n in the
     * heap and each descendant d of n, n <= d.  The element with the
     * lowest value is in queue[0], assuming the queue is nonempty.
     */
    private int[] queueInd; 
    private double[] queueDist; 

    /**
     * The number of elements in the priority queue.
     */
    private int size = 0;

    /**
     * Creates a {@code PixelPriorityQueue} with the default initial
     * capacity (11).
     */
    public PixelPriorityQueue() {
        this.queueInd = new int[INITIAL_CAPACITY];
        this.queueDist = new double[INITIAL_CAPACITY];
    }


    /**
     * Increases the capacity of the array.
     *
     */
    private void grow() {
        int oldCapacity = queueInd.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        queueInd = Arrays.copyOf(queueInd, newCapacity);
        queueDist = Arrays.copyOf(queueDist, newCapacity);
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(int ind, double dist) {
        int i = size;
        if (i >= queueInd.length) {
            grow();
        }
        size = i + 1;
        if (i == 0) { 
            queueInd[0] = ind;
            queueDist[0] = dist;
        } else {
            siftUp(i, ind, dist);
        }
        return true;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void poll(double[] result) {
        if (size == 0) {
            result[0] = -1;
        }
        int s = --size;
        result[0] = queueInd[0];
        result[1] = queueDist[0];
        int xInd = queueInd[s];
        double xDist = queueDist[s];
        queueInd[s] = -1;
        queueDist[s] = Double.NaN;
        if (s != 0) {
            siftDown(0, xInd, xDist);
        }
    }


    /**
     * Inserts item x at position k, maintaining heap invariant by
     * promoting x up the tree until it is greater than or equal to
     * its parent, or is the root.
     *
     * @param k the position to fill
     */
    private void siftUp(int k, int ind, double dist) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            int eInd = queueInd[parent];
            double eDist = queueDist[parent];
            if (dist >= eDist) {
                break;
            }
            queueInd[k] = eInd;
            queueDist[k] = eDist;
            k = parent;
        }
        queueInd[k] = ind;
        queueDist[k] = dist;
    }

    /**
     * Inserts item x at position k, maintaining heap invariant by
     * demoting x down the tree repeatedly until it is less than or
     * equal to its children or is a leaf.
     *
     * @param k the position to fill
     */
    private void siftDown(int k, int ind, double dist) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            int cInd = queueInd[child];
            double cDist = queueDist[child];
            int right = child + 1;
            if (right < size &&
                cDist > queueDist[right]) {
                child = right;
                cInd = queueInd[child];
                cDist = queueDist[child];
            }
            if (dist <= cDist) {
                break;
            }
            queueInd[k] = cInd;
            queueDist[k] = cDist;
            k = child;
        }
        queueInd[k] = ind;
        queueDist[k] = dist;
    }


}
    


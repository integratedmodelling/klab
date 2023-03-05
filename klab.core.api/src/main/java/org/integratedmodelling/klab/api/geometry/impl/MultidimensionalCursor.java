/*******************************************************************************
 * Copyright (C) 2007, 2014:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.geometry.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.integratedmodelling.klab.api.collections.impl.Triple;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KGeometry.Dimension;

public class MultidimensionalCursor {

    public enum StorageOrdering {
        // the first dimension varies slowest FIXME the name means the opposite
        ROW_FIRST,
        COLUMN_FIRST;
    }

    /**
     * At this stage we would only need a vector, but this can come in handy to
     * support descending dimensions later.
     * 
     * @author Ferdinando Villa
     *
     */
    private class StorageOrder {

        public ArrayList<Integer> ordering = new ArrayList<Integer>();

        void set(int dimensions, StorageOrdering order) {

            ordering.clear();

            for (int i = 0; i < dimensions; i++) {
                if (order == StorageOrdering.ROW_FIRST) {
                    ordering.add(dimensions - 1 - i);
                } else if (order == StorageOrdering.COLUMN_FIRST) {
                    ordering.add(i);
                }
            }
        }
    }

    /*
     * iterable to scan one dimension given values for all others
     */
    class SliceCursor implements Iterable<Long> {

        long[] extents;
        int    varIndex;
        long   varSize;

        class It implements Iterator<Long> {

            long current = 0;

            @Override
            public boolean hasNext() {
                return current < varSize;
            }

            @Override
            public Long next() {
                extents[varIndex] = current++;
                return getElementOffset(extents);
            }

            @Override
            public void remove() {
                // TODO Auto-generated method stub

            }

        }

        public SliceCursor(int dimIndex, long[] otherDimOffsets) {
            extents = otherDimOffsets;
            varIndex = dimIndex;
            varSize = MultidimensionalCursor.this.extents.get(varIndex);
        }

        public SliceCursor(int dimIndex, long[] otherDimOffsets, long sliceIndex, int sliceNumber) {
            extents = otherDimOffsets;
            varIndex = dimIndex;
            varSize = MultidimensionalCursor.this.extents.get(varIndex);
            /*
             * TODO offsets
             */
        }

        @Override
        public Iterator<Long> iterator() {
            return new It();
        }

    }

    long            multiplicity;
    int             dimensions;
    ArrayList<Long> extents      = new ArrayList<Long>();
    ArrayList<Long> strides      = new ArrayList<Long>();
    StorageOrdering storageOrderType;
    StorageOrder    storageOrder = new StorageOrder();
    int[]           ordering     = null;

    public MultidimensionalCursor(StorageOrdering order) {
        multiplicity = 0;
        dimensions = 0;
        storageOrderType = order;
    }

    public MultidimensionalCursor(KGeometry geometry) {
        multiplicity = 0;
        dimensions = 0;
        storageOrderType = StorageOrdering.ROW_FIRST;
        long[] dims = new long[geometry.getDimensions().size()];
        int i = 0;
        for (Dimension dimension : geometry.getDimensions()) {
            dims[i++] = dimension.size();
        }
        defineDimensions(dims);
        initializeStrides();
    }
    
    public MultidimensionalCursor(KGeometry geometry, long[] lockedDimensions) {
        multiplicity = 0;
        dimensions = 0;
        storageOrderType = StorageOrdering.ROW_FIRST;
        long[] dims = new long[geometry.getDimensions().size()];
        int i = 0;
        for (Dimension dimension : geometry.getDimensions()) {
            dims[i] = lockedDimensions[i] < 0 ? dimension.size() : 1;
            i++;
        }
        defineDimensions(dims);
        initializeStrides();
    }
    
    public MultidimensionalCursor() {
        this(StorageOrdering.ROW_FIRST);
    }

    public MultidimensionalCursor(MultidimensionalCursor cursor) {
        multiplicity = cursor.multiplicity;
        dimensions = cursor.dimensions;
        storageOrderType = cursor.storageOrderType;
        extents = cursor.extents;
        storageOrder = cursor.storageOrder;
        ordering = cursor.ordering;
        strides = cursor.strides;
    }

    public void reset() {
        multiplicity = 0;
        extents.clear();
        strides.clear();
    }

    public Iterable<Long> getDimensionScanner(int dimIndex, long[] otherDimOffsets) {
        return new SliceCursor(dimIndex, otherDimOffsets);
    }

    public Iterable<Long> getDimensionScanner(int dimIndex, long[] otherDimOffsets, long sliceIndex, int sliceNumber) {
        return new SliceCursor(dimIndex, otherDimOffsets, sliceIndex, sliceNumber);
    }

    private long initializeStrides() {

        long stride = 1;
        multiplicity = 1;
        strides.clear();
        for (int n = 0; n != dimensions; ++n)
            strides.add(0L);
        ordering = new int[dimensions];
        storageOrder.set(dimensions, storageOrderType);
        for (int n = 0; n != dimensions; ++n) {
            ordering[n] = storageOrder.ordering.get(n);
            strides.set(storageOrder.ordering.get(n), stride);
            stride *= extents.get(storageOrder.ordering.get(n));
            multiplicity *= extents.get(storageOrder.ordering.get(n));
        }

        return multiplicity;
    }

    public long[] getElementIndexes(long subscaleOffset) {

        long[] ret = new long[dimensions];
        long rest = subscaleOffset;

        if (dimensions == 0)
            return ret;

        if (storageOrderType == StorageOrdering.COLUMN_FIRST) {
            for (int i = dimensions - 1; i > 0; i--) {
                ret[i] = subscaleOffset / strides.get(i);
                rest -= ret[i] * strides.get(i);
            }
            ret[0] = rest;
        } else {
            for (int i = 0; i < dimensions - 1; i++) {
                ret[i] = subscaleOffset / strides.get(i);
                rest -= ret[i] * strides.get(i);
            }
            ret[dimensions - 1] = rest;
        }

        return ret;
    }

    /**
     * 
     * @param indices
     * @return linear offset
     */
    public long getElementOffset(long... indices) {
        long offset = 0;
        for (int n = 0; n < dimensions; ++n)
            offset += indices[n] * strides.get(n);
        return offset;
    }

    /**
     * returns the three offsets needed to implement a strided scanner - start
     * offset, past end offset, and stride - over a specified dimension. Must be
     * passed the dimension int and a vector with all the remaining offsets -
     * the one from dimension dim is ignored.
     * 
     * @param dimension
     * @param intes
     * @return the offsets
     */
    public Triple<Long, Long, Long> getStridedOffsets(int dimension, long[] intes) {

        intes[dimension] = 0;
        long ofs = getElementOffset(intes);
        intes[dimension] = 1;
        long stp = getElementOffset(intes) - ofs;
        intes[dimension] = extents.get(dimension);
        long end = getElementOffset(intes);
        return new Triple<>(ofs, end, stp);
    }
    
    public MultidimensionalCursor(long... extents) {
        multiplicity = 0;
        dimensions = 0;
        storageOrderType = StorageOrdering.ROW_FIRST;
    	defineDimensions(extents);
    }

    /**
     * 
     * @param extents
     * @return multiplicity
     */
    public long defineDimensions(long... extents) {

        reset();

        dimensions = extents == null ? 0 : extents.length;

        if (extents != null) {
            for (long ii : extents)
                this.extents.add(ii);
        }
        return initializeStrides();
        
    }

    /**
     * the extent of the specified dimension. We only support 0-based extents
     * for now.
     * 
     * @param nDim
     * @return dimension size
     */
    public long getDimensionSize(int nDim) {
        return extents.get(nDim);
    }

    /**
     * 
     * @return number of dimensions
     */
    public int getDimensionsCount() {
        return dimensions;
    }

    public long[] getExtents() {
        long[] ret = new long[extents.size()];
        int i = 0;
        for (long ex : extents) {
            ret[i++] = ex;
        }
        return ret;
    }

    /**
     * 
     * @return multiplicity
     */
    public long getMultiplicity() {
        return multiplicity;
    }

    public static void main(String[] args) {

        int[][] data = {
                { 0, 1, 2, 3, 4, 5, 6 },
                { 7, 8, 9, 10, 11, 12, 13 },
                { 14, 15, 16, 17, 18, 19, 20 } };

        MultidimensionalCursor md = new MultidimensionalCursor(MultidimensionalCursor.StorageOrdering.ROW_FIRST);

        // x size (cols), y size (rows)
        System.out.println("dimensions are x = " + data[0].length + " (columns) * y=" + data.length
                + " (rows)");

        long size = md.defineDimensions(data[0].length, data.length);

        System.out.println("strides = " + md.strides);
        System.out.println("orderin = " + md.storageOrder.ordering);

        for (int i = 0; i < size; i++) {
            long[] xy = md.getElementIndexes(i);
            System.out.println("order " + i + "-> (" + xy[0] + "," + xy[1] + ")");
            System.out.println("\t -> " + data[(int) xy[1]][(int) xy[0]]);
        }
    }

}

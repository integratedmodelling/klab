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
package org.integratedmodelling.klab.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;

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
	class SliceCursor implements Iterable<Integer> {

		int[] extents;
		int varIndex;
		int varSize;

		class It implements Iterator<Integer> {

			int current = 0;

			@Override
			public boolean hasNext() {
				return current < varSize;
			}

			@Override
			public Integer next() {
				extents[varIndex] = current++;
				return getElementOffset(extents);
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub

			}

		}

		public SliceCursor(int dimIndex, int[] otherDimOffsets) {
			extents = otherDimOffsets;
			varIndex = dimIndex;
			varSize = MultidimensionalCursor.this.extents.get(varIndex);
		}

		public SliceCursor(int dimIndex, int[] otherDimOffsets, int sliceIndex, int sliceNumber) {
			extents = otherDimOffsets;
			varIndex = dimIndex;
			varSize = MultidimensionalCursor.this.extents.get(varIndex);
			/*
			 * TODO offsets
			 */
		}

		@Override
		public Iterator<Integer> iterator() {
			return new It();
		}

	}

	int multiplicity;
	int dimensions;
	ArrayList<Integer> extents = new ArrayList<Integer>();
	ArrayList<Integer> strides = new ArrayList<Integer>();
	StorageOrdering storageOrderType;
	StorageOrder storageOrder = new StorageOrder();
	int[] ordering = null;

	public MultidimensionalCursor(StorageOrdering order) {
		multiplicity = 0;
		dimensions = 0;
		storageOrderType = order;
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

	public Iterable<Integer> getDimensionScanner(int dimIndex, int[] otherDimOffsets) {
		return new SliceCursor(dimIndex, otherDimOffsets);
	}

	public Iterable<Integer> getDimensionScanner(int dimIndex, int[] otherDimOffsets, int sliceIndex, int sliceNumber) {
		return new SliceCursor(dimIndex, otherDimOffsets, sliceIndex, sliceNumber);
	}

	private int initializeStrides() {

		int stride = 1;
		multiplicity = 1;
		strides.clear();
		for (int n = 0; n != dimensions; ++n)
			strides.add(0);
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

	public int[] getElementIndexes(int subscaleOffset) {

		int[] ret = new int[dimensions];
		int rest = subscaleOffset;

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
	public int getElementOffset(int... indices) {
		int offset = 0;
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
	public Triple<Integer, Integer, Integer> getStridedOffsets(int dimension, int[] intes) {

		intes[dimension] = 0;
		int ofs = getElementOffset(intes);
		intes[dimension] = 1;
		int stp = getElementOffset(intes) - ofs;
		intes[dimension] = extents.get(dimension);
		int end = getElementOffset(intes);
		return Tuples.create(ofs, end, stp);
	}

	/**
	 * 
	 * @param extents
	 * @return multiplicity
	 */
	public int defineDimensions(int... extents) {

		reset();

		dimensions = extents == null ? 0 : extents.length;

		if (extents != null)
			for (int ii : extents)
				this.extents.add(ii);

		return initializeStrides();
	}

	/**
	 * the extent of the specified dimension. We only support 0-based extents
	 * for now.
	 * 
	 * @param nDim
	 * @return dimension size
	 */
	public int getDimensionSize(int nDim) {
		return extents.get(nDim);
	}

	/**
	 * 
	 * @return number of dimensions
	 */
	public int getDimensionsCount() {
		return dimensions;
	}

	public int[] getExtents() {
		int[] ret = new int[extents.size()];
		int i = 0;
		for (int ex : extents) {
			ret[i++] = ex;
		}
		return ret;
	}

	/**
	 * 
	 * @return multiplicity
	 */
	public int getMultiplicity() {
		return multiplicity;
	}

	public static void main(String[] args) {

		int[][] data = { { 0, 1, 2, 3, 4, 5, 6 }, { 7, 8, 9, 10, 11, 12, 13 }, { 14, 15, 16, 17, 18, 19, 20 } };

		MultidimensionalCursor md = new MultidimensionalCursor(MultidimensionalCursor.StorageOrdering.ROW_FIRST);

		// x size (cols), y size (rows)
		System.out.println("dimensions are x = " + data[0].length + " (columns) * y=" + data.length + " (rows)");

		int size = md.defineDimensions(data[0].length, data.length);

		System.out.println("strides = " + md.strides);
		System.out.println("orderin = " + md.storageOrder.ordering);

		for (int i = 0; i < size; i++) {
			int[] xy = md.getElementIndexes(i);
			System.out.println("order " + i + "-> (" + xy[0] + "," + xy[1] + ")");
			System.out.println("\t -> " + data[xy[1]][xy[0]]);
		}
	}

}

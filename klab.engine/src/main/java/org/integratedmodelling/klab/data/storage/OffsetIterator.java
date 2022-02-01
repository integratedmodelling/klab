package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;

/**
 * Iterate a set of extents producing offsets for all the atomic states
 * pointed to.
 * 
 * @param data
 * @return
 */
class OffsetIterator implements Iterator<Offset> {

	final MultidimensionalCursor cursor = new MultidimensionalCursor();
	IScale scale;
	Iterator<ILocator>[] iterators;
	List<IExtent> extents;
	long current = 0;

	@SuppressWarnings("unchecked")
	OffsetIterator(IScale scale, List<IExtent> extents) {
		long[] sizes = new long[extents.size()];
		for (int i = 0; i < extents.size(); i++) {
			sizes[i] = extents.get(i).size();
		}
		this.extents = extents;
		this.iterators = new Iterator[extents.size()];
		this.cursor.defineDimensions(sizes);
		this.scale = scale;
	}

	public long size() {
		return cursor.getMultiplicity();
	}

	@Override
	public boolean hasNext() {
		return current < cursor.getMultiplicity();
	}

	@Override
	public Offset next() {

		double weight = 1.0;
		long[] iteratorOffsets = cursor.getElementIndexes(current);
		long[] offsets = new long[iteratorOffsets.length];
		for (int i = 0; i < iteratorOffsets.length; i++) {
			if (iteratorOffsets[i] == 0) {
				iterators[i] = extents.get(i).iterator();
			}	
			AbstractExtent extent = ((AbstractExtent)iterators[i].next());
			if (extent == null) {
				return null;
			}
			offsets[i] = extent.getLocatedOffset();
			if (offsets[i] < 0) {
				// original extent wasn't located, i.e. use the full extent with offset 0
				offsets[i] = 0;
			}
			weight *= extent.getCoverage();
		}
		current ++;
		return new Offset(this.scale, offsets, weight);
	}
}
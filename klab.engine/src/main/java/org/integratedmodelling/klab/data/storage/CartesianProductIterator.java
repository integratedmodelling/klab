package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.data.storage.RescalingState.ExtentLocation;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Generate the scale-wide cartesian product of the touched extent offsets,
 * multiplying the individual weights.
 * 
 * @param data
 * @return
 */
class CartesianProductIterator implements Iterator<Pair<long[], Double>> {

	List<List<ExtentLocation>> data;
	long[] ret;
	final MultidimensionalCursor cursor = new MultidimensionalCursor();

	long current = 0;

	CartesianProductIterator(List<List<ExtentLocation>> data) {
		this.data = data;
		this.ret = new long[data.size()];
		long[] sizes = new long[data.size()];
		for (int i = 0; i < data.size(); i++) {
			sizes[i] = data.get(i).size();
		}
		this.cursor.defineDimensions(sizes);
	}

	public long size() {
		return cursor.getMultiplicity();
	}

	@Override
	public boolean hasNext() {
		return current < cursor.getMultiplicity();
	}

	@Override
	public Pair<long[], Double> next() {

		long[] offsets = cursor.getElementIndexes(current);
		double weight = 1.0;

		for (int i = 0; i < offsets.length; i++) {
			ret[i] = data.get(i).get((int) offsets[i]).offset;
			weight *= data.get(i).get((int) offsets[i]).weight;
		}

		current++;
		return new Pair<>(ret, weight);
	}
}
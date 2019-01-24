package org.integratedmodelling.klab.data.classification;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

/**
 * A simple discretization that uses ranges and strings for classes. Can be
 * converted into a classification if needed. Used when the overhead of concepts
 * is more of a burden than an advantage.
 * 
 * @author ferdinando.villa
 *
 */
public class Discretization implements IDataKey {

	List<Range> ranges = new ArrayList<>();
	List<String> clIds = new ArrayList<>();

	/**
	 * Builds natural breaks.
	 * 
	 * @param range
	 * @param nBins
	 */
	public Discretization(Range range, int nBins) {
		double delta = range.getWidth() / nBins;
		double start = range.getLowerBound();
		for (int i = 0; i < nBins; i++) {
			Range r = Range.create(start, start + delta, i != (nBins - 1));
			ranges.add(r);
			clIds.add(r.toString());
			start += delta;
		}
	}

	@Override
	public int size() {
		return ranges.size();
	}

	@Override
	public int reverseLookup(Object value) {
		if (value instanceof Number) {
			int i = 0;
			for (Range r : ranges) {
				if (r.contains(((Number)value).doubleValue())) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}

	@Override
	public List<String> getLabels() {
		return clIds;
	}

	@Override
	public List<Pair<Integer, String>> getAllValues() {
		List<Pair<Integer, String>> ret = new ArrayList<>();
		for (int i = 0; i < clIds.size(); i++) {
			ret.add(Pair.create(i, clIds.get(i)));
		}
		return ret;
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

	public Range getRange(int n) {
		return ranges.get(n);
	}

}

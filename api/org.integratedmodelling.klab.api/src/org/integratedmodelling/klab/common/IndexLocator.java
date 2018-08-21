package org.integratedmodelling.klab.common;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IExtent;

/**
 * A naïve locator that only passes numeric indexes to an extent's
 * {@link IExtent#at(org.integratedmodelling.klab.api.data.ILocator)} for it to
 * interpret. Use when life only gives you coordinates.
 * 
 * @author ferdinando.villa
 *
 */
public class IndexLocator implements ILocator {

	long[] coordinates;
	
	private IndexLocator() {}
	
	public static IndexLocator create(long... offsets) {
		IndexLocator ret = new IndexLocator();
		ret.coordinates = offsets;
		return ret;
	}

	public static IndexLocator create(int... offsets) {
		IndexLocator ret = new IndexLocator();
		ret.coordinates = new long[offsets.length];
		for (int i = 0; i < offsets.length; i++) {
			ret.coordinates[i] = offsets[i];
		}
		return ret;
	}

	/**
	 * Get the coordinates this was created with. No other methods can
	 * be called.
	 * 
	 * @return
	 */
	public long[] getCoordinates() {
		return coordinates;
	}
	
	@Override
	public ILocator at(ILocator locator) {
		throw new IllegalStateException("naive index locators do not mediate");
	}

	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		throw new IllegalStateException("naive index locators do not mediate");
	}

}

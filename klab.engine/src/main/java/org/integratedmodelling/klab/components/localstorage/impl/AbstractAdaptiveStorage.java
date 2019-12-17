package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

/**
 * Smart storage using a configurable backend to store slices that are only
 * created when the values are different from others. This guarantees
 * optimization of storage and scales from scalars to huge datasets as long as
 * the backend can provide slices.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public abstract class AbstractAdaptiveStorage<T> implements IDataStorage<T> {

	private NavigableMap<Long, Slice> slices = new TreeMap<>();
	private long highTimeOffset = -1;
	private long maxTimeOffset;
	private long sliceSize;
	private long slicesInBackend = 0;

	// FIXME this is only for debugging, remove when done.
	private IGeometry geometry;

	// if one value only, just store it here and forget about everything.
	private boolean isScalar = false;
	private Object scalarValue = null;
	
	/*
	 * If trivial, we have no time or just one time state, and can contain at most
	 * one slice.
	 */
	private boolean trivial;

	/**
	 * A slice is generated when there are values in a timestep. Within a slice,
	 * storage is generated on the backend values are different from the previous
	 * slice stored or when the values for the current timestep are no longer
	 * unique.
	 */
	class Slice {

		// if not using backend, < 0; otherwise the slice offset in it, 0 or <= time
		long sliceOffsetInBackend = -1l;

		// the timestep this slice refers to
		long timestep;

		public T getAt(long sliceOffset) {

			return getValueFromBackend(sliceOffset, this.sliceOffsetInBackend);
		}
		
		// TODO synchronization here voids the parallelism in most functions. The newSlice thing should be
		// put in the implementation and synchronized there, so that multiple put() may happen.
		public void setAt(long sliceOffset, T value) {

			if (isEmpty()) {
				newSlice();
			}

			setValueIntoBackend(value, sliceOffset, this.sliceOffsetInBackend);
		}
		
		public boolean isEmpty() {
			return sliceOffsetInBackend < 0;
		}

		private void newSlice() {
			this.sliceOffsetInBackend = slicesInBackend;
			slicesInBackend++;
			createBackendStorage(this.sliceOffsetInBackend, null);
		}
		
		Slice(long timestep, Slice closest) {
			this.timestep = timestep;
			if (closest != null) {
				if (closest.sliceOffsetInBackend >= 0) {
					this.sliceOffsetInBackend = closest.sliceOffsetInBackend + 1;
					duplicateBackendSlice(closest.sliceOffsetInBackend, this.sliceOffsetInBackend);
				}
			}
		}

	}

	/**
	 * The time offset reached so far. We don't need to know the max time possible
	 * and it may be infinite. We may not have slices that reach to this level,
	 * which means a value was assigned but it was equal to the value in the last
	 * recorded slice.
	 * 
	 * @return
	 */
	protected long getHighTimeOffset() {
		return highTimeOffset;
	}


	protected abstract void duplicateBackendSlice(long sliceToCopy, long newSliceIndex);

	/**
	 * The maximum possible time offset or IGeometry.INFINITE. Only meaningful if
	 * not trivial. Only for debugging and for the implementing classes to check so
	 * they can optimize storage when it's finite.
	 * 
	 * @return
	 */
	protected long getMaxTimeOffset() {
		return maxTimeOffset;
	}

	/**
	 * Size of a non-temporal slice. All we need is linear storage of this size per
	 * each slice.
	 * 
	 * @return
	 */
	protected long getSliceSize() {
		return sliceSize;
	}

	protected AbstractAdaptiveStorage(IGeometry geometry) {

		this.isScalar = geometry.size() == 1;
		this.geometry = geometry;
		Dimension time = geometry.getDimension(Type.TIME);
		if (time == null) {
			this.trivial = true;
			this.sliceSize = geometry.size();
		} else if (time.size() == IGeometry.INFINITE_SIZE) {
			this.maxTimeOffset = time.size();
			this.sliceSize = geometry.size();
		} else {
			this.maxTimeOffset = time.size();
			this.sliceSize = geometry.size() / time.size();
		}

	}

	/**
	 * Create storage backend for the passed timestep. This would normally use a
	 * single backend for all timesteps, created only at the first call, but we call
	 * it anyway at each new timestep so that implementations are free to decide.
	 * 
	 * This can also be used to initialize the values from a previous timestep into
	 * the new one.
	 * 
	 * @param sliceOffsetInBackend the offset of the new slice. Calls will be
	 *                             ordered if the values are set in temporal order.
	 * @param initialValue         value with which to fill the slice. Null for
	 *                             nodata.
	 */
	protected abstract void createBackendStorage(long sliceOffsetInBackend, T initialValue);

	/**
	 * Get a value from the backend. The sliceOffsetInBackend is whatever we need to
	 * use for the specific timestep; it's <= the real timestep.
	 * 
	 * @param offsetInSlice        the offset within the slice specified by the
	 *                             second parameter
	 * @param sliceOffsetInBackend the offset of the slice in the backend
	 * @return
	 */
	protected abstract T getValueFromBackend(long offsetInSlice, long sliceOffsetInBackend);

	/**
	 * Write a value to the backend. The backendTimeSlice is whatever we need to use
	 * for the specific timestep and it's <= the real timestep; if the writes are
	 * monotonic in time, this will be called with values increasing by 0 or 1.
	 * 
	 * @param value
	 * @param offsetInSlice
	 * @param timestep
	 */
	protected abstract void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice);

	@SuppressWarnings("unchecked")
	public synchronized T get(ILocator locator) {

		if (isScalar) {
			return (T)scalarValue;
		}
		
		if (slices.isEmpty()) {
			return null;
		}

		Offset offsets = locator.as(Offset.class);

		if (offsets.length != geometry.getDimensions().size()) {
			throw new KlabInternalErrorException(
					"locator has different dimensionality than observation: should never happen");
		}

		long sliceOffset = product(offsets.pos, trivial ? 0 : 1);
		long timeOffset = trivial ? 0 : offsets.pos[0];

		// can only be the closest at this point.
		return getClosest(timeOffset).getAt(sliceOffset);
	}

	public long put(T value, ILocator locator) {

		if (isScalar) {
			scalarValue = value;
			return 0;
		}
		
		Offset offsets = locator.as(Offset.class);

		if (offsets.length != geometry.getDimensions().size()) {
			throw new KlabInternalErrorException(
					"locator has different dimensionality than observation: should never happen");
		}

		long sliceOffset = product(offsets.pos, trivial ? 0 : 1);
		long timeOffset = trivial ? 0 : offsets.pos[0];
		boolean noData = Observations.INSTANCE.isNodata(value);

		synchronized(this) {
			if (noData && slices.isEmpty()) {
				// everything's nodata so far, no need to store.
				return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
			}
		

			/*
			 * record high offset for posterity
			 */
			if (highTimeOffset < timeOffset) {
				highTimeOffset = timeOffset;
			}
	
			/*
			 * find the closest slice for the time
			 */
			Slice slice = getClosest(timeOffset);
			if (slice != null/* && slice.timestep != timeOffset */ && !slice.isEmpty() && equals(slice.getAt(sliceOffset), value)) {
				// don't store anything until it's different from the previous slice.
				return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
			}
	
			/*
			 * if we get here, we need to store in a slice of our own unless we found the
			 * exact timestep.
			 */
			if (slice == null || slice.timestep != timeOffset) {
				slice = addSlice(timeOffset, slice);
			}
	
			slice.setAt(sliceOffset, value);
		}

		return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
	}

	private long product(long[] offsets, int i) {
		long ret = offsets[i];
		for (int n = i + 1; n < offsets.length; n++) {
			ret *= offsets[n];
		}
		return ret;
	}

	private Slice addSlice(long timeOffset, Slice closest) {
		Slice slice = new Slice(timeOffset, closest);
		slices.put(timeOffset, slice);
		return slice;
	}

	private boolean equals(Object valueAt, T value) {
		return (valueAt == null && value == null) || (valueAt != null && value != null && valueAt.equals(value));
	}

	private Slice getClosest(long timeSlice) {
		Map.Entry<Long, Slice> low = slices.floorEntry(timeSlice);
		return low == null ? null : low.getValue();
	}

	protected int sliceCount() {
		return slices.size();
	}

	@Override
	public Object getObject(ILocator locator) {
		return get(locator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long putObject(Object value, ILocator locator) {
		return put((T) value, locator);
	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

}

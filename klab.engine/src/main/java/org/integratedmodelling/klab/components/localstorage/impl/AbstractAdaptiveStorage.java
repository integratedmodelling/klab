package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.LinkedList;
import java.util.List;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;

/**
 * Smart storage using a configurable backend to store slices that are not in
 * the current time projection. This guarantees optimization of storage and
 * scales from scalars to huge datasets as long as the backend can keep up.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public abstract class AbstractAdaptiveStorage<T> {

	/*
	 * slice storage: each slice is only added when needed along with its time
	 * index. There is always at least one slice in storage.
	 */
	List<Slice> slices = new LinkedList<>();

	/*
	 * the geometry of each slice, with time removed if it's there.
	 */
	IGeometry sliceGeometry;

	/*
	 * The time offset reached so far. We don't need to know the max time possible
	 * and it may be infinite.
	 */
	long maxTimeOffset;
	
	/*
	 * 
	 */
	long sliceSize;

	/**
	 * A slice is generated when there are values in a timestep. Within a slice,
	 * storage is generated on the backend values are different from the previous
	 * slice stored or when the values for the current timestep are no longer
	 * unique.
	 */
	class Slice {

		// assign to value, then use the smart logics after the first assignment
		boolean isNew = true;

		// use this until there is more than one value in the slice.
		T value = null;
		// if not using backend, < 0; otherwise the slice offset in it, 0 or <= time
		long sliceOffsetInBackend = -1l;

	}

	T getValue(long offset) {
		Slice slice = requestSlice(offset);
		return slice.sliceOffsetInBackend >= 0
				? getValueFromBackend(getOffsetInSlice(offset, slice.sliceOffsetInBackend), slice.sliceOffsetInBackend)
				: slice.value;
	}

	private long getOffsetInSlice(long offset, long sliceOffsetInBackend) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Slice requestSlice(long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	void setValue(T value, long offset) {

		Slice slice = requestSlice(offset);

		if (slice.isNew) {
			slice.value = value;
			slice.isNew = false;
			return;
		}

		boolean valuesDiffer = (slice.value == null && value != null) || (slice.value != null && value == null)
				|| (slice.value != null && value != null && !value.equals(slice.value));

		if (slice.sliceOffsetInBackend < 0 && valuesDiffer) {
//			request slice in backend and set value in it at the correspondent offset
		}

	}

	protected AbstractAdaptiveStorage(IGeometry geometry) {
	}

	/**
	 * Called once with the size of each slice before anything is written. Should
	 * ensure that each slice can hold the size passed. There is no guarantee that
	 * the storage will be used, so it will be best created and initialized when the
	 * first {@link #setValueIntoBackend(Object, long, long)} is called. 
	 * 
	 * @param sliceSize
	 */
	protected abstract void initializeStorage(long sliceSize);

	/**
	 * Create storage backend for the passed timestep. This would normally use a
	 * single backend for all timesteps, created only at the first call, but we call
	 * it anyway at each new timestep so that implementations are free to decide.
	 * 
	 * This can also be used to initialize the values from a previous timestep into
	 * the new one.
	 * 
	 * @param timestep
	 */
	protected abstract void createBackendStorage(long timestep);

	/**
	 * Get a value from the backend. The backendTimeSlice is whatever we need to use
	 * for the specific timestep and it's <= the real timestep.
	 * 
	 * @param sliceOffset
	 * @return
	 */
	protected abstract T getValueFromBackend(long offsetInSlice, long backendTimeSlice);

	/**
	 * Write a value to the backend. The backendTimeSlice is whatever we need to use
	 * for the specific timestep and it's <= the real timestep
	 * 
	 * @param value
	 * @param offsetInSlice
	 * @param timestep
	 */
	protected abstract void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice);

	protected T getValue(ILocator locator) {
		return null;
	}

	protected void setValue(T value, ILocator locator) {
		
		long timeSlice = 0; // TODO
		Slice slice = null;
		if (slices.isEmpty() && !Observations.INSTANCE.isNodata(value)) {
			slices.add(slice = new Slice());
		} else {
			slice = findClosestSlice(timeSlice);
		}
		
		this.maxTimeOffset = timeSlice;
		
	}

	private Slice findClosestSlice(long timeSlice) {

		return null;
		
	}

	
}

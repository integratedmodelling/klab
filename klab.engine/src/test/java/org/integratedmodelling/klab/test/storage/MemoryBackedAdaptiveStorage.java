package org.integratedmodelling.klab.test.storage;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import java.lang.reflect.Array;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;

public class MemoryBackedAdaptiveStorage<T> extends AbstractAdaptiveStorage<T> {

	Map<Long, T[]> data = new HashMap<>();
	Class<?> cls;
	
	public MemoryBackedAdaptiveStorage(IGeometry geometry, Class<?> cls) {
		super(geometry);
		this.cls = cls;
	}

	@Override
	protected void initializeStorage(long sliceSize, boolean hasTime) {
		// nothing to do
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createBackendStorage(long timestep) {
		if (data.get(timestep) == null) {
			data.put(timestep, (T[]) Array.newInstance(cls, (int)getSliceSize()));
		}
	}

	@Override
	protected T getValueFromBackend(long offsetInSlice, long backendTimeSlice) {
		return data.get(backendTimeSlice)[(int)offsetInSlice];
	}

	@Override
	protected void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice) {
		data.get(backendTimeSlice)[(int)offsetInSlice] = value;
	}

	@Override
	protected void fillSlice(long sliceIndex, T value) {
		for (long n = 0; n < getSliceSize(); n++) {
			data.get(sliceIndex)[(int)n] = value;
		}
	}

}

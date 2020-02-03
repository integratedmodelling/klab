package org.integratedmodelling.klab.test.storage;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

import java.io.IOException;
import java.lang.reflect.Array;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;

public class MemoryBackedAdaptiveStorage<T> extends AbstractAdaptiveStorage<T> {

	Map<Long, T[]> data = new HashMap<>();
	Class<?> cls;

	public MemoryBackedAdaptiveStorage(IGeometry geometry, Class<?> cls) {
		super(geometry);
		this.cls = cls;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void createBackendStorage(long timestep, T initialValue) {
		if (data.get(timestep) == null) {
			T[] slice =  (T[]) Array.newInstance(cls, (int)getSliceSize());
			for (long n = 0; n < getSliceSize(); n++) {
				slice[(int)n] = initialValue;
			}
			data.put(timestep, slice);
		}
	}

	@Override
	protected T getValueFromBackend(long offsetInSlice, long backendTimeSlice) {
		return data.get(backendTimeSlice)[(int) offsetInSlice];
	}

	@Override
	protected void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice) {
		data.get(backendTimeSlice)[(int) offsetInSlice] = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void duplicateBackendSlice(long sliceToCopy, long newSliceIndex) {
		T[] slice = data.get(newSliceIndex);
		T[] sourc = data.get(sliceToCopy);
		if (slice == null) {
			slice =  (T[]) Array.newInstance(cls, (int)getSliceSize());
			data.put(newSliceIndex, slice);
		}
		for (int i = 0; i < slice.length; i++) {
			slice[i] = sourc[i];
		}
		
	}

	public int getSliceCount() {
		return super.sliceCount();
	}

	public int getBackendSliceCount() {
		return data.size();
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(ITime time) {
		// TODO Auto-generated method stub
		
	}

}

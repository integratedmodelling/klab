package org.integratedmodellling.cdm.storage;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;

public class NetCDFBackedStorage<T> extends AbstractAdaptiveStorage<T> {

	protected NetCDFBackedStorage(IGeometry geometry, Class<?> valueClass) {
		super(geometry);
	}

	@Override
	protected void initializeStorage(long sliceSize, boolean hasTime) {

	}

	@Override
	protected void createBackendStorage(long timestep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected T getValueFromBackend(long offsetInSlice, long backendTimeSlice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fillSlice(long sliceSize, T value) {
		// TODO Auto-generated method stub
		
	}


}

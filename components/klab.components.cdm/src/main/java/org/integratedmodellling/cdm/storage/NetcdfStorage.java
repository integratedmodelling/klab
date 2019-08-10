package org.integratedmodellling.cdm.storage;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;

public class NetcdfStorage<T> extends AbstractAdaptiveStorage<T> {

	protected NetcdfStorage(IGeometry geometry) {
		super(geometry);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializeStorage(long sliceSize) {
		// TODO Auto-generated method stub
		
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


}

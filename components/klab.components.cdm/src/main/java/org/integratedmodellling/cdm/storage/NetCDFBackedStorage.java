package org.integratedmodellling.cdm.storage;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.NameGenerator;

import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;

public class NetCDFBackedStorage<T> extends AbstractAdaptiveStorage<T> {

	NetcdfFileWriter writer = null;
	File storage = null;
	private Class<?> valueClass;
	private Dimension dataDim;
	private Dimension timeDim;
	boolean first = true;

	private NetcdfFileWriter getWriter() {

		if (storage == null) {
			storage = new File(Configuration.INSTANCE.getTemporaryDataDirectory() + File.separator + "ktmp_"
					+ NameGenerator.shortUUID() + ".nc");
		}

		if (writer == null) {
			try {
				writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, storage.toString());
				storage.deleteOnExit();
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
			this.dataDim = writer.addDimension(null, "data", (int)getSliceSize());
			this.timeDim = writer.addUnlimitedDimension("time");
		}
		
		return writer;
	}

	protected NetCDFBackedStorage(IGeometry geometry, Class<?> valueClass) {
		super(geometry);
		this.valueClass = valueClass;
	}

	@Override
	protected void initializeStorage(long sliceSize, boolean hasTime) {

	}

	@Override
	protected void createBackendStorage(long timestep, T initialValue) {
		if (!first) {
			try {
				getWriter().flush();
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
			first = false;
		}
		
		// TODO
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
	protected void duplicateBackendSlice(long sliceToCopy, long newSliceIndex) {
		// TODO Auto-generated method stub

	}

}

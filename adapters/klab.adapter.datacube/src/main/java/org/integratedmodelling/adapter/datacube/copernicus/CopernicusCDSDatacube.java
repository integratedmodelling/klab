package org.integratedmodelling.adapter.datacube.copernicus;

import java.io.File;

import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.components.time.extents.Time;

/**
 * Caching base Copernicus datacube for any CDS dataset
 * 
 * @author Ferd
 *
 */
public class CopernicusCDSDatacube extends ChunkedDatacubeRepository {

	public CopernicusCDSDatacube(String dataset, ITimeInstant dataStart) {
		super(Time.resolution(1, Type.DAY), Time.resolution(3, Type.MONTH), dataStart,
				Configuration.INSTANCE.getDataPath("copernicus/" + dataset));
	}

	@Override
	protected boolean downloadChunk(int chunk, String variable, File destinationDirectory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean processChunk(int chunk, String variable, File destinationDirectory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getDataFilename(String variable, int tick) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getAggregatedFilename(String variable, int startTick, int endTick) {
		return variable + "__" + startTick + "_" + endTick + ".nc";
	}

	@Override
	protected boolean createAggregatedData(String variable, int startTick, int endTick, File destinationDirectory) {
		// TODO Auto-generated method stub
		return false;
	}


}

package org.integratedmodelling.weather.adapters.agera;

import org.integratedmodelling.adapter.datacube.Datacube;
import org.integratedmodelling.adapter.datacube.Datacube.AvailabilityService;
import org.integratedmodelling.klab.api.data.IGeometry;

public class AgERAAvailabilityService implements AvailabilityService {
		
	/*
	 * Checking availability means returning NONE if no possibility of ever getting
	 * the data exists; starting the processing and returning DELAYED if data
	 * require retrieval and/or processing before use; or returning AVAILABLE if
	 * available right away in normal response times.
	 * 
	 * The process is composed of 5 phases:
	 * 
	 * 1. establish the needed chunks. A chunk is a 3-month dataset downloaded as a
	 * single package (it's below the 100 "item" threshold). Chunks are named with
	 * the name of the variable, the year and the trimester (0-3) separated by
	 * slashes, e.g. temperature.max/2010/0.
	 * 
	 * 2. check if the chunks are available and if 1+ are not, return
	 * Availability.DELAYED and start the download. Download may be ongoing or have
	 * been asked before and failed.
	 * 
	 * 3. Chunks are available: establish the needed processing to obtain the base
	 * data. If processing is required, check if ongoing already. If ongoing, return
	 * Availability.DELAYED. If not, estimate the job size and start the processing.
	 * If job size > 20s, return Availability.DELAYED.
	 * 
	 * 4. Get processed data into primary data vessel. If caching is required, that
	 * will be the responsibility of the caching service, invoked after responding
	 * for uncached chunks.
	 * 
	 * 5. Establish needed postprocessing. Proceed as in 3.
	 * 
	 */

	@Override
	public Availability checkAvailability(IGeometry geometry, String variable, Datacube datacube) {

		
		
		/*
		 * we only worry about temporal availability because all AgERA datasets are
		 * global.
		 */
		return Availability.NONE;
	}
	
}

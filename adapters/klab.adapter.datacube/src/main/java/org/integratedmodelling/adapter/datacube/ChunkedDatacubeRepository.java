package org.integratedmodelling.adapter.datacube;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
import org.joda.time.Seconds;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;

/**
 * A repository where data are organized in chunk directories containing a set
 * of files for a variable, with uniform temporal coverage according to a
 * predefined "tick". Able to collect all the data for a period with the
 * necessary aggregation, using caching based on a specified set of temporal
 * intervals to minimize access.
 * <p>
 * The repository will malfunction unless the base for the chunk numbering (time
 * of first possible observation in fileResolution units) is not set.
 * <p>
 * The abstract Datacube class does not use this, but it can be used in derived
 * classes.
 * 
 * @author Ferd
 *
 */
public class ChunkedDatacubeRepository {

	private Resolution fileResolution;
	private Resolution chunkResolution;
	private File mainDirectory;
	private Resolution[] aggregationPoints;
	ITimeInstant timeBase;

	/**
	 * 
	 * @param fileResolution  the period covered by each file in a chunk
	 * @param chunkResolution the period covered by each chunk
	 * @param mainDirectory   the directory where the chunks are located
	 */
	public ChunkedDatacubeRepository(ITime.Resolution fileResolution, ITime.Resolution chunkResolution,
			ITimeInstant repositoryStart, File mainDirectory) {
		this.fileResolution = fileResolution;
		this.chunkResolution = chunkResolution;
		this.mainDirectory = mainDirectory;
		this.timeBase = repositoryStart;
	}

	public void setAggregationPoints(ITime.Resolution... resolutions) {
		this.aggregationPoints = resolutions;
	}

	/**
	 * Check for the availability of the data to cover the passed temporal extent.
	 * If startCaching is true and availability isn't immediate, call the download
	 * virtual method in a separate thread and return.
	 * 
	 * @param time
	 * @param variable
	 * @param startDownload
	 * @return
	 */
	public AvailabilityReference checkAvailability(ITime time, String variable, boolean startCaching) {

		AvailabilityReference ret = new AvailabilityReference();

		boolean downloading = false;
		int secs = 0;
		for (int chunk : getChunks(time)) {
			int seconds = isChunkAvailable(chunk, variable);
			// TODO divide this by number of threads in ingestor and add something
			secs += seconds;
			if (seconds > 0) {
				downloading = true;
				startChunkDownload(chunk, variable);
			}
		}

		ret.setRetryTimeSeconds(secs);
		ret.setAvailability(downloading ? Availability.DELAYED : Availability.NONE);

		return ret;
	}

	private int isChunkAvailable(int chunk, String variable) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void startChunkDownload(int chunk, String variable) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get the chunk numbers needed to cover the passed time. The numbers represent blocks of N seconds, where N is 
	 * the resolution of the chunk (chunkResolution.getSpan()).
	 * 
	 * @param time
	 * @return
	 */
	List<Integer> getChunks(ITime time) {
		List<Integer> ret = new ArrayList<>();
		long startchunk = Seconds
				.secondsBetween(((TimeInstant) timeBase).asDate(), ((TimeInstant) time.getStart()).asDate())
				.getSeconds()/(chunkResolution.getSpan()/1000);
		long endchunk = Seconds
				.secondsBetween(((TimeInstant) timeBase).asDate(), ((TimeInstant) time.getEnd()).asDate())
				.getSeconds()/(chunkResolution.getSpan()/1000);
		for (long n = startchunk; n <= endchunk; n++) {
			ret.add((int)n);
		}
		return ret;
	}

	/**
	 * Get all the file ticks necessary to cover one observation along a period. No
	 * aggregation strategy is considered here.
	 * 
	 * @param time
	 * @return
	 */
	List<Long> getTicks(ITime time) {
		List<Long> ret = new ArrayList<>();
		return ret;
	}

	public static void main(String[] dio) {
		
	}
	
}

package org.integratedmodelling.adapter.datacube;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

/**
 * A repository where data are organized in chunk directories containing a set
 * of files for a variable, with uniform temporal coverage according to a
 * predefined "tick". The chunk is a unit of download, the file a unit of
 * ingestion. It's able to collect all the data for a period with the necessary
 * aggregation, using caching based on a specified set of temporal intervals to
 * minimize access.
 * <p>
 * ACHTUNG: assumes the various resolutions are chosen to match perfectly.
 * Fractional resolutions won't work correctly. Uses real time units and handles
 * months and years appropriately.
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
public abstract class ChunkedDatacubeRepository {

	public static final String CHUNK_DOWNLOAD_TIME_MS = "time.download.ms";
	public static final String CHUNK_PROCESSING_TIME_MS = "time.processing.ms";

	private Resolution fileResolution;
	private Resolution chunkResolution;
	private File mainDirectory;
	private File aggregationDirectory;
	private List<Resolution> aggregationPoints;
	private TimeInstant timeBase;
	private Executor executor = null;

	/*
	 * estimated by averaging actual downloads as long as there is at least one
	 * chunk.
	 */
	int estimatedChunkDownloadTimeSeconds = 200;

	public class Granule {
		/**
		 * The datafile to use for data retrieval
		 */
		public File dataFile;
		/**
		 * How many of the data resolution units are represented by this (possibly
		 * aggregated) file. Files that result from aggregation have multipliers > 1.
		 */
		public int multiplier;
		/**
		 * If > 0, the file does not exist and the value is the estimated number of
		 * seconds required to produce it or download the chunk it's in.
		 */
		public int aggregationTimeSeconds;

	}

	/**
	 * All the information pertaining to an observation strategy, including all data
	 * files needed (existing or not) along with an estimated time to download
	 * completion (if 0, all data are available).
	 * 
	 * @author Ferd
	 *
	 */
	public class Strategy {

		public List<Granule> granules = new ArrayList<>();
		public List<Integer> chunks = new ArrayList<>();
		public List<Integer> ticks = new ArrayList<>();
		public int timeToAvailabilitySeconds;

		public String dump() {
			StringBuffer ret = new StringBuffer(1024);
			ret.append("Download strategy\n");
			int ttime = timeToAvailabilitySeconds;
			int ng = 0;
			for (Granule g : granules) {
				ret.append("   " + ng + ": " + g.dataFile + " ["
						+ (g.multiplier == 1 ? "data"
								: ("aggregating " + g.multiplier + " est. " + g.aggregationTimeSeconds + " sec"))
						+ "]\n");
				ng++;
				ttime += g.aggregationTimeSeconds;
			}
			ret.append("Estimated processing time: " + ttime + " seconds");

			return ret.toString();
		}

	}

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
		this.timeBase = (TimeInstant) repositoryStart;
		this.aggregationDirectory = new File(this.mainDirectory + File.separator + "aggregated");
		this.aggregationDirectory.mkdirs();
		recomputeProcessingTime();
	}

	/**
	 * Set the aggregation points for the datacube caching. If these aren't passed,
	 * no aggregation will happen.
	 * 
	 * @param resolutions
	 */
	public void setAggregationPoints(ITime.Resolution... resolutions) {
		this.aggregationPoints = new ArrayList<>();
		for (ITime.Resolution r : resolutions) {
			this.aggregationPoints.add(r);
		}

		/**
		 * Sort by descending coverage, to use during strategy assessment
		 */
		Collections.sort(this.aggregationPoints, new Comparator<ITime.Resolution>() {
			@Override
			public int compare(ITime.Resolution o1, ITime.Resolution o2) {
				return Long.compare(o2.getSpan(), o1.getSpan());
			}
		});
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
			if (startCaching && seconds > 0) {
				downloading = true;
				startChunkDownload(chunk, variable);
			}
		}

		ret.setRetryTimeSeconds(secs);
		ret.setAvailability(downloading ? Availability.DELAYED : Availability.NONE);

		return ret;
	}

	void recomputeProcessingTime() {

		/*
		 * estimate mean processing time per chunk based on contents of chunk properties
		 * and number of threads in executor.
		 */
		long secs = 0;
		int dirs = 0;

		for (File chunkDir : mainDirectory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		})) {

			File properties = new File(chunkDir + File.separator + "chunk.properties");
			if (properties.exists()) {
				Properties props = new Properties();
				try (InputStream input = new FileInputStream(properties)) {
					props.load(input);
					secs += Long.parseLong(props.getProperty(CHUNK_DOWNLOAD_TIME_MS))
							+ Long.parseLong(props.getProperty(CHUNK_PROCESSING_TIME_MS));
					dirs++;
				} catch (IOException e) {
					// just ignore, although this is likely a bad dir
				}
			}
		}

		if (dirs > 0) {
			this.estimatedChunkDownloadTimeSeconds = (int) (secs / (1000 * dirs));
		}

	}

	private void startChunkDownload(int chunk, String variable) {

		executor.execute(new Thread() {

			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long begin = start;
				boolean failure = false;
				File dir = new File(mainDirectory + File.separator + variable + "_" + chunk);
				dir.mkdirs();
				Properties properties = new Properties();
				if (downloadChunk(chunk, variable, dir)) {
					properties.setProperty(CHUNK_DOWNLOAD_TIME_MS, "" + (System.currentTimeMillis() - start));
					start = System.currentTimeMillis();
					if (processChunk(chunk, variable, dir)) {
						properties.setProperty(CHUNK_PROCESSING_TIME_MS, "" + (System.currentTimeMillis() - start));
						try (OutputStream out = new FileOutputStream(
								new File(dir + File.separator + "chunk.properties"))) {
							properties.store(out,
									"Chunk " + chunk + " of " + variable + " finished downloaded and processing at "
											+ DateTime.now(DateTimeZone.UTC) + ": total processing time = "
											+ new Period(System.currentTimeMillis() - begin));
						} catch (IOException e) {
							failure = true;
						}
					}
					if (failure) {
						Logging.INSTANCE.error("Transfer of chunk " + chunk + " for " + variable + " failed");
						FileUtils.deleteQuietly(dir);
					} else {
						recomputeProcessingTime();
					}
				}
			}

		});
	}

	private int isChunkAvailable(int chunk, String variable) {
		File pfile = new File(
				this.mainDirectory + File.separator + variable + "_" + chunk + File.separator + "chunk.properties");
		return pfile.exists() ? 0 : estimatedChunkDownloadTimeSeconds;

	}

	protected abstract boolean downloadChunk(int chunk, String variable, File destinationDirectory);

	protected abstract boolean processChunk(int chunk, String variable, File destinationDirectory);

	protected abstract String getDataFilename(String variable, int tick);

	protected abstract String getAggregatedFilename(String variable, int startTick, int endTick);

	protected abstract boolean createAggregatedData(String variable, int startTick, int endTick,
			File destinationDirectory);

	/**
	 * Return all the granules needed to cover the passed extent, with the estimated
	 * time considering both download, if needed, and aggregation.
	 * 
	 * @param time
	 * @return
	 */
	public Strategy getStrategy(String variable, ITime time) {

		Strategy ret = new Strategy();
		int skipping = 0;

		// chunks we have to download
		Set<Integer> chunks = new LinkedHashSet<>();

		for (Pair<Integer, Integer> cp : getTicks(time)) {

			ITimeInstant start = getTickStart(cp.getFirst());
			ITimeInstant end = getTickEnd(cp.getFirst());

			/*
			 * We technically don't need the chunks if we have an aggregation, but we don't
			 * need to support the case when only the aggregated data are available for now.
			 * Whoever deletes stuff from the repository deserves a NPE.
			 */
			chunks.add(cp.getSecond());

			// if there is a current aggregation and it covers the current tick,
			// continue
			if (skipping > 0) {
				skipping--;
				continue;
			}

			if (this.aggregationPoints == null) {
				
				Granule granule = new Granule();
				granule.multiplier = 1;
				granule.dataFile = new File(getChunkDirectory(variable, cp.getSecond()) + File.separator
						+ getDataFilename(variable, cp.getFirst()));
				granule.aggregationTimeSeconds = 0;

				ret.ticks.add(cp.getFirst());
				ret.granules.add(granule);

			} else {
				
				for (ITime.Resolution res : this.aggregationPoints) {

					if (start.plus(1, res).getMilliseconds() < time.getEnd().getMilliseconds()
							&& start.isAlignedWith(res) && getTickEnd(cp.getFirst()).isAlignedWith(res)) {

						/*
						 * use this resolution and move forward to next period
						 */
						Granule granule = new Granule();
						granule.multiplier = skipping = (int) start.getPeriods(start.plus(1, res), fileResolution);
						granule.dataFile = new File(aggregationDirectory + File.separator
								+ getAggregatedFilename(variable, cp.getFirst(), cp.getFirst() + skipping));
						granule.aggregationTimeSeconds = granule.dataFile.exists() ? 0
								: (int) (getEstimatedAggregationTime(res.getType()) * res.getMultiplier());

						ret.granules.add(granule);

					} else {

						Granule granule = new Granule();
						granule.multiplier = 1;
						granule.dataFile = new File(getChunkDirectory(variable, cp.getSecond()) + File.separator
								+ getDataFilename(variable, cp.getFirst()));
						granule.aggregationTimeSeconds = 0;

						ret.ticks.add(cp.getFirst());
						ret.granules.add(granule);

					}
				}
			}

			/*
			 * TODO scan the chunk set and add the total time to availability for all the
			 * missing ones
			 */
			for (Integer chunk : chunks) {
				ret.chunks.add(chunk);
				if (!getChunkDirectory(variable, chunk).exists()) {
					ret.timeToAvailabilitySeconds += this.estimatedChunkDownloadTimeSeconds;
				}
			}

		}

		return ret;
	}

	private File getChunkDirectory(String variable, Integer chunk) {
		return new File(mainDirectory + File.separator + variable + "_" + chunk);
	}

	protected int getEstimatedAggregationTime(Type type) {
		// TODO keep statistics
		return 1;
	}

	/**
	 * Get the chunk numbers needed to cover the passed time. The numbers represent
	 * blocks of N seconds, where N is the resolution of the chunk
	 * (chunkResolution.getSpan()).
	 * 
	 * @param time
	 * @return
	 */
	List<Integer> getChunks(ITime time) {
		List<Integer> ret = new ArrayList<>();
		long startchunk = time.getStart().getPeriods(this.timeBase, chunkResolution);
		long endchunk = time.getEnd().getPeriods(this.timeBase, chunkResolution);
		for (long n = startchunk; n < endchunk; n++) {
			ret.add((int) n);
		}
		return ret;
	}

	/**
	 * Get all the file ticks and the corresponding chunks necessary to cover one
	 * observation along a period. No aggregation strategy is considered here.
	 * 
	 * @param time
	 * @return
	 */
	List<Pair<Integer, Integer>> getTicks(ITime time) {
		List<Pair<Integer, Integer>> ret = new ArrayList<>();
		for (int chunk : getChunks(time)) {
			for (int tick : getChunkTicks(chunk)) {
				if (time.getStart().getMilliseconds() <= getTickStart(tick).getMilliseconds()
						&& time.getEnd().getMilliseconds() > getTickEnd(tick).getMilliseconds())
					ret.add(new Pair<>(tick, chunk));
			}
		}
		return ret;
	}

	public ITimeInstant getChunkStart(int chunk) {
		return timeBase.plus(chunk, chunkResolution);
	}

	public ITimeInstant getChunkEnd(int chunk) {
		return timeBase.plus(chunk + 1, chunkResolution);
	}

	public ITimeInstant getTickStart(int tick) {
		return timeBase.plus(tick, fileResolution);
	}

	public ITimeInstant getTickEnd(int tick) {
		return timeBase.plus(tick + 1, fileResolution);
	}

	public List<Integer> getChunkTicks(int chunk) {
		List<Integer> ret = new ArrayList<>();
		ITimeInstant start = getChunkStart(chunk);
		ITimeInstant end = getChunkEnd(chunk);
		long tick = start.getPeriods(this.timeBase, fileResolution);
		while (start.isBefore(end)) {
			ret.add((int) tick);
			start = start.plus(1, fileResolution);
			tick++;
		}
		return ret;
	}

	public static void main(String[] dio) {

		ChunkedDatacubeRepository dr = new ChunkedDatacubeRepository(Time.resolution(1, Type.DAY),
				Time.resolution(3, Type.MONTH), TimeInstant.create(1970, 1, 1),
				Configuration.INSTANCE.getDataPath("testrep")) {

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
				ITimeInstant start = getTickStart(tick);
				return variable + "_" + tick;
			}

			@Override
			protected String getAggregatedFilename(String variable, int startTick, int endTick) {
				return variable + "__" + startTick + "_" + endTick + ".nc";
			}

			@Override
			protected boolean createAggregatedData(String variable, int startTick, int endTick,
					File destinationDirectory) {
				// TODO Auto-generated method stub
				return false;
			}

		};

		dr.setAggregationPoints(Time.resolution(1, Type.WEEK), Time.resolution(1, Type.MONTH));
		
		for (int chunk : dr.getChunks(
				Time.create(TimeInstant.create(2010), TimeInstant.create(2012), Time.resolution(1, Type.YEAR)))) {
			System.out.println("Chunk " + chunk + ": " + dr.getChunkStart(chunk) + " to " + dr.getChunkEnd(chunk));
			for (int tick : dr.getChunkTicks(chunk)) {
				System.out.println("  Tick " + tick + ": " + dr.getTickStart(tick) + " to " + dr.getTickEnd(tick));
			}
		}

		Strategy strategy = dr.getStrategy("precipitation",
				Time.create(TimeInstant.create(2010), TimeInstant.create(2012), Time.resolution(1, Type.YEAR)));

		System.out.println(strategy.dump());
	}

}

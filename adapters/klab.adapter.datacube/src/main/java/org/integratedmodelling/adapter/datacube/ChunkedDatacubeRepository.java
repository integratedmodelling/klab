package org.integratedmodelling.adapter.datacube;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Level;

import javax.annotation.Nullable;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.apache.commons.io.FileUtils;
import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.adapter.datacube.api.IDatacube;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.storage.BasicFileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

/**
 * A repository where data are organized in chunk directories containing a set of files for a
 * variable, with uniform temporal coverage according to a predefined "tick". The chunk is a unit of
 * download, the file a unit of ingestion. It's able to collect all the data for a period with the
 * necessary aggregation, using caching based on a specified set of temporal intervals to minimize
 * access.
 * <p>
 * The repository can be used in both a static and dynamic configuration, producing either processes
 * or qualities, so the functions that return types and resource data take a "dynamic" flag to
 * distinguish.
 * <p>
 * ACHTUNG: assumes the various resolutions are chosen to match perfectly. Fractional resolutions
 * won't work correctly. Uses real time units and handles months and years appropriately.
 * <p>
 * The repository will malfunction unless the base for the chunk numbering (time of first possible
 * observation in fileResolution units) is not set.
 * <p>
 * The abstract Datacube class does not use this, but it can be used in derived classes.
 * 
 * @author Ferd
 *
 */
public abstract class ChunkedDatacubeRepository implements IDatacube {

    public static final String CHUNK_DOWNLOAD_TIME_MS = "time.download.ms";
    public static final String CHUNK_PROCESSING_TIME_MS = "time.processing.ms";
    public static final String DATACUBE_DOWNLOAD_THREADS_PROPERTY = "datacube.download.threads";

    private Resolution fileResolution;
    private Resolution chunkResolution;
    private File mainDirectory;
    private File aggregationDirectory;
    private List<Resolution> aggregationPoints;
    private TimeInstant timeBase;
    protected Executor executor = null;
    private boolean online;
    private String statusMessage;
    protected Geoserver geoserver;
    protected Double noDataValue;
    protected Map<String, Function<IScale, Strategy>> specialVariables = Collections.synchronizedMap(new HashMap<>());

    private Map<String, Strategy> beingProcessed = Collections.synchronizedMap(new HashMap<>());

    /**
     * Register a specially handled variable that will be served through any mechanism outside the
     * datacube's logics. The handler must return one or more granules describing all the temporal
     * extent requested, computed as needed and cached as appropriate.
     * 
     * @param varname
     * @param retriever
     */
    protected void registerSpecialVariable(String varname, Function<IScale, Strategy> retriever) {
        specialVariables.put(varname, retriever);
    }

    /*
     * estimated by averaging actual downloads as long as there is at least one chunk.
     */
    int estimatedChunkDownloadTimeSeconds = 80;

    public class Granule {
        /**
         * The datafile to use for data retrieval
         */
        public File dataFile;
        /**
         * How many of the data resolution units are represented by this (possibly aggregated) file.
         * Files that result from aggregation have multipliers > 1.
         */
        public int multiplier;
        /**
         * If > 0, the file does not exist and the value is the estimated number of seconds required
         * to produce it or download the chunk it's in.
         */
        public int aggregationTimeSeconds;

        /**
         * Layer for the granule in Geoserver WCS, including namespace
         */
        public String layerName;

        /**
         * Ticks for aggregation
         */
        public int startTick, endTick;

        @Override
        public String toString() {
            return "Granule [dataFile=" + dataFile + ", multiplier=" + multiplier + ", layerName=" + layerName + "]";
        }

    }

    /**
     * All the information pertaining to an observation strategy, including all data files needed
     * (existing or not) along with an estimated time to download completion (if 0, all data are
     * available).
     * 
     * TODO extract the interface and put it in IDatacube along with the generator.
     * 
     * @author Ferd
     *
     */
    public class Strategy implements ObservationStrategy {

        public List<Granule> granules = new ArrayList<>();
        public List<Integer> chunks = new ArrayList<>();
        public List<Integer> ticks = new ArrayList<>();
        private int timeToAvailabilitySeconds;

        @Override
        public int getTimeToAvailabilitySeconds() {
            return timeToAvailabilitySeconds;
        }

        private String variable;
        // this is set to true if the strategy shouldn't be synchronized with
        // copernicus.
        private boolean finished;

        public Strategy(String variable) {
            this.variable = variable;
        }

        public String dump() {
            StringBuffer ret = new StringBuffer(1024);
            ret.append("Download strategy\n");
            int ttime = timeToAvailabilitySeconds;
            int ng = 0;
            for (Granule g : granules) {
                ret.append("   " + ng + ": " + g.layerName + " ["
                        + (g.multiplier == 1
                                ? "data"
                                : ("aggregating " + g.multiplier + " est. " + g.aggregationTimeSeconds + " sec"))
                        + "]\n");
                ng++;
                ttime += g.aggregationTimeSeconds;
            }
            ret.append("Estimated processing time: " + ttime + " seconds");

            return ret.toString();
        }

        @Override
        public String toString() {
            return dump();
        }

        /**
         * Start any necessary processing, recording the ongoing operations so that successive calls
         * don't make a mess. If availability is delayed, exits after enqueuing tasks and before
         * they complete, reporting an estimated time to completion for retrying.
         */
        @Override
        public AvailabilityReference buildCache() {

            AvailabilityReference ret = new AvailabilityReference();
            ret.setRetryTimeSeconds(this.timeToAvailabilitySeconds);

            if (specialVariables.containsKey(this.variable)) {
                ret.setAvailability(Availability.COMPLETE);
                return ret;
            }

            List<Integer> toDownload = new ArrayList<>();
            List<Integer> candidates = new ArrayList<>();

            for (int chunk : chunks) {
                int delay = isChunkAvailable(chunk, variable);
                if (delay < 0) {
                    ret.setAvailability(Availability.NONE);
                    break;
                } else if (delay > 0) {
                    candidates.add(chunk);
                    ret.setAvailability(Availability.DELAYED);
                }
            }

            if (candidates.isEmpty()) {
                ret.setAvailability(Availability.COMPLETE);
            }

            for (int c : candidates) {
                if (!beingProcessed.containsKey(variable + "#" + c)) {
                    beingProcessed.put(variable + "#" + c, this);
                    toDownload.add(c);
                }
            }

            CountDownLatch latch = new CountDownLatch(toDownload.size());

            for (int c : toDownload) {
                startChunkDownload(c, variable, latch);
            }

            if (toDownload.size() > 0) {
                executor.execute(new Thread(){
                    @Override
                    public void run() {

                        try {
                            // wait for all chunks to download
                            latch.await();
                        } catch (InterruptedException e) {
                            Logging.INSTANCE.error("sync error in chunk processing: " + e.getMessage());
                        }

                        /*
                         * compute all useful aggregations within the chunks
                         */
                        List<Integer> allticks = new ArrayList<>();
                        for (int chunk : chunks) {
                            for (int tick : getChunkTicks(chunk)) {
                                allticks.add(tick);
                            }
                        }

                        // add 1 to aggregate all including the very last period
                        allticks.add(allticks.get(allticks.size() - 1) + 1);
                        Map<Resolution, Integer> checkpoints = new HashMap<>();
                        for (Resolution aggregationPoint : aggregationPoints) {

                            Logging.INSTANCE.info("Precomputing " + aggregationPoint.getType().getPredicate()
                                    + " aggregations for " + variable);

                            for (int tick : allticks) {

                                ITimeInstant start = getTickStart(tick);
                                if (start.isAlignedWith(aggregationPoint)) {
                                    if (checkpoints.containsKey(aggregationPoint)) {

                                        File aggregatedFile = new File(aggregationDirectory + File.separator
                                                + getAggregatedFilename(variable, checkpoints.get(aggregationPoint), tick - 1));

                                        if (aggregatedFile.exists()) {
                                            continue;
                                        }

                                        if (!createAggregatedLayer(variable, checkpoints.get(aggregationPoint), tick - 1,
                                                aggregationPoint, aggregatedFile)) {

                                            Logging.INSTANCE
                                                    .warn("aggregation between " + getTickStart(checkpoints.get(aggregationPoint))
                                                            + " and " + getTickEnd(tick - 1) + " returned a failure code");
                                        } else {
                                            Logging.INSTANCE.info("Created " + aggregationPoint.getType().getPredicate()
                                                    + " aggregation " + aggregatedFile + " ["
                                                    + (tick - checkpoints.get(aggregationPoint)) + " ticks]");
                                        }
                                    }
                                    checkpoints.put(aggregationPoint, tick);
                                }
                            }
                        }
                    }
                });
            }

            return ret;
        }

        @Override
        public boolean execute(Urn urn, IGeometry geometry, Builder builder, IContextualizationScope scope) {

            // FIXME remove
            Logging.INSTANCE.info("entering strategy.execute()");

            if (!(scope.getScale().getSpace() instanceof Space) || ((Space) scope.getScale().getSpace()).getGrid() == null) {
                throw new KlabIllegalStateException("Copernicus adapter only support grid geometries for now");
            }

            boolean first = true;
            IGrid grid = ((Space) scope.getScale().getSpace()).getGrid();
            BasicFileMappedStorage<Double> data = null;

            String stateName = getStateName(urn, variable);

            /*
             * no state for the variable: return
             */
            if (stateName == null) {
                builder.addNotification(
                        new Notification("no state for variable " + stateName + " return w/o result", Level.SEVERE.getName()));
                return false;
            }

            IUnit originalUnit = getOriginalUnit(variable);
            // process adapter makes its own states.
            Builder stateBuilder = builder.startState(stateName, originalUnit == null ? null : originalUnit.toString(), scope);
            double wsum = 0.0;
            Aggregation aggregation = getAggregation(variable);

            for (Granule g : granules) {

                // FIXME remove
                Logging.INSTANCE.info("adding granule " + g);

                if (specialVariables.containsKey(variable) && !g.dataFile.exists()) {
                    continue;
                }

                if (!g.dataFile.exists()) {
                    if (g.multiplier == 1) {
                        scope.getMonitor().error(
                                "repository error: " + getName() + ": missing datafile " + MiscUtilities.getFileName(g.dataFile));
                    } else {
                        scope.getMonitor().info("repository " + getName() + ": creating missing aggregated datafile "
                                + MiscUtilities.getFileName(g.dataFile));
                        createAggregatedLayer(variable, g.startTick, g.endTick, null, g.dataFile);
                    }
                }

                GridCoverage2D coverage = getCoverage(g.layerName, scope.getScale().getSpace());

                if (coverage == null) {
                    scope.getMonitor().error("coverage retrieval for " + g.layerName + " failed: geoserver may be offline");
                    return false;
                }

                if (granules.size() == 1) {

                    geoserver.encode(coverage, scope.getScale(), stateBuilder, 0, noDataValue, null);
                    stateBuilder.finishState();
                    return true;

                } else {

                    wsum += g.multiplier;

                    /*
                     * create temp storage if needed
                     */
                    if (data == null) {
                        data = new BasicFileMappedStorage<>(Double.class, grid.getXCells(), grid.getYCells());
                    }

                    RandomIter iterator = RandomIterFactory.create(coverage.getRenderedImage(), null);
                    for (long ofs = 0; ofs < grid.getCellCount(); ofs++) {

                        long[] xy = Grid.getXYCoordinates(ofs, grid.getXCells(), grid.getYCells());
                        double value = iterator.getSampleDouble((int) xy[0], (int) xy[1], 0);
                        if (first) {
                            double d = value;
                            if (!NumberUtils.equal(d, noDataValue)) {
                                d = value * (aggregation == Aggregation.MEAN ? g.multiplier : 1.0);
                            }
                            data.set(d, xy);

                        } else {

                            Double d = data.get(xy);

                            if (!NumberUtils.equal(d, noDataValue)) {
                                d += value * (aggregation == Aggregation.MEAN ? g.multiplier : 1.0);

                            } else {
                                d = value;
                            }

                            data.set(d, xy);
                        }
                    }
                }
                first = false;
            }

            if (data != null) {

                for (long ofs = 0; ofs < grid.getCellCount(); ofs++) {
                    long[] xy = Grid.getXYCoordinates(ofs, grid.getXCells(), grid.getYCells());
                    double value = data.get(xy);

                    if (NumberUtils.equal(value, noDataValue)) {
                        value = Double.NaN;
                    } else if (aggregation == Aggregation.MEAN && wsum > 1 && Observations.INSTANCE.isData(value)) {
                        value /= wsum;
                    }

                    stateBuilder.add(value);
                }

                stateBuilder.finishState();
                
                data.close();

                return true;
            }

            return false;
        }

        public void setTimeToAvailability(int tavail) {
            this.timeToAvailabilitySeconds = tavail;
        }

        public void setVariable(String codename) {
            this.variable = codename;
        }

        public void setFinished(boolean b) {
            this.finished = b;
        }

    }

    protected void setOnline(boolean b, String string) {
        this.online = b;
        this.statusMessage = string;
    }

    protected GridCoverage2D getCoverage(String layerName, ISpace space) {
        return this.geoserver.getWCSCoverage(space, getName(), layerName);
    }

    protected String getOriginalFile(String variable, int tick) {
        return getOriginalDataFilename(variable, tick, getChunkDirectory(variable, getChunk(tick)));
    }

    /**
     * 
     * @param fileResolution the period covered by each file in a chunk
     * @param chunkResolution the period covered by each chunk
     * @param repositoryStart date of first observation in remote repository
     * @param mainDirectory the directory where the chunks are located
     * @param noDataValue value for nodata in remote observations
     */
    public ChunkedDatacubeRepository(ITime.Resolution fileResolution, ITime.Resolution chunkResolution,
            ITimeInstant repositoryStart, File mainDirectory, double noDataValue) {

        this.fileResolution = fileResolution;
        this.chunkResolution = chunkResolution;
        this.mainDirectory = mainDirectory;
        this.timeBase = (TimeInstant) repositoryStart;
        this.aggregationDirectory = new File(this.mainDirectory + File.separator + "aggregated");
        this.aggregationDirectory.mkdirs();
        this.noDataValue = noDataValue;

        recomputeProcessingTime();

        int maxConcurrentThreads = Integer.parseInt(Configuration.INSTANCE.getProperty(DATACUBE_DOWNLOAD_THREADS_PROPERTY, "1"));
        this.executor = Executors.newFixedThreadPool(maxConcurrentThreads);

        this.geoserver = initializeGeoserver();
        if (!this.geoserver.isOnline()) {
            setOnline(false, "Copernicus CDS datacube: no Geoserver is available");
        } else {
            // TODO should also check the Copernicus service but that may be unnecessary if
            // we
            // have the data.
            setOnline(true, "Geoserver is connected and responding");
        }

    }

    /**
     * Set the aggregation points for the datacube caching. If these aren't passed, no aggregation
     * will happen.
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
        Collections.sort(this.aggregationPoints, new Comparator<ITime.Resolution>(){
            @Override
            public int compare(ITime.Resolution o1, ITime.Resolution o2) {
                return Long.compare(o2.getSpan(), o1.getSpan());
            }
        });
    }

    void recomputeProcessingTime() {

        /*
         * estimate mean processing time per chunk based on contents of chunk properties and number
         * of threads in executor.
         */
        long secs = 0;
        int dirs = 0;

        for (File chunkDir : mainDirectory.listFiles(new FileFilter(){
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

    private void startChunkDownload(int chunk, String variable, CountDownLatch latch) {

        executor.execute(new Thread(){

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
                        try (OutputStream out = new FileOutputStream(new File(dir + File.separator + "chunk.properties"))) {
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

                latch.countDown();
                beingProcessed.remove(variable + "#" + chunk);

            }

        });
    }

    private int isChunkAvailable(int chunk, String variable) {
        if (this.beingProcessed.containsKey(variable + "#" + chunk)) {
            // let's not decrement this for now
            return this.beingProcessed.get(variable + "#" + chunk).timeToAvailabilitySeconds;
        }
        File pfile = new File(this.mainDirectory + File.separator + variable + "_" + chunk + File.separator + "chunk.properties");
        return pfile.exists() ? 0 : (checkRemoteAvailability(chunk, variable) ? estimatedChunkDownloadTimeSeconds : -1);

    }

    /**
     * Get the unit for the passed variable, or null.
     * 
     * @param variable
     * @return
     */
    protected abstract IUnit getOriginalUnit(String variable);

    /**
     * Redefine to customize the way states must be named w.r.t. the variables mentioned in the URN.
     * Must return the actual name the state correspondent to the variable is known by in the scope.
     * For remote use, it's mandatory to use the same name as in the URN, potentially with dots
     * substituted by underscores.
     *
     * @param variable
     * @param scope
     * @return
     */
    protected String getStateName(Urn urn, String variable) {
        return variable;
    }

    /**
     * Check if passed chunk is available remotely for download.
     * 
     * @param chunk
     * @param variable
     * @return
     */
    protected abstract boolean checkRemoteAvailability(int chunk, String variable);

    /**
     * Download the passed chunk into the passed directory so that each tick in the chunk
     * corresponds to the filename returned by {@link #getDataFilename(String, int)}. If something
     * goes wrong, return false after deleting any turds.
     * 
     * @param chunk
     * @param variable
     * @param destinationDirectory
     * @return
     */
    protected abstract boolean downloadChunk(int chunk, String variable, File destinationDirectory);

    /**
     * Called after {@link #downloadChunk(int, String, File)} has returned true. Do anything you
     * need to get the files ingested and validated for use.
     * 
     * @param chunk
     * @param variable
     * @param destinationDirectory
     * @return
     */
    protected abstract boolean processChunk(int chunk, String variable, File destinationDirectory);

    /**
     * Return the file name corresponding to the passed tick. This is only called after the chunk
     * has been successfully downloaded, so the containing directory is valid in case the filename
     * can only be computed after the fact.
     * 
     * @param variable
     * @param tick
     * @return
     */
    protected abstract String getOriginalDataFilename(String variable, int tick, File containingDirectory);

    /**
     * Return the filename corresponding to the aggregation of the data in subsequent file ticks
     * between start and end, both inclusive. Aggregation should be done according to data semantics
     * as either sum or average.
     * 
     * @param variable
     * @param startTick
     * @param endTick
     * @return
     */
    protected abstract String getAggregatedFilename(String variable, int startTick, int endTick);

    /**
     * Actually perform the aggregation between the ticks indicated and produce a file (could be a
     * placeholder if encoding happens in other ways) with the passed path, which is a fully
     * specified path created using the result of {@link #getAggregatedFilename(String, int, int)}.
     * After this has returned true, a call for the result of
     * {@link #getAggregatedLayer(String, int, int)} to the linked geoserver must succeed.
     * 
     * @param variable
     * @param startTick
     * @param endTick
     * @param destinationDirectory
     * @return
     */
    protected abstract boolean createAggregatedLayer(String variable, int startTick, int endTick,
            @Nullable ITime.Resolution resolution, File destinationFile);

    @Override
    public Strategy getStrategy(String variable, IGeometry geometry) {

        IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

        if (specialVariables.containsKey(variable)) {
            return specialVariables.get(variable).apply(scale);
        }

        ITime time = scale.getTime();
        Strategy ret = new Strategy(variable);

        int skipping = 0;

        // chunks we have to download
        Set<Integer> chunks = new LinkedHashSet<>();

        for (Pair<Integer, Integer> cp : getTicks(time)) {

            ITimeInstant start = getTickStart(cp.getFirst());

            /*
             * We technically don't need the chunks if we have an aggregation, but we don't need to
             * support the case when only the aggregated data are available for now. Whoever deletes
             * stuff from the repository deserves a NPE.
             */
            chunks.add(cp.getSecond());

            // if there is a current aggregation and it covers the current tick,
            // continue
            if (skipping > 0) {
                skipping--;
                continue;
            }

            boolean aggregated = false;
            if (this.aggregationPoints != null) {
                for (ITime.Resolution res : this.aggregationPoints) {

                    if (start.plus(1, res).getMilliseconds() <= time.getEnd().getMilliseconds() && start.isAlignedWith(res)) {

                        /*
                         * use this resolution and move forward to next period
                         */
                        Granule granule = new Granule();
                        granule.multiplier = (int) start.getPeriods(start.plus(1, res), fileResolution);
                        skipping = granule.multiplier - 1;
                        granule.dataFile = new File(aggregationDirectory + File.separator
                                + getAggregatedFilename(variable, cp.getFirst(), cp.getFirst() + skipping));
                        granule.aggregationTimeSeconds = granule.dataFile.exists()
                                ? 0
                                : (int) (getEstimatedAggregationTime(res.getType()) * granule.multiplier);
                        granule.startTick = cp.getFirst();
                        granule.endTick = cp.getFirst() + skipping;
                        granule.layerName = getAggregatedLayer(variable, cp.getFirst(), cp.getFirst() + skipping);
                        ret.granules.add(granule);

                        aggregated = true;
                        break;
                    }
                }

                if (!aggregated) {

                    Granule granule = new Granule();
                    granule.multiplier = 1;
                    File directory = getChunkDirectory(variable, cp.getSecond());
                    directory.mkdirs();
                    granule.dataFile = new File(
                            directory + File.separator + getOriginalDataFilename(variable, cp.getFirst(), directory));
                    granule.aggregationTimeSeconds = 0;
                    granule.layerName = getDataLayer(variable, cp.getFirst());

                    ret.ticks.add(cp.getFirst());
                    ret.granules.add(granule);
                }
            }
        }

        /*
         * TODO scan the chunk set and add the total time to availability for all the missing ones
         */
        for (Integer chunk : chunks) {
            ret.chunks.add(chunk);
            if (!new File(getChunkDirectory(variable, chunk) + File.separator + "chunk.properties").exists()) {
                ret.timeToAvailabilitySeconds += this.estimatedChunkDownloadTimeSeconds;
            }
        }

        return ret;
    }

    private File getChunkDirectory(String variable, Integer chunk) {
        return new File(mainDirectory + File.separator + variable + "_" + chunk);
    }

    protected File getDataFolder() {
        return mainDirectory;
    }

    private int getChunk(int tick) {
        ITimeInstant tickTime = getTickStart(tick);
        return (int) tickTime.getPeriods(this.timeBase, chunkResolution);
    }

    protected int getEstimatedAggregationTime(Type type) {
        // TODO keep statistics
        return 1;
    }

    /**
     * Get the chunk numbers needed to cover the passed time. The numbers represent blocks of N
     * seconds, where N is the resolution of the chunk (chunkResolution.getSpan()).
     * 
     * @param time
     * @return
     */
    List<Integer> getChunks(ITime time) {
        List<Integer> ret = new ArrayList<>();
        long startchunk = time.getStart().getPeriods(this.timeBase, chunkResolution);
        long endchunk = time.getEnd().getPeriods(this.timeBase, chunkResolution);
        for (long n = startchunk; n <= endchunk; n++) {
            ret.add((int) n);
        }
        return ret;
    }

    /**
     * Get all the file ticks and the corresponding chunks necessary to cover one observation along
     * a period. No aggregation strategy is considered here.
     * 
     * @param time
     * @return Pairs with tick and chunk
     */
    protected List<Pair<Integer, Integer>> getTicks(ITime time) {
        List<Pair<Integer, Integer>> ret = new ArrayList<>();
        for (int chunk : getChunks(time)) {
            for (int tick : getChunkTicks(chunk)) {
                if (time.getStart().getMilliseconds() <= getTickStart(tick).getMilliseconds()
                        && time.getEnd().getMilliseconds() >= getTickEnd(tick).getMilliseconds())
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

    protected File getDataFile(String variable, int tick) {
        File dir = getChunkDirectory(variable, getChunk(tick));
        return new File(dir + File.separator + getOriginalDataFilename(variable, tick, dir));
    }

    public List<Integer> getChunkTicks(int chunk) {
        List<Integer> ret = new ArrayList<>();
        ITimeInstant start = getChunkStart(chunk);
        ITimeInstant end = getChunkEnd(chunk);
        long tick = start.getPeriods(this.timeBase, fileResolution);
        while(start.isBefore(end)) {
            ret.add((int) tick);
            start = start.plus(1, fileResolution);
            tick++;
        }
        return ret;
    }

    public boolean isOnline() {
        return this.online;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public abstract String getName();

    protected abstract Geoserver initializeGeoserver();

    protected abstract IArtifact.Type getResourceType(Urn urn, boolean dynamic);

    protected abstract IGeometry getResourceGeometry(Urn urn);

    protected abstract String getAggregatedLayer(String variable, int startTick, int endTick);

    protected abstract String getDataLayer(String variable, int tick);

    public abstract IResource getResource(String urn, boolean dynamic);

    /**
     * Return a stable variable name for a given URN
     * 
     * @param urn
     * @return
     */
    protected abstract Collection<String> getVariableNames(Urn urn);

}

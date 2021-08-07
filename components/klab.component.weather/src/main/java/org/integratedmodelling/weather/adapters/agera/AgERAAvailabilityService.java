package org.integratedmodelling.weather.adapters.agera;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.adapter.datacube.Datacube.AvailabilityService;
import org.integratedmodelling.adapter.datacube.Datacube.IngestionService;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource.Availability;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.rest.ResourceReference.AvailabilityReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube.VariableConfiguration;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class AgERAAvailabilityService implements AvailabilityService {

    AgERADatacube cube = null;

    /*
     * Checking availability means returning NONE if no possibility of ever getting the data exists;
     * starting the processing and returning DELAYED if data require retrieval and/or processing
     * before use; or returning AVAILABLE if available right away in normal response times.
     * 
     * The process is composed of 5 phases:
     * 
     * 1. establish the needed chunks. A chunk is a 3-month dataset downloaded as a single package
     * (it's below the 100 "item" threshold). Chunks are named with the name of the variable, the
     * year and the trimester (0-3) separated by slashes, e.g. temperature.max/2010/0.
     * 
     * 2. check if the chunks are available and if 1+ are not, return Availability.DELAYED and start
     * the download. Download may be ongoing or have been asked before and failed.
     * 
     * 3. Chunks are available: establish the needed processing to obtain the base data. If
     * processing is required, check if ongoing already. If ongoing, return Availability.DELAYED. If
     * not, estimate the job size and start the processing. If job size > 20s, return
     * Availability.DELAYED.
     * 
     * 4. Get processed data into primary data vessel. If caching is required, that will be the
     * responsibility of the caching service, invoked after responding for uncached chunks.
     * 
     * 5. Establish needed postprocessing. Proceed as in 3.
     * 
     */

    @SuppressWarnings("unchecked")
    @Override
    public AvailabilityReference checkAvailability(IGeometry geometry, Object vars, IngestionService ingestion) {

        AvailabilityReference ret = new AvailabilityReference();

        boolean downloading = false;
        int secs = 0;
        IScale scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);

        for (VariableConfiguration var : (Collection<VariableConfiguration>) vars) {

            if (var == null || var.variable == null) {
                ret.setAvailability(Availability.NONE);
                ret.setMessage("Resource URN references unidentified variables");
                
            } else {

                for (String chunk : getChunkIds(scale.getTime(), var)) {
                    int seconds = isAvailable(chunk);
                    // TODO divide this by number of threads in ingestor and add something
                    secs += seconds;
                    if (seconds > 0) {
                        downloading = true;
                        ingestion.queueDownload(chunk);
                    }
                }
            }
            
            ret.setRetryTimeSeconds(secs);
            ret.setAvailability(downloading ? Availability.DELAYED : Availability.NONE);
        }

        return ret;
    }

    /**
     * Return the highest useful chunk ID for the variable, assuming that the 3-month chunk before
     * the current time will be available (data are updated every week).
     */
    public Pair<Integer, Integer> getLatestAvailableChunk() {
        DateTime now = DateTime.now(DateTimeZone.UTC);
        int monthId = ((now.getMonthOfYear() - 1) / 4) + 1;
        int year = now.getYear();
        if (monthId == 0) {
            monthId = 3;
            year--;
        } else {
            monthId--;
        }
        return new Pair<>(year, monthId);
    }

    /**
     * Return the integer fields for the chunk that will cover the passed date.
     * 
     * @param variable
     * @param date
     * @return
     */
    public Pair<Integer, Integer> getChunkData(ITimeInstant date) {
        int monthId = ((((TimeInstant) date).asDate().getMonthOfYear() - 1) / 4) + 1;
        int year = date.getYear();
        return new Pair<>(year, monthId);

    }

    /**
     * Return the list of chunk IDs needed to address the query in the passed geometry.
     * 
     * Get all the chunk IDs necessary to cover the passed variable in the given time. If the time
     * isn't fully covered, the last chunk will be the special form "INCOMPLETE/year/month"
     * indicating the year and month when the series must stop. A completely non-covered extent will
     * contain only the latter ID.
     * 
     * 
     * @param time
     * @param variable
     * @return
     */
    public Collection<String> getChunkIds(ITime time, VariableConfiguration variable) {

        List<String> ret = new ArrayList<>();

        Pair<Integer, Integer> chunkStart = getChunkData(time.getStart());
        Pair<Integer, Integer> chunkEnd = getChunkData(time.getEnd());
        Pair<Integer, Integer> stopHere = getLatestAvailableChunk();

        boolean tooMuch = false;
        for (int year = chunkStart.getFirst(); !tooMuch && year <= chunkEnd.getFirst(); year++) {
            boolean last = chunkEnd.getFirst() == year;
            if (year > stopHere.getFirst()) {
                ret.add("INCOMPLETE." + year + ".0");
                break;
            }
            for (int month = 0; month < 4; month++) {
                if (stopHere.getFirst() == year && month > stopHere.getSecond()) {
                    ret.add("INCOMPLETE." + year + "." + month);
                    tooMuch = true;
                    break;
                }
                if (last && month > chunkEnd.getSecond()) {
                    break;
                }
                ret.add(variable + "." + year + "." + month);
            }
        }

        return ret;
    }

    public int isAvailable(String chunk) {
        File chunkDir = new File(Configuration.INSTANCE.getDataPath(AgERADatacube.ID) + File.separator + chunk);
        if (chunkDir.isDirectory()) {
            // check that processing has completed
            File procFile = new File(chunkDir + File.separator + "chunk.properties");
            // TODO may want to read the file and check its contents. Should contain the
            // time to download for estimation.
            if (procFile.exists()) {
                return 0;
            }
        }
        return ((AgERAIngestionService) ((AgERADatacube) cube).getIngestionService()).getMeanWaitTimePerChunk();
    }

    public void setDatacube(AgERADatacube agERADatacube) {
        this.cube = agERADatacube;
    }
}

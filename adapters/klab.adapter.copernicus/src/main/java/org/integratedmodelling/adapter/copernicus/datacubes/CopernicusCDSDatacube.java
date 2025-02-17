package org.integratedmodelling.adapter.copernicus.datacubes;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution.Type;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.klab.utils.ZipUtils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import ucar.nc2.dt.grid.GridDataset;

/**
 * Caching base Copernicus datacube for any CDS dataset
 * 
 * @author Ferd
 *
 */
public abstract class CopernicusCDSDatacube extends ChunkedDatacubeRepository {

    private String dataset;
    private String apiKey;
    private String apiUrl;

    public static final String CDS_API_KEY_PROPERTY = "klab.copernicus.cds.apikey";
    public static final String CDS_API_URL = "klab.copernicus.cds.url";
    public static final String CDS_API_VERSION = "1_1";
    public static final String CDS_API_FORMAT = "zip";
    public static final String CDS_API_KEY_HEADER = "PRIVATE-TOKEN";

    private int TIMEOUT_SECONDS = 60;
    private static Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");

    /*
     * tabulate and shut up
     */
    String[][] monts = {{"01", "02", "03"}, {"04", "05", "06"}, {"07", "08", "09"}, {"10", "11", "12"}};

    /**
     * These don't have to be right for the months
     */
    String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public CopernicusCDSDatacube(String dataset, ITimeInstant dataStart, double noDataValue) {

        super(Time.resolution(1, Type.DAY), Time.resolution(3, Type.MONTH), dataStart,
                Configuration.INSTANCE.getDataPath("copernicus/" + dataset), noDataValue);
        this.dataset = dataset;

        this.apiUrl = Configuration.INSTANCE.getProperties().getProperty(CDS_API_URL);
        this.apiKey = Configuration.INSTANCE.getProperties().getProperty(CDS_API_KEY_PROPERTY);
        if (this.apiKey == null || this.apiUrl == null) {
            setOnline(false, "Copernicus CDS datacube: no CDS url or api key provided in configuration");
        } else {
            setOnline(true, null);
        }

    }

    protected Geoserver initializeGeoserver() {
        return Geoserver.create();
    }

    /**
     * Put the necessary informations in the map that will be sent as json in the
     * CDS API request based on the variable string in the URN. At a minimum it
     * should contain the "variable" field so that the CDS API for this dataset will
     * recognize it, but it could also encode statistical variants, time points and
     * other selectors.
     * <p>
     * This is called after setting year, months and day fields for the passed
     * chunk. For now these are fixed for 3-month chunks of daily data, changing
     * them will need more configuration.
     * 
     * @param variable
     * @param payload
     */
    protected abstract void configureRequest(String variable, Map<String, Object> payload);

    @Override
    protected boolean downloadChunk(int chunk, String variable, File destinationDirectory) {

        Map<String, Object> bodyWrapper = new HashMap<>();
        Map<String, Object> body = new HashMap<>();

        boolean ret = false;
        ITimeInstant date = getChunkStart(chunk);

        /*
         * check if it was downloaded and not processed, or added by hand. If we have
         * the expected number of files, all with the expected tick number, we have them
         * and we can return true.
         */
        boolean partial = false;
        boolean present = true;
        for(int tick : getChunkTicks(chunk)) {
            File tickFile = new File(
                    destinationDirectory + File.separator + getOriginalDataFilename(variable, tick, destinationDirectory));
            if (tickFile.exists()) {
                partial = true;
            } else {
                present = false;
            }
        }

        if (present) {
            return true;
        }

        if (partial) {
            FileUtils.deleteQuietly(destinationDirectory);
            destinationDirectory.mkdirs();
        }

        body.put("year", "" + date.getYear());
        body.put("month", this.monts[(date.getMonth() - 1) / 3]);
        body.put("day", this.days);
        body.put("version", CDS_API_VERSION);

        configureRequest(variable, body);

        bodyWrapper.put("inputs", body);
        String jsonBody = JsonUtils.printAsJson(bodyWrapper);

        Logging.INSTANCE.info("requesting chunk " + chunk + " of " + variable + " to CDS API");

        // retrieve the job id
        String endpoint = getEndpointUrl("/processes/" + this.dataset + "/execute");
        Logging.INSTANCE.info("Ask for job id: " + endpoint + "\n" + jsonBody);
        HttpResponse<JsonNode> response = Unirest.post(endpoint).header(CDS_API_KEY_HEADER, apiKey)
                .header("Content-Type", "application/json").header("Accept", "application/json").body(jsonBody).asJson();

        if (response.isSuccess()) {

            if (response.getBody().getObject().has("status") && "accepted".equals(response.getBody().getObject().get("status"))) {
                // check the status of job
                int time = 0;
                int tryafter = 5;
                String requestId = response.getBody().getObject().has("jobID")
                        ? response.getBody().getObject().getString("jobID")
                        : null;
                if (requestId == null) {
                    Logging.INSTANCE.warn("Retrieval of CDS chunk " + variable + "/" + chunk + " didn't return a job ID");
                    Logging.INSTANCE.warn(response.getBody().toPrettyString());
                    return ret;
                }
                String status = null;
                do {
                    try {
                        Thread.sleep(tryafter * 1000);
                    } catch (InterruptedException e) {
                        break;
                    }

                    time += tryafter;
                    /*
                     * inquire about task
                     */
                    endpoint = getEndpointUrl("/jobs/" + requestId);
                    Logging.INSTANCE.info("Ask for job status: " + endpoint);
                    response = Unirest.get(endpoint).header("PRIVATE-TOKEN", apiKey).header("Content-Type", "application/json")
                            .header("Accept", "application/json").asJson();
                    if (response.isSuccess()) {
                        status = response.getBody().getObject().getString("status");
                        Logging.INSTANCE.info("Status of retrieval of CDS chunk " + variable + "/" + chunk + ": " + status);
                        if ("failed".equals(status)) {
                            break;
                        }
                    } else {
                        Logging.INSTANCE.warn(
                                "Ask for job status return an error " + response.getStatus() + ": " + response.getStatusText());
                        return false;
                    }

                } while (time < TIMEOUT_SECONDS && !"successful".equals(status) && !"failed".equals(status));

                // retrieve the job results
                endpoint = getEndpointUrl("/jobs/" + requestId + "/results");
                Logging.INSTANCE.info("Ask for job results: " + endpoint);
                response = Unirest.get(endpoint).header("PRIVATE-TOKEN", apiKey).header("Content-Type", "application/json")
                        .header("Accept", "application/json").asJson();
                if (response.isSuccess()) {
                    // retrieve the url
                    String href = null;
                    JSONObject rBody = response.getBody().getObject();
                    try {
                        href = rBody.getJSONObject("asset").getJSONObject("value").getString("href");
                    } catch (JSONException e) {
                        Logging.INSTANCE.warn("The result is not API compliant: " + response.getBody().toPrettyString());
                        return false;
                    }
                    if (href.endsWith(".zip")) {
                        Logging.INSTANCE.info("chunk " + chunk + " data for " + variable + " ready: downloading....");
                        /*
                         * Download the zip and unzip in chunk directory
                         */
                        try {
                            URL uurl = new URL(href);
                            File zipFile = File.createTempFile("agera", ".zip");
                            URLUtils.copyChanneled(uurl, zipFile);
                            ZipUtils.unzip(zipFile, destinationDirectory);
                            FileUtils.deleteQuietly(zipFile);
                            ret = true;
                            Logging.INSTANCE.info("download of chunk " + chunk + " data for " + variable + " successful");
                        } catch (Throwable e) {
                            Logging.INSTANCE.warn(
                                    "Download of CDS chunk " + variable + "/" + chunk + " threw exception: " + e.getMessage());
                        }
                    } else {
                        Logging.INSTANCE.warn("The returned file is not .zip" + href);
                        return false;
                    }
                } else {
                    Logging.INSTANCE
                            .warn("The job results return an error " + response.getStatus() + ": " + response.getStatusText());
                    if (response.getBody().getObject().has("status")) {
                        StringBuffer details = new StringBuffer().append("Details:\n")
                                .append(response.getBody().getObject().getString("status"));
                        if (response.getBody().getObject().has("traceback")) {
                            details.append("\nTraceback: ").append(response.getBody().getObject().getString("traceback"));
                        }
                        Logging.INSTANCE.warn(details.toString());
                    }
                    return false;
                }
            } else {
                Logging.INSTANCE
                        .error("API request made to CDS Service didn't get accepted: " + response.getBody().toPrettyString());
                return false;
            }
        } else {
            Logging.INSTANCE
                    .error("API request to CDS service returned error " + response.getStatus() + ": " + response.getStatusText());
            return false;
        }
        return ret;
    }

    public String getEndpointUrl(String request) {
        return this.apiUrl + request;
    }

    @Override
    protected boolean processChunk(int chunk, String variable, File destinationDirectory) {

        String[] fields = variable.split("\\.");
        String cdsname = fields[0];
        String nativeName = null;

        Logging.INSTANCE.info("chunk " + chunk + " data for " + variable + " being ingested in local Geoserver");

        for(File f : destinationDirectory.listFiles(new FilenameFilter(){

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".nc");
            }
        })) {

            /*
             * parse the file name and get year, month and day
             */
            Matcher matcher = pattern.matcher(MiscUtilities.getFileBaseName(f));
            if (!matcher.matches()) {
                Logging.INSTANCE.warn("CDS file does not match naming pattern: ignoring " + f);
                continue;
            }

            // layer naming logic is repeated in #getDataLayer()
            String daySignature = matcher.group(1).substring(1, 9);
            String layerId = cdsname + "_" + daySignature;

            if (nativeName == null) {
                try (GridDataset dataset = GridDataset.open(f.toString())) {
                    nativeName = dataset.getGrids().get(0).getName();
                } catch (IOException e) {
                    Logging.INSTANCE.warn("Can't open CDS file : " + f);
                    return false;
                }
            }

            if (!geoserver.createCoverageLayer(dataset, layerId, f, nativeName)) {
                Logging.INSTANCE.warn("Geoserver ingestion of " + f + " returned a failure code");
                return false;
            }
        }

        Logging.INSTANCE.info("Geoserver ingestion of chunk " + chunk + " data for " + variable + " terminated successfully");

        return true;
    }

    @Override
    protected String getDataLayer(String variable, int tick) {

        String[] fields = variable.split("\\.");
        String cdsname = fields[0];
        String file = getOriginalFile(variable, tick);
        Matcher matcher = pattern.matcher(MiscUtilities.getFileBaseName(file));
        if (matcher.matches()) {
            String daySignature = matcher.group(1).substring(1, 9);
            return cdsname + "_" + daySignature;
        }
        return null;
    }

    protected Geoserver getGeoserver() {
        return geoserver;
    }

    @Override
    public String getName() {
        return dataset;
    }

}

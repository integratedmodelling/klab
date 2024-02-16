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
	private String user;

	public static final String CDS_USER_NUMBER_PROPERTY = "klab.copernicus.cds.user";
	public static final String CDS_API_KEY_PROPERTY = "klab.copernicus.cds.apikey";
	public static final String CDS_API_VERSION_1_0 = "1_0";
	public static final String CDS_API_VERSION_1_1 = "1_1";
	private int TIMEOUT_SECONDS = 30;
	private static Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");

	/*
	 * tabulate and shut up
	 */
	String[][] monts = { { "01", "02", "03" }, { "04", "05", "06" }, { "07", "08", "09" }, { "10", "11", "12" } };

	/**
	 * These don't have to be right for the months
	 */
	String[] days = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	public CopernicusCDSDatacube(String dataset, ITimeInstant dataStart, double noDataValue) {

		super(Time.resolution(1, Type.DAY), Time.resolution(3, Type.MONTH), dataStart,
				Configuration.INSTANCE.getDataPath("copernicus/" + dataset), noDataValue);
		this.dataset = dataset;
		this.user = Configuration.INSTANCE.getProperties().getProperty(CDS_USER_NUMBER_PROPERTY);
		this.apiKey = Configuration.INSTANCE.getProperties().getProperty(CDS_API_KEY_PROPERTY);
		if (this.apiKey == null || this.user == null) {
			setOnline(false, "Copernicus CDS datacube: no CDS credentials provided in configuration");
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
		for (int tick : getChunkTicks(chunk)) {
			File tickFile = new File(destinationDirectory + File.separator
					+ getOriginalDataFilename(variable, tick, destinationDirectory));
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
		body.put("version", CDS_API_VERSION_1_0);

		configureRequest(variable, body);

		String jsonBody = JsonUtils.printAsJson(body);

		Logging.INSTANCE.info("requesting chunk " + chunk + " of " + variable + " to CDS API");
		
		HttpResponse<JsonNode> response = Unirest.post(getEndpointUrl("resources/datasets/" + this.dataset))
				.basicAuth(user, apiKey).header("Accept", "application/json").body(jsonBody).asJson();

		if (response.isSuccess()) {

			System.out.println(response.getBody().getObject());

			if (response.getBody().getObject().has("state")) {

				int time = 0;
				int tryafter = 5;
				String url = null;

				while (time < TIMEOUT_SECONDS && !"completed".equals(response.getBody().getObject().get("state"))) {

					String requestId = response.getBody().getObject().has("request_id")
							? response.getBody().getObject().getString("request_id")
							: null;

					if (requestId == null || response.getBody().getObject().has("error")) {
						break;
					}

					try {
						Thread.sleep(tryafter * 1000);
					} catch (InterruptedException e) {
						break;
					}

					time += tryafter;

					/*
					 * inquire about task
					 */
					response = Unirest.get(getEndpointUrl("tasks/" + requestId)).basicAuth(user, apiKey).asJson();

					System.out.println(response.getBody().getObject());

					if (response.getBody().getObject().has("error")) {
						break;
					}

					// heed their fucking advice
					if (response.getHeaders().containsKey("Retry-After")) {
						tryafter = (int) Math.ceil(Double.parseDouble(response.getHeaders().get("Retry-After").get(0)));
					}
				}

				if (response.getBody().getObject().has("location")) {

					url = response.getBody().getObject().getString("location");
					if (url.endsWith(".zip")) {

				        Logging.INSTANCE.info("chunk " + chunk + " data for " + variable + " ready: downloading....");

				        /*
						 * Download the zip and unzip in chunk directory
						 */
						try {
							URL uurl = new URL(url);
							File zipFile = File.createTempFile("agera", ".zip");
							URLUtils.copyChanneled(uurl, zipFile);
							ZipUtils.unzip(zipFile, destinationDirectory);
							FileUtils.deleteQuietly(zipFile);
							ret = true;
	                        Logging.INSTANCE.info("download of chunk " + chunk + " data for " + variable + " successful");
						} catch (Throwable e) {
							Logging.INSTANCE.warn("Download of CDS chunk " + variable + "/" + chunk
									+ " threw exception: " + e.getMessage());
						}
					}
				}

			} else {
				Logging.INSTANCE.warn("Retrieval of CDS chunk " + variable + "/" + chunk + " threw exception: "
						+ response.getBody().getObject().get("message"));
			}
		} else {
		    Logging.INSTANCE.error("API request to CDS service returned error " + response.getStatusText());
		}

		return ret;
	}
	
	public String getEndpointUrl(String request) {
		return "https://cds.climate.copernicus.eu/api/v2/" + request;
	}

	@Override
	protected boolean processChunk(int chunk, String variable, File destinationDirectory) {

		String[] fields = variable.split("\\.");
		String cdsname = fields[0];
		String nativeName = null;
		
        Logging.INSTANCE.info("chunk " + chunk + " data for " + variable + " being ingested in local Geoserver");

		for (File f : destinationDirectory.listFiles(new FilenameFilter() {

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

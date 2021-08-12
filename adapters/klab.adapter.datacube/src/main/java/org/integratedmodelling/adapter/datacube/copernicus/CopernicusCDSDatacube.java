package org.integratedmodelling.adapter.datacube.copernicus;

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
import org.integratedmodelling.klab.exceptions.KlabConfigurationException;
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

	String dataset;
	String apiKey;
	String user;
	Geoserver geoserver;

	private int TIMEOUT_SECONDS = 30;

	/*
	 * tabulate and shut up
	 */
	String[][] monts = { { "01", "02", "03" }, { "04", "05", "06" }, { "07", "08", "09" }, { "10", "11", "12" } };

	/**
	 * These don't have to be right for the months
	 */
	String[] days = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	public CopernicusCDSDatacube(String dataset, ITimeInstant dataStart) {
		super(Time.resolution(1, Type.DAY), Time.resolution(3, Type.MONTH), dataStart,
				Configuration.INSTANCE.getDataPath("copernicus/" + dataset));
		this.dataset = dataset;
		this.user = Configuration.INSTANCE.getProperties().getProperty("copernicus.cds.user");
		this.apiKey = Configuration.INSTANCE.getProperties().getProperty("copernicus.cds.apikey");

		this.geoserver = Geoserver.create();

		if (!this.geoserver.isOnline()) {
			throw new KlabConfigurationException("AgERA5 ingestor: no Geoserver is available");
		}
	}

	@Override
	protected boolean downloadChunk(int chunk, String variable, File destinationDirectory) {

		Map<String, Object> body = new HashMap<>();
		String[] fields = variable.split("\\.");
		String cdsname = fields[0];
		boolean ret = false;
		ITimeInstant date = getChunkStart(chunk);

		body.put("variable", cdsname);
//			if (var.timepoint != null) {
//
//			}
//			if (var.statistic != null) {
//				body.put("statistic", var.timepoint.cdsname);
//			}
		body.put("year", "" + date.getYear());
		body.put("month", this.monts[(date.getMonth() - 1) / 3]);
		body.put("day", this.days);

		String jsonBody = JsonUtils.printAsJson(body);

		HttpResponse<JsonNode> response = Unirest.post(getEndpointUrl("resources/datasets/" + this.dataset))
				.basicAuth(user, apiKey).header("Accept", "application/json").body(jsonBody).asJson();

		if (response.isSuccess()) {

			if (response.getBody().getObject().has("state")) {

				int time = 0;
				int tryafter = 5;
				String url = null;

				while (time < TIMEOUT_SECONDS && !"completed".equals(response.getBody().getObject().get("state"))) {

					String requestId = response.getBody().getObject().getString("request_id");
					try {
						Thread.sleep(tryafter * 1000);
					} catch (InterruptedException e) {
						break;
					}
					time += tryafter;
					/*
					 * inquire about task
					 */
					response = Unirest.post(getEndpointUrl("tasks/" + requestId)).basicAuth(user, apiKey).asJson();

					if (response.getBody().getObject().has("error")) {
						break;
					}

					// heed their fucking advice
					if (response.getHeaders().containsKey("Retry-After")) {
						tryafter = Integer.parseInt(response.getHeaders().get("Retry-After").get(0));
					}
				}

				if (response.getBody().getObject().has("location")) {

					url = response.getBody().getObject().getString("location");
					if (url.endsWith(".zip")) {

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
		Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");

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

			String daySignature = matcher.group(1).substring(1, 9);
			String layerId = cdsname + "_" + daySignature;

			String nativeName = layerId;
			try (GridDataset dataset = GridDataset.open(f.toString())) {
				nativeName = dataset.getGrids().get(0).getName();
			} catch (IOException e) {
				Logging.INSTANCE.warn("Can't open CDS file (ignored): " + f);
				continue;
			}

			if (!geoserver.createCoverageLayer(dataset, layerId, f, nativeName)) {
				Logging.INSTANCE.warn("Geoserver ingestion of " + f + " returned a failure code");
				return false;
			}
		}
		return true;
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

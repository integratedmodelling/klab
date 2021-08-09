package org.integratedmodelling.weather.adapters.agera;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.adapter.datacube.Datacube.IngestionService;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabConfigurationException;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.klab.utils.ZipUtils;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube.Variable;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube.VariableConfiguration;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import ucar.nc2.dt.grid.GridDataset;

public class AgERAIngestionService implements IngestionService {

	private Executor executor = null;
	private AgERADatacube cube;
	Geoserver geoserver;

	public static final String CHUNK_DOWNLOAD_TIME_MS = "time.download.ms";
	public static final String CHUNK_PROCESSING_TIME_MS = "time.processing.ms";

	private long TIMEOUT_SECONDS = 30;

	/**
	 * TODO read all the chunks available at initialization and memorize the mean
	 * download time if 1+ are available.
	 */
	private int meanChunkDownloadTime = 200;
	/*
	 * tabulate and shut up
	 */
	String[][] monts = { { "01", "02", "03" }, { "04", "05", "06" }, { "07", "08", "09" }, { "10", "11", "12" } };

	/**
	 * These don't have to be right for the months
	 */
	String[] days = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	public AgERAIngestionService() {

		/*
		 * One download at a time unless specifically configured.
		 */
		this.executor = new ThreadPoolExecutor(0,
				Integer.parseInt(Configuration.INSTANCE.getProperty(AgERADatacube.CDS_DOWNLOAD_THREADS_PROPERTY, "1")),
				60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		this.geoserver = Geoserver.create();

		if (!this.geoserver.isOnline()) {
			throw new KlabConfigurationException("AgERA5 ingestor: no Geoserver is available");
		}
		
		recomputeProcessingTime();
	}

	void recomputeProcessingTime() {

		/*
		 * estimate mean processing time per chunk based on contents of chunk
		 * properties and number of threads in executor.
		 */
		long secs = 0;
		int dirs = 0;
		
		for (File chunkDir : Configuration.INSTANCE.getDataPath(AgERADatacube.ID).listFiles(new FileFilter() {
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
					secs += Long.parseLong(props.getProperty(CHUNK_DOWNLOAD_TIME_MS)) + Long.parseLong(props.getProperty(CHUNK_PROCESSING_TIME_MS));
					dirs ++;
				} catch (IOException e) {
					// just ignore, although this is likely a bad dir
				}
			}
		}

		if (dirs > 0) {
			this.meanChunkDownloadTime = (int)(secs/(1000*dirs));
		}

	}

	@Override
	public void queueDownload(Object downloadSpecs, Object variableSpecs) {

		executor.execute(new Thread() {

			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long begin = start;
				boolean failure = false;
				Properties properties = new Properties();
				File dir = downloadChunk(downloadSpecs.toString());
				if (dir != null) {
					properties.setProperty(CHUNK_DOWNLOAD_TIME_MS, "" + (System.currentTimeMillis() - start));
					start = System.currentTimeMillis();
					if (processChunk(downloadSpecs.toString(), (Variable) variableSpecs, dir)) {
						properties.setProperty(CHUNK_PROCESSING_TIME_MS, "" + (System.currentTimeMillis() - start));
						try (OutputStream out = new FileOutputStream(
								new File(dir + File.separator + "chunk.properties"))) {
							properties.store(out,
									"Chunk " + downloadSpecs + " finished downloaded and processing at "
											+ DateTime.now(DateTimeZone.UTC) + ": total processing time = "
											+ new Period(System.currentTimeMillis() - begin));
						} catch (IOException e) {
							failure = true;
						}
					}
					if (failure) {
						Logging.INSTANCE.error("Transfer of CDS chunk " + downloadSpecs + " failed");
						FileUtils.deleteQuietly(dir);
					}
				}
			}

		});
	}

	// download, return dir if all OK
	private File downloadChunk(String string) {

		Map<String, Object> body = new HashMap<>();
		String[] fields = string.split("\\.");
		File ret = null;

		// should contain only one
		Collection<VariableConfiguration> vars = cube.getVariable(StringUtil.join(fields, ".", fields.length - 2));

		for (VariableConfiguration var : vars) {

			int year = Integer.parseInt(fields[fields.length - 2]);
			int mont = Integer.parseInt(fields[fields.length - 1]);

			body.put("variable", var.variable.cdsname);
			if (var.timepoint != null) {

			}
			if (var.statistic != null) {
				body.put("statistic", var.timepoint.cdsname);
			}
			body.put("year", "" + year);
			body.put("month", this.monts[mont]);
			body.put("day", this.days);

			String jsonBody = JsonUtils.printAsJson(body);

			HttpResponse<JsonNode> response = Unirest
					.post(cube.getEndpointUrl("resources/datasets/sis-agrometeorological-indicators"))
					.basicAuth(cube.getUsername(), cube.getApiKey()).header("Accept", "application/json").body(jsonBody)
					.asJson();

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
						response = Unirest.post(cube.getEndpointUrl("tasks/" + requestId))
								.basicAuth(cube.getUsername(), cube.getApiKey()).asJson();

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

							File chunkDir = new File(
									Configuration.INSTANCE.getDataPath(AgERADatacube.ID) + File.separator + string);

							/*
							 * Download the zip and unzip in chunk directory
							 */
							try {
								URL uurl = new URL(url);
								File zipFile = File.createTempFile("agera", ".zip");
								URLUtils.copyChanneled(uurl, zipFile);
								ZipUtils.unzip(zipFile, chunkDir);
								FileUtils.deleteQuietly(zipFile);
								ret = chunkDir;
							} catch (Throwable e) {
								Logging.INSTANCE.warn("Download of AgERA5 chunk " + string + " returned exception: "
										+ e.getMessage());
								break;
							}
						}
					}

				} else {
					Logging.INSTANCE.warn("Retrieval of AgERA5 chunk " + string + " returned exception: "
							+ response.getBody().getObject().get("message"));
				}

			}
		}

		recomputeProcessingTime();
		
		return ret;
	}

	public int getMeanWaitTimePerChunk() {
		return meanChunkDownloadTime;
	}

	// process, return true if all OK
	protected boolean processChunk(String chunk, Variable variable, File dir) {

		List<File> toIngest = new ArrayList<>();
		Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");

		for (File f : dir.listFiles(new FilenameFilter() {

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
			int year = Integer.parseInt(daySignature.substring(0, 4));
			int month = Integer.parseInt(daySignature.substring(4, 6));
			int day = Integer.parseInt(daySignature.substring(6));
			String layerId = variable.cdsname + "_" + daySignature;

			String nativeName = layerId;
			try (GridDataset dataset = GridDataset.open(f.toString())) {
				nativeName = dataset.getGrids().get(0).getName();
			} catch (IOException e) {
				Logging.INSTANCE.warn("Can't open CDS file (ignored): " + f);
				continue;
			}

			if (!geoserver.createCoverageLayer("agera5", layerId, f, nativeName)) {
				Logging.INSTANCE.warn("Geoserver ingestion of " + f + " returned a failure code");
			}
		}

		return true;
	}

	public void setDatacube(AgERADatacube agERADatacube) {
		this.cube = agERADatacube;
	}

	// TODO remove
	public static void main(String[] dio) {

		File dir = new File(
				"C:\\Users\\Ferd\\Dropbox\\Data\\AgERA5\\dataset-sis-agrometeorological-indicators-5a0fb25d-193a-416b-82e1-69200c300954");
		Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");
		Variable variable = Variable.LIQUID_PRECIPITATION_VOLUME;
		Geoserver geoserver = Geoserver.create();

		for (File f : dir.listFiles(new FilenameFilter() {

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
			int year = Integer.parseInt(daySignature.substring(0, 4));
			int month = Integer.parseInt(daySignature.substring(4, 6));
			int day = Integer.parseInt(daySignature.substring(6));
			String layerId = variable.cdsname + "_" + daySignature;

			String nativeName = layerId;
			try (GridDataset dataset = GridDataset.open(f.toString())) {
				nativeName = dataset.getGrids().get(0).getName();
			} catch (IOException e) {
				Logging.INSTANCE.warn("Can't open CDS file (ignored): " + f);
				continue;
			}

			if (!geoserver.createCoverageLayer("agera5", layerId, f, nativeName)) {
				Logging.INSTANCE.warn("Geoserver ingestion of " + f + " returned a failure code");
			}

		}

	}

}

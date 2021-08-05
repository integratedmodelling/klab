package org.integratedmodelling.weather.adapters.agera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.adapter.datacube.Datacube.IngestionService;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube.VariableConfiguration;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class AgERAIngestionService implements IngestionService {

	private Executor executor = null;
	private AgERADatacube cube;

	public static final String CHUNK_DOWNLOAD_TIME_MS = "time.download.ms";
	public static final String CHUNK_PROCESSING_TIME_MS = "time.processing.ms";

	private long TIMEOUT_SECONDS = 30;
	
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

	}

	@Override
	public void queueDownload(Object downloadSpecs) {

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
					if (processChunk(dir)) {
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

		VariableConfiguration var = cube.getVariable(Arrays.copyOf(fields, fields.length - 2));
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

		HttpResponse<JsonNode> response = Unirest
				.post(cube.getEndpointUrl("resources/datasets/sis-agrometeorological-indicators"))
				.basicAuth(cube.getUsername(), cube.getApiKey()).header("Accept", "application/json")
				.body(JsonUtils.printAsJson(body)).asJson();

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
					response = Unirest
							.post(cube.getEndpointUrl("tasks/" + requestId))
							.basicAuth(cube.getUsername(), cube.getApiKey()).header("Accept", "application/json")
							.body(JsonUtils.printAsJson(body)).asJson();
					
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
						 * Download the zip 
						 */
						
					}
				}
				
			} else {
				Logging.INSTANCE.warn("Retrieval of AgERA5 chunk " + string + " returned exception: "
						+ response.getBody().getObject().get("message"));
			}

		}

		return null;
	}

	// process, return true if all OK
	protected boolean processChunk(File dir) {
		// TODO Auto-generated method stub
		return true;
	}

	public void setDatacube(AgERADatacube agERADatacube) {
		this.cube = agERADatacube;
	}

}

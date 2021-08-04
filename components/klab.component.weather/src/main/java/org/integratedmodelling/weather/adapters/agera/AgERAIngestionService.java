package org.integratedmodelling.weather.adapters.agera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube.Variable;
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
		
		Variable var = cube.getVariable(Path.getFirst(fields[0], "."));
		int year = Integer.parseInt(fields[1]);
		int mont = Integer.parseInt(fields[2]);
		
		HttpResponse<JsonNode> response = Unirest.post(cube.getEndpointUrl("resources/datasets/sis-agrometeorological-indicators"))
				.basicAuth(cube.getUsername(), cube.getApiKey()).header("Accept", "application/json")
				.body(JsonUtils.printAsJson(body)).asJson();
		
		
		
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

package org.integratedmodelling.klab.ogc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.openeo.OpenEO;
import org.integratedmodelling.klab.openeo.OpenEO.OpenEOFuture;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.MapUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OpenEOTests {

	static String sShape = "EPSG:4326 POLYGON ((-2.6430600187259428 40.75436284251779, -7.8229143312147436 40.75436284251779, -7.8229143312147436 43.322224962103604, -2.6430600187259428 43.322224962103604, -2.6430600187259428 40.75436284251779))";

	private OpenEO openEO;
	private Process smallProcess;
	private Process largeProcess;

	Map<String, Object> testGeometryGeoJSON = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void checkConnection() {
		this.openEO = new OpenEO("https://openeo-dev.vito.be/openeo/1.1.0", Klab.INSTANCE.getRootMonitor());
		assert openEO.isOnline();
		this.smallProcess = JsonUtils.loadFromClasspath("openeo/small.json", Process.class);
		this.largeProcess = JsonUtils.loadFromClasspath("openeo/udp.json", Process.class);
		assert this.smallProcess instanceof Process;
		this.testGeometryGeoJSON = Shape.create(sShape).asGeoJSON();
	}

	@Test
	public void smallProcessTest() {
		assertEquals(openEO.runJob("add", Parameters.create("x", 5, "y", 3), Number.class), 8);
	}

//	@Test
//	public void createAndDeleteSmallJob() {
//		String jobId = openEO.createJob(smallProcess, null);
//		assertNotNull(jobId);
//		System.out.println("Job created: ID is " + jobId);
//		System.out.println(MapUtils.dump(openEO.getJobMetadata(jobId)));
//		System.out.println("Deleting job...");
//		assert openEO.deleteJob(jobId);
//	}

	@Test
	public void largeProcessTestNoParameters() {
		openEO.submit("dummy_udp", null, (result) -> {
			fail("should not run without parameters");
		}, (code, error) -> {
			assertEquals(code, "ProcessParameterRequired");
		}, largeProcess);
	}

	@Test
	public void runLargeProcessTest() {
		/*
		 * Run a large data calculation synchronously, pass the output as a stream to a
		 * file serializer
		 */
		File outfile = new File(System.getProperty("user.home") + File.separator + "openeo_test.tif");
		FileUtils.deleteQuietly(outfile);
		
		openEO.runJob("udp_annual_avg_fcover", Parameters.create("year", 2020, "geometry", testGeometryGeoJSON),
				(input) -> {
					try {
						FileUtils.copyInputStreamToFile(input,
								outfile);
					} catch (IOException e) {
						throw new KlabIOException(e);
					}
				}, OpenEO.readUdp(
						"https://raw.githubusercontent.com/integratedmodelling/OpenEO-UDP-UDF-catalogue/main/UDP/json/udp_annual_avg_fcover.json"));
		
		assert outfile.isFile();
		
	}
	
	@Test
	public void runLargeProcessAsync() throws InterruptedException, ExecutionException {
		/*
		 * Run a large data calculation synchronously, pass the output as a stream to a
		 * file serializer
		 */
		OpenEOFuture future = openEO.submit("udp_annual_avg_fcover",
				Parameters.create("year", 2020, "geometry", testGeometryGeoJSON), OpenEO.readUdp(
						"https://raw.githubusercontent.com/integratedmodelling/OpenEO-UDP-UDF-catalogue/main/UDP/json/udp_annual_avg_fcover.json"));

		if (future.getError() != null) {
			System.out.println("ZIOCAN process terminated with errors: " + future.getError());
		} else {
			Map<String, Object> result = future.get();
			System.out.println(MapUtils.dump(result));
			assert result instanceof Map;
		}
	}

	@Test
	public void largeProcessTest() {

		Parameters<String> parameters = Parameters.create("geometry", testGeometryGeoJSON, "year", 2021, "resolution",
				100);

		openEO.submit("dummy_udp", parameters, (result) -> {
			System.out.println(JsonUtils.asString(result));
		}, (code, error) -> {
			fail(code + ": " + error);
		}, OpenEO.readUdp(
				"https://raw.githubusercontent.com/integratedmodelling/OpenEO-UDP-UDF-catalogue/main/UDP/json/udp_annual_avg_fcover.json"));
	}

}

package org.integratedmodelling.klab.ogc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.openeo.OpenEO;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
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

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void checkConnection() {
        this.openEO = new OpenEO("https://openeo.vito.be/openeo/1.1.0", Klab.INSTANCE.getRootMonitor());
        assert openEO.isOnline();
        this.smallProcess = JsonUtils.loadFromClasspath("openeo/small.json", Process.class);
        this.largeProcess = JsonUtils.loadFromClasspath("openeo/udp.json", Process.class);
        assert this.smallProcess instanceof Process;
    }

    @Test
    public void smallProcessTest() {

        if (openEO.validateProcess(smallProcess, null, null)) {
            assertEquals(openEO.runJob(smallProcess, null, Number.class), 8);
        } else {
            fail("Validation of UDP failed");
        }
    }

    @Test
    public void createAndDeleteSmallJob() {
        String jobId = openEO.createJob(smallProcess, null);
        assertNotNull(jobId);
        System.out.println("Job created: ID is " + jobId);
        System.out.println(MapUtils.dump(openEO.getJobMetadata(jobId)));
        System.out.println("Deleting job...");
        assert openEO.deleteJob(jobId);
    }

    @Test
    public void largeProcessTestNoParameters() {
        openEO.submit(largeProcess, null, (result) -> {
            fail("should not run without parameters");
        }, (code, error) -> {
            assertEquals(code, "ProcessParameterRequired");
        });
    }

    @Test
    public void largeProcessTest() {

        Map<String, Object> nsp = Shape.create(sShape).asGeoJSON();
        Parameters<String> parameters = Parameters.create("geometry", nsp, "year", 2021, "resolution", 100);
        
        openEO.submit(largeProcess, parameters, (result) -> {
            System.out.println(JsonUtils.asString(result));
        }, (code, error) -> {
            fail(code + ": " + error);
        });
    }

}

package org.integratedmodelling.klab.ogc.test;

import static org.junit.Assert.assertEquals;

import org.integratedmodelling.klab.openeo.OpenEO;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class OpenEOTests {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void smallProcessTest() {

        OpenEO openEO = new OpenEO("https://openeo.vito.be/openeo/1.1.0");
        Process process = JsonUtils.loadFromClasspath("openeo/small.json", Process.class);

        if (openEO.validateProcess(process, null)) {
            assertEquals(openEO.runJob(process, 0, null, Number.class), 8);

            // if (job != null) {
            // openEO.startJob(job, (result) -> {
            // System.out.println(result);
            // }, (error) -> {
            // System.err.println(error);
            // });
            // }
        }
    }

}

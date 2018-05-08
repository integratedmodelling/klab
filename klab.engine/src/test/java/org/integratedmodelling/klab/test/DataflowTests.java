/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.io.InputStream;
import java.util.regex.Pattern;

import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.klab.Dataflows;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 * Parse KDL files in src/test/resources for now. Later will run them
 * when appropriate, using the dataflow compiler.
 * 
 * @author ferdinando.villa
 *
 */
public class DataflowTests {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public void tearDown() throws Exception {
    }

    @Test
    public void parseKdl() throws Exception {

        /*
         * parse every file in the kdl/ package, under tests/resources
         */
        for (String test : new Reflections("kdl", new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.kdl"))) {
            
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(test)) {
                IKdlDataflow kdl = Dataflows.INSTANCE.declare(stream);
                System.out.println("result: " + kdl);
            } catch (Exception e) {
                throw e;
            }
            
        }
    }

}

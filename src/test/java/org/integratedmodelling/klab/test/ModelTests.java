/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.regex.Pattern;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 * Runs every .kim test file in src/main/resources/kim as a k.LAB test
 * namespace.
 * 
 * @author ferdinando.villa
 *
 */
public class ModelTests {

    Engine engine;

    @Before
    public void setUp() throws Exception {
        engine = Engine.start();
    }

    @After
    public void tearDown() throws Exception {
      engine.stop();
    }

    @Test
    public void runTests() throws Exception {
      
      try (ISession session = engine.createSession()) {
        ISubject context = session.observe("test.tanzania").get();
        IObservation elevation = context.observe("hydrology:SurfaceWaterFlow").get();
      }
      
        /*
         * run every file in the kim/ package, under tests/resources
         */
        for (String test : new Reflections("kim", new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.kim"))) {
            engine.run(getClass().getClassLoader().getResource(test)).get();
        }
    }

}

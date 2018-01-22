/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
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

    IEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = Engine.start();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void runTests() throws KlabException {
        /*
         * run every file in the kim/ package, under tests/resources
         */
        for (String test : new Reflections("kim", new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.kim"))) {
            engine.run(getClass().getClassLoader().getResource(test));
        }
    }

}

/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.regex.Pattern;

import org.integratedmodelling.klab.engine.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 * Runs every .kim test file in src/main/resources/kim as a k.LAB test namespace.
 * <p>
 * If a system property <code>test.case = [kim file name (no extension)]</code> is passed, only run
 * the specific file named. Otherwise run them all.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public class ModelTests {

  static Engine engine;

  @BeforeClass
  public static void setUp() throws Exception {
    engine = Engine.start();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    if (engine != null) {
      engine.stop();
    }
  }

  @Test
  public void runTests() throws Exception {

    String file = System.getProperty("test.case");

    /*
     * run every file in the kim/ package, under tests/resources
     */
    for (String test : new Reflections("kim", new ResourcesScanner())
        .getResources(Pattern.compile(".*\\.kim"))) {
      if (file == null || test.endsWith(file + ".kim")) {
        engine.run(getClass().getClassLoader().getResource(test)).get();
      }
    }
  }
}

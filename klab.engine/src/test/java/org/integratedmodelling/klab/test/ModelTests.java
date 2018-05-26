/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.regex.Pattern;

import org.integratedmodelling.klab.engine.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 * Runners for all the k.IM test cases in /kim.
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

	// @Test
	public void runAllTests() throws Exception {

		String file = System.getProperty("test.case");

		/*
		 * run every file in the kim/ package, under tests/resources
		 */
		for (String test : new Reflections("kim", new ResourcesScanner()).getResources(Pattern.compile(".*\\.kim"))) {
			if (file == null || test.endsWith(file + ".kim")) {
				engine.run(getClass().getClassLoader().getResource(test)).get();
			}
		}
	}

	@Test
	public void simpleQualities() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test1.kim")).get();
	}

	@Test
	public void simpleQualitiesWithDependency() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test2.kim")).get();
	}

	@Test
	public void simpleQualitiesWithSharedDependencies() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test3.kim")).get();
	}
	
	@Test
	public void spatialInstantiation() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test4.kim")).get();
	}
	
	@Test
	public void spatialConformantRescaling() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test5.kim")).get();
	}
	
	@Test
	public void simpleValueMediation() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test6.kim")).get();
	}
	
	@Test
	public void complexValueMediation() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test7.kim")).get();
	}
	
	@Ignore("not supported yet")
	@Test
	public void featureUpstreamResolution() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test8.kim")).get();
	}
	
	@Test
	public void nonStandardProjection() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test9.kim")).get();
	}
	
	@Test
	@Ignore("not supported yet")
	public void indirectDistanceComputation() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test10.kim")).get();
	}

	@Test
	public void simpleNonsemanticModel() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test11.kim")).get();
	}
}

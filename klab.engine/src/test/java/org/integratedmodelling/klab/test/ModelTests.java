/**
 * 
 */
package org.integratedmodelling.klab.test;

import java.util.function.Consumer;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
		// ensure errors cause exc
		Logging.INSTANCE.setErrorWriter(new Consumer<String>() {
			
			@Override
			public void accept(String t) {
				throw new KlabException(t);
			}
		});
	}

	@AfterClass
	public static void tearDown() throws Exception {
		if (engine != null) {
			engine.stop();
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
	@Ignore("fails due to bad projection data, not k.LAB")
	public void nonStandardProjection() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test9.kim")).get();
	}
	
	@Test
	public void indirectDistanceComputation() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test10.kim")).get();
	}

	@Test
	public void simpleNonsemanticModel() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test11.kim")).get();
	}
	
	@Test
	public void simpleClassification() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim/test12.kim")).get();
	}
}

/**
 * 
 */
package org.integratedmodelling.klab.ogc.test;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Runs every .kim test file in src/main/resources/kim as a k.LAB test
 * namespace.
 * <p>
 * If a system property <code>test.case = [kim file name (no extension)]</code>
 * is passed, only run the specific file named. Otherwise run them all.
 * <p>
 * TODO fix run logics according to Luke's comments.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public class VectorValidationTests extends TestSetup {

	static Engine engine;
	static IProject testProject;

	@BeforeClass
	public static void setUp() throws Exception {
		engine = Engine.start();
		testProject = Resources.INSTANCE.getLocalWorkspace().getProject("test.ogc.vector");
		if (testProject == null) {
			testProject = Resources.INSTANCE.getLocalWorkspace().createProject("test.ogc.vector",
					Klab.INSTANCE.getRootMonitor());
		}
		Resources.INSTANCE.getLocalResourceCatalog().clearOnly(testProject);
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		engine.stop();
	}

	@Test
	public void importStatesShp() {
		System.out.println(JsonUtils
				.printAsJson(((Resource) importResource("data.vector/states.shp", testProject)).getReference()));
	}

	@Test
	public void importArchsites() {
		System.out.println(JsonUtils
				.printAsJson(((Resource) importResource("data.vector/archsites.shp", testProject)).getReference()));
	}

	@Test
	public void importTigerRoads() {
		System.out.println(JsonUtils
				.printAsJson(((Resource) importResource("data.vector/tiger_roads.shp", testProject)).getReference()));
	}

}

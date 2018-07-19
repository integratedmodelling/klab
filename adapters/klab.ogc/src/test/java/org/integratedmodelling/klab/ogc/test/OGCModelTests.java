/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.ogc.test;

import java.util.regex.Pattern;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.engine.Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 * Test cases with local resources. Will create a project and load/validate
 * resources in them.
 * 
 * @author ferdinando.villa
 *
 */
public class OGCModelTests {

	static Engine engine;

	String[] resources = {
			"local:anonymous:test:utah_landcover",
			"local:anonymous:test:dem90m",
			"local:anonymous:test:states",
			"local:anonymous:test:nyroads"
	};

	/**
	 * Start the engine and ensure all our test URNs are present.
	 */
	@BeforeClass
	public static void setUp() throws Exception {

		engine = Engine.start();

		IProject testProject = Resources.INSTANCE.getLocalWorkspace().getProject("test");
		if (testProject == null) {
			testProject = Resources.INSTANCE.getLocalWorkspace().createProject("test", Klab.INSTANCE.getRootMonitor());
		}
		
		Resources.INSTANCE.getLocalResourceCatalog().clearOnly(testProject);
		
		/*
		 * TODO validate and build resources. This should be a set of test cases of its
		 * own, but there's no way in JUnit to ensure some tests are required to run
		 * before others, so we'll just use the test cases from another class.
		 */
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		engine.stop();
	}

	@Test
	public void rasterUtmProjection() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.raster/test1.kim")).get();
	}

	@Test
	public void simpleWCSTest() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.raster/test2.kim")).get();
	}

	@Test
	public void simpleVectorTest() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.raster/test3.kim")).get();
	}

	@Test
	public void simpleWFSTest() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.raster/test4.kim")).get();
	}
	
	@Test
	@Ignore
	// TODO - shape extraction isn't there yet
	public void simpleWCSTestWithShapeExtraction() throws Exception {
		engine.run(getClass().getClassLoader().getResource("kim.raster/test5.kim")).get();
	}
}

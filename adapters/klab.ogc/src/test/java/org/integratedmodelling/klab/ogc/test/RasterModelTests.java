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
import org.integratedmodelling.klab.engine.Engine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

// TODO: Auto-generated Javadoc
/**
 * Runs every .kim test file in src/main/resources/kim as a k.LAB test namespace.
 * <p>
 * If a system property <code>test.case = [kim file name (no extension)]</code> is passed, only run
 * the specific file named. Otherwise run them all.
 * <p>
 * TODO fix run logics according to Luke's comments.
 * <p>
 * @author ferdinando.villa
 *
 */
public class RasterModelTests {

  Engine engine;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    
    engine = Engine.start();
//    // load test resource set
//    Resources.INSTANCE.setResourceCatalog(
//         FileCatalog.create(getClass().getClassLoader().getResource("resources.raster/resources.json"),
//            IResource.class, Resource.class));
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {
    engine.stop();
  }

  /**
   * Run tests.
   *
   * @throws Exception the exception
   */
  @Test
  public void runTests() throws Exception {

    String file = System.getProperty("test.case");

    /*
     * run every file in the kim/ package, under tests/resources
     */
    for (String test : new Reflections("kim.raster", new ResourcesScanner())
        .getResources(Pattern.compile(".*\\.kim"))) {
      if (file == null || test.endsWith(file + ".kim")) {
        engine.run(getClass().getClassLoader().getResource(test)).get();
      }
    }
  }
}

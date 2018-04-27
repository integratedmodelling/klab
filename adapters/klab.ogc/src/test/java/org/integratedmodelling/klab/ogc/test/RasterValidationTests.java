/**
 * 
 */
package org.integratedmodelling.klab.ogc.test;

import java.util.regex.Pattern;

import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.raster.RasterAdapter;
import org.integratedmodelling.klab.utils.JsonUtils;
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
 * 
 * @author ferdinando.villa
 *
 */
public class RasterValidationTests {

  Engine        engine;
  RasterAdapter adapter = new RasterAdapter();

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {

    engine = Engine.start();

//    // load test resource set
//    Resources.INSTANCE.setResourceCatalog(FileCatalog.create(
//        getClass().getClassLoader().getResource("resources.raster/resources.json"), IResource.class,
//        Resource.class));
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

    /*
     * validate all data TODO create a test per dataset, some with expected failures
     */
    for (String datafile : new Reflections("data.raster", new ResourcesScanner())
        .getResources(Pattern.compile(".*\\.tif"))) {
      Builder builder =
          adapter.getValidator().validate(getClass().getClassLoader().getResource(datafile),
              new Parameters(), Klab.INSTANCE.getRootMonitor());
      IResource resource = builder.build(Urns.INSTANCE.createDisposableUrn());

      System.out.println(JsonUtils.printAsJson(resource));
      
      Resources.INSTANCE.getLocalResourceCatalog().put(resource.getUrn(), resource);
    }
  }
}

/**
 * 
 */
package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.observation.Scale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Runs various scale subsetting and indexing tests using regular and irregular space and time extents.
 * 
 * @author ferdinando.villa
 *
 */
public class ScaleTests {

  Engine engine;
  Scale spaceonly_regular = null;// TODO
  Scale timeonly_regular = null;// TODO
  Scale spacetime_regular = null;// TODO
  Scale spaceonly_irregular = null;// TODO
  Scale timeonly_irregular = null;// TODO
  Scale spacetime_irregular = null;// TODO
  Scale spaceonly_single = null;// TODO
  Scale timeonly_single = null;// TODO
  Scale spacetime_single = null;// TODO

  Scale[] scales = new Scale[] {spaceonly_regular, timeonly_regular, spacetime_regular,
      spaceonly_irregular, timeonly_irregular, spacetime_irregular, spaceonly_single,
      timeonly_single, spacetime_single};

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
    for (Scale scale : scales) {
      // TODO
    }
  }

}

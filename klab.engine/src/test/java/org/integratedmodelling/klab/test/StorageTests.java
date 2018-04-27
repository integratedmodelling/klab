/**
 * 
 */
package org.integratedmodelling.klab.test;

import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.localstorage.impl.DoubleStorage;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Runs various scale subsetting and indexing tests using regular and irregular space and time extents.
 * 
 * @author ferdinando.villa
 *
 */
public class StorageTests {
  
  @Test
  @Ignore("fails because of LArrayJ bug")
  public void runTests() throws Exception {

    Geometry geometry = Geometry.create("T1(2000)");

    // basic read/write
    // FAILS because of LArrayJ bug - double storage simply doesn't work.
    DoubleStorage storage = new DoubleStorage(geometry);
    for (int i = 0; i < 2000; i ++) {
      storage.set(geometry.locate(i), i);
    }
    for (int i = 0; i < 2000; i ++) {
      Double value = storage.get(geometry.locate(i));
      assert(value == (double)i);
    }
    
  }

}

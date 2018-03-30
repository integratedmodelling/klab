package org.integratedmodelling.klab.api.resolution;

import java.util.List;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.data.ILocator;

/**
 * The computable interface applies to objects that can contribute computations to a dataflow. If
 * so, they must be able to provide a declarative form of the computation as a list of resources
 * that can be turned into KDL actuators.
 * 
 * @author ferdinando.villa
 *
 */
public interface IComputable {

  /**
   * Return the computations for the passed locator.
   * 
   * @param locator
   * @return a list of computable resources, possibly empty.
   */
  List<IComputableResource> getComputation(ILocator locator);

}

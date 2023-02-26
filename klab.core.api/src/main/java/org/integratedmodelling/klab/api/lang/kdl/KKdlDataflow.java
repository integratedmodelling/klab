package org.integratedmodelling.klab.api.lang.kdl;

import java.util.Collection;
import java.util.List;

/**
 * A dataflow contains zero or more actuator specifications and some specified metadata.
 * 
 * @author Ferd
 *
 */
public interface KKdlDataflow extends KKdlStatement {

  /**
   * All actuators in the dataflow. If > 1, each can be computed in parallel. They may be resolved
   * or not, in which case they specify interfaces and possibly adapter operations to ensure correct
   * representation.
   * 
   * @return all actuators
   */
  Collection<KKdlActuator> getActuators();

  /**
   * Endpoint for this computation, if any. Implementations must be able to understand how to use it
   * based on other metadata.
   * 
   * @return the endpoint
   */
  String getEndpoint();

  /**
   * Version of this dataflow.
   * 
   * @return the version
   */
  String getVersion();

  /**
   * Minimum version of k.LAB needed to run.
   * 
   * @return the minimum version
   */
  String getKlabVersion();

  /**
   * Worldview to interpret the semantics in actors, if any.
   * 
   * @return the worldview name
   */
  String getWorldview();

  /**
   * Contextualizers to build the scale for the dataflow.
   * 
   * @return the scale definition
   */
  List<KKdlContextualizer> getScale();

  /**
   * Package name, if any, for declaration of functions in k.IM.
   * 
   * @return the package name
   */
  String getPackageName();
}

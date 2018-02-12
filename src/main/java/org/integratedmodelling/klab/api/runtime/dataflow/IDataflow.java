package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * A dataflow is a graph of actuators connected by links. When iterated, it returns all actuators in
 * order of dependency. It is also an actuator, representing a composite.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDataflow extends IActuator, Iterable<IActuator> {

  /**
   * All actuators in order of declaration. Iterate the dataflow itself to obtain them in dependency
   * order.
   * 
   * @return
   */
  public List<IActuator> getActuators();

  public Collection<ILink> getLinks();

  IScale getScale();
  
}

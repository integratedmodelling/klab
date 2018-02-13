package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDataflowService;

/**
 * A dataflow is a graph of actuators connected by links. When iterated, it returns all actuators in
 * order of dependency. It is also an actuator, representing a composite.
 * 
 * The end result of running a dataflow is a {@link IArtifact}.
 * 
 * Dataflows are created from KDL specifications by {@link IDataflowService}, or they can be built
 * piece by piece using a {@link Builder} (and serialized to KDL if necessary).
 * 
 * @author ferdinando.villa
 *
 */
public interface IDataflow extends IActuator, Iterable<IActuator> {

  public interface Builder {
    IDataflow build();
  }

  /**
   * All actuators in order of declaration. Iterate the dataflow itself to obtain them in dependency
   * order.
   * 
   * @return
   */
  public List<IActuator> getActuators();

  public Collection<ILink> getLinks();

  public IArtifact run(IMonitor monitor);

}

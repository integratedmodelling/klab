package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.Collection;

/**
 * A dataflow is a graph of actuators connected by links. When iterated, it returns all actuators in order of
 * dependency. It is also an actuator, representing a composite.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDataflow extends IActuator, Iterable<IActuator> {

    public Collection<IActuator> getActuators();

    public Collection<ILink> getLinks();
}

package org.integratedmodelling.klab.api.runtime.dataflow;

/**
 * A reference is an actuator that references another. Used to implement links and establish the
 * topological order of computation. The name of the reference may be different from the original to
 * reflect the local alias name; mediators may be added to match an observable with different
 * observation semantics.
 * 
 * @author ferdinando.villa
 *
 */
public interface IActuatorReference extends IActuator {

  IActuator getOriginalActuator();

}

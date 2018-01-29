package org.integratedmodelling.klab.api.observations;

import java.util.Collection;

/**
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IDirectObservation extends IObservation {

  String getName();

  Collection<IState> getStates();

}

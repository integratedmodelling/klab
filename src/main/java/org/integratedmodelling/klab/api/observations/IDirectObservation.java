package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.raw.IObjectData;

/**
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IDirectObservation extends IObservation, IObjectData {

  String getName();

  Collection<IState<?>> getStates();

}

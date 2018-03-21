package org.integratedmodelling.klab.api.observations;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.raw.IObjectArtifact;

/**
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IDirectObservation extends IObservation, IObjectArtifact {

  String getName();

  Collection<IState> getStates();

}

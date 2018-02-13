package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;

/**
 * Tags the observations that are countable in addition to direct. Countable
 * observations can be built by {@link IInstantiator} models ('model each').
 * 
 * @author ferdinando.villa
 *
 */
public interface ICountableObservation extends IDirectObservation {

}

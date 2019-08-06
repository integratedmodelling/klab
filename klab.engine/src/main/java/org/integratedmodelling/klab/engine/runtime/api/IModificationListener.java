package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.observations.IObservation;

/**
 * Observations use this interface to get notified when other observations
 * they depend on change.
 * 
 * @author ferdinando.villa
 *
 */
public interface IModificationListener {

	void onModification(IObservation modified);
	
}

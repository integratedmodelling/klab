package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * The predicate resolver takes a concrete predicate and an observation, and
 * resolves the role of the predicate in it.
 * 
 * @author Ferd
 *
 */
public interface IPredicateResolver<T extends IObservation> extends IContextualizer {

	/**
	 * Resolve the predicate within the observation. If false is returned, the
	 * predicate is removed.
	 * 
	 * @param predicate
	 * @param observation
	 * @param context
	 */
	boolean resolve(IConcept predicate, T observation, IComputationContext context);

}

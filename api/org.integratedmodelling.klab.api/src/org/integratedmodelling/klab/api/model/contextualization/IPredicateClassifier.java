package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A predicate classifier will receive each observation and decide the concrete
 * classifier to attribute to it based on an abstract one.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface IPredicateClassifier<T extends IDirectObservation> extends IContextualizer {

	/**
	 * Initialize if needed. This will be called only once before classify() is
	 * called on each object. If false is returned, classify() will not be called.
	 * 
	 * @param observations      the contextualized set of observations we will scan
	 *                          at classify().
	 * @param abstractPredicate the nature we want to assess in each observation.
	 * @param targetPredicate   if not null, the concrete target of the
	 *                          classification (i.e. the observation is trying to
	 *                          assess a specific nature rather than classifying any
	 *                          of the abstract predicates in the target).
	 * @param context           the runtime context
	 * @return true if classification should proceed.
	 */
	public boolean initialize(IObjectArtifact observations, IConcept abstractPredicate, IConcept targetPredicate,
			IContextualizationScope context);

	/**
	 * Classify an individual observation.
	 * 
	 * @param abstractPredicate
	 * @param observation
	 * @param context
	 * @return the concrete subclass of <code>abstractPredicate</code> that the
	 *         observation will get. Null will cause attribution and resolution to
	 *         be canceled for this object.
	 */
	public IConcept classify(IConcept abstractPredicate, T observation, IContextualizationScope context);

}

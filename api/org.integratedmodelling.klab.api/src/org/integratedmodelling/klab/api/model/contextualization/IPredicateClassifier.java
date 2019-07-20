package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

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
	 * @param observations
	 * @param context
	 * @return true if classification should proceed.
	 */
	public boolean initialize(IObjectArtifact observations, IComputationContext context);

	/**
	 * Classify an individual observation.
	 * 
	 * @param abstractPredicate
	 * @param observation
	 * @param context
	 * @return the concrete subclass of <code>abstractPredicate</code> that the
	 *         observation will get. Null will cause attribution and resolution
	 *         to be canceled for this object.
	 */
	public IConcept classify(IConcept abstractPredicate, T observation, IComputationContext context);

}

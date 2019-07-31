package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateClassifier;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A classifier that defines the predicate to attribute a direct observation through an
 * expression.
 * 
 * @author Ferd
 *
 */
public class ExpressionClassifier implements IPredicateClassifier<IDirectObservation> {

	IConcept abstractPredicate;
	
	@Override
	public Type getType() {
		return IArtifact.Type.CONCEPT;
	}

	@Override
	public boolean initialize(IObjectArtifact observations, IComputationContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConcept classify(IConcept abstractPredicate, IDirectObservation observation, IComputationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}

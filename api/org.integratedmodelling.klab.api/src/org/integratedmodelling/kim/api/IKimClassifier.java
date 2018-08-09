package org.integratedmodelling.kim.api;

import java.util.ArrayList;

import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.Range;

/**
 * Syntactic bean for a k.IM classifier, used in both classifications and
 * lookup tables.
 * 
 * @author ferdinando.villa
 *
 */
public interface IKimClassifier extends IKimStatement {

	boolean isCatchAll();

	boolean isCatchAnything();

	boolean isNegated();

	IKimConcept getConceptMatch();

	Double getNumberMatch();

	Boolean getBooleanMatch();

	ArrayList<IKimClassifier> getClassifierMatches();

	Range getIntervalMatch();

	boolean isNullMatch();

	IKimExpression getExpressionMatch();

	String getStringMatch();

	ArrayList<IKimConcept> getConceptMatches();
	
	/**
	 * The type of the object incarnated by this classifier
	 * 
	 * @return
	 */
	IArtifact.Type getType();

}

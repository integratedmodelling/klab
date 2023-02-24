package org.integratedmodelling.klab.api.data.mediation.classification;

import java.util.List;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;

/**
 * A lookup table matches a table to a lookup strategy expressed as a set of
 * arguments to be matched to columns. It exposes a lookup method using values
 * in a map to be matched to the search arguments.
 * 
 * @author Ferd
 *
 */
public interface KLookupTable extends KDataKey {

	/**
	 * Each argument to be matched can be an id (pointing to a dependency name) or a
	 * concept that is matched to the concrete incarnation of an abstract predicate
	 * resolved in the model.
	 * 
	 * @author Ferd
	 *
	 */
	interface Argument {

		String getId();

		KConcept getConcept();
	}

//	/**
//	 * The table we use for lookup. Classifiers are the most general content type
//	 * for it.
//	 * 
//	 * @return a table. Never null.
//	 */
//	IStructuredTable<IClassifier> getTable();

	/**
	 * The variables we look up. Their number corresponds to the columns in the
	 * table; the special values "?" and "*" denote the search column and any
	 * ignored column. If a concept, it must be an abstract predicate known to the
	 * containing model, and the scope of contextualization must contain its
	 * resolution to a concrete one, which is then passed to the table for matching.
	 * 
	 * @return vars the list of lookup arguments
	 */
	List<Argument> getArguments();

	/**
	 * Lookup an object in the search column by matching the other search fields
	 * with the correspondent values in the passed parameters.
	 * 
	 * @param parameters
	 * @param context contains the observations with the table's identifiers
	 * @param locator to capture states when the parameter is a state
	 * @return the first matching object from the result column, or null
	 */
	Object lookup(KParameters<String> parameters, KContextScope context, KLocator locator);

	/**
	 * The artifact type for the results in the lookup column, which must be
	 * uniform.
	 * 
	 * @return
	 */
	KArtifact.Type getResultType();

	/**
	 * Column providing the results.
	 * 
	 * @return
	 */
	int getResultColumn();

}

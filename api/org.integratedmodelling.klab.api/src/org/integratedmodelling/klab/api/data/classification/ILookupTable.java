package org.integratedmodelling.klab.api.data.classification;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A lookup table matches a table to a lookup strategy expressed as a set of
 * arguments to be matched to columns. It exposes a lookup method using values
 * in a map to be matched to the search arguments.
 * 
 * @author Ferd
 *
 */
public interface ILookupTable extends IDataKey {

	/**
	 * The table we use for lookup. Classifiers are the most general content type
	 * for it.
	 * 
	 * @return a table. Never null.
	 */
	ITable<IClassifier> getTable();

	/**
	 * The variables we look up. Their number corresponds to the columns in the
	 * table; the special values "?" and "*" denote the search column and any
	 * ignored column.
	 * 
	 * @return vars the list of lookup arguments
	 */
	List<String> getArguments();

	/**
	 * Lookup an object in the search column by matching the other search fields
	 * with the correspondent values in the passed parameters.
	 * 
	 * @param parameters
	 * @param context
	 * @return the first matching object from the result column, or null
	 */
	Object lookup(IParameters<String> parameters, IComputationContext context);

	/**
	 * The artifact type for the results in the lookup column, which must be 
	 * uniform.
	 * 
	 * @return
	 */
	Type getResultType();

}

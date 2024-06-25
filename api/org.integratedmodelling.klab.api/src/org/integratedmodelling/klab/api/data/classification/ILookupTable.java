package org.integratedmodelling.klab.api.data.classification;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

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
     * Each argument to be matched can be an id (pointing to a dependency name) or a
     * concept that is matched to the concrete incarnation of an abstract predicate
     * resolved in the model.
     * 
     * @author Ferd
     *
     */
    interface Argument {

        String getId();

        IConcept getConcept();
    }

    /**
     * The table we use for lookup. Classifiers are the most general content type
     * for it.
     * 
     * @return a table. Never null.
     */
    IStructuredTable<IClassifier> getTable();

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
     * Return the concept associated to the keys index
     * @param index the index in the keys list
     * @return the associated concept
     */
    IConcept getConcept(int index);

    /**
     * Lookup an object in the search column by matching the other search fields
     * with the correspondent values in the passed parameters.
     * 
     * @param parameters
     * @param context contains the observations with the table's identifiers
     * @param locator to capture states when the parameter is a state
     * @return the first matching object from the result column, or null
     */
    Object lookup(IParameters<String> parameters, IContextualizationScope context, ILocator locator);

    /**
     * The artifact type for the results in the lookup column, which must be
     * uniform.
     * 
     * @return
     */
    Type getResultType();

    /**
     * Column providing the results.
     * 
     * @return
     */
    int getResultColumn();

}

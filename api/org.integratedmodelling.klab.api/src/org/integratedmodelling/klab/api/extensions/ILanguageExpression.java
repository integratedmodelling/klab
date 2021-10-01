package org.integratedmodelling.klab.api.extensions;

import java.util.Collection;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A compiled expression used in an expression language. It adds
 * wrapping/unwrapping and overriding methods and methods to simplify calling
 * from the API.
 * 
 * @author Ferd
 *
 */
public interface ILanguageExpression extends IExpression {

	/**
	 * Return the language we're in.
	 * 
	 * @return
	 */
	public String getLanguage();

	/**
	 * Pass a context and parameters using a varargs argument. If a parameter is a
	 * IParameters object, use it as is, otherwise pair with the next and use as
	 * key/value pair.
	 * 
	 * @param scope
	 * @param parameters
	 * @return the value after evaluation with passed parameters.
	 */
	Object eval(IContextualizationScope scope, Object... parameters);

	/**
	 * Return all the identifiers referenced in the expression. This may be limited
	 * to known identifiers passed before compilation to the expression context.
	 * 
	 * @return
	 */
	Collection<String> getIdentifiers();

	/**
	 * Unwrap an object from an expression result if needed.
	 * 
	 * @param object
	 * @return
	 * @deprecated shouldn't be in API
	 */
	Object unwrap(Object object);

	/**
	 * Override the passed variables using key/value pairs. Used to quickly change
	 * values before evaluation without wrapping when expressions are called many
	 * times. Variables are only overridden if they were placed in the set of
	 * overriddable ones before, which is done by the implementation.
	 * 
	 * @param variables
	 * @return this for chaining
	 * @deprecated shouldn't be in API
	 */
	ILanguageExpression override(Object... variables);

}

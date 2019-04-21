package org.integratedmodelling.klab.api.extensions;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A compiled expression used in an expression language. It adds wrapping/unwrapping and overriding methods and methods to
 * simplify calling from the API.
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
     * Pass a context and parameters using a varargs argument. If a parameter is
     * a IParameters object, use it as is, otherwise pair with the next and use
     * as key/value pair.
     * 
     * @param context
     * @param parameters
     * @return the value after evaluation with passed parameters.
     */
    Object eval(IComputationContext context, Object... parameters);

    /**
     * Wrap the object if necessary for use within the language. Only necessary if the
     * language is run by a JVM.
     * 
     * @param object
     * @return
     */
    Object wrap(Object object);

    /**
     * Unwrap an object from an expression result if needed.
     * 
     * @param object
     * @return
     */
    Object unwrap(Object object);

    /**
     * Override the passed variables using key/value pairs. Used to quickly change 
     * values before evaluation without wrapping when expressions are called many times. Variables
     * are only overridden if they were placed in the set of overriddable ones
     * before, which is done by the implementation.
     * 
     * @param variables
     * @return this for chaining
     */
    ILanguageExpression override(Object... variables);

}

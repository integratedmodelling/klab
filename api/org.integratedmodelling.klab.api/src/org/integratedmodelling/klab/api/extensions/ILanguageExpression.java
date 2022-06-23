package org.integratedmodelling.klab.api.extensions;

import java.util.Collection;

import org.integratedmodelling.klab.api.data.general.IExpression;

/**
 * A compiled expression used in an expression language. It adds wrapping/unwrapping and overriding
 * methods and methods to simplify calling from the API.
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
     * Return all the identifiers referenced in the expression. This may be limited to known
     * identifiers passed before compilation to the expression context.
     * 
     * @return
     */
    Collection<String> getIdentifiers();

}

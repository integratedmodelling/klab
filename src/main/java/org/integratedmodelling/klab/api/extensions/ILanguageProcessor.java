package org.integratedmodelling.klab.api.extensions;

import java.util.Collection;
import javax.annotation.Nullable;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * A processor for a supported expression language to be used in k.IM expressions. The 
 * k.LAB implementation must provide a default, accessible through 
 * 
 * @author Ferd
 *
 */
public interface ILanguageProcessor {

  /**
   * 
   * @author Ferd
   *
   */
  interface Descriptor {
    
    /**
     * Return all identifiers detected.
     * 
     * @return set of identifiers 
     */
    Collection<String> getIdentifiers();

    /**
     * Return true if the expression contains scalar usage for the
     * passed identifiers within a transition (i.e. used alone or with locator semantics for
     * space or other non-temporal domain). 
     * 
     * @param identifier identifiers representing states
     * 
     * @return true if the identifier is used in a scalar context.
     */
    boolean isScalar(String identifier);

    
    /**
     * Return true if the expression contains scalar usage for any of the
     * passed identifiers within a transition (i.e. used alone or with locator semantics for
     * space or other non-temporal domain). 
     * 
     * @param stateIdentifiers identifiers representing states
     * 
     * @return true if any of the identifiers is used in a scalar context.
     */
    boolean isScalar(Collection<String> stateIdentifiers);

    /**
     * In order to avoid duplicated action, the descriptor alone must be enough to
     * compile the expression. If we have a valid descriptor the returned expression
     * must be valid so no exceptions are thrown unless the descriptor has errors,
     * which causes an IllegalArgumentException.
     * 
     * @return a compiled expression ready for execution in the context that produced the descriptor
     * @throws IllegalArgumentException if the descriptor has errors
     */
    IExpression compile();
  }
  
  /**
   * Compile the expression in the passed context, which may be null.
   * 
   * @param expression
   * @param context
   * @return the compiled expression
   * @throws KlabValidationException if compilation produces any errors
   */
  IExpression compile(String expression, @Nullable IComputationContext context) throws KlabValidationException;

  /**
   * Preprocess an expression and return the descriptor. The context may be null.
   * 
   * @param expression
   * @param context 
   * @return a preprocessed descriptor, which must be enough to produce an IExpression on request.
   * @throws KlabValidationException if the expression contains syntax of logical errors
   */
  Descriptor describe(String expression, @Nullable IComputationContext context) throws KlabValidationException;

  /**
   * Assume that the passed expression evaluates to a boolean and produce the language equivalent of its
   * negation.
   * 
   * @param expression
   * @return another expression producing the opposite truth value as the original
   */
  String negate(String expression);
  
}

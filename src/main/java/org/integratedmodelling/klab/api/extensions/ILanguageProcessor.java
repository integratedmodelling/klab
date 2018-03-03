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
     * Return true if the expression contains scalar usage for any of the
     * passed identifiers within a transition (i.e. used alone or with locator semantics for
     * space or other non-temporal domain). 
     * 
     * @param stateIdentifiers identifiers representing states
     * 
     * @return true if any of the identifiers is used in a scalar context.
     */
    boolean isScalar(Collection<String> stateIdentifiers);
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
   * In order to avoid duplicated action, the descriptor alone must be enough to
   * compile the expression. If we have a valid descriptor the returned expression
   * must be valid so no exceptions are thrown unless a descriptor with errors is passed,
   * which causes an IllegalArgumentException.
   * 
   * @param expressionDescriptor
   * @return the descriptor
   * @throws IllegalArgumentException if the descriptor has errors
   */
  IExpression compile(Descriptor expressionDescriptor);

  /**
   * Preprocess an expression and return the descriptor. The context may be null.
   * 
   * @param expression
   * @param context 
   * @return a preprocessed descriptor, which must be enough to produce an IExpression on request.
   * @throws KlabValidationException if the expression contains syntax of logical errors
   */
  IExpression describe(String expression, @Nullable IComputationContext context) throws KlabValidationException;
  
}

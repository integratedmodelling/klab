package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.kim.api.IParameters;

/**
 * Wraps a value or an expression that was specified in a model using 'set to'
 * on an undeclared name. These are passed to any contextualizer that may use
 * them by adding them to the contextualization scope for the actuator they are
 * part of. All they have is a get() method, which enables lazy execution of
 * those expressions that depend on the fine-grained context or on previous
 * variables.
 * 
 * @author Ferd
 *
 */
public interface IVariable {

	/**
	 * Return or compute the value.
	 * @return
	 */
	Object getValue(IContextualizationScope scope, IParameters<String> parameters);
	
}

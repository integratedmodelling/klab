package org.integratedmodelling.klab.api.testing;

import java.util.List;

import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * An assertion is an object that takes a list of targets and checks conditions
 * on them, returning true (OK) or false. Exceptions are equivalent to returning
 * false, with an additional message. These should be created by functions defined
 * within lists of assertions in the test annotations.
 * 
 * @author ferdinando.villa
 *
 */
public interface Assertion {

	/**
	 * Evaluate all conditions.
	 * 
	 * @param targets
	 * @param parameters
	 * @param scope
	 * @return true if OK, false if fail
	 * @throws KlabValidationException
	 *             for any error that can be described better than with just a fail
	 *             message.
	 */
	boolean evaluate(List<String> targets, Parameters<String> parameters, IContextualizationScope scope)
			throws KlabValidationException;
	
	/**
	 * If anything worth noticing happened, the details should be returned here. If nothing needs reporting,
	 * return an empty list.
	 * 
	 * @return any details about the assertion
	 */
	List<String> getDetails();

}

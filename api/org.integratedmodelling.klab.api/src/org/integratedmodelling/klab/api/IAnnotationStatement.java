package org.integratedmodelling.klab.api;

import java.util.Collection;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * The syntactic peer for an annotation. Extends parameters and allows 
 * named and unnamed parameters.
 * 
 * @author Ferd
 *
 */
public interface IAnnotationStatement extends IParameters<String> {

	/**
	 * If the function does not have named parameters, any parameters passed are
	 * named like this; if there is more than one parameter they will be grouped
	 * into a list returned with this key.
	 */
	public static String DEFAULT_PARAMETER_NAME = "value";

	/**
	 * Name of the function being called. Never null.
	 * 
	 * @return the function name
	 */
	String getName();

	/**
	 * Number of <i>user</i> parameters. May be different from getParameters().size().
	 * 
	 * @return
	 */
	int getParameterCount();

	/**
	 * Prototype. May be null if the annotation is unknown.
	 * 
	 * @return
	 */
	IPrototype getPrototype();
	
	/**
	 * Return any parameter IDs that were passed with a syntax that defines those
	 * that can be changed by the user.
	 * 
	 * @return
	 */
	Collection<String> getInteractiveParameters();

}

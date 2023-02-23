package org.integratedmodelling.klab.api.lang;

import java.util.Collection;

import org.integratedmodelling.klab.api.collections.IParameters;

/**
 * A parsed function call. Parameters can be named by user or by default.
 * 
 * @author Ferd
 *
 */
public interface IServiceCall {

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
	 * Parameters passed to the call. If a function call was passed, it is invoked
	 * before this is returned.
	 * 
	 * @return the parameters for the call. See {@link #DEFAULT_PARAMETER_NAME} for
	 *         naming rules if no names are given.
	 */
	IParameters<String> getParameters();
	
	/**
	 * Number of <i>user</i> parameters. May be different from getParameters().size().
	 * 
	 * @return
	 */
	int getParameterCount();

	/**
	 * Prototype. May be null if the function is unknown.
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

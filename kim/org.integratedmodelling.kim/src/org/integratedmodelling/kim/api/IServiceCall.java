package org.integratedmodelling.kim.api;

import org.integratedmodelling.kim.utils.Parameters;

/**
 * A parsed function call. Parameters can be named by user or by default.
 * 
 * @author Ferd
 *
 */
public interface IServiceCall extends IKimStatement {

	/**
	 * If the function does not have named parameters, any parameters passed are
	 * named like this; if there is more than one parameter, the ones after the
	 * first are named DEFAULT_PARAMETER_NAME_n with N starting at 1.
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
	Parameters getParameters();

}

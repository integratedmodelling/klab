package org.integratedmodelling.klab.api.lang.kactors;

import java.util.List;

/**
 * Syntactic peer for an action in a behavior.
 * 
 * @author Ferd
 *
 */
public interface KKActorsAction extends KKActorsCodeStatement {

	String getName();

	/**
	 * The code for the action, most likely a group at this level.
	 * 
	 * @return
	 */
	KKActorsStatement getCode();

	/**
	 * Any formal argument names declared for the action, to be matched to actual
	 * parameters.
	 * 
	 * @return
	 */
	List<String> getArgumentNames();

	/**
	 * If this returns true, the action was declared as 'function' and is expected
	 * to return a value and exit, instead of firing it and continuing.
	 * 
	 * @return
	 */
	boolean isFunction();
}

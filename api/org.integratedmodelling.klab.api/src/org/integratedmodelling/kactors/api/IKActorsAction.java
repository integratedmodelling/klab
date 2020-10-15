package org.integratedmodelling.kactors.api;

import java.util.List;

/**
 * Syntactic peer for an action in a behavior.
 * 
 * @author Ferd
 *
 */
public interface IKActorsAction extends IKActorsCodeStatement {

	String getName();

	/**
	 * The code for the action, most likely a group at this level.
	 * 
	 * @return
	 */
	IKActorsStatement getCode();

	/**
	 * Any formal argument names declared for the action, to be matched to
	 * actual parameters.
	 * 
	 * @return
	 */
	List<String> getArgumentNames();
}

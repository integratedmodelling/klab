package org.integratedmodelling.kactors.api;

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
}

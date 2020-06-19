package org.integratedmodelling.kactors.api;

/**
 * Syntactic peer for an action in a behavior.
 * 
 * @author Ferd
 *
 */
public interface IKActorsAction extends IKActorsCodeStatement {

	enum Type {
		/**
		 * A "pure" behavior action, expected to fire and finish without side effects
		 */
		ACTION,
		/**
		 * An action that will create a "peer" actor when executed, which will remain
		 * active and may fire repeatedly until stopped.
		 */
		ACTOR,
		/**
		 * An action that will create an actor controllable through an MVC pattern.
		 */
		COMPONENT
	}
	
	Type getType();

	String getName();

	/**
	 * The code for the action, most likely a group at this level.
	 * 
	 * @return
	 */
	IKActorsStatement getCode();
}

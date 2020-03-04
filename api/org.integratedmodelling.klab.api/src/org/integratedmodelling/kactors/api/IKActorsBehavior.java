package org.integratedmodelling.kactors.api;

import java.io.File;

import org.integratedmodelling.klab.api.actors.IBehavior;

/**
 * The syntactic peer resulting from parsing a .kactor file. Specifies a
 * {@link IBehavior} to be used in the k.LAB network.
 * 
 * @author Ferd
 *
 */
public interface IKActorsBehavior extends IKActorsStatement {

	enum Type {
		/**
		 * The behavior defines an observed actor
		 */
		BEHAVIOR,
		/**
		 * The behavior will be incorporated in a session actor, creating a
		 * session-level application
		 */
		APP,
		/**
		 * The behavior will be incorporated in a user actor, intercepting any calls
		 * that won't make it to other actors.
		 */
		USER,
		/**
		 * The behavior is a collection of actions to be incorporated in another actor
		 * definition as a collection of traits ("personality"). In an app context it
		 * can simply be declared as a "library".
		 */
		TRAITS
	}

	/**
	 * The fully qualified namespace ID for the behavior.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Who this behavior is for
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * Where it comes from. Always a file resource for now.
	 * 
	 * @return
	 */
	File getFile();
	
	

}

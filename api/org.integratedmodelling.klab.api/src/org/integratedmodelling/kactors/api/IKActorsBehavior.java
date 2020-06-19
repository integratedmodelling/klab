package org.integratedmodelling.kactors.api;

import java.io.File;
import java.util.List;

import org.integratedmodelling.klab.api.actors.IBehavior;

/**
 * The syntactic peer resulting from parsing a .kactor file. Specifies a
 * {@link IBehavior} to be used in the k.LAB network.
 * 
 * @author Ferd
 *
 */
public interface IKActorsBehavior extends IKActorsCodeStatement {

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
		TRAITS,

		/**
		 * The behavior is an app defining a collection of annotated unit tests
		 */
		UNITTEST
	}

	enum Platform {
		ANY, DESKTOP, WEB, MOBILE
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
	 * If this is an app, return the platform this is specialized for, which may be
	 * ANY. For any other behavior, return ANY.
	 * 
	 * @return
	 */
	Platform getPlatform();

	/**
	 * Where it comes from. Always a file resource for now.
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * All behaviors imported, resolved and parsed.
	 * 
	 * @return
	 */
	List<IKActorsBehavior> getImports();

	/**
	 * All the actions declared in this behavior (not in the imported ones)
	 * 
	 * @return
	 */
	List<IKActorsAction> getActions();

	/**
	 * If a style is specified in the preamble, return it here.
	 * 
	 * @return
	 */
	String getStyle();

	/**
	 * Label (docstring). This should/could be in metadata but we still have some
	 * API weirdness with IKimMetadata being cumbersome.
	 * 
	 * @return
	 */
	String getLabel();

	/**
	 * Description (as per preamble). This should/could be in metadata but we still have some
	 * API weirdness with IKimMetadata being cumbersome.
	 * 
	 * @return
	 */
	String getDescription();
	
	/**
	 * If a logo pathname (relative to the application) has been specified, return it.
	 * 
	 * @return
	 */
	String getLogo();
	
	/**
	 * Name of project we are declared into. Null if not in a project.
	 * 
	 * @return
	 */
	String getProjectId();

}

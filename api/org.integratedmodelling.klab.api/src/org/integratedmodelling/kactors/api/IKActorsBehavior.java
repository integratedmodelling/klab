package org.integratedmodelling.kactors.api;

import java.io.File;
import java.util.List;
import java.util.Map;

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
		 * The behavior defines an observed actor. Normally bound to observations
		 * through a k.IM bind annotation.
		 */
		BEHAVIOR,
		/**
		 * The behavior will be incorporated in a session actor, creating a
		 * session-level application. Apps can only be run directly through CLI,
		 * drag/drop (modeler) or URL identification (explorer) and may access project
		 * resources (such as logos) from the apps directory.
		 */
		APP,
		/**
		 * The behavior will be incorporated in a user actor, intercepting any calls
		 * that won't make it to other actors. A user actor definition is the only
		 * k.Actors resource that must be located outside of a project, typically in a
		 * user profile and saved to the k.LAB data directory.
		 */
		USER,
		/**
		 * The behavior is a collection of actions to be incorporated in another actor
		 * definition as a collection of traits ("personality"). In an app context it
		 * can simply be declared as a "library". Traits are imported using the 'import'
		 * clause in the k.Actors preamble, or explicitly with a system action; they
		 * cannot be bound to anything directly.
		 */
		TRAITS,

		/**
		 * The behavior is an app defining a collection of annotated unit tests. Can
		 * only be run directly and explicitly.
		 */
		UNITTEST,

		/**
		 * A component is an actor that should be created only by other actors and
		 * normally provides a piece of behavior including UI elements, or anything else
		 * that makes it "composable". The system will reject any bindings to components
		 * and only let this be created using the 'new' verb in apps.
		 */
		COMPONENT,

		/**
		 * A task is a batch job run by a session without user involvement. It must have
		 * a main and can only be run from the IDE, CLI or through an engine launched
		 * with the task URN as an option (which will run the task and then exit).
		 */
		TASK
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
	 * Description (as per preamble). This should/could be in metadata but we still
	 * have some API weirdness with IKimMetadata being cumbersome.
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * If a logo pathname (relative to the application) has been specified, return
	 * it.
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

	/**
	 * Optional map of style specification (CSS-like) encoded in k.Actors as a map
	 * after "style [<name> with] #{ ... }", all values and keys converted to
	 * strings.
	 * 
	 * @return
	 */
	Map<String, String> getStyleSpecs();

	/**
	 * True if 'public' was specified in front of the declaration, which makes the
	 * behavior visible to clients before it is run. Only behaviors with getType()
	 * == Type.APP can be public.
	 * 
	 * @return
	 */
	boolean isPublic();

}
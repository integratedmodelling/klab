package org.integratedmodelling.kactors.api;

import java.util.List;

import org.integratedmodelling.klab.api.model.IAnnotation;

/**
 * An actor in a k.Actors application. An application is simply a list of these
 * with a few additional metadata.
 * 
 * @author Ferd
 *
 */
public interface IKactor  extends IKactorsStatement {

	/**
	 * The name is only non-null in top-level actors.
	 * 
	 * @return the name or null. Sorry, I hate Optional.
	 */
	String getName();

	/**
	 * 
	 * @return
	 */
	boolean isSystem();

	/**
	 * 
	 * @return
	 */
	boolean isView();

	/**
	 * True if actors are concurrent. Note that it will say no if it contains
	 * concurrent groups but is not defined as concurrent.
	 * 
	 * @return
	 */
	boolean isConcurrent();

	/**
	 * A composite actor has actors and no actions.
	 * 
	 * @return the actors or an empty list.
	 */
	List<IKactor> getActors();

	/**
	 * A simple actor has actions and no actors.
	 * 
	 * @return the actions or an empty list.
	 */
	List<IKaction> getActions();

	/**
	 * An empty actor has no actions and no actors.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 
	 * @return
	 */
	List<IAnnotation> getAnnotations();

}

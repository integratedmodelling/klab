package org.integratedmodelling.klab.api.actors;

import java.util.List;
import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IKimObject;

/**
 * The result of parsing an actor specification within the engine. Provides
 * operational configuration for all k.LAB actors.
 * 
 * @author Ferd
 *
 */
public interface IBehavior extends IKimObject {

	public interface Action extends IKimObject {

		@Override
		IKActorsAction getStatement();

	}

	@Override
	IKActorsBehavior getStatement();

	/**
	 * Metadata, following the (forthcoming) actor-specific schema in
	 * IMetadata.Schema.
	 * 
	 * @return
	 */
	IMetadata getMetadata();

	/**
	 * Who this is for.
	 * 
	 * @return
	 */
	IKActorsBehavior.Type getDestination();

	/**
	 * Get all the actions in order of declaration. If a string is passed, match it
	 * to the action name; if it starts with "@", match it to the ID of annotations
	 * associated to it. All parameters are in OR.
	 *
	 * 
	 * @return
	 */
	List<Action> getActions(String... match);

	/**
	 * Get an action by name.
	 * 
	 * @param actionId local name of the action (no path)
	 * @return the action or null
	 */
	Action getAction(String actionId);

}

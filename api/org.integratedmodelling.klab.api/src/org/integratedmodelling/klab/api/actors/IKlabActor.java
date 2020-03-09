package org.integratedmodelling.klab.api.actors;

import org.integratedmodelling.klab.api.auth.IIdentity;

/**
 * Tag (for now) interface that is adopted by anything in k.LAB that can have a
 * behavior associated with it. It extends any IIdentity that is enabled to have
 * behavior and interact - eventually probably all identities.
 * 
 * @author Ferd
 *
 */
public interface IKlabActor extends IIdentity {

	/**
	 * An empty actor does nothing and quietly ignores any message sent to it. An
	 * identity that has not been initialized as an actor but implements this API
	 * should return true but give no error when message() is called.
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Load the passed behavior, adding to the existing one if it was called before.
	 * The base implementations may set native behaviors independent of the
	 * specifications. Identities without agent initialization should transparently
	 * create the appropriate implementation when this is called.
	 * 
	 * @param behavior
	 */
	void loadBehavior(IBehavior behavior);

	/**
	 * Send a message to the actor. Messages respond asynchronously and generate
	 * nothing at the level of the calling API. Notification of other actors to
	 * interact with is also done with messages.
	 * 
	 * @param arguments
	 */
	void message(Object... arguments);

}

package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;

/**
 * Messages not accessible to users but necessary to enable the base runtime
 * actor communication protocols. Includes spawning appropriate child actors,
 * loading behavior files, firing things and sending k.Actors message calls.
 * Their initialize() method won't be called.
 * 
 * @author Ferd
 *
 */
public class SystemBehavior {

	/**
	 * Load a behavior
	 * 
	 * @author Ferd
	 *
	 */
	public static class Load implements KlabMessage {

		String behavior;
		IRuntimeScope scope;
		String appId;
		// if not null, this is a child behavior from a 'new' instruction and it carries
		// a ref to the parent
		ActorRef<KlabMessage> parent = null;

		public Load(String behavior, String appId, IRuntimeScope scope) {
			this.behavior = behavior;
			this.appId = appId;
			this.scope = scope;
		}

		public Load withParent(ActorRef<KlabMessage> parent) {
			this.parent = parent;
			return this;
		}

		@Override
		public Load direct() {
			return new Load(behavior, null, scope);
		}
	}

	/**
	 * Setup a view component with view actions
	 * 
	 * @author Ferd
	 *
	 */
	public static class SetView implements KlabMessage {

		ViewComponent component;

		public SetView(ViewComponent component) {
			this.component = component;
		}

		@Override
		public SetView direct() {
			throw new KlabIllegalStateException("Actors shouldn't stop themselves.");
		}
	}

	/**
	 * Load a behavior
	 * 
	 * @author Ferd
	 *
	 */
	public static class Stop implements KlabMessage {

		String appId;

		public Stop(String appId) {
			this.appId = appId;
		}

		@Override
		public Stop direct() {
			throw new KlabIllegalStateException("Actors shouldn't stop themselves.");
		}
	}

	/**
	 * Notify a user action from a view to the actor that must process it as a
	 * message.
	 * 
	 * @author Ferd
	 *
	 */
	public static class UserAction implements KlabMessage {

		ViewAction action;
		IRuntimeScope scope;
		String appId;

		public UserAction(ViewAction action, String appId, IRuntimeScope scope) {
			this.action = action;
			this.appId = appId;
			this.scope = scope;
		}

		@Override
		public UserAction direct() {
			return new UserAction(action, null, scope);
		}
	}

	/**
	 * Bind an action's notification ID to the ID of a component in the associated
	 * view, so that matches can be triggered from the view even if the view was
	 * built before the action existed.
	 * 
	 * @author Ferd
	 *
	 */
	public static class BindUserAction implements KlabMessage {

		long notifyId;
		String componentId;
		String appId;

		public BindUserAction(long notifyId, String appId, String componentId) {
			this.notifyId = notifyId;
			this.componentId = componentId;
			this.appId = appId;
		}

		@Override
		public BindUserAction direct() {
			return new BindUserAction(notifyId, null, componentId);
		}
	}

	/**
	 * Sent before stopping to ensure that listeners are unregistered and any other
	 * cleanup operations are performed.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Cleanup implements KlabMessage {

		@Override
		public KlabMessage direct() {
			return null;
		}

	}

	/**
	 * Report a temporal transition
	 * 
	 * @author Ferd
	 *
	 */
	public static class Transition implements KlabMessage {

		KlabActor.Scope scope;
		String appId;

		public Transition(String appId, KlabActor.Scope scope) {
			this.scope = scope;
			this.appId = appId;
		}

		@Override
		public Transition direct() {
			return new Transition(null, scope);
		}
	}

	/**
	 * Spawn an appropriate child actor.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Spawn implements KlabMessage {

		IActorIdentity<KlabMessage> identity;
		String appId;

		public Spawn(IActorIdentity<KlabMessage> identity, String appId) {
			this.identity = identity;
			this.appId = appId;
		}

		@Override
		public Spawn direct() {
			return new Spawn(identity, null);
		}

	}

	/**
	 * The message sent back to a listening actor when an actor fires, triggering
	 * pattern matching. If finalize == true, the listener in the actor must be
	 * removed as the sending actor won't fire again.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Fire implements KlabMessage {

		Object value;
		boolean finalize;
		Long listenerId;
		String appId;

		public Fire(Long listenerId, Object firedValue, boolean isFinal, String appId) {
			this.value = firedValue;
			this.finalize = isFinal;
			this.listenerId = listenerId;
			this.appId = appId;
		}

		@Override
		public String toString() {
			return "[FIRE" + value + " @" + listenerId + "]";
		}

		@Override
		public Fire direct() {
			return new Fire(listenerId, value, finalize, null);
		}

	}

	/**
	 * The message sent back to a listening actor when a child component fires,
	 * triggering pattern matching on the actions installed after the 'new' action
	 * that created it.
	 * 
	 * @author Ferd
	 *
	 */
	public static class ComponentFire implements KlabMessage {

		Object value;
		boolean finalize;
		String listenerId;
		ActorRef<KlabMessage> child;

		public ComponentFire(String listenerId, Object firedValue, ActorRef<KlabMessage> child) {
			this.value = firedValue;
			this.listenerId = listenerId;
			this.child = child;
		}

		@Override
		public String toString() {
			return "[COMPONENT FIRE" + value + " @" + listenerId + "]";
		}

		@Override
		public ComponentFire direct() {
			return new ComponentFire(listenerId, value, child);
		}

	}

	/**
	 * The shuttle for a k.Actors message call. Always comes from a k.Actors
	 * behavior, sent by an actor to another.
	 * 
	 * @author Ferd
	 *
	 */
	public static class KActorsMessage implements KlabMessage {

		ActorRef<KlabMessage> sender;
		String message;
		String receiver;
		IParameters<String> arguments = Parameters.create();
		KlabActor.Scope scope;
		String appId;
		// for caching
		String actionInternalId;

		public KActorsMessage(ActorRef<KlabMessage> sender, String receiver, String actionId, String actionInternalId,
				IParameters<String> arguments, KlabActor.Scope scope, String appId) {

			this.sender = sender;
			this.receiver = receiver;
			this.message = actionId;
			this.actionInternalId = actionInternalId;
			if (arguments != null) {
				this.arguments.putAll(arguments);
			}
			this.scope = scope;
			this.appId = appId;
		}

		@Override
		public String toString() {
			return "[" + message + " @" + scope + "]";
		}

		@Override
		public KActorsMessage direct() {
			return new KActorsMessage(sender, receiver, message, actionInternalId, arguments, scope, null);
		}

	}
}

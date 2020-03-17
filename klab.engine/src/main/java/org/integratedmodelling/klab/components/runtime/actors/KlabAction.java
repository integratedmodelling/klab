package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

import akka.actor.typed.ActorRef;

/**
 * Abstract class for system actions written in Java and defined within behavior
 * classes. System will store these based {@link Behavior} and {@link Action}
 * annotations. All must have the same constructor.
 * 
 * @author Ferd
 *
 */
public abstract class KlabAction {

	protected ActorRef<KlabMessage> sender;
	protected IParameters<String> arguments;
	protected KlabActor.Scope scope;
	protected IActorIdentity<KlabMessage> identity;
	
	protected final Boolean DEFAULT_FIRE = Boolean.TRUE;

	public KlabAction(IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
			KlabActor.Scope scope) {
		this.sender = identity.getActor();
		this.arguments = arguments;
		this.scope = scope;
		this.identity = identity;
	}

	public void fire(Object value, boolean isFinal) {
		this.sender.tell(new Fire(scope.listenerId, value, isFinal));
	}

	abstract void run();

}

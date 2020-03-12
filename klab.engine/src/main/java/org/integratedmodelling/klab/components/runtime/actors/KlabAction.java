package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;

import akka.actor.typed.ActorRef;

/**
 * Abstract class for system actions written in Java and defined within behavior
 * classes. System will store these based {@link Behavior} and {@link Action}
 * annotations. All must have the same constructor.
 * 
 * @author Ferd
 *
 */
public class KlabAction {

	private ActorRef<KlabMessage> sender;
	protected IParameters<String> arguments;
	private String messageId;

	public KlabAction(ActorRef<KlabMessage> sender, IParameters<String> arguments, String messageId) {
		this.sender = sender;
		this.arguments = arguments;
		this.messageId = messageId;
	}

	public void fire(Object value, boolean isFinal) {
		this.sender.tell(new Fire(messageId, value, isFinal));
	}

}

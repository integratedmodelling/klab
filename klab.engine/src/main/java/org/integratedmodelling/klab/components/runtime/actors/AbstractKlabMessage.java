package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;

/**
 * Base class for all k.Actors messages, ensuring
 * 
 * @author Ferd
 *
 */
public abstract class AbstractKlabMessage implements KlabMessage {

	/*
	 * if this isn't null, something is listening for this message and we must send
	 * any fired reply to the sender actor.
	 */
	protected String id = null;

	@Override
	public String getId() {
		return this.id;
	}

	public KlabMessage withId(String notifyId) {
		this.id = notifyId;
		return this;
	}

}

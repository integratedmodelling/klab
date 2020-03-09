package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.utils.NameGenerator;

/**
 * Base class for all k.Actors messages, ensuring 
 * 
 * @author Ferd
 *
 */
public abstract class AbstractKlabMessage implements KlabMessage {

	private String id = NameGenerator.shortUUID();
	
	@Override
	public String getId() {
		return this.id;
	}

}

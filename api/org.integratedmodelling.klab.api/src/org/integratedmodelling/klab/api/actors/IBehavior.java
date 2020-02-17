package org.integratedmodelling.klab.api.actors;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.INamespaceQualified;

/**
 * The result of parsing an actor specification within the engine. Provides
 * operational configuration for all k.LAB actors.
 * 
 * @author Ferd
 *
 */
public interface IBehavior extends IKimObject, INamespaceQualified {

	enum Destination {
		/**
		 * The behavior will be incorporated in an observed actor
		 */
		ACTOR,
		/**
		 * The behavior will be incorporated in a session actor
		 */
		SESSION,
		/**
		 * The behavior will be incorporated in a user actor
		 */
		USER,
		/**
		 * The behavior is a collection of messages to be incorporated in another actor
		 * definition
		 */
		LIBRARY
	}

	/**
	 * Who this behavior is for
	 * 
	 * @return
	 */
	Destination getDestination();

	/**
	 * All messages defined in the actor.
	 * 
	 * @return
	 */
	List<IMessageHandler> getMessageHandlers();

	/**
	 * Metadata, following the (forthcoming) actor-specific schema in
	 * IMetadata.Schema.
	 * 
	 * @return
	 */
	IMetadata getMetadata();

}

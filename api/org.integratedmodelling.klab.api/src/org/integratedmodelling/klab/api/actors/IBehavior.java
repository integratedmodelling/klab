package org.integratedmodelling.klab.api.actors;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
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
	
	/**
	 * Who this is for.
	 * 
	 * @return
	 */
	IKActorsBehavior.Type getDestination();

}

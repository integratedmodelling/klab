package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.SpatialExtent;

/**
 * Front-end receiver for the web explorer.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabExplorer extends KlabPeer {

	String sessionId;

	public KlabExplorer(String sessionId) {
		super(Sender.EXPLORER);
		this.sessionId = sessionId;
	}

	@MessageHandler(type = IMessage.Type.RegionOfInterest)
	public void setRegionOfInterest(IMessage message, SpatialExtent extent) {
		send(message);
	}

}

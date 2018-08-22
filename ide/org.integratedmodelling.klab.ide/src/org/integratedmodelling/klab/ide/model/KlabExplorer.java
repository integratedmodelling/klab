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

	public KlabExplorer(String relayId) {
		super(Sender.EXPLORER, relayId);
	}

	@MessageHandler(type = IMessage.Type.RegionOfInterest)
	public void setRegionOfInterest(IMessage message, SpatialExtent extent) {
		send(message);
	}
	
	@MessageHandler(type = IMessage.Type.ResetContext)
	public void resetContext(IMessage message, String dummyPayload) {
		send(message);
	}

}

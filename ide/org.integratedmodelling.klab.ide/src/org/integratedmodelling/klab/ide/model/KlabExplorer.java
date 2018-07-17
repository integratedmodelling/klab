package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.SpatialExtent;

/**
 * Front-end receiver for the web explorer.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabExplorer {

	String sessionId;

	public KlabExplorer(String sessionId) {
		this.sessionId = sessionId;
	}

	@MessageHandler
	public void setRegionOfInterest(SpatialExtent extent) {
		System.out.println("Spatial extent: " + extent);
	}

}

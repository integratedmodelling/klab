package org.integratedmodelling.klab.ide.kim;

import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SpatialExtent;

/**
 * The session is just the designated front-end receiver of each message that comes from
 * the back end.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession {

	String sessionId;
	
	public KlabSession(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@MessageHandler
	public void setRegionOfInterest(SpatialExtent extent) {
		System.out.println("Set spatial extent: " + extent);
	}

	@MessageHandler
	public void handleSearchResponse(SearchResponse response) {
		System.out.println("Search response: " + response);
	}
	
}

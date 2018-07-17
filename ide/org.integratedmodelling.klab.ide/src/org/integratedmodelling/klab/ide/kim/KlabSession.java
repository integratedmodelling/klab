package org.integratedmodelling.klab.ide.kim;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.SearchResponse;

/**
 * Front-end receiver for session messages.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession {

	String sessionId;
	
	public KlabSession(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@MessageHandler(messageClass=IMessage.MessageClass.Notification)
	public void handleNotification(String string, IMessage.Type type) {
		System.out.println("NOTIFICATION " + type + ": " + string);
		// TODO
	}
	
	@MessageHandler
	public void handleSearchResponse(SearchResponse response) {
		System.out.println("Search response: " + response);
	}
	
}

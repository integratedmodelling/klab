package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.CompilationResult;

public class KlabEngine extends KlabPeer {
	
	public KlabEngine(String identity) {
		super(Sender.ENGINE, identity);
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notificatgion) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
	}
	
	@MessageHandler
	public void handleNotification(CompilationResult report) {
	    System.out.println("GOT COMPILE ISSUES: " + report);
	}

}

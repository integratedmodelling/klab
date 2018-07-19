package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;

public class KlabEngine {
	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(String string, IMessage.Type type) {
		System.out.println("ENGINE NOTIFICATION " + type /*+ ": " + string*/);
		// TODO
	}

}

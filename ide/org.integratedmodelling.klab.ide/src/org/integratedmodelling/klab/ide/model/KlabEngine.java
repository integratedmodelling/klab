package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;

public class KlabEngine extends KlabPeer {
	
	public KlabEngine() {
		super(Sender.ENGINE);
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notificatgion) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
	}
}

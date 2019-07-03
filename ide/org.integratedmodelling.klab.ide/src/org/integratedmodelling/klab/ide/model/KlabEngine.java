package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;

public class KlabEngine extends KlabPeer {

	public KlabEngine(String identity) {
		super(Sender.ENGINE, identity);
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notification) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
		// the session keeps the logs
		Activator.session().recordNotification(notification, message.getIdentity(), message.getType(), message.getId());
	}

	@MessageHandler
	public void handleNotification(NamespaceCompilationResult report) {
		KlabNavigator.refresh(() -> {
			Kim.INSTANCE.updateErrors(report);
			Activator.loader().revalidate(report.getNamespaceId());
			if (report.getNotifications().size() > 0) {
				Eclipse.INSTANCE.refreshOpenEditors(report.getNamespaceId());
			}
		});
	}

}

package org.integratedmodelling.klab.ide.model;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.kim.api.IKimNamespace;
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
		
		Activator.klab().updateErrors(report);
		IKimNamespace namespace = Activator.loader().getNamespace(report.getNamespaceId());
		if (namespace != null) {
			Activator.klab().setNamespaceStatus(namespace.getName(), report);
			IFile ifile = Eclipse.INSTANCE.getIFile(namespace);
			if (ifile != null) {
				Eclipse.INSTANCE.updateMarkersForNamespace(report, ifile);
				KlabNavigator.refresh();
			}
		}
	}

}

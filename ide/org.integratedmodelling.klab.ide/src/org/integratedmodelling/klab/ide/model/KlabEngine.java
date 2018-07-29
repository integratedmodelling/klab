package org.integratedmodelling.klab.ide.model;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
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
		IKimNamespace namespace = Activator.loader().getNamespace(report.getNamespaceId());
		if (namespace != null) {
			File file = namespace.getFile();
			if (file != null) {
				IFile ifile = Eclipse.INSTANCE.getIFile(file);
				if (ifile != null) {
					// null-safe operators, ever?
					Eclipse.INSTANCE.updateMarkersForNamespace(report.getNotifications(), ifile);
				}
			}
		}
	}

}

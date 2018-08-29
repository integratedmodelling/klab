package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.rest.ObservationReference;

public class KlabTask extends KlabPeer {

    public KlabTask(String taskId) {
        super(Sender.TASK, taskId);
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
    public void handleObservation(ObservationReference observation) {
    	Activator.session().recordObservation(observation);
    }
}

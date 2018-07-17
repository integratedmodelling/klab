package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;

public class KlabTask {

	String taskId;
	
	public KlabTask(String taskId) {
		this.taskId = taskId;
	}
	
	@MessageHandler(messageClass=IMessage.MessageClass.Notification)
	public void handleNotification(String string, IMessage.Type type) {
		System.out.println("TASK NOTIFICATION " + type + ": " + string);
		// TODO
	}


}

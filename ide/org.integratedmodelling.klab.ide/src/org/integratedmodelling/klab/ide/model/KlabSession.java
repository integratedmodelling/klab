package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * Front-end receiver for session messages.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession extends KlabPeer {

	public KlabSession(String sessionId) {
		super(Sender.SESSION, sessionId);
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notification) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
	}

	@MessageHandler
	public void handleSearchResponse(IMessage message, SearchResponse response) {
		send(message);
	}

	@MessageHandler(type = Type.TaskStarted)
	public void handleTaskStarted(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		bus.subscribe(task.getId(), new KlabTask(task.getId()));
	}

	@MessageHandler(type = Type.TaskFinished)
	public void finishTask(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler(type = Type.TaskAborted)
	public void abortTask(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler
	public void handleDataflow(IMessage message, DataflowReference dataflow) {
		send(message);
	}
}

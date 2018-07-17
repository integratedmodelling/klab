package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.ide.Activator;
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
public class KlabSession {

	String sessionId;

	public KlabSession(String sessionId) {
		this.sessionId = sessionId;
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(String string, IMessage.Type type) {
		System.out.println("NOTIFICATION " + type + ": " + string);
		// TODO
	}

	@MessageHandler
	public void handleSearchResponse(SearchResponse response) {
		System.out.println("Search response: " + response);
	}

	@MessageHandler(type = Type.TaskStarted)
	public void handleTaskStarted(TaskReference task) {

		/*
		 * TODO notify the views
		 */

		// subscribe task peer with monitor, which will unsubscribe itself when ends
		Activator.get().subscribe(task.getId(), new KlabTask(task.getId()));
	}

	@MessageHandler(type = Type.TaskFinished)
	public void finishTask(TaskReference task) {
		System.out.println("TASK FINISHED " + task.getId());
		Activator.get().unsubscribe(task.getId());
	}

	@MessageHandler(type = Type.TaskAborted)
	public void abortTask(TaskReference task) {
		System.out.println("TASK FUBAR " + task.getId());
		Activator.get().unsubscribe(task.getId());
	}

	@MessageHandler
	public void handleDataflow(DataflowReference dataflow) {
		// TODO notify
	}
}

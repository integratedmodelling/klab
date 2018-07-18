package org.integratedmodelling.klab.ide.model;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
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
		System.out.println("SESSION NOTIFICATION " + type + ": " + string);
		// TODO
	}

	@MessageHandler
	public void handleSearchResponse(SearchResponse response) {
		System.out.println("Search response: " + response);
	}

	@MessageHandler(type = Type.TaskStarted)
	public void handleTaskStarted(TaskReference task, IMessageBus bus) {
		/*
		 * TODO notify the views
		 */
		System.out.println("TASK START " + task.getId());
//		bus.subscribe(task.getId(), new KlabTask(task.getId()));
	}

	@MessageHandler(type = Type.TaskFinished)
	public void finishTask(TaskReference task, IMessageBus bus) {
		System.out.println("TASK FINISHED " + task.getId());
//		bus.unsubscribe(task.getId());
	}

	@MessageHandler(type = Type.TaskAborted)
	public void abortTask(TaskReference task, IMessageBus bus) {
		System.out.println("TASK FUBAR " + task.getId());
//		bus.unsubscribe(task.getId());
	}

	@MessageHandler
	public void handleDataflow(DataflowReference dataflow) {
		System.out.println("GOT DATAFLOW");
		// TODO notify. Task ID is in the dataflow - this may need to change
	}
}

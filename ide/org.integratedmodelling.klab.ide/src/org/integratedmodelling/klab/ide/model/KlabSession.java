package org.integratedmodelling.klab.ide.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.RunScriptRequest;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * Front-end session proxy and receiver for session messages.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession extends KlabPeer {

	public KlabSession(String sessionId) {
		super(Sender.SESSION, sessionId);
	}

	public void importFileResource(File file) {
		try {
			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ImportResource,
					new ResourceImportRequest(file.toURI().toURL()));
		} catch (MalformedURLException e) {
			// dio petardo
			Eclipse.INSTANCE.handleException(e);
		}
	}

	public void launchScript(URL url) {
		Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunScript, new RunScriptRequest(url, false));
	}

	public void launchTest(URL url) {
		Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunTest, new RunScriptRequest(url, true));
	}

	public void observe(EKimObject dropped) {
		// TODO either gets a concept or a model. Context must exist.
	}

	public void observe(EObserver dropped, boolean addToContext) {
		// TODO
	}

	public void previewResource(ResourceReference dropped) {
		// TODO Auto-generated method stub
	}


	/*
	 * ----- Message handlers -----
	 */

	/*
	 * All resource-related responses
	 */
	@MessageHandler
	public void handleResourceLifecycle(ResourceReference resource, IMessage.Type type) {
		/*
		 * TODO
		 */
		System.out.println("GOT RESOURCE " + type + ": " + resource);
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

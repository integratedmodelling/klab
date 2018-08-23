package org.integratedmodelling.klab.ide.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
import org.integratedmodelling.klab.ide.navigator.model.beans.DisplayPriority;
import org.integratedmodelling.klab.ide.navigator.model.beans.EDataflowReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ENotification;
import org.integratedmodelling.klab.ide.navigator.model.beans.EObservationReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ERuntimeObject;
import org.integratedmodelling.klab.ide.navigator.model.beans.ETaskReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.RunScriptRequest;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * Front-end session proxy and receiver for session messages. Maintains and
 * manages state for the session, including the list of available resources and
 * the history of user actions and their consequences.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession extends KlabPeer {

	private AtomicLong queryCounter = new AtomicLong();

	/*
	 * all tasks in the session, indexed by ID of task in maps linked to the root
	 * context. Each task reference is linked to child descriptors for dataflow,
	 * artifacts produced and log entries. Used to populate the task tree in the
	 * runtime view. Maintains chronological order both in the task map and the task
	 * catalog.
	 */
	private Map<String, ETaskReference> taskCatalog = Collections.synchronizedMap(new LinkedHashMap<>());

	/*
	 * All observation seen
	 */
	private Map<String, EObservationReference> observationCatalog = Collections.synchronizedMap(new LinkedHashMap<>());

	/*
	 * All notifications not linked to a task
	 */
	private List<ENotification> systemNotifications = Collections.synchronizedList(new ArrayList<>());

	/*
	 * Current context in UI or null
	 */
	private String currentContextId;

	/*
	 * Historical of contexts seen. Redundant with taskCatalog.keySet() - check.
	 */
	private List<String> contexts = Collections.synchronizedList(new LinkedList<>());

	private ETaskReference contextTask;

	public KlabSession(String sessionId) {
		super(Sender.SESSION, sessionId);
	}

	/*
	 * --- public methods ---
	 */

	/**
	 * Build a list describing the entire known history of the session, honoring
	 * chosen display priority and options. The first-level objects will always be
	 * root contexts, most recently created first.
	 * 
	 * @param priority
	 * @return the session history
	 */
	public List<ERuntimeObject> getSessionHistory(DisplayPriority priority) {
		List<ERuntimeObject> ret = new ArrayList<>();
		if (contextTask != null) {
			ret.add(contextTask);
		}
		for (String ctx : contexts) {
			ret.add(observationCatalog.get(ctx));
		}
		return ret;
	}

	/**
	 * ID of the current root context, or null if none is set.
	 * 
	 * @return
	 */
	public String getCurrentContextId() {
		return currentContextId;
	}

	/**
	 * The latest observation made, or the one set by the user.
	 * 
	 * @return
	 */
	public EObservationReference getCurrentContext() {
		return currentContextId == null ? null : observationCatalog.get(currentContextId);
	}

	/**
	 * All the observations made in this session, last observed first. May be filled
	 * on startup after rejoining a session.
	 * 
	 * @return
	 */
	public List<EObservationReference> getContexts() {
		List<EObservationReference> ret = new ArrayList<>();
		for (String id : contexts) {
			ret.add(observationCatalog.get(id));
		}
		return ret;
	}

	/*
	 * --- State and history management
	 */

	void recordNotification(String notification, String identity, Type type) {
		ETaskReference parent = null;
		if (taskCatalog.containsKey(identity)) {
			parent = taskCatalog.get(identity);
		}
		
		ENotification enote = new ENotification();
		enote.setMessage(notification);
		switch (type) {
		case Debug:
			enote.setLevel(Level.FINE.getName());
			break;
		case Error:
			enote.setLevel(Level.SEVERE.getName());
			break;
		case Info:
			enote.setLevel(Level.INFO.getName());
			break;
		case Warning:
			enote.setLevel(Level.WARNING.getName());
			break;
		default:
			break;
		}
		
		enote.setParent(parent);
		if (parent != null) {
			parent.addNotification(enote);
		}
		
		if (parent != null) {
			send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, enote);
		} else {
			systemNotifications.add(enote);
			send(IMessage.MessageClass.UserInterface, IMessage.Type.Notification, enote);
		}
	}

	private void recordTask(TaskReference task, Type event) {
		
		ETaskReference etask = null;
		EObservationReference context = null;
		if (task.getContextId() != null) {
			context = observationCatalog.get(task.getContextId());
		}
		if (event == Type.TaskStarted) {
			etask = new ETaskReference(task, null);
			taskCatalog.put(task.getId(), etask);
			if (task.getContextId() == null) {
				this.contextTask = etask;
			}
		} else {
			etask = taskCatalog.get(task.getId());
			if (this.contextTask != null && this.contextTask.getId().equals(task.getId())) {
				if (context != null) {
					context.setCreator(etask);
				}
				// if aborted just remove; if finished, put under its context
				this.contextTask = null;
			}
		}
		etask.setStatus(event);
		send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, etask);
	}

	private void recordObservation(ObservationReference observation) {

		this.currentContextId = observation.getId();
		EObservationReference parent = null;
		if (observation.getParentId() != null) {
			parent = observationCatalog.get(observation.getParentId());
		}

		EObservationReference obs = new EObservationReference(observation, parent);
		if (observation.getTaskId() != null) {
			ETaskReference task = taskCatalog.get(observation.getTaskId());
			obs.setCreator(task);
			task.addCreated(obs);
		}

		observationCatalog.put(observation.getId(), obs);
		if (observation.getParentId() == null) {
			this.currentContextId = observation.getId();
			contexts.add(0, observation.getId());
		}

		send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, obs);
		if (observation.getParentId() == null) {
			send(IMessage.MessageClass.UserInterface, IMessage.Type.FocusChanged, obs);
		}
	}

	/*
	 * --- Front-end action triggers ---
	 */

	public void importFileResource(File file, String project) {
		try {
			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ImportResource,
					new ResourceImportRequest(file.toURI().toURL(), project));
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
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(dropped.getId(), currentContextId, null));
	}

	public void observe(String urn) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(urn, currentContextId, null));
	}

	public void observe(EObserver dropped, boolean addToContext) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(dropped.getId(), addToContext ? currentContextId : null, null));
	}

	public void previewResource(ResourceReference resource) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(resource.getUrn(), null, null));
	}

	public long startQuery(String query) {

		long queryIndex = queryCounter.getAndIncrement();

		SearchRequest request = new SearchRequest();
		request.setRequestId(queryIndex);
		request.setQueryString(query);

		return queryIndex;
	}

	// nah, use the response feature in the message bus and make it right
	public void continueQuery(String query, long previous) {
		//
	}

	/*
	 * ----- Back-end message handlers -----
	 */

	/*
	 * All resource-related responses
	 */
	@MessageHandler
	public void handleResourceLifecycle(ResourceReference resource, IMessage.Type type) {
		/*
		 * TODO
		 */
		switch (type) {
		case ResourceImported:
			Activator.klab().notifyResourceImport(resource);
			break;
		default:
			break;
		}
		System.out.println("GOT RESOURCE " + type + ": " + resource);
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notification) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
		recordNotification(notification, message.getIdentity(), message.getType());
	}

	@MessageHandler
	public void handleSearchResponse(IMessage message, SearchResponse response) {
		send(message);
	}

	@MessageHandler(type = Type.TaskStarted)
	public void handleTaskStarted(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		recordTask(task, Type.TaskStarted);
		bus.subscribe(task.getId(), new KlabTask(task.getId()));
	}

	@MessageHandler(type = Type.TaskFinished)
	public void handleTaskFinished(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler(type = Type.TaskAborted)
	public void handleTaskAborted(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler
	public void handleSearchResponse(SearchResponse response) {

	}

	@MessageHandler
	public void handleObservation(ObservationReference observation) {
		recordObservation(observation);
	}

	@MessageHandler
	public void handleDataflow(IMessage message, DataflowReference dataflow) {
		ETaskReference task = taskCatalog.get(dataflow.getTaskId());
		if (task != null) {
			task.setDataflow(new EDataflowReference(dataflow, task));
			send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, task);
		}
		send(message);
	}

	public List<ENotification> getSystemNotifications() {
		return new ArrayList<>(systemNotifications);
	}

}

package org.integratedmodelling.klab.ide.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigatorActions;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EObserver;
import org.integratedmodelling.klab.ide.navigator.model.beans.DisplayPriority;
import org.integratedmodelling.klab.ide.navigator.model.beans.EDataflowReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ENotification;
import org.integratedmodelling.klab.ide.navigator.model.beans.EObservationReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ERuntimeObject;
import org.integratedmodelling.klab.ide.navigator.model.beans.ETaskReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.utils.StringUtils;
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
	 * all tasks in the session
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
	 * Current root context and task in UI or null
	 */
	private String currentRootContextId;
	private String currentRootTaskId;

	// historical list of root tasks and contexts in order of appearance.
	private List<String> contexts = Collections.synchronizedList(new LinkedList<>());
	private List<String> tasks = Collections.synchronizedList(new LinkedList<>());

	/*
	 * This is to attribute new tasks to the root one they belong to. This hierarchy
	 * is unique to the UI and does not reflect the arrangement of tasks in the
	 * engine.
	 */
	private Map<String, ETaskReference> taskByObservation = Collections.synchronizedMap(new HashMap<>());

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
	public List<ERuntimeObject> getSessionHistory(DisplayPriority priority, Level level) {

		List<ERuntimeObject> ret = new ArrayList<>();
		if (priority == DisplayPriority.ARTIFACTS_FIRST) {
			for (int i = contexts.size() - 1; i >= 0; i--) {
				ret.add(observationCatalog.get(contexts.get(i)));
			}
		} else {
			for (int i = tasks.size() - 1; i >= 0; i--) {
				ret.add(taskCatalog.get(tasks.get(i)));
			}
		}
		return ret;
	}

	/**
	 * ID of the current root context, or null if none is set.
	 * 
	 * @return
	 */
	public String getCurrentContextId() {
		return currentRootContextId;
	}

	public String getCurrentTaskId() {
		return currentRootTaskId;
	}

	/**
	 * The latest observation made, or the one set by the user.
	 * 
	 * @return
	 */
	public EObservationReference getCurrentContext() {
		return currentRootContextId == null ? null : observationCatalog.get(currentRootContextId);
	}

	/**
	 * The latest task started, or the one set by the user.
	 * 
	 * @return
	 */
	public ETaskReference getCurrentTask() {
		return currentRootTaskId == null ? null : taskCatalog.get(currentRootTaskId);
	}

	/**
	 * All the observations made in this session, last observed first. May be filled
	 * on startup after rejoining a session.
	 * 
	 * @return
	 */
	public List<EObservationReference> getContexts() {
		List<EObservationReference> ret = new ArrayList<>();
		for (int i = contexts.size() - 1; i >= 0; i--) {
			ret.add(observationCatalog.get(contexts.get(i)));
		}
		return ret;
	}

	/*
	 * --- State and history management
	 */

	void recordNotification(String notification, String identity, Type type, String messageId) {

		ETaskReference parent = null;
		if (taskCatalog.containsKey(identity)) {
			parent = taskCatalog.get(identity);
		}

		/*
		 * TODO multi-line notification should be broken up into multiple ones, with
		 * continuation flag from the second on, so they can be displayed properly
		 */

		ENotification enote = new ENotification(messageId);
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
			if (task.getContextId() == null) {
				// new root task
				tasks.add(task.getId());
				this.currentRootTaskId = task.getId();
			}
			etask = new ETaskReference(task);
			taskCatalog.put(task.getId(), etask);
			if (task.getContextId() != null) {
				ETaskReference parent = taskByObservation.get(task.getContextId());
				if (parent != null) {
					parent.addChildTask(etask);
				}
			}
		} else {
			etask = taskCatalog.get(task.getId());
		}

		etask.setStatus(event);
		send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, etask);
	}

	public void recordObservation(ObservationReference observation) {
		
		EObservationReference obs = observationCatalog.get(observation.getId());

		if (obs != null) {
			obs.setFocal(true);
			for (EObservationReference oo : observationCatalog.values()) {
				if (oo.getTaskId().equals(obs.getTaskId()) && !oo.getId().equals(observation.getId())) {
					oo.setDependentTo(obs);
				}
			}
		} else {

			EObservationReference parent = null;
			if (observation.getParentId() != null) {
				parent = observationCatalog.get(observation.getParentId());
				parent.addChildObservationId(observation.getId());
			} else {
				this.currentRootContextId = observation.getId();
				contexts.add(observation.getId());
			}

			obs = new EObservationReference(observation, observation.getTaskId(),
					observation.getParentId());
			if (observation.getTaskId() != null) {
				ETaskReference task = taskCatalog.get(observation.getTaskId());
				if (task.getContextId() == null) {
					task.setContextId(observation.getId());
				}
				task.addCreated(obs);
				taskByObservation.put(observation.getId(), task);
			}

			observationCatalog.put(observation.getId(), obs);

			if (observation.getParentId() == null) {
				this.currentRootContextId = observation.getId();
			}
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
				new ObservationRequest(dropped.getId(), currentRootContextId, null));
	}

	public void observe(String urn) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(urn, currentRootContextId, null));
	}

	public void observe(EObserver dropped, boolean addToContext) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(dropped.getId(), addToContext ? currentRootContextId : null, null));
	}

	public void previewResource(ResourceReference resource) {
		Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
				new ObservationRequest(resource.getUrn(), currentRootContextId, null));
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
        case ResourceDeleted:
            Activator.klab().notifyResourceDeleted(resource);
            break;
		default:
			break;
		}
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Notification)
	public void handleNotification(IMessage message, String notification) {
		if (message.getType() != IMessage.Type.Debug) {
			send(message);
		}
		recordNotification(notification, message.getIdentity(), message.getType(), message.getId());
	}

	@MessageHandler
	public void handleSearchResponse(IMessage message, SearchResponse response) {
		send(message);
	}

	@MessageHandler(type = IMessage.Type.ResetContext)
	private void handleResetContextRequest(IMessage message, String dummy) {
		this.currentRootContextId = null;
		this.currentRootTaskId = null;
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
		recordTask(task, Type.TaskFinished);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler(type = Type.TaskAborted)
	public void handleTaskAborted(IMessage message, TaskReference task, IMessageBus bus) {
		send(message);
		recordTask(task, Type.TaskAborted);
		bus.unsubscribe(task.getId());
	}

	@MessageHandler
	public void handleObservation(ObservationReference observation) {
		recordObservation(observation);
	}

	@MessageHandler
	public void handleDataflow(IMessage message, DataflowReference dataflow) {
		ETaskReference task = taskCatalog.get(dataflow.getTaskId());
		if (task != null) {
			task.setDataflow(new EDataflowReference(dataflow, message.getId(), task));
			send(IMessage.MessageClass.UserInterface, IMessage.Type.HistoryChanged, task);
		}
		send(message);
	}

	public List<ENotification> getSystemNotifications(Level level) {
		List<ENotification> ret = new ArrayList<>();
		for (int i = systemNotifications.size() - 1; i >= 0; i--) {
			if (level.intValue() <= Level.parse(systemNotifications.get(i).getLevel()).intValue()) {
				ret.add(systemNotifications.get(i));
			}
		}
		return ret;
	}

	public void dumpHistory(DisplayPriority priority, Level logLevel) {
		for (ERuntimeObject e : getSessionHistory(priority, Level.FINE)) {
			dumpHistoryObject(e, priority, logLevel, 0);
		}
	}

	private void dumpHistoryObject(ERuntimeObject e, DisplayPriority priority, Level logLevel, int level) {

		System.out.println(StringUtils.spaces(level * 3) + e);
		for (ERuntimeObject c : e.getEChildren(priority, logLevel)) {
			dumpHistoryObject(c, priority, logLevel, level + 1);
		}
	}

	public ETaskReference getTask(String id) {
		return taskCatalog.get(id);
	}

	public EObservationReference getObservation(String id) {
		return observationCatalog.get(id);
	}

}

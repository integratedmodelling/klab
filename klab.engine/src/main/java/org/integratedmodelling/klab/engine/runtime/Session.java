package org.integratedmodelling.klab.engine.runtime;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Indexing;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.CRUDOperation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.model.KimObject;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.DocumentationReference;
import org.integratedmodelling.klab.rest.InterruptTask;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ProjectLoadRequest;
import org.integratedmodelling.klab.rest.ProjectLoadResponse;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.RunScriptRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchMatchAction;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.SpatialLocation;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Engine session. Implements UserDetails to be directly usable as a principal
 * in Spring security.
 * 
 * @author ferdinando.villa
 *
 */
public class Session implements ISession, UserDetails, IMessageBus.Relay {

	private static final long serialVersionUID = -1571090827271892549L;

	Monitor monitor;
	String token = "s" + NameGenerator.shortUUID();
	IEngineUserIdentity user;
	List<Listener> listeners = new ArrayList<>();
	boolean closed = false;
	Set<GrantedAuthority> authorities = new HashSet<>();
	long lastActivity = System.currentTimeMillis();
	long creation = System.currentTimeMillis();
	long lastJoin = System.currentTimeMillis();
	boolean isDefault = false;
	boolean lockResolution = false;
	Set<String> relayIdentities = new HashSet<>();

	SpatialExtent regionOfInterest = null;

	/**
	 * A scheduler to periodically collect observation and task garbage
	 */
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/*
	 * Tasks created in this session, managed as task/script start and end. Content
	 * may be a IScript or a ITask.
	 */
	Map<String, Future<?>> tasks = Collections.synchronizedMap(new HashMap<>());

	/*
	 * The contexts for all root observations built in this session, up to the
	 * configured number, most recent first. Synchronized.
	 */
	Deque<IRuntimeContext> observationContexts = new LinkedBlockingDeque<>(
			Configuration.INSTANCE.getMaxLiveObservationContextsPerSession());

	/*
	 * Support for incremental search from the front-end. Synchronized because
	 * searches can take arbitrary time although in most cases they will be fast.
	 */
	private Map<String, Pair<IIndexingService.Context, List<Match>>> searchContexts = Collections
			.synchronizedMap(new HashMap<>());

	private double gridSize;

	private String gridUnits;

	public interface Listener {
		void onClose(ISession session);
	}

	public Session(Engine engine, IEngineUserIdentity user) {
		this.user = user;
		this.monitor = ((Monitor) engine.getMonitor()).get(this);
		this.authorities.add(new SimpleGrantedAuthority(Roles.SESSION));
		Authentication.INSTANCE.registerSession(this);
	}

	void touch() {
		this.lastActivity = System.currentTimeMillis();
	}

	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}

	@Override
	public String getId() {
		return token;
	}

	@Override
	public boolean is(Type type) {
		return type == Type.MODEL_SESSION;
	}

	@Override
	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return IIdentity.findParent(this, type);
	}

	@Override
	public IEngineUserIdentity getParentIdentity() {
		return user;
	}

	@Override
	public Monitor getMonitor() {
		return monitor;
	}

	@Override
	public void close() throws IOException {
		for (Listener listener : listeners) {
			listener.onClose(this);
		}
		this.closed = true;
	}

	@Override
	public Future<ISubject> observe(String urn, String... scenarios) {

		touch();

		Object object = null;

		if (urn.contains(" ")) {
			// can only be a declaration
			object = Observables.INSTANCE.declare(urn);
		} else {
			object = Resources.INSTANCE.getModelObject(urn);
		}

		if (object == null) {
			// check for URN and launch a viewer task if so.
			IResource resource = Resources.INSTANCE.resolveResource(urn);
			if (resource != null) {
				return new UrnContextualizationTask(this, urn);
			} else {
				throw new KlabContextualizationException("cannot resolve URN " + urn);
			}
		}

		if (!(object instanceof Observer)) {

			if (regionOfInterest != null && (object instanceof KimObject || object instanceof IObservable)) {

				INamespace namespace = object instanceof KimObject ? ((KimObject) object).getNamespace()
						: Namespaces.INSTANCE.getNamespace(((IObservable) object).getNamespace());
				SpatialExtent roi = new SpatialExtent(regionOfInterest);
				if (lockResolution) {
					roi.setGridResolution(this.gridSize);
					roi.setGridUnit(this.gridUnits);
				}
				Observer observer = Observations.INSTANCE.makeROIObserver(roi, (Namespace) namespace, monitor);
				try {
					ISubject subject = new ObserveContextTask(this, observer, CollectionUtils.arrayToList(scenarios))
							.get();
					if (subject != null) {
						/*
						 * the inner task gets lost - should not matter as this is simply handling an
						 * asynchronous UI action.
						 */
						subject.observe(urn);
						return ConcurrentUtils.constantFuture(subject);
					}
				} catch (InterruptedException | ExecutionException e) {
					monitor.error(e);
					return null;
				}
			}

			throw new KlabContextualizationException("URN " + urn + " does not specify an observation");
		}

		return new ObserveContextTask(this, (Observer) object, CollectionUtils.arrayToList(scenarios));
	}

	public String toString() {
		// TODO add user
		return "<session " + getId() + ">";
	}

	@Override
	public Set<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return getId();
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !closed;
	}

	@Override
	public boolean isEnabled() {
		return !closed;
	}

	@Override
	public IGeometry getRegionOfInterest() {

		if (regionOfInterest == null) {
			return Geometry.empty();
		}
		return Geometry.create("S1").withBoundingBox(regionOfInterest.getEast(), regionOfInterest.getWest(),
				regionOfInterest.getSouth(), regionOfInterest.getNorth());
	}

	@Override
	public IScript run(URL url) throws KlabException {
		IScript ret = null;
		if (url.toString().endsWith(".kim")) {
			return new Script(this, url);
		}
		return ret;
	}

	@Override
	public IObservation getObservation(String observationId) {
		// start at the most recent
		for (IRuntimeContext context : observationContexts) {
			IObservation ret = context.getObservation(observationId);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Future<?>> T getTask(String taskId, Class<T> cls) {
		return (T) tasks.get(taskId);
	}

	/**
	 * Register a task. It may be a ITask or a IScript, which only have the Future
	 * identity in common.
	 * 
	 * @param task
	 * @param monitor2
	 */
	public void registerTask(Future<?> task) {
		String id = task instanceof ITask ? ((ITask<?>) task).getId() : ((IScript) task).getId();
		this.tasks.put(id, task);
	}

	/**
	 * Interrupt the passed task, notifying its monitor for computations to
	 * terminate gracefully. Return true if there was a task to interrupt and it was
	 * indeed canceled.
	 * 
	 * @param taskId
	 * @return true if interruption was achieved
	 */
	public boolean interruptTask(String taskId, boolean forceInterruption) {
		Future<?> task = this.tasks.get(taskId);
		if (task != null) {
			((Monitor) ((IRuntimeIdentity) task).getMonitor()).interrupt();
			if (task.cancel(forceInterruption)) {
				unregisterTask(task);
				return true;
			}
		}
		return false;
	}

	/**
	 * Register a task. It may be a ITask or a IScript, which only have the Future
	 * identity in common.
	 * 
	 * @param task
	 */
	public void unregisterTask(Future<?> task) {
		this.tasks.remove(task instanceof ITask ? ((ITask<?>) task).getId() : ((IScript) task).getId());
	}

	/**
	 * Register the runtime context of a new observation. If needed, dispose of the
	 * oldest observation made.
	 * 
	 * @param runtimeContext
	 */
	public void registerObservationContext(IRuntimeContext runtimeContext) {

		if (!observationContexts.offerFirst(runtimeContext)) {
			disposeObservation(observationContexts.pollLast());
			observationContexts.addFirst(runtimeContext);
		}
		// this is for human watchers, everything else is done by the runtime
		monitor.info("new context registered with ID " + runtimeContext.getRootSubject().getId() + " for "
				+ runtimeContext.getRootSubject());
	}

	private void disposeObservation(IRuntimeContext context) {
		// TODO dispose of the observation
		// TODO send a notification through the session monitor that the obs is now out
		// of scope.
		Logging.INSTANCE.warn("Disposing of observation " + context.getRootSubject() + ": TODO");
	}

	/*
	 * ------------------------------------------------------------------------
	 * handlers for messages
	 * ------------------------------------------------------------------------
	 */

	@MessageHandler(type = IMessage.Type.FeatureAdded)
	private void handleFeatureAdded(final SpatialLocation location) {

		if (location.getContextId() == null) {
			Shape shape = Shape.create("EPSG:4326 " + location.getWktShape());
			Observer observer = Observations.INSTANCE.makeROIObserver(shape, null, monitor);
			try {
				new ObserveContextTask(this, observer, new ArrayList<>()).get();
			} catch (InterruptedException | ExecutionException e) {
				monitor.error(e);
			}
		} else {
			// TODO do something with the shape - must involve user to define semantics
		}
	}

	@MessageHandler
	private void handleResourceCRUDRequest(final ResourceCRUDRequest request, IMessage.Type type) {

		for (String urn : request.getResourceUrns()) {

			IResource resource = Resources.INSTANCE.resolveResource(urn);
			if (resource == null) {
				monitor.warn("requested resource not found: " + urn);
				continue;
			}

			IKimProject sourceProject = Kim.INSTANCE.getProject(resource.getLocalProjectName());

			if (sourceProject == null) {
				monitor.error("resource comes from an unknown project: canceling operation");
				return;
			}

			if (request.getOperation() == CRUDOperation.MOVE) {

				IProject destinationProject = Resources.INSTANCE.getProject(request.getDestinationProject());
				if (destinationProject == null) {
					monitor.error("resource target is an unknown project: canceling operation");
					return;
				}
				monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceDeleted,
						((Resource) resource).getReference());
				resource = Resources.INSTANCE.getLocalResourceCatalog().move(resource, destinationProject);
				monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
						((Resource) resource).getReference());
			} else if (request.getOperation() == CRUDOperation.COPY) {

				IProject destinationProject = Resources.INSTANCE.getProject(request.getDestinationProject());
				if (destinationProject == null) {
					monitor.error("resource target is an unknown project: canceling operation");
					return;
				}

				resource = Resources.INSTANCE.getLocalResourceCatalog().copy(resource, destinationProject);
				monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
						((Resource) resource).getReference());
			} else if (request.getOperation() == CRUDOperation.DELETE) {
				resource = Resources.INSTANCE.getLocalResourceCatalog().remove(urn);
				monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceDeleted,
						((Resource) resource).getReference());
			}

		}
	}

	@MessageHandler
	private void importResource(final ResourceImportRequest request) {
		IProject project = Resources.INSTANCE.getProject(request.getProjectName());
		if (project == null) {
			monitor.error("cannot import resource: project " + request.getProjectName() + " is unknown");
		} else {
			new Thread() {
				@Override
				public void run() {
					if (request.isBulkImport()) {
						for (IResource resource : Resources.INSTANCE.importResources(request.getImportUrl(), project,
								request.getAdapter())) {
							monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
									((Resource) resource).getReference());
						}
					} else {
						IResource resource = Resources.INSTANCE.importResource(request.getImportUrl(), project);
						if (resource != null) {
							monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
									((Resource) resource).getReference());
						}
					}
				}

			}.start();
		}
	}

	@MessageHandler
	private void setRegionOfInterest(SpatialExtent extent) {
		Envelope envelope = Envelope.create(extent.getEast(), extent.getWest(), extent.getSouth(), extent.getNorth(),
				Projection.getLatLon());
		ScaleReference scale = new ScaleReference();
		Pair<Integer, String> resolution = envelope.getResolutionForZoomLevel();
		int scaleRank = envelope.getScaleRank();
		scale.setEast(envelope.getMaxX());
		scale.setWest(envelope.getMinX());
		scale.setNorth(envelope.getMaxY());
		scale.setSouth(envelope.getMinY());
		scale.setSpaceUnit(resolution.getSecond());
		scale.setSpaceResolution(resolution.getFirst());
		scale.setSpaceResolutionDescription(resolution.getFirst() + " " + resolution.getSecond());
		scale.setResolutionDescription(resolution.getFirst() + " " + resolution.getSecond());
		scale.setSpaceScale(scaleRank);

		monitor.send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined, scale);
		this.regionOfInterest = extent;
	}

	@MessageHandler
	private void interruptTask(InterruptTask request) {
		interruptTask(request.getTaskId(), request.isForceInterruption());
	}

	@MessageHandler
	private void handleMatchAction(SearchMatchAction action) {

		final String contextId = action.getContextId();
		Pair<Context, List<Match>> ctx = searchContexts.get(contextId);
		if (ctx == null) {
			throw new IllegalStateException("match action has invalid context ID");
		}
		Context newContext = action.getMatchIndex() < 0 ? ctx.getFirst().previous()
				: ctx.getFirst().accept(ctx.getSecond().get(action.getMatchIndex()));
		searchContexts.put(contextId, new Pair<>(newContext, new ArrayList<>()));
	}

	@MessageHandler(type = IMessage.Type.DataflowNodeDetail)
	private void handleMatchAction(DataflowState state) {
		System.out.println("Document node " + state.getNodeId());
	}

	@MessageHandler
	private void handleSearchRequest(SearchRequest request, IMessage message) {

		final String contextId = request.getContextId() == null ? NameGenerator.shortUUID() : request.getContextId();
		if (request.getContextId() == null) {
			searchContexts.put(contextId,
					new Pair<>(Indexing.INSTANCE.createContext(request.getMatchTypes(), request.getSemanticTypes()),
							new ArrayList<>()));
		}

		if (request.isCancelSearch()) {
			/*
			 * just garbage collect it
			 */
			searchContexts.remove(contextId);

		} else {

			/*
			 * spawn search thread, which will respond when done.
			 */
			new Thread() {

				@Override
				public void run() {

					SearchResponse response = new SearchResponse();
					response.setContextId(contextId);
					response.setRequestId(request.getRequestId());
					response.setLast(true);

					final Pair<Context, List<Match>> context = searchContexts.get(contextId);

					if (request.isDefaultResults()) {

						/*
						 * These come from the user's groups. They should eventually be linked to
						 * session history and preferences.
						 */
						for (ObservableReference observable : Authentication.INSTANCE
								.getDefaultObservables(Session.this)) {
							response.getMatches().add(new SearchMatch(observable.getObservable(), observable.getLabel(),
									observable.getDescription(), observable.getSemantics()));
						}

					} else {

						List<Match> matches = Indexing.INSTANCE.query(request.getQueryString(), context.getFirst());

						for (Match match : matches) {
							SearchMatch m = new SearchMatch();
							m.getSemanticType().addAll(match.getConceptType());
							m.setMainSemanticType(Kim.INSTANCE.getFundamentalType(match.getConceptType()));
							m.setMatchType(match.getMatchType());
							m.setName(match.getName());
							m.setId(match.getId());
							m.setDescription(match.getDescription());
							response.getMatches().add(m);
						}
						searchContexts.put(contextId, new Pair<Context, List<Match>>(context.getFirst(), matches));
					}
					monitor.send(Message.create(token, IMessage.MessageClass.Query, IMessage.Type.QueryResult,
							response.signalEndTime()).inResponseTo(message));
				}

			}.start();
		}
	}

	/**
	 * Flag the session as default. The effect is that engine pings from localhost
	 * will receive the session ID so they can choose to join it.
	 * 
	 * @return
	 */
	public Session setDefault() {
		this.isDefault = true;
		return this;
	}

	@MessageHandler
	private void handleRunScriptRequest(final RunScriptRequest request) {
		run(request.getScriptUrl());
	}

	/**
	 * Create, delete, modify resources in workspace.
	 * 
	 * @param message
	 * @param request
	 */
	@MessageHandler
	private void handleProjectModificationRequest(IMessage message, final ProjectModificationRequest request) {

		Project project = Resources.INSTANCE.getProject(request.getProjectId());

		if (project == null && message.getType() != IMessage.Type.CreateProject) {
			throw new IllegalArgumentException("project " + request.getProjectId() + " could not be found");
		}

		switch (message.getType()) {
		case CreateTestCase:
		case CreateCalibration:
		case CreateScript:
		case CreateNamespace:

			File file = null;
			switch (message.getType()) {
			case CreateTestCase:
				file = project.createTestCase(request.getAssetId(), request.getScriptName(), request.getScriptPath());
				break;
			case CreateScript:
				file = project.createScript(request.getAssetId(), request.getScriptName(), request.getScriptPath());
				break;
			case CreateNamespace:
				file = project.createNamespace(request.getAssetId(), false);
				break;
			default:
				// can't happen when calibrations are implemented
				throw new KlabUnimplementedException("can't yet create a calibration");
			}

			monitor.send(Message
					.create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.QueryResult,
							new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION, file))
					.inResponseTo(message));
			// send the message before adding, as the addition will trigger a modification
			// message which would cause
			// an issue
			Resources.INSTANCE.getLoader().add(file);
			break;

		case CreateProject:

			project = (Project) Resources.INSTANCE.getLocalWorkspace().createProject(request.getProjectId(), monitor);
			monitor.send(Message.create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateProject,
					new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION,
							project.getRoot()))
					.inResponseTo(message));
			// Resources.INSTANCE.getLoader().add(project.getStatement());
			break;

		case CreateScenario:

			file = project.createNamespace(request.getAssetId(), true);
			monitor.send(Message
					.create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateScenario,
							new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION, file))
					.inResponseTo(message));
			Resources.INSTANCE.getLoader().add(file);
			break;

		case DeleteLocalResource:
			break;

		case DeleteScript:
		case DeleteNamespace:
		case DeleteTestCase:

			IKimNamespace ns = Kim.INSTANCE.getNamespace(request.getAssetId());
			if (ns != null) {
				Resources.INSTANCE.getLoader().delete(ns.getFile());
				FileUtils.deleteQuietly(ns.getFile());
				monitor.send(
						Message.create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteNamespace,
								new ProjectModificationNotification(ProjectModificationNotification.Type.DELETION,
										ns.getFile()))
								.inResponseTo(message));
			}
			break;

		case DeleteProject:
			break;
		case DeleteResource:
			break;
		default:
			break;
		}
	}

	@MessageHandler(type = IMessage.Type.ResetContext)
	private void handleResetContextRequest(String dummy) {
		this.regionOfInterest = null;
		monitor.send(IMessage.Type.ResetContext, IMessage.MessageClass.UserContextChange, "");
	}

	/**
	 * This is all we need to react to UI events modifying the workspace or any of
	 * its imports.
	 * 
	 * @param event
	 * @param type
	 */
	@MessageHandler
	private void handleProjectEvent(final ProjectModificationNotification event, IMessage.Type type) {

		switch (type) {
		case ProjectFileAdded:
			Resources.INSTANCE.getLoader().add(event.getFile());
			break;
		case ProjectFileDeleted:
			Resources.INSTANCE.getLoader().delete(event.getFile());
			break;
		case ProjectFileModified:
			Resources.INSTANCE.getLoader().touch(event.getFile());
			break;
		default:
			break;
		}
	}

	@MessageHandler
	private void handleDocumentationEvent(final DocumentationReference documentation, IMessage.Type type) {
		IKimProject project = Kim.INSTANCE.getProject(documentation.getProjectName());
		if (project != null) {
			if (type == IMessage.Type.DocumentationModified) {
				Documentation.INSTANCE.resetDocumentation(
						IDocumentation.getDocumentationFile(documentation.getDocId(), project.getRoot()));
			}
		}
	}

	@MessageHandler
	private void handleObservationRequest(final ObservationRequest request) {

		/*
		 * TODO if we have no context in the request but the URN is not an observer and
		 * we have a ROI, create the context from the ROI and block the thread until
		 * it's observed. This should probably go in observe().
		 */

		if (request.getSearchContextId() != null) {
			searchContexts.remove(request.getSearchContextId());
		}

		if (request.getContextId() != null) {

			IObservation subject = getObservation(request.getContextId());
			if (!(subject instanceof ISubject)) {
				throw new IllegalArgumentException("cannot use a state as the context for an observation");
			}

			if (!OWL.INSTANCE.isSemantic(subject.getObservable())) {
				throw new IllegalArgumentException("context has no semantics and cannot support further observations");
			}

			((ISubject) subject).observe(request.getUrn(),
					request.getScenarios().toArray(new String[request.getScenarios().size()]));
		} else {
			observe(request.getUrn(), request.getScenarios().toArray(new String[request.getScenarios().size()]));
		}
	}

	@MessageHandler(type = IMessage.Type.TaskInterrupted)
	private void handleTaskInterruptRequest(InterruptTask request) {
		interruptTask(request.getTaskId(), true);
	}

	@MessageHandler(type = IMessage.Type.ScaleDefined)
	private void handleScaleChangeRequest(ScaleReference scaleRef) {
		if (scaleRef.isUnlockSpace()) {
			this.lockResolution = false;
			if (this.regionOfInterest != null) {
				this.setRegionOfInterest(this.regionOfInterest);
			}
		} else {
			this.gridSize = scaleRef.getSpaceResolution();
			this.gridUnits = scaleRef.getSpaceUnit();
			this.lockResolution = true;
			// TODO time - may not have space one day
		}
	}

	@MessageHandler
	private void handleProjectLoadRequest(final ProjectLoadRequest request, IMessage message) {
		new Thread() {

			@Override
			public void run() {
				ProjectLoadResponse response = new ProjectLoadResponse();
				Resources.INSTANCE.getLoader().loadProjectFiles(request.getProjectLocations());

				for (File file : request.getProjectLocations()) {
					IKimProject project = Kim.INSTANCE.getProjectIn(file, false);
					if (project != null) {
						IProject proj = Resources.INSTANCE.getProject(project.getName());
						response.getProjects().add(Resources.INSTANCE.createProjectDescriptor(proj));
					}
				}
				
				monitor.send(Message.create(token, IMessage.MessageClass.ProjectLifecycle,
						IMessage.Type.UserProjectOpened, response)/* .inResponseTo(message) */);
			}

		}.start();
	}

	/*
	 * REST communication
	 */
	public SessionReference getSessionReference() {

		SessionReference ret = new SessionReference();

		ret.setId(token);
		ret.setTimeEstablished(creation);
		ret.setTimeLastJoined(lastJoin);
		ret.setTimeRetrieved(System.currentTimeMillis());
		ret.setTimeLastActivity(lastActivity);

		for (IRuntimeContext ctx : observationContexts) {
			ret.getRootObservations().put(ctx.getRootSubject().getId(), Observations.INSTANCE
					.createArtifactDescriptor(ctx.getRootSubject(), null, ITime.INITIALIZATION, 0, false));
		}

		return ret;
	}

	@Override
	public Collection<String> getRelayIdentities() {
		return relayIdentities;
	}

	public void addRelayId(String relayId) {
		relayIdentities.add(relayId);
	}

	public boolean isDefault() {
		return isDefault;
	}

}

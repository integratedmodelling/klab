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
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Currencies;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Indexing;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
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
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.processing.osm.Geocoder;
import org.integratedmodelling.klab.components.geospace.processing.osm.Geocoder.Location;
import org.integratedmodelling.klab.components.runtime.DefaultRuntimeProvider;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.KimObject;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.rest.ContextualizationRequest;
import org.integratedmodelling.klab.rest.DataflowDetail;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.DocumentationReference;
import org.integratedmodelling.klab.rest.InterruptTask;
import org.integratedmodelling.klab.rest.NetworkReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ProjectLoadRequest;
import org.integratedmodelling.klab.rest.ProjectLoadResponse;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.ResourcePublishRequest;
import org.integratedmodelling.klab.rest.ResourcePublishResponse;
import org.integratedmodelling.klab.rest.RunScriptRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchMatch.TokenClass;
import org.integratedmodelling.klab.rest.SearchMatchAction;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.SettingChangeRequest;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.SpatialLocation;
import org.integratedmodelling.klab.rest.TicketRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ibm.icu.text.NumberFormat;

import akka.actor.ActorRef;

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
	Deque<IRuntimeScope> observationContexts = new LinkedBlockingDeque<>(
			Configuration.INSTANCE.getMaxLiveObservationContextsPerSession());

	/*
	 * Support for incremental search from the front-end. Synchronized because
	 * searches can take arbitrary time although in most cases they will be fast.
	 */
	private Map<String, Pair<IIndexingService.Context, List<Match>>> searchContexts = Collections
			.synchronizedMap(new HashMap<>());

	/**
	 * These are defined every time the ROI is set unless space or time are locked,
	 * in which case they will only be set if null.
	 */
	private Double spatialGridSize = null;
	private String spatialGridUnits = null;
	private Double temporalGridSize = null;
	private String temporalGridUnits = null;

	private AtomicBoolean interactive = new AtomicBoolean(false);
	/*
	 * Space and time locking defines behavior at context reset: if context is reset
	 * and resolution is locked, we keep the user-defined resolution (defining it
	 * from the current at the moment of locking if the user res is null).
	 */
	private AtomicBoolean lockSpace = new AtomicBoolean(false);
	private AtomicBoolean lockTime = new AtomicBoolean(false);

	private ActorRef rootActor;

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
						: Namespaces.INSTANCE.getNamespace(((IObservable) object).getType().getNamespace());
				/*
				 * gridsize is defined if ROI is.
				 */
				SpatialExtent roi = new SpatialExtent(regionOfInterest);
				roi.setGridResolution(this.spatialGridSize);
				roi.setGridUnit(this.spatialGridUnits);

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

			throw new KlabContextualizationException("Cannot observe " + urn + ": unknown or no context established");
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
		for (IRuntimeScope context : observationContexts) {
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
	public void registerObservationContext(IRuntimeScope runtimeContext) {

		if (!observationContexts.offerFirst(runtimeContext)) {
			disposeObservation(observationContexts.pollLast());
			observationContexts.addFirst(runtimeContext);
		}
		// this is for human watchers, everything else is done by the runtime
		monitor.info("new context registered with ID " + runtimeContext.getRootSubject().getId() + " for "
				+ runtimeContext.getRootSubject());
	}

	private void disposeObservation(IRuntimeScope context) {
		// TODO dispose of the observation
		// TODO send a notification through the session monitor that the obs is now out
		// of scope.
		Logging.INSTANCE.info("Disposing of observation " + context.getRootSubject() + ": TODO");
	}

	/*
	 * ------------------------------------------------------------------------
	 * handlers for messages
	 * ------------------------------------------------------------------------
	 */

	@MessageHandler(type = IMessage.Type.ChangeSetting)
	private void handleSettingChange(final SettingChangeRequest request) {
		switch (request.getSetting()) {
		case InteractiveMode:
			this.interactive.set(Boolean.parseBoolean(request.getNewValue()));
			monitor.info("interactive mode set to " + (interactive.get() ? "on" : "off"));
			break;
		case LockSpace:
			this.lockSpace.set(Boolean.parseBoolean(request.getNewValue()));
			monitor.info("spatial resolution " + (lockSpace.get() ? "" : "un") + "locked");
			break;
		case LockTime:
			this.lockTime.set(Boolean.parseBoolean(request.getNewValue()));
			monitor.info("temporal resolution " + (lockSpace.get() ? "" : "un") + "locked");
			break;
		default:
			break;
		}
	}

	@MessageHandler(messageClass = IMessage.MessageClass.Authorization, type = IMessage.Type.NetworkStatus)
	private void handleNetworkStatusRequest(String dummy) {
		/*
		 * send back a network descriptor with all nodes we can publish to at the moment
		 * of the call.
		 */
		NetworkReference ret = new NetworkReference();
		ret.setHub(Network.INSTANCE.getHub());
		INetworkSessionIdentity network = this.getParentIdentity(INetworkSessionIdentity.class);
		if (network != null) {
			for (INodeIdentity node : network.getNodes()) {
				NodeReference desc = Network.INSTANCE.getNodeDescriptor(node.getName());
				if (desc != null) {
					if (node.getPermissions().contains(Permission.PUBLISH)) {
						ret.getPublishing().add(node.getName());
					}
					if (node.getPermissions().contains(Permission.QUERY)) {
						ret.getSearchable().add(node.getName());
					}
					desc.getAdapters().addAll(node.getAdapters());
					ret.getNodes().put(node.getName(), desc);
				}
			}
		}
		monitor.send(IMessage.MessageClass.Authorization, IMessage.Type.NetworkStatus, ret);
	}

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
	private void publishResource(final ResourcePublishRequest request, IMessage.Type type) {

		ResourcePublishResponse response = new ResourcePublishResponse();
		if (type == IMessage.Type.PublishLocalResource) {
			response.setOriginalUrn(request.getUrn());
			IResource resource = Resources.INSTANCE.resolveResource(request.getUrn());
			if (resource == null || resource.hasErrors()) {
				response.setError("Resource has errors or is unknown to the engine");
			} else {
				try {
					String ticketId = Resources.INSTANCE
							.submitResource(resource, request.getNode().getId(), request.getSuggestedName()).getId();
					response.setTicketId(ticketId);
				} catch (Throwable e) {
					response.setError(e.getMessage());
				}
			}
		} else {
			response.setError("Updating of public resources is still unimplemented");
		}
		monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceSubmitted, response);
	}

	@MessageHandler
	private void handleTicketRequest(final TicketRequest request) {

		TicketResponse ret = new TicketResponse();

		if (request.getTicketId() != null) {
			
		} else {
			
		}
		
		monitor.send(IMessage.MessageClass.EngineLifecycle, IMessage.Type.TicketResponse, ret);
	}

	@MessageHandler
	private void handleResourceCRUDRequest(final ResourceCRUDRequest request, IMessage.Type type) {

		if (request.getOperation() == CRUDOperation.CREATE) {

			IResource resource = Resources.INSTANCE.createLocalResource(request, monitor);
			if (resource != null) {
				monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceCreated,
						((Resource) resource).getReference());
			}

		} else {

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

				} else if (request.getOperation() == CRUDOperation.UPDATE) {

					resource = Resources.INSTANCE.getLocalResourceCatalog().removeDefinition(urn);
					((Resource) resource).update(request);
					Resources.INSTANCE.getLocalResourceCatalog().put(urn, resource);
					monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceUpdated,
							((Resource) resource).getReference());

				}
			}

		}
	}

	@MessageHandler
	private void importResource(final ResourceImportRequest request, IMessage.Type type) {

		if (type == IMessage.Type.ImportResource) {

			IProject project = Resources.INSTANCE.getProject(request.getProjectName());
			if (project == null) {
				monitor.error("cannot import resource: project " + request.getProjectName() + " is unknown");
			} else {
				new Thread() {

					@Override
					public void run() {
						if (request.isBulkImport()) {
							for (IResource resource : Resources.INSTANCE.importResources(request.getImportUrl(),
									project, request.getAdapter())) {
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

		} else if (type == IMessage.Type.ImportIntoResource) {

			IResource resource = Resources.INSTANCE.resolveResource(request.getTargetResourceUrn());
			if (resource == null) {
				monitor.error("cannot import into resource: URN " + request.getTargetResourceUrn() + " is unknown");
			} else {
				new Thread() {

					@Override
					public void run() {
						if (Resources.INSTANCE.importIntoResource(request.getImportUrl(), resource, getMonitor())) {
							monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceUpdated,
									((Resource) resource).getReference());
						} else {
							// TODO complain to client
						}
					}

				}.start();
			}

			// TODO
		}
	}

	@MessageHandler
	private void setRegionOfInterest(SpatialExtent extent) {

		Envelope envelope = Envelope.create(extent.getEast(), extent.getWest(), extent.getSouth(), extent.getNorth(),
				Projection.getLatLon());
		ScaleReference scale = new ScaleReference();

		if (!lockSpace.get() || this.spatialGridSize == null) {
			Pair<Integer, String> rres = envelope.getResolutionForZoomLevel();
			this.spatialGridSize = (double) rres.getFirst();
			this.spatialGridUnits = rres.getSecond();
		}

		Pair<Double, String> resolution = new Pair<>(this.spatialGridSize, this.spatialGridUnits);
		Unit sunit = Unit.create(resolution.getSecond());
		int scaleRank = envelope.getScaleRank();
		scale.setEast(envelope.getMaxX());
		scale.setWest(envelope.getMinX());
		scale.setNorth(envelope.getMaxY());
		scale.setSouth(envelope.getMinY());
		scale.setSpaceUnit(resolution.getSecond());
		scale.setSpaceResolution(resolution.getFirst());
		scale.setSpaceResolutionConverted(sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS).doubleValue());
		scale.setSpaceResolutionDescription(
				NumberFormat.getInstance().format(scale.getSpaceResolutionConverted()) + " " + this.spatialGridUnits);
		scale.setResolutionDescription(
				NumberFormat.getInstance().format(scale.getSpaceResolutionConverted()) + " " + this.spatialGridUnits);
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

		if (action.getMatchId().startsWith("klab:")) {
			// TODO/FIXME: use a more robust test
			observe(action.getMatchId());
			return;
		}

		Context newContext = action.isAdded() ? ctx.getFirst().accept(ctx.getSecond().get(action.getMatchIndex()))
				: ctx.getFirst().previous();

		searchContexts.put(contextId, new Pair<>(newContext, new ArrayList<>()));
	}

	@MessageHandler(type = IMessage.Type.DataflowNodeDetail)
	private void handleDataflowAction(DataflowState state) {

		IRuntimeScope context = findContext(state.getContextId());
		if (context != null) {
			Flowchart.Element element = context.getContextualizationStrategy().findDataflowElement(state.getNodeId());
			if (element != null) {
				String documentation = DataflowDocumentation.INSTANCE.getDocumentation(element, context);
				if (documentation != null) {
					System.out.println(documentation);
					monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.DataflowDocumentation,
							new DataflowDetail(state.getNodeId(), documentation,
									element.getType() == ElementType.RESOURCE
											|| element.getType() == ElementType.TABLE));
				}
			} else {
				System.out.println("element not found: " + state.getNodeId());
			}
		} else {
			System.out.println("context not found: " + state.getContextId());
		}
	}

	private IRuntimeScope findContext(String contextId) {
		for (IRuntimeScope ctx : observationContexts) {
			if (ctx.getRootSubject().getId().equals(contextId)) {
				return ctx;
			}
		}
		return null;
	}

	@MessageHandler
	private void handleSearchRequest(SearchRequest request, IMessage message) {

		final String contextId = request.getContextId() == null ? NameGenerator.shortUUID() : request.getContextId();
		if (request.getContextId() == null
				|| searchContexts.get(contextId) == null && searchContexts.get(contextId).getFirst() == null) {
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
					// response.setLast(true);

					final Pair<Context, List<Match>> context = searchContexts.get(contextId);

					// happens if lots of time passes between queries
					if (context.getFirst() == null) {
						return;
					}

					response.setParenthesisDepth(context.getFirst().getDepth());

					if (request.getSearchMode() == SearchRequest.Mode.FREETEXT) {

						// TODO also lookup local matches and spawn search for remote ones to answer
						// afterwards.
						List<Match> matches = new ArrayList<>();
						int i = 0;
						for (Location location : Geocoder.INSTANCE.lookup(request.getQueryString())) {
							if ("relation".equals(location.getOsm_type())) {

								SearchMatch match = new SearchMatch(location.getURN(), location.getName(),
										location.getDescription(), IKimConcept.Type.SUBJECT);
								match.setIndex(i++);
								response.getMatches().add(match);
								matches.add(new org.integratedmodelling.klab.engine.indexing.SearchMatch(match));
							}
						}

					} else if (request.isDefaultResults()) {

						/*
						 * These come from the user's groups. They should eventually be linked to
						 * session history and preferences.
						 */
						List<Match> matches = new ArrayList<>();
						int i = 0;
						for (ObservableReference observable : Authentication.INSTANCE
								.getDefaultObservables(Session.this)) {
							SearchMatch match = new SearchMatch(observable.getObservable(), observable.getLabel(),
									observable.getDescription(), observable.getSemantics(), observable.getState(),
									observable.getExtendedDescription());
							match.setIndex(i++);
							response.getMatches().add(match);
							matches.add(new org.integratedmodelling.klab.engine.indexing.SearchMatch(match));
						}
						searchContexts.put(contextId, new Pair<Context, List<Match>>(context.getFirst(), matches));

					} else {

						Match lastMatch = context.getFirst().getAcceptedMatch();
						boolean literalMatch = false;

						if (lastMatch == null || lastMatch.getTokenClass() == TokenClass.TOKEN) {

							if (request.getQueryString().equals("(")) {

								// TODO open submatch with empty head of list
								System.out.println("OPEN PARENTHESIS");
								literalMatch = true;

							} else if (request.getQueryString().equals("(")) {

								// TODO close submatch and collapse meaning
								System.out.println("OPEN PARENTHESIS");
								literalMatch = true;

							} else {

								/*
								 * proceed to querying next tokens
								 */
								List<Match> matches = Indexing.INSTANCE.query(request.getQueryString(),
										context.getFirst());

								int i = 0;
								for (Match match : matches) {
									SearchMatch m = new SearchMatch();
									m.getSemanticType().addAll(match.getConceptType());
									m.setMainSemanticType(Kim.INSTANCE.getFundamentalType(match.getConceptType()));
									m.setMatchType(match.getMatchType());
									m.setName(match.getName());
									m.setId(match.getId());
									m.setDescription(match.getDescription());
									m.setIndex(i++);
									m.setNextTokenClass(match.getTokenClass());
									response.getMatches().add(m);
								}

								searchContexts.put(contextId,
										new Pair<Context, List<Match>>(context.getFirst(), matches));

							}

						} else if (lastMatch.getTokenClass() == TokenClass.DOUBLE) {

							try {
								Double.parseDouble(request.getQueryString());
							} catch (Throwable t) {
								response.setError(true);
								response.setErrorMessage("invalid floating point number: " + request.getQueryString());
							}

							literalMatch = true;

						} else if (lastMatch.getTokenClass() == TokenClass.INTEGER) {

							try {
								Integer.parseInt(request.getQueryString());
							} catch (Throwable t) {
								response.setError(true);
								response.setErrorMessage("invalid integer number: " + request.getQueryString());
							}
							literalMatch = true;

						} else if (lastMatch.getTokenClass() == TokenClass.UNIT) {

							try {
								Units.INSTANCE.getUnit(request.getQueryString());
							} catch (Throwable t) {
								response.setError(true);
								response.setErrorMessage("invalid unit: " + request.getQueryString());
							}
							// TODO ensure the unit is compatible
							literalMatch = true;

						} else if (lastMatch.getTokenClass() == TokenClass.CURRENCY) {

							try {
								Currencies.INSTANCE.getCurrency(request.getQueryString());
							} catch (Throwable t) {
								response.setError(true);
								response.setErrorMessage("invalid currency: " + request.getQueryString());
							}
							// TODO ensure the currency is compatible
							literalMatch = true;

						}

						if (literalMatch) {
							List<Match> matches = new ArrayList<>();
							SearchMatch m = new SearchMatch();
							m.getSemanticType().addAll(lastMatch.getConceptType());
							m.setMainSemanticType(Kim.INSTANCE.getFundamentalType(lastMatch.getConceptType()));
							m.setMatchType(lastMatch.getMatchType());
							m.setName(request.getQueryString());
							m.setId(request.getQueryString());
							m.setDescription(request.getQueryString());
							m.setIndex(0);
							m.setNextTokenClass(TokenClass.TOKEN);
							response.getMatches().add(m);
							matches.add(new org.integratedmodelling.klab.engine.indexing.SearchMatch(m));
							searchContexts.put(contextId, new Pair<Context, List<Match>>(context.getFirst(), matches));
						}
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
				file = project.createNamespace(request.getAssetId(), false, request.getParameters() != null
						&& "true".equals(request.getParameters().get(ProjectModificationRequest.PRIVATE_OPTION)));
				break;
			default:
				// shut up
				break;
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

			file = project.createNamespace(request.getAssetId(), true, request.getParameters() != null
					&& "true".equals(request.getParameters().get(ProjectModificationRequest.PRIVATE_OPTION)));
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
			Resources.INSTANCE.deleteProject(request.getProjectId());
			break;
		case DeleteResource:
			break;
		default:
			break;
		}
	}

	@MessageHandler(type = IMessage.Type.ResetContext)
	private void handleResetContextRequest(String dummy) {
		if (!lockSpace.get()) {
			this.spatialGridSize = null;
			this.spatialGridUnits = null;
		}
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
	private void handleRecontextualizationRequest(ContextualizationRequest request) {
		System.out.println(request);
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

		// TODO add observer to other observer (with parameter in request)
		// TODO substitute observer to existing context (not done at the moment)
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
		this.spatialGridSize = Units.INSTANCE.METERS
				.convert(scaleRef.getSpaceResolutionConverted(), Unit.create(scaleRef.getSpaceUnit())).doubleValue();
		this.spatialGridUnits = scaleRef.getSpaceUnit();
	}

	@MessageHandler
	private void handleProjectLoadRequest(final ProjectLoadRequest request, IMessage message) {
		new Thread() {

			@Override
			public void run() {
				ProjectLoadResponse response = new ProjectLoadResponse();
				Resources.INSTANCE.getLoader().loadProjectFiles(request.getProjectLocations());

				for (File file : request.getProjectLocations()) {
					IKimProject project = Kim.INSTANCE.getProjectIn(file);
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

		for (IRuntimeScope ctx : observationContexts) {
			ret.getRootObservations().put(ctx.getRootSubject().getId(), Observations.INSTANCE
					.createArtifactDescriptor(ctx.getRootSubject(), null, ctx.getScale().initialization(), 0, false));
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

	@Override
	public boolean isInteractive() {
		return interactive.get();
	}

	/**
	 * Call this one to validate and register a resource after it's been built by an
	 * external agent. NOTE: this resource is built without the involvement of an
	 * adapter, so it should not be used with user input or anything not previously
	 * established as valid.
	 * 
	 * @param ret
	 * @return
	 */
	public IResource registerResource(IResource ret) {
		// invoke the service to validate, register and notify
		ret = Resources.INSTANCE.registerResource(ret);
		if (ret != null) {
			monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceCreated,
					((Resource) ret).getReference());
		}
		return ret;
	}

	/**
	 * TARIK this is the root actor in a Session, created on demand and child of the
	 * root actor in the engine, which is held by the runtime system. Probably a
	 * different call should be used (and maybe even a different type - no idea)!
	 * The {@link EventBus} (part of {@link RuntimeScope} should call this as the
	 * father of the context's actor, to create it and all the successive ones as
	 * new observations are created.
	 * 
	 * @return
	 */
	public ActorRef getRootActor() {
		if (this.rootActor == null) {
			this.rootActor = ((DefaultRuntimeProvider) Klab.INSTANCE.getRuntimeProvider()).getActorSystem()
					.actorFor(getId());
		}
		return this.rootActor;
	}

}

package org.integratedmodelling.klab.engine.runtime;

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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;

import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Indexing;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
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
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.InterruptTask;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.RunScriptRequest;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchMatchAction;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.CollectionUtils;
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
	public Future<ISubject> observe(String urn, String... scenarios) throws KlabException {
		touch();
		IKimObject object = Resources.INSTANCE.getModelObject(urn);
		if (!(object instanceof Observer)) {
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

	@MessageHandler
	private void importResource(final ResourceImportRequest request) {
		IProject project = Resources.INSTANCE.getLocalWorkspace().getProject(request.getProjectName());
		if (project != null) {
			monitor.error("cannot import resource: project " + request.getProjectName() + " is unknown");
		} else {

			new Thread() {

				@Override
				public void run() {
					IResource resource = Resources.INSTANCE.importResource(request.getImportUrl(), project);
					if (resource != null) {
						monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
								((Resource) resource).getReference());
					}
				}

			}.start();
		}
	}

	@MessageHandler
	private void setRegionOfInterest(SpatialExtent extent) {
		monitor.debug("setting ROI = " + extent);
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
		final Engine engine = getParentIdentity(Engine.class);
		if (engine != null) {
			engine.run(request.getScriptUrl());
		}
	}

	@MessageHandler
	private void handleObservationRequest(final ObservationRequest request) {

		/*
		 * TODO if we have no context in the request but we have a ROI, create the
		 * context from the ROI and block the thread until it's observed
		 */
		/*
		 * TODO observe the URN in the request
		 */
		System.out.println("Observation request: " + request);
	}

	/*
	 * REST communication
	 */
	public SessionReference getSessionReference() {

		SessionReference ret = new SessionReference();

		ret.setTimeEstablished(creation);
		ret.setTimeLastJoined(lastJoin);
		ret.setTimeRetrieved(System.currentTimeMillis());
		ret.setTimeLastActivity(lastActivity);

		for (IRuntimeContext ctx : observationContexts) {
			ret.getRootObservations().put(ctx.getRootSubject().getId(), Observations.INSTANCE
					.createArtifactDescriptor(ctx.getRootSubject(), null, ITime.INITIALIZATION, 0));
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

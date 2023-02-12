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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActorsBehavior;
import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Currencies;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Indexing;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.data.CRUDOperation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.api.knowledge.IAuthority.Identity;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.cli.CommandConsole;
import org.integratedmodelling.klab.cli.DebuggerConsole;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder.Location;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.ActorReference;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.components.runtime.actors.TestScope;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.resources.Codelist;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.debugger.Debug;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.engine.services.scope.actors.SessionActor;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.syntax.SemanticExpression;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.AuthorityQueryRequest;
import org.integratedmodelling.klab.rest.AuthorityQueryResponse;
import org.integratedmodelling.klab.rest.AuthorityResolutionRequest;
import org.integratedmodelling.klab.rest.ConsoleNotification;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ContextualizationRequest;
import org.integratedmodelling.klab.rest.DataflowDetail;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.DocumentationReference;
import org.integratedmodelling.klab.rest.EngineAction;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.GroupReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.InterruptTask;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.MenuAction;
import org.integratedmodelling.klab.rest.NetworkReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.NodeReference.Permission;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ProjectActionResponse;
import org.integratedmodelling.klab.rest.ProjectLoadRequest;
import org.integratedmodelling.klab.rest.ProjectLoadResponse;
import org.integratedmodelling.klab.rest.ProjectModificationNotification;
import org.integratedmodelling.klab.rest.ProjectModificationRequest;
import org.integratedmodelling.klab.rest.QueryStatusResponse;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.ResourceOperationRequest;
import org.integratedmodelling.klab.rest.ResourceOperationResponse;
import org.integratedmodelling.klab.rest.ResourcePublishRequest;
import org.integratedmodelling.klab.rest.ResourcePublishResponse;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.ScenarioSelection;
import org.integratedmodelling.klab.rest.SearchMatch;
import org.integratedmodelling.klab.rest.SearchMatch.TokenClass;
import org.integratedmodelling.klab.rest.SearchMatchAction;
import org.integratedmodelling.klab.rest.SearchRequest;
import org.integratedmodelling.klab.rest.SearchRequest.Mode;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.SettingChangeRequest;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.SpatialLocation;
import org.integratedmodelling.klab.rest.StyledKimToken;
import org.integratedmodelling.klab.rest.TestRun;
import org.integratedmodelling.klab.rest.TestStatistics;
import org.integratedmodelling.klab.rest.TicketRequest;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.WatchRequest;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import akka.actor.typed.ActorRef;
import groovy.lang.GroovyObjectSupport;

/**
 * Engine session. Implements UserDetails to be directly usable as a principal in Spring security.
 * 
 * TODO this must become a front-end for session actors, managed uniformly within the runtime
 * singleton.
 * 
 * TODO the session state must become the IObserver interface; a session represents the
 * observer/user so it should implement it
 * 
 * @author ferdinando.villa
 *
 */
public class Session extends GroovyObjectSupport
        implements
            ISession,
            IActorIdentity<KlabMessage>,
            UserDetails,
            IMessageBus.Relay {

    private static final long serialVersionUID = -1571090827271892549L;

    public static class Estimate {

        public long estimatedCost;
        public ContextRequest contextRequest;
        public ObservationRequest observationRequest;

        public Estimate(long cost, ContextRequest contextRequest, ObservationRequest observationRequest) {

            this.estimatedCost = cost;
            if (contextRequest != null) {
                contextRequest.setEstimate(false);
                contextRequest.setEstimatedCost(cost);
            }
            if (observationRequest != null) {
                observationRequest.setEstimate(false);
                observationRequest.setEstimatedCost(cost);
            }
            this.contextRequest = contextRequest;
            this.observationRequest = observationRequest;
        }
    }

    Monitor monitor;
    String token = "s" + NameGenerator.shortUUID();
    IUserIdentity user;
    List<Listener> listeners = new ArrayList<>();
    boolean closed = false;
    Set<GrantedAuthority> authorities = new HashSet<>();
    long lastActivity = System.currentTimeMillis();
    long creation = System.currentTimeMillis();
    long lastJoin = System.currentTimeMillis();
    boolean isDefault = false;
    Set<String> relayIdentities = new HashSet<>();
    ActorRef<KlabMessage> actor;
    private SessionState globalState = new SessionState(this);
    private View view;
    private Map<String, IConsole> consoles = new HashMap<>();
    // estimated costs of jobs by ticket ID
    private Map<String, Estimate> estimates = Collections.synchronizedMap(new HashMap<>());
    private TestScope rootTestScope = null;

    // tracks the setting of the actor so we can avoid the ask pattern
    private AtomicBoolean actorSet = new AtomicBoolean(Boolean.FALSE);

    /**
     * A scheduler to periodically collect observation and task garbage
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /*
     * Tasks created in this session, managed as task/script start and end. Content may be a IScript
     * or a ITask.
     */
    Map<String, Future<?>> tasks = Collections.synchronizedMap(new HashMap<>());

    /*
     * Tasks completed in this session. Allows for tracking completed tasks on the remote engine.
     * Content may be a IScript or a ITask.
     */
    Map<String, Future<?>> completedTasks = new HashMap<>();

    /*
     * The contexts for all root observations built in this session, up to the configured number,
     * most recent first. Synchronized.
     */
    Deque<IRuntimeScope> observationContexts = new LinkedBlockingDeque<>(
            Configuration.INSTANCE.getMaxLiveObservationContextsPerSession());

    /*
     * Support for incremental search from the front-end. Synchronized because searches can take
     * arbitrary time although in most cases they will be fast.
     */
    private Map<String, Pair<IIndexingService.Context, List<Match>>> searchContexts = Collections
            .synchronizedMap(new HashMap<>());

    private AtomicBoolean interactive = new AtomicBoolean(false);
    private AtomicLong lastNetworkCheck = new AtomicLong(0);

    // listeners for script/testcase end
    private Map<String, Runnable> exitListeners = Collections.synchronizedMap(new HashMap<>());

    // a simple monitor that will only compile all notifications into a list to be
    // sent back to clients
    class ReportingMonitor implements IMonitor {

        List<Notification> notifications = new ArrayList<>();
        int errors = 0;
        Inspector inspector;

        @Override
        public void info(Object... info) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(info);
            notifications.add(new Notification(message.getFirst(), Level.INFO.getName(), System.currentTimeMillis()));
        }

        @Override
        public void warn(Object... o) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            notifications.add(new Notification(message.getFirst(), Level.WARNING.getName(), System.currentTimeMillis()));
        }

        @Override
        public void error(Object... o) {
            errors++;
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            notifications.add(new Notification(message.getFirst(), Level.SEVERE.getName(), System.currentTimeMillis()));
        }

        @Override
        public void debug(Object... o) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            notifications.add(new Notification(message.getFirst(), Level.FINE.getName(), System.currentTimeMillis()));
        }

        @Override
        public void send(Object... message) {
        }

        @Override
        public Future<IMessage> ask(Object... message) {
            return null;
        }

        @Override
        public void post(Consumer<IMessage> handler, Object... message) {
        }

        @Override
        public IIdentity getIdentity() {
            return null;
        }

        @Override
        public boolean isInterrupted() {
            return false;
        }

        @Override
        public boolean hasErrors() {
            return errors > 0;
        }

        @Override
        public void addWait(int seconds) {
            // TODO Auto-generated method stub

        }

        @Override
        public int getWaitTime() {
            // TODO Auto-generated method stub
            return 0;
        }
    }

    public Session(Engine engine, IUserIdentity user) {
        this.user = user;
        this.monitor = ((Monitor) engine.getMonitor()).get(this);
        this.lastNetworkCheck.set(System.currentTimeMillis());
        this.authorities.add(new SimpleGrantedAuthority(Roles.SESSION));
        Authentication.INSTANCE.registerSession(this);
    }

    public void touch() {
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
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.MODEL_SESSION;
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
        return (IEngineUserIdentity) user;
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

    /**
     * Get the root runtime scope of the passed observation
     */
    public IRuntimeScope getRootScope(String observationId) {
        // start at the most recent
        for (IRuntimeScope context : observationContexts) {
            IObservation ret = context.getObservation(observationId);
            if (ret != null) {
                return context;
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
     * Register a task. It may be a ITask or a IScript, which only have the Future identity in
     * common.
     * 
     * @param task
     * @param monitor2
     */
    public void registerTask(Future<?> task) {
        String id = task instanceof ITask ? ((ITask<?>) task).getId() : ((IScript) task).getId();
        this.tasks.put(id, task);
    }

    /**
     * Interrupt the passed task, notifying its monitor for computations to terminate gracefully.
     * Return true if there was a task to interrupt and it was indeed canceled.
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
     * Register a task. It may be a ITask or a IScript, which only have the Future identity in
     * common.
     * 
     * @param task
     */
    public void unregisterTask(Future<?> task) {
        this.tasks.remove(task instanceof ITask ? ((ITask<?>) task).getId() : ((IScript) task).getId());
        storeCompletedTask(task);
    }

    /**
     * Store a completed task. It may be a ITask or a IScript, which only have the Future identity
     * in common.
     * 
     * @param task
     */
    public void storeCompletedTask(Future<?> task) {
        String id = task instanceof ITask ? ((ITask<?>) task).getId() : ((IScript) task).getId();
        this.completedTasks.putIfAbsent(id, task);
    }

    /**
     * Register the runtime context of a new observation. If needed, dispose of the oldest
     * observation made.
     * 
     * @param runtimeContext
     */
    public void registerObservationContext(IRuntimeScope runtimeContext) {

        this.globalState.setContext(runtimeContext);

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
     * ------------------------------------------------------------------------ handlers for
     * messages ------------------------------------------------------------------------
     */

    @MessageHandler(type = IMessage.Type.ChangeSetting)
    private void handleSettingChange(final SettingChangeRequest request) {

        switch(request.getSetting()) {
        case InteractiveMode:
            this.interactive.set(Boolean.parseBoolean(request.getNewValue()));
            monitor.info("interactive mode set to " + (interactive.get() ? "on" : "off"));
            break;
        default:
            this.globalState.register(request);
            break;
        }
    }

    @MessageHandler(messageClass = IMessage.MessageClass.Authorization, type = IMessage.Type.NetworkStatus)
    private void handleNetworkStatusRequest(String urnOrDummy, IMessage message) {

        /*
         * If message contains a URN, check on the status of the correspondent resource
         */
        if (Urns.INSTANCE.isUrn(urnOrDummy)) {
            IResource ref = Resources.INSTANCE.resolveResource(urnOrDummy);
            if (ref != null) {
                boolean online = Resources.INSTANCE.isResourceOnline(ref);
                monitor.send(Message.create(this.token, IMessage.MessageClass.ResourceLifecycle,
                        online ? IMessage.Type.ResourceOnline : IMessage.Type.ResourceOffline, ((Resource) ref).getReference())
                        .inResponseTo(message));
            } else {
                monitor.send(Message
                        .create(this.token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceUnknown, urnOrDummy)
                        .inResponseTo(message));
            }
            return;
        }

        /*
         * if message didn't contain a URN, send back a network descriptor with all nodes we can
         * publish to at the moment of the call.
         */
        NetworkReference ret = new NetworkReference();
        ret.setHub(Network.INSTANCE.getHub());
        INetworkSessionIdentity network = this.getParentIdentity(INetworkSessionIdentity.class);
        if (network != null) {
            for (INodeIdentity node : network.getNodes()) {
                NodeReference desc = new NodeReference(node);
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

        ret.getOnlineUrns().addAll(Resources.INSTANCE.getPublicResourceCatalog().getOnlineUrns());

        for (ITicket resolved : Klab.INSTANCE.getTicketManager().getResolvedAfter(lastNetworkCheck.get())) {
            ret.getResolvedTickets().add(TicketManager.encode(resolved));
        }

        this.lastNetworkCheck.set(System.currentTimeMillis());

        monitor.send(IMessage.MessageClass.Authorization, IMessage.Type.NetworkStatus, ret);
    }

    @MessageHandler(type = IMessage.Type.FeatureAdded)
    private void handleFeatureAdded(final SpatialLocation location) {
        this.globalState.setShape((location.getWktShape() == null || location.getWktShape().isEmpty())
                ? null
                : Shape.create("EPSG:4326 " + location.getWktShape()));
    }

    @MessageHandler
    private void publishResource(final ResourcePublishRequest request, IMessage.Type type) {

        ResourcePublishResponse response = new ResourcePublishResponse();
        if (type == IMessage.Type.PublishLocalResource) {

            Map<String, String> publicationData = new HashMap<>();

            if (request.getPermissions() != null) {
                publicationData.put(IMetadata.IM_PERMISSIONS, request.getPermissions());
            }
            if (request.getSuggestedName() != null) {
                publicationData.put(IMetadata.IM_SUGGESTED_RESOURCE_ID, request.getSuggestedName());
            }
            if (request.getSuggestedNamespace() != null) {
                publicationData.put(IMetadata.IM_SUGGESTED_NAMESPACE_ID, request.getSuggestedNamespace());
            }
            if (request.getSuggestedCatalog() != null) {
                publicationData.put(IMetadata.IM_SUGGESTED_CATALOG_ID, request.getSuggestedCatalog());
            }

            response.setOriginalUrn(request.getUrn());
            IResource resource = Resources.INSTANCE.resolveResource(request.getUrn());
            if (resource == null || resource.hasErrors()) {
                response.setError("Resource has errors or is unknown to the engine");
            } else {
                try {
                    String ticketId = Resources.INSTANCE.submitResource(resource, request.getNode().getId(), publicationData)
                            .getId();
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
    private void handleScenarioSelection(final ScenarioSelection request) {
        this.getState().setActiveScenarios(request.getScenarios());
    }

    @MessageHandler
    private void handleResourceOperation(final ResourceOperationRequest request, IMessage message) {

        final IResource resource = Resources.INSTANCE.resolveResource(request.getUrn());
        if (resource == null) {
            // send back resource not found, very unlikely;
            return;
        }

        ResourceOperationRequest.Standard op = null;
        try {
            op = ResourceOperationRequest.Standard.valueOf(request.getOperation());
        } catch (IllegalArgumentException e) {
            // leave it null;
        }

        // uff
        final ResourceOperationRequest.Standard operation = op;

        new Thread(){

            @Override
            public void run() {

                ResourceOperationResponse response = new ResourceOperationResponse();
                ReportingMonitor rmonitor = new ReportingMonitor();
                IResource res = resource;

                if (operation != null) {
                    switch(operation) {
                    case Revalidate:
                        Resources.INSTANCE.revalidate(resource, rmonitor);
                        break;
                    }
                } else {
                    IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());
                    res = adapter.getValidator().performOperation(resource, request.getOperation(),
                            Parameters.create(request.getParameters()), Resources.INSTANCE.getCatalog(resource), rmonitor);
                }

                response.setUrn(resource.getUrn());
                response.setOperation(request.getOperation());
                response.getNotifications().addAll(rmonitor.notifications);
                monitor.send(Message.create(Session.this.getId(), IMessage.MessageClass.ResourceLifecycle,
                        IMessage.Type.ResourceInformation, response).inResponseTo(message));

                /*
                 * TODO if there are no errors or a non-standard operation was chosen, refresh the
                 * resource details in the client by sending the resource again, using the result of
                 * performOperation (i.e. res, not resource).
                 */
            }

        }.start();

    }

    @MessageHandler
    private void handleResourceCRUDRequest(final ResourceCRUDRequest request, IMessage message) {

        if (request.getOperation() == CRUDOperation.CREATE) {

            IResource resource = Resources.INSTANCE.createLocalResource(request, monitor);
            if (resource != null) {
                monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceCreated,
                        ((Resource) resource).getReference())/*
                                                              * .inResponseTo(message)
                                                              */);
            }

        } else if (message.getType() == IMessage.Type.CreateCodelist) {

            IResource resource = Resources.INSTANCE.resolveResource(request.getResourceUrns().iterator().next());
            if (resource == null) {
                monitor.error("requested resource not found: " + request.getResourceUrns().iterator().next());
                return;
            }

            ICodelist codelist = Resources.INSTANCE.createCodelist(resource, request.getCodelistAttribute(), monitor);
            if (codelist != null) {
                monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.CodelistCreated,
                        ((Codelist) codelist).getReference())/*
                                                              * .inResponseTo(message)
                                                              */);
            }

        } else if (message.getType() == IMessage.Type.GetCodelist) {

            IResource resource = Resources.INSTANCE.resolveResource(request.getResourceUrns().iterator().next());
            if (resource == null) {
                monitor.error("requested resource not found: " + request.getResourceUrns().iterator().next());
                return;
            }

            ICodelist codelist = Resources.INSTANCE.getCodelist(resource, request.getCodelistAttribute(), monitor);
            if (codelist != null) {
                monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.CodelistCreated,
                        ((Codelist) codelist).getReference())/*
                                                              * .inResponseTo(message)
                                                              */);
            }

        } else if (message.getType() == IMessage.Type.UpdateCodelist) {

            IResource resource = Resources.INSTANCE.resolveResource(request.getResourceUrns().iterator().next());
            if (resource == null) {
                monitor.error("requested resource not found: " + request.getResourceUrns().iterator().next());
                return;
            }

            Resources.INSTANCE.updateCodelist(resource, request.getCodelist(), monitor);

        } else if (message.getType() == IMessage.Type.DeleteCodelist) {

            IResource resource = Resources.INSTANCE.resolveResource(request.getResourceUrns().iterator().next());
            if (resource == null) {
                monitor.error("requested resource not found: " + request.getResourceUrns().iterator().next());
                return;
            }

            Resources.INSTANCE.deleteCodelist(resource, request.getCodelist(), monitor);

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
                    monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceDeleted,
                            ((Resource) resource).getReference())/* .inResponseTo(message) */);
                    resource = Resources.INSTANCE.getLocalResourceCatalog().move(resource, destinationProject);
                    monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
                            ((Resource) resource).getReference())/* .inResponseTo(message) */);
                } else if (request.getOperation() == CRUDOperation.COPY) {

                    IProject destinationProject = Resources.INSTANCE.getProject(request.getDestinationProject());
                    if (destinationProject == null) {
                        monitor.error("resource target is an unknown project: canceling operation");
                        return;
                    }

                    resource = Resources.INSTANCE.getLocalResourceCatalog().copy(resource, destinationProject);
                    monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceImported,
                            ((Resource) resource).getReference())/* .inResponseTo(message) */);
                } else if (request.getOperation() == CRUDOperation.DELETE) {

                    resource = Resources.INSTANCE.getLocalResourceCatalog().remove(urn);
                    monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceDeleted,
                            ((Resource) resource).getReference())/* .inResponseTo(message) */);

                } else if (request.getOperation() == CRUDOperation.UPDATE) {

                    resource = Resources.INSTANCE.updateResource(urn, request);
                    monitor.send(Message.create(token, IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceUpdated,
                            ((Resource) resource).getReference()).inResponseTo(message));

                }
            }

        }
    }

    @MessageHandler
    private void handleAuthorityResolutionRequest(AuthorityResolutionRequest request, IMessage message) {
        Identity ret = Authorities.INSTANCE.getIdentity(request.getAuthority(), request.getIdentity());
        if (ret == null) {
            ret = new AuthorityIdentity();
            ret.getNotifications().add(new Notification(
                    "Authority identity " + request.getAuthority() + ":" + request.getIdentity() + " could not be resolved",
                    Level.SEVERE.getName()));
        } else if (ret.getDescription() != null) {
            // reformat the documentation as HTML
            ((AuthorityIdentity) ret).setDescription(MarkdownUtils.INSTANCE.format(ret.getDescription()));
        }
        monitor.send(Message.create(this.token, IMessage.MessageClass.KimLifecycle, IMessage.Type.AuthorityDocumentation, ret)
                .inResponseTo(message));
    }

    @MessageHandler
    private void handleAuthorityQueryRequest(AuthorityQueryRequest request, IMessage message) {

        AuthorityQueryResponse ret = new AuthorityQueryResponse();

        IAuthority authority = Authorities.INSTANCE.getAuthority(request.getAuthorityId());
        if (authority == null) {
            ret.setError("Authority " + request.getAuthorityId() + " is inaccessible or non-existent");
        } else if (authority.getCapabilities().isSearchable()) {
            for (IAuthority.Identity identity : authority.search(request.getQueryString(), request.getAuthorityCatalog())) {
                if (identity instanceof AuthorityIdentity) {
                    ret.getMatches().add((AuthorityIdentity) identity);
                }
            }
        } else {
            Identity identity = authority.getIdentity(request.getQueryString(), request.getAuthorityCatalog());
            if (identity instanceof AuthorityIdentity) {
                ret.getMatches().add((AuthorityIdentity) identity);
            } else {
                ret.setError("Identity " + request.getAuthorityId() + " produced an invalid or null result");
            }
        }

        monitor.send(Message.create(this.token, IMessage.MessageClass.UserInterface, IMessage.Type.AuthoritySearchResults, ret)
                .inResponseTo(message));
    }

    @MessageHandler
    private void importResource(final ResourceImportRequest request, IMessage.Type type) {

        if (type == IMessage.Type.ImportResource) {

            IProject project = Resources.INSTANCE.getProject(request.getProjectName());
            if (project == null) {
                monitor.error("cannot import resource: project " + request.getProjectName() + " is unknown");
            } else {
                new Thread(){

                    @Override
                    public void run() {
                        if (request.isBulkImport()) {
                            for (IResource resource : Resources.INSTANCE.importResources(request.getImportUrl(), project,
                                    request.getAdapter(), request.getRegex())) {
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
                new Thread(){

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
        this.globalState.register(extent, false);
    }

    @MessageHandler
    private void interruptTask(InterruptTask request) {
        interruptTask(request.getTaskId(), request.isForceInterruption());
    }

    @MessageHandler
    private void handleMatchAction(SearchMatchAction action, IMessage message) {

        if (message.getType() == IMessage.Type.SemanticMatch) {
            SemanticExpression expression = semanticExpressions.get(action.getContextId());
            /*
             * Insert choice into current expression and setup for next search.
             */
            SearchResponse matches = expression.getData("matches", SearchResponse.class);
            if (matches != null
                    && matches.getMatches().size() > action.getMatchIndex()/*
                                                                            * which would be weird
                                                                            */) {
                acceptChoice(expression, matches.getMatches().get(action.getMatchIndex()), action.getContextId());
            }
            return;
        }

        final String contextId = action.getContextId();
        Pair<Context, List<Match>> ctx = searchContexts.get(contextId);
        if (ctx == null) {
            throw new IllegalStateException("match action has invalid context ID");
        }

        if (action.getMatchId() != null && action.getMatchId().startsWith("klab:")) {
            // TODO/FIXME: use a more robust test
            getState().submitGeolocation(action.getMatchId());
            return;
        }

        Context newContext = action.isAdded()
                ? ctx.getFirst().accept(ctx.getSecond().get(action.getMatchIndex()))
                : ctx.getFirst().previous();

        searchContexts.put(contextId, new Pair<>(newContext, new ArrayList<>()));
    }

    private void acceptChoice(SemanticExpression expression, SearchMatch searchMatch, String contextId) {

        Object input = searchMatch.getId();

        if (searchMatch.getMatchType() == Match.Type.CONCEPT) {
            input = Concepts.c(searchMatch.getId());
        } else if (searchMatch.getMatchType() == Match.Type.VALUE_OPERATOR) {
            input = ValueOperator.getOperator(searchMatch.getId());
        } else if (searchMatch.getMatchType() == Match.Type.UNARY_OPERATOR
                || searchMatch.getMatchType() == Match.Type.PREFIX_OPERATOR) {
            input = UnarySemanticOperator.forCode(searchMatch.getId());
        } else if (searchMatch.getMatchType() == Match.Type.SEMANTIC_MODIFIER
                || searchMatch.getMatchType() == Match.Type.INFIX_OPERATOR) {
            input = SemanticModifier.forCode(searchMatch.getId());
        } else if (searchMatch.getMatchType() == Match.Type.BINARY_OPERATOR) {
            input = BinarySemanticOperator.forCode(searchMatch.getId());
        } else if (searchMatch.getMatchType() == Match.Type.MODIFIER) {
            input = SemanticModifier.forCode(searchMatch.getId());
        }

        if (!expression.accept(input)) {
            monitor.error(expression.getErrorAndReset());
        }

        /*
         * send current status to client so it can be displayed
         */
        QueryStatusResponse response = new QueryStatusResponse();
        response.setContextId(contextId);
        response.getErrors().addAll(expression.getErrors());
        response.getCode().addAll(expression.getStyledCode());
        response.setCurrentType(expression.getObservableType());

        StringBuffer code = new StringBuffer(256);
        for (StyledKimToken token : response.getCode()) {
            code.append(token.getValue() + " ");
        }
        String cc = code.toString();
        if (!StringUtils.isBlank(cc) && !cc.contains("?")) {
            try {
                IObservable observable = Observables.INSTANCE.declare(cc);
                if (observable != null) {
                    response.setDescription(Observables.INSTANCE.describe(observable.getType()));
                }
            } catch (Throwable t) {
                // nothing
            }
        }

        monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.QueryStatus, response);
    }

    @MessageHandler(type = IMessage.Type.DataflowNodeDetail)
    private void handleDataflowAction(DataflowState state) {

        IRuntimeScope context = findContext(state.getContextId());
        if (context != null) {
            Flowchart.Element element = ((RuntimeScope) context).findDataflowElement(state.getNodeId());
            if (element != null) {
                String documentation = DataflowDocumentation.INSTANCE.getDocumentation(element, context);
                if (documentation != null) {
                    if (Configuration.INSTANCE.isEchoEnabled()) {
                        System.out.println(documentation);
                    }
                    monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.DataflowDocumentation,
                            new DataflowDetail(state.getNodeId(), documentation,
                                    element.getType() == ElementType.RESOURCE || element.getType() == ElementType.TABLE));
                }
            } else {
                if (Configuration.INSTANCE.isEchoEnabled()) {
                    System.out.println("element not found: " + state.getNodeId());
                }
            }
        } else {
            if (Configuration.INSTANCE.isEchoEnabled()) {
                System.out.println("context not found: " + state.getContextId());
            }
        }
    }

    @MessageHandler
    private void handleConsoleRequest(final ConsoleNotification message, final IMessage.Type messageType) {
        switch(messageType) {
        case CommandRequest:
            ConsoleNotification response = new ConsoleNotification();
            response.setCommandId(message.getCommandId());
            response.setConsoleId(message.getConsoleId());
            response.setConsoleType(message.getConsoleType());
            if (consoles.containsKey(message.getConsoleId())) {
                // set payload to
                response.setPayload(consoles.get(message.getConsoleId()).executeCommand(message.getPayload()));
            } else {
                response.setPayload("ERROR: console ID not recognized");
            }
            this.monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.CommandResponse, response);
            break;
        case ConsoleClosed:
            IConsole console = consoles.remove(message.getConsoleId());
            if (console instanceof DebuggerConsole) {
                Debug.INSTANCE.removeDebugger(message.getConsoleId());
            }
            break;
        case ConsoleCreated:
            switch(message.getConsoleType()) {
            case Console:
                consoles.put(message.getConsoleId(), new CommandConsole(message.getConsoleId(), this));
                break;
            case Debugger:
                DebuggerConsole db = new DebuggerConsole(message.getConsoleId(), this);
                consoles.put(message.getConsoleId(), db);
                Debug.INSTANCE.addDebugger(message.getConsoleId(), db.getDebugger());
                break;
            }
            break;
        default:
            break;
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

    /**
     * Move up and substitute searchContexts when done.
     */
    private Map<String, SemanticExpression> semanticExpressions = new HashMap<>();

    /**
     * The next-generation semantic search using ObservableComposer
     * 
     * @param request
     * @param message
     */
    private void semanticSearch(SearchRequest request, IMessage message) {

        if (request.isCancelSearch()) {
            semanticExpressions.remove(request.getContextId());
            searchContexts.remove(request.getContextId());
        } else {
            /*
             * spawn search thread, which will respond when done.
             */
            new Thread(){

                @Override
                public void run() {

                    SearchResponse response = setupResponse(request);

                    switch(request.getSearchMode()) {
                    case FREETEXT:
                        searchFreetext(request, response);
                        break;
                    case UNDO:

                        // client may be stupid, as mine is
                        if (request.getContextId() != null && semanticExpressions.containsKey(request.getContextId())) {

                            SemanticExpression expression = semanticExpressions.get(request.getContextId());
                            boolean ok = true;
                            if (!semanticExpressions.get(request.getContextId()).undo()) {
                                semanticExpressions.remove(request.getContextId());
                                searchContexts.remove(request.getContextId());
                                ok = false;
                            }

                            QueryStatusResponse qr = new QueryStatusResponse();
                            qr.setContextId(ok ? request.getContextId() : null);
                            if (ok) {
                                qr.getErrors().addAll(expression.getErrors());
                                qr.getCode().addAll(expression.getStyledCode());
                                qr.setCurrentType(expression.getObservableType());
                            }
                            monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.QueryStatus, qr);
                        }
                        break;

                    case OPEN_SCOPE:

                        semanticExpressions.get(response.getContextId()).accept("(");
                        QueryStatusResponse qr = new QueryStatusResponse();
                        qr.setContextId(request.getContextId());
                        qr.getErrors().addAll(semanticExpressions.get(response.getContextId()).getErrors());
                        qr.getCode().addAll(semanticExpressions.get(response.getContextId()).getStyledCode());
                        qr.setCurrentType(semanticExpressions.get(response.getContextId()).getObservableType());
                        monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.QueryStatus, qr);
                        break;

                    case CLOSE_SCOPE:

                        semanticExpressions.get(response.getContextId()).accept(")");
                        qr = new QueryStatusResponse();
                        qr.setContextId(request.getContextId());
                        qr.getErrors().addAll(semanticExpressions.get(response.getContextId()).getErrors());
                        qr.getCode().addAll(semanticExpressions.get(response.getContextId()).getStyledCode());
                        qr.setCurrentType(semanticExpressions.get(response.getContextId()).getObservableType());
                        monitor.send(IMessage.MessageClass.UserInterface, IMessage.Type.QueryStatus, qr);
                        break;

                    case SEMANTIC:

                        if (request.isDefaultResults()) {
                            setDefaultSearchResults(response.getContextId(), request, response);
                        } else {
                            SemanticExpression expression = semanticExpressions.get(response.getContextId());
                            if (expression == null) {
                                expression = SemanticExpression.create();
                                semanticExpressions.put(response.getContextId(), expression);
                            }
                            runSemanticSearch(expression, request, response);
                        }

                        break;
                    }

                    response.setElapsedTimeMs(System.currentTimeMillis() - response.getElapsedTimeMs());
                    monitor.send(Message
                            .create(token, IMessage.MessageClass.Query, IMessage.Type.QueryResult, response.signalEndTime())
                            .inResponseTo(message));
                }
            }.run();
        }

    }

    protected SearchResponse setupResponse(SearchRequest request) {

        final String contextId = request.getContextId() == null ? NameGenerator.shortUUID() : request.getContextId();

        if ((request.getSearchMode() == Mode.FREETEXT || request.isDefaultResults())) {
            if ((request.getContextId() == null)
                    || searchContexts.get(contextId) == null && searchContexts.get(contextId).getFirst() == null) {
                searchContexts.put(contextId, new Pair<>(
                        Indexing.INSTANCE.createContext(request.getMatchTypes(), request.getSemanticTypes()), new ArrayList<>()));
            }
        }

        SearchResponse response = new SearchResponse();
        response.setContextId(contextId);
        response.setRequestId(request.getRequestId());

        return response;
    }

    /**
     * TODO move into semantics package
     * 
     * @param expression
     * @param request
     * @param response
     */
    protected void runSemanticSearch(SemanticExpression expression, SearchRequest request, SearchResponse response) {

        for (Match match : Indexer.INSTANCE.query(request.getQueryString(), expression.getCurrent().getScope(),
                request.getMaxResults())) {
            response.getMatches().add(((org.integratedmodelling.klab.engine.indexing.SearchMatch) match).getReference());
        }

        // save the matches in the expression so that we recognize a choice
        expression.setData("matches", response);
    }

    /**
     * TODO move into authentication package or semantics
     * 
     * @param response
     */
    protected void setDefaultSearchResults(String contextId, SearchRequest request, SearchResponse response) {
        /*
         * These come from the user's groups. They should eventually be linked to session history
         * and preferences.
         */
        List<Match> matches = new ArrayList<>();
        int i = 0;
        for (ObservableReference observable : Authentication.INSTANCE.getDefaultObservables(Session.this)) {
            SearchMatch match = new SearchMatch(observable.getObservable(), observable.getLabel(), observable.getDescription(),
                    observable.getSemantics(), observable.getState(), observable.getExtendedDescription());
            match.setIndex(i++);
            response.getMatches().add(match);
            matches.add(new org.integratedmodelling.klab.engine.indexing.SearchMatch(match));
        }
        searchContexts.put(contextId, new Pair<Context, List<Match>>(
                Indexing.INSTANCE.createContext(request.getMatchTypes(), request.getSemanticTypes()), matches));
    }

    /**
     * TODO move into semantic package or concepts
     * 
     * @param request
     * @param response
     */
    protected void searchFreetext(SearchRequest request, SearchResponse response) {
        // TODO also lookup local matches and spawn search for remote ones to answer
        // afterwards.
        List<Match> matches = new ArrayList<>();
        int i = 0;
        for (Location location : Geocoder.INSTANCE.lookup(request.getQueryString())) {
            if ("relation".equals(location.getOsm_type())) {

                SearchMatch match = new SearchMatch(location.getURN(), location.getName(), location.getDescription(),
                        IKimConcept.Type.SUBJECT);
                match.setIndex(i++);
                response.getMatches().add(match);
                matches.add(new org.integratedmodelling.klab.engine.indexing.SearchMatch(match));
            }
        }
    }

    @Deprecated
    @MessageHandler
    private void handleSearchRequest(SearchRequest request, IMessage message) {

        if (message.getType() == IMessage.Type.SemanticSearch) {
            semanticSearch(request, message);
            return;
        }

        final String contextId = request.getContextId() == null ? NameGenerator.shortUUID() : request.getContextId();
        if (request.getContextId() == null
                || searchContexts.get(contextId) == null && searchContexts.get(contextId).getFirst() == null) {
            searchContexts.put(contextId, new Pair<>(
                    Indexing.INSTANCE.createContext(request.getMatchTypes(), request.getSemanticTypes()), new ArrayList<>()));
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
            new Thread(){

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
                        for (ObservableReference observable : Authentication.INSTANCE.getDefaultObservables(Session.this)) {
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
                                // System.out.println("OPEN PARENTHESIS");
                                literalMatch = true;

                            } else if (request.getQueryString().equals("(")) {

                                // TODO close submatch and collapse meaning
                                // System.out.println("OPEN PARENTHESIS");
                                literalMatch = true;

                            } else {

                                /*
                                 * proceed to querying next tokens
                                 */
                                List<Match> matches = Indexing.INSTANCE.query(request.getQueryString(), context.getFirst());

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

                                searchContexts.put(contextId, new Pair<Context, List<Match>>(context.getFirst(), matches));

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

                    monitor.send(Message
                            .create(token, IMessage.MessageClass.Query, IMessage.Type.QueryResult, response.signalEndTime())
                            .inResponseTo(message));
                }

            }.start();
        }
    }

    /**
     * Flag the session as default. The effect is that engine pings from localhost will receive the
     * session ID so they can choose to join it.
     * 
     * @return
     */
    public Session setDefault() {
        this.isDefault = true;
        return this;
    }

    @MessageHandler
    private void handleLoadApplicationRequest(final LoadApplicationRequest request, final IMessage.Type type) {

        this.globalState.register(request);

        switch(type) {
        case RunApp:
        case RunUnitTest:
            if (request.isStop()) {
                stop(request.getBehavior());
                // TODO should clear the state now, but it comes AFTER initialization of the
                // next
                // application!
            } else {
                this.globalState.clear();
                IBehavior behavior = Actors.INSTANCE.getBehavior(request.getBehavior());
                if (behavior != null) {
                    this.load(behavior, new SimpleRuntimeScope(this));
                }
            }
            break;
        case RunTest:
        case RunScript:
            // these run to the end or can be stopped through their monitor (not handled for
            // now)
            run(request.getScriptUrl());
        default:
            break;
        }
    }

    @MessageHandler
    private void handleViewAction(ViewAction action) {

        this.globalState.register(action);

    }

    @MessageHandler
    private void handleMenuAction(MenuAction action) {

        this.globalState.register(action);

    }

    /*
     * This can arrive with different message types
     */
    @MessageHandler
    private void handleWatchRequest(WatchRequest request) {

        if (request.getEventType() != null) {
            if (request.isActive()) {
                Klab.INSTANCE.subscribe(this, request.getEventType());
            } else {
                Klab.INSTANCE.unsubscribe(this, request.getEventType());
            }
        } else {

            IRuntimeScope scope = getRootScope(request.getRootContextId());
            if (scope != null) {
                if (request.isActive()) {
                    scope.getWatchedObservationIds().add(request.getObservationId());
                } else {
                    scope.getWatchedObservationIds().remove(request.getObservationId());
                }
            }
        }
    }

    /**
     * Create, delete, modify resources in workspace.
     * 
     * @param message
     * @param request
     */
    @MessageHandler(messageClass = IMessage.MessageClass.ProjectLifecycle)
    private void handleProjectModificationRequest(IMessage message, final ProjectModificationRequest request) {

        Project project = Resources.INSTANCE.getProject(request.getProjectId());

        if (project == null && message.getType() != IMessage.Type.CreateProject) {
            throw new IllegalArgumentException("project " + request.getProjectId() + " could not be found");
        }

        switch(message.getType()) {
        case CreateTestCase:
        case CreateScript:
        case CreateNamespace:

            File file = null;
            switch(message.getType()) {
            case CreateTestCase:
                file = project.createTestCase(request.getAssetId(), request.getScriptName(), request.getScriptPath(),
                        request.getScriptType());
                break;
            case CreateScript:
                file = project.createScript(request.getAssetId(), request.getScriptName(), request.getScriptPath(),
                        request.getScriptType());
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
            if ("kim".equals(request.getScriptType())) {
                Resources.INSTANCE.getLoader().add(file);
            } else if ("kactor".equals(request.getScriptType())) {
                KActors.INSTANCE.add(file);
            }

            break;

        case CreateBehavior:

            file = project.createBehavior(request.getAssetId(),
                    "true".equals(request.getParameters().get(ProjectModificationRequest.LIBRARY_OPTION))
                            ? IKActorsBehavior.Type.TRAITS
                            : IKActorsBehavior.Type.BEHAVIOR,
                    false);
            monitor.send(Message
                    .create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateBehavior,
                            new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION, file))
                    .inResponseTo(message));

            Resources.INSTANCE.getLoader().add(file);

            break;

        case CreateProject:

            project = (Project) Resources.INSTANCE.getLocalWorkspace().createProject(request.getProjectId(), monitor);
            monitor.send(Message
                    .create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.CreateProject,
                            new ProjectModificationNotification(ProjectModificationNotification.Type.ADDITION, project.getRoot()))
                    .inResponseTo(message));
            Resources.INSTANCE.getLoader().add(project.getStatement());

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
                Models.INSTANCE.releaseNamespace(ns.getName(), monitor);
                Resources.INSTANCE.getLoader().delete(ns.getFile());
                FileUtils.deleteQuietly(ns.getFile());
                monitor.send(Message
                        .create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DeleteNamespace,
                                new ProjectModificationNotification(ProjectModificationNotification.Type.DELETION, ns.getFile()))
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

    @MessageHandler
    private void handleEngineCommand(EngineAction message) {

        switch(message.getRequest()) {
        case "debugger":
            // Debug.INSTANCE.newDebugger(this);
            break;
        }

    }

    @MessageHandler(type = IMessage.Type.ResetContext)
    private void handleResetContextRequest(String dummy) {

        this.globalState.resetContext();
        //
        // if (!lockSpace.get()) {
        // this.spatialGridSize = null;
        // this.spatialGridUnits = null;
        // this.timeEnd = null;
        // this.timeStart = null;
        // this.temporalResolution = null;
        // }
        // this.regionOfInterest = null;
        // this.regionNameOfInterest = "Region of interest";
        // this.timeOfInterest =
        // org.integratedmodelling.klab.Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR);
        // monitor.send(IMessage.Type.ResetContext,
        // IMessage.MessageClass.UserContextChange, "");
        // this.globalState.resetContext();
    }

    /**
     * This is all we need to react to UI events modifying the workspace or any of its imports.
     * 
     * @param event
     * @param type
     */
    @MessageHandler
    private void handleProjectEvent(final ProjectModificationNotification event, IMessage.Type type, IMessage message) {

        ProjectActionResponse response = new ProjectActionResponse();

        switch(type) {
        case ProjectFileAdded:
            if (KActors.INSTANCE.isKActorsFile(event.getFile())) {
                KActors.INSTANCE.add(event.getFile());
            } else {
                response.getNamespaces().add(Resources.INSTANCE.getLoader().add(event.getFile()).getName());
            }
            break;
        case ProjectFileDeleted:
            if (KActors.INSTANCE.isKActorsFile(event.getFile())) {
                KActors.INSTANCE.delete(event.getFile());
            } else {
                for (IKimNamespace ns : Resources.INSTANCE.getLoader().delete(event.getFile())) {
                    response.getNamespaces().add(ns.getName());
                }
            }
            break;
        case ProjectFileModified:
            if (KActors.INSTANCE.isKActorsFile(event.getFile())) {
                KActors.INSTANCE.touch(event.getFile());
            } else {
                for (IKimNamespace ns : Resources.INSTANCE.getLoader().touch(event.getFile())) {
                    response.getNamespaces().add(ns.getName());
                }
            }
            break;
        default:
            break;
        }

        monitor.send(Message.create(token, IMessage.MessageClass.ProjectLifecycle, type, response).inResponseTo(message));

    }

    @MessageHandler
    private void handleRecontextualizationRequest(ContextualizationRequest request) {
        this.globalState.recontextualize(request);
    }

    @MessageHandler
    private void handleDocumentationEvent(final DocumentationReference documentation, IMessage.Type type) {
        IKimProject project = Kim.INSTANCE.getProject(documentation.getProjectName());
        if (project != null) {
            if (type == IMessage.Type.DocumentationModified) {
                Documentation.INSTANCE
                        .resetDocumentation(IDocumentation.getDocumentationFile(documentation.getDocId(), project.getRoot()));
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

        this.globalState.register(request);
    }

    @MessageHandler(type = IMessage.Type.TaskInterrupted)
    private void handleTaskInterruptRequest(InterruptTask request) {
        interruptTask(request.getTaskId(), true);
    }

    @MessageHandler(type = IMessage.Type.ScaleDefined)
    private void handleScaleChangeRequest(ScaleReference scaleRef) {
        this.globalState.register(scaleRef);
    }

    @MessageHandler
    private void handleProjectLoadRequest(final ProjectLoadRequest request, IMessage message) {
        new Thread(){

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

                monitor.send(Message.create(token, IMessage.MessageClass.ProjectLifecycle, IMessage.Type.UserProjectOpened,
                        response)/* .inResponseTo(message) */);
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
        for (String app : Actors.INSTANCE.getPublicApps()) {
            ret.getPublicApps().add(((KActorsBehavior) Actors.INSTANCE.getBehavior(app).getStatement()).getReference());
        }
        // FIXME remove
        ret.getAppUrns().addAll(Actors.INSTANCE.getPublicApps());
        ret.getUserAppUrns().addAll(Actors.INSTANCE.getBehaviorIds(IKActorsBehavior.Type.USER));

        /*
         * TODO add views in context; add running application IDs
         */

        IUserIdentity user = getParentIdentity(IUserIdentity.class);
        if (user != null) {
            IdentityReference uid = new IdentityReference();
            uid.setEmail(user.getEmailAddress());
            uid.setId(user.getUsername());
            for (Group group : user.getGroups()) {
                uid.getGroups().add(new GroupReference(group));
            }
            uid.setLastLogin(user.getLastLogin().toString());
            ret.setOwner(uid);
        }

        for (IRuntimeScope ctx : observationContexts) {
            ret.getRootObservations().put(ctx.getRootSubject().getId(), Observations.INSTANCE
                    .createArtifactDescriptor(ctx.getRootSubject()/* , null */, ctx.getScale().initialization(), 0));
        }

        return ret;
    }

    public List<IObservation> getRootContexts() {
        List<IObservation> ret = new ArrayList<>();
        for (IRuntimeScope scope : observationContexts) {
            ret.add(scope.getRootSubject());
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

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public boolean isInteractive() {
        return interactive.get();
    }

    /**
     * Call this one to validate and register a resource after it's been built by an external agent.
     * NOTE: this resource is built without the involvement of an adapter, so it should not be used
     * with user input or anything not previously established as valid.
     * 
     * @param ret
     * @return
     */
    public IResource registerResource(IResource ret) {
        // invoke the service to validate, register and notify
        ret = Resources.INSTANCE.registerResource(ret);
        if (ret != null) {
            monitor.send(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ResourceCreated, ((Resource) ret).getReference());
        }
        return ret;
    }
    
    @Override
    public IObserver<?> getObserver() {
        return null;
    }

    @Override
    public Reference getActor() {

        if (this.actor == null) {

            EngineUser engine = getParentIdentity(EngineUser.class);
            if (engine != null) {

                ActorRef<KlabMessage> parentActor = ((ActorReference) engine.getActor()).actor;
                parentActor.tell(new Spawn(this, null));

                /*
                 * wait for instrumentation to succeed. Couldn't figure out the ask pattern. TODO
                 * when this has a chance to fail (e.g. in a cluster situation), add a timeout, or
                 * figure out the ask pattern.
                 */
                while(!this.actorSet.get()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }

        if (this.actor == null) {
            // no upstream actor (should not happen), create directly
            this.actor = Actors.INSTANCE.createActor(SessionActor.create(this, null), this);
        }

        return new ActorReference(this.actor);
    }

    @Override
    public String load(IBehavior behavior, IContextualizationScope scope) {
        String ret = "app" + NameGenerator.shortUUID();
        if (behavior.getDestination() == IKActorsBehavior.Type.APP) {
            globalState.setApplicationId(ret);
            globalState.setApplicationName(behavior.getName());
        } else if (behavior.getDestination() != IKActorsBehavior.Type.SCRIPT
                && behavior.getDestination() != IKActorsBehavior.Type.UNITTEST) {
            throw new KlabActorException("internal: sessions can only load apps, scripts and unit tests");
        }
        if (behavior.getDestination() != IKActorsBehavior.Type.APP) {
            exitListeners.put(ret, null);
        }
        getActor().tell(new SystemBehavior.Load(this,
                behavior.getId() + (behavior.getLocale() == null ? "" : ("." + behavior.getLocale())), ret,
                (IRuntimeScope) scope));
        return ret;
    }

    public String loadScript(IBehavior behavior, KlabActor.Scope scope, Runnable onExit) {
        String ret = "script" + NameGenerator.shortUUID();
        if (behavior.getDestination() != IKActorsBehavior.Type.SCRIPT
                && behavior.getDestination() != IKActorsBehavior.Type.UNITTEST) {
            throw new KlabActorException("internal: loadScript() can only be used with scripts and unit tests");
        }
        this.exitListeners.put(ret, onExit);
        getActor().tell(new SystemBehavior.Load(this, behavior.getId(), ret, scope));
        return ret;
    }

    @Override
    public boolean stop(String applicationId) {
        getActor().tell(new SystemBehavior.Stop(applicationId));
        return true;
    }

    @Override
    public boolean stop() {
        // TODO Auto-generated method stub
        return false;
    }

    public void instrument(Reference actor) {
        this.actor = ((ActorReference) actor).actor;
        this.actorSet.set(true);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setView(View layout) {
        this.view = layout;
    }

    @Override
    public SessionState getState() {
        return globalState;
    }

    public void notifyNewContext(ISubject object) {
        globalState.notifyNewContext(object);
    }

    public void notifyNewObservation(IObservation object, ISubject context) {
        globalState.notifyNewObservation(object, context);
    }

    public long getLastActivity() {
        return lastActivity;
    }

    @Override
    public Object getProperty(String property) {
        if (this.globalState.containsKey(property)) {
            return this.globalState.get(property);
        }
        return super.getProperty(property);
    }

    @Override
    public synchronized void interruptAllTasks() {
        for (Future<?> task : this.tasks.values()) {
            if (task instanceof ITaskTree<?>) {
                interruptTask(((AbstractTask<?>) task).getToken(), true);
            }
        }
    }

    public Map<String, Future<?>> getCompletedTasks() {
        return this.completedTasks;
    }

    public boolean isRunning(String appId) {
        return this.exitListeners.containsKey(appId);
    }

    public void notifyScriptEnd(String appId) {
        if (this.exitListeners.containsKey(appId)) {
            Runnable r = this.exitListeners.remove(appId);
            if (r != null) {
                r.run();
            }
        }
    }

    public int getScriptReturnValue(String app) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IUserIdentity getUser() {
        return user;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ITask<ISubject> observe(IObservable observable, IGeometry geometry) {
        Acknowledgement observer = Observations.INSTANCE.makeObserver(observable, geometry, new Metadata());
        return (ITask<ISubject>) (ITask) ObserveContextTask.create(this, observer, false);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ITask<ISubject> observe(String observerUrn) {
        IKimObject observer = Resources.INSTANCE.getModelObject(observerUrn);
        if (!(observer instanceof IAcknowledgement)) {
            throw new KlabIllegalArgumentException(observerUrn + " does not specify an observer");
        }
        return (ITask<ISubject>) (ITask) ObserveContextTask.create(this, (Acknowledgement) observer, false);
    }

    /**
     * Open a session-local ticket.
     * 
     * @param ticketData
     * @return
     */
    public ITicket openTicket(Object... ticketData) {
        /**
         * TODO FIXME use local ticket manager based on MapDB or Mongo (could really just be a
         * RAM-based map); tickets must disappear with the session
         */
        return Klab.INSTANCE.getTicketManager().open(ticketData);
    }

    public ITicket getTicket(String ticketId) {
        // TODO FIXME switch to local ticket manager
        return Klab.INSTANCE.getTicketManager().getTicket(ticketId);
    }

    /*
     * Estimates are handled externally by the API, we only expose the catalog
     */
    public Map<String, Estimate> getEstimates() {
        return this.estimates;
    }

    public void resetAfterTest(Action action) {
        globalState.resetConstraints();
        globalState.resetRoles();
        globalState.resetInspector();
        globalState.resetContext();
    }

    public TestScope getRootTestScope() {
        if (rootTestScope == null) {
            rootTestScope = new TestScope(this);
            monitor.send(Message.create(this.token, IMessage.MessageClass.UnitTests, IMessage.Type.TestRunStarted,
                    new TestRun(rootTestScope.getTestId())));
        }
        return rootTestScope;
    }

    public void notifyTestCaseStart(IBehavior behavior, TestStatistics statistics) {
        monitor.send(Message.create(this.token, IMessage.MessageClass.UnitTests, IMessage.Type.TestCaseStarted, statistics));
    }

    @Override
    public long getTimestamp() {
        return this.creation;
    }

    @Override
    public IProvenance getProvenance() {
        // TODO this is an agent which may be implied in multiple provenance graphs
        return null;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

}

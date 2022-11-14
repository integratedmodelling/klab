package org.integratedmodelling.klab.ide.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Platform;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.API.PUBLIC.Export;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.monitoring.MessageHandler;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference;
import org.integratedmodelling.klab.client.messaging.ResourceMonitor;
import org.integratedmodelling.klab.client.messaging.SessionMonitor;
import org.integratedmodelling.klab.client.messaging.SessionMonitor.ContextDescriptor;
import org.integratedmodelling.klab.client.messaging.SessionMonitor.Listener;
import org.integratedmodelling.klab.client.tickets.TicketManager;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EAcknowledgement;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.views.ApplicationView;
import org.integratedmodelling.klab.ide.views.ResourcesView;
import org.integratedmodelling.klab.ide.views.SearchView;
import org.integratedmodelling.klab.rest.ActionStatistics;
import org.integratedmodelling.klab.rest.AuthorityQueryRequest;
import org.integratedmodelling.klab.rest.AuthorityQueryResponse;
import org.integratedmodelling.klab.rest.ContextualizationNotification;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.LocalResourceReference;
import org.integratedmodelling.klab.rest.NetworkReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ProjectLoadResponse;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.rest.QueryStatusResponse;
import org.integratedmodelling.klab.rest.ResourceImportRequest;
import org.integratedmodelling.klab.rest.ResourceOperationResponse;
import org.integratedmodelling.klab.rest.ResourcePublishResponse;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.RuntimeEvent;
import org.integratedmodelling.klab.rest.SearchResponse;
import org.integratedmodelling.klab.rest.SessionReference;
import org.integratedmodelling.klab.rest.TaskReference;
import org.integratedmodelling.klab.rest.TestRun;
import org.integratedmodelling.klab.rest.TestStatistics;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.WatchRequest;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Front-end session proxy and receiver for session messages. Maintains and manages state for the
 * session, including the list of available resources and the history of user actions and their
 * consequences.
 * 
 * @author ferdinando.villa
 *
 */
public class KlabSession extends KlabPeer {

    private int NETWORK_CHECK_INTERVAL_SECONDS = 60;
    private int TICKET_CHECK_INTERVAL_SECONDS = 6;

    // six hours
    private static final long MAX_TICKET_AGE = 1000l * 60l * 60l * 6l;

    // private AtomicLong queryCounter = new AtomicLong();
    private Map<EngineEvent.Type, Set<Long>> engineEvents = Collections.synchronizedMap(new HashMap<>());
    private SessionReference sessionReference;
    
    private static Logger logger = LoggerFactory.getLogger(KlabSession.class);

    SessionMonitor sessionMonitor = new SessionMonitor(){

        @Override
        protected void subscribe(ContextGraph contextGraph, ObservationReference observation, boolean open) {
            WatchRequest request = new WatchRequest();
            request.setActive(open);
            request.setObservationId(observation.getId());
            request.setRootContextId(observation.getRootContextId());
            Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.WatchObservation, request);
        }

        @Override
        protected List<ObservationReference> retrieveChildren(ObservationReference observation, int offset, int count) {
            List<ObservationReference> ret = new ArrayList<>();
            for (ObservationReference obs : Activator.engineMonitor().getChildren(observation, offset, count)) {
                ret.add(obs);
            }
            return ret;
        }

    };

    /**
     * Use in views to provide the root of any visualization tree.
     * 
     * @return
     */
    public ContextDescriptor getCurrentContextDescriptor() {
        return currentRootContextId == null ? null : sessionMonitor.getContextDescriptor(currentRootContextId);
    }

    /*
     * Tickets to track engine tickets that track node tickets
     */
    private TicketManager ticketManager;
    private ResourceMonitor resourceMonitor = new ResourceMonitor();
    private Map<String, ITicket.Status> ticketStatus = Collections.synchronizedMap(new HashMap<>());

    /*
     * Current root context and task in UI or null
     */
    private String currentRootContextId;

    public KlabSession(String sessionId) {
        super(Sender.SESSION, sessionId);

        this.sessionMonitor.addListener(new Listener(){

            @Override
            public void onTaskStatusChange(ObservationReference rootContext, TaskReference task,
                    org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status status) {
                send(IMessage.MessageClass.UserInterface, IMessage.Type.RuntimeEvent,
                        new RuntimeEvent(rootContext, task, status));
            }

            @Override
            public void onSystemNotification(Notification notification) {
                send(IMessage.MessageClass.UserInterface, IMessage.Type.RuntimeEvent, new RuntimeEvent(notification));
            }

            @Override
            public void onStructureChange(ObservationReference rootContext, Object added, TaskReference objectParent) {
                if (added instanceof ObservationReference && rootContext.getId().equals(((ObservationReference) added).getId())) {
                    setCurrentContext(rootContext);
                }
                send(IMessage.MessageClass.UserInterface, IMessage.Type.RuntimeEvent,
                        new RuntimeEvent(rootContext, added, objectParent));
            }

            @Override
            public void onDataflowChange(ObservationReference rootContext, ContextualizationNotification dataflow) {
                send(IMessage.MessageClass.UserInterface, IMessage.Type.RuntimeEvent, new RuntimeEvent(rootContext, dataflow));
            }
        });

        this.ticketManager = new TicketManager(
                new File(Configuration.INSTANCE.getDataPath() + File.separator + "tickets.modeler.json"));

        /*
         * initialize resource status
         */
        for (ITicket ticket : ticketManager.get()) {
            if (!isStale(ticket)) {
                resourceMonitor.add(ticket);
            }
        }

        // run the first network check in 10 seconds
        new CheckNetworkTask().schedule(10000);
        // start checking tickets in 5
        new CheckTicketsTask().schedule(5000);
    }

    protected void setCurrentContext(ObservationReference rootContext) {
        this.currentRootContextId = rootContext.getId();

    }

    class CheckNetworkTask extends Job {

        public CheckNetworkTask() {
            super("Checking network status...");
        }

        protected IStatus run(IProgressMonitor monitor) {
            try {
                if (Activator.engineMonitor().isRunning()) {
                    Activator.post(IMessage.MessageClass.Authorization, IMessage.Type.NetworkStatus, "networkStatus");
                }
            } catch (Throwable t) {
                // shut up
                logger.warn("Error while checking network: " + t.getMessage());
            }
            schedule(NETWORK_CHECK_INTERVAL_SECONDS * 1000);
            return Status.OK_STATUS;
        }
    }

    class CheckTicketsTask extends Job {

        public CheckTicketsTask() {
            super("");
        }

        protected IStatus run(IProgressMonitor monitor) {

            schedule(TICKET_CHECK_INTERVAL_SECONDS * 1000);

            List<ITicket> toRemove = new ArrayList<>();
            for (ITicket ticket : ticketManager.get()) {
                if (isStale(ticket)) {
                    // TODO use logging!
                    logger.warn("Removing stale ticket " + ticket);
                    toRemove.add(ticket);
                } else {
                    /*
                     * notify if status changed or ticket is new
                     */
                    if (ticketStatus.containsKey(ticket.getId())) {
                        if (ticket.getStatus() != ticketStatus.get(ticket.getId())) {
                            ticketStatus.put(ticket.getId(), ticket.getStatus());
                            processTicketEvent(ticket, false);
                        }
                    } else {
                        ticketStatus.put(ticket.getId(), ticket.getStatus());
                        processTicketEvent(ticket, true);
                    }
                }
            }

            for (ITicket ticket : toRemove) {
                ticketManager.remove(ticket);
            }

            return Status.OK_STATUS;
        }

    }

    private boolean isStale(ITicket ticket) {
        return ticket.getStatus() == ITicket.Status.OPEN
                && (System.currentTimeMillis() - ticket.getPostDate().getTime()) > MAX_TICKET_AGE;
    }

    /*
     * --- public methods ---
     */

    public SessionMonitor getContextMonitor() {
        return sessionMonitor;
    }

    /*
     * --- State and history management
     */

    public void processTicketEvent(ITicket ticket, boolean isNew) {
        resourceMonitor.add(ticket);
        // expose the resources view, which will set itself to public and refresh when
        // it gets the message
        Eclipse.INSTANCE.openView(ResourcesView.ID, null);
        send(IMessage.MessageClass.UserInterface, isNew ? IMessage.Type.TicketCreated : IMessage.Type.TicketStatusChanged,
                ticket);
    }

    void recordNotification(String notification, String identity, Type type, String messageId) {

        /*
         * TODO multi-line notification should be broken up into multiple ones, with continuation
         * flag from the second on, so they can be displayed properly
         */

        Notification enote = new Notification();
        enote.setMessage(notification);
        enote.setIdentity(identity);
        enote.setId(messageId);
        switch(type) {
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
        sessionMonitor.register(enote);
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

    public void importFileIntoResource(File file, EResource resource) {
        try {
            ResourceImportRequest request = new ResourceImportRequest();
            request.setTargetResourceUrn(resource.getUrn());
            request.setImportUrl(file.toURI().toURL());
            Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.ImportIntoResource, request);
        } catch (MalformedURLException e) {
            // dio petardo
            Eclipse.INSTANCE.handleException(e);
        }
    }

    public void launchScript(URL url) {
        Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunScript, new LoadApplicationRequest(url, false));
    }

    public void launchTest(URL url) {
        Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunTest, new LoadApplicationRequest(url, true));
    }

    public void searchAuthority(String authorityId, String authorityCatalog, String queryString) {
        Activator.post(IMessage.MessageClass.UserInterface, IMessage.Type.AuthorityQuery,
                new AuthorityQueryRequest(authorityId, authorityCatalog, queryString));
    }

    public void launchApp(String behavior) {

        String loc = "";
        List<String> localizations = getLocalizations(behavior);
        if (localizations.size() == 1) {
            loc = localizations.get(0);
            
        } else if (localizations.size() > 1) {
            // returns null if canceled
            loc = Eclipse.INSTANCE.chooseOne("Choose a locale", localizations);
        }
        if (loc != null && loc.contains(" - ")) {
            loc = loc.substring(0, loc.indexOf(" - "));
        }

        if (loc != null) {
            Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunApp,
                    new LoadApplicationRequest(behavior + (loc.isEmpty() ? "" : ("." + loc)), false, false));
        }
    }

    private List<String> getLocalizations(String behavior) {

        List<String> ret = new ArrayList<>();
        IKActorsBehavior source = KActors.INSTANCE.getBehavior(behavior);
        if (source != null) {
            File loc = MiscUtilities.changeExtension(source.getFile(), "localization");
            if (loc != null) {
                FileCatalog<Map> cat = FileCatalog.create(loc, Map.class, Map.class);
                for (String lang : cat.keySet()) {
                    Locale locale = Locale.forLanguageTag(lang);
                    ret.add(lang + " - " + locale.getDisplayLanguage());
                }
            }
        }
        return ret;
    }

    public void launchTest(String behavior) {
        Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunUnitTest, new LoadApplicationRequest(behavior, true, false));
    }

    public void observe(EKimObject dropped) {
        Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
                new ObservationRequest(dropped.getId(), currentRootContextId, null));
    }

    public void observe(String urn) {
        Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
                new ObservationRequest(urn, currentRootContextId, null));
    }

    public void observe(EAcknowledgement dropped, boolean addToContext) {
        Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
                new ObservationRequest(dropped.getId(), addToContext ? currentRootContextId : null, null));
    }

    public void previewResource(ResourceReference resource) {
        Activator.post(IMessage.MessageClass.ObservationLifecycle, IMessage.Type.RequestObservation,
                new ObservationRequest(resource.getUrn(), currentRootContextId, null));
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
        switch(type) {
        case ResourceCreated:
        case ResourceImported:
            Activator.klab().notifyResourceImport(resource);
            break;
        case ResourceDeleted:
            Activator.klab().notifyResourceDeleted(resource);
            break;
        case ResourceUpdated:
            Activator.klab().notifyResourceUpdated(resource);
            break;
        default:
            break;
        }
    }

    @MessageHandler(type = IMessage.Type.AuthoritySearchResults)
    public void handleAuthoritySearchResults(IMessage message, AuthorityQueryResponse response) {
        send(message);
    }

    @MessageHandler(messageClass = IMessage.MessageClass.Authorization, type = IMessage.Type.NetworkStatus)
    public void handleNetworkStatus(NetworkReference network) {
        Activator.klab().updateNetwork(network);
        send(IMessage.MessageClass.UserInterface, IMessage.Type.NetworkStatus, network);
        for (TicketResponse.Ticket ticket : network.getResolvedTickets()) {
            ticketManager.update(ticket);
            // scheduled job will pick this up
        }
    }

    @MessageHandler(messageClass = IMessage.MessageClass.Notification)
    public void handleNotification(IMessage message, String notification) {
        recordNotification(notification, message.getIdentity(), message.getType(), message.getId());
    }

    @MessageHandler
    public void handleSearchResponse(IMessage message, SearchResponse response) {
        send(message);
    }

    @MessageHandler(type = IMessage.Type.EngineEvent)
    public synchronized void handleEngineEvent(IMessage message, EngineEvent event) {
        Set<Long> current = engineEvents.get(event.getType());
        if (current == null) {
            current = new HashSet<>();
            engineEvents.put(event.getType(), current);
        }

        /*
         * Only notify the first started and the last finished
         */
        boolean notify = false;
        if (event.isStarted()) {
            notify = current.size() == 0;
            current.add(event.getId());
        } else {
            current.remove(event.getId());
            notify = current.size() == 0;
        }

        if (notify) {
            send(message);
        }
    }

    @MessageHandler(type = IMessage.Type.ResetContext)
    private void handleResetContextRequest(IMessage message, String dummy) {
        this.currentRootContextId = null;
        send(message);
    }

    @MessageHandler(type = Type.TaskStarted)
    public void handleTaskStarted(IMessage message, TaskReference task, IMessageBus bus) {
        sessionMonitor.register(task);
        bus.subscribe(task.getId(), new KlabTask(task.getId()));
    }

    @MessageHandler(type = Type.TaskFinished)
    public void handleTaskFinished(IMessage message, TaskReference task, IMessageBus bus) {
        sessionMonitor.update(task, ITaskReference.Status.Finished);
        bus.unsubscribe(task.getId());
    }

    @MessageHandler(type = Type.TaskAborted)
    public void handleTaskAborted(IMessage message, TaskReference task, IMessageBus bus) {
        sessionMonitor.update(task, ITaskReference.Status.Aborted);
        bus.unsubscribe(task.getId());
    }

    @MessageHandler
    public void handleCreateView(IMessage message, Layout component) {
        if (component.getDestination() == IKActorsBehavior.Type.USER) {
            Eclipse.INSTANCE.openView(SearchView.ID, null);
        } else if (component.getPlatform() == null || component.getPlatform() == Platform.DESKTOP
                || component.getPlatform() == Platform.ANY) {
            Eclipse.INSTANCE.openView(ApplicationView.ID, null);
        }
        send(message);
    }

    @MessageHandler(type = Type.QueryStatus)
    public void handleQueryStatus(IMessage message, QueryStatusResponse response) {
        send(message);
    }

    @MessageHandler(messageClass = MessageClass.UnitTests)
    public void handleTestCaseMessage(IMessage message, TestRun testRun) {
        send(message);
    }

    @MessageHandler(messageClass = MessageClass.UnitTests)
    public void handleTestCaseMessage(IMessage message, TestStatistics testStatistics) {
        send(message);
    }
    @MessageHandler(messageClass = MessageClass.UnitTests)
    public void handleTestCaseMessage(IMessage message, ActionStatistics actionStatistics) {
        send(message);
    }

    @MessageHandler(type = Type.CreateViewComponent)
    public void handleCreateComponent(IMessage message, ViewComponent component) {
        send(message);
    }

    @MessageHandler(type = Type.ViewAction)
    public void handleViewAction(IMessage message, ViewAction component) {
        send(message);
    }

    @MessageHandler
    public void handleObservation(ObservationReference observation) {
        sessionMonitor.register(observation);
    }

    @MessageHandler
    public void handleObservation(ObservationChange observation) {
        sessionMonitor.register(observation);
    }

    @MessageHandler
    public void handlePublishResponse(ResourcePublishResponse response) {
        if (response.getError() != null) {
            Eclipse.INSTANCE.alert("Publishing of " + response.getOriginalUrn() + " failed: " + response.getError());
        } else {
            // TODO enqueue an event for later checking
        }
    }

    @MessageHandler
    public void handleResourceOperation(ResourceOperationResponse response) {
        String title = "Results of " + response.getOperation() + " on " + response.getUrn();
        String message = "";
        for (Notification notification : response.getNotifications()) {
            message += (message.isEmpty() ? "" : "\n") + notification.getLevel() + ": " + notification.getMessage();
        }
        Eclipse.INSTANCE.info(title + "\n\n" + message);
    }

    @MessageHandler
    public void handleDataflow(IMessage message, ContextualizationNotification dataflow) {
        sessionMonitor.register(dataflow);
        send(message);
    }

    @MessageHandler
    public void handleProjectNotification(ProjectLoadResponse response) {
        for (ProjectReference project : response.getProjects()) {
            for (LocalResourceReference resource : project.getLocalResources()) {
                Activator.klab().updateResource(resource);
            }
        }
        // Eclipse.INSTANCE.refreshOpenEditors();
        KlabNavigator.refresh();
        send(IMessage.MessageClass.UserInterface, IMessage.Type.UserProjectModified, response);
    }

    public TicketManager getTicketManager() {
        return this.ticketManager;
    }

    public List<Pair<String, IParameters<String>>> getPublishedResources() {
        return resourceMonitor.getPublished();
    }

    public void notifyTicket(ITicket ticket) {
        this.resourceMonitor.add(ticket);
        send(IMessage.MessageClass.UserInterface, IMessage.Type.TicketCreated, ticket);
    }

    public String getDefaultUserBehavior() {
        // TODO
        return "default";
    }

    public List<String> getUserBehaviors() {
        return this.sessionReference == null ? new ArrayList<>() : this.sessionReference.getUserAppUrns();
    }

    public String getDataflow(String id) {
        return Activator.client().with(getIdentity()).accept("text/plain").get(API.PUBLIC.EXPORT_DATA
                .replace(API.PUBLIC.P_OBSERVATION, id).replace(API.PUBLIC.P_EXPORT, Export.DATAFLOW.name().toLowerCase()),
                String.class);
    }

}

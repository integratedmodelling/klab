package org.integratedmodelling.klab;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.ITicket.Status;
import org.integratedmodelling.klab.api.runtime.ITicketManager;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.api.services.IRuntimeService;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.engine.rest.SchemaExtractor;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.exceptions.KlabConfigurationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Runtime would be a better name for this, but it makes it awkward to code with
 * as it conflicts with Java's Runtime which is imported by default.
 * 
 * @author ferdinando.villa
 *
 */
public enum Klab implements IRuntimeService {

	INSTANCE;

	public static final int TICKET_CHECK_INTERVAL_SECONDS = 60;
	public static final int TICKET_CHECK_NOTIFICATIONS_SECONDS = 5;
	public static final int STATISTICS_INTERVAL_SECONDS = 30;
	public static final String STATS_SERVICE_ADAPTER_ID = "stats";

	/**
	 * This can be set to a runnable that starts the REST services.
	 */
	Runnable serviceApplication = null;
	IComponent storageProvider = null;
	IComponent runtimeProvider = null;
	File workDirectory = new File(".").getAbsoluteFile();
	boolean networkServicesStarted = false;
	boolean networkServicesFailed = false;
	IIdentity rootIdentity = null;
	IMessageBus messageBus = null;
	ITicketManager ticketManager = null;
	INodeIdentity statisticsServer = null;

	Queue<ObservationResultStatistics> statisticsQueue = new ConcurrentLinkedQueue<>();
	Consumer<ObservationResultStatistics> statisticsConsumer;

	/*
	 * Counter for event IDs.
	 */
	AtomicLong eventCounter = new AtomicLong(0);
	Object eventLock = new Object();

	/**
	 * Current status of synchronous engine events
	 */
	Map<EngineEvent.Type, Set<Long>> eventStatus = Collections.synchronizedMap(new HashMap<>());

	/**
	 * Subscribers to engine events, indexed by event type and then by identity ID.
	 * Each identity contains the notified event start IDs so that any that get
	 * missed during subscription can be notified by a reaper thread.
	 */
	Map<EngineEvent.Type, Map<String, Set<Long>>> eventSubscriptions = Collections.synchronizedMap(new HashMap<>());

	/**
	 * Handler to process classes with k.LAB annotations. Register using
	 * {@link #registerAnnotationHandler(Class, AnnotationHandler)}; the handlers
	 * will be called for each matching class when {@link #scanPackage(String)} is
	 * called.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public interface AnnotationHandler {

		void processAnnotatedClass(Annotation annotation, Class<?> cls) throws KlabException;
	}

	private Map<Class<? extends Annotation>, AnnotationHandler> annotationHandlers = new HashMap<>();
	private IMonitor rootMonitor = new RootMonitor();
	private Timer timer = new Timer("Ticket checking");

	private Klab() {

		rootMonitor = new RootMonitor();
		setupExtensions();
		this.ticketManager = new TicketManager(
				new File(Configuration.INSTANCE.getDataPath() + File.separator + "tickets.engine.json"));
		Services.INSTANCE.registerService(this, IRuntimeService.class);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkPendingTickets();
			}
		}, 30 * 1000, TICKET_CHECK_INTERVAL_SECONDS * 1000);

		/*
		 * this one has been unnecessary so far, with the delay between start/stop
		 * notification fixing any problems. TODO remove or reconsider
		 */
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkPendingEventNotifications();
			}
		}, 1000, TICKET_CHECK_NOTIFICATIONS_SECONDS * 1000);

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkPendingStatistics();
			}
		}, 30 * 1000, STATISTICS_INTERVAL_SECONDS * 1000);

	}

	synchronized void checkPendingStatistics() {
		if (statisticsQueue.size() > 0) {
			INodeIdentity node = getStatisticsServer();
			if (node != null || statisticsConsumer != null) {
				List<ObservationResultStatistics> payload = new ArrayList<>();
				for (int i = 0; i < 10; i++) {
					if (statisticsQueue.size() == 0) {
						break;
					}
					payload.add(statisticsQueue.remove());
				}
				if (payload.size() > 0) {
					for (int i = payload.size() - 1; i >= 0; i--) {
						if (statisticsConsumer != null) {
							statisticsConsumer.accept(payload.get(i));
						} else if (node != null && !node.getClient().put(API.STATS.STATS_ADD, payload)) {
							/*
							 * put the payload back. May need to stop if things become big.
							 */
							statisticsQueue.offer(payload.get(i));
						}
					}
				}
			}
		}
	}

	public void setStatisticsLocalHandler(Consumer<ObservationResultStatistics> handler) {
		this.statisticsConsumer = handler;
	}

	public void setNetworkServiceApplication(Runnable runnable) {
		this.serviceApplication = runnable;
	}

	/**
	 * Set the root identity (owning the root monitor). Set to the owning user
	 * first, then to the engine if it is started correctly. Propagate the identity
	 * to the logging manager so we can sign logging notifications to subscribers.
	 * 
	 * @param identity
	 */
	public void setRootIdentity(IIdentity identity) {
		this.rootIdentity = identity;
		Logging.INSTANCE.setRootIdentity(identity);
	}

	/**
	 * Register a class annotation and its handler for processing when
	 * {@link #scanPackage(String)} is called.
	 * 
	 * @param annotationClass
	 * @param handler
	 */
	public void registerAnnotationHandler(Class<? extends Annotation> annotationClass, AnnotationHandler handler) {
		annotationHandlers.put(annotationClass, handler);
	}

	@Override
	public IMessageBus getMessageBus() {
		if (this.messageBus == null) {
			// create default
			// TODO propagate to Logging service
		}
		return this.messageBus;
	}

	/**
	 * Single scanning loop for all registered annotations in a package. Done on the
	 * main codebase and in each component based on the declared packages.
	 * 
	 * @param packageId
	 * @return all annotations found with the corresponding class
	 * @throws KlabException
	 */
	public List<Pair<Annotation, Class<?>>> scanPackage(String packageId) throws KlabException {

		List<Pair<Annotation, Class<?>>> ret = new ArrayList<>();

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		for (Class<? extends Annotation> ah : annotationHandlers.keySet()) {
			provider.addIncludeFilter(new AnnotationTypeFilter(ah));
		}

		Set<BeanDefinition> beans = provider.findCandidateComponents(packageId);
		for (BeanDefinition bd : beans) {
			for (Class<? extends Annotation> ah : annotationHandlers.keySet()) {
				try {
					Class<?> cls = Class.forName(bd.getBeanClassName());
					Annotation annotation = cls.getAnnotation(ah);
					if (annotation != null) {
						annotationHandlers.get(ah).processAnnotatedClass(annotation, cls);
						ret.add(new Pair<>(annotation, cls));
					}
				} catch (ClassNotFoundException e) {
					Logging.INSTANCE.error(e);
					continue;
				}
			}
		}

		return ret;
	}

	// functions

	// URNs

	/*
	 * Get extensions (ImageIO, Geotools) optimally configured. Most copied from
	 * Geoserver's GeoserverInitStartupListener
	 */
	private static void setupExtensions() {

		if (System.getProperty("org.geotools.referencing.forceXY") == null) {
			System.setProperty("org.geotools.referencing.forceXY", "true");
		}
	}

	/**
	 * Register a class as a k.IM toolkit, providing extensions usable from Groovy
	 * or other expression language.
	 * 
	 * @param cls
	 */
	public void registerKimToolkit(Class<?> cls) {
		// TODO Auto-generated method stub

	}

	class RootMonitor implements IMonitor {

		int errors = 0;
		private int waitTime;
		private Inspector inspector;

		@Override
		public void info(Object... info) {
			if (messageBus != null
					&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.INFO.intValue()) {
				messageBus.post(Message.create(getIdentity().getId(), MessageClass.Notification, Type.Info,
						NotificationUtils.getMessage(info).getFirst()));
			} else {
				Logging.INSTANCE.info(info);
			}
		}

		@Override
		public void warn(Object... o) {
			if (messageBus != null
					&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.WARNING.intValue()) {
				messageBus.post(Message.create(getIdentity().getId(), MessageClass.Notification, Type.Warning,
						NotificationUtils.getMessage(o).getFirst()));
			} else {
				Logging.INSTANCE.warn(o);
			}
		}

		@Override
		public void error(Object... o) {
			errors++;
			if (messageBus != null
					&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.SEVERE.intValue()) {
				messageBus.post(Message.create(getIdentity().getId(), MessageClass.Notification, Type.Error,
						NotificationUtils.getMessage(o).getFirst()));
			} else {
				Logging.INSTANCE.error(o);
			}
		}

		@Override
		public void debug(Object... o) {
			if (messageBus != null
					&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.FINE.intValue()) {
				messageBus.post(Message.create(getIdentity().getId(), MessageClass.Notification, Type.Debug,
						NotificationUtils.getMessage(o).getFirst()));
			} else {
				Logging.INSTANCE.debug(o);
			}
		}

		@Override
		public void send(Object... o) {
			if (o != null && o.length > 0) {
				if (messageBus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						messageBus.post((IMessage) o[0]);
					} else {
						messageBus.post(Message.create(rootIdentity.getId(), o));
					}
				}
			}
		}

		@Override
		public Future<IMessage> ask(Object... o) {
			if (o != null && o.length > 0) {
				if (messageBus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						return messageBus.ask((IMessage) o[0]);
					} else {
						return messageBus.ask(Message.create(rootIdentity.getId(), o));
					}
				}
			}
			return null;
		}

		@Override
		public void post(Consumer<IMessage> handler, Object... o) {
			if (o != null && o.length > 0) {
				if (messageBus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						messageBus.post((IMessage) o[0], handler);
					} else {
						messageBus.post(Message.create(rootIdentity.getId(), o), handler);
					}
				}
			}
		}

		@Override
		public IIdentity getIdentity() {
			return rootIdentity;
		}

		@Override
		public boolean hasErrors() {
			return errors > 0;
		}

		@Override
		public boolean isInterrupted() {
			return false;
		}

		@Override
		public void addWait(int seconds) {
			// TODO improve with specific messages
			this.waitTime = seconds;
			warn("Please try this operation again in " + seconds + " seconds");
		}

		@Override
		public int getWaitTime() {
			// TODO Auto-generated method stub
			return this.waitTime;
		}

	}

	@Override
	public IStorageProvider getStorageProvider() {

		String providerComponent = Configuration.INSTANCE.getProperties()
				.getProperty(IConfigurationService.STORAGE_PROVIDER_COMPONENT);
		int nproviders = 0;

		if (storageProvider == null) {
			for (IComponent component : Extensions.INSTANCE.getComponents()) {
				Optional<Class<?>> cclass = ((Component) component).getImplementingClass();
				if (cclass.isPresent() && IStorageProvider.class.isAssignableFrom(cclass.get())) {
					if (providerComponent != null) {
						if (component.getName().equals(providerComponent)) {
							storageProvider = component;
							break;
						}
					} else if (providerComponent == null) {
						storageProvider = component;
					}
					nproviders++;
				}
			}
			if (nproviders > 1 && providerComponent == null) {
				throw new KlabConfigurationException(
						"multiple storage providers found: please configure the class of the desired provider");
			}
		}

		if (storageProvider == null) {
			throw new KlabConfigurationException("no storage provider found: please install a storage plug-in");
		}

		return (IStorageProvider) ((Component) storageProvider).getImplementation();
	}

	@Override
	public IRuntimeProvider getRuntimeProvider() {

		String providerComponent = Configuration.INSTANCE.getProperties()
				.getProperty(IConfigurationService.RUNTIME_PROVIDER_COMPONENT);
		int nproviders = 0;

		if (runtimeProvider == null) {
			for (IComponent component : Extensions.INSTANCE.getComponents()) {
				Optional<Class<?>> cclass = ((Component) component).getImplementingClass();
				if (cclass.isPresent() && IRuntimeProvider.class.isAssignableFrom(cclass.get())) {
					if (providerComponent != null) {
						if (component.getName().equals(providerComponent)) {
							runtimeProvider = component;
							break;
						}
					} else if (providerComponent == null) {
						runtimeProvider = component;
					}
					nproviders++;
				}
			}
			if (nproviders > 1 && providerComponent == null) {
				throw new KlabConfigurationException(
						"multiple dataflow runtime components found: please configure the class of the desired provider");
			}
		}

		if (runtimeProvider == null) {
			throw new KlabConfigurationException(
					"no dataflow runtime component found: please install a runtime plug-in");
		}

		return (IRuntimeProvider) ((Component) runtimeProvider).getImplementation();
	}

	@Override
	public IMonitor getRootMonitor() {
		return rootMonitor;
	}

	@Override
	public IIdentity getRootIdentity() {
		return rootIdentity;
	}

	/**
	 * Resolve a file name to a file using the work directory if the path is not
	 * found as is.
	 * 
	 * @param filename
	 * @return an existing file, or null.
	 */
	public File resolveFile(String filename) {

		if (filename.startsWith("~")) {
			filename = System.getProperty("user.home") + filename.substring(1);
		}

		File ret = new File(filename);
		if (ret.exists()) {
			return ret;
		}
		ret = new File(workDirectory + File.separator + filename);
		return ret.exists() ? ret : null;
	}

	/**
	 * Work directory (defaults at '.') is only for interactive applications (script
	 * running, imports, certificate generation and the like).
	 * 
	 * @return the current work directory
	 */
	public File getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(File file) {
		this.workDirectory = file;
	}

	public void setMessageBus(IMessageBus messageBus) {
		this.messageBus = messageBus;
		Logging.INSTANCE.setMessageBus(messageBus);
	}

	@Override
	public String getResourceSchema() {
		return SchemaExtractor.getSchemata(IConfigurationService.REST_RESOURCES_PACKAGE_ID);
	}

	@Override
	public String getResourceSchema(String resourceId) {
		return SchemaExtractor.getSchema(IConfigurationService.REST_RESOURCES_PACKAGE_ID, resourceId);
	}

	/**
	 * Retrieve runtime capabilities. TODO flesh out.
	 * 
	 * @param user the identity requesting the capabilities.
	 * 
	 * @return the capabilities for the identity.
	 */
	public Capabilities getCapabilities(IIdentity user) {

		Capabilities ret = new Capabilities();

		ret.setName(rootIdentity.getId());
		ret.setVersion(Version.CURRENT);
		ret.setBuild(Version.VERSION_BUILD);
		ret.setOnline(Authentication.INSTANCE.getAuthenticatedIdentity(INetworkSessionIdentity.class) != null);
		// TODO
		// List<ComponentReference> staticComponents = new ArrayList<>();
		// List<ComponentReference> dynamicComponents = new ArrayList<>();
		// long refreshFrequencyMillis = 0;
		// int loadFactor = 0;
		IUserIdentity uid = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
		if (uid != null) {
			ret.setOwner(
					new IdentityReference(uid.getUsername(), uid.getEmailAddress(), uid.getLastLogin().toString()));
		}
		ret.getAuthorities().addAll(Authorities.INSTANCE.getAuthorityDescriptors());
		ret.getResourceAdapters().addAll(Resources.INSTANCE.describeResourceAdapters());
		// IUserIdentity parent = rootIdentity.getParentIdentity(IUserIdentity.class);

		return ret;
	}

	@Override
	public ITicketManager getTicketManager() {
		return ticketManager;
	}

	protected void checkPendingTickets() {
		// TODO Auto-generated method stub
		for (ITicket open : ticketManager.get(ITicket.Status.OPEN)) {
			if (open.getData().containsKey("node")) {
				// check with node
				// System.out.println("Checking open ticket " + open.getId() + "...");
				INodeIdentity node = Network.INSTANCE.getNode(open.getData().get("node"));
				if (node != null && open.getData().get("ticket") != null) {
					TicketResponse.Ticket response = node.getClient().get(API.TICKET.INFO, TicketResponse.Ticket.class,
							"ticket", open.getData().get("ticket"));
					// System.out.println("GOT " + response);
					if (response.getStatus() != Status.OPEN) {
						open.resolve(response);
					}
				}
			}
		}
	}

	/**
	 * Subscribe the passed identity to the event and immediately notify it if it's
	 * ongoing.
	 * 
	 * @param identity
	 * @param event
	 */
	public synchronized void subscribe(IIdentity identity, EngineEvent.Type event) {
		synchronized (eventLock) {
			Map<String, Set<Long>> subscriptions = eventSubscriptions.get(event);
			if (subscriptions == null) {
				subscriptions = new HashMap<>();
				eventSubscriptions.put(event, subscriptions);
			}
			subscriptions.put(identity.getId(), new HashSet<>());
			notifyEventListeners(event, true);
		}
	}

	public synchronized void unsubscribe(IIdentity identity, EngineEvent.Type event) {
		synchronized (eventLock) {
			Map<String, Set<Long>> subscriptions = eventSubscriptions.get(event);
			if (subscriptions == null) {
				subscriptions = new HashMap<>();
				eventSubscriptions.put(event, subscriptions);
			}
			subscriptions.remove(identity.getId());
		}
	}

	private void notifyEventListeners(EngineEvent.Type event, boolean started) {
		Map<String, Set<Long>> subscriptions = eventSubscriptions.get(event);
		Set<Long> eventIds = eventStatus.get(event);
		if (eventIds != null && eventIds.size() > 0) {
			if (subscriptions != null) {
				for (String id : subscriptions.keySet()) {
					IIdentity identity = Authentication.INSTANCE.getIdentity(id, IIdentity.class);
					if (identity instanceof IRuntimeIdentity) {
						for (Long eid : eventIds) {
							EngineEvent message = new EngineEvent();
							message.setStarted(started);
							message.setTimestamp(System.currentTimeMillis());
							message.setType(event);
							message.setId(eid);
							if (started) {
								subscriptions.get(id).add(eid);
							} else {
								subscriptions.get(id).remove(eid);
							}
							((IRuntimeIdentity) identity).getMonitor().send(IMessage.MessageClass.Notification,
									IMessage.Type.EngineEvent, message);
						}
					}
				}
			}
		}
	}

	/*
	 * reaper function that periodically sends notifications for events notified
	 * that have disappeared during a synchronized operation
	 */
	protected void checkPendingEventNotifications() {
		synchronized (eventLock) {
			for (EngineEvent.Type event : eventSubscriptions.keySet()) {
				if (eventStatus.containsKey(event)) {
					Map<String, Set<Long>> subscription = eventSubscriptions.get(event);
					for (String identity : subscription.keySet()) {
						IIdentity receiver = null;
						for (Long eid : subscription.get(identity)) {
							if (!eventStatus.get(event).contains(eid)) {
								if (receiver == null) {
									receiver = Authentication.INSTANCE.getIdentity(identity, IIdentity.class);
								}
								if (receiver instanceof IRuntimeIdentity) {
									EngineEvent message = new EngineEvent();
									message.setStarted(false);
									message.setTimestamp(System.currentTimeMillis());
									message.setType(event);
									message.setId(eid);
									subscription.get(identity).remove(eid);
									((IRuntimeIdentity) receiver).getMonitor().send(IMessage.MessageClass.Notification,
											IMessage.Type.EngineEvent, message);
								}
							}
						}
					}
				}
			}
		}
	}

	public synchronized long notifyEventStart(EngineEvent.Type event) {
		synchronized (eventLock) {

			Set<Long> eventIds = eventStatus.get(event);
			if (eventIds == null) {
				eventIds = new HashSet<>();
				eventStatus.put(event, eventIds);
			}
			long id = eventCounter.incrementAndGet();
			eventIds.add(id);
			Map<String, Set<Long>> subscriptions = eventSubscriptions.get(event);
			if (subscriptions != null) {
				for (String identity : subscriptions.keySet()) {
					IIdentity receiver = Authentication.INSTANCE.getIdentity(identity, IIdentity.class);
					if (receiver instanceof IRuntimeIdentity) {
						EngineEvent message = new EngineEvent();
						message.setStarted(true);
						message.setTimestamp(System.currentTimeMillis());
						message.setType(event);
						message.setId(id);
						subscriptions.get(identity).add(id);
						((IRuntimeIdentity) receiver).getMonitor().send(IMessage.MessageClass.Notification,
								IMessage.Type.EngineEvent, message);
					}
				}
			}
			try {
				// if not enough time passes between event start and stop, the messages won't
				// keep up with the events.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// ignore
			}
			return id;
		}
	}

	public synchronized void notifyEventEnd(long id) {
		synchronized (eventLock) {
			for (EngineEvent.Type type : eventStatus.keySet()) {
				if (eventStatus.get(type).remove(id)) {
					Map<String, Set<Long>> subscriptions = eventSubscriptions.get(type);
					if (subscriptions != null) {
						for (String identity : subscriptions.keySet()) {
							IIdentity receiver = Authentication.INSTANCE.getIdentity(identity, IIdentity.class);
							if (receiver instanceof IRuntimeIdentity) {
								EngineEvent message = new EngineEvent();
								message.setStarted(false);
								message.setTimestamp(System.currentTimeMillis());
								message.setType(type);
								message.setId(id);
								subscriptions.get(identity).remove(id);
								((IRuntimeIdentity) receiver).getMonitor().send(IMessage.MessageClass.Notification,
										IMessage.Type.EngineEvent, message);
							}
						}
					}
					break;
				}
			}
		}
	}

	public void addActivity(IIdentity identity, ActivityBuilder activity) {

		System.out.println(activity);

		INodeIdentity node = getStatisticsServer();
		if (node != null || statisticsConsumer != null) {
			/*
			 * Pull a descriptor and put it in the queue
			 */
			ObservationResultStatistics stats = activity.encode();
			if (stats != null) {
				statisticsQueue.add(stats);
			}
		}

	}

	INodeIdentity getStatisticsServer() {
		if (this.statisticsServer == null) {
			for (INodeIdentity node : Network.INSTANCE.getNodesWithAdapter(STATS_SERVICE_ADAPTER_ID)) {
				// TODO there should be just one, or we should be able to pick the one in our
				// federated hub. See what to do if the field isn't null.
				this.statisticsServer = node;
				break;
			}
		}
		return this.statisticsServer;
	}

}

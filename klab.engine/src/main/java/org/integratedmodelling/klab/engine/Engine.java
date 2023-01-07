package org.integratedmodelling.klab.engine;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.xtext.testing.IInjectorProvider;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Klab.AnnotationHandler;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IKlabUserIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.integratedmodelling.klab.api.extensions.KimToolkit;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.IClient;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.AnonymousEngineCertificate;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.UserIdentity;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.rest.SchemaExtractor;
import org.integratedmodelling.klab.engine.runtime.Script;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabConfigurationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.kim.KimNotifier;
import org.integratedmodelling.klab.kim.KimValidator;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.xtext.KactorsInjectorProvider;
import org.integratedmodelling.klab.utils.xtext.KimInjectorProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.inject.Injector;

public class Engine extends Server implements IEngine, UserDetails {

    private static final long serialVersionUID = 5797834155173805536L;

    private ICertificate certificate;
    private String name;
    // start with a non-null boot time to avoid exceptions at ping() during boot,
    // then redefine after
    private Date bootTime = new Date();
    private Monitor monitor;
    // owner identity may be a IKlabUserIdentity (engines) or INodeIdentity (nodes)
    private IIdentity owner = null;
    private IEngineUserIdentity defaultEngineUser = null;
    private ExecutorService scriptExecutor;
    private ExecutorService taskExecutor;
    private String token = "e_" + NameGenerator.newName();
    protected Set<GrantedAuthority> authorities = new HashSet<>();
    public IParameters<String> globalState = Parameters.create();

    /**
     * A scheduler to periodically check for abandoned sessions and close them
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Check interval for session expiration in minutes. TODO configure.
     */
    protected long sessionCheckMinutes = 15L;
    private ScheduledFuture<?> sessionClosingTask;

    public static class Monitor implements IMonitor {

        private IIdentity identity;
        private int errorCount = 0;
        private AtomicBoolean isInterrupted = new AtomicBoolean(false);
        List<Listener> listeners = new ArrayList<>();
        private int waitTime;

        protected Monitor(IIdentity engine) {
            this.identity = engine;
        }

        protected Monitor(Monitor monitor) {
            this.identity = monitor.identity;
            this.listeners.addAll(monitor.listeners);
            this.errorCount = monitor.errorCount;
        }

        public void setError(Throwable e) {
            this.errorCount++;
        }

        public List<Listener> getListeners() {
            return listeners;
        }

        public void addListener(Listener listener) {
            this.listeners.add(listener);
        }

        @Override
        public void info(Object... info) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(info);
            Consumer<String> infoWriter = Logging.INSTANCE.getInfoWriter();
            if (infoWriter != null) {
                infoWriter.accept(message.getFirst());
            }
            send(new KimNotification(message, Level.INFO));
        }

        @Override
        public void warn(Object... o) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            Consumer<String> warningWriter = Logging.INSTANCE.getWarningWriter();
            if (warningWriter != null) {
                warningWriter.accept(message.getFirst());
            }
            send(new KimNotification(message, Level.WARNING));
        }

        @Override
        public void error(Object... o) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            Consumer<String> errorWriter = Logging.INSTANCE.getErrorWriter();
            if (errorWriter != null) {
                errorWriter.accept(message.getFirst());
            }
            send(new KimNotification(message, Level.SEVERE));
            errorCount++;
        }

        @Override
        public void debug(Object... o) {
            Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
            Consumer<String> debugWriter = Logging.INSTANCE.getDebugWriter();
            if (debugWriter != null) {
                debugWriter.accept(message.getFirst());
            }
            send(new KimNotification(message, Level.FINE));
        }

        @Override
        public void send(Object... o) {

            IMessage message = null;

            if (o != null && o.length > 0) {
                IMessageBus bus = Klab.INSTANCE.getMessageBus();
                if (bus != null) {
                    if (o.length == 1 && o[0] instanceof IMessage) {
                        bus.post(message = (IMessage) o[0]);
                    } else if (o.length == 1 && o[0] instanceof INotification) {
                        bus.post(message = Message.create((INotification) o[0], this.identity.getId()));
                    } else {
                        bus.post(message = Message.create(this.identity.getId(), o));
                    }
                }
            }
        }

        @Override
        public Future<IMessage> ask(Object... o) {
            if (o != null && o.length > 0) {
                IMessageBus bus = Klab.INSTANCE.getMessageBus();
                if (bus != null) {
                    if (o.length == 1 && o[0] instanceof IMessage) {
                        return bus.ask((IMessage) o[0]);
                    } else if (o.length == 1 && o[0] instanceof INotification) {
                        return bus.ask(Message.create((INotification) o[0], this.identity.getId()));
                    } else {
                        return bus.ask(Message.create(this.identity.getId(), o));
                    }
                }
            }
            return null;
        }

        @Override
        public void post(Consumer<IMessage> handler, Object... o) {
            if (o != null && o.length > 0) {
                IMessageBus bus = Klab.INSTANCE.getMessageBus();
                if (bus != null) {
                    if (o.length == 1 && o[0] instanceof IMessage) {
                        bus.post((IMessage) o[0], handler);
                    } else if (o.length == 1 && o[0] instanceof INotification) {
                        bus.post(Message.create((INotification) o[0], this.identity.getId()), handler);
                    } else {
                        bus.post(Message.create(this.identity.getId(), o), handler);
                    }
                }
            }
        }

        @Override
        public IIdentity getIdentity() {
            return identity;
        }

        @Override
        public boolean hasErrors() {
            return errorCount > 0;
        }

        public Monitor get(IIdentity identity) {
            Monitor ret = new Monitor(identity);
            return ret;
        }

        /**
         * Called to notify the start of any runtime job pertaining to our identity (always a
         * {@link IRuntimeIdentity} such as a task or script).
         */
        public void notifyStart() {
            System.out.println(identity + " started");
        }

        /**
         * Called to notify the start of any runtime job pertaining to our identity (always a
         * {@link IRuntimeIdentity} such as a task or script).
         * 
         * @param error true for abnormal exit
         */
        public void notifyEnd(boolean error) {
            ((errorCount > 0 || error) ? System.err : System.out).println(
                    identity + ((errorCount > 0 || error)
                            ? " finished with errors"
                            : " finished with no errors"));
        }

        public void interrupt() {
            isInterrupted.set(true);
            // IIdentity id = getIdentity();
            // // interrupt any parents that are the same class as ours (i.e. tasks)
            // while (id != null &&
            // id.getClass().isAssignableFrom(id.getParentIdentity().getClass())) {
            // id = id.getParentIdentity();
            // ((Monitor)((IRuntimeIdentity)id).getMonitor()).interrupt();
            // }
        }

        @Override
        public boolean isInterrupted() {
            return isInterrupted.get();
        }

        @Override
        public void addWait(int seconds) {
            this.waitTime = seconds;
            warn("Please try this operation again in " + seconds + " seconds");
        }

        @Override
        public int getWaitTime() {
            return this.waitTime;
        }
    }

    public Engine(ICertificate certificate) {
        this.certificate = certificate;
        this.owner = Authentication.INSTANCE.authenticate(certificate);
        Klab.INSTANCE.setRootIdentity(this.owner);
    }

    @Override
    public IIdentity getParentIdentity() {
        return this.owner;
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public IIdentity.Type getIdentityType() {
        return IIdentity.Type.ENGINE;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public IEngineUserIdentity authenticateUser(IUserCredentials credentials) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Session createSession() {
        return createSession(getDefaultEngineUser());
    }

    public IEngineUserIdentity getDefaultEngineUser() {
        if (defaultEngineUser == null) {
            IKlabUserIdentity owner = this.getParentIdentity(IKlabUserIdentity.class);
            if (owner == null) {
                /*
                 * this only happens in a node, which should only create sessions for explicitly
                 * authorized users.
                 */
                throw new KlabAuthorizationException(
                        "Node engines cannot create modeling sessions without explicit authorization");
            }

            defaultEngineUser = new EngineUser((UserIdentity) owner, this);
            ((EngineUser) defaultEngineUser).getAuthorities().add(new SimpleGrantedAuthority(Roles.OWNER));
        }
        return defaultEngineUser;
    }

    @Override
    public Session createSession(IUserIdentity user) {
        Session session = new Session(this, user);
        for (Consumer<ISession> callback : Klab.INSTANCE.getSessionInitializers()) {
        	callback.accept(session);
        }
        return session;
    }

    /**
     * Create an engine using the default k.LAB certificate and options, and start it. Return after
     * startup is complete.
     * 
     * @return a new running engine.
     * @throws KlabAuthorizationException if certificate is invalid
     * @throws KlabException if startup fails
     */
    public static Engine start() {
        return start(null, new EngineStartupOptions());
    }

    public static Engine start(ICertificate certificate) {
        return start(certificate, new EngineStartupOptions());
    }

    public static Engine start(EngineStartupOptions options) {
        return start(null, options);
    }

    public static Engine start(ICertificate certificate, IEngineStartupOptions options) {

        if (certificate == null) {

            if (options.isAnonymous()) {
                certificate = new AnonymousEngineCertificate();
            } else {

                if (options.getCertificateResource() != null) {
                    certificate = KlabCertificate.createFromClasspath(options.getCertificateResource());
                } else {
                    File certFile = options.getCertificateFile();
                    if (!certFile.exists()) {
                        // check for legacy certificate
                        certFile = new File(
                                Configuration.INSTANCE.getDataPath() + File.separator + "im.cert");
                    }
                    certificate = certFile.exists()
                            ? KlabCertificate.createFromFile(certFile)
                            : KlabCertificate.createDefault();
                }
            }
        }

        if (!certificate.isValid()) {
            throw new KlabAuthorizationException(
                    "certificate is invalid: " + certificate.getInvalidityCause());
        }

        Engine ret = new Engine(certificate);

        if (!ret.boot(options)) {
            throw new KlabException("engine failed to start");
        }

        if (certificate.getType() == ICertificate.Type.ENGINE) {
            System.out.println("\n" + Logo.ENGINE_BANNER);
            System.out.println(
                    "\nStartup successful: " + ret.getUsername() + " v" + Version.CURRENT + " on "
                            + new Date());
        }

        return ret;
    }

    public boolean stop() {

        // shutdown all components
        if (this.sessionClosingTask != null) {
            this.sessionClosingTask.cancel(true);
        }

        // shutdown the task executor
        if (taskExecutor != null) {
            taskExecutor.shutdown();
            try {
                if (!taskExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    taskExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                taskExecutor.shutdownNow();
            }
        }

        // shutdown the script executor
        if (scriptExecutor != null) {
            scriptExecutor.shutdown();
            try {
                if (!scriptExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    scriptExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                scriptExecutor.shutdownNow();
            }
        }

        // and the session scheduler
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }

        // shutdown the indexer
        Indexer.INSTANCE.commitChanges();

        // shutdown the runtime
        Klab.INSTANCE.getRuntimeProvider().shutdown();

        return true;
    }

    /**
     * Perform the engine boot sequence. Can only be called after a valid certificate was read or
     * anonymous status was granted. The boot sequence consists of:
     * 
     * <ul>
     * <li></li>
     * </ul>
     * 
     * @param options the options read from the command line; a default is provided if no command
     *        line was used.
     * 
     * @return true if the boot was successful, false otherwise. Exceptions are only thrown in case
     *         of bad usage (called before a certificate is read).
     */
    protected boolean boot(IEngineStartupOptions options) {

        runJvmChecks();

        if (options.isHelp()) {
            System.out.println(options.usage());
            System.exit(0);
        }

        Logging.INSTANCE.info("k.LAB v" + Version.CURRENT + " build " + Version.VERSION_BUILD + " @"
                + Version.VERSION_COMMIT + " booting...");

        /*
         * set up access to the k.IM grammar
         */
        IInjectorProvider injectorProvider = new KimInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            Kim.INSTANCE.setup(injector);
        }

        /*
         * ...and k.Actors
         */
        IInjectorProvider kActorsInjectorProvider = new KactorsInjectorProvider();
        Injector kActorsInjector = kActorsInjectorProvider.getInjector();
        if (kActorsInjector != null) {
            KActors.INSTANCE.setup(kActorsInjector);
        }

        if (certificate == null) {
            throw new UnsupportedOperationException(
                    "Engine.boot() was called before a valid certificate was read. Exiting.");
        }

        this.monitor = new Monitor(this);

        /*
         * boot the actor system
         */
        Actors.INSTANCE.setup();

        /**
         * Annotation prototypes
         */
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            for (Resource res : patternResolver.getResources("components/engine/services/*.kdl")) {
                Annotations.INSTANCE.declareServices(res.getURL());
            }
            for (Resource res : patternResolver.getResources("components/engine/doc/*.*")) {
                DataflowDocumentation.INSTANCE.addTemplate("components/engine/doc/" + res.getFilename());
            }
        } catch (Exception e) {
            Logging.INSTANCE.error(e);
            return false;
        }

        boolean ret = true;
        try {
            /*
             * read core OWL knowledge from classpath
             */
            if (!Resources.INSTANCE.loadCoreKnowledge(this.monitor)) {
                Logging.INSTANCE.error("could not load core knowledge");
                return false;
            }

            /*
             * Install the k.IM validator and notifier to build concepts and model objects
             */
            Kim.INSTANCE.setValidator(new KimValidator(this.monitor));
            Kim.INSTANCE.addNotifier(new KimNotifier(this.monitor));

            /*
             * register instance now before loading projects have a chance to screw up the boot.
             * This should be later but there are still issues with non-responding resources that
             * make a functioning engine not register.
             */
            Authentication.INSTANCE.registerIdentity(this);

            /*
             * initialize but do not load the local workspace, so that we can later override the
             * worldview if we have some worldview projects in the workspace.
             */
            Resources.INSTANCE.initializeLocalWorkspace(options.getWorkspaceLocation(), this.monitor);

            /*
             * initialize but do not load the service workspace.
             */
            Resources.INSTANCE.initializeServiceWorkspace(options.getServiceLocation(), this.monitor);

            /*
             * prime and check integrity of kboxes; init listeners for Kim reading
             */

            /*
             * get worldview from certificate and sync it
             */
            if (!Resources.INSTANCE.loadWorldview(certificate, this.monitor)) {
                Logging.INSTANCE.error("could not load worldview");
                return false;
            }

            /*
             * TODO hop on the network
             */

            /*
             * all binary content is now available: scan the classpath for recognized extensions
             */
            scanClasspath();

            /*
             * sync components and load binary assets
             */
            Resources.INSTANCE.loadComponents(options.getComponentPaths(), this.monitor);

            /*
             * load component knowledge after all binary content is registered.
             */
            try {
                Resources.INSTANCE.getComponentsWorkspace().load(getMonitor());
            } catch (Throwable t) {
                Logging.INSTANCE
                        .error("Component workspace contains errors: proceed at your own risk. Error message was: "
                                + t.getMessage());
            }

            /*
             * save cache of function prototypes and resolved URNs for clients
             */
            saveClientInformation();

            /*
             * now we can finally load the workspace
             */
            if (!Resources.INSTANCE.loadLocalWorkspace(this.monitor)) {
                Logging.INSTANCE.error("could not load local workspace");
                return false;
            }

            /*
             * cache all resource schemata before anything asks for them
             */
            SchemaExtractor.extractResourceSchema(IConfigurationService.REST_RESOURCES_PACKAGE_ID);

            /*
             * run any init scripts from configuration
             */

            /*
             * run any init scripts from parameters
             */

            /*
             * Schedule the session reaper
             */
            this.sessionClosingTask = scheduler.scheduleAtFixedRate(() -> closeExpiredSessions(), 1,
                    sessionCheckMinutes, TimeUnit.MINUTES);

            /*
             * After the engine has successfully booted, it becomes the root identity and is owned
             * by the context owner.
             */
            this.owner = Klab.INSTANCE.getRootMonitor().getIdentity();
            Klab.INSTANCE.setRootIdentity(this);

            /*
             * establish engine authority
             */
            this.authorities.add(new SimpleGrantedAuthority(Roles.ENGINE));
            Logging.INSTANCE.info("Engine authenticated and registered");

            /*
             * Load the service workspace last. TODO we may want to reset it if the load fails.
             */
            Resources.INSTANCE.loadServiceWorkspace(this.monitor);

            Actors.INSTANCE.loadUserBehaviors();

            /*
             * boot time is now
             */
            this.bootTime = new Date();

            /*
             * if exit after scripts is requested, exit
             */
            if (options.isExitAfterStartup()) {
                System.exit(0);
            }

        } catch (Exception e) {
            Logging.INSTANCE.error(e.getClass().getCanonicalName() + ": " + e);
            // ret = false;
        } finally {
            Indexer.INSTANCE.commitChanges();
        }

        return ret;
    }

    protected void closeExpiredSessions() {
        // TODO Auto-generated method stub
        // Logging.INSTANCE.info("checking for expired sessions...");
    }

    private void runJvmChecks() {
        // verify we're 64 bit and run on 1.8+; throw an exception if not
        String bitness = System.getProperty("sun.arch.data.model");
        String version = System.getProperty("java.version");
        if (bitness == null || version == null) {
            // we're on a non-Sun JDK; don't die but show some unhappiness
            Logging.INSTANCE
                    .warn("Cannot establish Java version and bit model: JRE is non-standard, proceed at your own risk");
        } else {
            int bits = Integer.parseInt(bitness);
            Version vers = Version.create(version);
            if (bits < 64 || !vers.isGreaterOrEqualTo(Version.create("1.8.0"))) {
                throw new KlabConfigurationException(
                        "JVM is incompatible with k.LAB: must be 64 bit and 1.8 or later");
            }
        }
    }

    /**
     * Save JSON files for prototypes and URN resolution data for any clients to use in validation.
     */
    private void saveClientInformation() {
        Extensions.INSTANCE.exportPrototypes(
                new File(
                        Configuration.INSTANCE.getDataPath("language") + File.separator + "prototypes.json"));
        Annotations.INSTANCE.exportPrototypes(
                new File(Configuration.INSTANCE.getDataPath("language") + File.separator
                        + "annotations.json"));
        Actors.INSTANCE.exportBehaviors(
                new File(Configuration.INSTANCE.getDataPath("language") + File.separator + "behaviors.json"));
    }

    private void scanClasspath() throws KlabException {

        registerCommonAnnotations();

        Klab.INSTANCE.registerAnnotationHandler(KimToolkit.class, new AnnotationHandler(){

            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
                Klab.INSTANCE.registerKimToolkit(cls);
            }
        });

        Klab.INSTANCE.scanPackage("org.integratedmodelling");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getBootTime() {
        return bootTime;
    }

    @Override
    public IScript run(URL resource) throws KlabException {

        IScript ret = null;
        if (resource.toString().endsWith(".kim")) {
            Logging.INSTANCE.info("running namespace " + resource);
            return new Script(this, resource);
        }
        return ret;
    }

    @Override
    public boolean is(Type type) {
        return getIdentityType() == type;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    /**
     * Get the Executor that will run script tasks.
     * 
     * @return a valid executor
     */
    public ExecutorService getScriptExecutor() {
        if (scriptExecutor == null) {
            // TODO condition both the type and the parameters of the executor to options
            scriptExecutor = Executors.newFixedThreadPool(Configuration.INSTANCE.getScriptThreadCount());
        }
        return scriptExecutor;
    }

    /**
     * Get the Executor that will run script tasks.
     * 
     * @return a valid executor
     */
    public ExecutorService getTaskExecutor() {
        if (taskExecutor == null) {
            // TODO condition both the type and the parameters of the executor to options
            taskExecutor = Executors.newFixedThreadPool(Configuration.INSTANCE.getTaskThreadCount());
        }
        return taskExecutor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<String> getUrls() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOnline() {
        return Network.INSTANCE.getHub() != null && Network.INSTANCE.getNodes().size() > 0;
    }

    @Override
    public IClient getClient() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String load(IBehavior behavior, IContextualizationScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean stop(String behaviorId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IParameters<String> getState() {
        return globalState;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package org.integratedmodelling.klab.engine;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Klab.AnnotationHandler;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.engine.ICapabilities;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.integratedmodelling.klab.api.extensions.KimToolkit;
import org.integratedmodelling.klab.api.extensions.KlabBatchRunner;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.common.monitoring.MulticastMessageBus;
import org.integratedmodelling.klab.engine.runtime.Script;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.kim.KimValidator;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public class Engine extends Server implements IEngine {

  private ICertificate        certificate;
  private String              name;
  private Date                bootTime;
  private MulticastMessageBus multicastBus;
  private Monitor             monitor;
  private IEngineUserIdentity user = null;
  private ExecutorService     scriptExecutor;
  private ExecutorService     taskExecutor;

  public class Monitor implements IMonitor {

    private IIdentity identity   = Engine.this;
    int               errorCount = 0;

    @Override
    public void info(Object... info) {
      // TODO Auto-generated method stub
      System.out.println(NotificationUtils.getMessage(info));
    }

    @Override
    public void warn(Object... o) {
      // TODO Auto-generated method stub
      System.err.println(NotificationUtils.getMessage(o));
    }

    @Override
    public void error(Object... o) {
      // TODO Auto-generated method stub
      System.err.println(NotificationUtils.getMessage(o));
      errorCount++;
    }

    @Override
    public void debug(Object... o) {
      // TODO Auto-generated method stub
      System.err.println(NotificationUtils.getMessage(o));
    }

    @Override
    public void send(Object o) {
      // TODO Auto-generated method stub
    }

    @Override
    public IIdentity getIdentity() {
      return identity;
    }

    @Override
    public boolean hasErrors() {
      // TODO Auto-generated method stub
      return false;
    }

    public Monitor get(IIdentity identity) {
      Monitor ret = new Monitor();
      ret.identity = identity;
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
      ((errorCount > 0 || error) ? System.err : System.out).println(identity
          + ((errorCount > 0 || error) ? " finished with errors" : " finished with no errors"));
    }

  }

  public Engine(ICertificate certificate) {
    // TODO
    this.certificate = certificate;
  }

  @Override
  public INetworkSessionIdentity getParentIdentity() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
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
  public ICapabilities getCapabilities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Session createSession() {
    return createSession(getParentIdentity(IEngineUserIdentity.class));
  }

  @Override
  public Session createSession(IEngineUserIdentity user) {
    // TODO Auto-generated method stub
    return new Session(this, user);
  }

  /**
   * Create an engine using the default k.LAB certificate and options, and start it. Return after
   * startup is complete.
   * 
   * @return a new running engine.
   * @throws KlabRuntimeException if startup fails
   */
  public static Engine start() {
    return start(new EngineStartupOptions());
  }

  public static Engine start(IEngineStartupOptions options) {
    File certFile = options.getCertificateFile();
    Engine ret = new Engine(certFile.exists() ? KlabCertificate.createFromFile(certFile)
        : KlabCertificate.createDefault());
    if (!ret.boot(options)) {
      throw new KlabRuntimeException("engine failed to start");
    }
    return ret;
  }


  public void stop() {

    // TODO shutdown all components

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
  }

  /**
   * Perform the engine boot sequence. Can only be called after a valid certificate was read or
   * anonymous status was granted. The boot sequence consists of:
   * 
   * <ul>
   * <li></li>
   * </ul>
   * 
   * @param options the options read from the command line; a default is provided if no command line
   *        was used.
   * 
   * @return true if the boot was successful, false otherwise. Exceptions are only thrown in case of
   *         bad usage (called before a certificate is read).
   */
  private boolean boot(IEngineStartupOptions options) {

    if (options.isHelp()) {
      System.out.println(options.usage());
      System.exit(0);
    }

    if (certificate == null) {
      throw new UnsupportedOperationException(
          "Engine.boot() was called before a valid certificate was read. Exiting.");
    }

    this.monitor = new Monitor();

    /*
     * load annotation prototypes declared in this package
     */
    for (String kdl : new Reflections(getClass().getPackage().getName(), new ResourcesScanner())
        .getResources(Pattern.compile(".*\\.kdl"))) {
      try {
        Annotations.INSTANCE.declareServices(getClass().getClassLoader().getResource(kdl));
      } catch (KlabException e) {
        Logging.INSTANCE.error(e);
        return false;
      }
    }

    /*
     * if we have been asked to open a communication channel from a client, do so. The channel
     * should be unique among all engines on the same network.
     */
    if (options.getMulticastChannel() != null) {
      Logging.INSTANCE.info("Starting multicast of IP on cluster " + options.getMulticastChannel()
          + " communicating on port " + options.getPort());
      this.multicastBus =
          new MulticastMessageBus(this, options.getMulticastChannel(), options.getPort());

      /*
       * TODO send 'boot started' message
       */

    }



    boolean ret = true;
    try {
      /*
       * read core OWL knowledge from classpath
       */
      if (!Resources.INSTANCE.loadCoreKnowledge(this.monitor)) {
        return false;
      }

      /*
       * Install the k.IM validator to build concepts and model objects
       */
      Kim.INSTANCE.setValidator(new KimValidator(this.monitor));

      /*
       * initialize but do not load the local workspace, so that we can later override the worldview
       * if we have some worldview projects in the workspace.
       */
      Resources.INSTANCE.initializeLocalWorkspace(options.getWorkspaceLocation(), this.monitor);

      /*
       * prime and check integrity of kboxes; init listeners for Kim reading
       */

      /*
       * get worldview from certificate and sync it
       */
      if (!Resources.INSTANCE.loadWorldview(certificate, this.monitor)) {
        return false;
      }

      /*
       * TODO hop on the network
       */

      /*
       * sync components and load binary assets
       */
      // Workspaces.INSTANCE.loadComponents(options.get);

      /*
       * all binary content is now available: scan the classpath for recognized extensions
       */
      scanClasspath();

      /*
       * load component knowledge after their binary content is registered.
       */

      /*
       * now we can finally load the workspace
       */
      if (!Resources.INSTANCE.loadLocalWorkspace(this.monitor)) {
        return false;
      }

      /*
       * run any init scripts from configuration
       */

      /*
       * run any init scripts from parameters
       */

      /*
       * save cache of function prototypes and resolved URNs for clients
       */
      saveClientInformation();

      /*
       * if anything is connected, send 'boot finished' message.
       */
      if (multicastBus != null) {
        // TODO
      }

      /*
       * if exit after scripts is requested, exit
       */
      if (options.isExitAfterStartup()) {
        System.exit(0);
      }

    } catch (Exception e) {
      ret = false;
    }

    return ret;
  }

  /**
   * Save JSON files for prototypes and URN resolution data for any clients to use in validation.
   */
  private void saveClientInformation() {
    Extensions.INSTANCE.exportPrototypes(new File(
        Configuration.INSTANCE.getDataPath("language") + File.separator + "prototypes.json"));
    Annotations.INSTANCE.exportPrototypes(new File(
        Configuration.INSTANCE.getDataPath("language") + File.separator + "annotations.json"));
  }

  private void scanClasspath() throws KlabException {

    registerCommonAnnotations();

    // // TODO reinstate whatever must be kept plus any data services and subsystems
    // Klab.INSTANCE.registerAnnotationHandler(SubjectType.class, new AnnotationHandler() {
    // @SuppressWarnings("unchecked")
    // @Override
    // public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
    // String concept = ((SubjectType) annotation).value();
    // if (ISubject.class.isAssignableFrom(cls)) {
    // Observations.INSTANCE.registerSubjectClass(concept, (Class<? extends ISubject>) cls);
    // }
    // }
    // });

    Klab.INSTANCE.registerAnnotationHandler(KlabBatchRunner.class, new AnnotationHandler() {
      @Override
      public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
        // String id = ((KlabBatchRunner) annotation).id();
        // if (IBatchRunner.class.isAssignableFrom(cls)) {
        // KLAB.registerRunnerClass(id, (Class<? extends IBatchRunner>) cls);
        // }
      }
    });

    Klab.INSTANCE.registerAnnotationHandler(KimToolkit.class, new AnnotationHandler() {
      @Override
      public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
        Klab.INSTANCE.registerKimToolkit(cls);
      }
    });

    Klab.INSTANCE.scanPackage("org.integratedmodelling.klab");
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

    /*
     * 'script' can be .kim (test namespace) or .ks (host language script)
     */
    if (resource.toString().endsWith(".kim")) {

      // TODO this must create a task and a script in it.
      Logging.INSTANCE.info("running namespace " + resource);
      return new Script(this, resource);
    }

    return ret;
  }

  @Override
  public boolean is(Type type) {
    return TYPE == type;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  public MulticastMessageBus getMessageBus() {
    return multicastBus;
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

}

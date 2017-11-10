package org.integratedmodelling.klab.engine;

import java.lang.annotation.Annotation;
import java.util.Date;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.ICapabilities;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.extensions.KimToolkit;
import org.integratedmodelling.klab.api.extensions.KlabBatchRunner;
import org.integratedmodelling.klab.api.extensions.SubjectType;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.MulticastMessageBus;
import org.integratedmodelling.klab.api.services.IRuntimeService.AnnotationHandler;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Engine extends Server implements IEngine {

    private ICertificate        certificate;
    private String              name;
    private Date                bootTime;
    private MulticastMessageBus multicastBus;

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
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
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
    public ISession createSession(IEngineUserIdentity user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEngineUserIdentity getUser() {
        IIdentity identity = certificate.getIdentity();
        if (!(identity instanceof IUserIdentity)) {
            // TODO shit all over the place
        }
        return identity instanceof IEngineUserIdentity ? (IEngineUserIdentity) identity
                : null /* TODO make engine user out of other */;
    }

    /**
     * Create an engine using the default k.LAB certificate and options, and start it. Return after startup is
     * complete.
     * 
     * @return a new running engine, or null if startup failed.
     */
    public static IEngine start() {
        EngineStartupOptions options = new EngineStartupOptions();
        Engine ret = new Engine(new KlabCertificate(options.getCertificateFile()));
        if (!ret.boot(options)) {
            return null;
        }
        return ret;
    }

    /**
     * Perform the engine boot sequence. Can only be called after a valid certificate was read. The boot
     * sequence consists of:
     * 
     * <ul>
     * <li></li>
     * </ul>
     * 
     * @param options
     *            the options read from the command line; a default is provided if no command line was used.
     * 
     * @return true if the boot was successful, false otherwise. Exceptions are only thrown in case of bad
     *         usage (called before a certificate is read).
     */
    private boolean boot(EngineStartupOptions options) {

        if (options.isHelp()) {
            System.out.println(options.usage());
            System.exit(0);
        }

        if (certificate == null) {
            throw new UnsupportedOperationException("Engine.boot() was called before a valid certificate was read. Exiting.");
        }

        boolean ret = true;
        try {
            /*
             *  read core OWL knowledge from classpath
             */
            Workspaces.INSTANCE.loadCoreKnowledge();

            /*
             * initialize but do not load the local workspace, so that we can later override the worldview if we
             * have some worldview projects in the workspace.
             */
            Workspaces.INSTANCE.initializeLocalWorkspace(options.getWorkspaceLocation());

            /*
             *  prime and check integrity of kboxes; init listeners for Kim reading
             */

            /*
             *  get worldview from certificate and sync it
             */
            Workspaces.INSTANCE.loadWorldview(certificate);

            /*
             *  hop on the network
             */

            /*
             *  sync components and load binary assets
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
             *  now we can finally load the workspace
             */
            Workspaces.INSTANCE.getLocal().load(false);

            /*
             *  run any init scripts from configuration
             */

            /*
             * run any init scripts from parameters
             */

            /*
             * if exit after scripts is requested, exit
             */
            if (options.isExitAfterStartup()) {
                System.exit(0);
            }

            /*
             * if we have been asked to open a communication channel from a client, do so. The channel
             * should be unique among all engines on the same network.
             */
            if (options.getMulticastChannel() != null) {
                Klab.INSTANCE.info("Starting multicast of IP on cluster " + options.getMulticastChannel()
                        + " communicating on port " + options.getPort());
                this.multicastBus = new MulticastMessageBus(this, options.getMulticastChannel(), options
                        .getPort());
            }

        } catch (Exception e) {
            ret = false;
        }

        return ret;
    }

    private void scanClasspath() throws KlabException {

        registerCommonAnnotations();

        // TODO reinstate whatever must be kept plus any data services and subsystems
        Klab.INSTANCE.registerAnnotationHandler(SubjectType.class, new AnnotationHandler() {
            @SuppressWarnings("unchecked")
            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
                String concept = ((SubjectType) annotation).value();
                if (ISubject.class.isAssignableFrom(cls)) {
                    Observations.INSTANCE.registerSubjectClass(concept, (Class<? extends ISubject>) cls);
                }
            }
        });

        Klab.INSTANCE.registerAnnotationHandler(KlabBatchRunner.class, new AnnotationHandler() {
            @Override
            public void processAnnotatedClass(Annotation annotation, Class<?> cls) {
                String id = ((KlabBatchRunner) annotation).id();
//                if (IBatchRunner.class.isAssignableFrom(cls)) {
//                    KLAB.registerRunnerClass(id, (Class<? extends IBatchRunner>) cls);
//                }
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

}

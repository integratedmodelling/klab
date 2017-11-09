package org.integratedmodelling.klab.engine;

import org.integratedmodelling.klab.Workspaces;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.ICapabilities;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.auth.KlabCertificate;

public class Engine implements IEngine {

    ICertificate certificate;

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

        if (certificate == null) {
            throw new UnsupportedOperationException("Engine.boot() was called before a vallid certificate was read. Exiting.");
        }

        boolean ret = true;
        try {
            // setup logging

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
             *  sync and read components
             */
            // Workspaces.INSTANCE.loadComponents(options.get);

            /*
             * all binary content available: scan the classpath for all recognized extensions
             */
            scanClasspath();

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

        } catch (Exception e) {
            ret = false;
        }

        return ret;
    }

    private void scanClasspath() {
        // TODO Auto-generated method stub

    }

}

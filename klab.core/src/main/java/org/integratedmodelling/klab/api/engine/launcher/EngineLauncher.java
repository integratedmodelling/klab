/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.engine.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.invoker.SystemOutLogger;
import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.JavaUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NetUtilities;
import org.integratedmodelling.klab.utils.OS;
import org.integratedmodelling.klab.utils.URLUtils;

// TODO: Auto-generated Javadoc
/**
 * A launcher for an engine, handling both local Maven installs for developers and official distributions
 * synchronized from the network. The launcher also handles cleaning and restarting a crashed engine if
 * necessary.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public abstract class EngineLauncher {

    private static final String LOGGING_FILE = "logging.file";

    /**
     * The listener interface for receiving launch events. The class that is interested in
     * processing a launch event implements this interface, and the object created with that class
     * is registered with a component using the component's <code>addLaunchListener<code> method.
     * When the launch event occurs, that object's appropriate method is invoked.
     */
    public static interface LaunchListener {

        void shutdownStarted();

        void shutdownFinished();

        /**
         * only called if Maven launch is done
         */
        void compilationSuccessful();

        /**
         * called if compilation happens and fails
         */
        void compilationFailed();

        /**
         * called after launch
         */
        void launchSuccessful();

        /**
         * called if launch fails
         * 
         * @param exception
         */
        void launchFailed(Throwable exception);
    }

    /**
     * The Class Source.
     *
     * @author ferdinando.villa
     */
    public static class Source {
        boolean        isDevelop;
        File           jarFile;
        int            debugPort;
        
        /** The use debug. */
        public boolean useDebug;
        
        /** The is local. */
        public boolean isLocal;
        
        /** The maven directory. */
        public File    mavenDirectory;

        /**
         * Requires restart.
         *
         * @param previous the previous
         * @return true, if successful
         */
        public boolean requiresRestart(Source previous) {
            if (isDevelop != previous.isDevelop) {
                // TODO!
            }
            return false;
        }
    }

    File                mavenDirectory;
    File                engineJar;
    File                engineJarDev;
    File                logFile;

    static final String BUILD_FILE_URL       = "http://www.integratedmodelling.org/downloads/build.txt";
    static final String BUILD_DEV_FILE_URL   = "http://www.integratedmodelling.org/downloads/build_dev.txt";

    int                 memLimitMb           = 2048;
    int                 networkPort          = 8183;
    File                dataDir              = null;
    File                certificate          = null;

    int                 debugPort            = 8897;

    boolean             launchAutomatically;
    boolean             upgradeAutomatically;
    boolean             stopOnExit;
    boolean             useLocal;
    boolean             useDeveloper;
    boolean             useDebug;

    // not tied to options - just set when necessary.
    boolean             offlineMode          = true;

    Integer             localBuildNumber     = null;
    Integer             localBuildNumberDev  = null;
    Integer             remoteBuildNumber    = null;
    Integer             remoteBuildNumberDev = null;

    String              context              = "/kmodeler";

    LaunchListener      listener;

    /**
     * <p>error.</p>
     *
     * @param error a {@link java.lang.String} object.
     */
    protected abstract void error(String error);

    /*
     * obsolete, as we require Java 8; if we didn't this would be a nice permsize constant
     * to use.
     */
    final int       PERMSIZE = 256;
    private String  clientSignature;
    private boolean klabDebug;

    /**
     * Create a launcher with the passed certificate and datadir. If a client signature is passed,
     * it is compared with anything found in .clientsig in the datadir, and if the two are the same
     * the engine will assume that it is running on the same filesystem as the client and avoid web
     * transfer for project synchronization.
     *
     * @param certificate the certificate
     * @param dataDir the data dir
     * @param clientSignature the client signature
     */
    public EngineLauncher(File certificate, File dataDir, String clientSignature) {

        /*
         * TODO talk to the client and see if we have local engines already running. If
         * these do not correspond to our port, change message to notify that engine ops
         * will be allowed when the configured engine is the active one.
         */

        this.dataDir = dataDir;
        this.clientSignature = clientSignature;

        File certFile = new File(dataDir + File.separator + "im.cert");
        if (!certificate.equals(certFile)) {
            try {
                FileUtils.copyFile(certificate, certFile);
                this.certificate = certFile;
            } catch (IOException e) {
                throw new KlabRuntimeException(e);
            }
        }
        define(Configuration.INSTANCE.getProperties());
        this.logFile = new File(dataDir + File.separator + "logs");
        this.logFile.mkdirs();
        this.logFile = new File(this.logFile + File.separator + "klab.log");
    }

    /**
     * Gets the maven directory.
     *
     * @return the maven directory
     */
    public File getMavenDirectory() {
        return mavenDirectory;
    }

    /**
     * Determine the OS-dependent binary artifacts to make available to the engine and ensure that
     * they are available, downloading if necessary. In case of error it just logs and returns
     * false.
     *
     * @return a boolean.
     */
    public boolean downloadExtBinaryArtifacts() {

        boolean ret = true;

        String ext = "win64";
        String[] libs = new String[] { "smile32.jar", "smile64.jar", "jsmile.dll" };
        if (Configuration.INSTANCE.getOS().equals(OS.UNIX)) {
            ext = "linux";
            libs = new String[] {
                    "smile32.jar",
                    "smile64.jar",
                    "libjsmile.so",
                    "libjsmile32.so",
                    "libjsmile64.so" };
        } else if (Configuration.INSTANCE.getOS().equals(OS.MACOS)) {
            ext = "osx";
            libs = new String[] { "smile.jar", "libjsmile.jnilib" };
        }
        File extdir = new File(Configuration.INSTANCE.getDataPath("engine") + File.separator + "ext"
                + File.separator + ext);

        if (!extdir.exists()) {
            extdir.mkdirs();
            for (String lib : libs) {
                try {
                    URL url = new URL("http://www.integratedmodelling.org/downloads/extlibs/"
                            + lib);
                    URLUtils.copy(url, new File(extdir + File.separator + lib));
                } catch (Exception e) {
                    error("cannot download external binary artifacts from main server: functionalities may be limited: "
                            + e.getMessage());
                    Logging.INSTANCE
                            .error("cannot download external binary artifacts from main server: functionalities may be limited: "
                                    + e.getMessage());
                    ret = false;
                }
            }
        }

        return ret;
    }

    /**
     * Check for available upgrades.
     *
     * @return true if the currently selected distribution can be upgraded.
     */
    public boolean canUpgrade() {
        Integer rb = getBuildNumber();
        if (rb == null) {
            return false;
        }
        if (useDeveloper) {
            return localBuildNumberDev == null || localBuildNumberDev < rb;
        }
        return localBuildNumber == null || localBuildNumber < rb;
    }

    /**
     * Sets the maven directory.
     *
     * @param mavenDirectory the new maven directory
     */
    public void setMavenDirectory(File mavenDirectory) {
        this.mavenDirectory = mavenDirectory;
    }

    /**
     * Gets the engine jar.
     *
     * @return the engine jar
     */
    public File getEngineJar() {
        return useDeveloper ? engineJarDev : engineJar;
    }

    /**
     * Sets the engine jar.
     *
     * @param engineJar the new engine jar
     */
    public void setEngineJar(File engineJar) {
        this.engineJar = engineJar;
    }

    /**
     * Sets the engine jar dev.
     *
     * @param engineJarDev the new engine jar dev
     */
    public void setEngineJarDev(File engineJarDev) {
        this.engineJarDev = engineJarDev;
    }

    /**
     * Gets the mem limit mb.
     *
     * @return the mem limit mb
     */
    public int getMemLimitMb() {
        return memLimitMb;
    }

    /**
     * Sets the mem limit mb.
     *
     * @param memLimitMb the new mem limit mb
     */
    public void setMemLimitMb(int memLimitMb) {
        this.memLimitMb = memLimitMb;
    }

    /**
     * Gets the network port.
     *
     * @return the network port
     */
    public int getNetworkPort() {
        return networkPort;
    }

    /**
     * Sets the network port.
     *
     * @param networkPort the new network port
     */
    public void setNetworkPort(int networkPort) {
        this.networkPort = networkPort;
    }

    /**
     * Gets the data dir.
     *
     * @return the data dir
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Sets the data dir.
     *
     * @param dataDir the new data dir
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Gets the certificate.
     *
     * @return the certificate
     */
    public File getCertificate() {
        return certificate;
    }

    /**
     * Sets the certificate.
     *
     * @param certificate the new certificate
     */
    public void setCertificate(File certificate) {
        File certFile = new File(dataDir + File.separator + "im.cert");
        if (!certificate.equals(certFile)) {
            try {
                FileUtils.copyFile(certificate, certFile);
            } catch (IOException e) {
                throw new KlabRuntimeException(e);
            }
        }
        this.certificate = certFile;
    }

    /**
     * Checks if is launch automatically.
     *
     * @return a boolean.
     */
    public boolean isLaunchAutomatically() {
        return launchAutomatically;
    }

    /**
     * Sets the launch automatically.
     *
     * @param launchAutomatically the new launch automatically
     */
    public void setLaunchAutomatically(boolean launchAutomatically) {
        this.launchAutomatically = launchAutomatically;
    }

    /**
     * Checks if is upgrade automatically.
     *
     * @return a boolean.
     */
    public boolean isUpgradeAutomatically() {
        return upgradeAutomatically;
    }

    /**
     * Sets the upgrade automatically.
     *
     * @param upgradeAutomatically the new upgrade automatically
     */
    public void setUpgradeAutomatically(boolean upgradeAutomatically) {
        this.upgradeAutomatically = upgradeAutomatically;
    }

    /**
     * Checks if is stop on exit.
     *
     * @return a boolean.
     */
    public boolean isStopOnExit() {
        return stopOnExit;
    }

    /**
     * Sets the stop on exit.
     *
     * @param stopOnExit the new stop on exit
     */
    public void setStopOnExit(boolean stopOnExit) {
        this.stopOnExit = stopOnExit;
    }

    /**
     * Checks if is use local.
     *
     * @return a boolean.
     */
    public boolean isUseLocal() {
        return useLocal;
    }

    /**
     * Sets the use local.
     *
     * @param useLocal the new use local
     */
    public void setUseLocal(boolean useLocal) {
        this.useLocal = useLocal;
    }

    /**
     * Checks if is use developer.
     *
     * @return a boolean.
     */
    public boolean isUseDeveloper() {
        return useDeveloper;
    }

    /**
     * Sets the use developer.
     *
     * @param useDeveloper the new use developer
     */
    public void setUseDeveloper(boolean useDeveloper) {
        this.useDeveloper = useDeveloper;
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_USE_DEVELOPER_NETWORK, useDeveloper
                        ? "true"
                        : "false");
    }

    /**
     * Checks if is use debug.
     *
     * @return a boolean.
     */
    public boolean isUseDebug() {
        return useDebug;
    }

    /**
     * Checks if is klab debug.
     *
     * @return a boolean.
     */
    public boolean isKlabDebug() {
        return this.klabDebug;
    }

    /**
     * Sets the use debug.
     *
     * @param useDebug the new use debug
     */
    public void setUseDebug(boolean useDebug) {
        this.useDebug = useDebug;
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_USE_DEBUG, useDebug ? "true" : "false");
    }

    /**
     * Sets the klab debug.
     *
     * @param klabDebug the new klab debug
     */
    public void setKlabDebug(boolean klabDebug) {
        this.klabDebug = klabDebug;
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_KLAB_DEBUG, klabDebug ? "true" : "false");
    }

    private void define(Properties properties) {

        File engineJarFile = new File(Configuration.INSTANCE.getDataPath("engine") + File.separator
                + "kmodeler.jar");
        if (engineJarFile.exists()) {
            engineJar = engineJarFile;
        }
        File engineJarFileDev = new File(Configuration.INSTANCE.getDataPath("engine")
                + File.separator + "kmodeler_dev.jar");
        if (engineJarFileDev.exists()) {
            engineJarDev = engineJarFileDev;
        }

        String srcDir = properties.getProperty(Configuration.KLAB_SOURCE_DISTRIBUTION);
        if (srcDir != null) {
            File srcDirectory = new File(srcDir);
            if (srcDirectory.exists()) {
                mavenDirectory = srcDirectory;
            }
        }

        if (properties.containsKey(BUILD_DEV_PROPERTY)) {
            localBuildNumberDev = Integer
                    .parseInt(properties.getProperty(BUILD_DEV_PROPERTY));
        }
        if (properties.containsKey(BUILD_PROPERTY)) {
            localBuildNumber = Integer.parseInt(properties.getProperty(BUILD_PROPERTY));
        }

        if (properties.containsKey(MAX_MEMORY_PROPERTY)) {
            memLimitMb = Integer.parseInt(properties.getProperty(MAX_MEMORY_PROPERTY));
        }

        /*
         * reset defaults if previously stored
         */
        if (properties.getProperty(Configuration.KLAB_ENGINE_DATADIR) != null) {
            dataDir = new File(properties
                    .getProperty(Configuration.KLAB_ENGINE_DATADIR));
        }
        if (properties.getProperty(Configuration.KLAB_ENGINE_CERTIFICATE) != null) {
            certificate = new File(properties
                    .getProperty(Configuration.KLAB_ENGINE_CERTIFICATE));
        }

        useReasoning = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_REASONING, "false"));
        launchAutomatically = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_LAUNCH_AUTOMATICALLY, "true"));
        upgradeAutomatically = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_UPGRADE_AUTOMATICALLY, "true"));
        stopOnExit = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_SHUTDOWN_ON_EXIT, "false"));
        useLocal = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_USE_LOCAL_INSTALLATION, "false"));
        useDeveloper = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_USE_DEVELOPER_NETWORK, "false"));
        useDebug = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_USE_DEBUG, "false"));
        klabDebug = Boolean.parseBoolean(properties
                .getProperty(Configuration.KLAB_ENGINE_KLAB_DEBUG, "false"));
        debugPort = Integer.parseInt(properties
                .getProperty(Configuration.KLAB_ENGINE_DEBUG_PORT, "8893"));
    }

    /**
     * True if engine has already been launched.
     *
     * @return true if engine is online
     */
    public boolean isOnline() {
        return NetUtilities.urlResponds("http://127.0.0.1:" + networkPort + context
                + "/capabilities");
    }

    /**
     * Gets the active source.
     *
     * @return the active source
     */
    public Source getActiveSource() {

        Source ret = new Source();
        ret.debugPort = debugPort;
        ret.useDebug = useDebug;
        ret.isDevelop = useDeveloper;
        ret.isLocal = useLocal;
        ret.jarFile = getEngineJar();
        ret.mavenDirectory = mavenDirectory;

        return ret;
    }

    /**
     * Shutdown.
     *
     * @param maxSeconds the max seconds
     * @return true if engine is offline, whether as a result of the shutdown or for its own
     *         reasons.
     */
    public boolean shutdown(int maxSeconds) {

        if (isOnline()) {

            if (listener != null) {
                listener.shutdownStarted();
            }

            long timeout = 0;
            try (InputStream is = new URL("http://127.0.0.1:" + networkPort + context
                    + API.ADMIN.SHUTDOWN).openStream()) {
                //
            } catch (Exception e) {
                error("exception calling shutdown: " + e.getMessage());
                return false;
            }
            do {
                try {
                    Thread.sleep(500);
                    timeout += 500;
                } catch (Exception e) {
                }
            } while (isOnline() && timeout < (maxSeconds * 1000));

            if (timeout >= (maxSeconds * 1000) && isOnline()) {
                error("Timeout reached while stopping the engine. Please use OS facilities to stop it manually.");
            }

        }

        if (!isOnline()) {

            if (listener != null) {
                listener.shutdownFinished();
            }

            return true;
        }

        return false;
    }

    /**
     * Launch.
     *
     * @param waitForEngine the wait for engine
     * @return a boolean.
     */
    public boolean launch(boolean waitForEngine) {
        return launch(waitForEngine, null);
    }

    /**
     * Launch.
     *
     * @param waitForEngine the wait for engine
     * @param listener the listener
     * @return a boolean.
     */
    public boolean launch(boolean waitForEngine, LaunchListener listener) {

        this.listener = listener;

        if (!isOnline()) {

            downloadExtBinaryArtifacts();

            return (useLocal && mavenDirectory != null) ? launchMaven(waitForEngine)
                    : launchJar(waitForEngine);
        }
        return true;
    }

    /**
     * Launch maven.
     *
     * @param waitForRunning the wait for running
     * @return true if successfully starting or started
     */
    public boolean launchMaven(boolean waitForRunning) {

        if (mavenDirectory != null) {

            if (System.getProperty("maven.home") == null) {
                String mavenHome = System.getenv("MAVEN_HOME");
                if (mavenHome != null) {
                    System.setProperty("maven.home", mavenHome);
                }
                /*
                 * if not, give it a try anyway - on some architectures it succeeds
                 * nevertheless. In case, error message will be seem at launch.
                 */
            }

            String ext = "ext" + File.separator + "win64";
            if (Configuration.INSTANCE.getOS().equals(OS.UNIX)) {
                ext = "ext/linux";
            } else if (Configuration.INSTANCE.getOS().equals(OS.MACOS)) {
                ext = "ext/osx";
            }

            File cPath = Configuration.INSTANCE.getDataPath("engine");

            final InvocationRequest compile = new DefaultInvocationRequest();
            final InvocationRequest request = new DefaultInvocationRequest();

            String mavenOptions = "";

            for (String s : JavaUtils.getOptions(512, memLimitMb, true, PERMSIZE)) {
                mavenOptions += (mavenOptions.isEmpty() ? "" : " ") + s;
            }

            if (useDebug) {
                mavenOptions += (mavenOptions.isEmpty() ? "" : " ")
                        + "-Xdebug -Xbootclasspath/p:lib/jsr166.jar -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address="
                        + debugPort;
            }

            mavenOptions += (mavenOptions.isEmpty() ? "" : " ") + "-Djava.library.path="
                    + cPath + File.separator + ext;

            request.setMavenOpts(mavenOptions);

            request.setBaseDirectory(new File(mavenDirectory + File.separator
                    + "klab-modeler"));
            request.setRecursive(true);
            request.setPomFile(new File(mavenDirectory + File.separator + "klab-modeler"
                    + File.separator + "pom.xml"));
            request.setGoals(Arrays.asList("spring-boot:run"));

            compile.setBaseDirectory(mavenDirectory);
            compile.setRecursive(true);
            compile.setPomFile(new File(mavenDirectory + File.separator + "pom.xml"));
            compile.setGoals(Arrays.asList("install"));

            compile.setOffline(offlineMode);

            Properties properties = new Properties();
            properties
                    .setProperty(Configuration.KLAB_DATA_DIRECTORY, dataDir.toString());
            properties.setProperty("server-port", "" + networkPort);
            properties.setProperty(Configuration.KLAB_REASONING, useReasoning ? "true"
                    : "false");
            properties.setProperty(Configuration.KLAB_ENGINE_USE_DEVELOPER_NETWORK, useDeveloper ? "true"
                    : "false");
            properties.setProperty(Configuration.KLAB_OFFLINE, Configuration.INSTANCE.isOffline() ? "true"
                    : "false");
            properties.setProperty(LOGGING_FILE, logFile.toString());
            if (Configuration.INSTANCE.isOffline()) {
              // FIXME restore?
              //                // we'll need these
//                properties.setProperty(Configuration.KLAB_CLIENT_PROJECTS, StringUtils
//                        .join( ((AbstractWorkspace)Workspaces.INSTANCE.getLocal()).getProjectLocations(), ","));
            }
            if (Configuration.INSTANCE.getProperties()
                    .getProperty(Configuration.KLAB_LOCAL_COMPONENTS) != null) {
                properties.setProperty(Configuration.KLAB_LOCAL_COMPONENTS, Configuration.INSTANCE
                        .getProperties()
                        .getProperty(Configuration.KLAB_LOCAL_COMPONENTS));
            }
            request.setProperties(properties);

            status = Status.STARTING;

            /*
             * spawn Maven task in thread and wait for engine to be seen online.
             */
            new Thread() {

                @Override
                public void run() {

                    final Invoker invoker = new DefaultInvoker();

                    invoker.setLogger(new SystemOutLogger());

                    InvocationResult result;
                    try {
                        result = invoker.execute(compile);
                        if (listener != null) {
                            if (result.getExitCode() == 0) {
                                listener.compilationSuccessful();
                            } else {
                                listener.compilationFailed();
                            }
                        }
                        result = invoker.execute(request);
                    } catch (MavenInvocationException e) {
                        synchronized (status) {
                            status = Status.ERROR;
                        }
                        if (listener != null) {
                            listener.launchFailed(e);
                        }
                        return;
                    } catch (IllegalStateException e) {
                        synchronized (status) {
                            error(e.getMessage());
                            status = Status.ERROR;
                            if (listener != null) {
                                listener.launchFailed(e);
                            }
                        }
                        return;
                    }

                    if (result.getExitCode() != 0) {
                        if (result.getExecutionException() != null) {
                            synchronized (status) {
                                status = Status.ERROR;
                            }
                            if (listener != null) {
                                listener.launchFailed(result.getExecutionException());
                            }
                            return;
                        } else {
                            synchronized (status) {
                                status = Status.STOPPED;
                            }
                            if (listener != null) {
                                listener.launchFailed(null);
                            }
                            return;
                        }
                    } else {
                        if (listener != null) {
                            listener.launchSuccessful();
                        }
                    }
                }
            }.start();
        }

        /*
         * Note: multiplying timeout by 10 if we're using Maven.
         */

        if (waitForRunning) {
            long timeout = 0;
            do {
                try {
                    Thread.sleep(500);
                    timeout += 500;
                } catch (Exception e) {
                }
            } while (!isOnline() && timeout < (TIMEOUT * 10));

            if (timeout >= (TIMEOUT * 10) && !isOnline()) {
                error("Timeout reached while starting the engine. Please check configuration and computer requirements.");
            } else {
                status = Status.RUNNING;
            }
        }

        return status != Status.STOPPED;
    }

    enum Status {
        STOPPED,
        STARTING,
        STOPPING,
        RUNNING,
        ERROR;
    };

    Status                     status              = Status.STOPPED;

    /*
     * default timeout 1min until we decide that the server didn't start up properly. FV
     * made it 2mins to accommodate BC3 worst-in-their-class laptops for modelers, or long
     * Maven builds.
     */
    private static long        TIMEOUT             = 120000;

    /** The Constant BUILD_DEV_PROPERTY. */
    public static final String BUILD_DEV_PROPERTY  = "klab.build.dev";
    
    /** The Constant BUILD_PROPERTY. */
    public static final String BUILD_PROPERTY      = "klab.build";
    
    /** The Constant MAX_MEMORY_PROPERTY. */
    public static final String MAX_MEMORY_PROPERTY = "klab.max.memory";

    /**
     * Launch (if necessary) the selected Jar distribution. If waitForRunning is true, return only
     * when engine is responsive or after error. Otherwise return during startup. Check isOnline()
     * to see what happened.
     *
     * @param waitForRunning the wait for running
     * @return true if operation went as expected
     */
    public boolean launchJar(boolean waitForRunning) {

        String ext = "ext" + File.separator + "win64";
        if (Configuration.INSTANCE.getOS().equals(OS.UNIX)) {
            ext = "ext/linux";
        } else if (Configuration.INSTANCE.getOS().equals(OS.MACOS)) {
            ext = "ext/osx";
        }

        File cPath = Configuration.INSTANCE.getDataPath("engine");

        CommandLine cmdLine = new CommandLine(JavaUtils.getJavaExecutable());

        for (String s : JavaUtils.getOptions(512, memLimitMb, true, PERMSIZE)) {
            cmdLine.addArgument(s);
        }

        if (useDebug) {
            cmdLine.addArgument("-Xdebug");
            cmdLine.addArgument("-Xbootclasspath/p:lib/jsr166.jar");
            cmdLine.addArgument("-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address="
                    + debugPort);
        }

        cmdLine.addArgument("-Djava.library.path=" + cPath + File.separator + ext);
        cmdLine.addArgument("-D" + Configuration.KLAB_DATA_DIRECTORY + "=" + dataDir);
        cmdLine.addArgument("-Dserver-port=" + networkPort);
        cmdLine.addArgument("-Dlogging.file=" + logFile);
        cmdLine.addArgument("-D" + Configuration.KLAB_REASONING + "=" + (useReasoning ? "true" : "false"));
        cmdLine.addArgument("-D" + Configuration.KLAB_ENGINE_USE_DEVELOPER_NETWORK + "="
                + (useDeveloper ? "true" : "false"));
        cmdLine.addArgument("-D" + Configuration.KLAB_OFFLINE + "="
                + (Configuration.INSTANCE.isOffline() ? "true" : "false"));
        if (Configuration.INSTANCE.isOffline()) {
            // we're gonna need these
          // FIXME restore?
//            cmdLine.addArgument("-D" + Configuration.KLAB_CLIENT_PROJECTS + "="
//                    + StringUtils.join( ((AbstractWorkspace)Workspaces.INSTANCE.getLocal()).getProjectLocations(), ","));
        }
        if (Configuration.INSTANCE.getProperties()
                .getProperty(Configuration.KLAB_LOCAL_COMPONENTS) != null) {
            cmdLine.addArgument("-D" + Configuration.KLAB_LOCAL_COMPONENTS + "="
                    + Configuration.INSTANCE.getProperties()
                            .getProperty(Configuration.KLAB_LOCAL_COMPONENTS));
        }

        cmdLine.addArgument("-jar");
        cmdLine.addArgument(useDeveloper ? engineJarDev.toString()
                : engineJar.toString());

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(cPath);

        Map<String, String> env = new HashMap<>();
        env.putAll(System.getenv());

        status = Status.STARTING;

        try {
            Logging.INSTANCE.info("starting engine from " + cPath);
            executor.execute(cmdLine, env, new ExecuteResultHandler() {

                @Override
                public void onProcessFailed(ExecuteException arg0) {
                    synchronized (status) {
                        status = Status.STOPPED;
                    }
                    if (listener != null) {
                        listener.launchFailed(arg0);
                    }
                }

                @Override
                public void onProcessComplete(int arg0) {
                    synchronized (status) {
                        status = Status.STOPPED;
                    }
                }
            });
        } catch (Exception e) {
            status = Status.STOPPED;
        }

        if (waitForRunning && status == Status.STARTING) {
            long timeout = 0;
            do {
                try {
                    Thread.sleep(500);
                    timeout += 500;
                } catch (Exception e) {
                }
            } while (!isOnline() && timeout < TIMEOUT);

            if (timeout >= TIMEOUT && !isOnline()) {
                error("Timeout reached while starting the engine. Please check configuration and computer requirements.");
                if (listener != null) {
                    listener.launchFailed(null);
                }
            } else {
                status = Status.RUNNING;
                if (listener != null) {
                    listener.launchSuccessful();
                }
            }
        }

        return status != Status.STOPPED;
    }

    /**
     * Persist properties.
     */
    public void persistProperties() {

        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_LAUNCH_AUTOMATICALLY, launchAutomatically
                        ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_UPGRADE_AUTOMATICALLY, upgradeAutomatically
                        ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_SHUTDOWN_ON_EXIT, stopOnExit
                        ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_USE_LOCAL_INSTALLATION, useLocal
                        ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_USE_DEVELOPER_NETWORK, useDeveloper
                        ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_KLAB_DEBUG, klabDebug ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_USE_DEBUG, useDebug ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_DEBUG_PORT, "" + debugPort);
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_DATADIR, dataDir.toString());
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_ENGINE_CERTIFICATE, certificate
                        .toString());
        Configuration.INSTANCE.getProperties()
                .setProperty(Configuration.KLAB_REASONING, useReasoning ? "true"
                        : "false");
        Configuration.INSTANCE.getProperties().setProperty(MAX_MEMORY_PROPERTY, memLimitMb + "");
        if (mavenDirectory != null) {
            Configuration.INSTANCE.getProperties()
                    .setProperty(Configuration.KLAB_SOURCE_DISTRIBUTION, mavenDirectory
                            .toString());
        }
        Configuration.INSTANCE.save();
    }

    /**
     * Must be called after stopping the engine.
     */
    public void runCleaningCycle() {

        if (isOnline()) {
            error("cannot run a cleaning cycle with a running engine");
            return;
        }

        File kboxDir = new File(Configuration.INSTANCE.getDataPath("engine") + File.separator
                + "kbox");

        if (runModelClean && kboxDir.exists()) {
            for (File file : kboxDir.listFiles()) {
                if (file.isFile() && MiscUtilities.getFileName(file.toString())
                        .startsWith("models2_")) {
                    FileUtils.deleteQuietly(file);
                }
            }
        }
        if (runDataClean) {
            File cacheDir = new File(Configuration.INSTANCE.getDataPath("engine") + File.separator
                    + ".scratch");
            if (cacheDir.exists() && cacheDir.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(cacheDir);
                } catch (IOException e) {
                    error("could not delete directory " + cacheDir);
                }
            }
        }
        if (runObsClean && kboxDir.exists()) {
            for (File file : kboxDir.listFiles()) {
                if (file.isFile() && MiscUtilities.getFileName(file.toString())
                        .startsWith("observations_")) {
                    FileUtils.deleteQuietly(file);
                }
            }
        }

    }

    /*
     * TODO
     */
    String[]        additionalRelativePaths = new String[] {};
    private boolean runGC;
    private boolean runObsClean;
    private boolean runDataClean;
    private boolean runModelClean;
    private boolean runClean;

    // non-persistent, just to capture user choice from GUI.
    private boolean rememberSettings        = true;
    private boolean stopEngine              = false;
    private boolean useReasoning            = true;

    /**
     * Gets the classpath.
     *
     * @return the classpath
     */
    public String getClasspath() {

        String CLASSPATH_SEPARATOR = getClasspathSeparator();
        File basePath = useLocal ? mavenDirectory
                : new File(dataDir + File.separator + "engine");
        String ret = "";

        boolean ok = true;
        for (String s : additionalRelativePaths) {
            File f = new File(basePath + File.separator + s);
            if (!(f.exists() && f.isDirectory())) {
                ok = false;
            }
        }

        if (ok) {
            /*
             * TODO more tests
             */

            /*
             * build classpath with development classes first
             */
            ret = "";
            for (String s : additionalRelativePaths) {
                ret += (ret.isEmpty() ? "" : CLASSPATH_SEPARATOR) + basePath
                        + File.separator + s;
            }

            // ret += CLASSPATH_SEPARATOR + basePath + File.separator +
            // LIB_FILES_PATH +
            // "/*";
        }

        return ret;
    }

    /*
     * can't believe this is necessary.
     */
    /**
     * <p>getClasspathSeparator.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected static String getClasspathSeparator() {
        switch (Configuration.INSTANCE.getOS()) {
        case MACOS:
        case UNIX:
            return ":";
        case WIN:
            return ";";
        }
        return ":";
    }

    /**
     * Return the build number from the network distribution, or null if the network cannot be accessed.
     *
     * @return the build number for the set (remote) engine. Ignores local distribution setting.
     */
    public Integer getBuildNumber() {

        if (remoteBuildNumberDev == null) {
            try {
                File bn = File.createTempFile("build", "txt");
                URLUtils.copy(new URL(BUILD_DEV_FILE_URL), bn);
                remoteBuildNumberDev = Integer
                        .parseInt(FileUtils.readFileToString(bn).trim());
            } catch (Exception e) {
                // leave it null
            }
        }
        if (remoteBuildNumber == null) {
            try {
                File bn = File.createTempFile("build", "txt");
                URLUtils.copy(new URL(BUILD_FILE_URL), bn);
                remoteBuildNumber = Integer
                        .parseInt(FileUtils.readFileToString(bn).trim());
            } catch (Exception e) {
                // leave it null
            }
        }
        return useDeveloper ? remoteBuildNumberDev : remoteBuildNumber;
    }

    /**
     * Return whether the settings correspond to defined properties so that an engine can be launched.
     *
     * @return true if we can be launched with the current settings.
     */
    public boolean canLaunch() {
        if (useLocal) {
            return mavenDirectory != null && mavenDirectory.exists()
                    && mavenDirectory.isDirectory()
                    && new File(mavenDirectory + File.separator + "pom.xml").exists();
        }
        if (useDeveloper) {
            return engineJarDev != null && engineJarDev.isFile()
                    && engineJarDev.canRead();
        }
        return engineJar != null && engineJar.isFile() && engineJar.canRead();
    }

    /**
     * Call after a download attempt to rescan the files and reset the build numbers.
     */
    public void rescanDistributions() {

        File engineJarFile = new File(Configuration.INSTANCE.getDataPath("engine") + File.separator
                + "kmodeler.jar");
        if (engineJarFile.exists()) {
            engineJar = engineJarFile;
            if (Configuration.INSTANCE.getProperties().containsKey(BUILD_PROPERTY)) {
                localBuildNumber = Integer.parseInt(Configuration.INSTANCE.getProperties()
                        .getProperty(BUILD_PROPERTY));
            }
        }
        File engineJarFileDev = new File(Configuration.INSTANCE.getDataPath("engine")
                + File.separator + "kmodeler_dev.jar");
        if (engineJarFileDev.exists()) {
            engineJarDev = engineJarFileDev;
            if (Configuration.INSTANCE.getProperties().containsKey(BUILD_DEV_PROPERTY)) {
                localBuildNumberDev = Integer.parseInt(Configuration.INSTANCE.getProperties()
                        .getProperty(BUILD_DEV_PROPERTY));
            }
        }

        remoteBuildNumber = null;
        remoteBuildNumberDev = null;

        getBuildNumber();
    }

    /**
     * Sets the run GC.
     *
     * @param selection the new run GC
     */
    public void setRunGC(boolean selection) {
        this.runGC = selection;
    }

    /**
     * Sets the clean observation cache.
     *
     * @param selection the new clean observation cache
     */
    public void setCleanObservationCache(boolean selection) {
        this.runObsClean = selection;
    }

    /**
     * Sets the clean data cache.
     *
     * @param selection the new clean data cache
     */
    public void setCleanDataCache(boolean selection) {
        this.runDataClean = selection;
    }

    /**
     * Sets the clean model cache.
     *
     * @param selection the new clean model cache
     */
    public void setCleanModelCache(boolean selection) {
        this.runModelClean = selection;
    }

    /**
     * Sets the clean cycle.
     *
     * @param selection the new clean cycle
     */
    public void setCleanCycle(boolean selection) {
        this.runClean = selection;
    }

    /**
     * Checks if is restart requested.
     *
     * @return a boolean.
     */
    public boolean isRestartRequested() {
        return runObsClean || runDataClean || runModelClean || runClean;
    }

    /**
     * Sets the remember settings.
     *
     * @param selection the new remember settings
     */
    public void setRememberSettings(boolean selection) {
        this.rememberSettings = selection;
    }

    /**
     * Checks if is remember settings.
     *
     * @return a boolean.
     */
    public boolean isRememberSettings() {
        return rememberSettings;
    }

    /**
     * Sets the stop engine now.
     *
     * @param selection the new stop engine now
     */
    public void setStopEngineNow(boolean selection) {
        this.stopEngine = selection;
    }

    /**
     * Checks if is stop engine now.
     *
     * @return a boolean.
     */
    public boolean isStopEngineNow() {
        return this.stopEngine;
    }

    /**
     * Sets the debug port.
     *
     * @param port the new debug port
     */
    public void setDebugPort(int port) {
        debugPort = port;
    }

    /**
     * Gets the debug port.
     *
     * @return the debug port
     */
    public int getDebugPort() {
        return debugPort;
    }

    /**
     * Sets the use reasoning.
     *
     * @param selection the new use reasoning
     */
    public void setUseReasoning(boolean selection) {
        useReasoning = selection;
    }

    /**
     * Checks if is use reasoning.
     *
     * @return a boolean.
     */
    public boolean isUseReasoning() {
        return useReasoning;
    }

    /**
     * Checks if is develop.
     *
     * @return a boolean.
     */
    public boolean isDevelop() {
        return useDeveloper;
    }

}

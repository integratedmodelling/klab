/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;

import org.integratedmodelling.kim.utils.OS;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Interface IConfigurationService.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IConfigurationService {

    /** The Constant KLAB_CLIENT_PROJECTS. */
    public static final String KLAB_CLIENT_PROJECTS = "klab.client.workspace";

    /** The Constant KLAB_OFFLINE. */
    public static final String KLAB_OFFLINE = "klab.offline";

    /** The Constant KLAB_USE_CONTEXT_QUALITIES. */
    public static final String KLAB_USE_CONTEXT_QUALITIES = "klab.use.context.qualities";

    /** The Constant KLAB_EXPORT_PATH. */
    public static final String KLAB_EXPORT_PATH = "klab.export.path";

    /** The Constant KLAB_REASONING. */
    public static final String KLAB_REASONING = "klab.reasoning";

    /** The Constant KLAB_DEBUG. */
    public static final String KLAB_DEBUG = "klab.debug";

    /** The Constant CERTFILE_PROPERTY. */
    public static final String CERTFILE_PROPERTY = "klab.certificate";

    /** The Constant KLAB_SOURCE_DISTRIBUTION. */
    public static final String KLAB_SOURCE_DISTRIBUTION = "thinklab.source.distribution";

    /** The Constant KLAB_CONNECTION_TIMEOUT. */
    public static final String KLAB_CONNECTION_TIMEOUT = "klab.connection.timeout";

    /*
     * these properties can be set to define what states to store during contextualization when the
     * defaults are inadequate. They're mostly unsupported at this time.
     */
    /** The Constant KLAB_STORE_RAW_DATA. */
    public static final String KLAB_STORE_RAW_DATA = "klab.store.raw";

    /** The Constant KLAB_STORE_INTERMEDIATE_DATA. */
    public static final String KLAB_STORE_INTERMEDIATE_DATA = "klab.store.intermediate";

    /** The Constant KLAB_STORE_CONDITIONAL_DATA. */
    public static final String KLAB_STORE_CONDITIONAL_DATA = "klab.store.conditional";

    /** The Constant KLAB_STORE_MEDIATED_DATA. */
    public static final String KLAB_STORE_MEDIATED_DATA = "klab.store.mediated";

    /**
     * Minutes after which a session times out. Default 60.
     */
    public static final String KLAB_SESSION_TIMEOUT_MINUTES = "klab.session.timeout";

    /**
     * Absolute path of work directory. Overrides the default which is
     * ${user.home}/THINKLAB_WORK_DIRECTORY
     */
    public static final String KLAB_DATA_DIRECTORY = "klab.data.directory";

    /**
     * Name of work directory relative to ${user.home}. Ignored if THINKLAB_DATA_DIRECTORY_PROPERTY is
     * specified.
     */
    public static final String KLAB_WORK_DIRECTORY = "klab.work.directory";

    /**
     * Points to a comma-separated list of directories where components are loaded from their Maven
     * development tree.
     */
    public static final String KLAB_LOCAL_COMPONENTS = "klab.local.components";

    /** The Constant KLAB_ENGINE_CERTIFICATE. */
    public static final String KLAB_ENGINE_CERTIFICATE = "klab.engine.certificate";

    /** The Constant KLAB_ENGINE_DATADIR. */
    public static final String KLAB_ENGINE_DATADIR = "klab.engine.datadir";

    /** The Constant KLAB_ENGINE_DEBUG_PORT. */
    public static final String KLAB_ENGINE_DEBUG_PORT = "klab.engine.debugPort";

    /** The Constant KLAB_ENGINE_USE_DEBUG. */
    public static final String KLAB_ENGINE_USE_DEBUG = "klab.engine.useDebug";

    /** The Constant KLAB_ENGINE_KLAB_DEBUG. */
    public static final String KLAB_ENGINE_KLAB_DEBUG = "klab.engine.klabDebug";

    /** The Constant KLAB_ENGINE_USE_DEVELOPER_NETWORK. */
    public static final String KLAB_ENGINE_USE_DEVELOPER_NETWORK = "klab.engine.useDeveloperNetwork";

    /** The Constant KLAB_ENGINE_USE_LOCAL_INSTALLATION. */
    public static final String KLAB_ENGINE_USE_LOCAL_INSTALLATION = "klab.engine.useLocalInstallation";

    /** The Constant KLAB_ENGINE_SHUTDOWN_ON_EXIT. */
    public static final String KLAB_ENGINE_SHUTDOWN_ON_EXIT = "klab.engine.shutdownOnExit";

    /** The Constant KLAB_ENGINE_UPGRADE_AUTOMATICALLY. */
    public static final String KLAB_ENGINE_UPGRADE_AUTOMATICALLY = "klab.engine.upgradeAutomatically";

    /** The Constant KLAB_ENGINE_LAUNCH_AUTOMATICALLY. */
    public static final String KLAB_ENGINE_LAUNCH_AUTOMATICALLY = "klab.engine.launchAutomatically";

    /**
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the ontology
     * holding the main concept in the declaration (false).
     */
    public static final String KLAB_USE_COMMON_ONTOLOGY = "klab.engine.useCommonOntology";

    /**
     * Class to choose to create storage - used only to disambiguate if > 1 storage providers are
     * available.
     */
    public static final String STORAGE_PROVIDER_COMPONENT = "klab.storage.provider.class";

    /**
     * Class to choose to create dataflow runtimes - used only to disambiguate if > 1 runtime
     * providers are available.
     */
    public static final String RUNTIME_PROVIDER_COMPONENT = "klab.runtime.provider.class";

    /**
     * The main properties, read and written by default to ${user.dir}/.klab/klab.properties.
     *
     * @return the properties. Created if absent, never null.
     */
    Properties getProperties();

    /**
     * The operating system where we are running. Only recognizes the three main ones.
     *
     * @return the OS identifier.
     */
    OS getOS();

    /**
     * Create (if necessary) and return a subdirectory within the k.LAB workspace. Slash-separated
     * subspace strings can be used to specify nested subdirectories.
     *
     * @param subspace a {@link java.lang.String} object.
     * @return the file directory created.
     */
    File getDataPath(String subspace);

    /**
     * <p>
     * isOffline.
     * </p>
     *
     * @return a boolean.
     */
    boolean isOffline();

    /**
     * Return the k.LAB workspace, by default ${user.dir}/.klab.
     *
     * @return the k.LAB workspace directory.
     */
    File getDataPath();

    /**
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the ontology
     * holding the main concept in the declaration (false). Default is true.
     *
     * @return the value of this setting (default true, should be false only for special purposes,
     *         such as using k.LAB only as an OWL processor)
     */
    boolean useCommonOntology();

    /**
     * True if debugging mode has been enabled.
     *
     * @return debugging mode
     */
    boolean isDebuggingEnabled();

    /**
     * <p>
     * isRemoteResolutionEnabled.
     * </p>
     *
     * @return a boolean.
     */
    boolean isRemoteResolutionEnabled();

    /**
     * <p>
     * getDataflowThreadCount.
     * </p>
     *
     * @return a int.
     */
    int getDataflowThreadCount();

    /**
     * <p>
     * getTaskThreadCount.
     * </p>
     *
     * @return a int.
     */
    int getTaskThreadCount();

    /**
     * <p>
     * getScriptThreadCount.
     * </p>
     *
     * @return a int.
     */
    int getScriptThreadCount();

    /**
     * Check whether anonymous usage is allowed, resulting in the generation of an anonymous
     * certificate (which won't connect to the network) if the certfile is not in place. Default is
     * true, which should be connected to something different than the property file.
     * 
     * @return true if anonymous usage is allowed
     */
    boolean allowAnonymousUsage();

    /**
     * The logging level (linked to property klab.logging.level, default ERROR) controls which notifications sent to
     * {@link IMonitor monitors} are also logged.
     * 
     * @return the level of logging for monitor notifications.
     */
    Level getLoggingLevel();

    /**
     * The notification level (linked to property klab.logging.level, default INFO) controls which notifications sent to
     * {@link IMonitor monitors} are sent to subscribers.
     * 
     * @return the level of logging for monitor notifications.
     */
    Level getNotificationLevel();

}

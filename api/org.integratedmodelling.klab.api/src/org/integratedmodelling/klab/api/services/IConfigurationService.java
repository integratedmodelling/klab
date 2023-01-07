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

import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.OS;

/**
 * The Interface IConfigurationService.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IConfigurationService {

	/**
	 * The package containing all REST resource beans.
	 */
	static final public String REST_RESOURCES_PACKAGE_ID = "org.integratedmodelling.klab.rest";

	public static final int DEFAULT_ENGINE_PORT = 8283;
	public static final int DEFAULT_HUB_PORT = 8284;
	public static final int DEFAULT_NODE_PORT = 8287;
	public static final int DEFAULT_LEVER_PORT = 8761;
	public static final int DEFAULT_SEMANTIC_SERVER_PORT = 8301;
	public static final String DEFAULT_PRODUCTS_BRANCH = "master";

	public static final String KLAB_LOG_FILE = "klab.log.file";
	public static final String KLAB_OFFLINE = "klab.offline";
	public static final String KLAB_EXPORT_PATH = "klab.export.path";
	public static final String KLAB_DEBUG_RESOLUTION_RANKS = "klab.debugging.resolution.ranks";
	public static final String KLAB_DEBUG_RESOLUTION_GRAPH = "klab.debugging.resolution.graph";
	public static final String KLAB_DEBUG_RESOLUTION_DFLOW = "klab.debugging.resolution.dflow";
	public static final String KLAB_USE_IN_MEMORY_DATABASE = "klab.database.inmemory";
	public static final String KLAB_PARALLELIZE_CONTEXTUALIZATION = "klab.computation.parallel";
	public static final String KLAB_USE_IN_MEMORY_STORAGE = "klab.storage.inmemory";
	public static final String CERTFILE_PROPERTY = "klab.certificate";
	public static final String KLAB_CONNECTION_TIMEOUT = "klab.connection.timeout";
	public static final String KLAB_PROJECT_BLACKLIST_PROPERTY = "klab.project.blacklist";
	public static final String KLAB_STATS_SERVER_URL_PROPERTY = "stats.server.url";
	public static final String KLAB_LENIENT_GRID_INTERSECTION = "klab.grid.intersection.lenient";
	public static final String LOCAL_STATS_ACTIVE_PROPERTY = "org.integratedmodelling.stats.active";
	public static final String LOCAL_STATS_PRIVATE_PROPERTY = "org.integratedmodelling.stats.private";

	/**
	 * If false, coverage of merged spatial layers is interpreted strictly, i.e. if
	 * a covered portion with higher priority has nodata and a filler with lower
	 * priority has data, the nodata from the covered portions substitute the
	 * filler's data.
	 */
	public static final String KLAB_FILL_COVERED_NODATA = "klab.space.fillcoverednodata";

	/**
	 * Minutes after which a session times out. Default 60.
	 */
	public static final String KLAB_SESSION_TIMEOUT_MINUTES = "klab.session.timeout";

	/**
	 * Absolute path of work directory. Overrides the default which is
	 * ${user.home}/THINKLAB_WORK_DIRECTORY
	 */
	public static final String KLAB_DATA_DIRECTORY = "klab.data.directory";

	// configurable temp dir for (potentially very large) storage during simulation.
	public static final String KLAB_TEMPORARY_DATA_DIRECTORY = "klab.temporary.data.directory";

	public static final String KLAB_DISABLE_CONSOLE_ECHO = "klab.disable.console.echo";

	public static final String KLAB_ACCEPTED_WAIT_TIME_SECONDS = "klab.accepted.wait.time";

	/**
	 * Name of work directory relative to ${user.home}. Ignored if
	 * THINKLAB_DATA_DIRECTORY_PROPERTY is specified.
	 */
	public static final String KLAB_WORK_DIRECTORY = "klab.work.directory";

	public static final String KLAB_ENGINE_CERTIFICATE = "klab.engine.certificate";

	/** The Constant KLAB_ENGINE_USE_DEVELOPER_NETWORK. */
	public static final String KLAB_ENGINE_USE_DEVELOPER_NETWORK = "klab.engine.useDeveloperNetwork";

	/**
	 * Class to choose to create storage - used only to disambiguate if > 1 storage
	 * providers are available.
	 */
	public static final String STORAGE_PROVIDER_COMPONENT = "klab.storage.provider.class";

	/**
	 * Class to choose to create dataflow runtimes - used only to disambiguate if >
	 * 1 runtime providers are available.
	 */
	public static final String RUNTIME_PROVIDER_COMPONENT = "klab.runtime.provider.class";

	/**
	 * If defined, the engine will print times for each actuator run
	 */
	public static final String KLAB_SHOWTIMES_PROPERTY = "klab.showtimes";

	/**
	 * If defined and set to <code>true</code>, then the region context will be
	 * extended assure square grid cells.
	 */
	public static final String KLAB_GRID_CONSTRAINT = "klab.grid.forceSquarecells";

	/**
	 * If defined and set to <code>true</code>, then intermediate data processed by
	 * the models are to be dumped to disk.
	 */
	public static final String KLAB_MODEL_DUMP_INTERMEDIATE = "klab.model.dumpIntermediateData";

	/**
	 * URL of local node (must match certfile) when running in develop config. Pass
	 * to hub as -D to override the default (which won't work on Win), normally with
	 * a 127.0.0.1-based URL.
	 */
	public static final String KLAB_DEV_NODE_URL = "klab.dev.node.url";

	/**
	 * Branch to use for groups observables
	 */
	public static final String KLAB_PRODUCTS_BRANCH = "klab.products.branch";

	/**
	 * The main properties, read and written by default to
	 * ${user.dir}/.klab/klab.properties.
	 *
	 * @return the properties. Created if absent, never null.
	 */
	Properties getProperties();

	/**
	 * The operating system where we are running. Only recognizes the three main
	 * ones.
	 *
	 * @return the OS identifier.
	 */
	OS getOS();

	/**
	 * Create (if necessary) and return a subdirectory within the k.LAB workspace.
	 * Slash-separated subspace strings can be used to specify nested
	 * subdirectories.
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
	 * True if debugging mode has been enabled.
	 *
	 * @return debugging mode
	 */
	boolean isDebugResolutionRanks();

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
	 * Check whether anonymous usage is allowed, resulting in the generation of an
	 * anonymous certificate (which won't connect to the network) if the certfile is
	 * not in place. Default is true, which should be connected to something
	 * different than the property file.
	 * 
	 * @return true if anonymous usage is allowed
	 */
	boolean allowAnonymousUsage();

	/**
	 * The logging level (linked to property klab.logging.level, default ERROR)
	 * controls which notifications sent to {@link IMonitor monitors} are also
	 * logged.
	 * 
	 * @return the level of logging for monitor notifications.
	 */
	Level getLoggingLevel();

	/**
	 * The notification level (linked to property klab.logging.level, default INFO)
	 * controls which notifications sent to {@link IMonitor monitors} are sent to
	 * subscribers.
	 * 
	 * @return the level of logging for monitor notifications.
	 */
	Level getNotificationLevel();

	/**
	 * Return the proportion of error (0-1) allowed when subsets of grids are
	 * created during scale mediation. If the error in a subsetting operation is
	 * higher than what is returned, a much more expensive non-conformant grid
	 * mediator will be used instead of snapping the subgrid to the original one for
	 * 1-to-1 rescaling.
	 * 
	 * @return the accepted subsetting proportion of error
	 */
	double getAcceptedSubsettingError();

	/**
	 * If true, each instance created by instantiatiors will be resolved
	 * independently using the k.LAB network. This may make for more accurate
	 * resolution when instances and context have widely different scales, but will
	 * use much more time and resources if the instances are many. The default is
	 * false.
	 * 
	 * @return true to force independent resolution of instances
	 */
	boolean resolveAllInstances();

	/**
	 * The maximum number of root contexts kept alive per session. Defaults at 10.
	 * 
	 * @return the maximum number of live contexts
	 */
	int getMaxLiveObservationContextsPerSession();

	/**
	 * The branch to use for groups observables
	 * 
	 * @return
	 */
	String getProductsBranch();

	/**
	 * Return an individual property. Instead of merely looking into the result of
	 * {{@link #getProperties()}, this one shold look first if the property has been
	 * defined through a Java system variable (-Dppp=vvv) and if so, returns that
	 * value independent of what the property file contains.
	 * 
	 * @param property
	 * @param defaultValue
	 * @return its value from runtime properties or config, or the default value.
	 */
	String getProperty(String property, String defaultValue);

}

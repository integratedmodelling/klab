package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.util.Properties;
import org.integratedmodelling.klab.utils.OS;

/**
 * 
 * @author Ferd
 *
 */
public interface IConfigurationService {

    public static final String KLAB_CLIENT_PROJECTS               = "klab.client.workspace";

    /**
     * 
     */
    public static final String KLAB_OFFLINE                       = "klab.offline";

    /**
     * 
     */
    public static final String KLAB_USE_CONTEXT_QUALITIES         = "klab.use.context.qualities";

    /**
     * 
     */
    public static final String KLAB_EXPORT_PATH                   = "klab.export.path";

    /**
     * 
     */
    public static final String KLAB_REASONING                     = "klab.reasoning";

    /**
     * 
     */
    public static final String KLAB_DEBUG                         = "klab.debug";

    /**
     * 
     */
    public static final String CERTFILE_PROPERTY                  = "klab.certificate";

    /**
     * 
     */
    public static final String KLAB_SOURCE_DISTRIBUTION           = "thinklab.source.distribution";

    /**
     * 
     */
    public static final String KLAB_CONNECTION_TIMEOUT            = "klab.connection.timeout";

    /*
     * these properties can be set to define what states to store during contextualization when the
     * defaults are inadequate. They're mostly unsupported at this time.
     */
    /**
     * 
     */
    public static final String KLAB_STORE_RAW_DATA                = "klab.store.raw";
    /**
     * 
     */
    public static final String KLAB_STORE_INTERMEDIATE_DATA       = "klab.store.intermediate";
    /**
     * 
     */
    public static final String KLAB_STORE_CONDITIONAL_DATA        = "klab.store.conditional";
    /**
     * 
     */
    public static final String KLAB_STORE_MEDIATED_DATA           = "klab.store.mediated";

    /**
     * Minutes after which a session times out. Default 60.
     */
    public static final String KLAB_SESSION_TIMEOUT_MINUTES       = "klab.session.timeout";

    /**
     * Absolute path of work directory. Overrides the default which is
     * ${user.home}/THINKLAB_WORK_DIRECTORY
     */
    public static final String KLAB_DATA_DIRECTORY                = "klab.data.directory";

    /**
     * Name of work directory relative to ${user.home}. Ignored if THINKLAB_DATA_DIRECTORY_PROPERTY is
     * specified.
     */
    public static final String KLAB_WORK_DIRECTORY                = "klab.work.directory";

    /**
     * Points to a comma-separated list of directories where components are loaded from their Maven
     * development tree.
     */
    public static final String KLAB_LOCAL_COMPONENTS              = "klab.local.components";

    /**
     * 
     */
    public static final String KLAB_ENGINE_CERTIFICATE            = "klab.engine.certificate";

    /**
     * 
     */
    public static final String KLAB_ENGINE_DATADIR                = "klab.engine.datadir";

    /**
     * 
     */
    public static final String KLAB_ENGINE_DEBUG_PORT             = "klab.engine.debugPort";

    /**
     * 
     */
    public static final String KLAB_ENGINE_USE_DEBUG              = "klab.engine.useDebug";

    /**
     * 
     */
    public static final String KLAB_ENGINE_KLAB_DEBUG             = "klab.engine.klabDebug";

    /**
     * 
     */
    public static final String KLAB_ENGINE_USE_DEVELOPER_NETWORK  = "klab.engine.useDeveloperNetwork";

    /**
     * 
     */
    public static final String KLAB_ENGINE_USE_LOCAL_INSTALLATION = "klab.engine.useLocalInstallation";

    /**
     * 
     */
    public static final String KLAB_ENGINE_SHUTDOWN_ON_EXIT       = "klab.engine.shutdownOnExit";

    /**
     * 
     */
    public static final String KLAB_ENGINE_UPGRADE_AUTOMATICALLY  = "klab.engine.upgradeAutomatically";

    /**
     * 
     */
    public static final String KLAB_ENGINE_LAUNCH_AUTOMATICALLY   = "klab.engine.launchAutomatically";

    /**
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the ontology
     * holding the main concept in the declaration (false).
     */
    public static final String KLAB_USE_COMMON_ONTOLOGY           = "klab.engine.useCommonOntology";

    /**
     * Class to choose to create storage - used only to disambiguate if > 1 storage providers are available.
     */
    public static final String STORAGE_PROVIDER_COMPONENT         = "klab.storage.provider.class";

    /**
     * Class to choose to create dataflow runtimes - used only to disambiguate if > 1 runtime providers are available.
     */
    public static final String RUNTIME_PROVIDER_COMPONENT         = "klab.runtime.provider.class";

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
     * @param subspace
     * @return the file directory created.
     */
    File getDataPath(String subspace);

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

}

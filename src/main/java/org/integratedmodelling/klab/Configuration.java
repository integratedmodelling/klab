package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.OS;

/**
 * TODO use a declarative approach for all properties, so that there is one place for all
 * default settings and it's possible to override any of them through global JVM settings.
 * 
 * @author Ferd
 *
 */
public enum Configuration implements IConfigurationService {
    INSTANCE;

    private OS                 os;

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
    public static final String KLAB_CLIENT_DEBUG                  = "thinklab.client.debug";

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
     * these properties can be set to define what states to store during
     * contextualization when the defaults are inadequate. They're mostly
     * unsupported at this time.
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
     * Absolute path of work directory. Overrides the default which is ${user.home}/THINKLAB_WORK_DIRECTORY
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
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the
     * ontology holding the main concept in the declaration (false).
     */
    public static final String KLAB_USE_COMMON_ONTOLOGY           = "klab.engine.useCommonOntology";

//    /**
//     * If true, instructs some calls to spoof the _dev URLs of development network nodes to look like the
//     * official one (without the suffix returned by {@link #getDeveloperNetworkURLPostfix()}). Used when
//     * external services are configured with the official node URL and the modified URL gets in the way.
//     */
//    public static final String KLAB_SPOOF_DEV_URL                 = "klab.engine.spoofdevurl";

    private Properties         properties;
    private File               dataPath;
    private Level              notificationLevel;

    public String              KLAB_RELATIVE_WORK_PATH            = ".klab";

    private Configuration() {

        if (System.getProperty(KLAB_DATA_DIRECTORY) != null) {

            this.dataPath = new File(System.getProperty(KLAB_DATA_DIRECTORY));
            // this.scratchPath = new File(this.dataPath + File.separator + ".scratch");

        } else {
            String home = System.getProperty("user.home");
            if (System.getProperty(KLAB_WORK_DIRECTORY) != null) {
                KLAB_RELATIVE_WORK_PATH = System.getProperty(KLAB_WORK_DIRECTORY);
            }
            this.dataPath = new File(home + File.separator + KLAB_RELATIVE_WORK_PATH);
            // this.scratchPath = new File(this.dataPath + File.separator + ".scratch");

            /*
             * make sure it's  available for substitution in property files etc.
             */
            System.setProperty(KLAB_DATA_DIRECTORY, this.dataPath.toString());
        }

        this.dataPath.mkdirs();
        // this.scratchPath.mkdirs();

        // KLAB.info("k.LAB data directory set to " + dataPath);

        notificationLevel = Level.INFO;

        this.properties = new Properties();
        File pFile = new File(dataPath + File.separator + "klab.properties");
        if (!pFile.exists()) {
            try {
                FileUtils.touch(pFile);
            } catch (IOException e) {
                throw new KlabRuntimeException("cannot write to configuration directory");
            }
        }
        try (InputStream input = new FileInputStream(pFile)) {
            this.properties.load(input);
        } catch (Exception e) {
            throw new KlabRuntimeException("cannot read configuration properties");
        }

        if (this.properties.containsKey(KLAB_CLIENT_DEBUG)) {
            if (this.properties.getProperty(KLAB_CLIENT_DEBUG, "off").equals("on")) {
                notificationLevel = Level.FINEST;
            }
        }
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    /*
     * Non-API
     * Save the properties after making changes from outside configuration. 
     * Should be used only internally, or removed in favor of a painful setting API.
     */
    public void save() {

        File td = new File(dataPath + File.separator + "klab.properties");

        // String[] doNotPersist = new String[] { Project.ORIGINATING_NODE_PROPERTY };

        Properties p = new Properties();
        p.putAll(getProperties());

        // for (String dn : doNotPersist) {
        // p.remove(dn);
        // }

        try {
            p.store(new FileOutputStream(td), null);
        } catch (Exception e) {
            throw new KlabRuntimeException(e);
        }

    }

    @Override
    public boolean useReasoner() {
        return true;
    }

    @Override
    public boolean useCommonOntology() {
        return !(getProperties().containsKey(KLAB_USE_COMMON_ONTOLOGY)
                && !Boolean.parseBoolean(getProperties().getProperty(KLAB_USE_COMMON_ONTOLOGY)));
    }

    @Override
    public OS getOS() {

        if (this.os == null) {

            String osd = System.getProperty("os.name").toLowerCase();

            // TODO ALL these checks need careful checking
            if (osd.contains("windows")) {
                os = OS.WIN;
            } else if (osd.contains("mac")) {
                os = OS.MACOS;
            } else if (osd.contains("linux") || osd.contains("unix")) {
                os = OS.UNIX;
            }
        }

        return this.os;
    }

    @Override
    public File getDataPath(String subspace) {
        File ret = new File(dataPath + File.separator + subspace);
        ret.mkdirs();
        return ret;
    }

    @Override
    public boolean isOffline() {
        return getProperties().getProperty(KLAB_OFFLINE, "off").equals("on");
    }

    @Override
    public File getDataPath() {
        return dataPath;
    }

}

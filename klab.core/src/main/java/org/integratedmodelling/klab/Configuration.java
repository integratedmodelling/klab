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


    // /**
    // * If true, instructs some calls to spoof the _dev URLs of development network nodes to look like the
    // * official one (without the suffix returned by {@link #getDeveloperNetworkURLPostfix()}). Used when
    // * external services are configured with the official node URL and the modified URL gets in the way.
    // */
    // public static final String KLAB_SPOOF_DEV_URL = "klab.engine.spoofdevurl";

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

        if (this.properties.containsKey(KLAB_DEBUG)) {
            if (this.properties.getProperty(KLAB_DEBUG, "off").equals("on")) {
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

        String dpath = dataPath.toString();
        File ret = dataPath;

        String[] paths = subspace.split("/");
        for (String path : paths) {
            ret = new File(dpath + File.separator + path);
            ret.mkdirs();
            dpath += File.separator + path;
        }
        return ret;
    }

    @Override
    public boolean isOffline() {
        return getProperties().getProperty(KLAB_OFFLINE, "false").equals("true");
    }
    
    @Override
    public boolean isDebuggingEnabled() {
      return getProperties().getProperty(KLAB_DEBUG, "false").equals("true");
    }

    @Override
    public File getDataPath() {
        return dataPath;
    }

    @Override
    public int getDataflowThreadCount() {
      // TODO Auto-generated method stub
      return 10;
    }

    @Override
    public int getTaskThreadCount() {
      // TODO Auto-generated method stub
      return 10;
    }
    
    @Override
    public int getScriptThreadCount() {
      // TODO Auto-generated method stub
      return 3;
    }

    @Override
    public boolean isRemoteResolutionEnabled() {
      // TODO tie to option + live setting
      return true;
    }
}

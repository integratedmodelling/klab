package org.integratedmodelling.klab.auth;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Wraps an IM certificate and checks for encryption and validity.
 * 
 * @author Ferd
 *
 */
public class KlabCertificate implements ICertificate {

    private File                file                           = null;
    private Properties          properties                     = null;
    private String              cause                          = null;
    private Set<String>         groups                         = null;
    private DateTime            expiry;
    private String              worldview                      = DEFAULT_WORLDVIEW;
    private Collection<String>  worldview_repositories         = StringUtils
            .splitOnCommas(DEFAULT_WORLDVIEW_REPOSITORIES);

    /**
     * Property key for username
     */
    public static final String  USER_KEY                       = "user";
    /**
     * Property key for primary node.
     */
    public static final String  PRIMARY_NODE_KEY               = "primary.server";

    private static final String DEFAULT_WORLDVIEW              = "im";

    private static final String DEFAULT_WORLDVIEW_REPOSITORIES = "https://bitbucket.org/ariesteam/im.git#feature/noobservers";

    /**
     * Get the file for the certificate according to configuration. Does not check that the file exists.
     * 
     * @return the configured certificate file location
     */
    public static File getCertificateFile() {
        if (System.getProperty(Configuration.CERTFILE_PROPERTY) != null) {
            return new File(System.getProperty(Configuration.CERTFILE_PROPERTY));
        }
        return new File(Configuration.INSTANCE.getDataPath() + File.separator + "im.cert");
    }

    /**
     * Get the file from its configured locations and open it. Check {@link #isValid()} after construction.
     */
    public KlabCertificate() {
        this(getCertificateFile());
    }

    public KlabCertificate(String s) {
        try {
            this.file = File.createTempFile("imcert", "cert");
            FileUtils.writeStringToFile(this.file, s);
        } catch (IOException e) {
            throw new KlabRuntimeException(e);
        }
    }

    /**
     * Create from a certificate file, downloading the public keyring from the net if not already installed.
     * Check {@link #isValid()} after construction.
     * 
     * @param file
     */
    public KlabCertificate(File file) {
        this.file = file;
    }

    /**
     * If this returns true, the certificate exists, is readable and properly encrypted, and is current.
     * 
     * If this returns false, {@link #getCause} will contain the reason why.
     * 
     * @return true if everything is OK.
     */
    public boolean isValid() {
        if (cause != null) {
            return false;
        }
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            cause = "certificate file cannot be read";
            return false;
        }
        if (properties == null) {
            try {
                properties = LicenseManager.readCertificate(file, new URL(Auth.PUBRING_URL));

                /*
                 * check expiration
                 */
                String exp = properties.getProperty("expiry");
                if (exp != null) {
                    try {
                        this.expiry = DateTime.parse(properties.getProperty("expiry"));
                    } catch (Throwable e) {
                    }
                    if (expiry /* still */ == null) {
                        Klab.INSTANCE.info("error parsing expiry date: setting to tomorrow");
                        this.expiry = DateTime.now().plusDays(1);
                    }
                }
                if (expiry == null) {
                    cause = "certificate has no expiration date. Please obtain a new certificate.";
                    return false;
                } else if (expiry.isBeforeNow()) {
                    cause = "certificate expired on " + expiry;
                    return false;
                }

            } catch (Throwable e) {
                cause = "error reading certificate: " + e.getMessage();
                return false;
            }
        }

        return true;
    }

    public DateTime getExpiryDate() {
        return expiry;
    }

    /**
     * If {@link #isValid()} returns false, return the reason why.
     * 
     * @return cause of invalidity
     */
    public String getCause() {
        return cause;
    }

    /**
     * Return the file we've been read from.
     * 
     * @return the certificate file
     */
    public File getFile() {
        return file;
    }

    /**
     * The properties defined in the certificate. Call after checking validity.
     * 
     * @return properties. Only null if {@link KlabCertificate#isValid()} has not been called or has returned
     *         false.
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * 
     * @return the certificate contents
     * @throws KlabIOException
     */
    public String getCertificate() throws KlabIOException {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }
    }

    /**
     * Groups extracted from certificate. May not be the same groups as the user profile after online
     * authentication.
     * 
     * @return the groups in the cert file.
     */
    public Set<String> getGroups() {
        if (this.groups == null) {
            this.groups = new HashSet<>();
            if (properties != null && properties.containsKey(Auth.GROUPS)) {
                for (String g : StringUtils
                        .split(properties.getProperty(Auth.GROUPS), ',')) {
                    groups.add(g);
                }
            }
        }
        return this.groups;
    }

    /**
     * Descriptive string with user name, email, and expiry.
     * 
     * @return printable description.
     */
    public String getUserDescription() {
        if (properties != null && properties.containsKey(Auth.USER)) {
            return properties.getProperty(Auth.USER) + " ("
                    + properties.getProperty(Auth.EMAIL) + "), valid until "
                    + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
        }
        return "anonymous user";
    }

    @Override
    public IWorldview getWorldview() {
        return new Worldview(worldview, Configuration.INSTANCE
                .getDataPath("worldview"), worldview_repositories, ((AbstractWorkspace)Resources.INSTANCE.getLocalWorkspace())
                        .getProjectLocations()
                        .toArray(new File[ ((AbstractWorkspace)Resources.INSTANCE.getLocalWorkspace()).getProjectLocations().size()]));
    }

    @Override
    public IIdentity getIdentity() {
        // TODO Auto-generated method stub
        return null;
    }
}

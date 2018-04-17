package org.integratedmodelling.klab.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.data.rest.client.Client;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticationRequest;
import org.integratedmodelling.klab.data.rest.resources.responses.AuthenticationResponse;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
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

    /*
     * Keys for user properties in certificates or for set operations.
     */
    public static final String KEY_EMAIL = "klab.user.email";
    public static final String KEY_USER = "klab.username";
    public static final String KEY_SIGNATURE = "klab.user.signature";
    public static final String KEY_SERVER = "klab.partner.node";
    public static final String KEY_CERTIFICATE = "klab.certificate";
    public static final String KEY_CERTIFICATE_TYPE = "klab.certificate.type";

    // TODO just store a URL and handle uniformly
    private File file = null;
    private String resource = null;

    private Properties properties = null;
    private String cause = null;
    private DateTime expiry;
    private String worldview = DEFAULT_WORLDVIEW;
    private Collection<String> worldview_repositories = StringUtils.splitOnCommas(DEFAULT_WORLDVIEW_REPOSITORIES);
    AuthenticationResponse authentication = null;

    /**
     * Property key for username
     */
    public static final String USER_KEY = "user";
    /**
     * Property key for primary node.
     */
    public static final String PRIMARY_NODE_KEY = "primary.server";

    static final String DEFAULT_WORLDVIEW = "im";

    static final String DEFAULT_WORLDVIEW_REPOSITORIES = "https://bitbucket.org/ariesteam/im.git#feature/noobservers";

    /**
     * Create a new certificate from a file. Check isValid() on the resulting certificate.
     * 
     * @param file the certificate file
     * @return a certificate read from the passed file
     */
    public static ICertificate createFromFile(File file) {
        if (file == null) {
            return createDefault();
        }
        return new KlabCertificate(file);
    }

    public static ICertificate createFromClasspath(String resource) {
        return new KlabCertificate(resource);
    }

    /**
     * Get the file from its configured locations and open it. If there is no certificate and the
     * configuration allows anonymous users, return an anonymous certificate. Check {@link #isValid()}
     * after construction.
     * 
     * @return the default certificate
     */
    public static ICertificate createDefault() {

        File certfile = getCertificateFile();
        if (certfile.exists()) {
            return new KlabCertificate(certfile);
        }
        if (Configuration.INSTANCE.allowAnonymousUsage()) {
            return new AnonymousCertificate();
        }
        throw new KlabIllegalStatusException("certificate file not found and anonymous usage not allowed");
    }

    /**
     * Get the file for the certificate according to configuration. Does not check that the file
     * exists.
     * 
     * @return the configured certificate file location
     */
    public static File getCertificateFile() {
        if (System.getProperty(Configuration.CERTFILE_PROPERTY) != null) {
            return new File(System.getProperty(Configuration.CERTFILE_PROPERTY));
        }
        return new File(Configuration.INSTANCE.getDataPath() + File.separator + DEFAULT_CERTIFICATE_FILENAME);
    }

    /**
     * Create from a certificate file, downloading the public keyring from the net if not already
     * installed. Check {@link #isValid()} after construction.
     * 
     * @param file
     */
    private KlabCertificate(File file) {
        this.file = file;
    }

    private KlabCertificate(String resource) {
        this.resource = resource;
    }

    public boolean isValid() {

        if (cause != null) {
            return false;
        }

        if (properties == null) {

            if (file != null && (!file.exists() || !file.isFile() || !file.canRead())) {
                cause = "certificate file cannot be read";
                return false;
            }

            properties = new Properties();
            try (InputStream inp = (resource == null ? new FileInputStream(file)
                    : getClass().getClassLoader().getResource(resource).openStream())) {
                properties.load(inp);
            } catch (IOException e) {
                cause = e.getMessage();
                return false;
            }

            /*
             * TODO perform some basic checks on the certificate to guarantee that all needed
             * properties are there.
             */
            if (properties.getProperty(KEY_CERTIFICATE_TYPE, "").equals(ICertificate.CERTIFICATE_TYPE_NODE)) {
                return authenticateNode();
            } else if (properties.getProperty(KEY_CERTIFICATE_TYPE, "").equals(ICertificate.CERTIFICATE_TYPE_USER)) {
                return authenticateUser();
            }

        }

        return false;
    }

    private boolean authenticateNode() {
        // TODO Auto-generated method stub
        return true;
    }

    private boolean authenticateUser() {

        Logging.INSTANCE.info("authenticating user " + properties.getProperty(KEY_USER));

        /*
         * Authenticate with server(s). If authentication fails because of a 403, invalidate
         * the certificate. If no server can be reached, certificate is valid but engine is offline.
         */
        AuthenticationRequest request = new AuthenticationRequest(properties.getProperty(KEY_USER),
                properties.getProperty(KEY_SIGNATURE), properties.getProperty(KEY_CERTIFICATE_TYPE),
                properties.getProperty(KEY_CERTIFICATE));

        try {
            authentication = Client.create().authenticate(properties.getProperty(KEY_SERVER), request);
        } catch (Throwable e) {

            Logging.INSTANCE.error(
                    "authentication failed for user " + properties.getProperty(KEY_USER) + ": " + e.getMessage());
            // TODO inspect exception; fatal if 403, warn and proceed offline otherwise
        }

        if (authentication != null) {

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
                    Logging.INSTANCE.info("error parsing expiry date: setting to tomorrow");
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
        } else {
            /*
             * user is offline
             */
            Logging.INSTANCE.error(
                    "authentication unsuccessful for " + properties.getProperty(KEY_USER) + ": continuing offline");

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
    @Override
    public String getInvalidityCause() {
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
     * Descriptive string with user name, email, and expiry.
     * 
     * @return printable description.
     */
    public String getUserDescription() {
        if (properties != null && properties.containsKey(KEY_USER)) {
            return properties.getProperty(KEY_USER) + " (" + properties.getProperty(KEY_USER) + "), valid until "
                    + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
        }
        return "anonymous user";
    }

    @Override
    public IWorldview getWorldview() {
        return new Worldview(worldview, Configuration.INSTANCE.getDataPath("worldview"), worldview_repositories,
                ((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace()).getProjectLocations()
                        .toArray(new File[((AbstractWorkspace) Resources.INSTANCE.getLocalWorkspace())
                                .getProjectLocations().size()]));
    }

    @Override
    public IIdentity getIdentity() {
        PartnerIdentity partner = null; // TODO
        NodeIdentity node = new NodeIdentity(null, partner); // TODO insert the node we authenticated with
        // TODO if we have connected, insert network session identity
        if (authentication != null) {

        }
        KlabUserIdentity ret = new KlabUserIdentity(properties.getProperty(KEY_USER), node);
        ret.setOnline(authentication != null);
        return ret;
    }
}

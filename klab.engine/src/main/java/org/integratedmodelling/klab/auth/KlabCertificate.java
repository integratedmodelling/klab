package org.integratedmodelling.klab.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.data.rest.client.Client;
import org.integratedmodelling.klab.data.rest.resources.NodeReference;
import org.integratedmodelling.klab.data.rest.resources.requests.AuthenticationRequest;
import org.integratedmodelling.klab.data.rest.resources.responses.AuthenticationResponse;
import org.integratedmodelling.klab.engine.resources.AbstractWorkspace;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.exceptions.KlabIllegalStatusException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
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
    public static final String KEY_USERNAME = "klab.username";
    public static final String KEY_NODENAME = "klab.nodename";
    public static final String KEY_URL = "klab.url";
    public static final String KEY_SIGNATURE = "klab.signature";
    public static final String KEY_SERVER = "klab.partner.node";
    public static final String KEY_PARTNER_NAME = "klab.partner.name";
    public static final String KEY_PARTNER_EMAIL = "klab.partner.email";
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
    private AuthenticationResponse authentication = null;
    private IIdentity identity = null;
    private String certificateType = "UNKNOWN";

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

            this.certificateType = properties.getProperty(KEY_CERTIFICATE_TYPE, "UNKNOWN");

            /*
             * TODO perform some basic checks on the certificate to guarantee that all needed
             * properties are there.
             */
            if (this.certificateType.equals(ICertificate.CERTIFICATE_TYPE_NODE)) {
                return authenticateNode();
            } else if (this.certificateType.equals(ICertificate.CERTIFICATE_TYPE_USER)) {
                return authenticateUser();
            }

        }

        return false;
    }

    private boolean authenticateNode() {
        Logging.INSTANCE.info("authenticating node " + properties.getProperty(KEY_USERNAME));
        /*
         * TODO authenticate node!
         */
        return true;
    }

    private boolean authenticateUser() {

        Logging.INSTANCE.info("authenticating user " + properties.getProperty(KEY_USERNAME));

        /*
         * Authenticate with server(s). If authentication fails because of a 403, invalidate
         * the certificate. If no server can be reached, certificate is valid but engine is offline.
         */
        AuthenticationRequest request = new AuthenticationRequest(properties.getProperty(KEY_USERNAME),
                properties.getProperty(KEY_SIGNATURE), properties.getProperty(KEY_CERTIFICATE_TYPE),
                properties.getProperty(KEY_CERTIFICATE));

        try {
            authentication = Client.create().authenticate(properties.getProperty(KEY_SERVER), request);
        } catch (Throwable e) {

            Logging.INSTANCE.error(
                    "authentication failed for user " + properties.getProperty(KEY_USERNAME) + ": " + e.getMessage());
            // TODO inspect exception; fatal if 403, warn and proceed offline otherwise
        }

        if (authentication != null) {

            /*
             * check expiration
             */
            try {
                this.expiry = DateTime.parse(authentication.getUserData().getExpiry());
            } catch (Throwable e) {
                cause = "bad date or wrong date format in certificate. Please use latest version of software.";
                return false;
            }
            if (expiry == null) {
                cause = "certificate has no expiration date. Please obtain a new certificate.";
                return false;
            } else if (expiry.isBeforeNow()) {
                cause = "certificate expired on " + expiry + ". Please obtain a new certificate.";
                return false;
            }
        } else {
            
            /*
             * user is offline
             */
            Logging.INSTANCE.error(
                    "authentication unsuccessful for " + properties.getProperty(KEY_USERNAME) + ": continuing offline");

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
        if (properties != null && properties.containsKey(KEY_USERNAME)) {
            return properties.getProperty(KEY_USERNAME) + " (" + properties.getProperty(KEY_USERNAME)
                    + "), valid until " + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
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

        if (this.identity == null) {

            if (CERTIFICATE_TYPE_USER.equals(this.certificateType)) {

                // TODO if we have connected, insert network session identity
                if (authentication != null) {

                    NodeReference partnerNode = null;
                    for (NodeReference n : authentication.getNodes()) {
                        if (n.getId().equals(authentication.getAuthenticatingNodeId())) {
                            partnerNode = n;
                            break;
                        }
                    }
                    Partner partner = Auth.INSTANCE.requirePartner(partnerNode.getOwningPartner());
                    Node node = new Node(partnerNode, partner);
                    node.setOnline(true);
                    NetworkSession networkSession = new NetworkSession(authentication.getUserData().getToken(),
                            authentication.getNodes(), node);

                    this.identity = new KlabUser(authentication.getUserData(), networkSession);

                } else {

                    // offline node with no partner
                    Node node = new Node(properties.getProperty(KEY_NODENAME), null);
                    ((Node) node).setOnline(false);
                    this.identity = new KlabUser(properties.getProperty(KEY_USERNAME), node);
                }

                ((KlabUser) this.identity).setOnline(authentication != null);

            } else if (CERTIFICATE_TYPE_NODE.equals(this.certificateType)) {

                Partner partner = new Partner(properties.getProperty(KEY_PARTNER_NAME)); // TODO
                partner.setEmailAddress(properties.getProperty(KEY_PARTNER_EMAIL));

                this.identity = new Node(properties.getProperty(KEY_NODENAME), partner);
                /**
                 * TODO add authenticated data
                 */
                ((Node) this.identity).getUrls().add(properties.getProperty(KEY_URL));

            } else {
                throw new KlabUnsupportedFeatureException("cannot create identity of type " + this.certificateType);
            }

        }
        return this.identity;

    }
}

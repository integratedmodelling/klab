package org.integratedmodelling.klab.auth;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Wraps an IM certificate and checks for encryption and validity.
 * 
 * @author Ferd
 *
 */
public class KlabCertificate implements ICertificate {

    // just for info
    public static final String KEY_EXPIRATION = "klab.validuntil";

    private static final String LEGACY_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/collaboration/authentication/cert-file";

    // TODO just store a URL and handle uniformly
    private File file = null;
    private String resource = null;
    private Properties properties = null;
    private String cause = null;
    private DateTime expiry;
    private String worldview = DEFAULT_WORLDVIEW;
    private Map<String, Set<String>> worldview_repositories = null;
    private Type type = Type.ENGINE;
    private Level level = Level.USER;

    /**
     * Property key for username
     */
    public static final String USER_KEY = "user";
    /**
     * Property key for primary node.
     */
    public static final String PRIMARY_NODE_KEY = "primary.server";

    static final String DEFAULT_WORLDVIEW = "im";

    // static final String DEFAULT_WORLDVIEW_REPOSITORIES =
    // "https://bitbucket.org/integratedmodelling/im.git,https://bitbucket.org/integratedmodelling/im.aries.git";

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

    public static KlabCertificate createFromClasspath(String resource) {
        return new KlabCertificate(resource);
    }

    public static KlabCertificate createFromProperties(Properties props) {
        return new KlabCertificate(props);
    }

    /**
     * Create a new certificate from a string. Check isValid() on the resulting certificate in this
     * block because the file is deleted after.
     * 
     * @param string is the certificate as a complete string
     * @return a certificate read from the passed string
     */
    public static ICertificate createFromString(String string) {
        ICertificate cert;
        try {
            File temp = File.createTempFile(NameGenerator.newName(), ".cert");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            bw.write(string);
            bw.close();
            cert = KlabCertificate.createFromFile(temp);
            cert.isValid();
            temp.delete();
        } catch (IOException e) {
            throw new KlabIllegalStateException("certificate string could not be turned into a file");
        }
        return cert;

    }

    /**
     * Get the file from its configured locations and open it. If there is no certificate and the
     * configuration allows anonymous users, return an anonymous certificate. Check
     * {@link #isValid()} after construction.
     * 
     * @return the default certificate
     */
    public static ICertificate createDefault() {

        File certfile = getCertificateFile();
        if (certfile.exists()) {
            return new KlabCertificate(certfile);
        }
        if (Configuration.INSTANCE.allowAnonymousUsage()) {
            return new AnonymousEngineCertificate();
        }
        throw new KlabIllegalStateException("certificate file not found and anonymous usage not allowed");
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
        return new File(Configuration.INSTANCE.getDataPath() + File.separator + DEFAULT_ENGINE_CERTIFICATE_FILENAME);
    }

    /**
     * Create from a certificate file, downloading the public keyring from the net if not already
     * installed. Check {@link #isValid()} after construction.
     * 
     * @param file
     */
    private KlabCertificate(File file) {
        this.file = file;
        // for (String w : StringUtil
        // .splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES)) {
        // worldview_repositories.put(w, new HashSet<>());
        // }
    }

    private KlabCertificate(String resource) {
        this.resource = resource;
        // for (String w : StringUtil
        // .splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES)) {
        // worldview_repositories.put(w, new HashSet<>());
        // }
    }

    public KlabCertificate(Properties props) {
        this.properties = props;
        // for (String w : StringUtil
        // .splitOnCommas(KlabCertificate.DEFAULT_WORLDVIEW_REPOSITORIES)) {
        // worldview_repositories.put(w, new HashSet<>());
        // }
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

            if (file != null && upgradeCertificate(file)) {
                return true;
            }

            properties = new Properties();
            try (InputStream inp = (resource == null
                    ? new FileInputStream(file)
                    : getClass().getClassLoader().getResource(resource).openStream())) {
                properties.load(inp);
            } catch (IOException e) {
                cause = e.getMessage();
                return false;
            }

            this.type = Type.valueOf(properties.getProperty(KEY_CERTIFICATE_TYPE));
            this.level = Level.valueOf(properties.getProperty(KEY_CERTIFICATE_LEVEL, Level.USER.name()));

            if (this.type == Type.NODE || this.type == Type.HUB) {
                if (properties.getProperty(KEY_URL) == null) {
                    cause = "node or hub certificate must define the public URL for the service (klab.url)";
                    return false;
                }
            }

            return true;
        }

        return true;
    }

    /**
     * Check if the certificate is an old version and try to upgrade it by authenticating to the
     * collaboration server and writing a new one for a generic hub. If successful, finish
     * authentication and return true to signal that everything is done.
     * 
     * @param file
     * @return true if
     */
    public boolean upgradeCertificate(File file2) {
        try {
            String fileContent = FileUtils.readFileToString(file2);
            if (fileContent.startsWith("-----BEGIN PGP MESSAGE-----")) {

                // reauthenticate and produce a new format certificate
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<String> request = new HttpEntity<String>(fileContent);
                Map<?, ?> response = restTemplate.postForObject(LEGACY_AUTHENTICATION_ENDPOINT, request, Map.class);

                if (response != null) {

                    Map<?, ?> profile = (Map<?, ?>) response.get("profile");

                    Logging.INSTANCE.info("upgrading pre-0.10 certificate to new format: new certificate will be saved as "
                            + ICertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME);

                    this.properties = new Properties();
                    this.properties.setProperty(KEY_CERTIFICATE, fileContent);
                    this.properties.setProperty(KEY_EXPIRATION, response.get("expiration").toString());
                    this.properties.setProperty(KEY_USERNAME, response.get("username").toString());
                    this.properties.setProperty(KEY_EMAIL, profile.get("email").toString());
                    this.properties.setProperty(KEY_SIGNATURE, "legacy certificate");
                    this.properties.setProperty(KEY_PARTNER_NAME, "integratedmodelling.org");
                    this.properties.setProperty(KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
                    this.properties.setProperty(KEY_NODENAME, "im");
                    this.properties.setProperty(KEY_CERTIFICATE_TYPE, Type.ENGINE.name());
                    this.properties.setProperty(KEY_CERTIFICATE_LEVEL, ICertificate.Level.LEGACY.name());

                    /*
                     * Default hub address. TODO must be https. Blank or no response will try a
                     * local test hub before going offline.
                     */
                    this.properties.setProperty(KlabCertificate.KEY_PARTNER_HUB, "http://www.integratedmodelling.org/klab");

                    File out = new File(Configuration.INSTANCE.getDataPath() + File.separator
                            + ICertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME);
                    try (FileOutputStream o = new FileOutputStream(out)) {
                        this.properties.store(o, "Automatically upgraded on " + new Date());
                    }

                    this.expiry = DateTime.parse(response.get("expiration").toString());
                    if (expiry == null) {
                        cause = "certificate has no expiration date. Please obtain a new certificate.";
                        return false;
                    } else if (expiry.isBeforeNow()) {
                        cause = "certificate expired on " + expiry + ". Please obtain a new certificate.";
                        return false;
                    }

                    return true;

                } else {
                    Logging.INSTANCE.error("legacy certificate could not be authenticated");
                }
            }
        } catch (IOException e) {
            // just return false
        }
        return false;
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
            return properties.getProperty(KEY_USERNAME) + " (" + properties.getProperty(KEY_USERNAME) + "), valid until "
                    + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
        }
        return "anonymous user";
    }

    @Override
    public String getWorldview() {
        return worldview;
    }

    public Map<String, Set<String>> getWorldviewRepositories(String worldviewIds) {
        if (worldview_repositories == null) {
            worldview_repositories = new HashMap<>();
            IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
            this.worldview = Authentication.INSTANCE.getWorldview(user);
            for (String repository : Authentication.INSTANCE.getWorldviewRepositories(user)) {
                worldview_repositories.put(repository, new HashSet<>());
            }
        }
        return worldview_repositories;
    }

    @Override
    public String getProperty(String property) {
        return properties == null ? null : properties.getProperty(property);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Level getLevel() {
        return level;
    }
}

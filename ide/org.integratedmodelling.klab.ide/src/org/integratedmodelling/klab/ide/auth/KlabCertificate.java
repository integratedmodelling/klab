package org.integratedmodelling.klab.ide.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.joda.time.DateTime;

public class KlabCertificate {
    // just for info
    public static final String KEY_EXPIRATION = "klab.validuntil";

    /*
     * Keys for user properties in certificates or for set operations.
     */
    public static final String KEY_EMAIL = "klab.user.email";
    public static final String KEY_USERNAME = "klab.username";
    public static final String KEY_NODENAME = "klab.nodename";
    public static final String KEY_HUBNAME = "klab.hubname";
    public static final String KEY_URL = "klab.url";
    public static final String KEY_SIGNATURE = "klab.signature";
    public static final String KEY_PARTNER_HUB = "klab.partner.hub";
    public static final String KEY_PARTNER_NAME = "klab.partner.name";
    public static final String KEY_PARTNER_EMAIL = "klab.partner.email";
    public static final String KEY_CERTIFICATE = "klab.certificate";
    public static final String KEY_CERTIFICATE_TYPE = "klab.certificate.type";
    public static final String KEY_CERTIFICATE_LEVEL = "klab.certificate.level";

    // TODO just store a URL and handle uniformly
    private File file = null;
    private String resource = null;
    private Properties properties = null;
    private String cause = null;
    private DateTime expiry;

    private boolean valid;

    /**
     * Property key for username
     */
    public static final String USER_KEY = "user";
    /**
     * Property key for primary node.
     */
    public static final String PRIMARY_NODE_KEY = "primary.server";

    static final String DEFAULT_WORLDVIEW = "im";

    /**
     * Create from a certificate file, downloading the public keyring from the net
     * if not already installed. Check {@link #isValid()} after construction.
     * 
     * @param file
     */
    KlabCertificate(File file) {
        this.file = file;
        this.valid = validate();
    }

    public boolean isValid() {
        return this.valid;
    }

    private boolean validate() {

        if (cause != null) {
            return false;
        }

        if (properties == null) {

            if (file != null && (!file.exists() || !file.isFile() || !file.canRead())) {
                cause = "certificate file cannot be read";
                return false;
            }

            if (file == null) {
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

            if (getExpiryDate() == null) {
                cause = "invalid certificate";
                return false;
            }
            
            if (getExpiryDate().isBefore(DateTime.now())) {
                cause = "certificate expired";
                return false;
            }
            
            return true;
        }

        return false;
    }

    public DateTime getExpiryDate() {
        if (this.expiry == null) {
            String td = properties.getProperty(KEY_EXPIRATION);
            if (td != null) {
                this.expiry = DateTime.parse(td);
            }
        }
        return this.expiry;
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
//  public String getUserDescription() {
//      if (properties != null && properties.containsKey(KEY_USERNAME)) {
//          return properties.getProperty(KEY_USERNAME) + " (" + properties.getProperty(KEY_USERNAME)
//                  + "), valid until " + DateTimeFormat.forPattern("MM/dd/YYYY").print(expiry);
//      }
//      return "anonymous user";
//  }

    public String getProperty(String property) {
        return properties == null ? null : properties.getProperty(property);
    }

    public Properties getProperties() {
        return properties;
    }

    public boolean isExpired() {
        return getExpiryDate() != null && getExpiryDate().isBefore(DateTime.now());
    }
}

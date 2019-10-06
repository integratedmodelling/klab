package org.integratedmodelling.controlcenter.auth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Certificate {
    
    /*
     * Keys for user properties in certificates or for set operations.
     */
    public static final String  KEY_EMAIL                      = "klab.user.email";
    public static final String  KEY_USERNAME                   = "klab.username";
    public static final String  KEY_NODENAME                   = "klab.nodename";
    public static final String  KEY_HUBNAME                    = "klab.hubname";
    public static final String  KEY_URL                        = "klab.url";
    public static final String  KEY_SIGNATURE                  = "klab.signature";
    public static final String  KEY_PARTNER_HUB                = "klab.partner.hub";
    public static final String  KEY_PARTNER_NAME               = "klab.partner.name";
    public static final String  KEY_PARTNER_EMAIL              = "klab.partner.email";
    public static final String  KEY_CERTIFICATE                = "klab.certificate";
    public static final String  KEY_CERTIFICATE_TYPE           = "klab.certificate.type";
    public static final String  KEY_CERTIFICATE_LEVEL          = "klab.certificate.level";

    public static final String  KEY_EXPIRATION                 = "klab.validuntil";
    private static final String LEGACY_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/collaboration/authentication/cert-file";

    private Properties          properties;
    private DateTime            expiry;

    private List<String> errors = new ArrayList<>();
    
    /**
     * Check if the certificate is an old version and try to upgrade it by
     * authenticating to the collaboration server and writing a new one for a
     * generic hub. If successful, finish authentication and return true to signal
     * that everything is done.
     * 
     * @param file
     * @return true if
     */
    public boolean upgradeCertificate(File file2, File out) {
        try {

            String fileContent = new String(Files.readAllBytes(file2.toPath()));

            if (fileContent.startsWith("-----BEGIN PGP MESSAGE-----")) {

                HttpResponse<JsonNode> httpResponse = Unirest.post(LEGACY_AUTHENTICATION_ENDPOINT)
                        .body(fileContent).asJson();
                JSONObject response = httpResponse.getBody().getObject();

                if (response != null) {

                    JSONObject profile = response.getJSONObject("profile");

                    this.properties = new Properties();
                    this.properties.setProperty(KEY_CERTIFICATE, fileContent);
                    this.properties.setProperty(KEY_EXPIRATION, response.getString("expiration"));
                    this.properties.setProperty(KEY_USERNAME, response.get("username").toString());
                    this.properties.setProperty(KEY_EMAIL, profile.get("email").toString());
                    this.properties.setProperty(KEY_SIGNATURE, "legacy certificate");
                    this.properties.setProperty(KEY_PARTNER_NAME, "integratedmodelling.org");
                    this.properties.setProperty(KEY_PARTNER_EMAIL, "info@integratedmodelling.org");
                    this.properties.setProperty(KEY_NODENAME, "im");
                    this.properties.setProperty(KEY_CERTIFICATE_TYPE, "ENGINE");
                    this.properties.setProperty(KEY_CERTIFICATE_LEVEL, "LEGACY");

                    /*
                     * Default hub address. TODO must be https. Blank or no response will try a
                     * local test hub before going offline.
                     */
                    this.properties
                            .setProperty(KEY_PARTNER_HUB, "http://www.integratedmodelling.org/klab");

                    try (FileOutputStream o = new FileOutputStream(out)) {
                        this.properties.store(o, "Automatically upgraded on " + new Date());
                    }

                    this.expiry = DateTime.parse(response.get("expiration").toString());
                    if (expiry == null) {
                        error("certificate has no expiration date. Please obtain a new certificate.");
                        return false;
                    } else if (expiry.isBeforeNow()) {
                        error("certificate expired on " + expiry + ". Please obtain a new certificate.");
                        return false;
                    }

                    return true;

                } else {
                    error("legacy certificate could not be authenticated");
                }
            }
        } catch (IOException e) {
            error("legacy certificate could not be read.");
        } catch (Exception e) {
            error("remote server did not respond or authentication was unsuccessful.");
        }
        return false;
    }

    private void error(String string) {
        errors.add(string);
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public File upgradeCertificate(File oldCert) {
        try {
            File newf = File.createTempFile("tmp", "klab.cert");
            upgradeCertificate(oldCert, newf);
            return newf;
        } catch (IOException e) {
            error("legacy certificate could not be read.");
        }
        return null; 
    }

}

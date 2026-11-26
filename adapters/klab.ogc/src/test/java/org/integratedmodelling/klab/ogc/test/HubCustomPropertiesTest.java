package org.integratedmodelling.klab.ogc.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.integratedmodelling.klab.rest.Group;
import org.junit.Assume;
import org.junit.Test;

/**
 * Logs in to the Hub with the certificate of the user (~/.klab/klab.cert), as the engine does at startup, and
 * prints the custom properties of the groups. THis is a helpful check to see if protected services 
 * properly resolve with the vault credentials. The assume should skip the test if no certificate is found, 
 * hence in the build system (more for manual use)
 */
public class HubCustomPropertiesTest {

    @Test
    public void printGroupCustomProperties() {
        File certificate = new File(System.getProperty("user.home"), ".klab/klab.cert");
        Assume.assumeTrue("No certificate at " + certificate, certificate.exists());

        // the hub is the one of the certificate (klab.partner.hub), or a local one at
        // http://127.0.0.1:8284/hub if running
        ICertificate cert = KlabCertificate.createFromFile(certificate);
        assertTrue("Invalid or expired certificate", cert.isValid()); // also loads the file
        System.out.println("Certificate hub: " + cert.getProperty(ICertificate.KEY_PARTNER_HUB));

        IUserIdentity user = Authentication.INSTANCE.authenticate(cert);
        assertNotNull("Login to the Hub failed", user);
        System.out.println("Logged in as " + user.getUsername());

        for (Group group : user.getGroups()) {
            System.out.println("Group " + group.getId());
            for (CustomPropertyRest property : group.getCustomProperties()) {
                boolean resolved = group.getCustomPropertyMap().containsKey(property.getKey());
                System.out.println("  " + property.getKey() + (resolved ? ": resolved" : ": DROPPED by the Hub"));
                System.out.println("    Raw value: " + property.getValue());
                if (resolved) {
                    System.out.println("    Resolved value: " + group.getCustomPropertyMap().get(property.getKey()));
                }
                
            }
        }
    }
}

package org.integratedmodelling.klab.ide.auth;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.osgi.container.Module.Settings;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.joda.time.DateTime;

public class Authentication {

    enum Status {
        ANONYMOUS, OFFLINE, EXPIRED, INVALID, VALID
    }

    private static final String DEFAULT_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/hub";
    public static final String DEVELOPER_GROUP = "DEVELOPERS";

    Status status = Status.ANONYMOUS;
    String authenticationEndpoint;
    String username = "anonymous";
    String email = "";
    DateTime tokenExpiration;
    List<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
    List<Group> groups = new ArrayList<Group>();
    boolean developer = false;
//    Settings settings;

    private KlabCertificate certificate;

    private String authorization;

    public Authentication(Settings settings) {
//        this.settings = settings;
//        File file = this.settings.getCertificateFile().getValue();
//        if (file.isFile() && file.canRead()) {
//            readCertificate(file);
//        }
    }

    public String getAuthenticationEndpoint() {
        return authenticationEndpoint;
    }

    public String getUsername() {
        return username == null
                ? (this.certificate == null ? null : this.certificate.getProperty(ICertificate.KEY_USERNAME))
                : username;
    }

    public String getEmail() {
        return email == null ? (this.certificate == null ? null : this.certificate.getProperty(ICertificate.KEY_EMAIL)) : email;
    }

    public DateTime getExpiration() {
        return this.certificate == null ? null : this.certificate.getExpiryDate();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public Status getStatus() {
        return status;
    }

    public List<HubNotificationMessage> getMessages() {
        return messages;
    }

    public void readCertificate(File file) {
        this.certificate = new KlabCertificate(file);
//        ControlCenter.INSTANCE.message("Attempting to authenticate...");
        authenticate();
//        ControlCenter.INSTANCE.setupAuthenticationUI();
//        ControlCenter.INSTANCE.message(null);
    }

    private void authenticate() {

        this.authenticationEndpoint = this.certificate.getProperties().getProperty(ICertificate.KEY_PARTNER_HUB,
                DEFAULT_AUTHENTICATION_ENDPOINT);

        EngineAuthenticationRequest request = new EngineAuthenticationRequest(
                certificate.getProperty(KlabCertificate.KEY_USERNAME), certificate.getProperty(KlabCertificate.KEY_SIGNATURE),
                certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
                certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), ICertificate.Level.USER);

        /*
         * worst-case scenario, using only info from physical certificate
         */
        status = certificate.isExpired() ? Status.EXPIRED : (certificate.isValid() ? Status.OFFLINE : Status.INVALID);

//        try {
//            HttpResponse<EngineAuthenticationResponse> httpResponse = Unirest
//                    .post(authenticationEndpoint + API.HUB.AUTHENTICATE_ENGINE).header("Content-Type", "application/json")
//                    .body(request).asObject(EngineAuthenticationResponse.class);
//
//            if (httpResponse.isSuccess()) {
//                EngineAuthenticationResponse response = httpResponse.getBody();
//
//                if (response != null) {
//                    AuthenticatedIdentity userData = response.getUserData();
//
//                    this.username = userData.getIdentity().getId();
//                    this.email = userData.getIdentity().getEmail();
//                    this.tokenExpiration = DateTime.parse(userData.getExpiry());
//                    this.authorization = userData.getToken();
//                    this.groups.clear();
//                    this.messages.clear();
//                    this.developer = false;
//                    response.getUserData().getGroups().forEach(g -> {
//                        this.groups.add(g);
//                        if (DEVELOPER_GROUP.equals(g.getId())) {
//                            this.developer = true;
//                        }
//                    });
//                    if (response.getMessages() != null) {
//                        response.getMessages().forEach(m -> this.messages.add(m));
//                    }
//                    Collections.sort(this.groups, new Comparator<Group>(){
//                        @Override
//                        public int compare(Group u1, Group u2) {
//                            return u1.getId().compareTo(u2.getId());
//                        }
//                    });
//
//                    status = this.getExpiration().isBefore(DateTime.now()) ? Status.EXPIRED : Status.VALID;
//                } else {
//                    ControlCenter.INSTANCE.message("Authentication error");
//                    this.status = Status.OFFLINE;
//                }
//            }
//        } catch (UnirestException e) {
//            // just leave the status as is
//            ControlCenter.INSTANCE.message("Connection error");
//        }

    }

    public String getAuthorization() {
        return authorization;
    }

    public void installCertificate(File file) {

//        /*
//         * take configured filename as the one to overwrite; if existing, make a backup in same
//         * directory
//         */
//        File certfile = this.settings.getCertificateFile().getValue();
//        if (certfile == null) {
//            certfile = new File(ControlCenter.INSTANCE.getWorkdir() + File.separator + "klab.cert");
//        }
//        if (certfile.exists()) {
//            File backup = new File(certfile + ".bak");
//            int i = 1;
//            while(backup.exists()) {
//                backup = new File(certfile + ".bak." + i++);
//            }
//            try {
//                FileUtils.copyFile(certfile, backup);
//            } catch (IOException e) {
//                // screw it
//            }
//        }
//
//        try {
//            FileUtils.copyFile(file, certfile);
//        } catch (IOException e) {
//            ControlCenter.INSTANCE.showErrorAlert("Unexpected error copying certificate file", false);
//        }
    }
}

package org.integratedmodelling.controlcenter.auth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.integratedmodelling.controlcenter.ControlCenter;
import org.integratedmodelling.controlcenter.api.IAuthentication;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.joda.time.DateTime;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public class Authentication implements IAuthentication {

	private static final String DEFAULT_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/hub";

	Status status = Status.ANONYMOUS;
	String authenticationEndpoint;
	String username = "anonymous";
	String email = "";
	DateTime expiration;
	List<HubNotificationMessage> messages = new ArrayList<HubNotificationMessage>();
	List<Group> groups = new ArrayList<Group>();

	private KlabCertificate certificate;

	private String authorization;

	public Authentication() {

		File file = ControlCenter.INSTANCE.getSettings().getCertificateFile();
		if (file.isFile() && file.canRead()) {
			readCertificate(file);
		}
	}

	@Override
	public String getAuthenticationEndpoint() {
		return authenticationEndpoint;
	}

	@Override
	public String getUsername() {
		return username == null
				? (this.certificate == null ? null : this.certificate.getProperty(ICertificate.KEY_USERNAME))
				: username;
	}

	@Override
	public String getEmail() {
		return email == null ? (this.certificate == null ? null : this.certificate.getProperty(ICertificate.KEY_EMAIL))
				: email;
	}

	@Override
	public DateTime getExpiration() {
		return expiration == null ? (this.certificate == null ? null : this.certificate.getExpiryDate()) : expiration;
	}

	@Override
	public List<Group> getGroups() {
		return groups;
	}

	@Override
	public Status getStatus() {
		return status;
	}

    @Override
    public List<HubNotificationMessage> getMessages() {
        return messages;
    }

	public void readCertificate(File file) {
		this.certificate = new KlabCertificate(file);
		ControlCenter.INSTANCE.message("Attempting to authenticate...");
		authenticate();
		ControlCenter.INSTANCE.setupAuthenticationUI();
		ControlCenter.INSTANCE.message(null);
	}

	private void authenticate() {

		this.authenticationEndpoint = this.certificate.getProperties().getProperty(ICertificate.KEY_PARTNER_HUB,
				DEFAULT_AUTHENTICATION_ENDPOINT);

		EngineAuthenticationRequest request = new EngineAuthenticationRequest(
				certificate.getProperty(KlabCertificate.KEY_USERNAME),
				certificate.getProperty(KlabCertificate.KEY_SIGNATURE),
				certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
				certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), ICertificate.Level.USER);

		/*
		 * worst-case scenario, using only info from physical certificate
		 */
		status = certificate.isExpired() ? Status.EXPIRED : (certificate.isValid() ? Status.OFFLINE : Status.INVALID);

		try {

			HttpResponse<EngineAuthenticationResponse> httpResponse = Unirest.post(authenticationEndpoint + API.HUB.AUTHENTICATE_ENGINE)
					.header("Content-Type", "application/json").body(request).asObject(EngineAuthenticationResponse.class);
			
			if (httpResponse.isSuccess()) {
			    EngineAuthenticationResponse response = httpResponse.getBody();

	            if (response != null) {
	                AuthenticatedIdentity userData = response.getUserData();

                    this.username = userData.getIdentity().getId();
                    this.email =  userData.getIdentity().getEmail();
                    this.expiration = DateTime.parse(userData.getExpiry());
                    this.authorization = userData.getToken();
                    this.groups.clear();
                    this.messages.clear();
                    response.getUserData().getGroups().forEach(g -> this.groups.add(g));
                    
                    if (response.getMessages() != null) {
                        response.getMessages().forEach(m -> this.messages.add(m));
                    }
                    

                    status = this.expiration.isBefore(DateTime.now()) ? Status.EXPIRED : Status.VALID;
	            } else {
	                ControlCenter.INSTANCE.message("Authentication error");
	                this.status = Status.OFFLINE;
	            }
			}
		} catch (UnirestException e) {
			// just leave the status as is
			ControlCenter.INSTANCE.message("Connection error");
		}

	}

	public String getAuthorization() {
		return authorization;
	}

	public void installCertificate(File file) {
		
		/*
		 * take configured filename as the one to overwrite; if existing, make a backup in
		 * same directory
		 */
		File certfile = ControlCenter.INSTANCE.getSettings().getCertificateFile();
		if (certfile == null) {
			certfile = new File(ControlCenter.INSTANCE.getWorkdir() + File.separator + "klab.cert");
		}
		if (certfile.exists()) {
			File backup = new File(certfile + ".bak");
			int i = 1;
			while (backup.exists()) {
				backup = new File(certfile + ".bak." + i++);
			}
			try {
				FileUtils.copyFile(certfile, backup);
			} catch (IOException e) {
				// screw it
			}
		}
		
		try {
			FileUtils.copyFile(file, certfile);
		} catch (IOException e) {
			ControlCenter.INSTANCE.errorAlert("Unexpected error copying certificate file");
		}
	}

}

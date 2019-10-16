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
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.joda.time.DateTime;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;

public class Authentication implements IAuthentication {

	private static final String DEFAULT_AUTHENTICATION_ENDPOINT = "https://integratedmodelling.org/hub";

	Status status = Status.ANONYMOUS;
	String authenticationEndpoint;
	String username = "anonymous";
	String email = "";
	DateTime expiration;
	List<Group> groups = new ArrayList<>();

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
		 * worst-case scenario, using only info from pbysical certificate
		 */
		status = certificate.isExpired() ? Status.EXPIRED : (certificate.isValid() ? Status.OFFLINE : Status.INVALID);

		try {

			HttpResponse<JsonNode> httpResponse = Unirest.post(authenticationEndpoint + API.HUB.AUTHENTICATE_ENGINE)
					.header("Content-Type", "application/json").body(request).asJson();

			JSONObject response = httpResponse.getBody().getObject();

			if (response != null) {

				if (response.has("error")) {

					ControlCenter.INSTANCE.message("Authentication error");
					this.status = Status.OFFLINE;

				} else {

					this.username = response.getJSONObject("userData").getJSONObject("identity").getString("id");
					this.email = response.getJSONObject("userData").getJSONObject("identity").getString("email");
					this.expiration = DateTime.parse(response.getJSONObject("userData").getString("expiry"));
					this.authorization = response.getJSONObject("userData").getString("token");

					for (Object group : response.getJSONObject("userData").getJSONArray("groups")) {
						Group g = new Group();
						g.name = ((JSONObject)group).getString("id");
						g.iconUrl = ((JSONObject)group).has("iconUrl") ? ((JSONObject)group).getString("iconUrl") : null;
						g.description = g.name + " user group";
						this.groups.add(g);
					}

					status = this.expiration.isBefore(DateTime.now()) ? Status.EXPIRED : Status.VALID;
				}

			}

		} catch (UnirestException e) {
			// just leave the status as is
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

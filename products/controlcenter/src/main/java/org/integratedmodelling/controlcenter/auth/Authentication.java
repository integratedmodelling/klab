package org.integratedmodelling.controlcenter.auth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public List<Group> groups() {
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
						System.out.println("GROUP " + ((JSONObject) group).getString("id"));
					}

					status = this.expiration.isBefore(new DateTime()) ? Status.EXPIRED : Status.VALID;
				}

			}

		} catch (UnirestException e) {
			// just leave the status as is
		}

	}

}

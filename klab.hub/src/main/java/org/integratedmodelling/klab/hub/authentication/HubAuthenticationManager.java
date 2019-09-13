package org.integratedmodelling.klab.hub.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.joda.time.DateTime;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HubAuthenticationManager {

	//private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	//private static final String ENGINE_AUDIENCE = "engine";

	/**
	 * Authenticate an engine certificate. This may be a USER or a PARTNER
	 * certificate.
	 * 
	 * Initial simple strategy, good for testing: find all the certificates in the
	 * classpath and scan them, then index them by content and make them available.
	 * 
	 */
	private Map<String, KlabCertificate> engineCertificates = new HashMap<>();
	private Map<String, KlabCertificate> nodeCertificates = new HashMap<>();
	private Map<String, KlabCertificate> hubCertificates = new HashMap<>();

	// set after authentication
	HubReference hubReference;

	@Autowired
	NetworkManager networkManager;
	
	@Autowired
	GroupManager groupManager;

	String hubName;

	Client client = Client.create();
	private Partner partner;

	public HubAuthenticationManager() {

		for (String test : new Reflections(new ResourcesScanner()).getResources(Pattern.compile(".*\\.cert"))) {
			KlabCertificate certificate = KlabCertificate.createFromClasspath(test);
			if (certificate.isValid()) {
				if (certificate.getType() == ICertificate.Type.NODE) {
					nodeCertificates.put(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate);
				} else if (certificate.getType() == ICertificate.Type.ENGINE) {
					engineCertificates.put(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate);
				} else if (certificate.getType() == ICertificate.Type.HUB) {
					hubCertificates.put(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate);
				}
			}
		}
	}

	public HubReference getHubReference() {
		return this.hubReference;
	}

	public Partner getPartner() {
		return this.partner;
	}

	/**
	 * Read our own certificate and set the necessary permissions.
	 * 
	 * @param certificate
	 */
	public void authenticate(IHubStartupOptions options, ICertificate certificate) {

		this.hubName = options.getHubName() == null ? certificate.getProperty(ICertificate.KEY_HUBNAME)
				: options.getHubName();

		this.hubReference = new HubReference();
		this.hubReference.setId(this.hubName);
		this.hubReference.setOnline(true);
		this.hubReference.getUrls().add(certificate.getProperty(ICertificate.KEY_URL));

		IdentityReference partnerIdentity = new IdentityReference();
		partnerIdentity.setId(certificate.getProperty(ICertificate.KEY_PARTNER_NAME));
		partnerIdentity.setEmail(certificate.getProperty(ICertificate.KEY_PARTNER_EMAIL));
		partnerIdentity.setLastLogin(DateTime.now().toString());

		// TODO address and stuff

		this.partner = new Partner(partnerIdentity);
		this.hubReference.setPartner(partnerIdentity);

		if (certificate.getProperty(ICertificate.KEY_PARTNER_HUB) != null) {
			/*
			 * TODO if we have a parent hub, handshake so it can serve our nodes.
			 */
		}

		/*
		 * ensure we have a valid key pair for JWT signing
		 */
		if (NetworkKeyManager.INSTANCE.initialize(certificate.getProperty(ICertificate.KEY_SIGNATURE),
				this.hubReference.getId())) {
			// TODO a new certificate was generated, so any pre-issued tokens are invalid
		}
	}


	/**
	 * Our official name (from certificate or overridden in startup options).
	 * 
	 * @return this hub's name.
	 */
	public String getHubName() {
		return hubName;
	}

	/*
	 * TODO handshake protocol with other hubs. Should use a mutual SSL
	 * authentication with a certificate that is only known to each hub for mutual
	 * authentication.
	 */

}

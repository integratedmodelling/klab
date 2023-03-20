package org.integratedmodelling.klab.hub.authentication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.hub.config.LegacyLicenseConfig;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public enum HubAuthenticationManager {
	
	INSTANCE;

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

	String hubName = null;

	Client client = Client.create();
	
	LegacyLicenseConfig licenseConfig;

	private HubAuthenticationManager() {
		ConfigurationBuilder config = new ConfigurationBuilder()
	     .setUrls(ClasspathHelper.forPackage("org.integratedmodelling.klab.hub"))
	     .setScanners(new ResourcesScanner())
	     .filterInputsBy(new FilterBuilder().include((".*\\.cert")));
	     
		for (String test : new Reflections(config).getResources(Pattern.compile(".*\\.cert"))) {
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

	/**
	 * Read our own certificate and set the necessary permissions.
	 * 
	 * @param certificate
	 * @return 
	 */
	public IPartnerIdentity authenticate(IHubStartupOptions options, ICertificate certificate) {

		this.hubName = options.getHubName() == null ? certificate.getProperty(ICertificate.KEY_HUBNAME)
				: options.getHubName();
		
		IdentityReference partnerIdentity = new IdentityReference();
		partnerIdentity.setId(certificate.getProperty(ICertificate.KEY_PARTNER_NAME));
		partnerIdentity.setEmail(certificate.getProperty(ICertificate.KEY_PARTNER_EMAIL));
		partnerIdentity.setLastLogin(LocalDateTime.now().toString());
		IPartnerIdentity rootIdentity = new Partner(partnerIdentity);
		
		Authentication.INSTANCE.registerIdentity(rootIdentity);
		
		HubReference hubReference = new HubReference();
		hubReference.setId(this.hubName);
		hubReference.setOnline(true);
		hubReference.getUrls().add(certificate.getProperty(ICertificate.KEY_URL));

		hubReference.setPartner(partnerIdentity);
		
		Hub hub = new Hub(hubReference);
		
		Authentication.INSTANCE.registerIdentity(hub);

		if (certificate.getProperty(ICertificate.KEY_PARTNER_HUB) != null) {
			/*
			 * TODO if we have a parent hub, handshake so it can serve our nodes.
			 */
		}

		/*
		 * ensure we have a valid key pair for JWT signing
		 */
		if (NetworkKeyManager.INSTANCE.initialize(certificate.getProperty(ICertificate.KEY_SIGNATURE),
				hubReference.getId())) {
			// TODO a new certificate was generated, so any pre-issued tokens are invalid
		}
		
		return rootIdentity;
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
	
	
	public KlabCertificate checkLocalEngineCertificates(String engineCert) {
		KlabCertificate certificate;
		certificate = hubCertificates.get(engineCert);
		return certificate;
	}

	public KlabCertificate checkLocalNodeCertificates(String nodeCert) {
		KlabCertificate certificate;
		certificate = hubCertificates.get(nodeCert);
		return certificate;
	}

	public KlabCertificate checkLocalHubCertificates(String hubCert) {
		KlabCertificate certificate;
		certificate = hubCertificates.get(hubCert);
		return certificate;
	}
	
	public LegacyLicenseConfig getLicenseConfig() {
		return licenseConfig;
	}


}

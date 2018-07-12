package org.integratedmodelling.klab.hub.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.security.KeyManager;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.HubReference;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationManager {

	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String ENGINE_AUDIENCE = "engine";

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
	private Set<Group> groups = new HashSet<>();

	// set after authentication
	HubReference hubReference;

	@Autowired
	NetworkManager networkManager;

	String hubName;

	/*
	 * for legacy authentication calls
	 */
	private static final String LEGACY_AUTHENTICATION_SERVICE = "https://integratedmodelling.org/collaboration/authentication/cert-file";
	private static final int EXPIRATION_DAYS = 10;
	Client client = Client.create();
	private Partner partner;

	public AuthenticationManager() {

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

		/*
		 * TODO load all groups into group set
		 */
	}

	/**
	 * TODO may want to compile a record per each engine IP and pass it so that the
	 * user's parent may be set.
	 * 
	 * @param cert
	 * @return
	 */
	public IUserIdentity authenticatePreinstalledEngineCertificate(String cert, String ip) {
		KlabCertificate certificate = engineCertificates.get(cert);
		if (certificate != null) {
			if (!IPUtils.isLocal(ip)) {
				throw new KlabAuthorizationException(
						"pre-installed engine certificates are only allowed on local connections");
			}
			EngineUser ret = new EngineUser(certificate.getProperty(KlabCertificate.KEY_USERNAME), null);
			ret.setEmailAddress(certificate.getProperty(KlabCertificate.KEY_EMAIL));
			return ret;
		}
		throw new KlabAuthorizationException();
	}

	/**
	 * Use the legacy server to authenticate a previously issued certificate.
	 * 
	 * @param request
	 * @return
	 */
	public EngineUser authenticateLegacyEngineCertificate(EngineAuthenticationRequest request, String ip) {

		try {
			HttpEntity<String> httpRequest = new HttpEntity<String>(request.getCertificate());
			Map<?, ?> response = new RestTemplate().postForObject(LEGACY_AUTHENTICATION_SERVICE, httpRequest,
					Map.class);
			if (response != null) {
				Map<?, ?> profile = (Map<?, ?>) response.get("profile");
				EngineUser ret = new EngineUser(response.get("username").toString(), null);
				ret.setEmailAddress(profile.get("email").toString());
				// ret.getGroups().addAll()
				Logging.INSTANCE
						.info("authenticated user " + response.get("username") + " through legacy certificate service");
				return ret;
			}
		} catch (Throwable t) {
			throw new KlabAuthorizationException("legacy authentication failed: " + t.getMessage());
		}
		throw new KlabAuthorizationException("legacy authentication failed");
	}

	/**
	 * Authenticate a certificate that wasn't preinstalled.
	 * 
	 * @param cert
	 * @param level
	 * @param string
	 * @return
	 */
	public EngineUser authenticateEngineCertificate(EngineAuthenticationRequest request, String ip) {

		switch (request.getLevel()) {
		case ANONYMOUS:
		case TEST:
			if (IPUtils.isLocal(ip)) {
				EngineUser ret = new EngineUser(request.getUsername(), null);
				ret.setEmailAddress(request.getEmail());
				Logging.INSTANCE.info("authenticated local user " + request.getUsername());
				return ret;
			}
			break;
		case LEGACY:
			return authenticateLegacyEngineCertificate(request, ip);
		case USER:
		case INSTITUTIONAL:
			// TODO use JWT authentication
			break;
		default:
			break;

		}
		return null;
	}

	/**
	 * Node authentication. Every node that wants to be used must authenticate to a
	 * hub. The hub retrieves all capabilities and status and sends it to the
	 * network manager, which installs monitors for the node and decides how to
	 * offer the node to authorized engines.
	 * 
	 * @param request
	 * 
	 * @param cert
	 * @return
	 */
	public INodeIdentity authenticateNodeCertificate(NodeAuthenticationRequest request, String ip) {

		INodeIdentity ret = null;
		KlabCertificate certificate = nodeCertificates.get(request.getCertificate());
		if (certificate != null) {
			if (!IPUtils.isLocal(ip)) {
				throw new KlabAuthorizationException(
						"pre-installed node certificates are only allowed on local connections");
			}

			if (networkManager.getNode(request.getNodeName()) != null) {
				throw new KlabAuthorizationException("node names must be unique: a node named "
						+ request.getCertificate() + " was already authorized");
			}

			ret = new Node(request.getNodeName(), this.partner);
			ret.getUrls().add(certificate.getProperty(ICertificate.KEY_URL));
			return ret;
		}

		/*
		 * TODO proceed with regular authentication
		 */

		throw new KlabAuthorizationException();
	}

	public HubReference getHubReference() {
		return this.hubReference;
	}

	public Partner getPartner() {
		return this.partner;
	}

	/**
	 * Read our own certificate and set the necessary permissions
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

		if (certificate.getProperty(ICertificate.KEY_SERVER) != null) {
			/*
			 * TODO if we have a parent hub, handshake and link its nodes in the network
			 * manager.
			 */
		}

		/*
		 * ensure we have a valid key pair for JWT signing
		 */
		if (KeyManager.INSTANCE.initialize(certificate.getProperty(ICertificate.KEY_SIGNATURE),
				this.hubReference.getId())) {
			// TODO a new certificate was generated, so any pre-issued tokens are invalid
		}
	}

	/**
	 * Establish the user identity as a JWT token which our nodes can consume with
	 * our advertised public key.
	 * 
	 * @param user
	 * @return the user identity with local credentials
	 */
	public EngineUser authorizeUser(EngineUser user) throws KlabAuthorizationException {

		JwtClaims claims = new JwtClaims();

		claims.setIssuer(hubReference.getId());
		claims.setSubject(user.getUsername());
		claims.setAudience(ENGINE_AUDIENCE);

		claims.setIssuedAtToNow();
		claims.setExpirationTimeMinutesInTheFuture(60 * 24 * EXPIRATION_DAYS);
		claims.setGeneratedJwtId();

		// TODO add email
		List<String> roleStrings = new ArrayList<>();
		for (String role : user.getGroups()) {
			roleStrings.add(role);
		}
		claims.setStringListClaim(JWT_CLAIM_KEY_PERMISSIONS, roleStrings);

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(KeyManager.INSTANCE.getPrivateKey());
		// jws.setKeyIdHeaderValue(webSecurityConfig.getJwtPrivateKeyId());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		String token;
		try {
			token = jws.getCompactSerialization();
		} catch (JoseException e) {
			token = null;
			Logging.INSTANCE
					.error(String.format("Failed to generate JWT token string for user '%s': ", user.getUsername()), e);
		}

		user.setToken(token);
		return user;
	}

	/**
	 * All groups known to this hub.
	 * 
	 * @return
	 */
	public Collection<Group> getGroups() {
		return groups;
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

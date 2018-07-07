package org.integratedmodelling.klab.hub.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.utils.IPUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationManager {

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

	@Autowired
	NetworkManager networkManager;

	/*
	 * for legacy authentication calls
	 */
	private static final String LEGACY_AUTHENTICATION_SERVICE = "https://integratedmodelling.org/collaboration/authentication/cert-file";
	Client client = Client.create();

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
	public IUserIdentity authenticateLegacyEngineCertificate(EngineAuthenticationRequest request, String ip) {

		try {
			HttpEntity<String> httpRequest = new HttpEntity<String>(request.getCertificate());
			Map<?, ?> response = new RestTemplate().postForObject(LEGACY_AUTHENTICATION_SERVICE, httpRequest,
					Map.class);
			if (response != null) {
				Map<?, ?> profile = (Map<?, ?>) response.get("profile");
				EngineUser ret = new EngineUser(response.get("username").toString(), null);
				ret.setEmailAddress(profile.get("email").toString());
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
	public IUserIdentity authenticateEngineCertificate(EngineAuthenticationRequest request, String ip) {

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
	public INodeIdentity authenticateNodeCertificate(EngineAuthenticationRequest request, String ip) {

		INodeIdentity ret = null;
		KlabCertificate certificate = nodeCertificates.get(request.getCertificate());
		if (certificate != null) {
			if (!IPUtils.isLocal(ip)) {
				throw new KlabAuthorizationException(
						"pre-installed node certificates are only allowed on local connections");
			}
			Partner partner = new Partner(certificate.getProperty(KlabCertificate.KEY_PARTNER_NAME)); // TODO
			partner.setEmailAddress(certificate.getProperty(KlabCertificate.KEY_PARTNER_EMAIL));
			ret = new Node(certificate.getProperty(KlabCertificate.KEY_NODENAME), partner);
			networkManager.notifyAuthorizedNode(ret);
			return ret;
		}

		/*
		 * TODO proceed with regular authentication
		 */

		throw new KlabAuthorizationException();
	}

	/*
	 * TODO handshake protocol with other hubs. Should use a mutual SSL
	 * authentication with a certificate that is only known to each hub for mutual
	 * authentication.
	 */

}

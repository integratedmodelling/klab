package org.integratedmodelling.klab.hub.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManager {

	/**
	 * Authenticate an engine certificate. This may be a USER or a PARTNER
	 * certificate.
	 * 
	 * Initial simple strategy, good for testing: find all the certificates in the
	 * classpath and scan them, then index them by content and make them available.
	 * 
	 * TODO move this to test packages/dev configuration
	 * 
	 * TODO create proxying authenticator for legacy certificates
	 * 
	 * TODO use true auth for production
	 */
	private Map<String, KlabCertificate> engineCertificates = new HashMap<>();
	private Map<String, KlabCertificate> nodeCertificates = new HashMap<>();

	/*
	 * for proxying calls
	 */
	private static final String LEGACY_AUTHENTICATION_SERVICE = "http://integratedmodelling.org/collaboration/authentication/cert-file";
	Client client = Client.create();

	public AuthenticationManager() {

		for (String test : new Reflections(new ResourcesScanner()).getResources(Pattern.compile(".*\\.cert"))) {
			KlabCertificate certificate = KlabCertificate.createFromClasspath(test);
			if (certificate.isValid()) {
				if (certificate.getProperty(KlabCertificate.KEY_CERTIFICATE).equals("NODE")) {
					nodeCertificates.put(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate);
				} else {
					engineCertificates.put(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate);
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
	public IUserIdentity authenticatePreinstalledEngineCertificate(String cert) {
		KlabCertificate certificate = engineCertificates.get(cert);
		if (certificate != null) {
			EngineUser ret = new EngineUser(certificate.getProperty(KlabCertificate.KEY_USERNAME), null);
			ret.setEmailAddress(certificate.getProperty(KlabCertificate.KEY_EMAIL));
			return ret;
		}
		throw new KlabAuthorizationException();
	}

	/**
	 * Use the legacy server to authenticate a previously issued certificate.
	 * 
	 * @param cert
	 * @return
	 */
	public IUserIdentity authenticateLegacyEngineCertificate(String cert) {
		// TODO
		return null;
	}

	/**
	 * Authenticate a 0.10.0 certificate.
	 * 
	 * @param cert
	 * @return
	 */
	public IUserIdentity authenticateEngineCertificate(String cert) {
		// TODO
		return null;
	}

	/**
	 * Node authentication. Every node that wants to be used must authenticate to a
	 * hub. The hub retrieves all capabilities and status and sends it to the
	 * network manager, which installs monitors for the node and decides how to
	 * offer the node to authorized engines.
	 * 
	 * @param cert
	 * @return
	 */
	public INodeIdentity authenticateNodeCertificate(String cert) {

		KlabCertificate certificate = nodeCertificates.get(cert);
		if (certificate != null) {

		}
		throw new KlabAuthorizationException();

		// TODO
		// else if (KlabCertificate.CERTIFICATE_TYPE_NODE
		// .equals(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE))) {
		//
		// /*
		// * FIXME - this is obsolete and won't happen - left for future porting to node
		// * codebase
		// */
		// Partner partner = new
		// Partner(certificate.getProperty(KlabCertificate.KEY_PARTNER_NAME)); // TODO
		// partner.setEmailAddress(certificate.getProperty(KlabCertificate.KEY_PARTNER_EMAIL));
		//
		// ret = new Node(certificate.getProperty(KlabCertificate.KEY_NODENAME),
		// partner);
		// /**
		// * TODO add authenticated data
		// */
		// ((Node) ret).getUrls().add(certificate.getProperty(KlabCertificate.KEY_URL));
		//
		// }
	}

	/*
	 * TODO handshake protocol with other hubs. Should use a mutual SSL
	 * authentication with a certificate that is only known to each hub for mutual
	 * authentication.
	 */

}

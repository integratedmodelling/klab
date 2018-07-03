package org.integratedmodelling.klab;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IAuthenticationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.auth.NetworkSession;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.Session.Listener;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.rest.AuthenticationRequest;
import org.integratedmodelling.klab.rest.AuthenticationResponse;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.joda.time.DateTime;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public enum Auth implements IAuthenticationService {

	/**
	 * The global instance singleton.
	 */
	INSTANCE;

	/**
	 * Local catalog of all partner identities registered from the network.
	 * 
	 */
	Map<String, Partner> partners = Collections.synchronizedMap(new HashMap<>());

	/**
	 * All identities available for inspection (sessions, users). The network
	 * session is a singleton (or a zeroton) so it's not included as its ID
	 * conflicts with the user holding it.
	 */
	Map<String, IIdentity> identities = Collections.synchronizedMap(new HashMap<>());

	/**
	 * Status of a user wrt. the network. Starts at UNKNOWN.
	 */
	public enum Status {
		/**
		 * User is authenticated locally but not online, either for lack of
		 * authentication or lack of network connection/
		 */
		OFFLINE,
		/**
		 * User is authenticated and online with the network.
		 */
		ONLINE,
		/**
		 * User has not been submitted for authentication yet.
		 */
		UNKNOWN
	}

	/**
	 * ID for an anonymous user. Unsurprising.
	 */
	public static final String ANONYMOUS_USER_ID = "anonymous";

	/**
	 * Id for anonymous user who is connecting from the local host. Will have admin
	 * privileges.
	 */
	public static final String LOCAL_USER_ID = "local";

	/**
	 * If the partner mentioned in the response bean is already known, return it,
	 * otherwise create it.
	 * 
	 * @param partner
	 * @return a partner identity for the passed description
	 */
	public Partner requirePartner(IdentityReference partner) {
		Partner ret = partners.get(partner.getId());
		if (ret == null) {
			ret = new Partner(partner);
			partners.put(partner.getId(), ret);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IIdentity> T getIdentity(String id, Class<T> type) {
		// TODO Auto-generated method stub
		IIdentity ret = identities.get(id);
		return ret != null && type.isAssignableFrom(ret.getClass()) ? (T) ret : null;
	}

	/**
	 * Register a new session and inject a close listener that will de-register it
	 * on expiration.
	 * 
	 * @param session
	 */
	public void registerSession(Session session) {

		session.addListener(new Listener() {
			@Override
			public void onClose(ISession session) {
				identities.remove(session.getId());
			}
		});
		identities.put(session.getId(), session);
	}

	/**
	 * Register any new identity with this.
	 * 
	 * @param identity
	 */
	public void registerIdentity(IIdentity identity) {
		identities.put(identity.getId(), identity);
	}

	/**
	 * Util to extract an identity from the principal of a Spring request. TODO make
	 * another to return a specific type or throw an authorization exception
	 * 
	 * @param principal
	 * @return the identity or null
	 */
	public IIdentity getIdentity(Principal principal) {
		if (principal instanceof PreAuthenticatedAuthenticationToken
				&& ((PreAuthenticatedAuthenticationToken) principal).getPrincipal() instanceof IIdentity) {
			return (IIdentity) ((PreAuthenticatedAuthenticationToken) principal).getPrincipal();
		}
		return null;
	}

	@Override
	public IIdentity authenticate(ICertificate certificate) {

		IIdentity ret = null;

		Logging.INSTANCE.info("authenticating " + certificate.getProperty(KlabCertificate.KEY_USERNAME));

		String authenticationServer = certificate.getProperty(KlabCertificate.KEY_SERVER);
		if (authenticationServer == null) {
			Logging.INSTANCE.warn("certificate has no hub address: trying a local hub");
			// try local hub, let fail if not active
			authenticationServer = "http://127.0.0.1:8284/klab";
		}

		/*
		 * Authenticate with server(s). If authentication fails because of a 403,
		 * invalidate the certificate. If no server can be reached, certificate is valid
		 * but engine is offline.
		 */
		AuthenticationRequest request = new AuthenticationRequest(
				certificate.getProperty(KlabCertificate.KEY_USERNAME),
				certificate.getProperty(KlabCertificate.KEY_SIGNATURE),
				certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
				certificate.getProperty(KlabCertificate.KEY_CERTIFICATE));

		AuthenticationResponse authentication = null;

		try {
			authentication = Client.create().authenticate(authenticationServer, request);
		} catch (Throwable e) {
			Logging.INSTANCE.error("authentication failed for user "
					+ certificate.getProperty(KlabCertificate.KEY_USERNAME) + ": " + e.getMessage());
			// TODO inspect exception; fatal if 403, warn and proceed offline otherwise
		}

		if (authentication != null) {

			DateTime expiry = null;
			/*
			 * check expiration
			 */
			try {
				expiry = DateTime.parse(authentication.getUserData().getExpiry());
			} catch (Throwable e) {
				Logging.INSTANCE
						.error("bad date or wrong date format in certificate. Please use latest version of software.");
				return null;
			}
			if (expiry == null) {
				Logging.INSTANCE.error("certificate has no expiration date. Please obtain a new certificate.");
				return null;
			} else if (expiry.isBeforeNow()) {
				Logging.INSTANCE.error("certificate expired on " + expiry + ". Please obtain a new certificate.");
				return null;
			}
		} else {

			/*
			 * user is offline
			 */
			Logging.INSTANCE.error("authentication unsuccessful for "
					+ certificate.getProperty(KlabCertificate.KEY_USERNAME) + ": continuing offline");

		}

		/*
		 * build the identity
		 */
		if (KlabCertificate.CERTIFICATE_TYPE_USER
				.equals(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE))) {

			// TODO if we have connected, insert network session identity
			if (authentication != null) {

				NodeReference partnerNode = null;
				for (NodeReference n : authentication.getNodes()) {
					if (n.getId().equals(authentication.getAuthenticatingNodeId())) {
						partnerNode = n;
						break;
					}
				}
				Partner partner = Auth.INSTANCE.requirePartner(partnerNode.getPartner());
				Node node = new Node(partnerNode, partner);
				node.setOnline(true);
				NetworkSession networkSession = new NetworkSession(authentication.getUserData().getToken(),
						authentication.getNodes(), node);

				ret = new KlabUser(authentication.getUserData(), networkSession);

			} else {

				// offline node with no partner
				Node node = new Node(certificate.getProperty(KlabCertificate.KEY_NODENAME), null);
				((Node) node).setOnline(false);
				ret = new KlabUser(certificate.getProperty(KlabCertificate.KEY_USERNAME), node);
			}

			((KlabUser) ret).setOnline(authentication != null);

		} else if (KlabCertificate.CERTIFICATE_TYPE_NODE
				.equals(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE))) {

			/*
			 * FIXME - this is obsolete and won't happen - left for future porting to node codebase
			 */
			Partner partner = new Partner(certificate.getProperty(KlabCertificate.KEY_PARTNER_NAME)); // TODO
			partner.setEmailAddress(certificate.getProperty(KlabCertificate.KEY_PARTNER_EMAIL));

			ret = new Node(certificate.getProperty(KlabCertificate.KEY_NODENAME), partner);
			/**
			 * TODO add authenticated data
			 */
			((Node) ret).getUrls().add(certificate.getProperty(KlabCertificate.KEY_URL));

		} else {
			throw new KlabUnsupportedFeatureException(
					"cannot create identity of type " + certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE));
		}
		
		Network.INSTANCE.buildNetwork(authentication);

		return ret;

	}

}

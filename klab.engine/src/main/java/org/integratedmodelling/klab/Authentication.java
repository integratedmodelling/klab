package org.integratedmodelling.klab;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.ICertificate.Type;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IAuthenticationService;
import org.integratedmodelling.klab.auth.AnonymousEngineCertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.auth.NetworkSession;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.Session.Listener;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeReference;
import org.joda.time.DateTime;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public enum Authentication implements IAuthenticationService {

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

    private Client client = Client.create();

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
        IIdentity ret = identities.get(id);
        if (ret != null && type.isAssignableFrom(ret.getClass())) {
            return (T) ret;
        }
        return null;
    }

    @Override
    public <T extends IIdentity> T getAuthenticatedIdentity(Class<T> type) {
        return Klab.INSTANCE.getRootMonitor().getIdentity().getParentIdentity(type);
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
    public IIdentity authenticate(ICertificate certificate) throws KlabAuthorizationException {

        IIdentity ret = null;
        EngineAuthenticationResponse authentication = null;

        if (certificate instanceof AnonymousEngineCertificate) {
            // no partner, no node, no token, no nothing. REST calls automatically accept
            // the
            // anonymous user when secured as Roles.PUBLIC.
            return new KlabUser(Authentication.ANONYMOUS_USER_ID, null);
        }

        String authenticationServer = certificate.getProperty(KlabCertificate.KEY_SERVER);

        if (authenticationServer == null) {
            Logging.INSTANCE.warn("certificate has no hub address");
            // try local hub, let fail if not active
            if (client.ping("http://127.0.0.1:8284/klab")) {
                authenticationServer = "http://127.0.0.1:8284/klab";
                Logging.INSTANCE.info("local hub is available: trying local authentication");
            } else {
                Logging.INSTANCE.warn("local hub unavailable: continuing offline");
            }
        }

        if (authenticationServer != null) {

            Logging.INSTANCE.info("authenticating " + certificate.getProperty(KlabCertificate.KEY_USERNAME)
                    + " with hub " + authenticationServer);

            /*
             * Authenticate with server(s). If authentication fails because of a 403,
             * invalidate the certificate. If no server can be reached, certificate is valid
             * but engine is offline.
             */
            EngineAuthenticationRequest request = new EngineAuthenticationRequest(
                    certificate.getProperty(KlabCertificate.KEY_USERNAME),
                    certificate.getProperty(KlabCertificate.KEY_SIGNATURE),
                    certificate.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE),
                    certificate.getProperty(KlabCertificate.KEY_CERTIFICATE), certificate.getLevel());

            // add email if we have it, so the hub can notify in any case if so configured
            request.setEmail(certificate.getProperty(KlabCertificate.KEY_USERNAME));

            try {
                authentication = client.authenticateEngine(authenticationServer, request);
            } catch (Throwable e) {
                Logging.INSTANCE.error("authentication failed for user "
                        + certificate.getProperty(KlabCertificate.KEY_USERNAME) + ": " + e.getMessage());
                // TODO inspect exception; fatal if 403, warn and proceed offline otherwise
            }
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
        }

        /*
         * build the identity
         */
        if (certificate.getType() == Type.ENGINE) {

            // if we have connected, insert network session identity
            if (authentication != null) {

                NodeReference hubNode = authentication.getHub();
                Partner partner = Authentication.INSTANCE.requirePartner(hubNode.getPartner());
                Node node = new Node(hubNode, partner);
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

        } else {
            throw new KlabAuthorizationException(
                    "wrong certificate for an engine: cannot create identity of type " + certificate.getType());
        }

        Network.INSTANCE.buildNetwork(authentication);

        return ret;

    }

}

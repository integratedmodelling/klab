package org.integratedmodelling.klab;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IAuthenticationService;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.Session.Listener;
import org.integratedmodelling.klab.rest.IdentityReference;
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
     * All identities available for inspection (sessions, users). The network session is
     * a singleton (or a zeroton) so it's not included as its ID conflicts with the user
     * holding it.
     */
    Map<String, IIdentity> identities = Collections.synchronizedMap(new HashMap<>());

    /**
     * Status of a user wrt. the network. Starts at UNKNOWN.
     */
    public enum Status {
        /**
         * User is authenticated locally but not online, either for lack of authentication or lack of network
         * connection/
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
    public static final String ANONYMOUS_USER_ID    = "anonymous";
    
    /**
     * Id for anonymous user who is connecting from the local host. Will have
     * admin privileges.
     */
    public static final String LOCAL_USER_ID    = "local";

    /**
     * If the partner mentioned in the response bean is already known, return it, otherwise
     * create it.
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
        return ret != null && type.isAssignableFrom(ret.getClass()) ? (T)ret : null;
    }
    
    /**
     * Register a new session and inject a close listener that will de-register it on
     * expiration.
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
     * Util to extract an identity from the principal of a Spring request.
     * TODO make another to return a specific type or throw an authorization exception
     * @param principal
     * @return the identity or null
     */
    public IIdentity getIdentity(Principal principal) {
        if (principal instanceof PreAuthenticatedAuthenticationToken && ((PreAuthenticatedAuthenticationToken)principal).getPrincipal() instanceof IIdentity) {
            return (IIdentity)((PreAuthenticatedAuthenticationToken)principal).getPrincipal();
        }
        return null;
    }
    
}

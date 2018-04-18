package org.integratedmodelling.klab;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.services.IAuthenticationService;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.data.rest.resources.IdentityReference;

public enum Auth implements IAuthenticationService {
    
    INSTANCE;

    /**
     * Local catalog of all partner identities registered from the network.
     * 
     */
    Map<String, Partner> partners = new HashMap<>();
    
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

    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

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
}

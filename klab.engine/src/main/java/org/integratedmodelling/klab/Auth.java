package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IAuthenticationService;

public enum Auth implements IAuthenticationService {
    INSTANCE;

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


    public String getKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getKeyring() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }
}

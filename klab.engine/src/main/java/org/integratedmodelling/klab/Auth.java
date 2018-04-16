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

    /*
     * Keys for user properties in certificates or for set operations.
     */
    public static final String REALNAME             = "realname";
    public static final String FIRSTNAME            = "firstname";
    public static final String EMAIL                = "email";
    public static final String LASTNAME             = "lastname";
    public static final String INITIALS             = "initials";
    public static final String PHONE                = "phone";
    public static final String ADDRESS              = "address";
    public static final String AFFILIATION          = "affiliation";
    public static final String JOBTITLE             = "jobtitle";
    public static final String COMMENTS             = "comments";
    public static final String SKEY                 = "key";
    public static final String USER                 = "user";
    public static final String ROLES                = "roles";
    public static final String GROUPS               = "groups";
    public static final String MODULES              = "modules";
    public static final String EXPIRY               = "expiry";
    public static final String SERVER               = "primary.server";

    public static final String ROLE_ADMINISTRATOR   = "ADMIN";
    public static final String GROUP_ADMINISTRATORS = "ADMINISTRATORS";
    public static final String GROUP_DEVELOPERS     = "DEVELOPERS";
    public static final String GROUP_IM             = "IM";

    // URL of pubring resource in classpath
    public static final String PUBRING_RESOURCE          = "ssh/pubring.gpg";

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

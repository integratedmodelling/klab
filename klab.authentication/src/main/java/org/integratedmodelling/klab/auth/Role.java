package org.integratedmodelling.klab.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    // needs to match cn={camelCaseRoleName},ou=groups,dc=integratedmodelling,dc=org in LDAP,
    // where the values here are UPPER_CASE_ROLE_NAME
    ROLE_ADMINISTRATOR,
    ROLE_DATA_MANAGER,
    ROLE_MANAGER,
    ROLE_USER,

    ROLE_CLICKBACK,
    ROLE_ENGINE,
    ROLE_SYSTEM,
    ROLE_TEMPORARY,
    ROLE_LEVER,;

    public static final String ADMINISTRATOR = "ROLE_ADMINISTRATOR";

    public static final String DATA_MANAGER = "ROLE_DATA_MANAGER";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String USER = "ROLE_USER";

    public static final String CLICKBACK = "ROLE_CLICKBACK";

    public static final String ENGINE = "ROLE_ENGINE";

    public static final String SYSTEM = "ROLE_SYSTEM";
    
    public static final String HUB = "ROLE_HUB";
    
    public static final String LEVER = "ROLE_LEVER";

    // no privileges - only used for fake/temporary auth during LDAP operations
    public static final String TEMPORARY = "ROLE_TEMPORARY";

    @Override
    public String getAuthority() {
        return name();
    }

    public String plainName() {
        return getAuthority().replaceFirst("ROLE_", "");
    }
}

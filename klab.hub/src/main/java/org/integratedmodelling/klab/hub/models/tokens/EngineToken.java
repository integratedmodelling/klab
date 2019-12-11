package org.integratedmodelling.klab.hub.models.tokens;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.integratedmodelling.klab.hub.users.Role;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;

public class EngineToken extends AuthenticationToken {

    private static final long serialVersionUID = -4433591204500903916L;

    private static final int TOKEN_TTL_SECONDS = 60 * 60 * 24; // 24 hours (reboot k.LAB or have a retry hook to refresh)

    private static final Collection<GrantedAuthority> ROLES = new ArrayList<GrantedAuthority>(
            Arrays.asList(Role.ROLE_ENGINE));

    public EngineToken(String username) {
        super(username);
        expiration = DateTime.now().plusSeconds(TOKEN_TTL_SECONDS);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ROLES;
    }

    @Override
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    }
}
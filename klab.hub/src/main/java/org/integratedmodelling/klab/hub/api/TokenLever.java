package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.integratedmodelling.klab.auth.Role;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.security.core.GrantedAuthority;

@TypeAlias("LeverToken")
public class TokenLever extends TokenAuthentication{

	private static final long serialVersionUID = -7438294770687877720L;
	
	private static final int TOKEN_TTL_SECONDS = 60 * 60 * 24; // 24 hours (reboot k.LAB or have a retry hook to refresh)
	
    private static final Collection<GrantedAuthority> ROLES = new ArrayList<GrantedAuthority>(
            Arrays.asList(Role.ROLE_LEVER));
	
	
    public TokenLever(String name) {
        super(name);
        //expiration = DateTime.now().plusSeconds(TOKEN_TTL_SECONDS);
    }
}

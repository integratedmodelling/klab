package org.integratedmodelling.klab.engine.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service gets the output of {@link PreauthenticationFilter} (unless null)
 * and turns it into the correspondent user details.
 * 
 * @author Ferd
 *
 */
@Service
public class PreauthenticatedUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<String> roles = new ArrayList<>();
        roles.add(Roles.PUBLIC);

        boolean authorize = false;

        if (Auth.ANONYMOUS_USER_ID.equals(username)) {
            authorize = true;
        } else if (Auth.LOCAL_USER_ID.equals(username)) {
            authorize = true;
            roles.add(Roles.ADMIN);
        } else {
            IIdentity identity = Auth.INSTANCE.getIdentity(username, IIdentity.class);
            // known k.LAB identities are UserDetails and have established roles
            if (identity instanceof UserDetails) {
                return (UserDetails) identity;
            } else if (identity != null) {
                throw new KlabInternalErrorException(
                        "internal error: non-conformant identity in Auth catalog! " + identity);
            }
        }

        if (!authorize) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        
        // anonymous or local user
        return new KlabUser(username, NameGenerator.newName(), authorities);
    }

}

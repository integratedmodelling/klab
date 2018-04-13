package org.integratedmodelling.klab.engine.rest.security;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.auth.UserIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    UserIdentity user = null; // userDao.getUser(username);
    if (user == null) {// should have proper handling of Exception
        throw new UsernameNotFoundException("User '" + username + "' not found.");
    }
    
    List<GrantedAuthority> roles = new ArrayList<>();
    for (String role : user.getRoles()) {
      roles.add(new SimpleGrantedAuthority(role));
    }
    UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), roles);
    return details;
  }

}

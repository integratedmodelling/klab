package org.integratedmodelling.klab.auth;

import java.util.Collection;
import java.util.Set;
import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class UserIdentity implements IUserIdentity, UserDetails {

  private static final long serialVersionUID = -5670348187596399293L;

  protected String username;

  public UserIdentity(String username) {
    this.username = username;
  }

  @Override
  public boolean isAnonymous() {
    return username.equals(Auth.ANONYMOUS_USER_ID);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends IIdentity> T getParentIdentity(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public Set<String> getGroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserIdentity other = (UserIdentity) obj;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }
  

}

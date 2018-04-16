package org.integratedmodelling.klab.auth;

import java.util.Date;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.springframework.security.core.userdetails.UserDetails;

public class PartnerIdentity extends UserIdentity implements IPartnerIdentity, UserDetails {

  private static final long serialVersionUID = -129699145554376751L;

  public PartnerIdentity(String partnerName) {
    super(partnerName);
  }

  @Override
  public IIdentity getParentIdentity() {
    // only legitimate null.
    return null;
  }

  @Override
  public String getServerURL() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getEmailAddress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getFirstName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLastName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getInitials() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getAffiliation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getComment() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Date getLastLogin() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean is(Type type) {
    return type == IPartnerIdentity.TYPE;
  }

}

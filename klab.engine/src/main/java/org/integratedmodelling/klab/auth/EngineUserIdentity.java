package org.integratedmodelling.klab.auth;

import java.util.Date;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;

public class EngineUserIdentity extends UserIdentity implements IEngineUserIdentity {

  private static final long serialVersionUID = -134196454400472128L;
  private IEngineIdentity parent;
  
  public EngineUserIdentity(String username, IEngineIdentity parent) {
    super(username);
    this.parent = parent;
  }
  
  /**
   * Create the default engine user from the engine's owner.
   * 
   * @param engine an engine identity. Must have an owner.
   * @return the default engine user for the passed owner and engine
   */
  public static EngineUserIdentity promote(IEngineIdentity engine) {
    KlabUserIdentity owner = engine.getParentIdentity(KlabUserIdentity.class);
    if (owner == null) {
      throw new IllegalArgumentException("engine does not have an owner: cannot create default engine user");
    }
    return null;
  }

  @Override
  public boolean isAnonymous() {
    // TODO Auto-generated method stub
    return false;
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
  public boolean isEngineOwner() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IEngineIdentity getParentIdentity() {
    return parent;
  }

  @Override
  public boolean is(Type type) {
    return type == IEngineUserIdentity.TYPE;
  }

}

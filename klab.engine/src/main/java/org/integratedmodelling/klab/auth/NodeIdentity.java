package org.integratedmodelling.klab.auth;

import java.util.Date;
import java.util.Set;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class NodeIdentity implements INodeIdentity {

  String name;
  IPartnerIdentity parent;

  public NodeIdentity(String name, IPartnerIdentity owner) {
    this.name = name;
    this.parent = owner;
  }
  
  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Date getBootTime() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMonitor getMonitor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean is(Type type) {
    return type == INodeIdentity.TYPE;
  }

  @Override
  public <T extends IIdentity> T getParentIdentity(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public IPartnerIdentity getParentIdentity() {
    return parent;
  }

  @Override
  public boolean isOnline() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Set<Permission> getPermissions() {
    // TODO Auto-generated method stub
    return null;
  }

}

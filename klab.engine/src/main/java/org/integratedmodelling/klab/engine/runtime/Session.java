package org.integratedmodelling.klab.engine.runtime;

import java.io.IOException;
import java.util.concurrent.Future;

import org.integratedmodelling.kim.utils.CollectionUtils;
import org.integratedmodelling.kim.utils.NameGenerator;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Observer;

public class Session implements ISession {

  Monitor monitor;
  String  token = "s" + NameGenerator.shortUUID();
  IEngineUserIdentity user;

  public Session(Engine engine, IEngineUserIdentity user) {
    this.user = user;
    this.monitor = ((Monitor) engine.getMonitor()).get(this);
  }

  @Override
  public String getId() {
    return token;
  }

  @Override
  public boolean is(Type type) {
    return type == Type.MODEL_SESSION;
  }

  @Override
  public <T extends IIdentity> T getParentIdentity(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public IEngineUserIdentity getParentIdentity() {
    return user;
  }

  @Override
  public Monitor getMonitor() {
    return monitor;
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub
  }

  @Override
  public Future<ISubject> observe(String urn, String... scenarios) throws KlabException {
    // urn must specify observer
    IKimObject object = Resources.INSTANCE.getModelObject(urn);
    if (!(object instanceof Observer)) {
      throw new KlabContextualizationException("URN " + urn + " does not specify an observation");
    }
    return new ObserveContextTask(this, (Observer) object, CollectionUtils.arrayToList(scenarios));
  }

  public String toString() { 
    // TODO add user
    return "<session " + getId() + ">";
  }
  
}

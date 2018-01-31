package org.integratedmodelling.klab.engine.runtime;

import java.io.IOException;
import java.util.concurrent.Future;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NameGenerator;

public class Session implements ISession {

  Monitor monitor;
  String  token = NameGenerator.newName("s");
  Engine  engine;

  public Session(Engine engine, IEngineUserIdentity user) {
    this.engine = engine;
    this.monitor = ((Monitor) engine.getMonitor()).get(this);
  }

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public boolean is(Type type) {
    return type == Type.MODEL_SESSION;
  }

  @Override
  public <T extends IIdentity> T getParent(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public IEngineIdentity getParentIdentity() {
    return engine;
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
  public Future<ISubject> observe(String urn) throws KlabException {
    // urn must specify observer
    IKimObject object = Resources.INSTANCE.getModelObject(urn);
    if (!(object instanceof IObserver)) {
      throw new KlabContextualizationException("URN " + urn + " does not specify an observation");
    }
    return new ObserveContextTask(this, (IObserver) object);
  }

}

package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;

public enum Observations implements IObservationService {

  INSTANCE;

  @Override
  public IDataflow<ISubject> resolve(String urn, ISession session, String[] scenarios) {
    return null;
  }

  @Override
  public IDataflow<IObservation> resolve(String urn, IDirectObservation context,
      String[] scenarios) {
    return null;
  }

  @Override
  public void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException {
    // TODO remove all artifacts from local kbox
  }

  @Override
  public void index(IObserver observer, IMonitor monitor) throws KlabException {
    // TODO
  }

  /*
   * Non-API - sync namespace. TODO check equivalent in Models.
   */
  public void registerNamespace(Namespace ns, Monitor monitor) {
    // TODO Auto-generated method stub

  }
}

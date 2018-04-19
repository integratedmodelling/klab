package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.scale.Scale;

public enum Observations implements IObservationService {

  INSTANCE;

  @Override
  public IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios)
      throws KlabException {
    return Resolver.INSTANCE.resolve(urn, session, scenarios);
  }

  @Override
  public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios)
      throws KlabException {
    return Resolver.INSTANCE.resolve(urn, context, scenarios);
  }

  @Override
  public void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException {
    // TODO remove all artifacts from local kbox
  }

  @Override
  public void index(IObserver observer, IMonitor monitor) throws KlabException {
    // TODO
  }
  
  @Override
  public IState getStateView(IState state, IScale scale, IComputationContext context) {
      return new RescalingState(state, (Scale)scale, (IRuntimeContext)context);
  }

  /*
   * Non-API - sync namespace. TODO check equivalent in Models.
   */
  public void registerNamespace(Namespace ns, Monitor monitor) {
    // TODO Auto-generated method stub

  }
}

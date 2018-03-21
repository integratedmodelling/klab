package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import java.util.Optional;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.Path;

public abstract class Observation extends ObservationData implements IObservation {

  private static final long      serialVersionUID = -7645502752899232235L;

  private Observable             observable;
  private Subject                observer;
  private Namespace              namespace;

  private IEngineSessionIdentity parentIdentity;

  public String getUrn() {
    return "local:observation:" + getParentIdentity(Session.class).getToken() + ":" + getToken();
  }

  protected Observation(Observable observable, Scale scale, RuntimeContext context) {
    super(scale, context);
    this.observable = observable;
    this.parentIdentity =
        context.getMonitor().getIdentity().getParentIdentity(IEngineSessionIdentity.class);
  }

  @Override
  public Optional<ISubject> getObserver() {
    return observer == null ? Optional.empty() : Optional.of(observer);
  }

  @Override
  public Observable getObservable() {
    return observable;
  }

  @Override
  public Scale getScale() {
    return (Scale) getGeometry();
  }

  @Override
  public boolean isSpatiallyDistributed() {
    return getScale().isSpatiallyDistributed();
  }

  @Override
  public boolean isTemporallyDistributed() {
    return getScale().isTemporallyDistributed();
  }

  @Override
  public boolean isTemporal() {
    return getScale().getTime() != null;
  }

  @Override
  public boolean isSpatial() {
    return getScale().getSpace() != null;
  }

  @Override
  public Space getSpace() {
    return getScale().getSpace();
  }

  @Override
  public IEngineSessionIdentity getParentIdentity() {
    return parentIdentity;
  }

  @Override
  public Monitor getMonitor() {
    return (Monitor) getRuntimeContext().getMonitor();
  }

  @Override
  public String getToken() {
    return super.getId();
  }

  @Override
  public boolean is(Type type) {
    return type == Type.OBSERVATION;
  }

  @Override
  public <T extends IIdentity> T getParentIdentity(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public IProvenance getProvenance() {
    return getRuntimeContext().getProvenance();
  }

  public void setObservable(Observable observable) {
    this.observable = observable;
  }

  public void setObserver(Subject observer) {
    this.observer = observer;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public void setNamespace(INamespace namespace) {
    this.namespace = (Namespace) namespace;
  }

  public void setContext(DirectObservation context) {
    // TODO Auto-generated method stub
  }

  @Override
  public DirectObservation getContext() {
    return (DirectObservation)super.getParent();
  }
  
  // Provenance (from IArtifact's contract)

  @Override
  public IAgent getConsumer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IAgent getOwner() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getAntecedents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> getConsequents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact trace(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> collect(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact trace(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getTimestamp() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }
  
  public String toString() {
    return "{" + Path.getLast(this.getClass().getCanonicalName(), '.') + " " + getToken() + ": "
        + getObservable() + "}";
  }

  public Observation next() {
    return (Observation)super.next();
  }
  

}

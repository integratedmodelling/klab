package org.integratedmodelling.klab.observation;

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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Path;

public abstract class Observation implements IObservation {

  private static final long      serialVersionUID = -7645502752899232235L;

  private Scale                  scale;
  private Observable             observable;
  private String                 token            = "o" + NameGenerator.shortUUID();
  private Subject                observer;
  private DirectObservation      contextObservation;
  private Monitor                monitor;
  private Namespace              namespace;
  
  private IEngineSessionIdentity parentIdentity;

  public String getUrn() {
    return "local:observation:" + getParent(Session.class).getToken() + ":" + getToken();
  }
  
  protected Observation(Observable observable, Scale scale, IMonitor monitor) {
    this.observable = observable;
    this.scale = scale;
    this.monitor = ((Monitor) monitor).get(this);
    this.parentIdentity = monitor.getIdentity().getParent(IEngineSessionIdentity.class);
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
    return scale;
  }

  @Override
  public boolean isSpatiallyDistributed() {
    return scale.isSpatiallyDistributed();
  }

  @Override
  public boolean isTemporallyDistributed() {
    return scale.isTemporallyDistributed();
  }

  @Override
  public boolean isTemporal() {
    return scale.getTime() != null;
  }

  @Override
  public boolean isSpatial() {
    return scale.getSpace() != null;
  }

  @Override
  public Space getSpace() {
    return scale.getSpace();
  }

  @Override
  public IEngineSessionIdentity getParentIdentity() {
    return parentIdentity;
  }

  @Override
  public Monitor getMonitor() {
    return monitor;
  }

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public boolean is(Type type) {
    return type == Type.OBSERVATION;
  }

  @Override
  public <T extends IIdentity> T getParent(Class<T> type) {
    return IIdentity.findParent(this, type);
  }

  @Override
  public DirectObservation getContext() {
    return contextObservation;
  }

  @Override
  public Subject getRoot() {
    if (contextObservation == null) {
      if (this instanceof Subject) {
        return (Subject) this;
      }
    }
    return contextObservation == null ? null : contextObservation.getRoot();
  }

  @Override
  public IProvenance getProvenance() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void explore() {
    // TODO Auto-generated method stub
  }

  public String getId() {
    return token;
  }

  public void setId(String id) {
    this.token = id;
  }

  public DirectObservation getContextObservation() {
    return contextObservation;
  }

  public void setContextObservation(DirectObservation contextObservation) {
    this.contextObservation = contextObservation;
  }

  public void setScale(Scale scale) {
    this.scale = scale;
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
  

  @Override
  public boolean hasNext() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IObservation next() {
    // TODO Auto-generated method stub
    return null;
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
    return "{" + Path.getLast(this.getClass().getCanonicalName(), '.') + " " + getToken() +  ": " + getObservable() + "}";
  }

}

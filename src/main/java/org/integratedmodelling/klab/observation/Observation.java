package org.integratedmodelling.klab.observation;

import java.util.Optional;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.NameGenerator;

public abstract class Observation implements IObservation {

  private static final long serialVersionUID = -7645502752899232235L;

  private Scale             scale;
  private Observable        observable;
  private String            id               = NameGenerator.shortUUID();
  private Subject           observer;
  private DirectObservation contextObservation;

  protected Observation(Observable observable, Scale scale) {
    this.observable = observable;
    this.scale = scale;
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
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Space getSpace() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IEngineSessionIdentity getParentIdentity() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Monitor getMonitor() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getToken() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean is(Type type) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
    // TODO Auto-generated method stub
    return null;
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

}

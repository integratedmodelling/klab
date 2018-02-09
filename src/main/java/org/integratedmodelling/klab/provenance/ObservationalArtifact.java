package org.integratedmodelling.klab.provenance;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;

public abstract class ObservationalArtifact<T extends IObservation> implements IArtifact<T> {

  T observation;
  
  public ObservationalArtifact(T observation) {
    this.observation = observation;
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

  @Override
  public boolean hasNext() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T next() {
    // TODO Auto-generated method stub
    return null;
  }

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
  public Collection<IArtifact<?>> getAntecedents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact<?>> getConsequents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact<?> trace(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact<?>> collect(IConcept concept) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IArtifact<?> trace(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IArtifact<?>> collect(IConcept role, IDirectObservation roleContext) {
    // TODO Auto-generated method stub
    return null;
  }

}

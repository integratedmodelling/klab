package org.integratedmodelling.klab.observation;

import java.util.Collection;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.ObservationalArtifact;

public class Event extends CountableObservation implements IEvent {

  private Event(String name, Observable observable, Scale scale, IMonitor monitor) {
    super(name, observable, scale, monitor);
    provenanceDelegate = new ObservationalArtifact<Event>(this) {
      @Override
      public IProvenance getProvenance() {
        return getContextObservation().getRoot().getRuntimeContext().getProvenance();
      }
    };
  }

  private static final long serialVersionUID = -5518029878668042674L;

  public static Event create(String name, IObservable observable, IScale scale, ISubject context) {
    return null;
  }

  ObservationalArtifact<Event> provenanceDelegate;
  
  public long getTimestamp() {
    return provenanceDelegate.getTimestamp();
  }

  public IProvenance getProvenance() {
    return provenanceDelegate.getProvenance();
  }

  public boolean isEmpty() {
    return provenanceDelegate.isEmpty();
  }

  public boolean hasNext() {
    return provenanceDelegate.hasNext();
  }

  public Event next() {
    return provenanceDelegate.next();
  }

  public IAgent getConsumer() {
    return provenanceDelegate.getConsumer();
  }

  public IAgent getOwner() {
    return provenanceDelegate.getOwner();
  }

  public int hashCode() {
    return provenanceDelegate.hashCode();
  }

  public Collection<IArtifact<?>> getAntecedents() {
    return provenanceDelegate.getAntecedents();
  }

  public Collection<IArtifact<?>> getConsequents() {
    return provenanceDelegate.getConsequents();
  }

  public IArtifact<?> trace(IConcept concept) {
    return provenanceDelegate.trace(concept);
  }

  public Collection<IArtifact<?>> collect(IConcept concept) {
    return provenanceDelegate.collect(concept);
  }

  public IArtifact<?> trace(IConcept role, IDirectObservation roleContext) {
    return provenanceDelegate.trace(role, roleContext);
  }

  public Collection<IArtifact<?>> collect(IConcept role, IDirectObservation roleContext) {
    return provenanceDelegate.collect(role, roleContext);
  }

  public boolean equals(Object obj) {
    return provenanceDelegate.equals(obj);
  }

  public String toString() {
    return provenanceDelegate.toString();
  }

}

package org.integratedmodelling.klab.observation;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.ObservationalArtifact;

public class State extends Observation implements IState {

    private State(Observable observable, Scale scale, IMonitor monitor) {
        super(observable, scale, monitor);
        provenanceDelegate = new ObservationalArtifact<State>(this) {
          @Override
          public IProvenance getProvenance() {
            return getContextObservation().getRoot().getRuntimeContext().getProvenance();
          }
        };
    }

    private static final long serialVersionUID = -7075415960868285693L;
    
    public static State create(String name, IObservable observable, ISubject context, IMonitor monitor) {
        return null;
    }
    
    @Override
    public IStorage<?> getStorage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getValueCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getValue(Locator locator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConstant() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDynamic() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public State as(IObservable observable) {
        // TODO Auto-generated method stub
        return null;
    }
    

    ObservationalArtifact<State> provenanceDelegate;
    
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

    public State next() {
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

package org.integratedmodelling.klab.observation;

import java.util.Collection;
import java.util.Map;
import org.integratedmodelling.kim.utils.CollectionUtils;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IIndividual;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.ObserveInContextTask;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.ObservationalArtifact;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.RuntimeContext;

public class Subject extends CountableObservation implements ISubject {

  private RuntimeContext runtimeContext;

  private Subject(String name, Observable observable, Scale scale, IMonitor monitor) {
    super(name, observable, scale, monitor);
    provenanceDelegate = new ObservationalArtifact<Subject>(this) {
      @Override
      public IProvenance getProvenance() {
        return getContextObservation().getRoot().getRuntimeContext().getProvenance();
      }
    };
  }

  private static final long serialVersionUID = 2466999232658613114L;

  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @param monitor 
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale, IMonitor monitor) {
    return new Subject(name, observable, scale, monitor);
  }
  
  /**
   * 
   * @param name
   * @param observable
   * @param scale
   * @param context
   * @param monitor 
   * @return
   */
  public static Subject create(String name, Observable observable, Scale scale, Subject context, IMonitor monitor) {
    Subject ret = new Subject(name, observable, scale, monitor);
    ret.setContextObservation(context);
    return ret;
  }

  @Override
  public Collection<IEvent> getEvents() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IProcess> getProcesses() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<ISubject> getSubjects() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getRelationships() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getIncomingRelationships(ISubject subject) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<IRelationship> getOutgoingRelationships(ISubject subject) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<IConcept, IConfiguration> getConfigurations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IIndividual instantiate(IOntology ontology) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ITask<IObservation> observe(String urn, String... scenarios) {
    return new ObserveInContextTask(this, urn, CollectionUtils.arrayToList(scenarios));
  }

  /**
   * Return the runtime context where all the 'live' bookkeeping is kept.
   * 
   * @return
   */
  public RuntimeContext getRuntimeContext() {
    return runtimeContext;
  }

  /**
   * Each call to getResolutionScope returns a new scope for this subject.
   * 
   * @param scenarios
   * @return
   */
  public ResolutionScope getResolutionScope(String... scenarios) {
    return new ResolutionScope(this, scenarios);
  }
  

  ObservationalArtifact<Subject> provenanceDelegate;
  
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

  public Subject next() {
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

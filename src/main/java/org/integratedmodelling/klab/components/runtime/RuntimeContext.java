package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IStorageProvider;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IRuntimeContext;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.observation.Relationship;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.provenance.Provenance;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * A runtime context is installed in the root subject to keep track of what happens during
 * contextualization.
 * 
 * TODO Agent graphs, schedules etc should be here.
 * 
 * @author ferdinando.villa
 *
 */
public class RuntimeContext implements IRuntimeContext {

  Subject                              subject;
  Provenance                           provenance;
  EventBus                             eventBus;
  ConfigurationDetector                configurationDetector;
  IStorageProvider                     storageProvider;
  IRuntimeProvider                     runtimeProvider;
  DirectedGraph<ISubject, IRelationship> structure;

  /**
   * Create a runtime context for a computation that hasn't yet defined
   * a context subject. 
   */
  public RuntimeContext() {
    this.storageProvider = Klab.INSTANCE.getStorageProvider();
    this.runtimeProvider = Klab.INSTANCE.getRuntimeProvider();
    this.structure = new DefaultDirectedGraph<>(Relationship.class);
  }
  
  /**
   * Set the root subject for the context, initializing the provenance and
   * the 
   * @param subject
   */
  public void setRootSubject(ISubject subject) {
    ((Subject)subject).setRuntimeContext(this);
    this.subject = (Subject) subject;
    this.eventBus = new EventBus((Subject) subject);
    this.configurationDetector = new ConfigurationDetector((Subject) subject, structure);
    this.provenance = new Provenance((Subject) subject);
    this.structure.addVertex(subject);
  }

  @Override
  public IProvenance getProvenance() {
    return provenance;
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }

  @Override
  public ConfigurationDetector getConfigurationDetector() {
    return configurationDetector;
  }

  @Override
  public Collection<IRelationship> getOutgoingRelationships(ISubject observation) {
    return structure.outgoingEdgesOf(observation);
  }

  @Override
  public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
    return structure.incomingEdgesOf(observation);
  }

  @Override
  public Collection<ISubject> getAllSubjects() {
    return structure.vertexSet();
  }

  @Override
  public ISubject getRoot() {
    return subject;
  }
  
  public void exportStructure(String outFile) {
    // TODO export a GEFX file
  }

}

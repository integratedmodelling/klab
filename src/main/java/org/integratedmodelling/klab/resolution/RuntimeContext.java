package org.integratedmodelling.klab.resolution;

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

  public RuntimeContext(Subject subject) {
    this.subject = subject;
    this.storageProvider = Klab.INSTANCE.getStorageProvider();
    this.runtimeProvider = Klab.INSTANCE.getRuntimeProvider();
    this.eventBus = new EventBus(subject);
    this.structure = new DefaultDirectedGraph<>(Relationship.class);
    this.configurationDetector = new ConfigurationDetector(subject, structure);
    this.provenance = new Provenance(subject);
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

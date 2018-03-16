package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.observation.Relationship;
import org.integratedmodelling.klab.observation.Subject;
import org.integratedmodelling.klab.provenance.Provenance;
import org.jgrapht.Graph;
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

  Namespace namespace;
  IObjectData subject;
  Provenance provenance;
  EventBus eventBus;
  ConfigurationDetector configurationDetector;
  Graph<ISubject, IRelationship> structure = new DefaultDirectedGraph<>(Relationship.class);
  Map<String, IObservationData> catalog = new HashMap<>();

  public RuntimeContext() {}

  private RuntimeContext(RuntimeContext context) {
    this.namespace = context.namespace;
    this.subject = context.subject;
    this.provenance = context.provenance;
    this.eventBus = context.eventBus;
    this.configurationDetector = context.configurationDetector;
    this.structure = context.structure;
    this.catalog.putAll(context.catalog);
  }

  /**
   * Set the root subject for the context, initializing the provenance and the
   * 
   * @param subject
   */
  public void setRootSubject(ISubject subject) {
    ((Subject) subject).setRuntimeContext(this);
    this.subject = subject.getData();
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
  public IObjectData getSubjectData() {
    return subject;
  }

  /*
   * TODO this should be API but not the public API - an internal extended API for the engine. Same
   * for recontextualizing methods
   */
  public void exportStructure(String outFile) {
    // TODO export a GEFX file
  }

  @Override
  public INamespace getNamespace() {
    // TODO Auto-generated method stub
    return namespace;
  }

  @Override
  public Collection<IObservable> getKnownObservables() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IObservationData get(String localName) {
    return catalog.get(localName);
  }

  @Override
  public IRuntimeContext copy() {
    return new RuntimeContext(this);
  }

  @Override
  public void rename(String name, String alias) {
    IObservationData obj = catalog.get(name);
    if (obj != null) {
      catalog.remove(name);
      catalog.put(alias, obj);
    }
  }

  @Override
  public void set(String name, IObservationData data) {
    catalog.put(name, data);
  }

}

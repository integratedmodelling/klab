package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
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
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.utils.Pair;
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
  Map<String, Object> data = new HashMap<>();
    
  public RuntimeContext() {}

  RuntimeContext(RuntimeContext context) {
    this.namespace = context.namespace;
    this.subject = context.subject;
    this.provenance = context.provenance;
    this.eventBus = context.eventBus;
    this.configurationDetector = context.configurationDetector;
    this.structure = context.structure;
    this.catalog.putAll(context.catalog);
    this.data.putAll(context.data);
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
  public IObservationData getData(String localName) {
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
  public void setData(String name, IObservationData data) {
    catalog.put(name, data);
  }

  /*
   * return all states that must be localized when a IStateContextualizer is run.
   */
  public Collection<Pair<String, IStorage<?>>> getStateDependentData() {
    List<Pair<String, IStorage<?>>> ret = new ArrayList<>();
    for (String var : catalog.keySet()) {
      if (catalog.get(var) instanceof IStorage<?>) {
        ret.add(new Pair<>(var, (IStorage<?>)catalog.get(var)));
      }
    }
    return ret;
  }

  @Override
  public <T> T get(String name, T object) {
    // TODO transform to T if compatible and needed
    return data.containsKey(name) ? (T)data.get(name) : object;
  }

  @Override
  public <T> T get(String name, Class<? extends T> cls) {
    // TODO transform to T if compatible and needed
    return (T)data.get(name);
  }

  public void set(String name, Object value) {
    data.put(name, value);
  }

}

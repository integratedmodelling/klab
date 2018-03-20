package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.api.data.raw.IObjectData;
import org.integratedmodelling.klab.api.data.raw.IObservationData;
import org.integratedmodelling.klab.api.data.raw.IStorage;
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
public class RuntimeContext extends Parameters implements IRuntimeContext {

  Namespace namespace;
  IObjectData subject;
  Provenance provenance;
  EventBus eventBus;
  ConfigurationDetector configurationDetector;
  Graph<ISubject, IRelationship> structure = new DefaultDirectedGraph<>(Relationship.class);
  Map<String, IObservationData> catalog = new HashMap<>();
    
  public RuntimeContext() {}

  RuntimeContext(RuntimeContext context) {
    this.putAll(context);
    this.namespace = context.namespace;
    this.subject = context.subject;
    this.provenance = context.provenance;
    this.eventBus = context.eventBus;
    this.configurationDetector = context.configurationDetector;
    this.structure = context.structure;
    this.catalog.putAll(context.catalog);
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

  @Override
  public void exportStructure(String outFile) {
    // TODO export a GEFX file
  }

  @Override
  public INamespace getNamespace() {
    return namespace;
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

  public void set(String name, Object value) {
    this.put(name, value);
  }

}

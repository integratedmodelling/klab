package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.api.data.raw.IDataArtifact;
import org.integratedmodelling.klab.api.data.raw.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.Relationship;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.utils.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

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

  INamespace                           namespace;
  IArtifact                     target;
  Provenance                           provenance;
  EventBus                             eventBus;
  ConfigurationDetector                configurationDetector;
  Graph<ISubject, IRelationship>       network;
  Graph<IArtifact, DefaultEdge> structure;
  Map<String, IArtifact>        catalog = new HashMap<>();
  IMonitor                             monitor;
  RuntimeContext                       parent;

  public RuntimeContext(IObservable observable, IScale scale, INamespace namespace,
      IMonitor monitor) {
    this.network = new DefaultDirectedGraph<>(Relationship.class);
    this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
    this.provenance = /* TODO new Provenance() */ null;
    this.monitor = monitor;
    this.namespace = namespace;
    this.target = DefaultRuntimeProvider.createObservation(observable, scale, this, null);
    this.structure.addVertex(this.target);
    // TODO provenance (may need to pass the actuator)
    if (this.target instanceof ISubject) {
      this.network.addVertex((ISubject) this.target);
    }
  }

  RuntimeContext(RuntimeContext context) {
    this.putAll(context);
    this.namespace = context.namespace;
    this.provenance = context.provenance;
    this.eventBus = context.eventBus;
    this.configurationDetector = context.configurationDetector;
    this.network = context.network;
    this.structure = context.structure;
    this.monitor = context.monitor;
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
    return network.outgoingEdgesOf(observation);
  }

  @Override
  public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
    return network.incomingEdgesOf(observation);
  }

  @Override
  public Collection<ISubject> getAllSubjects() {
    return network.vertexSet();
  }

  @Override
  public void exportNetwork(String outFile) {
    // TODO export a GEFX file
  }

  @Override
  public INamespace getNamespace() {
    return namespace;
  }

  @Override
  public IArtifact getData(String localName) {
    return catalog.get(localName);
  }

  @Override
  public IRuntimeContext copy() {
    return new RuntimeContext(this);
  }

  @Override
  public void rename(String name, String alias) {
    IArtifact obj = catalog.get(name);
    if (obj != null) {
      catalog.remove(name);
      catalog.put(alias, obj);
    }
  }

  @Override
  public void setData(String name, IArtifact data) {
    catalog.put(name, data);
  }

  /*
   * return all states that must be localized when a IStateContextualizer is run.
   */
  public Collection<Pair<String, IDataArtifact>> getStateDependentData() {
    List<Pair<String, IDataArtifact>> ret = new ArrayList<>();
    for (String var : catalog.keySet()) {
      if (catalog.get(var) instanceof IDataArtifact) {
        ret.add(new Pair<>(var, (IDataArtifact) catalog.get(var)));
      }
    }
    return ret;
  }

  public void set(String name, Object value) {
    this.put(name, value);
  }

  @Override
  public IArtifact getTarget() {
    return target;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  @Override
  public ICountableObservation newObservation(IObservable observable, IGeometry geometry) {
    // TODO Auto-generated method stub
    // TODO plain objects if scale has no time, Akka actors otherwise
    return null;
  }

  @Override
  public IRelationship newRelationship(IObservable observable, IGeometry geometry,
      IObjectArtifact source, IObjectArtifact target) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IRuntimeContext createChild(IObservable target, INamespace namespace) {
    RuntimeContext ret = new RuntimeContext(this);
    ret.parent = this;
    ret.namespace = namespace;
    if (!(this.target instanceof DirectObservation)) {
      throw new IllegalArgumentException(
          "RuntimeContext: cannot add a child observation to a non-direct observation");
    }
    ret.target = DefaultRuntimeProvider.createObservation(target,
        ((Observation) this.target).getScale(), this, (DirectObservation) this.target);
    this.structure.addVertex(ret.target);
    // create child->parent edge
    this.structure.addEdge(ret.target, this.target);
    // TODO provenance (may need to pass the actuator)
    if (ret.target instanceof ISubject) {
      this.network.addVertex((ISubject) ret.target);
    }
    return ret;
  }
  
  /**
   * Return all the children of an artifact in the structural graph that match a certain class.
   * 
   * @param artifact
   * @param cls
   * @return the set of all children of class cls
   */
  @SuppressWarnings("unchecked")
  public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
    List<T> ret = new ArrayList<>();
    for (DefaultEdge edge : this.structure.incomingEdgesOf(artifact)) {
      IArtifact source = this.structure.getEdgeSource(edge);
      if (cls.isAssignableFrom(source.getClass())) {
        ret.add((T)source);
      }
    }
    return ret;
  }

  @Override
  public IRuntimeContext createChild(IObservation target, INamespace namespace) {
    RuntimeContext ret = new RuntimeContext(this);
    ret.parent = this;
    ret.namespace = namespace;
    ret.target = (Observation) target;
    return ret;
  }

}

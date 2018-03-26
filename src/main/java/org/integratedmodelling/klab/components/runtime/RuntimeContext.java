package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.utils.Parameters;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Relationship;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
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

  INamespace namespace;
  Provenance provenance;
  EventBus eventBus;
  ConfigurationDetector configurationDetector;
  Graph<ISubject, IRelationship> network;
  Graph<IArtifact, DefaultEdge> structure;
  Map<String, IArtifact> catalog;
  IMonitor monitor;
  RuntimeContext parent;
  IArtifact target;
  IScale scale;
  IKimConcept.Type artifactType;
  Set<String> inputs;
  Set<String> outputs;
  Map<String, IObservable> semantics;

  // root scope of the entire dataflow, unchanging, for downstream resolutions
  ResolutionScope resolutionScope;

  public RuntimeContext(Actuator actuator, IResolutionScope scope, IScale scale, IMonitor monitor) {

    this.catalog = new HashMap<>();
    this.network = new DefaultDirectedGraph<>(Relationship.class);
    this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
    this.provenance = /* TODO new Provenance() */ null;
    this.monitor = monitor;
    this.namespace = actuator.getNamespace();
    this.scale = scale;
    this.target = DefaultRuntimeProvider.createObservation(actuator, this);
    this.catalog.put(actuator.getObservable().getLocalName(), target);
    this.structure.addVertex(target);
    this.artifactType = Observables.INSTANCE.getObservableType(actuator.getObservable());
    
    // store and set up for further resolutions
    this.resolutionScope = (ResolutionScope) scope;
    if (this.target instanceof IDirectObservation) {
      this.resolutionScope.setContext((IDirectObservation)this.target);
    }
    
    this.inputs = new HashSet<>();
    this.outputs = new HashSet<>();
    this.semantics = new HashMap<>();

    if (!actuator.getObservable().is(Type.COUNTABLE)) {
      this.outputs.add(actuator.getName());
      this.semantics.put(actuator.getName(), actuator.getObservable());
    }
    for (IActuator a : actuator.getActuators()) {
      if (!((Actuator) a).isExported()) {
        String id = a.getAlias() == null ? a.getName() : a.getAlias();
        this.inputs.add(id);
        this.semantics.put(id, ((Actuator) a).getObservable());
      }
    }
    for (IActuator a : actuator.getOutputs()) {
      String id = a.getAlias() == null ? a.getName() : a.getAlias();
      this.outputs.add(id);
      this.semantics.put(id, ((Actuator) a).getObservable());
    }

    // TODO provenance (may need to pass the actuator)
    if (target instanceof ISubject) {
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
    this.catalog = context.catalog;
    this.scale = context.scale;
    this.artifactType = context.artifactType;
    this.inputs = context.inputs;
    this.outputs = context.outputs;
    this.semantics = context.semantics;
    this.parent = context.parent;
    this.resolutionScope = context.resolutionScope;
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
  public void exportNetwork(String outFile) {
    // TODO export a GEFX file
  }

  @Override
  public INamespace getNamespace() {
    return namespace;
  }

  @Override
  public IArtifact getTargetArtifact() {
    return target;
  }

  @Override
  public IArtifact getArtifact(String localName) {
    return catalog.get(localName);
  }

  @Override
  public IRuntimeContext copy() {
    RuntimeContext ret = new RuntimeContext(this);
    // make a deep copy of all localizable info so we can rename elements
    ret.catalog = new HashMap<>(this.catalog);
    ret.semantics = new HashMap<>(this.semantics);
    ret.inputs = new HashSet<>(this.inputs);
    ret.outputs = new HashSet<>(this.outputs);
    return ret;
  }

  @Override
  public void rename(String name, String alias) {
    IArtifact obj = catalog.get(name);
    if (obj != null) {
      catalog.remove(name);
      catalog.put(alias, obj);
      if (inputs.contains(name)) {
        inputs.remove(name);
        inputs.add(alias);
      }
      if (outputs.contains(name)) {
        outputs.remove(name);
        outputs.add(alias);
      }
      IObservable obs = semantics.remove(name);
      if (obs != null) {
        semantics.put(alias, obs);
      }
    }
  }

  @Override
  public void setData(String name, IArtifact data) {
    catalog.put(name, data);
  }

  public void set(String name, Object value) {
    this.put(name, value);
  }

  @Override
  public IArtifact getTargetArtifact(IActuator actuator) {
    IArtifact ret = catalog.get(actuator.getName());

    /**
     * If we don't have a target and the actuator needs storage (i.e. it's a quality or anything
     * that must be instantiated automatically), create the storage but do not add it to the
     * provenance and structure.
     */
    if (ret == null && !((Actuator) actuator).getObservable().is(Type.COUNTABLE)) {
      ret = DefaultRuntimeProvider.createObservation((Actuator) actuator, this);
      catalog.put(actuator.getName(), ret);
    }

    return ret;
  }

  @Override
  public IMonitor getMonitor() {
    return monitor;
  }

  @Override
  public ICountableObservation newObservation(IObservable observable, String name, IScale scale)
      throws KlabException {

    if (!observable.is(Type.COUNTABLE)) {
      throw new IllegalArgumentException(
          "RuntimeContext: cannot create a non-countable observation with newObservation()");
    }

    ICountableObservation ret = null;
    Observable obs = new Observable((Observable) observable);
    obs.setName(name);

    // TODO have these public resolvers return dataflows or null, reusing dataflows from previous runs
    ResolutionScope scope = Resolver.INSTANCE.resolve(obs, this.resolutionScope, Mode.RESOLUTION, scale);

    if (scope.isRelevant()) {
      Dataflow dataflow = Dataflows.INSTANCE
          .compile("local:task:" /* wazzafuck + session.getToken() + ":" + token */, scope);
      ret = (ICountableObservation) dataflow.run(scale, monitor);
    }
    return ret;
  }

  @Override
  public IRelationship newRelationship(IObservable observable, IScale scale, IObjectArtifact source,
      IObjectArtifact target) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IRuntimeContext createChild(IScale scale, IActuator actuator, IResolutionScope scope) {

    RuntimeContext ret = new RuntimeContext(this);
    ret.parent = this;
    ret.namespace = ((Actuator) actuator).getNamespace();
    ret.resolutionScope = (ResolutionScope)scope;
    ret.artifactType =
        Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable());
    if (!(this.target instanceof DirectObservation)) {
      throw new IllegalArgumentException(
          "RuntimeContext: cannot add a child observation to a non-direct observation");
    }

    ret.scale = scale;
    ret.inputs = new HashSet<>();
    ret.outputs = new HashSet<>();
    ret.semantics = new HashMap<>();

    for (IActuator a : actuator.getActuators()) {
      if (!((Actuator) a).isExported()) {
        String id = a.getAlias() == null ? a.getName() : a.getAlias();
        ret.inputs.add(id);
        ret.semantics.put(id, ((Actuator) a).getObservable());
      }
    }
    for (IActuator a : actuator.getOutputs()) {
      String id = a.getAlias() == null ? a.getName() : a.getAlias();
      ret.outputs.add(id);
      ret.semantics.put(id, ((Actuator) a).getObservable());
    }

//    if (!((Actuator) actuator).getObservable().is(Type.COUNTABLE)) {
      
      ret.outputs.add(actuator.getName());
      ret.semantics.put(actuator.getName(), ((Actuator) actuator).getObservable());
      ret.target = DefaultRuntimeProvider.createObservation(((Actuator) actuator), this);
      ret.artifactType =
          Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable());
      ret.catalog.put(((Actuator) actuator).getObservable().getLocalName(), ret.target);
      this.structure.addVertex(ret.target);
      // create child->parent edge
      this.structure.addEdge(ret.target, this.target);
      // TODO provenance (may need to pass the actuator)
      if (ret.target instanceof ISubject) {
        this.network.addVertex((ISubject) ret.target);
      }
//    }

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
        ret.add((T) source);
      }
    }
    return ret;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
    List<Pair<String, T>> ret = new ArrayList<>();
    for (String s : catalog.keySet()) {
      if (type.isAssignableFrom(catalog.get(s).getClass())) {
        ret.add(new Pair<>(s, (T) catalog.get(s)));
      }
    }
    return ret;
  }

  @Override
  public IScale getScale() {
    return scale;
  }

  @Override
  public Type getArtifactType() {
    return artifactType;
  }

  public void setScale(IScale scale) {
    this.scale = scale;
  }

  @Override
  public Collection<String> getInputs() {
    return inputs;
  }

  @Override
  public Collection<String> getOutputs() {
    return outputs;
  }

  @Override
  public IObservable getSemantics(String identifier) {
    return semantics.get(identifier);
  }

  @Override
  public void processAnnotation(IKimAnnotation annotation) {
    switch (annotation.getName()) {
      case "probe":
        addTargetToStructure(annotation);
        break;
      default:
        break;
    }
  }

  private void addTargetToStructure(IKimAnnotation probe) {

    IState state = null;
    if (probe.getParameters().get("observable") == null) {
      // TODO check if collapsing is requested
      state = target instanceof IState ? (IState) target : null;
    } else {
      // TODO build requested observation
    }

    if (state != null && !structure.vertexSet().contains(state) && parent != null
        && parent.target instanceof IDirectObservation) {
      structure.addVertex(state);
      structure.addEdge(state, parent.target);
    }

  }

  @Override
  public void setTarget(IArtifact target) {
    this.target = target;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
    IArtifact ret = getArtifact(localName);
    if (ret != null && cls.isAssignableFrom(ret.getClass())) {
      return (T) ret;
    }
    return null;
  }

}

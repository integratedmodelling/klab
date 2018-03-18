package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.integratedmodelling.kdl.api.IKdlActuator.Type;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowBuilder {

  String name;
  DirectObservation context;
  double coverage;

  Graph<IResolvable, ResolutionEdge> resolutionGraph =
      new DefaultDirectedGraph<>(ResolutionEdge.class);
  Map<Model, ModelD> modelCatalog = new HashMap<>();

  static class ResolutionEdge {

    Coverage coverage;

    ResolutionEdge(Coverage coverage) {
      this.coverage = coverage;
    }

    ResolutionEdge() {}

    public String toString() {
      return "resolves";
    }
  }

  public DataflowBuilder(String name) {
    this.name = name;
  }

  public Dataflow build(IMonitor monitor) {

    if (Configuration.INSTANCE.isDebuggingEnabled()) {
      Graphs.show(resolutionGraph);
    }

    Dataflow ret = new Dataflow(monitor);
    ret.setName(this.name);
    ret.setContext(this.context);
    ret.setCoverage(this.coverage);

    for (IResolvable root : getRootResolvables(resolutionGraph)) {

      modelCatalog.clear();

      Node node = compileActuator(root, resolutionGraph,
          this.context == null ? null : this.context.getScale(), monitor);
      Actuator actuator = node.getActuatorTree(monitor, new HashSet<>());
      actuator.setCreateObservation(root instanceof IObserver
          || !((Observable) root).is(org.integratedmodelling.kim.api.IKimConcept.Type.COUNTABLE));
      ret.getActuators().add(actuator);

      // this will overwrite scale and namespace - another way of saying that these should either be
      // identical or we shouldn't even allow more than one root resolvable.
      ret.setScale(actuator.getScale());
      ret.setNamespace(actuator.getNamespace());
    }

    return ret;
  }

  static class ModelD {

    Model model;
    // how many nodes reference this model's observables
    int useCount;
    // this is null unless the model covers only a part of the context
    Coverage coverage;

    public ModelD(Model model) {
      this.model = model;
    }

    @Override
    public int hashCode() {
      return model.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof ModelD && model.equals(((ModelD) obj).model);
    }

  }

  /**
   * Each node represents one use of a model to compute one observable. Each node will compute an
   * actuator (a true one the first use, a reference afterwards).
   * 
   * If there is more than one model, they will have to be computed individually in their own scale
   * and merged before any other computations are called.
   * 
   * The final actuator hierarchy is built by calling {@link #getActuatorTree(IMonitor)} on the root
   * node.
   * 
   * @author ferdinando.villa
   *
   */
  class Node {

    Observable observable;
    Observer observer;
    Set<ModelD> models = new HashSet<>();
    List<Node> children = new ArrayList<>();
    Scale scale;
    boolean definesScale;

    public Node(IResolvable observable) {
      if (observable instanceof Observable) {
        this.observable = (Observable) observable;
      } else if (observable instanceof Observer) {
        this.observer = (Observer) observable;
        this.observable = this.observer.getObservable();
      }
    }

    /*
     * get the actuator in the node, ignoring the children
     */
    Actuator createActuator(IMonitor monitor, Set<Model> generated) {

      /*
       * create the original actuator
       */
      Actuator ret = Actuator.create(monitor);

      ret.setObservable(observable);
      ret.setScale(scale);
      ret.setDefinesScale(definesScale);

      switch (observable.getObservationType()) {
        case CLASSIFICATION:
          ret.setType(Type.CONCEPT);
          break;
        case DETECTION:
        case INSTANTIATION:
          ret.setType(Type.OBJECT);
          break;
        case QUANTIFICATION:
          ret.setType(Type.NUMBER);
          break;
        case SIMULATION:
          ret.setType(Type.PROCESS);
          break;
        case VERIFICATION:
          ret.setType(Type.BOOLEAN);
          break;
      }

      if (observer != null) {
        ret.setNamespace(observer.getNamespace());
      }

      if (models.size() == 1) {

        // have the runtime provider turn each resource into a call that produces a contextualizer
        Model theModel = models.iterator().next().model;
        ret.setName(theModel.getLocalNameFor(observable));

        if (!generated.contains(theModel)) {
          generated.add(theModel);
          for (IComputableResource resource : models.iterator().next().model
              .getComputation(ITime.INITIALIZATION)) {
            ret.addComputation(resource);
          }
        } else {
          ret.setReference(true);
          ret.setAlias(observable.getLocalName());
        }

        /*
         * build in the models' computation or set to a reference
         */
        if (models.iterator().next().useCount == 1) {

          /*
           * ignore the model's observer id, use the entire computation for this observable
           */

        } else {

          /*
           * compile in a reference with the original model's observable ID 'as' our observable's
           * name
           */
        }

      } else if (models.size() > 1) {

        /*
         * duplicate the observable into ad-hoc separate sub-actuators with independent scale and
         * compile in a merge
         */
      }

      return ret;
    }

    /*
     * get the finished actuator with all the children and the mediation strategy
     */
    Actuator getActuatorTree(IMonitor monitor, Set<Model> generated) {
      Actuator ret = createActuator(monitor, generated);
      for (Node child : sortChildren()) {
        ret.getActuators().add(child.getActuatorTree(monitor, generated));
        // ret.getMediationStrategy().addAll(Observables.INSTANCE
        // .computeMediators(child.originalObservable, child.observable));
      }
      return ret;
    }

    // sort by reverse refcount of model, so that the actuators with references are output before
    // the
    // ones without.
    // TODO revise for 1 ModelD and 1+ models in it.
    private List<Node> sortChildren() {
      List<Node> ret = new ArrayList<>(children);
      Collections.sort(ret, new Comparator<Node>() {

        @Override
        public int compare(DataflowBuilder.Node o1, DataflowBuilder.Node o2) {
          if (o2.models.isEmpty() && o1.models.isEmpty()) {
            return 0;
          }
          if (!o2.models.isEmpty() && o1.models.isEmpty()) {
            return 1;
          }
          if (o2.models.isEmpty() && !o1.models.isEmpty()) {
            return -1;
          }
          return Integer.compare(o2.models.iterator().next().useCount,
              o1.models.iterator().next().useCount);
        }
      });
      return ret;
    }
  }

  /*
   * The simple compilation strategy keeps a catalog of models and a builds a tree of models usage
   * for each observable. Then node are scanned from the root and an actuator is built the first
   * time a model is encountered, a reference is built from the second on. If the model is only used
   * once and for a single observable, the original actuator for a model is given the name of its
   * use and the mediators are compiled in it; otherwise, a link is created and mediators are put in
   * the import instruction.
   */
  private Node compileActuator(IResolvable resolvable, Graph<IResolvable, ResolutionEdge> graph,
      Scale scale, IMonitor monitor) {

    Node ret = new Node(resolvable);

    if (scale == null && resolvable instanceof Observer) {
      try {
        scale = (Scale.create(((Observer) resolvable).getBehavior().getExtents(monitor)));
      } catch (KlabException e) {
        throw new KlabRuntimeException(e);
      }
      ret.definesScale = true;
    }

    ret.scale = scale;

    /*
     * go through models
     */
    boolean hasPartials = graph.incomingEdgesOf(resolvable).size() > 1;
    for (ResolutionEdge d : graph.incomingEdgesOf(resolvable)) {

      Model model = (Model) graph.getEdgeSource(d);
      ModelD md = compileModel(model);
      for (ResolutionEdge o : graph.incomingEdgesOf(model)) {

        Node child = compileActuator(graph.getEdgeSource(o), graph,
            o.coverage == null ? scale : o.coverage.getScale(), monitor);

        if (hasPartials) {
          md.coverage = d.coverage;
          child.definesScale = true;
        }
        ret.children.add(child);
      }
      ret.models.add(md);
    }

    return ret;
  }

  ModelD compileModel(Model model) {
    ModelD ret = modelCatalog.get(model);
    if (ret == null) {
      ret = new ModelD(model);
      modelCatalog.put(model, ret);
    }
    ret.useCount++;
    return ret;
  }

  public DataflowBuilder within(IDirectObservation context) {
    this.context = (DirectObservation) context;
    return this;
  }

  public DataflowBuilder withCoverage(double coverage) {
    this.coverage = coverage;
    return this;
  }

  public DataflowBuilder withResolution(IResolvable source, IResolvable target,
      ICoverage coverage) {
    resolutionGraph.addVertex(source);
    resolutionGraph.addVertex(target);
    resolutionGraph.addEdge(source, target, new ResolutionEdge((Coverage) coverage));
    return this;
  }

  private List<IResolvable> getRootResolvables(Graph<IResolvable, ResolutionEdge> graph) {
    List<IResolvable> ret = new ArrayList<>();
    for (IResolvable res : graph.vertexSet()) {
      if (graph.outgoingEdgesOf(res).size() == 0) {
        ret.add(res);
      }
    }
    return ret;
  }

  public DataflowBuilder withResolvable(IResolvable resolvable) {
    resolutionGraph.addVertex(resolvable);
    return this;
  }

}

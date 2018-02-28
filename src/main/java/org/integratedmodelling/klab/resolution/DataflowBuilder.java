package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kdl.api.IKdlActuator.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow.Builder;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.observation.Transition;
import org.integratedmodelling.klab.owl.Observable;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowBuilder<T extends IArtifact> implements Builder {

  String                                     name;
  Class<T>                                   cls;
  DirectObservation                          context;
  double                                     coverage;

  DirectedGraph<IResolvable, DependencyEdge> dependencyGraph =
      new DefaultDirectedGraph<>(DependencyEdge.class);

  static class DependencyEdge {

    Coverage coverage;

    DependencyEdge(Coverage coverage) {
      this.coverage = coverage;
    }
  }

  public DataflowBuilder(String name, Class<T> type) {
    this.name = name;
  }

  @Override
  public Dataflow<?> build(IMonitor monitor) {

    Dataflow<?> ret = new Dataflow<T>(monitor, cls);
    ret.setName(this.name);
    ret.setContext(this.context);
    ret.setCoverage(this.coverage);

    for (IResolvable root : getRootResolvables(dependencyGraph)) {

      Node node = compileActuator(root, dependencyGraph, new HashMap<>(), monitor);
      node.getActuator().setCreateObservation(root instanceof IObserver
          || !((Observable) root).is(org.integratedmodelling.kim.api.IKimConcept.Type.COUNTABLE));

      ret.getActuators().add(node.getActuatorTree());
    }

    return ret;
  }

  /**
   * Compilation builds a tree of nodes, each containing either an actuator or a reference. During
   * compilation, those may be rearranged so that an actuator needed in an outside scope is floated
   * to the containing scope and substituted with a reference to it.
   * 
   * The final actuator hierarchy is built by calling {@link #getActuatorTree()} on the root node.
   * 
   * @author ferdinando.villa
   *
   */
  class Node {

    Observable             observable;
    Observable             originalObservable;
    Actuator<?>            original;
    Actuator<?>            reference;
    List<Node>             children  = new ArrayList<>();
    List<IServiceCall> mediators = new ArrayList<>();
    int                    refcount  = 0;

    public Node(Observable observable) {
        this.observable = observable;
    }

    /*
     * get the actuator in the node, ignoring the children
     */
    Actuator<?> getActuator() {
      return original == null ? reference : original;
    }

    /*
     * get the finished actuator with all the children and the mediation strategy
     */
    Actuator<?> getActuatorTree() {
      Actuator<?> ret = getActuator();
      for (Node child : children) {
        ret.getActuators().add(child.getActuatorTree());
        ret.getMediationStrategy().addAll(
            Observables.INSTANCE.computeMediators(child.originalObservable, child.observable));
      }
      return ret;
    }

    public String toString() {
      return getActuator() == null ? "(empty node)" : getActuator().toString();
    }

  }

  /*
   * The catalog matches each resolvable to the node that currently holds the actuator and not a
   * reference.
   * 
   * We pass the root observable to decide whether we should build an observation for it in case
   * this is a resolution model.
   */
  private Node compileActuator(IResolvable resolvable,
      DirectedGraph<IResolvable, DependencyEdge> graph, Map<CompatibleObservable, Node> catalog,
      IMonitor monitor) {

    Observer observer = resolvable instanceof Observer ? (Observer) resolvable : null;
    Observable observable = resolvable instanceof Observable ? (Observable) resolvable
        : (observer == null ? null : observer.getObservable());

    Node previous = catalog.get(new CompatibleObservable(observable));
    Node ret = new Node(observable);

    if (previous == null) {

      /*
       * create the original actuator
       */
      ret.original = Actuator.create(monitor, Observables.INSTANCE.getObservationClass(observable));
      ret.original.setName(observable.getLocalName());

      switch (observable.getObservationType()) {
        case CLASSIFICATION:
          ret.original.setType(Type.CONCEPT);
          break;
        case DETECTION:
        case INSTANTIATION:
          ret.original.setType(Type.OBJECT);
          break;
        case QUANTIFICATION:
          ret.original.setType(Type.NUMBER);
          break;
        case SIMULATION:
          ret.original.setType(Type.PROCESS);
          break;
        case VERIFICATION:
          ret.original.setType(Type.BOOLEAN);
          break;
      }

      if (observer != null) {
        try {
          ret.original.setScale(Scale.create(observer.getBehavior().getExtents(monitor)));
        } catch (KlabException e) {
          throw new KlabRuntimeException(e);
        }
        ret.original.setNamespace(observer.getNamespace());
      }

      ret.original.setObservable(observable);

      /*
       * go through models
       */
      boolean hasPartials = graph.incomingEdgesOf(resolvable).size() > 1;
      for (DependencyEdge d : graph.incomingEdgesOf(resolvable)) {

        Model model = (Model) graph.getEdgeSource(d);

        for (DependencyEdge o : graph.incomingEdgesOf(model)) {

          Node child = compileActuator(graph.getEdgeSource(o), graph, catalog, monitor);

          if (hasPartials) {
            // add scale and name suffix
            Coverage coverage = d.coverage;
          }

          // if needed, set alias to name of dependency

          ret.children.add(child);
        }

        // NO use triggers separately 
        ret.original.getComputationStrategy()
            .addAll(model.getComputation(Transition.initialization()));
        ret.originalObservable = model.getCompatibleOutput(observable);
      }

      if (hasPartials) {
        // TODO add merge step to mediators
      } else {
        // take all computation and mediation from strategy.get(0)
      }

    } else {
      /*
       * float the previous actuator to our level, swapping it with a reference to replace the
       * original one created downstream. The actuator in this node becomes our own and gets aliased
       * to the name of this observable
       */
      previous.reference = previous.original.getReference();
      ret.original = previous.original;
      ret.original.setAlias(observable.getLocalName());
      ret.refcount = previous.refcount + 1;
      previous.refcount = 1;
      previous.original = null;
      ret.originalObservable = previous.originalObservable;
    }

    /*
     * the new node is either added to the catalog or takes place of the previous
     */
    catalog.put(new CompatibleObservable(observable), ret);

    return ret;
  }

  @Override
  public Builder within(IDirectObservation context) {
    this.context = (DirectObservation) context;
    return this;
  }

  @Override
  public Builder withCoverage(double coverage) {
    this.coverage = coverage;
    return this;
  }

  @Override
  public Builder withDependency(IResolvable source, IResolvable target, ICoverage coverage) {
    dependencyGraph.addVertex(source);
    dependencyGraph.addVertex(target);
    dependencyGraph.addEdge(source, target, new DependencyEdge((Coverage) coverage));
    return this;
  }

  private List<IResolvable> getRootResolvables(DirectedGraph<IResolvable, DependencyEdge> graph) {
    List<IResolvable> ret = new ArrayList<>();
    for (IResolvable res : graph.vertexSet()) {
      if (graph.outgoingEdgesOf(res).size() == 0) {
        ret.add(res);
      }
    }
    return ret;
  }

  @Override
  public Builder withResolvable(IResolvable resolvable) {
    dependencyGraph.addVertex(resolvable);
    return this;
  }

}

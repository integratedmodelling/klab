package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlActuator.Type;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow.Builder;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.Coverage;
import org.integratedmodelling.klab.utils.graph.GraphPartitioner;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowBuilder<T extends IArtifact> implements Builder {

  String                                     name;
  String                                     alias;
  Observable                                 newObservable;
  String                                     newUrn;
  List<DataflowBuilder<?>>                   children        = new ArrayList<>();
  Scale                                      scale;
  IKdlActuator.Type                          type;
  INamespace                                 namespace;
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
    this.type = setType(type);
  }

  private Type setType(Class<T> type) {
    this.cls = type;
    // TODO Auto-generated method stub
    if (ICountableObservation.class.isAssignableFrom(type)) {
      return Type.OBJECT;
    } else if (IProcess.class.isAssignableFrom(type)) {
      return Type.PROCESS;
    } else if (!IState.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException(
          "Cannot use " + type.getCanonicalName() + " as the returned type for a k.LAB dataflow");
    }
    return null;
  }

  @Override
  public Builder instantiating(IObservable observable) {

    this.newObservable = (Observable) observable;
    if (IState.class.isAssignableFrom(this.cls)) {
      if (!observable.is(IKimConcept.Type.QUALITY)) {
        throw new IllegalArgumentException(
            "Observable " + observable + " is incompatible with the dataflow type");
      }
      switch (observable.getObservationType()) {
        case CLASSIFICATION:
          this.type = Type.CONCEPT;
          break;
        case QUANTIFICATION:
          this.type = Type.NUMBER;
          break;
        case VERIFICATION:
          this.type = Type.BOOLEAN;
          break;
        default:
          throw new IllegalArgumentException(
              "Observable " + observable + " is incompatible with the dataflow type");
      }
    } else if (!(observable.is(IKimConcept.Type.QUALITY) && this.type == Type.PROCESS)
        && !(observable.is(IKimConcept.Type.COUNTABLE) && this.type == Type.OBJECT)) {
      throw new IllegalArgumentException(
          "Observable " + observable + " is incompatible with the dataflow type");
    }
    return this;
  }

  @Override
  public Builder withScale(IScale scale) {
    this.scale = (Scale) scale;
    return this;
  }

  @Override
  public Builder add(String actuatorName, Class<? extends IArtifact> type) {
    @SuppressWarnings({"rawtypes", "unchecked"})
    DataflowBuilder<?> builder = new DataflowBuilder(actuatorName, type);
    this.children.add(builder);
    return builder;
  }

  @Override
  public IActuator build() {

    // generate the outer dataflow
    Dataflow<?> ret = new Dataflow<T>(cls);
    ret.name = this.name;
    ret.type = this.type;
    ret.scale = this.scale;
    ret.newObservationType = newObservable;
    ret.context = this.context;
    ret.namespace = this.namespace == null ? null : this.namespace.getId();
    ret.coverage = this.coverage;

    Collection<DirectedGraph<IResolvable, DependencyEdge>> subgraphs =
        new GraphPartitioner<IResolvable, DependencyEdge>() {

          @Override
          public DirectedGraph<IResolvable, DependencyEdge> getNewGraph() {
            return new DefaultDirectedGraph<>(DependencyEdge.class);
          }

        }.getDisconnectedSubgraphs(dependencyGraph);

    /*
     * At the root level, in order to have parallel computation we need >1 completely independent
     * resolutions. At the moment having >1 subgraphs can't happen, unless we allow users to observe
     * more than one concept at a time, which is not supported in the current API but is a useful
     * use case: to do that today, >1 concepts should be added as dependencies for a dummy model,
     * which prevents parallelization.
     */
    for (DirectedGraph<IResolvable, DependencyEdge> graph : subgraphs) {
      for (Observable root : getRootObservables(graph)) {
        Node node = compileActuator(root, graph, new HashMap<>());
        ret.actuators.add(node.getActuator());
      }
    }

    return ret;
  }

  class Node {
    // has either of:
    // 1. specs for an actuator, or
    Actuator<?> original;
    // 2. specs for a reference to an actuator
    Actuator<?> reference;

    List<Node>         children = new ArrayList<>();

    Actuator<?> getActuator() {
      return original == null ? reference : original;
    }
    
    public String toString() {
      return getActuator() == null ? "(empty)" : getActuator().toString();
    }

  }


  /*
   * Catalog matches each observable to the current node that has the actual actuator specs.
   */
  private Node compileActuator(Observable observable,
      DirectedGraph<IResolvable, DependencyEdge> graph, Map<Observable, Node> catalog) {

    Node previous = catalog.get(observable);
    Node ret = new Node();

    if (previous == null) {

      /*
       * create the original actuator
       */
      ret.original = Actuator.create(Observables.INSTANCE.getObservationClass(observable));
      ret.original.name = observable.getLocalName();
      switch (observable.getObservationType()) {
        case CLASSIFICATION:
          ret.original.type = Type.CONCEPT;
          break;
        case DETECTION:
        case INSTANTIATION:
          ret.original.type = Type.OBJECT;
          break;
        case QUANTIFICATION:
          ret.original.type = Type.NUMBER;
          break;
        case SIMULATION:
          ret.original.type = Type.PROCESS;
          break;
        case VERIFICATION:
          ret.original.type = Type.BOOLEAN;
          break;
      }
      
      /*
       * go through models
       */

      boolean hasPartials = graph.incomingEdgesOf(observable).size() > 1;
      List<Node> strategy = new ArrayList<>();
      for (DependencyEdge d : graph.incomingEdgesOf(observable)) {

        Model model = (Model) graph.getEdgeSource(d);
        
        if (!model.isInstantiator()) {
          ret.original.newObservationType = observable;
        }
        
        Coverage coverage = d.coverage;

        for (DependencyEdge o : graph.incomingEdgesOf(model)) {

          Node child = compileActuator((Observable) graph.getEdgeSource(o), graph, catalog);

//          DataflowBuilder<?> depBuilder = child.getBuilder();
          if (hasPartials) {
            // add scale and name suffix
          }

          // add any needed mediator to depBuilder to match observable with the one coming from
          // the model

          // if needed, set alias to name of dependency
          String alias = ((Observable) graph.getEdgeSource(o)).getLocalName();
          // SBAGLIATO DIO CANE
//          if (!alias.equals(observable.getLocalName())) {
//            depBuilder.alias = alias;
//          }

          strategy.add(child);
        }
      }

      if (hasPartials) {
        // add them all
        for (Node child : strategy) {
          ret.children.add(child);
        }
        // TODO add merge step
      } else {
        // take all computation and mediation from strategy.get(0)
      }

    } else {

      /*
       * float the previous actuator to our level, swapping it with a reference to replace the
       * original one created downstream, using same alias
       */
      previous.reference = previous.original.getReference();

      /*
       * swap any mediators to match observable with the referenced one
       */

      /*
       * the actuator in this node becomes our own
       */
      ret.original = previous.original;
      previous.original = null;
    }

    /*
     * the new node is either added to the catalog or takes place of the previous
     */
    catalog.put(observable, ret);

    return ret;
  }

  @Override
  public Builder within(IDirectObservation context) {
    this.context = (DirectObservation) context;
    return this;
  }

  @Override
  public Builder instantiating(IObservable observable, INamespace namespace) {
    this.namespace = namespace;
    return instantiating(observable);
  }

  @Override
  public Builder withCoverage(double coverage) {
    this.coverage = coverage;
    return this;
  }

  @Override
  public void addDependency(IResolvable source, IResolvable target, ICoverage coverage) {
    dependencyGraph.addVertex(source);
    dependencyGraph.addVertex(target);
    dependencyGraph.addEdge(source, target, new DependencyEdge((Coverage) coverage));
  }

  private List<Observable> getRootObservables(DirectedGraph<IResolvable, DependencyEdge> graph) {
    List<Observable> ret = new ArrayList<>();
    for (IResolvable res : graph.vertexSet()) {
      if (res instanceof Observable && graph.outgoingEdgesOf(res).size() == 0) {
        ret.add((Observable) res);
      }
    }
    return ret;
  }
//
//  @Override
//  public Builder add(Builder child) {
//    children.add((DataflowBuilder<?>) child);
//    return this;
//  }
  
}


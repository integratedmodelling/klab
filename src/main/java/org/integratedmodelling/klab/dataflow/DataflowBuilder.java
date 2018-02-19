package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlActuator.Type;
import org.integratedmodelling.kim.api.IKimConcept;
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
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow.Builder;
import org.integratedmodelling.klab.observation.DirectObservation;
import org.integratedmodelling.klab.observation.Scale;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.graph.GraphPartitioner;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowBuilder<T extends IArtifact> implements Builder {

  String                                     name;
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
    ICoverage coverage;

    DependencyEdge(ICoverage coverage) {
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
  public <K extends IArtifact> IDataflow<K> build() {


    System.out.println("-------------------------------------------------");

    // generate the outer dataflow
    Dataflow<K> ret = new Dataflow<K>();
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
     * At the root level, in order to have parallel computation we need completely independent
     * resolutions. At the moment having >1 subgraphs can't happen unless we allow users to observe
     * more than one concept at a time, which is not supported in the current API.
     */
    for (DirectedGraph<IResolvable, DependencyEdge> graph : subgraphs) {
      CompilationContext compilationContext = new CompilationContext();
      for (Observable root : getRootObservables(graph)) {
        /* Node node = */ compileActuator(root, compilationContext);
        // children.add(node.getBuilder());
      }
    }

    // generate them
    for (Builder builder : children) {
      // TODO these may be other than dataflows
      ret.actuators.add(builder.build());
    }

    System.out.println("-------------------------------------------------");

    return ret;
  }

  private void /* Node */ compileActuator(Observable observable,
      /* Node parent, */ CompilationContext compilationContext) {

    System.out.println("ROOT is " + observable);

    /*
     * 1. get the existing actuator node with builder 2. if null: ret = actuator node with builder:
     * foreach model incoming compile observable to actuator if >1 model wrap into partial
     * computations add merge in computation else add generators in computation else ret = new
     * actuator node with original builder substitute builder in original node with a reference to
     * ret
     * 
     * 3. add mediators from model observable to this observable to computation 4. check naming and
     * use as <name> if reference 5. link to parent if any and return node
     */

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
    dependencyGraph.addEdge(source, target, new DependencyEdge(coverage));
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

  class CompilationContext {

  }

}

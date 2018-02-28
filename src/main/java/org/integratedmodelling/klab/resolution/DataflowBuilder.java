package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kdl.api.IKdlActuator.Type;
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

    DirectedGraph<IResolvable, DependencyEdge> dependencyGraph = new DefaultDirectedGraph<>(DependencyEdge.class);
    Map<Model, ModelD>                         modelCatalog    = new HashMap<>();

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

            modelCatalog.clear();

            Node node = compileActuator(root, dependencyGraph, monitor);
            Actuator<?> actuator = node.getActuatorTree(monitor, new HashSet<>());
            actuator.setCreateObservation(root instanceof IObserver
                    || !((Observable) root).is(org.integratedmodelling.kim.api.IKimConcept.Type.COUNTABLE));
            ret.getActuators().add(actuator);
        }

        return ret;
    }

    static class ModelD {

        Model    model;
        // how many nodes reference this model's observables
        int      useCount;
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
     * Each node represents one use of a model to compute one observable. Each node will compute
     * an actuator (a true one the first use, a reference afterwards). Nodes are arranged by
     * the compiler in a 
     * 
     * If there is more than one model, they will have to be computed individually in their own scale and
     * merged before any other computations are called.
     * 
     * The final actuator hierarchy is built by calling {@link #getActuatorTree(IMonitor)} on the root node.
     * 
     * @author ferdinando.villa
     *
     */
    class Node {

        Observable  observable;
        Observer    observer;
        Set<ModelD> models   = new HashSet<>();
        List<Node>  children = new ArrayList<>();

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
        Actuator<?> createActuator(IMonitor monitor, Set<Model> generated) {

            /*
             * create the original actuator
             */
            Actuator<?> ret = Actuator.create(monitor, Observables.INSTANCE.getObservationClass(observable));

            ret.setObservable(observable);
            ret.setName(observable.getLocalName());

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
                try {
                    ret.setScale(Scale.create(observer.getBehavior().getExtents(monitor)));
                } catch (KlabException e) {
                    throw new KlabRuntimeException(e);
                }
                ret.setNamespace(observer.getNamespace());
            }

            if (models.size() == 1) {

                Model theModel = models.iterator().next().model;

                if (!generated.contains(theModel)) {
                    generated.add(theModel);
                    ret.getComputationStrategy().addAll(models.iterator().next().model
                            .getComputation(Transition.initialization()));
                } else {
                    ret.setReference(true);
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
                     * compile in a reference with the original model's observable ID 'as' our observable's name
                     */
                }

            } else if (models.size() > 1) {
                
                /*
                 * duplicate the observable into ad-hoc separate sub-actuators with
                 * independent scale and compile in a merge 
                 */
            }

            return ret;
        }

        /*
         * get the finished actuator with all the children and the mediation strategy
         */
        Actuator<?> getActuatorTree(IMonitor monitor, Set<Model> generated) {
            Actuator<?> ret = createActuator(monitor, generated);
            for (Node child : children) {
                ret.getActuators().add(child.getActuatorTree(monitor, generated));
                // ret.getMediationStrategy().addAll(Observables.INSTANCE
                // .computeMediators(child.originalObservable, child.observable));
            }
            return ret;
        }
    }

    /*
     * The simple compilation strategy keeps a catalog of models and a builds a tree of models usage for each
     * observable. Then node are scanned from the root and an actuator is built the first time a model is encountered,
     * a reference is built from the second on. If the model is only used once and for a single observable, the original actuator for a model is
     * given the name of its use and the mediators are compiled in it; otherwise, a link is created and mediators are put in the
     * import instruction.
     */
    private Node compileActuator(IResolvable resolvable, DirectedGraph<IResolvable, DependencyEdge> graph, IMonitor monitor) {

        Node ret = new Node(resolvable);

        /*
         * go through models
         */
        boolean hasPartials = graph.incomingEdgesOf(resolvable).size() > 1;
        for (DependencyEdge d : graph.incomingEdgesOf(resolvable)) {

            Model model = (Model) graph.getEdgeSource(d);
            ModelD md = compileModel(model);
            for (DependencyEdge o : graph.incomingEdgesOf(model)) {
                Node child = compileActuator(graph.getEdgeSource(o), graph, monitor);
                if (hasPartials) {
                    md.coverage = d.coverage;
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

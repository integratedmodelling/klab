package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IAssociation;
import org.integratedmodelling.klab.api.provenance.IPlan;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.data.resources.ContextualizedResource;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.utils.DebugFile;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import groovy.lang.GroovyObjectSupport;

import org.integratedmodelling.klab.api.API.PUBLIC.Export;
import org.integratedmodelling.klab.api.actors.IBehavior;

/**
 * Holds two graphs: a simplified one that only contains artifacts (resources and observations) with
 * their scaled derivation links, optimized for display, and a full one that also includes processes
 * (models) and the user-triggered observation tasks (processes triggering models) with their plan
 * (dataflow) and controlling agents (k.LAB AI or session user). The simple provenance graph is most
 * useful at runtime, the other is fully OPM-compliant and is most useful for archival purposes.
 * Both should be visualizable in the Explorer along with the dataflow. Both should include the
 * relevant portions of any provenance records associated with the resources used.
 * 
 * Ops on provenance should be defined. Should include backwards and upwards lineage checks for
 * concepts and resources with or without scale filters, model inquiries, and possibly also
 * operation inquiries (e.g. find all the artifacts that have involved normalization and when,
 * possibly only in a specific time or space) and agent inquiries. At large scale this would ideally
 * sit in a network database.
 * 
 * @author Ferd
 */
public class Provenance extends GroovyObjectSupport implements IProvenance {

    private Graph<IProvenance.Node, ProvenanceEdge> simpleGraph = new DefaultDirectedGraph<>(ProvenanceEdge.class);
    private Graph<IProvenance.Node, ProvenanceEdge> fullGraph = new DefaultDirectedGraph<>(ProvenanceEdge.class);
    private RuntimeScope overallScope;
    Map<Dataflow, IPlan> plans = new HashMap<>();
    Map<ITask<?>, IActivity> tasks = new HashMap<>();
    Map<IViewModel, ViewNode> views = new HashMap<>();

    // wrapped nodes with improved UI behavior
    Map<Node, Node> nodes = new HashMap<>();

    /**
     * TODO this should also take the agent and activity that created the initial subject.
     */
    public Provenance(RuntimeScope scope) {
        super();
        this.overallScope = scope;
    }

    @Override
    public boolean isEmpty() {
        return simpleGraph.vertexSet().isEmpty();
    }

    @Override
    public List<IActivity> getPrimaryActions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IArtifact> getArtifacts() {
        Set<IArtifact> ret = new HashSet<>();
        for (IProvenance.Node node : simpleGraph.vertexSet()) {
            if (node instanceof IArtifact) {
                ret.add((IArtifact) node);
            }
        }
        return ret;
    }

    public void add(Node target, Node source, IScale scale, IActuator actuator, IRuntimeScope scope,
            IAssociation.Type association) {

        IPlan plan = getPlan(actuator, scope);
        IActivity task = getTask(actuator, scope);
        IActivity process = getProcess(actuator, scope);

        /*
         * actuator contains the process that has turned x into y, not necessarily by itself;
         * accumulate the processes that build each node and are fed by each precursor. Because
         * actuator == model we can use the model as plan for the process.
         */
        for (Node node : normalizeNode(target, actuator)) {
            for (Node previous : normalizeNode(source, actuator)) {

                // multiscale derivations allow this to happen
                if (node == previous) {
                    continue;
                }

                /*
                 * This separates out any partials that came out as potentially scale-restricted
                 * wrappers from normalizeNode() and adjusts the coverage.
                 */
                Triple<Node, Node, IScale> unwrapped = unwrapPartials(node, previous, scale);

                /*
                 * re-wrap into the final objects with API-friendly types and labels
                 */
                node = wrap(unwrapped.getFirst());
                previous = wrap(unwrapped.getSecond());
                IScale rscale = unwrapped.getThird();

                /*
                 * Agent may be k.LAB or the user if the artifact is main, i.e. the task observation
                 * is the same observable.
                 */

                simpleGraph.addVertex(node);
                simpleGraph.addVertex(previous);
                boolean linked = false;

                /*
                 * Should also document what is used for initialization vs. transitions
                 */
                for (ProvenanceEdge edge : simpleGraph.outgoingEdgesOf(node)) {
                    // should never be > 1
                    if (simpleGraph.getEdgeTarget(edge) == previous) {
                        edge.merge(rscale);
                        linked = true;
                        break;
                    }
                }
                if (!linked) {
                    // TODO link plan and process; this just links artifacts
                    simpleGraph.addEdge(node, previous, new ProvenanceEdge(rscale, IAssociation.Type.wasDerivedFrom, this, true));
                }
            }
        }
    }

    private Triple<Node, Node, IScale> unwrapPartials(Node node, Node previous, IScale scale) {

        if (node instanceof NodeWrapper) {
            if (((NodeWrapper) node).coverage != null) {
                scale = ((NodeWrapper) node).coverage;
            }
            node = ((NodeWrapper) node).delegate;
        }
        if (previous instanceof NodeWrapper) {
            // should be OK to substitute the scale both times instead of merging, as there
            // should not be a situation where both nodes are wrappers.
            if (((NodeWrapper) previous).coverage != null) {
                scale = ((NodeWrapper) previous).coverage;
            }
            previous = ((NodeWrapper) previous).delegate;
        }

        return new Triple<>(node, previous, scale);
    }

    private Node wrap(Node node) {
        Node ret = this.nodes.get(node);
        if (ret == null) {
            ret = new NodeWrapper(node);
            this.nodes.put(node, ret);
        }
        return ret;
    }

    private IActivity getTask(IActuator actuator, IRuntimeScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    private IActivity getProcess(IActuator actuator, IRuntimeScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    private Collection<Node> normalizeNode(Node node, IActuator actuator) {

        Collection<Node> ret = new ArrayList<>();

        /*
         * if null, we're looking at a view (or a filter? shouldn't happen) so make or retrieve a
         * placeholder for it.
         */
        if (node == null) {
            IModel model = ((Actuator) actuator).getModel();
            if (model instanceof Model && ((Model) model).getViewModel() != null) {
                node = makeViewNode(((Model) model).getViewModel());
            }
        }

        /*
         * if artifact, it may be rescaling, mediating or layered (the latter shouldn't happen but
         * is handled to be safe). Must use the original, unmediated delegate in all situations.
         */
        while(node instanceof DelegatingArtifact) {
            node = ((DelegatingArtifact) node).getDelegate();
        }

        /*
         * if resource, must extract the actual contextualized resource(s) being used, which could
         * be multiple with complementary, possibly overlapping, coverages
         */
        boolean done = false;
        if (node instanceof ComputableResource) {
            if (!((ComputableResource) node).isFinal()) {
                throw new KlabInternalErrorException("non-final resource passed to provenance collector");
            }
            if (((ComputableResource) node).getUrn() != null) {
                IResource resource = ((ComputableResource) node).getResource();
                if (resource instanceof ContextualizedResource) {
                    for (Pair<IResource, ICoverage> rp : ((ContextualizedResource) resource).getAtomicResources()) {
                        NodeWrapper wrapper = new NodeWrapper(rp.getFirst());
                        wrapper.coverage = rp.getSecond();
                        ret.add(wrapper);
                    }
                    done = true;
                } else if (resource != null) {
                    node = resource;
                }
            }
        }

        /*
         * default case, just add the node as is
         */
        if (!done) {
            ret.add(node);
        }

        return ret;
    }

    private Node makeViewNode(IViewModel model) {

        if (this.views.containsKey(model)) {
            return this.views.get(model);
        }

        ViewNode ret = new ViewNode(model);
        this.views.put(model, ret);

        return ret;
    }

    public Graph<Node, ProvenanceEdge> getSimplifiedGraph() {
        return simpleGraph;
    }

    public Graph<Node, ProvenanceEdge> getFullGraph() {
        return fullGraph;
    }

    @Override
    public <T> Collection<T> collect(Class<? extends T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

    public IPlan getPlan(IActuator actuator, IRuntimeScope scope) {
        // TODO make or return a plan for the dataflow currently running in the task
        return null;
    }

    /**
     * The agent representing the AI in k.LAB
     */
    public IAgent KLAB_AGENT = new IAgent(){

        @Override
        public String getId() {
            return overallScope.getSession().getParentIdentity(IEngine.class).getId();
        }

        @Override
        public long getTimestamp() {
            return 0;
        }

        @Override
        public IProvenance getProvenance() {
            return Provenance.this;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public IIdentity getParentIdentity() {
            return overallScope.getSession().getParentIdentity(IEngine.class).getParentIdentity();
        }

        @Override
        public boolean is(Type type) {
            return overallScope.getSession().getParentIdentity(IEngine.class).is(type);
        }

        @Override
        public <T extends IIdentity> T getParentIdentity(Class<T> type) {
            return overallScope.getSession().getParentIdentity(IEngine.class).getParentIdentity(type);
        }

        @Override
        public String toString() {
            return "k.LAB";
        }
        
        
        public IIdentity.Type getIdentityType() {
            return IIdentity.Type.KLAB;
        }

        @Override
        public Reference getActor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void instrument(Reference actor) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public View getView() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setView(View layout) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public boolean stop() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public IMonitor getMonitor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IParameters<String> getState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String load(IBehavior behavior, IContextualizationScope scope) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean stop(String behaviorId) {
            // TODO Auto-generated method stub
            return false;
        }

    };

    /**
     * The agent representing the session user (UI or API) making the observations
     */
    public IAgent USER_AGENT = new IAgent(){

        @Override
        public String getId() {
            return overallScope.getSession().getUser().getUsername();
        }

        @Override
        public long getTimestamp() {
            return 0;
        }

        @Override
        public IProvenance getProvenance() {
            return Provenance.this;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public IIdentity getParentIdentity() {
            return overallScope.getSession().getUser().getParentIdentity();
        }

        @Override
        public boolean is(Type type) {
            return overallScope.getSession().getUser().is(type);
        }

        @Override
        public <T extends IIdentity> T getParentIdentity(Class<T> type) {
            return overallScope.getSession().getUser().getParentIdentity(type);
        }

        @Override
        public String toString() {
            return overallScope.getSession().getUser().getUsername();
        }

        @Override
        public IIdentity.Type getIdentityType() {
            return IIdentity.Type.MODEL_SESSION;
        }

        @Override
        public Reference getActor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void instrument(Reference actor) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public View getView() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setView(View layout) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public boolean stop() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public IMonitor getMonitor() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IParameters<String> getState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String load(IBehavior behavior, IContextualizationScope scope) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean stop(String behaviorId) {
            // TODO Auto-generated method stub
            return false;
        }
    };

    class ActivityNode implements IProvenance.Node {

        IModel model;
        ITask<?> task;
        long timestamp = System.currentTimeMillis();
        Provenance provenance;

        public ActivityNode(IModel model, Provenance provenance) {
            this.model = model;
            this.provenance = provenance;
        }

        public ActivityNode(ITask<?> task, Provenance provenance) {
            this.task = task;
            this.provenance = provenance;
        }

        @Override
        public String getId() {
            return task == null ? model.getId() : task.getId();
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public IProvenance getProvenance() {
            return provenance;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String toString() {
            return task == null ? model.getName() : ((AbstractTask<?>) task).getDescriptor().getDescription();
        }
    }

    public class ViewNode implements IProvenance.Node {

        IViewModel view = null;
        long timestamp = System.currentTimeMillis();

        public ViewNode(IViewModel model) {
            this.view = model;
        }

        @Override
        public String getId() {
            return view.getId();
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public IProvenance getProvenance() {
            return Provenance.this;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String toString() {
            return view.getViewClass() + " " + view.getName();
        }

    }

    /**
     * This is just to normalize the contents and provide a uniform labeling strategy. All nodes in
     * the graphs are wrapped in this, and the delegate may itself be a wrapper.
     * 
     * @author Ferd
     *
     */
    public class NodeWrapper implements IProvenance.Node {

        Node delegate;
        // wrapper may contain coverage to remember resource contextualization to add to
        // the
        // edges in case of partials (still not happening)
        transient ICoverage coverage;

        public NodeWrapper(Node node) {
            this.delegate = node;
        }

        @Override
        public String getId() {
            return delegate.getId();
        }

        @Override
        public long getTimestamp() {
            return delegate.getTimestamp();
        }

        @Override
        public IProvenance getProvenance() {
            return Provenance.this;
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Override
        public String toString() {
            if (delegate instanceof IObservation) {
                return ((IObservation) delegate).getObservable().getDefinition() + " observation";
            } else if (delegate instanceof IResource) {
                return ((IResource) delegate).getUrn();
            } else if (delegate instanceof IModel) {
                return ((IModel) delegate).getName();
            } else if (delegate instanceof IContextualizable) {
                if (((IContextualizable) delegate).getLiteral() != null) {
                    return "" + ((IContextualizable) delegate).getLiteral();
                }
            }
            // TODO shouldn't be null, these are views
            return delegate == null ? "NULL" : delegate.toString();
        }

        public ElementType getType() {
            if (delegate instanceof IObservation) {
                return ElementType.SEMANTIC_ENTITY;
            } else if (delegate instanceof IResource) {
                return ElementType.RESOURCE_ENTITY;
            } else if (delegate instanceof ActivityNode) {
                return ((ActivityNode) delegate).task == null ? ElementType.MODEL_ACTIVITY : ElementType.TASK_ACTIVITY;
            } else if (delegate instanceof IContextualizable) {
                if (((IContextualizable) delegate).getLiteral() != null) {
                    return ElementType.LITERAL_ENTITY;
                }
            } else if (delegate instanceof IPlan) {
                return ElementType.DATAFLOW_PLAN;
            } else if (delegate == KLAB_AGENT) {
                return ElementType.KLAB_AGENT;
            } else if (delegate == USER_AGENT) {
                return ElementType.USER_AGENT;
            } else if (delegate instanceof ViewNode) {
                return ElementType.VIEW_ENTITY;
            }

            // TODO shouldn't be null, these are views
            return delegate == null ? ElementType.SEMANTIC_ENTITY : ElementType.RESOURCE_ENTITY;

        }

        public Node getDelegate() {
            return delegate;
        }

    }

    public String getElkGraph(boolean fullGraph) {
        Flowchart flowchart = Flowchart.create(this, overallScope, fullGraph ? getFullGraph() : getSimplifiedGraph());
        return flowchart.getJsonLayout(fullGraph ? Export.PROVENANCE_FULL : Export.PROVENANCE_SIMPLIFIED);
    }

    public String getKimCode(boolean fullGraph) {
        throw new KlabUnimplementedException("k.IM provenance export");
    }

}

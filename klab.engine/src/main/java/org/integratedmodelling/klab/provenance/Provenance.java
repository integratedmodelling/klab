package org.integratedmodelling.klab.provenance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
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
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import groovy.lang.GroovyObjectSupport;

/**
 * Holds two graphs: a simplified one that only contains artifacts (resources
 * and observations) with their scaled derivation links, optimized for display,
 * and a full one that also includes processes (models) and the user-triggered
 * observation tasks (processes triggering models) with their plan (dataflow)
 * and controlling agents (k.LAB AI or session user). The simple provenance
 * graph is most useful at runtime, the other is fully OPM-compliant and is most
 * useful for archival purposes. Both should be visualizable in the Explorer
 * along with the dataflow. Both should include the relevant portions of any
 * provenance records associated with the resources used.
 * 
 * @author Ferd
 *
 */
public class Provenance extends GroovyObjectSupport implements IProvenance {

	private Graph<IProvenance.Node, ProvenanceEdge> simpleGraph = new DefaultDirectedGraph<>(ProvenanceEdge.class);
	private Graph<IProvenance.Node, ProvenanceEdge> fullGraph = new DefaultDirectedGraph<>(ProvenanceEdge.class);
	private RuntimeScope overallScope;
	Map<Dataflow, IPlan> plans = new HashMap<>();
	Map<ITask<?>, IActivity> task = new HashMap<>();
	Map<IModel, ModelNode> views = new HashMap<>();

	// wrapped nodes with improved UI behavior
	Map<Node, Node> nodes = new HashMap<>();

	/**
	 * TODO this should also take the agent and activity that created the initial
	 * subject.
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
		 * actuator contains the process that has turned x into y, not necessarily by
		 * itself; accumulate the processes that build each node and are fed by each
		 * precursor. Because actuator == model we can use the model as plan for the
		 * process.
		 */
		for (Node node : normalizeNode(target, actuator)) {
			for (Node previous : normalizeNode(source, actuator)) {

				// multiscale derivations allow this to happen
				if (node == previous) {
					continue;
				}

				// CHECK this is mostly for the UI, we may want the full graph to keep the
				// original objects instead.
				node = wrap(node);
				previous = wrap(previous);

				/*
				 * Agent may be k.LAB or the user if the artifact is main, i.e. the task
				 * observation is the same observable.
				 */

				simpleGraph.addVertex(node);
				simpleGraph.addVertex(previous);
				boolean linked = false;

				/*
				 * Should also document what is used for initialization vs. transitions
				 */
				for (ProvenanceEdge edge : simpleGraph.incomingEdgesOf(node)) {
					// should never be > 1
					edge.merge(scale);
					linked = true;
				}
				if (!linked) {
					// TODO link plan and process; this just links artifacts
					simpleGraph.addEdge(node, previous, new ProvenanceEdge(scale, IAssociation.Type.wasDerivedFrom));
				}
			}
		}
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
		 * if null, we're looking at a view (or a filter? shouldn't happen) so make or
		 * retrieve a placeholder for it.
		 */
		if (node == null) {
			IModel model = ((Actuator) actuator).getModel();
			if (model instanceof IViewModel) {
				node = makeViewNode(model);
			}
		}

		/*
		 * if artifact, it may be rescaling, mediating or layered (the latter shouldn't
		 * happen but is handled to be safe). Must use the original, unmediated delegate
		 * in all situations.
		 */
		while (node instanceof DelegatingArtifact) {
			node = ((DelegatingArtifact) node).getDelegate();
		}

		/*
		 * if resource, must extract the actual contextualized resource(s) being used,
		 * which could be multiple with complementary, possibly overlapping, coverages
		 */
		if (node instanceof ComputableResource) {
			if (!((ComputableResource) node).isFinal()) {
				throw new KlabInternalErrorException("non-final resource passed to provenance collector");
			}
			if (((ComputableResource) node).getUrn() != null) {
				IResource resource = ((ComputableResource) node).getResource();
				if (resource != null) {
					node = resource;
				}
			}
		}

		/*
		 * default case, just add the node as is
		 */
		ret.add(node);

		return ret;
	}

	private Node makeViewNode(IModel model) {

		if (this.views.containsKey(model)) {
			return this.views.get(model);
		}

		ModelNode ret = new ModelNode(model, this);
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
	public IAgent KLAB_AGENT = new IAgent() {

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

	};

	/**
	 * The agent representing the session user (UI or API) making the observations
	 */
	public IAgent USER_AGENT = new IAgent() {

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

	};

	class ModelNode implements IProvenance.Node {

		IModel model;
		long timestamp = System.currentTimeMillis();
		Provenance provenance;

		public ModelNode(IModel model, Provenance provenance) {
			this.model = model;
			this.provenance = provenance;
		}

		@Override
		public String getId() {
			return model.getId();
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
			return "View " + model.getName();
		}
	}

	/**
	 * This is just to normalize the contents and provide a uniform labeling
	 * strategy.
	 * 
	 * @author Ferd
	 *
	 */
	class NodeWrapper implements IProvenance.Node {

		Node delegate;

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
				return ((IObservation) delegate).getObservable().getDefinition();
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
			return delegate == null ? "Mierda, null" : delegate.toString();
		}

	}

	public String getElkGraph(boolean fullGraph) {
		Flowchart flowchart = Flowchart.create(this, overallScope, fullGraph ? getFullGraph() : getSimplifiedGraph());
		return flowchart.getJsonLayout();
	}

}

package org.integratedmodelling.klab.provenance;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import groovy.lang.GroovyObjectSupport;

public class Provenance extends GroovyObjectSupport implements IProvenance {

	private Graph<IProvenance.Node, ProvenanceEdge> graph = new DefaultDirectedGraph(ProvenanceEdge.class);
	private RuntimeScope scope;

	/**
	 * TODO this should also take the agent and activity that created the initial
	 * subject.
	 */
	public Provenance(RuntimeScope scope) {
		super();
		this.scope = scope;
	}

	@Override
	public boolean isEmpty() {
		return graph.vertexSet().isEmpty();
	}

	@Override
	public List<IActivity> getPrimaryActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getRootArtifact() {
		return null;
	}

	@Override
	public Collection<IArtifact> getArtifacts() {
		Set<IArtifact> ret = new HashSet<>();
		for (IProvenance.Node node : graph.vertexSet()) {
			if (node instanceof IArtifact) {
				ret.add((IArtifact) node);
			}
		}
		return ret;
	}
	
	public void add(Node node, Node previous, IScale scale, IActuator actuator) {
		graph.addVertex(node);
		graph.addVertex(previous);
		boolean linked = false;
		for (ProvenanceEdge edge : graph.incomingEdgesOf(node)) {
			// should never be > 1
			edge.merge(scale);
			linked = true;
		}
		if (!linked) {
			// TODO add the plan (model) to the edge
			graph.addEdge(previous, node, new ProvenanceEdge(scale));
		}
	}

	@Override
	public <T> Collection<T> collect(Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}

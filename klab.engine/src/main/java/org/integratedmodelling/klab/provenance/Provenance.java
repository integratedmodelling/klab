package org.integratedmodelling.klab.provenance;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
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

	public void addArtifact(IArtifact ret) {
		graph.addVertex(ret);
	}

	@Override
	public <T> Collection<T> collect(Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}

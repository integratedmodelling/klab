package org.integratedmodelling.klab.components.runtime;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * The structural graph is a graph containing only observations and not groups,
 * with a direct link between group members and their context observations. It
 * is paired with another that describes only the group containment structure so
 * that both can be reconstructed.
 * 
 * @author ferdinando.villa
 *
 */
public class Structure extends DefaultDirectedGraph<IArtifact, DefaultEdge> {

	private static final long serialVersionUID = -6139267592470069943L;

	private Graph<IArtifact, DefaultEdge> artifactStructure = new DefaultDirectedGraph<IArtifact, DefaultEdge>(DefaultEdge.class);
	
	public Structure() {
		super(DefaultEdge.class);
	}

	@Override
	public DefaultEdge addEdge(IArtifact sourceVertex, IArtifact targetVertex) {
		if (sourceVertex instanceof ObservationGroup || targetVertex instanceof ObservationGroup) {
			artifactStructure.addVertex(sourceVertex);
			artifactStructure.addVertex(targetVertex);
			return artifactStructure.addEdge(sourceVertex, targetVertex);
		} 
		return super.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addVertex(IArtifact v) {
		if (v instanceof ObservationGroup) {
			return artifactStructure.addVertex(v);
		}
		return super.addVertex(v);
	}

	/**
	 * Lookup the parent of a known group.
	 * 
	 * @param observation
	 * @return
	 */
	public IDirectObservation getGroupParent(ObservationGroup observation) {
		for (DefaultEdge edge : this.artifactStructure.outgoingEdgesOf(observation)) {
			IArtifact source = this.artifactStructure.getEdgeTarget(edge);
			if (source instanceof IDirectObservation) {
				return (IDirectObservation) source;
			}
		}
		return null;
	}

	
	
}

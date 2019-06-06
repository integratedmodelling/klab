package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
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

	/**
	 * Reconstruct the artifact child hierarchy with the groups instead of the direct observations.
	 * 
	 * @param observation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IArtifact> getChildArtifacts(IObservation artifact) {
		if (artifact instanceof ObservationGroup) {
			return IteratorUtils.toList(((ObservationGroup)artifact).iterator());
		}
		List<IArtifact> ret = new ArrayList<>();
		for (DefaultEdge edge : incomingEdgesOf(artifact)) {
			Set<String> groupIds = new HashSet<>();
			IArtifact source = getEdgeSource(edge);
			if (source instanceof DirectObservation) {
				ObservationGroup group = ((DirectObservation)source).getGroup();
				if (group != null && !groupIds.contains(group.getId())) {
					ret.add(group);
					groupIds.add(group.getId());
				}
			} else if (source instanceof IObservation) {
				ret.add((IObservation)source);
			}
		}

		return ret;
	}
	
}

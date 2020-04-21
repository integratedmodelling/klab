package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
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
 * <p>
 * The <b>logical structure</b> only links continuants, events and
 * relationships: this means that qualities will never link to processes
 * (although this should be split between qualities created and affected and may
 * change) and object children are linked to their parent subjects and not to
 * their artifacts (observation groups).
 * <p>
 * The <b>artifact structure</b> contains processes and observation groups; all
 * countables linked to a subject have an observation group in the middle.
 * 
 * 
 * @author ferdinando.villa
 *
 */
public class Structure implements IArtifact.Structure {

	private Graph<IArtifact, DefaultEdge> logicalStructure = new DefaultDirectedGraph<IArtifact, DefaultEdge>(
			DefaultEdge.class);
	private Graph<IArtifact, DefaultEdge> artifactStructure = new DefaultDirectedGraph<IArtifact, DefaultEdge>(
			DefaultEdge.class);

	public Structure() {
//		super(DefaultEdge.class);
	}

	public IArtifact getRootArtifact() {
		for (IArtifact artifact : logicalStructure.vertexSet()) {
			if (logicalStructure.outgoingEdgesOf(artifact).size() == 0) {
				return artifact;
			}
		}
		return null;
	}

	public Collection<IArtifact> getLogicalChildren(IArtifact parent) {
		List<IArtifact> ret = new ArrayList<>();
		for (DefaultEdge edge : logicalStructure.incomingEdgesOf(parent)) {
			ret.add(logicalStructure.getEdgeSource(edge));
		}
		return ret;
	}

	public Collection<IArtifact> getArtifactChildren(IArtifact parent) {
		List<IArtifact> ret = new ArrayList<>();
		for (DefaultEdge edge : artifactStructure.incomingEdgesOf(parent)) {
			ret.add(artifactStructure.getEdgeSource(edge));
		}
		return ret;
	}

	public IArtifact getLogicalParent(IArtifact child) {
		for (DefaultEdge edge : logicalStructure.outgoingEdgesOf(child)) {
			return logicalStructure.getEdgeTarget(edge);
		}
		return null;
	}

	public IArtifact getArtifactParent(IArtifact child) {
		for (DefaultEdge edge : artifactStructure.outgoingEdgesOf(child)) {
			return artifactStructure.getEdgeTarget(edge);
		}
		return null;
	}

	/**
	 * Link observations, using artifact logics, as specified during
	 * contextualization. The artifact structure will contains the graph as
	 * specified. The logical structure will skip folders and processes, always
	 * attributing observations to their parent observations and linking process
	 * qualities to subjects.
	 * 
	 * @param childArtifact
	 * @param parentArtifact
	 */
	public void link(IArtifact childArtifact, IArtifact parentArtifact) {

		if (childArtifact instanceof IState && parentArtifact instanceof ObservationGroup) {
			System.out.println("FUUUUUUUUUCK");
		}
		
		/*
		 * artifact structure is verbatim
		 */
		artifactStructure.addVertex(childArtifact);
		artifactStructure.addVertex(parentArtifact);
		artifactStructure.addEdge(childArtifact, parentArtifact);

		// if we're linking a folder to something, all done
		if (childArtifact instanceof ObservationGroup) {
			return;
		}

		// otherwise link, possibly skipping the non-logical level
		if (parentArtifact instanceof ObservationGroup || parentArtifact instanceof IProcess) {
			parentArtifact = getArtifactParent(parentArtifact);
		}

		// add process children but not folders
		logicalStructure.addEdge(childArtifact, parentArtifact);
	}

//	@Override
	public void add(IArtifact v) {
		artifactStructure.addVertex(v);
		if (!(v instanceof ObservationGroup)) {
			logicalStructure.addVertex(v);
		}
	}

//	/**
//	 * Lookup the parent of a known group.
//	 * 
//	 * @param observation
//	 * @return
//	 */
//	public IDirectObservation getGroupParent(ObservationGroup observation) {
//		for (DefaultEdge edge : this.artifactStructure.outgoingEdgesOf(observation)) {
//			IArtifact source = this.artifactStructure.getEdgeTarget(edge);
//			if (source instanceof IDirectObservation) {
//				return (IDirectObservation) source;
//			}
//		}
//		return null;
//	}

//	/**
//	 * Reconstruct the artifact child hierarchy with the groups instead of the direct observations.
//	 * 
//	 * @param observation
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<IArtifact> getChildArtifacts(IObservation artifact) {
//		
//		if (artifact instanceof ObservationGroup) {
//			return IteratorUtils.toList(((ObservationGroup)artifact).iterator());
//		}
//		
//		if (artifact instanceof ObservationGroupView) {
//			return IteratorUtils.toList(((ObservationGroupView)artifact).iterator());
//		}		
//		
//		List<IArtifact> ret = new ArrayList<>();
//		Set<String> groupIds = new HashSet<>();
//		for (DefaultEdge edge : logicalStructure.incomingEdgesOf(artifact)) {
//			IArtifact source = logicalStructure.getEdgeSource(edge);
//			if (source instanceof DirectObservation) {
//				ObservationGroup group = ((DirectObservation)source).getGroup();
//				if (group != null && !groupIds.contains(group.getId())) {
//					ret.add(group);
//					groupIds.add(group.getId());
//				}
//			} else if (source instanceof IObservation) {
//				ret.add((IObservation)source);
//			}
//		}
//		return ret;
//	}

	public void replace(IArtifact original, IArtifact replacement) {

		// TODO deal with groups - really unnecessary at the moment, but incomplete.

		Set<IArtifact> outgoing = new HashSet<>();
		Set<IArtifact> incoming = new HashSet<>();
		if (logicalStructure.containsVertex(original)) {
			for (DefaultEdge edge : logicalStructure.outgoingEdgesOf(original)) {
				outgoing.add(logicalStructure.getEdgeTarget(edge));
			}
			for (DefaultEdge edge : logicalStructure.incomingEdgesOf(original)) {
				incoming.add(logicalStructure.getEdgeSource(edge));
			}
			logicalStructure.removeVertex(original);
		}

		logicalStructure.addVertex(replacement);
		for (IArtifact target : outgoing) {
			logicalStructure.addEdge(replacement, target);
		}
		for (IArtifact target : incoming) {
			logicalStructure.addEdge(target, replacement);
		}
	}

	public void removeArtifact(IArtifact object) {
		if (logicalStructure.containsVertex(object)) {
			logicalStructure.removeVertex(object);
		}
		if (artifactStructure.containsVertex(object)) {
			artifactStructure.removeVertex(object);
		}
	}

}

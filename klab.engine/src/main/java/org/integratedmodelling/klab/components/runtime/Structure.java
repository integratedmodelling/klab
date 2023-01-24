package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import groovy.lang.GroovyObjectSupport;

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
public class Structure extends GroovyObjectSupport implements IArtifact.Structure {

	private Graph<IArtifact, DefaultEdge> logicalStructure = new DefaultDirectedGraph<IArtifact, DefaultEdge>(
			DefaultEdge.class);
	private Graph<IArtifact, DefaultEdge> artifactStructure = new DefaultDirectedGraph<IArtifact, DefaultEdge>(
			DefaultEdge.class);
	private Map<String, IProcess> derivedOccurrents = new HashMap<>();
	
	@Override
	public IArtifact getRootArtifact() {
		for (IArtifact artifact : logicalStructure.vertexSet()) {
			if (logicalStructure.outgoingEdgesOf(artifact).size() == 0) {
				return artifact;
			}
		}
		return null;
	}

	@Override
	public Collection<IArtifact> getLogicalChildren(IArtifact parent) {
		List<IArtifact> ret = new ArrayList<>();
		for (DefaultEdge edge : logicalStructure.incomingEdgesOf(parent)) {
			ret.add(logicalStructure.getEdgeSource(edge));
		}
		return ret;
	}

	@Override
	public Collection<IArtifact> getArtifactChildren(IArtifact parent) {
		List<IArtifact> ret = new ArrayList<>();
		for (DefaultEdge edge : artifactStructure.incomingEdgesOf(parent)) {
			ret.add(artifactStructure.getEdgeSource(edge));
		}
		return ret;
	}

	@Override
	public IArtifact getLogicalParent(IArtifact child) {
		if (child instanceof ObservationGroup) {
			return getArtifactParent(child);
		}
		for (DefaultEdge edge : logicalStructure.outgoingEdgesOf(child)) {
			return logicalStructure.getEdgeTarget(edge);
		}
		return null;
	}

	@Override
	public IArtifact getArtifactParent(IArtifact child) {
		if (!artifactStructure.vertexSet().contains(child)) {
			return null;
		}
		for (DefaultEdge edge : artifactStructure.outgoingEdgesOf(child)) {
			return artifactStructure.getEdgeTarget(edge);
		}
		return null;
	}

	@Override
	public boolean contains(IArtifact artifact) {
		return artifactStructure.containsVertex(artifact);
	}

	/**
	 * Link observations, using artifact logics, as specified during
	 * contextualization. The artifact structure will contains the graph as
	 * specified, attributing process states to the parent subject. The logical
	 * structure will skip folders and processes, always attributing observations to
	 * their parent observations and linking process qualities to subjects.
	 * 
	 * @param childArtifact
	 * @param parentArtifact
	 */
	public void link(IArtifact childArtifact, IArtifact parentArtifact) {

		// these are redirected no matter what.
		if (parentArtifact instanceof IProcess) {

			/*
			 * we keep the information about the artifact being owned by a process, so
			 * that we can tell the occurrence when it's relevant.
			 */
			this.derivedOccurrents.put(childArtifact.getId(), (IProcess)parentArtifact);

			parentArtifact = getArtifactParent(parentArtifact);
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
		if (parentArtifact instanceof ObservationGroup) {
			parentArtifact = getArtifactParent(parentArtifact);
		}

		// add process children but not folders
		logicalStructure.addVertex(childArtifact);
		logicalStructure.addVertex(parentArtifact);
		logicalStructure.addEdge(childArtifact, parentArtifact);
	}

	public void add(IArtifact v) {
		artifactStructure.addVertex(v);
		if (!(v instanceof ObservationGroup)) {
			logicalStructure.addVertex(v);
		}
	}

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
		derivedOccurrents.remove(object.getId());		
	}
	
	public void swap(IArtifact original, IArtifact replacement) {

		if (logicalStructure.containsVertex(original)) {
			List<IArtifact> sources = new ArrayList<>();
			List<IArtifact> targets = new ArrayList<>();
			for (DefaultEdge edge : logicalStructure.incomingEdgesOf(original)) {
				sources.add(logicalStructure.getEdgeSource(edge));
			}
			for (DefaultEdge edge : logicalStructure.outgoingEdgesOf(original)) {
				targets.add(logicalStructure.getEdgeTarget(edge));
			}
			logicalStructure.removeVertex(original);
			logicalStructure.addVertex(replacement);
			for (IArtifact source : sources) {
				logicalStructure.addEdge(source, replacement);
			}
			for (IArtifact target : targets) {
				logicalStructure.addEdge(replacement, target);
			}
		}
		if (artifactStructure.containsVertex(original)) {
			List<IArtifact> sources = new ArrayList<>();
			List<IArtifact> targets = new ArrayList<>();
			for (DefaultEdge edge : artifactStructure.incomingEdgesOf(original)) {
				sources.add(artifactStructure.getEdgeSource(edge));
			}
			for (DefaultEdge edge : artifactStructure.outgoingEdgesOf(original)) {
				targets.add(artifactStructure.getEdgeTarget(edge));
			}
			artifactStructure.removeVertex(original);
			artifactStructure.addVertex(replacement);
			for (IArtifact source : sources) {
				artifactStructure.addEdge(source, replacement);
			}
			for (IArtifact target : targets) {
				artifactStructure.addEdge(replacement, target);
			}
		}
	}
	
	@Override
	public IProcess getOwningProcess(IArtifact artifact) {
		return derivedOccurrents.get(artifact.getId());
	}

    @Override
    public IObserver<?> getObserver(IArtifact artifact) {
        // TODO Auto-generated method stub
        return null;
    }

}

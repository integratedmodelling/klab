package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;

/**
 * Collects ObservationReference and ObservationChange messages to keep graphs
 * describing a context's structure. The graphs are updated by subscribing to
 * events that are sent only when a user calls expand() on a graph.
 * 
 * @author Ferd
 *
 */
public abstract class ContextMonitor {

	/**
	 * The context graph always contains groups for objects below root. The
	 * getChildren() call has the option of collapsing the group if the size is 1.
	 * <p>
	 * Calling expand() will physically insert or remove the child observations and
	 * should be done when the tree is expanded. Otherwise the count of children
	 * will be up to date but the children won't physically be in the tree.
	 * 
	 * @author Ferd
	 *
	 */
	public class ContextGraph extends DefaultDirectedGraph<ObservationReference, DefaultEdge> {

		private static final long serialVersionUID = 5610896406683973693L;

		private ObservationReference rootNode;

		public ContextGraph() {
			super(DefaultEdge.class);
		}

		/**
		 * 
		 * @return
		 */
		public ObservationReference getRootNode() {
			return rootNode;
		}

		/**
		 * Expand or collapse an observation's children, subscribing or unsubscribing to
		 * any changes that involve the resulting tree.
		 * 
		 * @param observation
		 * @param open
		 * @return
		 */
		public void expand(ObservationReference observation, boolean open) {
			subscribe(this, observation, open);
			if (!open) {
				// remove children
				List<ObservationReference> children = new ArrayList<>();
				for (DefaultEdge edge : incomingEdgesOf(observation)) {
					children.add(getEdgeSource(edge));
				}
				removeAllVertices(children);
			}
		}

		public List<ObservationReference> getChildren(ObservationReference observation, boolean collapseSingletons) {
			
			List<ObservationReference> ret = new ArrayList<>();

			if (observation.getChildrenCount() != incomingEdgesOf(observation).size()) {
				/*
				 * observation has been updated and now has more children than are in the tree
				 */
				updateChildren(this, observation);
			}

			for (DefaultEdge edge : incomingEdgesOf(observation)) {
				ObservationReference child = getEdgeSource(edge);
				if (collapseSingletons && child.getGeometryTypes().contains(GeometryType.GROUP)
						&& child.getChildrenCount() == 1) {
					ret.addAll(getChildren(observation, collapseSingletons));
				} else {
					ret.add(child);
				}
			}
			return ret;
		}

		public ObservationReference getParent(ObservationReference element, boolean collapseSingletons) {

			for (DefaultEdge edge : outgoingEdgesOf(element)) {
				// yes, there is one or zero
				ObservationReference target = getEdgeTarget(edge);
				if (collapseSingletons && target.getGeometryTypes().contains(GeometryType.GROUP)
						&& target.getChildrenCount() == 1) {
					target = getParent(target, collapseSingletons);
				}
				return target;
			}
			// root
			return null;
		}

	}

	private Map<String, ContextGraph> graphs = new HashMap<>();
	private Map<String, Map<String, ObservationReference>> catalogs = new HashMap<>();

	public ContextGraph getGraph(String rootContextId) {
		return graphs.get(rootContextId);
	}

	/**
	 * Subscribe or unsubscribe to any change events concerning a node of the tree
	 * or its siblings.
	 * 
	 * @param contextGraph
	 * @param observation
	 * @param open
	 */
	protected abstract void subscribe(ContextGraph contextGraph, ObservationReference observation, boolean open);

	protected abstract List<ObservationReference> retrieveChildren(ObservationReference observation, int offset,
			int count);

	/**
	 * Define to retrieve the children of the passed observation from the backend.
	 * For now gets them all, which is obviously crazy.
	 * 
	 * @param observation
	 */
	private void updateChildren(ContextGraph graph, ObservationReference observation) {
		List<ObservationReference> children = retrieveChildren(observation, 0, -1);
		graph.removeAllEdges(graph.incomingEdgesOf(observation));
		for (ObservationReference child : children) {
			// don't update che count
			graph.addVertex(child);
			graph.addEdge(child, observation);
		}
	}

	/**
	 * Register a new observation notified by the remote engine.
	 * 
	 * @param observation
	 */
	public void register(ObservationReference observation) {
		
		String parentId = observation.getParentArtifactId() == null ? observation.getParentId()
				: observation.getParentArtifactId();

		if (parentId == null) {
			ContextGraph graph = new ContextGraph();
			Map<String, ObservationReference> catalog = new HashMap<>();
			graph.addVertex(observation);
			graph.rootNode = observation;
			catalog.put(observation.getId(), observation);
			graphs.put(observation.getId(), graph);
			catalogs.put(observation.getId(), catalog);
		} else {
			ContextGraph graph = graphs.get(observation.getRootContextId());
			Map<String, ObservationReference> catalog = catalogs.get(observation.getRootContextId());
			catalog.put(observation.getId(), observation);
			ObservationReference parent = catalog.get(parentId);
			// when notified directly, we don't send a change so keep the child count updated
			parent.setChildrenCount(parent.getChildrenCount() + 1);
			graph.addVertex(observation);
			graph.addEdge(observation, parent);
		}
	}

	/**
	 * Register a change to an observation notified by the remote engine.
	 * 
	 * @param observationChange
	 */
	public void register(ObservationChange observationChange) {

		/*
		 * find the observation
		 */
		Map<String, ObservationReference> catalog = catalogs.get(observationChange.getContextId());
		if (catalog != null) {
			ObservationReference ref = catalog.get(observationChange.getId());
			if (ref != null) {
				ref.applyChange(observationChange);
			}
		}

	}

}

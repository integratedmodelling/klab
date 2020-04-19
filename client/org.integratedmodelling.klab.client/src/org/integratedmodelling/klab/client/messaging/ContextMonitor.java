package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.ObservationReference;

/**
 * Collects ObservationReference and ObservationChange messages to keep graphs
 * describing a context's structure. The graphs are updated by subscribing to
 * events that are sent only when a user calls expand() on a graph.
 * 
 * @author Ferd
 *
 */
public class ContextMonitor {

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

		public ContextGraph() {
			super(DefaultEdge.class);
		}

		/**
		 * Expand or collapse an observation's children, subscribing or unsubscribing to
		 * any changes that involve the resulting tree.
		 * 
		 * @param observation
		 * @param open
		 * @return
		 */
		public boolean expand(ObservationReference observation, boolean open) {
			return false;
		}

		public List<IObservation> getChildren(ObservationReference observation, boolean collapseSingletons) {
			List<IObservation> ret = new ArrayList<>();
			return ret;
		}

	}

	private Map<String, ContextGraph> graphs = new HashMap<>();
	private Map<String, Map<String, ObservationReference>> catalogs = new HashMap<>();

	public ContextGraph getGraph(String rootContextId) {
		return graphs.get(rootContextId);
	}

	/**
	 * Register a new observation notified by the remote engine.
	 * 
	 * @param observation
	 */
	public void register(ObservationReference observation) {

		if (observation.getParentArtifactId() == null) {
			ContextGraph graph = new ContextGraph();
			Map<String, ObservationReference> catalog = new HashMap<>();
			graph.addVertex(observation);
			catalog.put(observation.getId(), observation);
			graphs.put(observation.getId(), graph);
			catalogs.put(observation.getId(), catalog);
		} else {
			ContextGraph graph = graphs.get(observation.getRootContextId());
			Map<String, ObservationReference> catalog = catalogs.get(observation.getRootContextId());
			catalog.put(observation.getId(), observation);
			ObservationReference parent = catalog.get(observation.getParentArtifactId());
			graph.addEdge(observation, parent);
		}

		// TODO call listeners
	}

	/**
	 * Register a change to an observation notified by the remote engine.
	 * 
	 * @param observation
	 */
	public void register(ObservationChange observation) {

		/*
		 * find the observation
		 */
		Map<String, ObservationReference> catalog = catalogs.get(observation.getContextId());
		if (catalog != null) {
			ObservationReference ref = catalog.get(observation.getId());
			if (ref != null) {
				ref.applyChange(observation);
				
				// TODO call listeners
			}
		}

	}

}

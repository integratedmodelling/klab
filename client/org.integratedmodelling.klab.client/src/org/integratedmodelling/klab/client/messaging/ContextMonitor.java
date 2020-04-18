package org.integratedmodelling.klab.client.messaging;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
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

	public class ContextGraph extends DefaultDirectedGraph<ObservationReference, DefaultEdge> {

		private static final long serialVersionUID = 5610896406683973693L;

		public ContextGraph() {
			super(DefaultEdge.class);
		}

		public boolean expand(String observationId) {
			return false;
		}

		public boolean expand(ObservationReference observation) {
			return false;
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
		
		System.out.println("SHITTEBBUM " + observation);
		
		if (observation.getParentId() == null) {
			ContextGraph graph = new ContextGraph();
			Map<String, ObservationReference> catalog = new HashMap<>();
			graph.addVertex(observation);
			catalog.put(observation.getId(), observation);
			graphs.put(observation.getId(), graph);
			catalogs.put(observation.getId(), catalog);
		} else {
			ContextGraph graph = graphs.get(observation.getRootContextId());
			Map<String, ObservationReference> catalog = catalogs.get(observation.getRootContextId());

		}
	}

	/**
	 * Register a change to an observation notified by the remote engine.
	 * 
	 * @param observation
	 */
	public void register(ObservationChange observation) {
		
	}

}

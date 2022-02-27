package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A tree of dataflows, possibly connected through internal links. Root nodes
 * are arranged chronologically and correspond to user-generated observations.
 * Child nodes result from the resolution of instantiated countables.
 * <p>
 * Each context keeps one of these and adds to it as the user makes new
 * observations. Dataflows are added after resolution, independent of whether
 * the contextualization after it is successful. The contextualization strategy
 * for a context is one and pre-exists everything else, including the resolution
 * scope and any observation.
 * 
 * The contextualization strategy also holds the dataflow cache that allows the
 * resolver to pick a specific dataflow for repeated contextualizations (e.g.
 * when multiple instantiated objects are resolved, or for deferred observables
 * within distributed resolutions) based on the original coverage instead of
 * creating a new one.
 * 
 * FIXME this should host a single root dataflow (and potentially a map of
 * individual components), not a graph, as the hierarchy is kept in the dataflow
 * itself.
 * 
 * @author Ferd
 *
 */
public class ContextualizationStrategy extends DefaultDirectedGraph<Dataflow, DefaultEdge> {

    private String id = NameGenerator.shortUUID();
	private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;
	private Map<String, ElkConnectableShape> nodes = new HashMap<>();
	private Map<String, Element> elements = new HashMap<>();
	private Map<String, String> node2dataflowId = new HashMap<>();
	private Dataflow rootDataflow;
	private Map<ObservedConcept, List<Pair<ICoverage, Dataflow>>> dataflowCache = new HashMap<>();

	public ContextualizationStrategy() {
		super(DefaultEdge.class);
	}

	List<Dataflow> rootNodes = new ArrayList<>();
    private IRuntimeScope scope;

	private static final long serialVersionUID = 1L;

	public void add(Dataflow dataflow) {

		if (this.rootDataflow == null) {
			this.rootDataflow = dataflow;
		}

		synchronized (rootNodes) {
			addVertex(dataflow);
			rootNodes.add(dataflow);
		}
	}

	public void add(Dataflow dataflow, Dataflow parent) {
		synchronized (this) {
			addVertex(dataflow);
			addEdge(parent, dataflow);
		}
	}

	public String getKdl() {
		return rootDataflow == null ? null : rootDataflow.getKdlCode();
	}

	public static String getElkGraph(Dataflow dataflow, IRuntimeScope scope) {
		ContextualizationStrategy strategy = new ContextualizationStrategy();
		strategy.setScope(scope);
		strategy.add(dataflow);
		return strategy.getElkGraph();
	}

	/**
	 * Return or compute all the dataflows needed to resolve the passed observable
	 * in the passed mode, scale, scope and context. If >1, the dataflows will cover
	 * different portions of the scale. Empty dataflows mean no way to resolve this
	 * observable.
	 * 
	 * @param observable
	 * @param mode
	 * @param scale
	 * @param context
	 * @return
	 */
	public Dataflow getDataflow(IObservable observable, IResolutionScope.Mode mode, IScale scale,
			IDirectObservation context, Supplier<Dataflow> ifMissing) {

		Dataflow ret = null;
		ObservedConcept key = new ObservedConcept((Observable) observable, mode,
				context == null ? null : context.getObservable());
		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache.get(key);

		boolean found = false;
		if (pairs != null) {
			found = true;
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					// TODO only feasible if we copy the dataflow and localize afterwards. Just keep
					// the
					// empty ones, otherwise the scheduling won't work.
					if (pair.getSecond().isTrivial()) {
						ret = pair.getSecond();
						break;
					}
				}
			}
		}

		if (ret == null) {
			ret = ifMissing.get();
			if (pairs == null) {
				pairs = new ArrayList<>();
				dataflowCache.put(key, pairs);
			}
			if (ret != null) {
				pairs.add(new Pair<>(ret.getCoverage(), ret));
			}
		}

		return ret;
	}

	public String getElkGraph() {

		List<Flowchart> flowcharts = new ArrayList<>();

		// if (json == null) {
		synchronized (this) {

			elements.clear();
			nodes.clear();
			node2dataflowId.clear();
			flowcharts.clear();

			ElkNode root = kelk.createGraph(id);

			/*
			 * first create the flowcharts and link them, creating outputs for any exported
			 * observation. Then make the graph from the linked flowcharts.
			 */
			List<Triple<String, String, String>> connections = new ArrayList<>();
			for (Dataflow df : rootNodes) {

				Flowchart current = Flowchart.create(df, scope);

				for (String input : current.getExternalInputs().keySet()) {
					for (Flowchart previous : flowcharts) {
						String output = previous.pullOutput(input);
						if (output != null) {
							connections.add(new Triple<>(input, output, current.getExternalInputs().get(input)));
						}
					}
				}

				flowcharts.add(current);
			}

			// new nodes
			ElkNode contextNode = null;
			for (Flowchart flowchart : flowcharts) {
				DataflowGraph graph = new DataflowGraph(flowchart, this, kelk);
				// TODO children - recurse on secondary contextualizations
				ElkNode tgraph = graph.getRootNode();
				if (tgraph != null) {
					root.getChildren().add(tgraph);
					if (contextNode == null) {
						contextNode = tgraph;
					} else {
						int i = 0;
						for (ElkConnectableShape outPort : graph.getOutputs()) {
							kelk.createSimpleEdge(outPort, contextNode, "ctx" + outPort.getIdentifier() + "_" + i);
						}
					}
				}
			}

			for (Triple<String, String, String> connection : connections) {
				kelk.createSimpleEdge(nodes.get(connection.getSecond()), nodes.get(connection.getThird()), "external."
						+ connection.getSecond() + "." + connection.getThird() + "." + connection.getFirst());
			}

			RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
			engine.layout(root, new BasicProgressMonitor());

			String json = ElkGraphJson.forGraph(root).omitLayout(false).omitZeroDimension(true).omitZeroPositions(true)
					.shortLayoutOptionKeys(true).prettyPrint(true).toJson();

			// System.out.println(json);
			return json;
		}
		// }

		// return null;
	}

	public Map<String, ElkConnectableShape> getNodes() {
		synchronized (rootNodes) {
			return nodes;
		}
	}

	public Map<String, Element> getElements() {
		synchronized (rootNodes) {
			return elements;
		}
	}

	public Map<String, String> getComputationToNodeIdTable() {
		synchronized (rootNodes) {
			return node2dataflowId;
		}
	}

	public Element findDataflowElement(String nodeId) {
		synchronized (rootNodes) {
			return elements.get(nodeId);
		}
	}

    public void setScope(IRuntimeScope runtimeScope) {
        this.scope = runtimeScope;
    }

}

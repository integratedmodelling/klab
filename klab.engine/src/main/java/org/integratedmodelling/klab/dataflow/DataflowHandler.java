package org.integratedmodelling.klab.dataflow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;

/**
 * The root contextualization scope ultimately handles a dataflow that can be
 * incrementally defined. This base class contains all dataflow bookkeeping
 * facilities. The runtime contextualization scope derives from this.
 * 
 * Ultimately this will also be in charge of loading dataflow resources.
 * 
 * The derivation from Parameters is not necessary here, but because this serves
 * as base class for AbstractContextualizationScope and Java has no multiple
 * inheritance, we add it here for lack of better options.
 * 
 * @author Ferd
 *
 */
public class DataflowHandler extends Parameters<String> {

	private String id;
	private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;
	private Map<String, ElkConnectableShape> nodes = new HashMap<>();
	private Map<String, Element> elements = new HashMap<>();
	private Map<String, String> node2dataflowId = new HashMap<>();
	private Dataflow rootDataflow;

	/**
	 * These are created and run during resolution, before the root dataflow exists.
	 * Normally used to resolve abstract predicates. They are "orphans" when
	 * created, and the root dataflow will adopt them as soon as it is created.
	 */
	private List<Dataflow> preContextualizationDataflows = new ArrayList<>();
	private Map<ObservedConcept, List<Pair<ICoverage, Dataflow>>> dataflowCache = new HashMap<>();
	List<Dataflow> rootNodes = new ArrayList<>();
	
	public DataflowHandler() {
		this.id = NameGenerator.shortUUID();
		this.kelk = KlabElkGraphFactory.keINSTANCE;
		this.nodes = new HashMap<>();
		this.elements = new HashMap<>();
		this.node2dataflowId = new HashMap<>();
		this.preContextualizationDataflows = new ArrayList<>();
		this.dataflowCache = new HashMap<>();
		this.rootNodes = new ArrayList<>();
	}
	
	protected DataflowHandler(DataflowHandler other) {
		copyDataflowInfo(other);
	}
	
	protected void copyDataflowInfo(DataflowHandler other) {
		this.id = other.id;
		this.kelk = other.kelk;
		this.nodes = other.nodes;
		this.elements = other.elements;
		this.node2dataflowId = other.node2dataflowId;
		this.preContextualizationDataflows = other.preContextualizationDataflows;
		this.dataflowCache = other.dataflowCache;
		this.rootNodes = other.rootNodes;
	}

	public String getKdl() {
		if (rootDataflow == null) {
			return "";
		} 
		System.out.println(rootDataflow.dump());
		return rootDataflow.getKdlCode();
	}

	public static String getElkGraph(Dataflow dataflow, IRuntimeScope scope) {
		DataflowHandler strategy = new DataflowHandler();
		strategy.rootDataflow = dataflow;
		return strategy.getElkGraph(scope);
	}

	public void setRootDataflow(Dataflow dataflow) {
		this.rootDataflow = dataflow;
		dataflow.actuators.addAll(0, preContextualizationDataflows);
	}

	public void addPrecontextualizationDataflow(Dataflow dataflow, Dataflow root) {
		if (root == null) {
			preContextualizationDataflows.add(dataflow);
		} else {
			root.actuators.add(dataflow);
		}
	}

	public void exportDataflow(String baseName, File directory) {

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
			IDirectObservation context, Function<IScale, Dataflow> ifMissing) {

		Dataflow ret = null;
		ObservedConcept key = new ObservedConcept((Observable) observable, mode,
				context == null ? null : context.getObservable());
		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache.get(key);

		if (pairs != null) {
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					ret = pair.getSecond();
					break;
				}
			}
		}

		if (ret == null) {
			ret = ifMissing.apply(scale);
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

	public String getElkGraph(IRuntimeScope scope) {

		List<Flowchart> flowcharts = new ArrayList<>();

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

}

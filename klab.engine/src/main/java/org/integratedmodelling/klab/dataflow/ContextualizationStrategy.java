package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A tree of dataflows, possibly connected through internal links. Root nodes
 * are arranged chronologically and correspond to user-generated observations.
 * Child nodes result from the resolution of instantiated countables.
 * <p>
 * Each context keeps one of these and adds to it as the user makes new
 * observations. Dataflows are added after resolution, independent of whether
 * the contextualization after it is successful.
 * 
 * @author Ferd
 *
 */
public class ContextualizationStrategy extends DefaultDirectedGraph<Dataflow, DefaultEdge> {

	String id = NameGenerator.shortUUID();
	private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;
	private Map<String, ElkNode> nodes = new HashMap<>();
	String json = null;

	public ContextualizationStrategy() {
		super(DefaultEdge.class);
	}

	List<Dataflow> rootNodes = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	List<Dataflow> getRootNodes() {
		return rootNodes;
	}

	public void add(Dataflow dataflow) {
		synchronized (this) {
			addVertex(dataflow);
			rootNodes.add(dataflow);
			json = null;
		}
	}

	public void add(Dataflow dataflow, Dataflow parent) {
		synchronized (this) {
			addVertex(dataflow);
			addEdge(parent, dataflow);
			json = null;
		}
	}

	public String getElkGraph() {

		if (json == null) {
			synchronized (this) {

				ElkNode root = kelk.createGraph(id);

				// new nodes
				for (Dataflow df : rootNodes) {
					DataflowGraph graph = new DataflowGraph(df, nodes, kelk);
					// TODO children - recurse
					root.getChildren().add(graph.getRootNode());
				}

				RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
				engine.layout(root, new BasicProgressMonitor());

				json = ElkGraphJson.forGraph(root).omitLayout(false).omitZeroDimension(true).omitZeroPositions(true)
						.shortLayoutOptionKeys(true).prettyPrint(true).toJson();

				// System.out.println(json);
			}
		}

		return json;
	}
}

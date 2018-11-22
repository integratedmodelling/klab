package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A tree of dataflows, possibly connected through internal links. Root nodes are arranged chronologically
 * and correspond to user-generated observations. Child nodes result from the resolution of instantiated
 * countables.
 * <p>
 * Each context keeps one of these and adds to it as the user makes new observations. Dataflows are added 
 * after resolution, independent of whether the contextualization after it is successful.
 * 
 * @author Ferd
 *
 */
public class ContextualizationStrategy extends DefaultDirectedGraph<Dataflow, DefaultEdge>{
	
	String id = NameGenerator.shortUUID();
	private ElkGraphFactory elk = ElkGraphFactory.eINSTANCE;
	private Map<String, ElkNode> nodes = new HashMap<>();
	
	public ContextualizationStrategy() {
		super(DefaultEdge.class);
	}

	List<Dataflow> rootNodes = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	List<Dataflow> getRootNodes() {
		return rootNodes;
	}
	
	public void add(Dataflow dataflow) {
		addVertex(dataflow);
		rootNodes.add(dataflow);
	}

	public void add(Dataflow dataflow, Dataflow parent) {
		addVertex(dataflow);
		addEdge(parent, dataflow);
	}
	
	public String getElkGraph() {

		ElkNode root = elk.createElkNode();
		root.setIdentifier(id);
		
		for (Dataflow df : rootNodes) {
			DataflowGraph graph = new DataflowGraph(df, nodes);
			// TODO children - recurse
			root.getChildren().add(graph.getRootNode());
		}
		
		// TODO these options are copied from the docs without thinking
		String ret = ElkGraphJson.forGraph(root).omitLayout(true).omitZeroDimension(true).omitZeroPositions(true)
				.shortLayoutOptionKeys(false).prettyPrint(true).toJson();
		
		System.out.println(ret);
		
		return ret;
	}
	
}

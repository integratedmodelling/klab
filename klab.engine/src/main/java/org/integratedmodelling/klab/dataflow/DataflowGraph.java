package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;

public class DataflowGraph {

	private ElkNode rootNode;
	private ElkGraphFactory elk = ElkGraphFactory.eINSTANCE;

	private Map<String, ElkNode> nodes = new HashMap<>();
	
	public DataflowGraph() {
	}

	public ElkNode getRootNode() {
		return rootNode;
	}

	public DataflowGraph(Dataflow dataflow) {
		merge(dataflow);
	}

	/**
	 * Compile a dataflow into its graph and set or add to the main graph. Can be
	 * called as many times as needed; inputs will be resolved from the existing
	 * nodes.
	 * 
	 * @param dataflow
	 */
	public void merge(Dataflow dataflow) {
		ElkNode node = compile(dataflow);
		if (rootNode == null) {
			rootNode = node;
		}
		// TODO the next node may be independent; in that case we should only add
		// another root if there were no connections between the
		// new and the previous.
	}

	public ElkNode compile(IActuator actuator) {
		
		ElkNode root = elk.createElkNode();

		root.setIdentifier(actuator.getName());
		
		// input ports
		for (IActuator input : actuator.getInputs()) {
			
		}
		
		// mediators
		
		// computation

		// output port(s) from the computation - >1 if there are additional observables in the associated model
		
		// properties
		
		
		return rootNode;
	}
	
	public String asJson() {
		// TODO these options are copied from the docs without thinking
		return ElkGraphJson.forGraph(rootNode)
                .omitLayout(true)
                .omitZeroDimension(true)
                .omitZeroPositions(true)
                .shortLayoutOptionKeys(false)
                .prettyPrint(true)
                .toJson();
	}
	

}

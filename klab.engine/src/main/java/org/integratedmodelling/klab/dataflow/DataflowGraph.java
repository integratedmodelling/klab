package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.utils.Pair;

public class DataflowGraph {

	private ElkNode rootNode;
	private ElkGraphFactory elk = ElkGraphFactory.eINSTANCE;

	private Map<String, ElkNode> nodes;

	public ElkNode getRootNode() {
		return rootNode;
	}

	public DataflowGraph(Dataflow dataflow, Map<String, ElkNode> nodes) {
		this.nodes = nodes;
		rootNode = compile(dataflow);
	}

	public ElkNode compile(Actuator actuator) {

		ElkNode root = elk.createElkNode();
		root.setIdentifier(actuator.getId());

		nodes.put(actuator.getName(), root);
		root.getLabels().add(label(actuator));

		// imports
		for (IActuator child : actuator.getActuators()) {
			if (child.isInput()) {
				ElkPort port = elk.createElkPort();
				port.getLabels().add(label(child.getAlias() == null ? child.getName() : child.getAlias(),
						((Actuator) child).getId()));
				root.getPorts().add(port);
			} else {
				ElkNode childNode = compile((Actuator) child);
				root.getChildren().add(childNode);
			}
		}

		// mediators
		for (Pair<IServiceCall, IComputableResource> mediator : actuator.getComputationStrategy()) {
			// compile in the actor with all its literal parameters; link artifacts from the
			// environment
		}

		// computation
		for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {

		}

		// output port(s) from the computation - >1 if there are additional observables
		// in the associated model

		// properties

		return root;
	}

	private ElkLabel label(Actuator actuator) {
		String aname = actuator instanceof Dataflow ? ((Dataflow) actuator).getDescription()
				: StringUtils.capitalize(actuator.getName().replaceAll("_", " "));
		return label(aname, actuator.getId() + "l");
	}

	private ElkLabel label(String name, String id) {
		ElkLabel ret = elk.createElkLabel();
		ret.setText(name);
		ret.setIdentifier(id);
		return ret;
	}

	public String asJson() {
		// TODO these options are copied from the docs without thinking
		return ElkGraphJson.forGraph(rootNode).omitLayout(true).omitZeroDimension(true).omitZeroPositions(true)
				.shortLayoutOptionKeys(false).prettyPrint(true).toJson();
	}

}

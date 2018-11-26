package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
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
		rootNode = compile(dataflow, null);
	}

	public ElkNode compile(Actuator actuator, ElkNode parent) {

		Map<String, ElkNode> localNodes = new HashMap<>();
		
		ElkNode root = nodes.get(actuator.getId());
		if (root == null) {
			
			root = ElkGraphUtil.createNode(parent);
			root.setIdentifier(actuator.getId());
			nodes.put(actuator.getName(), root);
			root.getLabels().add(label(actuator));

			// main output
			ElkPort mainOut = ElkGraphUtil.createPort(root);
			mainOut.setIdentifier(actuator.getId() + "_out1");
			// property goes on east edge
			mainOut.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);
			
			// imports
			for (IActuator child : actuator.getActuators()) {
				if (child.isInput()) {
					ElkPort port = ElkGraphUtil.createPort(root);
					port.getLabels().add(label(child.getAlias() == null ? child.getName() : child.getAlias(),
							((Actuator) child).getId()));
					port.setProperty(CoreOptions.PORT_SIDE, PortSide.WEST);
				} else {
					ElkNode childNode = compile((Actuator) child, root);
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), childNode);
				}
			}

			// mediators
			for (Pair<IServiceCall, IComputableResource> mediator : actuator.getComputationStrategy()) {
				// compile in the actor with all its literal parameters; link artifacts from the
				// environment
			}

			// computation
			ElkNode call = null;
			ElkPort out = null;
			for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {
				
				ElkNode previousCall = call;
				ElkPort previousOut = out;
				
				IPrototype prototype = Extensions.INSTANCE.getPrototype(actor.getFirst().getName());
				call = ElkGraphUtil.createNode(root);
				call.setIdentifier(actuator.getId() + "_" + prototype.getName());
				call.getLabels().add(label(prototype.getName(), call.getIdentifier() + "l"));
				for (Object kp : actor.getFirst().getParameters().entrySet()) {
					
				}
				// all computations have a main output
				out = ElkGraphUtil.createPort(call);
				out.setIdentifier(call.getIdentifier() + "_out");
				out.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);
				
				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
//					if (arg.is)
				}

				if (previousCall != null) {
					ElkPort input = ElkGraphUtil.createPort(call);
					input.setIdentifier(call.getIdentifier() + "_in");
					input.setProperty(CoreOptions.PORT_SIDE, PortSide.WEST);
					
					ElkEdge feed = ElkGraphUtil.createSimpleEdge(previousOut, input);
					feed.setIdentifier(call.getIdentifier() + "_feed");
				}				
				
				// each computation feeds into the next, the last feeds into the actuator's output
				nodes.put(actuator.getName(), root);
			}
			
			// complete the chain
			if (call != null) {
				// feed output of last computation in actuator's output
				ElkEdge outfeed = ElkGraphUtil.createSimpleEdge(out, mainOut);
				outfeed.setIdentifier(call.getIdentifier() + "_outlink");
			}



			// properties

		}

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

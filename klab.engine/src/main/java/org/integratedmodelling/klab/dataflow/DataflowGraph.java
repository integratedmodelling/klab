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
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Create an Elk graph starting from a Dataflow
 * No dimensions are given. If is needed, extends this class and override create[ElkObject] methods
 * @author Ferdinando Villa
 */
public class DataflowGraph {

	private ElkNode rootNode;
	private ElkGraphFactory elk = ElkGraphFactory.eINSTANCE;

	private Map<String, ElkNode> nodes;
	
	public DataflowGraph(Dataflow dataflow, Map<String, ElkNode> nodes) {
		this.nodes = nodes;
		rootNode = compile(dataflow, null);
	}
	
	public ElkNode getRootNode() {
		return rootNode;
	}
	
	public ElkNode compile(Actuator actuator, ElkNode parent) {

		Map<String, ElkNode> localNodes = new HashMap<>();
		
		ElkNode root = nodes.get(actuator.getId());
		if (root == null) {
			
			root = createNode(actuator.getId(), parent);
			nodes.put(actuator.getName(), root);
			root.getLabels().add(createLabel(actuator));

			// main output
			ElkPort mainOut = ElkGraphUtil.createPort(root);
			mainOut.setIdentifier(actuator.getId() + "_out1");
			// property goes on east edge
			mainOut.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);
			
			// imports
			for (IActuator child : actuator.getActuators()) {
				if (child.isInput()) {
					ElkPort port = ElkGraphUtil.createPort(root);
					port.getLabels().add(createLabel(child.getAlias() == null ? child.getName() : child.getAlias(),
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
				call = createNode(actuator.getId() + "_" + prototype.getName(), root);
				call.getLabels().add(createLabel(prototype.getName(), call.getIdentifier() + "l"));
				for (Object kp : actor.getFirst().getParameters().entrySet()) {
					
				}
				// all computations have a main output
				out = createPort(call.getIdentifier() + "_out", call, PortSide.EAST);
				
				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
//					if (arg.is)
				}

				if (previousCall != null) {
					ElkPort input = createPort(call.getIdentifier() + "_in", call, PortSide.WEST);
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
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	protected ElkNode createNode(String identifier, ElkNode parent) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		return node;
	}
	
	/**
	 * Create ElkPort with default behavior, if necessary override this method
	 * @param identifier the port identifier
	 * @param parent the parent of port
	 * @param side the side of port. One of org.eclipse.elk.core.options.PortSide value
	 * @return the newly created port 
	 */
	protected ElkPort createPort(String identifier, ElkNode parent, PortSide side) {
		ElkPort port = ElkGraphUtil.createPort(parent);
		port.setIdentifier(identifier);
		port.setProperty(CoreOptions.PORT_SIDE, side);
		return port;
	}

	private ElkLabel createLabel(Actuator actuator) {
		String aname = actuator instanceof Dataflow ? ((Dataflow) actuator).getDescription()
				: StringUtils.capitalize(actuator.getName().replaceAll("_", " "));
		return createLabel(aname, actuator.getId() + "l");
	}

	/**
	 * Create ElkLabel with default behavior, if necessary override this method 
	 * @param name
	 * @param id
	 * @return
	 */
	protected ElkLabel createLabel(String text, String identifier) {
		ElkLabel label = elk.createElkLabel();
		label.setText(text);
		label.setIdentifier(identifier);
		return label;
	}
	/*
	public String asJson() {
		// TODO these options are copied from the docs without thinking
		return ElkGraphJson.forGraph(rootNode).omitLayout(true).omitZeroDimension(false).omitZeroPositions(false)
				.shortLayoutOptionKeys(false).prettyPrint(true).toJson();
	}
	*/
}

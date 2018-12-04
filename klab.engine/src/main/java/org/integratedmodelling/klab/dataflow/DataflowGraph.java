package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
// import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Create an Elk graph starting from a Dataflow
 * No information about visualisation are given. If is needed, extends this class and override create[ElkObject] methods
 * @author Ferdinando Villa
 */
public class DataflowGraph {

	private ElkNode rootNode;
	private KlabElkGraphFactory kelk;

	private Map<String, ElkNode> nodes;
	
	public DataflowGraph(Dataflow dataflow, Map<String, ElkNode> nodes, KlabElkGraphFactory kelk) {
		this.nodes = nodes;
		this.kelk = kelk;
		rootNode = compile(dataflow, null);
	}
	
	public ElkNode getRootNode() {
		return rootNode;
	}
	
	public ElkNode compile(Actuator actuator, ElkNode parent) {

		Map<String, ElkConnectableShape> localNodes = new HashMap<>();
		
		ElkNode root = nodes.get(actuator.getDataflowId());
		if (root == null) {
	
			root = kelk.createNode(actuator.getDataflowId(), parent);
			nodes.put(actuator.getName(), root);
			root.getLabels().add(kelk.createLabel(actuator, root));

			// main output
			// property goes on east edge
			ElkPort mainOut = kelk.createPort(actuator.getDataflowId() + "_out1", root, PortSide.EAST);
			
			// imports
			ElkNode lastChild = null;
			for (IActuator child : actuator.getActuators()) {
				if (child.isInput()) {
					ElkPort port = kelk.createPort(actuator.getDataflowId() + "_" + child.getName() + "_in",
							root,
							PortSide.WEST);
					ElkLabel label = kelk.createLabel((child.getAlias() == null ? child.getName() : child.getAlias()), ((Actuator) child).getId(), port);
					port.getLabels().add(label);
					
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), port);
					ElkNode input = nodes.get(child.getName());
					if (input != null) {
						ElkPort extout = kelk.getOutputPort(input);
						/* ElkEdge impedge = */ kelk.createSimpleEdge(extout, port, null); // no identifier?
					}
				} else {
					lastChild = compile((Actuator) child, root);
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), lastChild);
				}
			}

			// mediators
			for (Pair<IServiceCall, IComputableResource> mediator : actuator.getComputationStrategy()) {
				/*
				 * Compile each mediator and 
				 */
			}

			// computation
			ElkNode call = null;
			ElkPort out = null;
			for (Pair<IServiceCall, IComputableResource> actor : actuator.getComputationStrategy()) {

				ElkNode previousCall = call;
				ElkPort previousOut = out;

				IPrototype prototype = Extensions.INSTANCE.getPrototype(actor.getFirst().getName());
				call = kelk.createNode(actor.getSecond().getDataflowId(), root);
				// HMMM this would be better for space utilization but the node won't resize to contain the label
//							call.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideCenter());
				call.getLabels().add(
						kelk.createLabel(Extensions.INSTANCE.getServiceLabel(actor.getFirst()), call.getIdentifier() + "l", call));


				for (Object kp : actor.getFirst().getParameters().entrySet()) {
					// TODO maybe not - leave parameters for detail pane
				}
				
				// all computations have a main output
				out = kelk.createPort(call.getIdentifier() + "_out", call, PortSide.EAST);

				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
					if (arg.getName().equals("arguments")) {
						for (Pair<String, Type> rp : actor.getSecond().getRequiredResourceNames()) {
							ElkConnectableShape provider = localNodes.get(rp.getFirst());
							if (provider != null) {
								ElkPort pout = kelk.getOutputPort(provider);
								if (pout != null) {
									ElkPort in = kelk.createPort(call.getIdentifier() + "_in_" + rp.getFirst(), call,
											PortSide.WEST);
									/* ElkEdge put = */ kelk.createSimpleEdge(pout, in, NameGenerator.shortUUID());
								}
							}
						}
					} else if (arg.isArtifact()) {
						// must match another actuator or an import
						ElkConnectableShape node = localNodes.get(arg.getName());
						if (node != null) {
							ElkPort inp = kelk.createPort(call.getIdentifier() + "_" + arg.getName() + "_in", call,
									PortSide.WEST);
							ElkPort fut = kelk.getOutputPort(node);
							/* ElkEdge feed = */ kelk.createSimpleEdge(fut, inp, call.getIdentifier() + "_" + arg.getName() + "_feed");
						}
					} else if (actor.getFirst().getParameters().containsKey(arg.getName())) {
						// build parameter node
					}
				}

				if (previousCall != null) {
					ElkPort input = kelk.createPort(call.getIdentifier() + "_in", call, PortSide.WEST);
					/* ElkEdge feed = */ kelk.createSimpleEdge(previousOut, input, call.getIdentifier() + "_feed");
				}

				// each computation feeds into the next, the last feeds into the actuator's
				// output
				nodes.put(actuator.getName(), root);
			}

			// complete the chain
			if (call != null) {
				// feed output of last computation in actuator's output
				/* ElkEdge outfeed = */ kelk.createSimpleEdge(out, mainOut, call.getIdentifier() + "_outlink");
			} else if (lastChild != null) {
				// feed output of last inner actuator in observation's output (note: it could be
				// >1 parallel
				// actuators and have either n outputs or merge into 1).
				ElkPort pout = kelk.getOutputPort(lastChild);
				if (pout != null) {
					/* ElkEdge outfeed = */ kelk.createSimpleEdge(pout, mainOut, actuator.getId() + "_obsout");
				}
			}

		}

		return root;
	}
	
	
	
	
	/*
	public String asJson() {
		// TODO these options are copied from the docs without thinking
		return ElkGraphJson.forGraph(rootNode).omitLayout(true).omitZeroDimension(false).omitZeroPositions(false)
				.shortLayoutOptionKeys(false).prettyPrint(true).toJson();
	}
	*/
}

package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkConnectableShape;
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
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;

/**
 * Create an Elk graph starting from a Dataflow. No information about
 * visualisation are given. If is needed, extends this class and override
 * create[ElkObject] methods
 * 
 * @author Ferdinando Villa
 */
public class DataflowGraph {

	private ElkNode rootNode;
	private KlabElkGraphFactory kelk;
	private Map<String, ElkPort> inputs = new HashMap<>();
	private Map<String, ElkConnectableShape> nodes;

	public DataflowGraph(Dataflow dataflow, Map<String, ElkConnectableShape> nodes, KlabElkGraphFactory kelk) {
		this.nodes = nodes;
		this.kelk = kelk;
//		Flowchart flowchart = Flowchart.create(dataflow, null);
//		System.out.println(flowchart.dump());
//		rootNode = compile(flowchart);
		compile(dataflow, null);
	}

	public ElkNode getRootNode() {
		return rootNode;
	}

	public ElkNode compile(Flowchart flowchart) {
		ElkNode ret = compile(flowchart.getRoot(), null, nodes);
		for (Pair<String, String> connection : flowchart.getConnections()) {
			kelk.createSimpleEdge(nodes.get(connection.getFirst()), nodes.get(connection.getSecond()), null);
		}
		return ret;
	}

	public ElkNode compile(Element element, ElkNode parentNode, Map<String, ElkConnectableShape> nodes) {

		ElkNode ret = element.getType() == Element.Type.ACTUATOR
				? kelk.createActuatorNode(element.getNodeId(), parentNode)
				: kelk.createServiceNode(element.getNodeId(), parentNode);
				
		nodes.put(element.getId(), ret);
		ret.getLabels().add(kelk.createLabel(element.getLabel(), element.getId(), ret));

		for (String input : element.getInputs()) {
			ElkPort port = kelk.createPort(input, ret, parentNode == null ? PortSide.NORTH : PortSide.WEST);
			ElkLabel label = kelk.createLabel(Path.getLast(input, '.'), input + ".label", port);
			port.getLabels().add(label);
			nodes.put(input, port);
		}

		for (String output : element.getOutputs()) {
			ElkPort port = kelk.createPort(output, ret, parentNode == null ? PortSide.SOUTH : PortSide.EAST);
			ElkLabel label = kelk.createLabel(Path.getLast(output, '.'), output + ".label", port);
			port.getLabels().add(label);
			nodes.put(output, port);
		}

		for (Element child : element.getChildren()) {
			compile(child, ret, nodes);
		}

		return ret;
	}

	public ElkNode compile(Actuator actuator, ElkNode parent) {

		Map<String, ElkConnectableShape> localNodes = new HashMap<>();

		if (actuator instanceof Dataflow && actuator.actuators.size() == 1 && !actuator.actuators.get(0).isComputed()
				&& actuator.actuators.get(0).getActuators().size() == 0 && !actuator.isInput()) {
			actuator = (Actuator) actuator.actuators.get(0);
		}

		ElkNode root = (ElkNode) nodes.get(actuator.getDataflowId());
		if (root == null) {

			root = kelk.createActuatorNode(actuator.getDataflowId(), parent);
			if (rootNode == null) {
				rootNode = root;
			}
			nodes.put(actuator.getName(), root);
			root.getLabels().add(kelk.createLabel(actuator, root));

			// main output
			// property goes on east edge
			// FIXME if there are no ports, nothing gets drawn
			ElkPort mainOut = kelk.createPort(actuator.getDataflowId() + "_out1", root, PortSide.EAST);

			// imports
			ElkNode lastChild = null;
			for (IActuator child : actuator.getActuators()) {
				if (((Actuator) child).isReference() || actuator.isInput()) {

					ElkPort port = kelk.createPort(actuator.getDataflowId() + "_" + child.getName() + "_in",
							actuator.isInput() ? rootNode : root, PortSide.WEST);
					ElkLabel label = kelk.createLabel((child.getAlias() == null ? child.getName() : child.getAlias()),
							((Actuator) child).getId(), port);
					port.getLabels().add(label);
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), port);
					ElkNode input = null;
					ElkPort extout = null;

					if (actuator.isInput()) {
						// create an import port on top of the root node and connect to it; tag it for
						// the external
						// process to resolve and link
						String importName = actuator.getDataflowId() + "_" + child.getName() + "_extin";
						extout = inputs.get(importName);
						if (extout == null) {
							extout = kelk.createPort(importName, root, PortSide.NORTH);
							ElkLabel lbl = kelk.createLabel(
									(child.getAlias() == null ? child.getName() : child.getAlias()),
									((Actuator) child).getId(), port);
							port.getLabels().add(lbl);
							inputs.put(importName, extout);
						}
						kelk.createSimpleEdge(extout, port, null);
					} else {
						input = (ElkNode) nodes.get(child.getName());
					}
					if (input != null) {
						if (extout == null) {
							extout = kelk.getOutputPort(input);
						}
						kelk.createSimpleEdge(extout, port, null); // no identifier?
					}

				} else {
					// ACHTUNG references end up here - they should be connected to inside
					lastChild = compile((Actuator) child, root);
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), lastChild);
				}
			}

			// mediators
			for (Pair<IServiceCall, IComputableResource> mediator : actuator.getMediationStrategy()) {
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
				call = kelk.createServiceNode(actor.getSecond().getDataflowId(), root);
				call.getLabels().add(kelk.createLabel(Extensions.INSTANCE.getServiceLabel(actor.getFirst()),
						call.getIdentifier() + "l", call));

				// all computations have a main output
				out = kelk.createPort(call.getIdentifier() + "_out", call, PortSide.EAST);

				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
					if (arg.getName().equals("arguments")) {
						for (Pair<String, Type> rp : actor.getSecond().getInputs()) {
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
							/* ElkEdge feed = */ kelk.createSimpleEdge(fut, inp,
									call.getIdentifier() + "_" + arg.getName() + "_feed");
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

}

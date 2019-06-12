package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
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
	private Map<String, ElkConnectableShape> nodes;
	private Flowchart flowchart;
	private Map<String, String> node2dataflowId;

	public DataflowGraph(Dataflow dataflow, ContextualizationStrategy strategy, KlabElkGraphFactory kelk,
			String output) {
		this.nodes = strategy.getNodes();
		this.node2dataflowId = strategy.getNode2dataflowId();
		this.kelk = kelk;
		this.flowchart = Flowchart.create(dataflow, output);
		rootNode = compile(flowchart);
	}

	public ElkNode getRootNode() {
		return rootNode;
	}

	public Collection<ElkConnectableShape> getOutputs() {
		List<ElkConnectableShape> ret = new ArrayList<>();
		for (String s : flowchart.getExternalOutputs().keySet()) {
			ret.add(nodes.get(flowchart.getExternalOutputs().get(s)));
		}
		return ret;
	}

	public ElkConnectableShape getOutput(String observable) {
		String suffix = ".out." + observable;
		for (String node : nodes.keySet()) {
			if (node.endsWith(suffix)) {
				return nodes.get(node);
			}
		}
		return null;
	}

	public ElkNode compile(Flowchart flowchart) {

		ElkNode ret = compile(flowchart.getRoot(), null, nodes);
		for (Pair<String, String> connection : flowchart.getConnections()) {
			ElkConnectableShape source = nodes.get(connection.getFirst());
			ElkConnectableShape target = nodes.get(connection.getSecond());
			if (source != null && target != null) {
				kelk.createSimpleEdge(source, target, null);
			} else {
				Logging.INSTANCE.warn("INTERNAL: no ports for connection: (" + connection.getFirst() + " = " + source
						+ ") -> (" + connection.getSecond() + " = " + target + ")");
			}
		}

		for (String s : flowchart.getExternalInputs().keySet()) {
			ElkConnectableShape source = getOutput(s);
			ElkConnectableShape target = nodes.get(flowchart.getExternalInputs().get(s));
			if (source != null && target != null) {
				kelk.createSimpleEdge(source, target, null);
			} else {
				Logging.INSTANCE.warn("INTERNAL: no ports for connection: (" + s + " = " + source
						+ ") -> (" + flowchart.getExternalInputs().get(s) + " = " + target + ")");
			}
		}

		return ret;
	}

	public ElkNode compile(Element element, ElkNode parentNode, Map<String, ElkConnectableShape> nodes) {

		ElkNode ret = element.getType() == ElementType.ACTUATOR
				? kelk.createActuatorNode(element.getNodeId(), parentNode)
				: kelk.createServiceNode(element.getNodeId(), parentNode);

		node2dataflowId.put(element.getId(), ret.getIdentifier());

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

	// public ElkNode compile(Actuator actuator, ElkNode parent) {
	//
	// Map<String, ElkConnectableShape> localNodes = new HashMap<>();
	//
	// if (actuator instanceof Dataflow && actuator.actuators.size() == 1 &&
	// !actuator.actuators.get(0).isComputed()
	// && actuator.actuators.get(0).getActuators().size() == 0 &&
	// !actuator.isInput()) {
	// actuator = (Actuator) actuator.actuators.get(0);
	// }
	//
	// ElkNode root = (ElkNode) nodes.get(actuator.getDataflowId());
	// if (root == null) {
	//
	// root = kelk.createActuatorNode(actuator.getDataflowId(), parent);
	// if (rootNode == null) {
	// rootNode = root;
	// }
	// nodes.put(actuator.getName(), root);
	// root.getLabels().add(kelk.createLabel(actuator, root));
	//
	// // main output
	// // property goes on east edge
	// // FIXME if there are no ports, nothing gets drawn
	// ElkPort mainOut = kelk.createPort(actuator.getDataflowId() + "_out1", root,
	// PortSide.EAST);
	//
	// // imports
	// ElkNode lastChild = null;
	// for (IActuator child : actuator.getActuators()) {
	// if (((Actuator) child).isReference() || actuator.isInput()) {
	//
	// ElkPort port = kelk.createPort(actuator.getDataflowId() + "_" +
	// child.getName() + "_in",
	// actuator.isInput() ? rootNode : root, PortSide.WEST);
	// ElkLabel label = kelk.createLabel((child.getAlias() == null ? child.getName()
	// : child.getAlias()),
	// ((Actuator) child).getId(), port);
	// port.getLabels().add(label);
	// localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(),
	// port);
	// ElkNode input = null;
	// ElkPort extout = null;
	//
	// if (actuator.isInput()) {
	// // create an import port on top of the root node and connect to it; tag it
	// for
	// // the external
	// // process to resolve and link
	// String importName = actuator.getDataflowId() + "_" + child.getName() +
	// "_extin";
	// extout = inputs.get(importName);
	// if (extout == null) {
	// extout = kelk.createPort(importName, root, PortSide.NORTH);
	// ElkLabel lbl = kelk.createLabel(
	// (child.getAlias() == null ? child.getName() : child.getAlias()),
	// ((Actuator) child).getId(), port);
	// port.getLabels().add(lbl);
	// inputs.put(importName, extout);
	// }
	// kelk.createSimpleEdge(extout, port, null);
	// } else {
	// input = (ElkNode) nodes.get(child.getName());
	// }
	// if (input != null) {
	// if (extout == null) {
	// extout = kelk.getOutputPort(input);
	// }
	// kelk.createSimpleEdge(extout, port, null); // no identifier?
	// }
	//
	// } else {
	// // ACHTUNG references end up here - they should be connected to inside
	// lastChild = compile((Actuator) child, root);
	// localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(),
	// lastChild);
	// }
	// }
	//
	// // mediators
	// for (Pair<IServiceCall, IComputableResource> mediator :
	// actuator.getMediationStrategy()) {
	// /*
	// * Compile each mediator and
	// */
	// }
	//
	// // computation
	// ElkNode call = null;
	// ElkPort out = null;
	// for (Pair<IServiceCall, IComputableResource> actor :
	// actuator.getComputationStrategy()) {
	//
	// ElkNode previousCall = call;
	// ElkPort previousOut = out;
	//
	// IPrototype prototype =
	// Extensions.INSTANCE.getPrototype(actor.getFirst().getName());
	// call = kelk.createServiceNode(actor.getSecond().getDataflowId(), root);
	// call.getLabels().add(kelk.createLabel(Extensions.INSTANCE.getServiceLabel(actor.getFirst()),
	// call.getIdentifier() + "l", call));
	//
	// // all computations have a main output
	// out = kelk.createPort(call.getIdentifier() + "_out", call, PortSide.EAST);
	//
	// // find any other outputs, create port anyway
	// // output port(s) from the computation - >1 if there are additional
	// observables
	// // literal arguments must go on top edge
	// for (Argument arg : prototype.listArguments()) {
	// if (arg.getName().equals("arguments")) {
	// for (Pair<String, Type> rp : actor.getSecond().getInputs()) {
	// ElkConnectableShape provider = localNodes.get(rp.getFirst());
	// if (provider != null) {
	// ElkPort pout = kelk.getOutputPort(provider);
	// if (pout != null) {
	// ElkPort in = kelk.createPort(call.getIdentifier() + "_in_" + rp.getFirst(),
	// call,
	// PortSide.WEST);
	// /* ElkEdge put = */ kelk.createSimpleEdge(pout, in,
	// NameGenerator.shortUUID());
	// }
	// }
	// }
	// } else if (arg.isArtifact()) {
	// // must match another actuator or an import
	// ElkConnectableShape node = localNodes.get(arg.getName());
	// if (node != null) {
	// ElkPort inp = kelk.createPort(call.getIdentifier() + "_" + arg.getName() +
	// "_in", call,
	// PortSide.WEST);
	// ElkPort fut = kelk.getOutputPort(node);
	// /* ElkEdge feed = */ kelk.createSimpleEdge(fut, inp,
	// call.getIdentifier() + "_" + arg.getName() + "_feed");
	// }
	// } else if (actor.getFirst().getParameters().containsKey(arg.getName())) {
	// // build parameter node
	// }
	// }
	//
	// if (previousCall != null) {
	// ElkPort input = kelk.createPort(call.getIdentifier() + "_in", call,
	// PortSide.WEST);
	// /* ElkEdge feed = */ kelk.createSimpleEdge(previousOut, input,
	// call.getIdentifier() + "_feed");
	// }
	//
	// // each computation feeds into the next, the last feeds into the actuator's
	// // output
	// nodes.put(actuator.getName(), root);
	// }
	//
	// // complete the chain
	// if (call != null) {
	// // feed output of last computation in actuator's output
	// /* ElkEdge outfeed = */ kelk.createSimpleEdge(out, mainOut,
	// call.getIdentifier() + "_outlink");
	// } else if (lastChild != null) {
	// // feed output of last inner actuator in observation's output (note: it could
	// be
	// // >1 parallel
	// // actuators and have either n outputs or merge into 1).
	// ElkPort pout = kelk.getOutputPort(lastChild);
	// if (pout != null) {
	// /* ElkEdge outfeed = */ kelk.createSimpleEdge(pout, mainOut, actuator.getId()
	// + "_obsout");
	// }
	// }
	//
	// }
	//
	// return root;
	// }

}

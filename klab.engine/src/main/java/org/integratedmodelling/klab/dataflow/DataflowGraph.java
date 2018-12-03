package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.util.ElkUtil;
import org.eclipse.elk.graph.ElkConnectableShape;
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
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Create an Elk graph starting from a Dataflow No information about
 * visualisation are given. If is needed, extends this class and override
 * create[ElkObject] methods
 * 
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

		Map<String, ElkConnectableShape> localNodes = new HashMap<>();

		ElkNode root = nodes.get(actuator.getDataflowId());
		if (root == null) {

			root = createNode(actuator.getDataflowId(), parent);
			nodes.put(actuator.getName(), root);
			root.getLabels().add(createLabel(actuator));

			// main output on east edge
			ElkPort mainOut = ElkGraphUtil.createPort(root);
			mainOut.setIdentifier(actuator.getDataflowId() + "_out1");
			mainOut.setProperty(CoreOptions.PORT_SIDE, PortSide.EAST);

			// imports
			ElkNode lastChild = null;
			for (IActuator child : actuator.getActuators()) {
				if (child.isInput()) {
					ElkPort port = ElkGraphUtil.createPort(root);
					port.setIdentifier(actuator.getDataflowId() + "_" + child.getName() + "_in");
					port.getLabels().add(createLabel(child.getAlias() == null ? child.getName() : child.getAlias(),
							((Actuator) child).getId()));
					port.setProperty(CoreOptions.PORT_SIDE, PortSide.WEST);
					localNodes.put(child.getAlias() == null ? child.getName() : child.getAlias(), port);
					ElkNode input = nodes.get(child.getName());
					if (input != null) {
						ElkPort extout = getOutputPort(input);
						ElkGraphUtil.createSimpleEdge(extout, port);
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
				call = createNode(actor.getSecond().getDataflowId(), root);
				// HMMM this would be better for space utilization but the node won't resize to contain the label
//				call.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideCenter());
				call.getLabels().add(
						createLabel(Extensions.INSTANCE.getServiceLabel(actor.getFirst()), call.getIdentifier() + "l"));


				for (Object kp : actor.getFirst().getParameters().entrySet()) {
					// TODO maybe not - leave parameters for detail pane
				}
				
				// all computations have a main output
				out = createPort(call.getIdentifier() + "_out", call, PortSide.EAST);

				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
					if (arg.getName().equals("arguments")) {
						for (Pair<String, Type> rp : actor.getSecond().getRequiredResourceNames()) {
							ElkConnectableShape provider = localNodes.get(rp.getFirst());
							if (provider != null) {
								ElkPort pout = getOutputPort(provider);
								if (pout != null) {
									ElkPort in = createPort(call.getIdentifier() + "_in_" + rp.getFirst(), call,
											PortSide.WEST);
									ElkEdge put = ElkGraphUtil.createSimpleEdge(pout, in);
									put.setIdentifier(NameGenerator.shortUUID());
								}
							}
						}
					} else if (arg.isArtifact()) {
						// must match another actuator or an import
						ElkConnectableShape node = localNodes.get(arg.getName());
						if (node != null) {
							ElkPort inp = createPort(call.getIdentifier() + "_" + arg.getName() + "_in", call,
									PortSide.WEST);
							ElkPort fut = getOutputPort(node);
							ElkEdge feed = ElkGraphUtil.createSimpleEdge(fut, inp);
							feed.setIdentifier(call.getIdentifier() + "_" + arg.getName() + "_feed");
						}
					} else if (actor.getFirst().getParameters().containsKey(arg.getName())) {
						// build parameter node
					}
				}

				if (previousCall != null) {
					ElkPort input = createPort(call.getIdentifier() + "_in", call, PortSide.WEST);
					ElkEdge feed = ElkGraphUtil.createSimpleEdge(previousOut, input);
					feed.setIdentifier(call.getIdentifier() + "_feed");
				}

				// each computation feeds into the next, the last feeds into the actuator's
				// output
				nodes.put(actuator.getName(), root);
			}

			// complete the chain
			if (call != null) {
				// feed output of last computation in actuator's output
				ElkEdge outfeed = ElkGraphUtil.createSimpleEdge(out, mainOut);
				outfeed.setIdentifier(call.getIdentifier() + "_outlink");
			} else if (lastChild != null) {
				// feed output of last inner actuator in observation's output (note: it could be
				// >1 parallel
				// actuators and have either n outputs or merge into 1).
				ElkPort pout = getOutputPort(lastChild);
				if (pout != null) {
					ElkEdge outfeed = ElkGraphUtil.createSimpleEdge(pout, mainOut);
					outfeed.setIdentifier(actuator.getId() + "_obsout");
				}
			}


		}

		return root;
	}

	private ElkPort getOutputPort(ElkConnectableShape node) {
		if (node instanceof ElkPort) {
			return (ElkPort) node;
		}
		ElkPort ret = null;
		for (ElkPort port : ((ElkNode) node).getPorts()) {
			if (port.getIdentifier().endsWith("_out1")) {
				ret = port;
			}
		}
		return ret;
	}

	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * 
	 * @param identifier
	 *            the node identifier
	 * @param parent
	 *            the parent node
	 * @return the newly created node
	 */
	protected ElkNode createNode(String identifier, ElkNode parent) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		return node;
	}

	/**
	 * Create ElkPort with default behavior, if necessary override this method
	 * 
	 * @param identifier
	 *            the port identifier
	 * @param parent
	 *            the parent of port
	 * @param side
	 *            the side of port. One of org.eclipse.elk.core.options.PortSide
	 *            value
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
	 * 
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
	 * public String asJson() { // TODO these options are copied from the docs
	 * without thinking return
	 * ElkGraphJson.forGraph(rootNode).omitLayout(true).omitZeroDimension(false).
	 * omitZeroPositions(false)
	 * .shortLayoutOptionKeys(false).prettyPrint(true).toJson(); }
	 */
}

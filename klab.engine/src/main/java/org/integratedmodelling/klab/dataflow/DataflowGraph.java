package org.integratedmodelling.klab.dataflow;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.alg.common.nodespacing.internal.algorithm.NodeSizeCalculator;
import org.eclipse.elk.alg.layered.intermediate.LabelManagementProcessor;
import org.eclipse.elk.core.labels.ILabelManager;
import org.eclipse.elk.core.labels.LabelManagementOptions;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.emf.common.util.EMap;
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
	
	// First attempt to calculate label text size
	private static Graphics2D GRAPHICS;
	private static String FONT_TYPE = "sans-serif";
	private static final int FONT_SIZE = 8;
	
	private static final double PORT_SIZE = 5;
	
	static {
		// There seems to be a problem with SansSerif on Windows...
		String fontType;
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
        	fontType = "Arial";
        } else {
        	fontType = "SansSerif";
        }
        
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        
        GRAPHICS = image.createGraphics();
        GRAPHICS.setFont(new java.awt.Font(FONT_TYPE, 0 /* SWT.NORMAL */, FONT_SIZE));
	}
	
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
			
			root = createDefaultNode(actuator.getId(), parent);
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
				call = createDefaultNode(actuator.getId() + "_" + prototype.getName(), root);
				call.getLabels().add(label(prototype.getName(), call.getIdentifier() + "l"));
				for (Object kp : actor.getFirst().getParameters().entrySet()) {
					
				}
				// all computations have a main output
				out = createDefaultPort(call.getIdentifier() + "_out", call, PortSide.EAST);
				
				// find any other outputs, create port anyway
				// output port(s) from the computation - >1 if there are additional observables
				// literal arguments must go on top edge
				for (Argument arg : prototype.listArguments()) {
//					if (arg.is)
				}

				if (previousCall != null) {
					ElkPort input = createDefaultPort(call.getIdentifier() + "_in", call, PortSide.WEST);
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
	
	private ElkNode createDefaultNode(String identifier, ElkNode parent/*, Map<IProperty<? super T>, T> properties*/) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		/*
		for (IProperty<? super T> property: properties.keySet()) {
			node.setProperty(property, properties.get(property));
		}
		*/
		node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopLeft());
		// node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.DEFAULT_MINIMUM_SIZE, SizeOptions.MINIMUM_SIZE_ACCOUNTS_FOR_PADDING, SizeOptions.COMPUTE_PADDING, SizeOptions.PORTS_OVERHANG));
		return node;
	}
	
	private ElkPort createDefaultPort(String identifier, ElkNode parent, PortSide side) {
		ElkPort port = ElkGraphUtil.createPort(parent);
		port.setIdentifier(identifier);
		port.setProperty(CoreOptions.PORT_SIDE, side);
		port.setDimensions(PORT_SIZE, PORT_SIZE);
		return port;
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
		// ret.setProperty(CoreOptions.FONT_NAME, FONT_TYPE);
		// ret.setProperty(CoreOptions.FONT_SIZE, 10);
		computeLabelSize(ret);
		return ret;
	}
	/*
	public String asJson() {
		// TODO these options are copied from the docs without thinking
		return ElkGraphJson.forGraph(rootNode).omitLayout(true).omitZeroDimension(false).omitZeroPositions(false)
				.shortLayoutOptionKeys(false).prettyPrint(true).toJson();
	}
	*/
	
	 /**
     * Computes the size of a label if it doesn't already have one. The size is calculated by choosing a font and
     * calculating the space necessary to render the label's text with that font.
     */
    private static void computeLabelSize(final ElkLabel label) {
        // Check if we need to do anything
        if (label.getHeight() == 0 && label.getWidth() == 0 && label.getText() != null && !label.getText().equals("")) {
            Rectangle2D rect = GRAPHICS.getFontMetrics().getStringBounds(label.getText(), GRAPHICS);
            label.setHeight(rect.getHeight());
            label.setWidth(rect.getWidth());
        }
    }
}

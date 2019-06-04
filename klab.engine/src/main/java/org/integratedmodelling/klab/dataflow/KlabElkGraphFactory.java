/**
 * 
 */
package org.integratedmodelling.klab.dataflow;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.labels.ILabelManager;
import org.eclipse.elk.core.labels.LabelManagementOptions;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.util.ElkGraphUtil;

/**
 * k.LAB implementation of ElkFactory to centralize the detailed creation of graph elements
 * In this factory we centralize the view of graph, so a lot of options are as default
 * Info about options {@link https://www.eclipse.org/elk/reference.html}
 * @author Enrico Girotto
 *
 */
public class KlabElkGraphFactory {
	
	public static KlabElkGraphFactory keINSTANCE = new KlabElkGraphFactory();
	
	private static final double PORT_SIZE = 5;
	private static final ElkPadding ACTUATOR_PADDING = new ElkPadding(12, 0, 0, 5);
	// private static final ElkPadding SERVICE_PADDING = new ElkPadding(12, 0, 0, 5);
	private static final KlabLabelManager LABEL_MANAGER;
	
	private static class KlabLabelManager implements ILabelManager {
	
		private static final int FONT_SIZE = 8;
		private Graphics2D graphics;
		
		KlabLabelManager() {
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
			final String fontType = java.awt.Font.SANS_SERIF;
			graphics = image.createGraphics();
	        graphics.setFont(new java.awt.Font(fontType, java.awt.Font.PLAIN, FONT_SIZE));
		}
		
		@Override
		public KVector manageLabelSize(Object label, double targetWidth) {
			ElkLabel l = (ElkLabel)label;
			return computeLabelSize(l);
		}

		/**
	     * Computes the size of a label if it doesn't already have one. The size is calculated by choosing a font and
	     * calculating the space necessary to render the label's text with that font.
	     * Code obtained from elk library: org.eclipse.elk.alg.test.framework.SomeBoxRunner
		 * @param label the ElkLabel that need to be sized
		 */
	    private KVector computeLabelSize(final ElkLabel label) {
	        // Check if we need to do anything
	        if (label.getText() != null && !label.getText().equals("")) {
	            Rectangle2D rect = graphics.getFontMetrics().getStringBounds(label.getText(), graphics);
	            return new KVector(rect.getWidth(), rect.getHeight());
	        } else {
	        	return null;
	        }
	    }
	}
	
	static LayoutMetaDataService service;

	static {
		service = LayoutMetaDataService.getInstance();
		service.registerLayoutMetaDataProviders(new LayeredMetaDataProvider());
		LABEL_MANAGER = new KlabElkGraphFactory.KlabLabelManager();
	}
	
	
	/**
	 * 
	 */
	public KlabElkGraphFactory() {
		// create graphic object to calculate label dimensions
	}
	
	
	/**
	 * Create a k.LAB Graph with defaults options:
	 * 
	 * @return
	 */
	public ElkNode createGraph(String identifier) {
		ElkNode root = ElkGraphUtil.createGraph();
		root.setIdentifier(identifier);
		// root.setProperty(CoreOptions.NODE_LABELS_PADDING, ROOT_PADDING);
		root.setProperty(LabelManagementOptions.LABEL_MANAGER, LABEL_MANAGER);
		return root;
	}
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	public ElkNode createActuatorNode(String identifier, ElkNode parent) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		/*
		for (IProperty<? super T> property: properties.keySet()) {
			node.setProperty(property, properties.get(property));
		}
		*/
		node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopLeft());
		node.setProperty(CoreOptions.SPACING_LABEL_NODE, 0d);
		node.setProperty(CoreOptions.NODE_LABELS_PADDING, ACTUATOR_PADDING);
		node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
		// node.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		return node;
	}
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	public ElkNode createServiceNode(String identifier, ElkNode parent) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.H_LEFT, NodeLabelPlacement.V_CENTER, NodeLabelPlacement.INSIDE));
		// node.setProperty(CoreOptions.NODE_LABELS_PADDING, SERVICE_PADDING);
		node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING, SizeOptions.COMPUTE_PADDING));
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
		port.setDimensions(PORT_SIZE, PORT_SIZE);
		return port;
	}

	public ElkLabel createLabel(Actuator actuator, ElkGraphElement parent) {
		String aname = actuator instanceof Dataflow ? ((Dataflow) actuator).getDescription()
				: StringUtils.capitalize(actuator.getName().replaceAll("_", " "));
		return createLabel(aname, actuator.getId() + "l", parent);
	}

	/**
	 * Create ElkLabel with default behavior, if necessary override this method 
	 * @param name
	 * @param id
	 * @return
	 */
	public ElkLabel createLabel(String text, String identifier, ElkGraphElement parent) {
		ElkLabel label = ElkGraphUtil.createLabel(parent);
		label.setText(text);
		label.setIdentifier(identifier);
		// computeLabelSize(label);
		return label;
	}
	
	public ElkEdge createSimpleEdge(final ElkConnectableShape source, final ElkConnectableShape target, final String identifier) {
		ElkEdge simpleEdge = ElkGraphUtil.createSimpleEdge(source, target);
		if (identifier != null) {
			simpleEdge.setIdentifier(identifier);
		}
		return simpleEdge;
	}
	
	public ElkPort getOutputPort(ElkConnectableShape node) {
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
}

/**
 * 
 */
package org.integratedmodelling.klab.dataflow;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.impl.ElkGraphFactoryImpl;
import org.eclipse.elk.graph.util.ElkGraphUtil;

/**
 * k.LAB implementation of ElkFactory to centralize the detailed creation of graph elements
 * In this factory we centralize the view of graph, so a lot of options are as default
 * @author Enrico Girotto
 *
 */
public class KlabElkGraphFactory {
	
	public static KlabElkGraphFactory keINSTANCE = new KlabElkGraphFactory();
	
	private Graphics2D graphics;
	
	private static final int FONT_SIZE = 8;
	private static final double PORT_SIZE = 5;
	
	
	static LayoutMetaDataService service;

	static {
		service = LayoutMetaDataService.getInstance();
		service.registerLayoutMetaDataProviders(new LayeredMetaDataProvider());
	}
	
	
	/**
	 * 
	 */
	public KlabElkGraphFactory() {
		// create graphic object to calculate label dimensions
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final String fontType;
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			fontType = "Arial";
        } else {
        	fontType = "SansSerif";
        }
        graphics = image.createGraphics();
        graphics.setFont(new java.awt.Font(fontType, 0 /* SWT.NORMAL */, FONT_SIZE));
	}
	
	
	/**
	 * Create a k.LAB Graph with defaults options:
	 * 
	 * @return
	 */
	public ElkNode createGraph(String identifier) {
		ElkNode root = ElkGraphUtil.createGraph();
		root.setIdentifier(identifier);
		// root.setProperty(CoreOptions.ALGORITHM, "elk.layered");
		// root.setProperty(CoreOptions.NODE_SIZE_MINIMUM, new KVector(50,50));
		// root.setProperty(LayeredOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN);
		root.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS));
		root.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopLeft());
		root.setProperty(CoreOptions.NODE_LABELS_PADDING, new ElkPadding(10, 0, 0, 10));
		
		// root.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS,
		//		SizeConstraint.PORT_LABELS, SizeConstraint.PORTS, SizeConstraint.MINIMUM_SIZE));
		// root.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopLeft());
		root.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		// root.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.OUTSIDE_NODE_LABELS_OVERHANG))
		return root;
	}
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	public ElkNode createNode(String identifier, ElkNode parent) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		/*
		for (IProperty<? super T> property: properties.keySet()) {
			node.setProperty(property, properties.get(property));
		}
		*/
		node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS, SizeConstraint.MINIMUM_SIZE));
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideTopLeft());
		node.setProperty(CoreOptions.NODE_LABELS_PADDING, new ElkPadding(10, 0, 0, 5));
		node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
		node.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		// node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.COMPUTE_PADDING));
		// node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.DEFAULT_MINIMUM_SIZE, SizeOptions.MINIMUM_SIZE_ACCOUNTS_FOR_PADDING, SizeOptions.COMPUTE_PADDING, SizeOptions.PORTS_OVERHANG));
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
		computeLabelSize(label);
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
	
	/**
     * Computes the size of a label if it doesn't already have one. The size is calculated by choosing a font and
     * calculating the space necessary to render the label's text with that font.
     * Code obtained from elk library: org.eclipse.elk.alg.test.framework.SomeBoxRunner
	 * @param label the ElkLabel that need to be sized
	 */
    private void computeLabelSize(final ElkLabel label) {
        // Check if we need to do anything
        if (label.getHeight() == 0 && label.getWidth() == 0 && label.getText() != null && !label.getText().equals("")) {
            Rectangle2D rect = graphics.getFontMetrics().getStringBounds(label.getText(), graphics);
            label.setHeight(rect.getHeight());
            label.setWidth(rect.getWidth());
        }
    }
}

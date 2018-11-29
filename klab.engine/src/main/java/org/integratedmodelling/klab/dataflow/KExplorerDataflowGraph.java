package org.integratedmodelling.klab.dataflow;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.HierarchyHandling;
import org.eclipse.elk.core.options.NodeLabelPlacement;
import org.eclipse.elk.core.options.PortSide;
import org.eclipse.elk.core.options.SizeConstraint;
import org.eclipse.elk.core.options.SizeOptions;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;

/**
 * Create an Elk graph starting from a Dataflow
 * Dimension are given to show it in k.EXPLORER using json export
 * @author Enrico Girotto
 */
public class KExplorerDataflowGraph extends DataflowGraph {

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
        GRAPHICS.setFont(new java.awt.Font(fontType, 0 /* SWT.NORMAL */, FONT_SIZE));
	}
	
	public KExplorerDataflowGraph(Dataflow dataflow, Map<String, ElkNode> nodes) {
		super(dataflow, nodes);
	}
	
	@Override
	protected ElkNode createNode(String identifier, ElkNode parent) {
		ElkNode node = super.createNode(identifier, parent);
		/*
		for (IProperty<? super T> property: properties.keySet()) {
			node.setProperty(property, properties.get(property));
		}
		*/
		node.setProperty(LayeredOptions.HIERARCHY_HANDLING, HierarchyHandling.SEPARATE_CHILDREN);
		node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS, SizeConstraint.MINIMUM_SIZE));
		node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopLeft());
		node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.COMPUTE_PADDING));
		// node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.DEFAULT_MINIMUM_SIZE, SizeOptions.MINIMUM_SIZE_ACCOUNTS_FOR_PADDING, SizeOptions.COMPUTE_PADDING, SizeOptions.PORTS_OVERHANG));
		return node;
	}
	
	@Override
	protected ElkPort createPort(String identifier, ElkNode parent, PortSide side) {
		ElkPort port = super.createPort(identifier, parent, side);
		port.setDimensions(PORT_SIZE, PORT_SIZE);
		return port;
	}
	
	@Override
	protected ElkLabel createLabel(String text, String identifier) {
		ElkLabel label = super.createLabel(text, identifier);
		// label.setProperty(CoreOptions.FONT_NAME, FONT_TYPE);
		// label.setProperty(CoreOptions.FONT_SIZE, 10);
		computeLabelSize(label);
		return label;
	}
	
	/**
     * Computes the size of a label if it doesn't already have one. The size is calculated by choosing a font and
     * calculating the space necessary to render the label's text with that font.
     * Code getted by elk library: org.eclipse.elk.alg.test.framework.SomeBoxRunner
	 * @param label the ElkLabel that need to be dimensioned
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

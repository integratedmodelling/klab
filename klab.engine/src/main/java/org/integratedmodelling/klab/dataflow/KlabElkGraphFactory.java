/**
 * 
 */
package org.integratedmodelling.klab.dataflow;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.elk.alg.layered.options.CenterEdgeLabelPlacementStrategy;
import org.eclipse.elk.alg.layered.options.EdgeLabelSideSelection;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.alg.layered.options.Spacings;
import org.eclipse.elk.alg.layered.options.WrappingStrategy;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.labels.ILabelManager;
import org.eclipse.elk.core.labels.LabelManagementOptions;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;
import org.eclipse.elk.core.options.EdgeLabelPlacement;
import org.eclipse.elk.core.options.HierarchyHandling;
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

import org.integratedmodelling.klab.api.API.PUBLIC.Export;

import scala.collection.immutable.Map;
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
	private static final ElkPadding DATAFLOW_PADDING = new ElkPadding(12,6,12,6);
	private static final ElkPadding PROVENANCE_PADDING = new ElkPadding(6, 0, 6, 0);
	
	private static class KlabLabelManager implements ILabelManager {
	
		private static final int FONT_SIZE = 12;
		private Graphics2D graphics;
		private Export type;
		
		KlabLabelManager(Export type) {
		    this.type = type;
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			graphics = image.createGraphics();
			try {
                Font roboto = Font.createFont(Font.TRUETYPE_FONT, KlabLabelManager.class.getClassLoader().getResourceAsStream("fonts/roboto.ttf"));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(roboto);
                graphics.setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, FONT_SIZE));
            } catch (FontFormatException |IOException e) {
                graphics.setFont(new java.awt.Font(Font.SANS_SERIF, java.awt.Font.PLAIN, FONT_SIZE));
            }
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
	            return new KVector(rect.getWidth(), this.type == Export.DATAFLOW ? 0 : rect.getHeight());
	        } else {
	        	return null;
	        }
	    }
	}
	
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
	}
	
	
	/**
	 * Create a k.LAB Graph with defaults options:
	 * 
	 * @return
	 */
	public ElkNode createGraph(String identifier, Export type) {
		ElkNode root = ElkGraphUtil.createGraph();
		root.setIdentifier(identifier);
		root.setProperty(LabelManagementOptions.LABEL_MANAGER, new KlabElkGraphFactory.KlabLabelManager(type));
		root.setProperty(LayeredOptions.HIERARCHY_HANDLING, HierarchyHandling.INCLUDE_CHILDREN);		
//		root.setProperty(LayeredMetaDataProvider.NORTH_OR_SOUTH_PORT, true);
//		root.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_SIDE);
		switch (type) {
		case DATAFLOW:
		    root.setProperty(LayeredOptions.WRAPPING_STRATEGY, WrappingStrategy.MULTI_EDGE);
		    break;
		case PROVENANCE_FULL:
		case PROVENANCE_SIMPLIFIED:
		    // special provenance simplified options
		    root.setProperty(CoreOptions.DIRECTION, Direction.DOWN);
		    break;
		default:
		    break;
		}
		return root;
	}
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	public ElkNode createActuatorNode(String identifier, ElkNode parent, Export type) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		System.out.println("Actuator: "+identifier);
		switch (type) {
        case DATAFLOW:
            node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
            node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.H_LEFT, NodeLabelPlacement.V_TOP, NodeLabelPlacement.OUTSIDE));
            node.setProperty(CoreOptions.NODE_LABELS_PADDING, DATAFLOW_PADDING);
            node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
            break;
        case PROVENANCE_FULL:
        case PROVENANCE_SIMPLIFIED:
            node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS));
            node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideBottomLeft());
            node.setProperty(CoreOptions.NODE_LABELS_PADDING, PROVENANCE_PADDING);
            node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
            break;
        default:
            break;
        }
		// node.setProperty(LayeredMetaDataProvider.NORTH_OR_SOUTH_PORT, true);
		//node.setProperty(CoreOptions.PORT_CONSTRAINTS, PortConstraints.FIXED_SIDE);
		// node.setProperty(CoreOptions.SPACING_LABEL_NODE, 0d);
		
//        node.setProperty(CoreOptions.DIRECTION, Direction.RIGHT);
		return node;
	}
	
	/**
	 * Create ElkNode with default behavior, if necessary override this method
	 * @param identifier the node identifier
	 * @param parent the parent node
	 * @return the newly created node
	 */
	public ElkNode createServiceNode(String identifier, ElkNode parent, Export type) {
		ElkNode node = ElkGraphUtil.createNode(parent);
		node.setIdentifier(identifier);
		System.out.println("Service: "+identifier);
		switch (type) {
        case DATAFLOW:
            node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
            node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.H_CENTER, NodeLabelPlacement.V_CENTER, NodeLabelPlacement.INSIDE));
            node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
            break;
        case PROVENANCE_FULL:
        case PROVENANCE_SIMPLIFIED:
            node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORT_LABELS));
            node.setProperty(CoreOptions.NODE_LABELS_PADDING, PROVENANCE_PADDING);
            node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideBottomCenter());
            node.setProperty(CoreOptions.NODE_LABELS_PADDING, PROVENANCE_PADDING);
            node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
            break;
        default:
            break;
        }
		
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
	    System.out.println("Label: "+identifier+": "+text);
		ElkLabel label = ElkGraphUtil.createLabel(parent);
		label.setText(text);
		label.setIdentifier(identifier);
		return label;
	}
	
	public ElkEdge createSimpleEdge(final ElkConnectableShape source, final ElkConnectableShape target, final String identifier, Export type) {
//	    String[] palabras = {"Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit"};
//	    int index = (int) Math.floor(Math.random() * (palabras.length + 2));
//	    return createSimpleEdge(source, target, identifier, index >= palabras.length ? null : palabras[index]);
	    return createSimpleEdge(source, target, identifier, null, type);
	}
	
	public ElkEdge createSimpleEdge(final ElkConnectableShape source, final ElkConnectableShape target, final String identifier, final String label, Export type) {
		ElkEdge simpleEdge = ElkGraphUtil.createSimpleEdge(source, target);
		if (identifier != null) {
			simpleEdge.setIdentifier(identifier);
		}
		if (label != null) {
		    ElkLabel elkLabel = createLabel(label, source.getIdentifier() + "_" + target.getIdentifier(), simpleEdge);
		    switch (type) {
	        case DATAFLOW:
	            elkLabel.setProperty(CoreOptions.EDGE_LABELS_INLINE, true);
	            break;
	        case PROVENANCE_FULL:
	        case PROVENANCE_SIMPLIFIED:
	            elkLabel.setProperty(LayeredMetaDataProvider.EDGE_LABELS_SIDE_SELECTION, EdgeLabelSideSelection.ALWAYS_UP);
	            break;
	        default:
	            break;
	        } 
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

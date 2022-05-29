//package org.integratedmodelling.klab.dataflow;
//
//import java.util.EnumSet;
//
//import org.eclipse.elk.core.math.ElkPadding;
//import org.eclipse.elk.core.options.CoreOptions;
//import org.eclipse.elk.core.options.Direction;
//import org.eclipse.elk.core.options.NodeLabelPlacement;
//import org.eclipse.elk.core.options.SizeConstraint;
//import org.eclipse.elk.core.options.SizeOptions;
//import org.eclipse.elk.graph.ElkConnectableShape;
//import org.eclipse.elk.graph.ElkEdge;
//import org.eclipse.elk.graph.ElkLabel;
//import org.eclipse.elk.graph.ElkNode;
//import org.eclipse.elk.graph.util.ElkGraphUtil;
//import org.integratedmodelling.klab.api.API.PUBLIC.Export;
//
//public class KlabElkDataflowFactory extends KlabElkGraphFactory{
//    
//    public static KlabElkDataflowFactory keINSTANCE = new KlabElkDataflowFactory();
//    
//    private static final ElkPadding ACTUATOR_PADDING = new ElkPadding(10, 0, 0, 5);
//
//    public KlabElkDataflowFactory() {
//        
//    }
//
//    @Override
//    public ElkNode createGraph(String identifier, Export type) {
//        ElkNode root = super.createGraph(identifier, type);
//        return root;
//    }
//    
//    @Override
//    public ElkNode createActuatorNode(String identifier, ElkNode parent, Export type) {
//        ElkNode node = super.createActuatorNode(identifier, parent, type);
//        node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
//        node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.outsideTopLeft());
//        node.setProperty(CoreOptions.NODE_LABELS_PADDING, ACTUATOR_PADDING);
//        node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
//        return node;
//    }
//    
//    @Override
//    public ElkNode createServiceNode(String identifier, ElkNode parent) {
//        ElkNode node = ElkGraphUtil.createNode(parent);
//        node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.NODE_LABELS, SizeConstraint.PORTS));
//        node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.H_LEFT, NodeLabelPlacement.V_CENTER, NodeLabelPlacement.INSIDE));
//        node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
//        return node;
//    }
//    
//    @Override
//    public ElkEdge createSimpleEdge(ElkConnectableShape source, ElkConnectableShape target, String identifier, String label) {
//        ElkEdge simpleEdge = super.createSimpleEdge(source, target, identifier, label);
//        if (label != null) {
//            ElkLabel elkLabel = createLabel(label, source.getIdentifier() + "_" + target.getIdentifier(), simpleEdge);
//            elkLabel.setProperty(CoreOptions.EDGE_LABELS_INLINE, true);
//        }
//        return simpleEdge;
//    }
//}

//package org.integratedmodelling.klab.dataflow;
//
//import java.util.EnumSet;
//
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
//public class KlabElkProvenanceFactory extends KlabElkGraphFactory {
//    
//    public static KlabElkProvenanceFactory keINSTANCE = new KlabElkProvenanceFactory();
//    private boolean full;
//    
//    public KlabElkProvenanceFactory() {
//    }
//
//    public boolean isFull() {
//        return full;
//    }
//    
//    @Override
//    public ElkNode createGraph(String identifier, Export type) {
//        return createGraph(identifier, type, false);
//    }
//    
//    public ElkNode createGraph(String identifier, Export type, boolean full) {
//        this.full = full;
//        ElkNode root = super.createGraph(identifier, type);
//        root.setProperty(CoreOptions.DIRECTION, Direction.DOWN);
//        return root;
//    }
//    
//    @Override
//    public ElkNode createActuatorNode(String identifier, ElkNode parent, Export type) {
//        ElkNode node = super.createActuatorNode(identifier, parent, type);
//        node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.PORTS));
//        node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, EnumSet.of(NodeLabelPlacement.H_CENTER, NodeLabelPlacement.V_CENTER, NodeLabelPlacement.INSIDE));
//        node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
//        return node;
//    }
//    
//    @Override
//    public ElkNode createServiceNode(String identifier, ElkNode parent) {
//        ElkNode node = ElkGraphUtil.createNode(parent);
//        node.setProperty(CoreOptions.NODE_SIZE_CONSTRAINTS, EnumSet.of(SizeConstraint.PORTS));
//        node.setProperty(CoreOptions.NODE_LABELS_PLACEMENT, NodeLabelPlacement.insideCenter());
//        node.setProperty(CoreOptions.NODE_SIZE_OPTIONS, EnumSet.of(SizeOptions.UNIFORM_PORT_SPACING));
//        return node;
//    }
//
//}

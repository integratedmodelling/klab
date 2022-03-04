//package org.integratedmodelling.klab.dataflow;
//
//import java.util.Map;
//
//import org.eclipse.elk.core.options.PortSide;
//import org.eclipse.elk.graph.ElkConnectableShape;
//import org.eclipse.elk.graph.ElkNode;
//import org.eclipse.elk.graph.ElkPort;
//import org.integratedmodelling.klab.dataflow.Flowchart.Element;
//import org.integratedmodelling.klab.dataflow.Flowchart.ElementType;
//import org.integratedmodelling.klab.utils.Pair;
//
///**
// * Create an Elk graph starting from a Dataflow. Uses node list from current
// * contextualization strategy to resolve links to previously computed
// * observations, and incrementally builds the catalog of node IDs -> computation
// * IDs and elements in it. No information about visualisation are added.
// * 
// * @author Ferdinando Villa
// */
//public class DataflowLayout {
//
//    private ElkNode rootNode;
//    private KlabElkGraphFactory kelk;
//    private Map<String, ElkConnectableShape> nodes;
//    private Flowchart flowchart;
//    private Map<String, String> computationToNodeId;
//    private Map<String, Element> elements;
//
////    public static DataflowLayout create(IDataflow<?> dataflow, IRuntimeScope scope) {
////        return new DataflowLayout(compile(dataflow, scope), KlabElkGraphFactory.keINSTANCE);
////    }
//    
////    private static Flowchart compile(IDataflow<?> dataflow, IRuntimeScope scope) {
////
////        Flowchart ret = new Flowchart(scope);
//////      if (dataflow.getActuators().size() > 0) {
//////          Actuator actuator = (Actuator) dataflow.getActuators().get(0);
//////          ret.compileActuator(actuator, null);
//////
//////          if (!(actuator.getObservable().is(Type.COUNTABLE) && actuator.getMode() == Mode.RESOLUTION)) {
//////              if (ret.root != null) {
//////                  // FIXME this should never be null
//////                  ret.outputs.put(actuator.getName(), ret.root.getOrCreateOutput(actuator.getName()));
//////              }
//////          }
//////      }
////
////        return ret;
////    }
//    
//    private DataflowLayout(Flowchart flowchart, KlabElkGraphFactory kelk) {
////        this.nodes = strategy.getNodes();
////        this.elements = strategy.getElements();
////        this.computationToNodeId = strategy.getComputationToNodeIdTable();
//        this.kelk = kelk;
//        this.flowchart = flowchart;
//        rootNode = compile(flowchart);
//    }
//
////    private ElkNode getRootNode() {
////        return rootNode;
////    }
////
////    private Collection<ElkConnectableShape> getOutputs() {
////        List<ElkConnectableShape> ret = new ArrayList<>();
////        for (String s : flowchart.getOutputs()) {
////            ret.add(nodes.get(s));
////        }
////        return ret;
////    }
//
//    private ElkConnectableShape getOutput(String observable) {
//        String suffix = ".out." + observable;
//        for (String node : nodes.keySet()) {
//            if (node.endsWith(suffix)) {
//                return nodes.get(node);
//            }
//        }
//        return null;
//    }
//
//    private ElkNode compile(Flowchart flowchart) {
//
//        if (flowchart.getRoot() == null) {
//            return null;
//        }
//        
//        ElkNode ret = compile(flowchart.getRoot(), null);
//        for (Pair<String, String> connection : flowchart.getConnections()) {
//            ElkConnectableShape source = nodes.get(connection.getFirst());
//            ElkConnectableShape target = nodes.get(connection.getSecond());
//            if (source != null && target != null) {
//                kelk.createSimpleEdge(source, target, null);
//            }
//        }
//
//        for (String s : flowchart.getExternalInputs().keySet()) {
//            ElkConnectableShape source = getOutput(s);
//            ElkConnectableShape target = nodes.get(flowchart.getExternalInputs().get(s));
//            if (source != null && target != null) {
//                kelk.createSimpleEdge(source, target, null);
//            }
//        }
//
//        return ret;
//    }
//
//    private ElkNode compile(Element element, ElkNode parentNode) {
//
//        ElkNode ret = element.getType() == ElementType.ACTUATOR
//                ? kelk.createActuatorNode(element.getNodeId(), parentNode)
//                : kelk.createServiceNode(element.getNodeId(), parentNode);
//
//        computationToNodeId.put(element.getId(), ret.getIdentifier());
//
//        nodes.put(element.getId(), ret);
//        elements.put(ret.getIdentifier(), element);
//
//        ret.getLabels().add(kelk.createLabel(element.getLabel(), element.getId(), ret));
//
//        for (String input : element.getInputs()) {
//            ElkPort port = kelk.createPort(input, ret, getPortSide(input, PortSide.WEST));
//            nodes.put(input, port);
//        }
//
//        for (String output : element.getOutputs()) {
//            ElkPort port = kelk.createPort(output, ret, getPortSide(output, PortSide.EAST));
//            nodes.put(output, port);
//        }
//
//        for (Element child : element.getChildren()) {
//            compile(child, ret);
//        }
//
//        return ret;
//    }
//
//    private PortSide getPortSide(String input, PortSide defaultValue) {
//        PortSide ret = defaultValue;
//        if (input.contains(".import.")) {
//            return PortSide.NORTH;
//        } else if (input.contains(".export.")) {
//            return PortSide.SOUTH;
//        }
//        return ret;
//    }
//
//}

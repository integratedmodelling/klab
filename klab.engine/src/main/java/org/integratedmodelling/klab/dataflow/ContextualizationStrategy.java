package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A tree of dataflows, possibly connected through internal links. Root nodes
 * are arranged chronologically and correspond to user-generated observations.
 * Child nodes result from the resolution of instantiated countables.
 * <p>
 * Each context keeps one of these and adds to it as the user makes new
 * observations. Dataflows are added after resolution, independent of whether
 * the contextualization after it is successful.
 * 
 * @author Ferd
 *
 */
public class ContextualizationStrategy extends DefaultDirectedGraph<Dataflow, DefaultEdge> {

    String id = NameGenerator.shortUUID();
    private KlabElkGraphFactory kelk = KlabElkGraphFactory.keINSTANCE;
    private Map<String, ElkConnectableShape> nodes = new HashMap<>();
    private Map<String, Element> elements = new HashMap<>();
    private Map<String, String> node2dataflowId = new HashMap<>();

    public ContextualizationStrategy() {
        super(DefaultEdge.class);
    }

    List<Dataflow> rootNodes = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public void add(Dataflow dataflow) {
        synchronized (rootNodes) {
            addVertex(dataflow);
            rootNodes.add(dataflow);
        }
    }

    public void add(Dataflow dataflow, Dataflow parent) {
        synchronized (this) {
            addVertex(dataflow);
            addEdge(parent, dataflow);
        }
    }

    public static String getElkGraph(Dataflow dataflow) {
    	ContextualizationStrategy strategy = new ContextualizationStrategy();
    	strategy.add(dataflow);
    	return strategy.getElkGraph();
    }
    
    public String getElkGraph() {

        List<Flowchart> flowcharts = new ArrayList<>();

        //		if (json == null) {
        synchronized (this) {

            elements.clear();
            nodes.clear();
            node2dataflowId.clear();
            flowcharts.clear();

            ElkNode root = kelk.createGraph(id);

            /*
             * first create the flowcharts and link them, creating outputs for any exported
             * observation. Then make the graph from the linked flowcharts.
             */
            List<Triple<String, String, String>> connections = new ArrayList<>();
            for (Dataflow df : rootNodes) {

                Flowchart current = Flowchart.create(df);

                for (String input : current.getExternalInputs().keySet()) {
                    for (Flowchart previous : flowcharts) {
                        String output = previous.pullOutput(input);
                        if (output != null) {
                            connections.add(new Triple<>(input, output, current.getExternalInputs().get(input)));
                        }
                    }
                }

                flowcharts.add(current);
            }

            // new nodes
            ElkNode contextNode = null;
            for (Flowchart flowchart : flowcharts) {
                DataflowGraph graph = new DataflowGraph(flowchart, this, kelk);
                // TODO children - recurse on secondary contextualizations
                ElkNode tgraph = graph.getRootNode();
                if (tgraph != null) {
                    root.getChildren().add(tgraph);
                    if (contextNode == null) {
                        contextNode = tgraph;
                    } else {
                        int i = 0;
                        for (ElkConnectableShape outPort : graph.getOutputs()) {
                            kelk.createSimpleEdge(outPort, contextNode, "ctx" + outPort.getIdentifier() + "_" + i);
                        }
                    }
                }
            }

            for (Triple<String, String, String> connection : connections) {
                kelk.createSimpleEdge(nodes.get(connection.getSecond()), nodes.get(connection.getThird()), "external."
                        + connection.getSecond() + "." + connection.getThird() + "." + connection.getFirst());
            }

            RecursiveGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
            engine.layout(root, new BasicProgressMonitor());

            String json = ElkGraphJson.forGraph(root).omitLayout(false).omitZeroDimension(true).omitZeroPositions(true)
                    .shortLayoutOptionKeys(true).prettyPrint(true).toJson();

            // System.out.println(json);
            return json;
        }
        //        }

        //		return null;
    }

    public Map<String, ElkConnectableShape> getNodes() {
        synchronized (rootNodes) {
            return nodes;
        }
    }

    public Map<String, Element> getElements() {
        synchronized (rootNodes) {
            return elements;
        }
    }

    public Map<String, String> getComputationToNodeIdTable() {
        synchronized (rootNodes) {
            return node2dataflowId;
        }
    }

    public Element findDataflowElement(String nodeId) {
        synchronized (rootNodes) {
            return elements.get(nodeId);
        }
    }

}

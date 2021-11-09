package org.integratedmodelling.klab.utils.graph;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.model.KimLoader;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.utils.graph.Graphs.Layout;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Graphs {

	public enum Layout {
		HIERARCHICAL, RADIALTREE, SIMPLE, SPRING
	}

	public static <E> void show(org.integratedmodelling.contrib.jgrapht.Graph<?, E> graph, String title,
			Class<? extends E> edgeClass) {
		show(adaptContribGraph(graph, edgeClass), title, Layout.SPRING);
	}

	public static <E> void show(org.integratedmodelling.contrib.jgrapht.Graph<?, E> graph, String title, Layout layout,
			Class<? extends E> edgeClass) {
		show(adaptContribGraph(graph, edgeClass), title, layout);
	}

	public static void show(Graph<?, ?> graph, String title) {
		show(graph, title, Layout.SPRING);
	}

	public static void show(Graph<?, ?> graph, String title, Layout layout) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				GraphPanel panel = new GraphPanel(title, (Graph<Object, Object>) graph, layout);
				panel.showGraph();

//				JGraph jgraph = new JGraph(new JGraphModelAdapter<>(graph));
//				jgraph.setPreferredSize(new Dimension(800, 800));
//				JGraphLayout graphLayout = null;
//				switch (layout) {
//				case HIERARCHICAL:
//					graphLayout = new JGraphHierarchicalLayout();
//					break;
//				case RADIALTREE:
//					graphLayout = new JGraphRadialTreeLayout();
//					break;
//				case SIMPLE:
//					graphLayout = new JGraphSimpleLayout(0);
//					break;
//				case SPRING:
//					graphLayout = new JGraphFastOrganicLayout();
//					break;
//				default:
//					break;
//				}
//				final JGraphFacade graphFacade = new JGraphFacade(jgraph);
//				graphLayout.run(graphFacade);
//				final Map<?, ?> nestedMap = graphFacade.createNestedMap(true, true);
//				jgraph.getGraphLayoutCache().edit(nestedMap);
//
//				jgraph.setAutoscrolls(true);
//				jgraph.setEditable(false);
//				jgraph.setAutoResizeGraph(true);
//				jgraph.setBendable(true);
//
//				JFrame frame = new JFrame();
//				frame.setTitle(title);
//				frame.setSize(800, 800);
//				frame.getContentPane().add(new JScrollPane(jgraph));
//				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				frame.pack();
//				frame.setVisible(true);
			}

		});
	}

	@SuppressWarnings("unchecked")
	private static <E> Graph<?, ?> adaptContribGraph(org.integratedmodelling.contrib.jgrapht.Graph<?, E> graph,
			Class<? extends E> edgeClass) {

		DefaultDirectedGraph<Object, E> ret = new DefaultDirectedGraph<Object, E>(edgeClass);
		for (Object o : graph.vertexSet()) {
			ret.addVertex(o);
		}
		for (Object e : graph.edgeSet()) {
			ret.addEdge(graph.getEdgeSource((E) e), graph.getEdgeTarget((E) e), (E) e);
		}
		return ret;
	}

	/**
	 * Show the dependency graph in the loader.
	 */
	public static void showDependencies() {
		show(((KimLoader) Resources.INSTANCE.getLoader()).getDependencyGraph(), "Dependencies", DefaultEdge.class);
	}

	/**
	 * Return whether precursor has a directed edge to dependent in graph.
	 * 
	 * @param <V>
	 * @param <E>
	 * @param dependent
	 * @param precursor
	 * @param graph
	 * @return true if dependency exists
	 */
	public static <V, E> boolean dependsOn(V dependent, V precursor, Graph<V, E> graph) {

		for (E o : graph.incomingEdgesOf(dependent)) {
			if (graph.getEdgeSource(o).equals(precursor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Shallow copy of graph into another.
	 * 
	 * @param <V>
	 * @param <E>
	 * @param graph
	 * @param newGraph
	 * @return same graph passed as receiver
	 */
	public static <V, E> Graph<V, E> copy(Graph<V, E> graph, Graph<V, E> newGraph) {
		for (V vertex : graph.vertexSet()) {
			newGraph.addVertex(vertex);
		}
		for (E edge : graph.edgeSet()) {
			newGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
		}
		return newGraph;
	}

}

class GraphPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2707712944901661771L;

	public GraphPanel(String title, Graph<Object, Object> sourceGraph, Layout layout) {
		
		super(title);

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();

		try {

			Map<Object, Object> vertices = new HashMap<>();
			for (Object v : sourceGraph.vertexSet()) {
				vertices.put(v, graph.insertVertex(parent, null, v.toString(), 20, 20, v.toString().length() * 6, 30));
			}
			for (Object v : sourceGraph.edgeSet()) {
				graph.insertEdge(parent, null, v.toString(), vertices.get(sourceGraph.getEdgeSource(v)),
						vertices.get(sourceGraph.getEdgeTarget(v)));
			}

		} finally {
			graph.getModel().endUpdate();
		}

		switch (layout) {
		case HIERARCHICAL:
			break;
		case RADIALTREE:
			break;
		case SIMPLE:
			break;
		case SPRING:
			new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
			break;
		default:
			break;

		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public void showGraph() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 320);
		setVisible(true);
	}

}
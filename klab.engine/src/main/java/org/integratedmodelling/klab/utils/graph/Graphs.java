package org.integratedmodelling.klab.utils.graph;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.model.KimLoader;
import org.integratedmodelling.klab.Resources;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;

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

				JGraph jgraph = new JGraph(new JGraphModelAdapter<>(graph));
				jgraph.setPreferredSize(new Dimension(800, 800));
				JGraphLayout graphLayout = null;
				switch (layout) {
				case HIERARCHICAL:
					graphLayout = new JGraphHierarchicalLayout();
					break;
				case RADIALTREE:
					graphLayout = new JGraphRadialTreeLayout();
					break;
				case SIMPLE:
					graphLayout = new JGraphSimpleLayout(0);
					break;
				case SPRING:
					graphLayout = new JGraphFastOrganicLayout();
					break;
				default:
					break;
				}
				final JGraphFacade graphFacade = new JGraphFacade(jgraph);
				graphLayout.run(graphFacade);
				final Map<?, ?> nestedMap = graphFacade.createNestedMap(true, true);
				jgraph.getGraphLayoutCache().edit(nestedMap);

				jgraph.setAutoscrolls(true);
				jgraph.setEditable(false);
				jgraph.setAutoResizeGraph(true);
				jgraph.setBendable(true);

				JFrame frame = new JFrame();
				frame.setTitle(title);
				frame.setSize(800, 800);
				frame.getContentPane().add(new JScrollPane(jgraph));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
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
	public static <V, E> Graph<V,E> copy(Graph<V, E> graph, Graph<V,E> newGraph) {
		for (V vertex : graph.vertexSet()) {
			newGraph.addVertex(vertex);
		}
		for (E edge : graph.edgeSet()) {
			newGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
		}
		return newGraph;
	}


}

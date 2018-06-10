package org.integratedmodelling.klab.utils.graph;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;

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

}

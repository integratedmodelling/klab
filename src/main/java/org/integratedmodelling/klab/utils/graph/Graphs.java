package org.integratedmodelling.klab.utils.graph;

import java.awt.Dimension;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;

public class Graphs {

  public static void show(Graph<?,?> graph, String title) {

    JGraph jgraph = new JGraph(new JGraphModelAdapter<>(graph));
    jgraph.setPreferredSize(new Dimension(800, 800));
    final JGraphRadialTreeLayout graphLayout =
        new JGraphRadialTreeLayout();
    final JGraphFacade graphFacade = new JGraphFacade(jgraph);
    graphLayout.run(graphFacade);
    final Map<?, ?> nestedMap = graphFacade.createNestedMap(true, true);
    jgraph.getGraphLayoutCache().edit(nestedMap);

    JFrame frame = new JFrame();
    frame.setTitle(title);
    frame.setSize(800, 800);
    frame.getContentPane().add(new JScrollPane(jgraph));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

}

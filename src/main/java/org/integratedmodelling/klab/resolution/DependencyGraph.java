package org.integratedmodelling.klab.resolution;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DependencyGraph extends DefaultDirectedGraph<Dependency, DefaultEdge> {

  private static final long serialVersionUID = 1132784190007279107L;

  public DependencyGraph() {
    super(DefaultEdge.class);
  }

}

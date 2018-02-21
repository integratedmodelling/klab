package org.integratedmodelling.klab.utils.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Use to partition a jgrapht directed graph into all the disconnected subgraphs it contains. There
 * is probably a more idiomatic way using ConnectivityInspector that I may use another time.
 * 
 * @author ferdinando.villa
 *
 * @param <V>
 * @param <E>
 */
public class GraphPartitioner<V, E> {

  Class<? extends E> cls;
  
  public GraphPartitioner(Class<? extends E> cls) {
    this.cls = cls;
  }
  
  public Collection<DirectedGraph<V, E>> getDisconnectedSubgraphs(DirectedGraph<V, E> from) {
    List<DirectedGraph<V, E>> subGraphs = new ArrayList<>();

    Stack<V> verticesRemaining = new Stack<V>();
    verticesRemaining.addAll(from.vertexSet());

    if (verticesRemaining.isEmpty()) {
      subGraphs.add(from);
      return subGraphs;
    }
    
    do {
      Set<V> visited = new HashSet<>();
      DirectedGraph<V, E> newGraph = getNewGraph();
      dfsCopy(verticesRemaining.pop(), from, newGraph, visited);
      verticesRemaining.removeAll(visited);
      subGraphs.add(newGraph);
    } while (!verticesRemaining.isEmpty());

    return subGraphs;
  }

  public DirectedGraph<V, E> getNewGraph() {
    return new DefaultDirectedGraph<>(cls);
  }

  private void dfsCopy(V v, DirectedGraph<V, E> from, DirectedGraph<V, E> to, Set<V> visited) {

    if (!visited.add(v)) {
      return;
    }

    for (E e : from.incomingEdgesOf(v)) {
      V neighbor = from.getEdgeSource(e);
      to.addVertex(v);
      to.addVertex(neighbor);
      to.addEdge(neighbor, v, e);
      dfsCopy(neighbor, from, to, visited);
    }
    for (E e : from.outgoingEdgesOf(v)) {
      V neighbor = from.getEdgeTarget(e);
      to.addVertex(v);
      to.addVertex(neighbor);
      to.addEdge(v, neighbor, e);
      dfsCopy(neighbor, from, to, visited);
    }
  }

}

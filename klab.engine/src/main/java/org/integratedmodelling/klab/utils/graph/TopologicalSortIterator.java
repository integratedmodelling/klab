package org.integratedmodelling.klab.utils.graph;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import org.jgrapht.Graph;

/**
 * An iterator returning elements based on the partial order relationship we have in the directed
 * nodes, starting from the sources and moving towards the sinks
 * 
 * From Geotools, to be adapted for parallel sort (becomes Iterator<Collection<T>>)
 */
class TopologicalSortIterator<T,K> implements Iterator<T> {

  /**
   * Simple count down object
   */
  static final class Countdown {
    int value;

    public Countdown(int value) {
      this.value = value;
    }

    public int decrement() {
      return --value;
    }
  }

  // lists of nodes with zero residual inDegrees (aka sources)
  private final LinkedList<T>     sources           = new LinkedList<>();
  private final Map<T, Countdown> residualInDegrees = new LinkedHashMap<>();
  private boolean throwOnCycle = true;
  private Graph<T,K> graph;
  
  public TopologicalSortIterator(Graph<T,K> graph) {
    this.graph = graph;
    for (T node : graph.vertexSet()) {
      int inDegree = graph.incomingEdgesOf(node).size();
      if (inDegree == 0) {
        sources.add(node);
      } else {
        residualInDegrees.put(node, new Countdown(inDegree));
      }
    }
    if (sources.isEmpty() && !graph.vertexSet().isEmpty()) {
      maybeThrowLoopException();
    }
  }

  private void maybeThrowLoopException() {
    if (throwOnCycle ) {
      String message =
          "Some of the partial order relationship form a loop: " + residualInDegrees.keySet();
      throw new IllegalStateException(message);
    }
  }

  @Override
  public boolean hasNext() {
    if (sources.isEmpty() && !residualInDegrees.isEmpty()) {
      maybeThrowLoopException();
    }
    return !sources.isEmpty();
  }

  @Override
  public T next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    
    T next = sources.removeFirst();

    // lower the residual inDegree of all nodes after this one,
    // creating a new set of sources
    for (K eout : graph.outgoingEdgesOf(next)) {
      T out = graph.getEdgeTarget(eout);
      Countdown countdown = residualInDegrees.get(out);
      if (countdown.decrement() == 0) {
        sources.add(out);
        residualInDegrees.remove(out);
      }
    }

    return next;
  }

}


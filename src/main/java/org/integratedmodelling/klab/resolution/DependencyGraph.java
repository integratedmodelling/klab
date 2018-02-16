package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.resolution.DependencyGraph.Dependency;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DependencyGraph extends DefaultDirectedGraph<ResolvedObservable, Dependency> {

  /**
   * A dependency edge. TODO must contain a list of mediators - so far not clear
   * what these should be.
   * 
   * @author ferdinando.villa
   *
   */
  static class Dependency extends DefaultEdge {
    private static final long serialVersionUID = 842960997841576454L;
    List<ResolutionMediator> mediators = new ArrayList<>();
  }
  
  private static final long serialVersionUID = 1132784190007279107L;

  public DependencyGraph() {
    super(Dependency.class);
  }

  /**
   * Get the existing dependency for a passed observable and mode, or null if
   * none exists.
   * 
   * @param observable
   * @param mode
   * @param allowIndirectObservation 
   * @return
   */
  public ResolvedObservable getObservable(IObservable observable, Mode mode, boolean allowIndirectObservation) {
    for (ResolvedObservable o : vertexSet()) {
      if (o.observable.equals(observable) && o.resolutionMode == mode) {
        return o;
      }
    }
    if (allowIndirectObservation) {
      for (IObservable indirect : Resolver.INSTANCE.getIndirectObservables(observable, mode)) {
        ResolvedObservable alternative = getObservable(indirect, mode, true);
        if (alternative != null) {
          return alternative;
        }
      }
    }
    return null;
  }

  /**
   * Report the coverage of the passed observable in the passed mode. Return empty if the observable
   * isn't part of the graph.
   * 
   * Shorthand for getDependency(observable, mode)?.getCoverage()
   * 
   * @param observable
   * @param mode
   * @return
   */
  public Coverage getCoverage(IObservable observable, Mode mode) {
    ResolvedObservable dependency = getObservable(observable, mode, false);
    if (dependency != null) {
      return dependency.coverage;
    }
    return ResolutionScope.empty();
  }

  /**
   * Report the
   * 
   * @param requiring
   * @param dependent
   */
  public Dependency link(ResolvedObservable requiring, ResolvedObservable dependent) {
    Dependency dependency = addEdge(dependent, requiring);
//    // TODO if not identical, add the necessary mediations in the link.
//    for (ResolutionMediator mediator : getMediators())
    return dependency;
  }

  /**
   * Report the resolution of an observable by passing the corresponding, resolved scope. The scope
   * is expected to have an observable, a model and sufficient coverage. If the observable has been
   * resolved already, increment its usage count.
   * 
   * @param scope
   * @return
   */
  public ResolvedObservable resolve(ResolutionScope scope) {
    ResolvedObservable ret = getObservable(scope.getObservable(), scope.getMode(), false);
    if (ret != null) {
      ret.useCount ++;
    } else {
      ret = new ResolvedObservable(scope.getObservable());
      ret.model = scope.getModel();
      ret.coverage = scope;
      ret.resolutionMode = scope.getMode();
      addVertex(ret);
    }
    return ret;
  }

}

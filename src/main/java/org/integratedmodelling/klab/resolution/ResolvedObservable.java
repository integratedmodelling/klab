package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.data.Geometry;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;

/**
 * The node for a dependency graph, representing all the information relative to each resolved
 * observable, built during resolution. The graph itself is passed around in the resolution scope.
 * 
 * @author ferdinando.villa
 *
 */
public class ResolvedObservable {

  /**
   * The observable. If it's here, we can compute it, either because we have a model (i.e. model !=
   * null), because it's already there from an upper resolution we can use, or because we can obtain
   * it by mediating an upstream dependency.
   */
  Observable observable;

  /**
   * Usages count. If zero, we have the observable because a model produces it, but we don't need it
   * in the current dataflow, so it can be removed from the outputs. If 1, we're using it as output
   * only because the observing agent requested it. If > 1, it's needed elsewhere in the workflow,
   * so it will need to be referenced internally with a label.
   */
  int        useCount;

  /**
   * Different resolution modes are different dependencies
   */
  Mode       resolutionMode;

  /**
   * Model that computes this observable. If null, it's already there from an upper resolution that
   * we can use, or we can obtain it by mediating an upstream dependency.
   */
  Model      model;

  /**
   * The geometry covered. Allows us to know if this is a constant and whether it has temporal
   * coverage.
   */
  Geometry   geometry;

  /**
   * The scale covered by this observable in the current context.
   */
  Coverage   coverage;

  public ResolvedObservable(Observable observable) {
    this.observable = observable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((observable == null) ? 0 : observable.getType().hashCode());
    result = prime * result + ((resolutionMode == null) ? 0 : resolutionMode.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ResolvedObservable other = (ResolvedObservable) obj;
    if (observable == null) {
      if (other.observable != null)
        return false;
    } else if (!observable.getType().equals(other.observable.getType()))
      return false;
    if (resolutionMode != other.resolutionMode)
      return false;
    return true;
  }



}

package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;

/**
 * The node for a dependency graph, built during resolution and located in the resolution scope.
 * 
 * @author ferdinando.villa
 *
 */
public class Dependency {

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
   * Model that computes this observable. If null, it's already there from an upper resolution that
   * we can use, or we can obtain it by mediating an upstream dependency.
   */
  Model      model;

  public Dependency(Observable observable) {
    this.observable = observable;
  }



}

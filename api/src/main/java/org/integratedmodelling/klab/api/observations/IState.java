package org.integratedmodelling.klab.api.observations;

import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * A {@link IState} is the semantic {@link IObservation} that specializes a non-semantic
 * {@link IDataArtifact data artifact}. Its {@link #getObservable() observable} is always a quality.
 * <p>
 * @author ferdinando.villa
 *
 */
public interface IState extends IObservation, IDataArtifact {

  /**
   * True if the state has the same value overall independent of scale.
   * 
   * @return true if constant
   */
  boolean isConstant();

  /**
   * True if the state is expected to change at every time transition. This depends on semantics and
   * context: it will be expected to change if the observable is affected by a process that exists
   * in the context. States are expected to be changeable even if not dynamic, but will define their
   * storage more conservatively (and less efficiently) if so.
   * 
   * @return true if dynamic
   */
  boolean isDynamic();

  /**
   * Return either the original state or a wrapper that will allow get/set of values in a specified
   * observation semantics.
   * 
   * @param observable an observable that must be identical semantically but may have different
   *        observation semantics, e.g. a "by" clause or different units/currencies.
   * @return the (possibly wrapped) state
   */
  IState as(IObservable observable);

}

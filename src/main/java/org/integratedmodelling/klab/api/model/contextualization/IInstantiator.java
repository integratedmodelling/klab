package org.integratedmodelling.klab.api.model.contextualization;

import java.util.List;
import org.integratedmodelling.klab.api.data.raw.IRawObject;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * Instantiators are contextualizers that can be provided for any direct observation and are called
 * to produce observations in a context.
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public interface IInstantiator<T extends IRawObject> extends IContextualizer {

  /**
   * Instantiate and return the target observations in the passed context. Those observations will
   * be independently resolved afterwards by the dataflow.
   * 
   * @param semantics the direct observable we must incarnate in the context.
   * @param context the context observation.
   *
   * @return a list of observations, possibly empty but never null.
   */
  List<T> instantiate(IObservable semantics, IComputationContext context);

}

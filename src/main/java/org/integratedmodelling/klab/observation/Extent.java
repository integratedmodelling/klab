package org.integratedmodelling.klab.observation;

import java.util.Iterator;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.observation.Scale.Mediator;

public abstract class Extent extends AbstractExtent {

  public static int INAPPROPRIATE_LOCATOR = -2;
  public static int GENERIC_LOCATOR = -1;

  @Override
  public Iterator<IExtent> iterator() {
    return new Iterator<IExtent>() {

      int i = 0;

      @Override
      public boolean hasNext() {
        return i < (size() - 1);
      }

      @Override
      public IExtent next() {
        return getExtent(i++);
      }

    };
  }

  /**
   * All extents must be able to produce a deep copy of themselves.
   * 
   * @return a new extent identical to this.
   */
  public abstract Extent copy();

  /**
   * All extents must have a two-way street between k.IM code functions and themselves.
   * 
   * @return the k.IM function call specifying this extent.
   */
  public abstract IServiceCall getKimSpecification();

  /**
   * Return the n-th state of the ordered topology as a new extent with one state.
   * 
   * @param stateIndex
   * @return a new extent with getValueCount() == 1.
   */
  public abstract IExtent getExtent(long stateIndex);

  /**
   * Return the single-valued topological value that represents the total extent covered, ignoring
   * the subdivisions.
   * 
   * @return the full, 1-dim extent.
   */
  public abstract ITopologicallyComparable<?> getExtent();

  /**
   * True if the i-th state of the topology correspond to a concrete subdivision where observations
   * can be made. Determines the status of "data" vs. "no-data" for the state of an observation
   * defined over this extent.
   * 
   * @param stateIndex
   * @return whether there is an observable world at the given location.
   */
  public abstract boolean isCovered(long stateIndex);


  /**
   * True if the extent is completely specified and usable. Extents may be partially specified to
   * constrain observation to specific representations or scales.
   * 
   * @return true if consistent
   */
  public abstract boolean isConsistent();

  /**
   * Return true if this extent covers nothing.
   * 
   * @return true if empty
   */
  public abstract boolean isEmpty();

  /**
   * Each extent may have 1+ inner dimensions. If so, the linear offset (0 .. getMultiplicity())
   * will be mapped to them according to their size and order. This one returns the full internal
   * dimensionality. If the extent is one-dimensional, it will return
   * <code>new int[] { getMultiplicity() }</code>.
   *
   * @return dimension sizes
   */
  public abstract long[] getDimensionSizes();


  /**
   * Get a state mediator to the passed extent. If extent is incompatible return null; if no
   * mediation is needed, return an identity mediator, which all implementations should provide. Do
   * not throw exceptions (unchecked exception only if called improperly).
   * 
   * @param extent the foreign extent to mediate to.
   * @param observable the observable - mediators will want to know it to establish the aggregation
   *        strategy.
   * @param trait a data reduction trait to interpret the mediated values (may be null).
   * @return the configured mediator or null
   * 
   */
  public abstract Mediator getMediator(IExtent extent, IObservable observable,  IConcept trait);

}

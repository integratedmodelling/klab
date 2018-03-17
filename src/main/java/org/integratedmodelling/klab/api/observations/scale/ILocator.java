package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.observations.IState;

/**
 * Any topology or subset of it can be used as a locator. {@link IScale Scales} and {@link IExtent
 * extents} are locators and can produce their component locators as appropriate. They can also
 * "relocate" by producing lazy mediators that allow seeing observations through different lenses.
 * 
 * This is an opaque interface that does not expose byte offsets; any translation should happen
 * within the implementing classes, e.g. in {@link IState#getValue(ILocator) retrieving or setting
 * values from states}, and the "conformant" cases where the locator correspond to a simple offset
 * without mediations should be detected and translated as fast as possible.
 * 
 * @author Ferd
 *
 */
public interface ILocator {

  /**
   * Return another locator that describes the portion of the topology located by the passed one.
   * According to the types of locator passed, the return value may be a scanner for a dimension or
   * a subset of another.
   * 
   * @param locator
   * @return another valid locator
   * @throws IllegalArgumentException if the locator is inappropriate, i.e. does not intersect this
   *         either in extent or geometry.
   */
  ILocator at(ILocator locator);

  /**
   * Return another locator to point to a specific state within a shape returned by
   * {@link #getShape(org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type)}. This can be
   * used in contextualizers to preserve semantics when addressing dependent states and numeric
   * offsets are required to interface to other APIs.
   *
   * @param dimension the dimension to which the offsets refer
   * @param offsets
   * @return a locator pointing to the passed offsets in the passed dimension relative to this.
   */
  ILocator at(Dimension.Type dimension, long... offsets);

  /**
   * Iterate over a dimension. This will produce in turn all the locator pointing to each state in
   * the requested dimension, everything else being the same as this.
   * 
   * @param dimension
   * @return
   */
  Iterable<ILocator> over(Dimension.Type dimension);
  
  /**
   * Return the topological numerosity and shape correspondent to the passed dimension of the
   * underlying geometry. For example, <code>locator.getShape(Type.SPACE)</code> called on a scale
   * locator where space is a 10x20 grid will return [10, 20]. This is normally called in
   * contextualizers when numeric offsets need to be exposed. Because contextualizers are declared
   * with their geometry, there should be no need for error checking, and asking for a dimension
   * that is not part of the locator will throw an exception.
   * 
   * @param dimension
   * @return the shape corresponding to the dimension. Never null.
   * @throws IllegalArgumentException if the locator does not have the requested dimension.
   */
  long[] getShape(Dimension.Type dimension);

}

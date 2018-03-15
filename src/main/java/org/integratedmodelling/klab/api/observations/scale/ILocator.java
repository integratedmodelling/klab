package org.integratedmodelling.klab.api.observations.scale;

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

}

package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.api.observations.IState;

/**
 * Any topology or subset of it can be used as a locator. {@link IScale Scales} and {@link IExtent
 * extents} are locators and can produce their component locators as appropriate. They can also
 * "relocate" by producing lazy mediators that allow seeing observations through different lenses.
 * 
 * This is an opaque interface and behaving correctly when used is left to the implementation.
 * Locators are used for example to {@link IState#getValue(ILocator) retrieve or set values from
 * states}.
 * 
 * @author Ferd
 *
 */
public interface ILocator {

  /**
   * Return another locator that describes the portion located by the passed one. According to the
   * types of locator passed, the return value may be a scanner for a dimension or a subset of
   * another. 
   * 
   * @param locator
   * @return another valid locator
   * @throws IllegalArgumentException if the locator is inappropriate, i.e. does not intersect this either
   *    in extent or geometry.
   */
  ILocator at(ILocator locator);

}

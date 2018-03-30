package org.integratedmodelling.kim.api.data;

/**
 * Any geometry or subset of it can be used as a locator. {@link IScale Scales} and {@link IExtent
 * extents} are locators and can produce their component locators as appropriate. They can also
 * "relocate" by producing lazy mediators that allow seeing observations through different lenses.
 * 
 * Numeric offsets are only exposed to communicate with external raw data APIs; within k.LAB code ,
 * translation should happen within the implementing classes, and the "conformant" cases where the
 * locator correspond to a simple offset without mediations should be detected and translated as
 * fast as possible.
 * 
 * @author Ferd
 *
 */
public interface ILocator {

  /**
   * Return another locator that describes the portion of the geometry located by the passed one.
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

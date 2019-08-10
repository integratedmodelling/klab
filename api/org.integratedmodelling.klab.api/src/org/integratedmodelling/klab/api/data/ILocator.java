package org.integratedmodelling.klab.api.data;

/**
 * Any geometry or subset of it can be used as a locator. Scales and extents are
 * also locators and can produce their component locators as appropriate. They
 * can also "relocate" by producing lazy mediators that allow seeing
 * observations through different lenses.
 * <p>
 * Numeric offsets are only exposed to communicate with external raw data APIs;
 * within k.LAB code , translation should happen within the implementing
 * classes, and the "conformant" cases where the locator correspond to a simple
 * offset without mediations should be detected and translated as fast as
 * possible.
 * <p>
 * Locators can be parsed from a simple string parameters using the syntax
 * below:
 * 
 * <pre>
 * &lt;geometry&gt;@n,m,...
 * </pre>
 * 
 * where the {@link IGeometry geometry} specs before @ and the @ character
 * itself are optional if the locator is part of a request that unambiguously
 * identifies an observation. Any of the numbers in the list after that is either a
 * linear long integer offset in the correspondent dimension of the geometry or
 * a set of dimensional offsets in parentheses for dimensions with inherent
 * dimensionality > 1, and each can be substituted by a dot, meaning that the
 * entire dimension is located. If only one offset is mentioned, the remaining
 * ones are substituted with dots. So usually [0] in a temporally explicit
 * context means whatever is located at t=0.
 * 
 * @author Ferd
 *
 */
public interface ILocator {

	/**
	 * Use this instead of null to pass to extent functions when the entire extent
	 * should be used.
	 */
	public static ILocator FULL = null;

	/**
	 * Return another locator that describes the portion of the geometry located by
	 * the passed one. According to the types of locator passed, the return value
	 * may be a scanner for a dimension or a subset of another.
	 * 
	 * @param locator
	 * @return another valid locator
	 * @throws IllegalArgumentException if the locator is inappropriate, i.e. does
	 *                                  not intersect this either in extent or
	 *                                  geometry.
	 */
	ILocator at(ILocator locator);

	/**
	 * Get a locator of the passed interface from this one, or null. Allows
	 * switching between a full-scale locator to a specific extent's, or from/to an
	 * offset-based locator to an extent-based one.
	 * 
	 * @param type
	 * @return
	 */
	<T extends ILocator> T as(Class<T> cls);

}

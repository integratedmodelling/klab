package org.integratedmodelling.kim.api.data;

import java.io.Serializable;
import java.util.List;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type;

/**
 * A IGeometry is the declaration of the topological dimensions for an observed resource or
 * computation (for example identified by a ({@link IResource#getGeometry()} URN) or by a
 * {@link IPrototype#getGeometry() service prototype} declared in KDL). When used in such a
 * declarative fashion it may have a granularity > 1, meaning that it describes a multiplicity of
 * resources, and one child geometry describing the geometry of each resource.
 * <p>
 * Geometries do not contain any semantic information, and in k.LAB are used after conversion into a
 * worldview-aware {@code IScale scale} ({@code IWorldview#getScale(IGeometry)}) which extends the
 * {@code IGeometry} API. The scale also precisely locates the geometry and guarantees valid values
 * of the corresponding <strong>shape</strong>, consisting of long sizes and extents, which the
 * geometry can describe using {@link #size()} and its {@link Dimension#shape() dimensions} but in
 * the simplest instance returns undefined values for. When used as a {@code IScale} the geometry is
 * always associated to an observation, so it never has granularity != 1.
 * <p>
 * <strong>Declaring geometries</strong>
 * <p>
 * A geometry can be declared through a simple string (which is also its string value). Each
 * dimension can use S/s for space, T/s for time, and any other uppercase/lowercase letters except S
 * and T for other extents (the latter are currently unused and will need a "legend" of sort if/when
 * so).
 * <p>
 * Each letter can be used alone if the shape consists of one extent without subdivisions, or it can
 * be followed by the number of dimensions for each extent if subdivided: so T will mean located in
 * time but not distributed, while T1 will mean located and distributed in time along a
 * 1-dimensional topology (with an arbitrary 'shape', i.e. number of subdivisions). If the uppercase
 * letter is used, the topology is regular and the only information needed to characterize it is the
 * number of subdivisions (return value of {@link Dimension#shape()}). If the letter is lowercase,
 * the topology is irregular. Use the uppercase letter if there is no subdivision.
 * <p>
 * The trivial case is a scalar geometry, describing scalars with no structure and specified by an
 * asterisk.
 * <p>
 * If the shape is only letters with optional numbers, the topology is for a single object.
 * Otherwise, it can be prefixed by #, which distributes the geometry across multiple objects.
 * Objects that are children of another or others are defined after a comma. So for example
 * <p>
 * 
 * <code>
 * TS3,#S2
 * </code>
 *
 * <p>
 * denotes a single 3-dimensional regular spatial geometry (voxel cube), referenced but not
 * distributed in time and containing multiple sub-objects with just a 2-dimensional spatial
 * geometry; while
 * <p>
 * 
 * <code>
 * #s0
 * </code>
 * 
 * <p>
 * denotes a non-temporal set of irregular points in space. This notation assumes that all
 * dimensions of a topology are either regular or irregolar.
 * 
 * @author fvilla
 * 
 * @see {@link IResource#getGeometry()}
 * @see {@link IWorldview#getScale(IGeometry)}
 * 
 */
public interface IGeometry extends Serializable, ILocator {

  enum Granularity {
    /**
     * 
     */
    SINGLE,
    /**
     * 
     */
    MULTIPLE
  }

  /**
   * Constant for non-dimensional (referenced but not distributed) return value of
   * {@link Dimension#getDimensionality()}.
   */
  public static final int  NONDIMENSIONAL = -1;

  /**
   * Constant for undefined dimension size.
   */
  public static final long UNDEFINED      = -1l;

  /**
   * @author Ferd
   *
   */
  public interface Dimension {

    enum Type {
      /**
       * 
       */
      NUMEROSITY,
      /**
       * 
       */
      TIME,
      /**
       * 
       */
      SPACE;
    }

    /**
     * Match against constants {@link IGeometry#SPACE} and {@link IGeometry#TIME}. If none of these,
     * any other user-defined dimension is possible - conventions must be established in worldview
     * for those.
     * 
     * @return the dimension type
     */
    Type getType();

    /**
     * Whether any subdivisions in this dimension are regular or irregular.
     * 
     * @return regularity
     */
    boolean isRegular();

    /**
     * Can be {@link IGeometry#NONDIMENSIONAL} or a positive (0+) integer. Non-dimensional means
     * referenced but not distributed.
     * 
     * @return dimensionality of this dimension
     */
    int getDimensionality();

    /**
     * Return a long if this maps directly to the original dimension, or -1 if
     * mediation is necessary.
     * 
     * @param index
     * @return a valid offset for this locator, or -1 if mediation is needed.
     * @throws IllegalArgumentException if the locator type is not suitable for the receiver.
     */
    public abstract long getOffset(ILocator index);
    
    /**
     * Return the size of this dimension. In a geometry that has been declared but not defined (such
     * as the result of parsing a dimension string) this will return {@link IGeometry#UNDEFINED},
     * which is a negative value.
     * 
     * @return the size of the dimension or
     */
    long size();

    /**
     * Return the topological numerosity and shape correspondent to the passed dimension of the
     * underlying geometry. For example, <code>locator.getShape(Type.SPACE)</code> called on a scale
     * locator where space is a 10x20 grid will return [10, 20]. This is normally called in
     * contextualizers when numeric offsets need to be exposed. Because contextualizers are declared
     * with their geometry, there should be no need for error checking, and asking for a dimension
     * that is not part of the locator will throw an exception.
     * 
     * In a geometry whose size is undefined, this will return an array of
     * {@link IGeometry#UNDEFINED} values of size corresponding to the dimensionality.
     * 
     * @param dimension
     * @return the shape corresponding to the dimension. Never null.
     * @throws IllegalArgumentException if the locator does not have the requested dimension.
     */
    long[] shape();

  }

  /**
   * A geometry may imply another for component objects. E.g. spatial data may have geometry and
   * define objects within it, with different geometry constrained by this.
   * 
   * Was using Optional but it does not serialize well - do not do that.
   * 
   * @return the optional child geometry, or null.
   */
  IGeometry getChild();

  /**
   * Return all the dimensions this geometry. Dimensions are reinterpreted through the worldview and
   * turned into the worldview's topological interpretation before a scale can be built.
   * 
   * @return all dimensions
   */
  List<Dimension> getDimensions();

  /**
   * Return the dimension of the passed type, or null.
   * 
   * @param type
   * @return
   */
  Dimension getDimension(Type type);

  /**
   * A geometry may specify one or multiple objects.
   * 
   * @return the granularity
   */
  Granularity getGranularity();

  /**
   * An empty geometry applies to any resource that does not produce raw information but processes
   * data instead.
   * 
   * @return true for a geometry that was not specified.
   */
  boolean isEmpty();

  /**
   * A trivial geometry describes scalar values with no structure.
   * 
   * @return
   */
  boolean isScalar();

  /**
   * The product of the size of all dimensions. If one or more of the dimensions has size ==
   * UNDEFINED, return UNDEFINED.
   * 
   * @return the size of the geometry
   */
  long size();
  

  /**
   * Iterate over a dimension. This will produce in turn all the locators pointing to each state in
   * the requested dimension, everything else being the same as this.
   * 
   * @param dimension
   * @return an iterable over the dimension
   */
  Iterable<ILocator> over(Dimension.Type dimension);

  /**
   * Return another locator to point to a specific state within a shape returned by
   * {@link #getShape(org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type)}. This can be
   * used in contextualizers to preserve semantics when addressing dependent states and numeric
   * offsets are required to interface to other APIs.
   *
   * @param dimension the dimension to which the offsets refer
   * @param offsets (FIXME check)
   * @return a locator pointing to the passed offsets in the passed dimension relative to this.
   */
  ILocator at(Dimension.Type dimension, long... offsets);
  
  /**
   * Return a long if this maps directly to the original geometry, or -1 if
   * mediation is necessary.
   * 
   * @param index
   * @return a valid offset for this locator, or -1 if mediation is needed.
   * @throws IllegalArgumentException if the locator type is not suitable for the receiver.
   */
  public abstract long getOffset(ILocator index);

  /**
   * Get the shape of the requested dimension. 
   * 
   * @param space
   * @throws IllegalArgumentException if the dimension is not part of the geometry.
   * @return the dimension's shape
   */
  long[] shape(Type space);

}

package org.integratedmodelling.klab.api.data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.api.knowledge.IWorldview;

/**
 * A IGeometry describes the shape of a resource identified by a URN ({@link IResource#getGeometry()}). It
 * does not contain any semantic information, and before use, it must be converted into a worldview-aware
 * IScale ({@link IWorldview#getScale(IGeometry)}). The geometry may describe one root object or a list of
 * objects at the same level, possibly with child objects.
 * 
 * A geometry can be summarized through a simple string (its string value), currently used only for
 * at-a-glance checking and not parsed. Each shape can use S/s for space, T/s for time, and any other
 * uppercase/lowercase letters except S and T for other extents (the latter are currently unused and will need
 * a "legend" of sort if/when so). Each letter can be used alone if the shape is 1 extent without
 * subdivisions, or it can be followed by the number of dimensions for each extent if subdivided: so T will
 * mean located in time but not distributed, while T1 will mean located and distributed in time along a
 * 1-dimensional topology. If the uppercase letter is used, the topology is regular and the only information
 * needed to characterize it is the number of subdivisions (return value of getShape). If the letter is
 * lowercase, the topology is irregular and getShape will return each subdivision's relative size in that
 * dimension. Use the uppercase letter if there is no subdivision.
 * 
 * The trivial case is a scalar geometry, describing scalars with no structure and specified by an asterisk.
 * 
 * If the shape is only letters with optional numbers, the topology is for a single object. Otherwise, it can
 * be prefixed by #, which distributes the geometry across multiple objects. Objects that are children of
 * another or others are defined after a comma. So for example
 * 
 * <pre>
 * TS3,#S2
 * <pre>
 *
 * denotes a single 3-dimensional regular spatial geometry (voxel cube), referenced but not distributed in time
 * and containing multiple sub-objects with just a 2-dimensional spatial geometry; while
 * 
 * <pre>
 * #s0
 * </pre>
 * 
 * denotes a non-temporal set of irregular points in space. This notation assumes that all dimensions of a
 * topology are either regular or irregolar.
 * 
 * @author fvilla
 * 
 * @see {@link IResource#getGeometry()}
 * @see {@link IWorldview#getScale(IGeometry)}
 * 
 */
public interface IGeometry extends Serializable {

    enum Granularity {
        SINGLE,
        MULTIPLE
    }

    /**
     * Constant for time dimension in {@link Dimension#getType()}. 
     */
    public static final int TIME           = 0;
    /**
     * Constant for space dimension in {@link Dimension#getType()}. 
     */
    public static final int SPACE          = 1;

    /**
     * Constant for non-dimensional (referenced but not distributed) return value of {@link Dimension#getDimensionality()}.
     */
    public static final int NONDIMENSIONAL = -1;

    public interface Dimension {

        /**
         * Match against constants {@link IGeometry#SPACE} and {@link IGeometry#TIME}. If none of these,
         * any other user-defined dimension is possible - conventions must be established in worldview for
         * those.
         * 
         * @return the dimension type
         */
        int getType();

        /**
         * Whether any subdivisions in this dimension are regular or irregular.
         * @return regularity
         */
        boolean isRegular();

        /**
         * Can be {@link IGeometry#NONDIMENSIONAL} or a positive (0+) integer. Non-dimensional means referenced
         * but not distributed. 
         * @return dimensionality of this dimension
         */
        int getDimensionality();
    }

    /**
     * A geometry may imply another for component objects. E.g. spatial data may have geometry and
     * define objects within it, with different geometry constrained by this.
     * 
     * @return the optional child geometry
     */
    Optional<IGeometry> getChild();

    /**
     * Return all the dimensions this geometry. Dimensionsare  reinterpreted through the worldview and turned into
     * the worldview's topological interpretation before a scale can be built.
     * 
     * @return all dimensions
     */
    List<Dimension> getDimensions();

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

}

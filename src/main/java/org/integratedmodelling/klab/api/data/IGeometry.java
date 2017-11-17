package org.integratedmodelling.klab.api.data;

import java.io.Serializable;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;
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
 * If the shape is only letters with optional numbers, the topology is for a single object. Otherwise, it can
 * be prefixed by *, which distributes the geometry across multiple objects. Objects that are children of
 * another or others are defined after a slash. So for example
 * 
 * <pre>
 * TS3/*S2
 * <pre>
 *
 * denotes a single 3-dimensional regular spatial geometry (voxel cube), referenced but not distributed in time
 * and containing multiple sub-objects with just a 2-dimensional spatial geometry; while
 * 
 * <pre>
 * *s0
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
public interface IGeometry extends Iterable<IGeometry>, Serializable {

    /**
     * Number of objects in this geometry. Always 1 or more; if > 1, the geometry is a container and its shape
     * describes the union of its objects.
     * 
     * @return
     */
    int size();

    /**
     * 
     * @return
     */
    List<IGeometry> getChildren();

    /**
     * Return all the extent concepts that are used as dimensions this geometry. The concepts will be abstract
     * and come from the k.LAB core ontology; any shape and regularity can be connected to each. The concepts
     * and the details of the corresponding shapes must be reinterpreted through the worldview and turned into
     * the worldview's topological interpretation before a scale can be built.
     * 
     * @return
     */
    List<IConcept> getDimensions();

    /**
     * Get the number of dimensions for the passed extent.
     * 
     * @param extent
     * @return
     */
    int getDimensionCount(IConcept extent);

    /**
     * Get the shape for the passed extent. The array will have {@link #getDimensionCount(IKimConcept)}
     * elements.
     * 
     * @param extent
     * @return
     */
    long[] getShape(IConcept extent);

    /**
     * Return the regularity of each dimension of the shape for the passed extent. If the shape for this
     * extent is regular, the value for the dimension will contain the number of equal elements; otherwise it
     * will contain locations for each irregularly placed element.
     * 
     * @param extent
     * @return
     */
    boolean[] isRegular(IConcept extent);

}

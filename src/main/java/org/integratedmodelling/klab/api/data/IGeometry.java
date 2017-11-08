package org.integratedmodelling.klab.api.data;

import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;

/**
 * A IGeometry describes the shape of a data source identified by a URN. Before use, it must be converted into
 * a worldview-aware IScale. It describes one root object or a list of objects at the same level, possibly
 * with child objects.
 * 
 * @author fvilla
 *
 */
public interface IGeometry extends Iterable<IGeometry> {

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
    List<IKimConcept> getDimensions();

    /**
     * Get the number of dimensions for the passed extent.
     * 
     * @param extent
     * @return
     */
    int getDimensionCount(IKimConcept extent);

    /**
     * Get the shape for the passed extent. The array will have {@link #getDimensionCount(IKimConcept)} elements.
     * 
     * @param extent
     * @return
     */
    long[] getShape(IKimConcept extent);

    /**
     * Return the regularity of each dimension of the shape for the passed extent. If the shape for this
     * extent is regular, the value for the dimension will contain the number of equal elements; otherwise
     * it will contain locations for each irregularly placed element.
     * 
     * @param extent
     * @return
     */
    boolean[] isRegular(IKimConcept extent);

}

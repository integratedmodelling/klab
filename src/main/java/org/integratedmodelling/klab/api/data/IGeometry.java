package org.integratedmodelling.klab.api.data;

import java.util.List;

import org.integratedmodelling.kim.api.IConcept;

/**
 * A IGeometry describes the shape of a data source identified by a URN. Before
 * use, it must be converted into a worldview-aware IScale. It describes one
 * root object or a list of objects at the same level, possibly with child
 * objects.
 * 
 * @author fvilla
 *
 */
public interface IGeometry extends Iterable<IGeometry> {

	/**
	 * Number of objects in this geometry. Always 1 or more; if > 1, the geometry is
	 * a container and its shape describes the union of its objects.
	 * 
	 * @return
	 */
	int size();

	List<IGeometry> getChildren();

	List<IConcept> getDimensions();

	int getDimensionCount(IConcept extent);

	long[] getShape(IConcept extent);

	boolean isRegular(IConcept extent);
	
}

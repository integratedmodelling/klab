package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * A {@code IExtent} is a semantically aware {@link Dimension} that represents an observation of the
 * topology it describes. {@code IExtent}s make up the dimensions of the semantically aware
 * {@link IGeometry} represented by {@link IScale}.
 * 
 * In a {@code IExtent}, the {{@link #size()} will never return {IGeometry#UNDEFINED} and the shape
 * returned by {{@link #shape()} will never contain undefined values.
 * 
 * {@code IExtent}s can be used as {@link ILocator locators} to address the value space of
 * observations.
 * 
 * @author ferdinando.villa
 *
 */
public interface IExtent
    extends ILocator, ITopology<IExtent>, Iterable<IExtent>, IGeometry.Dimension {

  /**
   * Each extent must be able to return a worldview-dependent integer scale rank, usable to
   * constrain model retrieval to specific scales. In spatial extents this corresponds to something
   * like a "zoom level".
   * 
   * The worldview defines this using numeric restrictions on the data property used to annotate
   * scale constraints and establishes the range and granularity for the ranking.
   * 
   * @return an integer summarizing the extent's size within the range covered by the worldview
   */
  int getScaleRank();

  /**
   * Collapse the multiplicity and return the extent that represents the full extent of our topology
   * in one single state. This extent may not be of the same class.
   * 
   * @return a new extent with size() == 1.
   */
  IExtent collapse();

  /**
   * Return an extent of the same domainConcept that represents the merge of the two. The meaning of
   * merging depends on the extent. It should accommodate partially specified extents, such as adding
   * a grid resolution to a shape.
   * 
   * TODO add LogicalConnector parameter and eliminate union/intersection.
   * 
   * @param extent
   * @return the merged extent
   * @throws KlabException
   */
  IExtent merge(IExtent extent) throws KlabException;

  /**
   * Return a double that describes the extent of this topological object. It should only be used to
   * compare objects of the same type.
   * 
   * @return the covered extent
   */
  double getCoveredExtent();

  /**
   * If this extent specifies a larger portion of the topology than the modeled world contains,
   * return a < 1.0 coverage. This can happen when the extent semantics constrains the
   * representation - e.g. regular spatial grids covering more space than there actually is.
   * Coverage = 0 should never happen as such extents should not be returned by any function.
   * 
   * @return coverage in the range (0 1]
   */
  double getCoverage();

  /**
   *
   * 
   * @param other
   * @return the union of other and this
   * @throws KlabException
   */
  @Override
  IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how);

}

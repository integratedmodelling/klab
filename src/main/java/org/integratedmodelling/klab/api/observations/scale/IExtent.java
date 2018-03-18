package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.exceptions.KlabException;

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
   * Return the main concept for the topological class represented by this extent. It should be the
   * same concept for all the different kinds of extents representing the same domain, i.e.
   * geospace:Space, which should be an ancestor to the observable of this extent. It will be used
   * to ensure that no two extents of the same domain concept appear in a context. The context
   * implementation is expected to try to merge extents that share the same domain concept even if
   * their observables are not the same.
   * 
   * @return the domain concept for the extent
   */
  IConcept getDomainConcept();

  /**
   * Collapse the multiplicity and return the extent that represents the full extent of our topology
   * in one single state. This extent may not be of the same class.
   * 
   * @return a new extent with getValueCount() == 1.
   */
  IExtent collapse();


  /**
   * Return an extent of the same domainConcept that represents the merge of the two. If force is
   * false, this operation should just complete the semantics - e.g. add a shape to a grid or such -
   * and complain if things are incompatible.
   * 
   * If force is true, return an extent that is capable of representing the passed one through the
   * "lens" of our semantics. Return null if the operation is legal but it results in no context,
   * throw an exception if we don't know what to do with the passed context.
   * 
   * 
   * @param extent
   * @param force
   * @return the merged extent
   * @throws KlabException
   */
  IExtent merge(IExtent extent, boolean force) throws KlabException;

  /**
   * If this extent specifies a larger portion of the topology than the modeled world contains,
   * return a < 1.0 coverage. This can happen when the extent semantics constrains the
   * representation - e.g. regular spatial grids covering more space than there is. Coverage = 0
   * should not happen as such extents should not be returned by any function.
   * 
   * @return coverage in the range (0 1]
   */
  double getCoverage();

}

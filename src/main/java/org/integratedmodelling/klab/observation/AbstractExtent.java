package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;

/**
 * Common superclass for all Extents.
 * 
 * All this is providing is the scaleId field, used to match to a corresponding ID in the owning
 * Scale to very quickly check for same-scale lineage so that costly tests of conformity can be
 * skipped when matching topologies for mediation.
 * 
 * The (also abstract) Extent class adds more requirements for complex and composite extents. Simple
 * ones such as Cell, Shape and Period only adopt this one.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractExtent implements IExtent {

  private transient long scaleId;

  void setScaleId(long id) {
    this.scaleId = id;
  }
  
  boolean isOwnExtent(Scale scale) {
    return scale.scaleId == scaleId;
  }

  /**
   * Translate a linear offset into the offsets for each dimension. If the dimension is 1, return
   * the offset itself.
   * 
   * @param linearOffset
   * @return dimension offsets
   */
  public abstract long[] getDimensionOffsets(long linearOffset);

  /**
   * Check and return the offset corresponding to the passed dimensions.
   * @param dimOffsets
   * @return
   */
  public abstract long getOffset(long[] dimOffsets);
  
  /**
   * Return the single-valued topological value that represents the total extent covered, ignoring
   * the subdivisions.
   * 
   * @return the full, 1-dim extent.
   */
  public abstract ITopologicallyComparable<?> getExtent();
}

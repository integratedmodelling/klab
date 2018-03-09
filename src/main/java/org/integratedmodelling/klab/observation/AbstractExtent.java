package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.observations.scale.IExtent;

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

}

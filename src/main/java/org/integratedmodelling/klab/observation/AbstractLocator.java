package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.observations.scale.ILocator;

public class AbstractLocator {

  /**
   * Return a long if this maps directly to the original scale, or -1 if
   * mediation is necessary.
   * 
   * @param index
   * @return
   */
  public long getOffset(ILocator index) {
    // TODO Auto-generated method stub
    return -1;
  }
}

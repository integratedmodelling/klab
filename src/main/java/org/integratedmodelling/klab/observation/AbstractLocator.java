package org.integratedmodelling.klab.observation;

import org.integratedmodelling.klab.api.observations.scale.ILocator;

public abstract class AbstractLocator {

  /**
   * Return a long if this maps directly to the original scale, or -1 if
   * mediation is necessary.
   * 
   * @param index
   * @return a valid offset for this locator, or -1 if mediation is needed.
   * @throws IllegalArgumentException if the locator type is not suitable for the receiver.
   */
  public abstract long getOffset(ILocator index);
}

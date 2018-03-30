package org.integratedmodelling.klab.api.observations.scale.time;

import org.integratedmodelling.klab.api.observations.scale.IDimensional;

/**
 * Tag interface that will assign temporal nature to a class, so that 
 * inferences can be made when checking units or other types of use.
 * 
 * @author ferdinando.villa
 *
 */
public interface ITemporal extends IDimensional {
    static int getDimensionCount() {
        return 1;
    }
}

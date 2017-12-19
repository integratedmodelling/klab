package org.integratedmodelling.klab.api.observations.scale.space;

import org.integratedmodelling.klab.api.observations.scale.IDimensional;

/**
 * Tag interface that will assign 1-d spatial nature to a class, so that 
 * inferences can be made when checking units or other types of use.
 * 
 * @author ferdinando.villa
 *
 */
public interface ILineal extends IDimensional {
    static int getDimensionCount() {
        return 1;
    }
}

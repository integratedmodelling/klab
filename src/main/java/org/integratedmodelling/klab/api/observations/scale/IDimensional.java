package org.integratedmodelling.klab.api.observations.scale;

/**
 * Adopted by dimensional extents, specifies the count of dimensions in each.
 * 
 * FIXME this info now comes from the worldview, so it should be defined in a "soft" way.
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IDimensional {
    static int getDimensionCount() {
        return 0;
    }
}

package org.integratedmodelling.klab.api.data.mediation;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IExtent;

public interface IUnit extends IValueMediator {

    /**
     * True if a unit describing the passed extent is found at the denominator.
     * 
     * @param extent
     * @return
     */
    boolean isDensity(IConcept extent);

    /**
     * 
     * @return true if its over time
     */
    boolean isRate();

    /**
     * 
     * @return if rate, get the time unit
     */
    IUnit getTimeExtentUnit();

    /**
     * True if this unit is a density over a spatial length.
     * 
     * @return true if density over 1d space
     */
    boolean isLengthDensity();

    /**
     * 
     * @return if 1d density, return space unit
     */
    IUnit getLengthExtentUnit();

    boolean isArealDensity();

    /**
     * If the unit represents an areal density, return the area term with inverted exponents - e.g. if we are
     * something per square meter, return square meters. If not an areal density, return null.
     * 
     * @return unit of areal space if we're an areal density
     */
    IUnit getArealExtentUnit();

    boolean isVolumeDensity();

    IUnit getVolumeExtentUnit();

    boolean isUnitless();

    /**
     * True if this unit is a density over the kind of space represented by the passed extent, which may be
     * null (in which case, obviously, the result is false). Accounts automatically for the dimensionality of
     * the space represented.
     * 
     * @param scale
     * @return true if spatial density
     */
    boolean isSpatialDensity(IExtent scale);
}

package org.integratedmodelling.klab.api.observations.scale;

/**
 * Returned by getIndex, it returns offset indices along only one of the scale extents. It's also
 * capable of checking if the correspondent location is masked (i.e. not really part of the
 * topology) or active.
 * 
 * @author Ferd
 *
 */
public interface ICursor extends Iterable<Long> {

    /**
     * The total number of states accessed by this index.
     * 
     * @return number of states along this dimension
     */
    long size();

    /**
     * true if the index is browsing through a spatial extent.
     * 
     * @return true if iterating over space
     */
    boolean isSpatial();

    /**
     * true if the index is browsing through a temporal extent.
     * 
     * @return true if iterating over time
     */
    boolean isTemporal();

    /**
     * Get the fixed offsets of all the extents. The one we're browsing will be < 0.
     * 
     * @return fixed offset for all extents, with ours < 0
     */
    long[] getOffsets();

    /**
     * True if the dimension this is indexing has multiplicity = 1.
     * 
     * @return true if scalar
     */
    boolean isScalar();

    /**
     * Check if the passed offset (relative to this index's dimension) correspond to a "live"
     * observation when the extents have regular topologies but may have inactive subdivisions.
     * 
     * @param offset
     * @return true if the passed granule is active
     */
    boolean isActive(long offset);
}
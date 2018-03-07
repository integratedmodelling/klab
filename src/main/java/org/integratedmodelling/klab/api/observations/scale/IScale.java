package org.integratedmodelling.klab.api.observations.scale;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * 
 * @author ferdinando.villa
 *
 */
public interface IScale extends IObservationTopology, Iterable<ITransition> {

    /**
     * Adopted by any object that tracks one or more dimensions in a scale, pointing to a precise
     * 'granule' or to a slice for an extent along it. Used in {@link #getIndex(Locator...)} and
     * {@link #locate(Locator...)}, as well as in dataflow execution through {@link IContextualizer}.
     */
    public interface Locator {

        /**
         * Should be a constant but no way to ask for that in an interface. Number of dimension offsets
         * to locate one extent.
         * 
         * @return the number of dimension offsets required for locating a position.
         */
        public int getDimensionCount();

        /**
         * If true, this is locating a full dimension or subset, with multiple extents.
         * 
         * @return true if the locator is an aggregator
         */
        public boolean isAll();

        /**
         * If the locator only covers the granule partially, return a value less than one, reflecting
         * the amount of active coverage. This will only return anything other than 1 when computed by a
         * IState.Mediator, which matches two scales and may find partial coverage when checking one
         * index against another. It should normally return 1 and never return 0.
         * 
         * @return the proportion of the topological subdivision we're locating
         */
        public double getWeight();

        /**
         * Return the concept for the extent this is locating.
         * 
         * @return the extent concept
         */
        public IConcept getExtent();

    }

    /**
     * Merge all common extents from the given scale, using the force parameter to define how the
     * extents are merged (see IExtent.merge). Extents in common are merged according to the passed
     * operator to compute the merged extent. The adopt parameter controls whether extents in the
     * passed scale that are not in the original one appear in the result. All extents in the original
     * scale will appear in the result.
     *
     * Must not modify the original scales.
     * 
     * @param scale
     * @param how
     * @param adopt
     *
     * @return a new merged scale
     * @throws KlabException
     */
    IScale merge(IScale scale, LogicalConnector how, boolean adopt) throws KlabException;

    /**
     * Get an index to loop over one dimension (set as -1) given fixed position for all others.
     * 
     * @param locators
     * 
     * @return an iterator locked at the passed locators
     */
    ICursor getIndex(Locator... locators);

    /**
     * Get an index to loop over one dimension (set as -1) given fixed position for all others, only
     * considering the sliceIndex-th part of the field from a total number of slices = sliceNumber.
     * Used for parallelization of loops.
     * 
     * @param sliceIndex
     * @param sliceNumber
     * @param locators
     * 
     * @return an iterator as requested
     */
    ICursor getIndex(int sliceIndex, int sliceNumber, Locator... locators);

    /**
     * Take an array of objects that can locate a position in each extent, using the order of the
     * extents in the scale, and return the overall offset of the correspondent state (or -1 if not
     * compatible). The objects will be coordinates in the native reference system of each extent, and
     * should be interpreted correctly; each extent may take one or more objects from the list
     * according to which object is passed (e.g. a spatial point vs. lat and lon doubles).
     * 
     * @param locators
     * @return the offset corresponding to the locators
     */
    long locate(Locator... locators);

    /**
     * Get a scale that has either a 1-dimensional extent for the passed concept or doesn't have the
     * extent at all (if offset < 0). Ensure this scale remembers its offset and previous multiplicity
     * along the extent's dimension so that it will respond properly to getOffset() below.
     * 
     * TODO Possible improvement: allow passing an IExtent instead of an int, to accommodate variable
     * scales and ease the use of the API in some circumstances. That will require a
     * getExtentOffset(IExtent) method.
     * 
     * @param extent
     * @param offset
     * @return the subscale
     */
    IScale getSubscale(IConcept extent, long offset);

    /**
     * Call it on a scale returned by getSubscale to reapply the original offset and obtain the one
     * corresponding to the same granule on the full scale it derives from. If called on a full scale
     * just return the passed offset.
     * 
     * @param subscaleOffset
     * @return original offset
     */
    long getOriginalOffset(long subscaleOffset);

    /**
     * Take in another scale and complete what's left of our specs by merging in its details. E.g.,
     * we're a bounding box, we get a grid without extent, and we become a grid in that bounding box.
     * Will only be called during resolution, so the queries should have selected compatible scales,
     * but throw an exception if anything is not compatible.
     * 
     * @param scale
     * @return harmonized scale
     * @throws KlabException
     */
    IScale harmonize(final IScale scale) throws KlabException;

    /**
     * Get the offset in the specified extent that correspond to the overall offset passed.
     * 
     * @param extent
     * @param overallOffset
     * @return the extent offset
     */
    long getExtentOffset(IExtent extent, long overallOffset);

    /**
     * Return the list of extents ordered by contextualization priority.
     * 
     * @return the extents
     */
    List<IExtent> getExtents();

    /**
     * Get the individual extent offsets corresponding to the overall offset passed.
     * 
     * @param overallOffset
     * @return the extent indexes for the offset
     */
    long[] getExtentIndex(long overallOffset);

    /**
     * True if we have time AND the time topology determines more than a single state. It's also in
     * IObservation, but it's convenient to duplicate it here too.
     * 
     * @return true if distributed in time
     */
    boolean isTemporallyDistributed();

    /**
     * True if we have space AND the space topology determines more than a single state. It's also in
     * IObservation, but it's convenient to duplicate it here too.
     * 
     * @return true if distributed in space
     */
    boolean isSpatiallyDistributed();

    /**
     * 
     * @param offset
     * @return true if offset has all extents active
     */
    boolean isCovered(long offset);

    /**
     * Check that all extents are consistent and meaningful - e.g. empty intervals, degenerate shapes
     * etc.
     * 
     * @return true if scale is OK
     */
    boolean isConsistent();

}

package org.integratedmodelling.klab.api.observations.scale;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale.Locator;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IExtent extends ILocator, ITopology<IExtent>, Iterable<IExtent> {


    /**
     * Each extent must be able to return a worldview-dependent integer scale rank, usable
     * to constrain model retrieval to specific scales. In spatial extents this
     * corresponds to something like a "zoom level".
     * 
     * The worldview defines this using numeric restrictions on the data property used to
     * annotate scale constraints.
     * 
     * @return
     */
    int getScaleRank();

    /**
     * Return the main concept for the topological class represented by this extent. It
     * should be the same concept for all the different kinds of extents representing the
     * same domain, i.e. geospace:Space, which should be an ancestor to the observable of
     * this extent. It will be used to ensure that no two extents of the same domain
     * concept appear in a context. The context implementation is expected to try to merge
     * extents that share the same domain concept even if their observables are not the
     * same.
     * 
     * TODO remove and use getType()/getObservable()
     * 
     * @return the domain concept for the extent
     */
    IConcept getDomainConcept();

    /**
     * Collapse the multiplicity and return the extent that represents the full extent of
     * our topology in one single state. This extent may not necessarily be of the same
     * class.
     * 
     * @return a new extent with getValueCount() == 1.
     */
    IExtent collapse();

    /**
     * Return the n-th state of the ordered topology as a new extent with one state.
     * 
     * @param stateIndex
     * @return a new extent with getValueCount() == 1.
     * @deprecated use locators only
     */
    IExtent getExtent(long stateIndex);

    /**
     * Return the single-valued topological value that represents the total extent
     * covered, ignoring the subdivisions.
     * 
     * @return the full, 1-dim extent.
     */
    ITopologicallyComparable<?> getExtent();

    /**
     * True if the i-th state of the topology correspond to a concrete subdivision where
     * observations can be made. Determines the status of "data" vs. "no-data" for the
     * state of an observation defined over this extent.
     * 
     * @param stateIndex
     * @return whether there is an observable world at the given location.
     * @deprecated use locators only; do not return non-covered extents
     */
    boolean isCovered(long stateIndex);

    /**
     * Return an extent of the same domainConcept that represents the merge of the two. If
     * force is false, this operation should just complete the semantics - e.g. add a
     * shape to a grid or such - and complain if things are incompatible.
     * 
     * If force is true, return an extent that is capable of representing the passed one
     * through the "lens" of our semantics. Return null if the operation is legal but it
     * results in no context, throw an exception if we don't know what to do with the
     * passed context.
     * 
     * 
     * @param extent
     * @param force
     * @return the merged extent
     * @throws KlabException
     */
    IExtent merge(IExtent extent, boolean force) throws KlabException;
//
//    /**
//     * Try to cover the extent with the passed object and return the resulting coverage
//     * object and the proportion of the total extent covered by it.
//     * 
//     * FIXME put inside, remove from API
//     * 
//     * @param obj
//     * @return cov
//     * @throws KlabException
//     */
//    Pair<ITopologicallyComparable<?>, Double> checkCoverage(ITopologicallyComparable<?> obj)
//            throws KlabException;

    /**
     * True if the extent is completely specified and usable. Extents may be partially
     * specified to constrain observation to specific representations or scales.
     * 
     * @return true if consistent
     */
    boolean isConsistent();

    /**
     * Return true if this extent covers nothing.
     * 
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Each extent may have 1+ inner dimensions. If so, the linear offset (0 ..
     * getMultiplicity()) will be mapped to them according to their size and order. This
     * one returns the full internal dimensionality. If the extent is one-dimensional, it
     * will return <code>new int[] { getMultiplicity() }</code>.
     *
     * @return dimension sizes
     * @deprecated use locators only
     */
    long[] getDimensionSizes();

    /**
     * Translate a linear offset into the offsets for each dimension. If the dimension is
     * 1, return the offset itself.
     * 
     * @param linearOffset
     * @param rowFirst
     *            if true, endeavor to return offset in the order that most closely
     *            resembles row-first ordering wrt euclidean x,y,z (default).
     * @return dimension offsets
     * @deprecated use locators only
     */
    long[] getDimensionOffsets(long linearOffset, boolean rowFirst);

    /**
     * Use the passed object to locate an unambiguous extent within the topology. If an
     * extent can be located based on the passed object, return the index of the extent.
     * If the locator is not usable by this extent, return IExtent.INAPPROPRIATE_LOCATOR.
     * If the locator selects the whole extent, return GENERIC_LOCATOR. Passing a locator
     * that does not fit the extent should be admitted. Do not throw errors but return
     * INAPPROPRIATE_LOCATOR in that case.
     * 
     * @param locator
     * @return the offset corresponding to the locator
     * @deprecated use locators only
     */
    long locate(Locator locator);

    /**
     * Get a state mediator to the passed extent. If extent is incompatible return null;
     * if no mediation is needed, return an identity mediator, which all implementations
     * should provide. Do not throw exceptions (unchecked exception only if called
     * improperly).
     * 
     * @param extent
     *            the foreign extent to mediate to.
     * @param observable
     *            the observable - mediators will want to know it to establish the
     *            aggregation strategy.
     * @param trait
     *            a data reduction trait to interpret the mediated values (may be null).
     * @return the configured mediator or null     
     * 
     * @deprecated bring behind API
     */
    IState.Mediator getMediator(IExtent extent, IObservable observable, IConcept trait);
}

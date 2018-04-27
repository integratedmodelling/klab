package org.integratedmodelling.klab.scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Geometry.OffsetLocator;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.InstanceIdentifier;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;

public class Scale implements IScale {

    private static AtomicLong counter = new AtomicLong(0);
    transient long scaleId = counter.incrementAndGet();

    /**
     * 
     */
    private static final long serialVersionUID = -7855922162677333636L;

    /*
     * the underlying geometry, only built when required
     */
    private Geometry geometry = null;

    /**
     * Mediators are created by extents and are used to implement views of a state that mediate values
     * to another scale.
     * <p>
     * A mediator should be aware that the extents it mediates may have changed (it can use
     * States.hasChanged() to inspect that) and be able to readjust if necessary. This will properly
     * handle moving agents.
     * <p>
     * A <strong>conformant</strong> mediator is one where the mapping is offset-to-offset, i.e.
     * no aggregation or distribution is necessary. This for example applies to grid-to-grid mediators
     * where one grid is a simple subset of the other. Conformant mediators should be produced 
     * whenever possible, for example as a result of applying a scale resulting from a merge() of 
     * a subset on the original.
     * <p>
     * FIXME all mediators should be change listeners for the mediated scale, and rearrange the
     * mediation strategy at each change as needed - we should subscribe them automatically.
     * 
     * @author ferdinando.villa
     */
    public interface Mediator {

        /**
         * The kind of aggregation that the mediation implies.
         * 
         * @return aggregation type
         */
        Aggregation getAggregation();

        // /**
        // * Apply the locators to the original state, adding whatever other locators the mediation
        // * strategy implies. Return the aggregated value implied by the strategy.
        // *
        // * @param originalState
        // * @param otherLocators
        // *
        // * @return a mediated object
        // */
        // Object mediateFrom(IState originalState, Locator... otherLocators);

        /**
         * Apply the passed value to our scale and return the result.
         * 
         * @param value
         * @param index
         * @return a mediated object
         */
        Object mediateTo(Object value, long index);

        // /**
        // * Get all the locators that will map the original state's scale to the passed index in the
        // * mediated scale. Weights should be assigned according to coverage and aggregation strategy.
        // *
        // * @param index
        // * @return the locators needed to mediate
        // */
        // List<Locator> getLocators(long index);

        /**
         * Reduce the passed collection of pairs (value, weight) to one value according to aggregation
         * strategy.
         * 
         * @param toReduce
         * @param metadata a map to fill with any relevant statistics related to the aggregation
         *        (errors, uncertainty, boundaries, distributions, truth values etc) using the keys
         *        above.
         * 
         * @return the reduced value
         */
        Object reduce(Collection<IPair<Object, Double>> toReduce, IMetadata metadata);
    }
    //
    // /**
    // * Adopted by any object that tracks one or more dimensions in a scale, pointing to a precise
    // * 'granule' or to a slice for an extent along it. Used in {@link #getIndex(Locator...)} and
    // * {@link #locate(Locator...)}, as well as in dataflow execution through {@link
    // IContextualizer}.
    // *
    // * @deprecated use the scale itself or an extent
    // */
    // public interface Locator {
    //
    // /**
    // * Should be a constant but no way to ask for that in an interface. Number of dimension offsets
    // * to locate one extent.
    // *
    // * @return the number of dimension offsets required for locating a position.
    // */
    // public int getDimensionCount();
    //
    // /**
    // * If true, this is locating a full dimension or subset, with multiple extents.
    // *
    // * @return true if the locator is an aggregator
    // */
    // public boolean isAll();
    //
    // /**
    // * If the locator only covers the granule partially, return a value less than one, reflecting
    // * the amount of active coverage. This will only return anything other than 1 when computed by a
    // * IState.Mediator, which matches two scales and may find partial coverage when checking one
    // * index against another. It should normally return 1 and never return 0.
    // *
    // * @return the proportion of the topological subdivision we're locating
    // */
    // public double getWeight();
    //
    // /**
    // * Return the concept for the extent this is locating.
    // *
    // * @return the extent concept
    // */
    // public IConcept getExtent();
    //
    // }

    protected List<IExtent> extents = new ArrayList<>();
    protected long multiplicity = 0;
    protected int sIndex = -1;
    protected int tIndex = -1;
    protected ITime time = null;
    protected ISpace space = null;
    protected MultidimensionalCursor cursor;

    // this is copied to transitions so that we can quickly assess if two transitions
    // come from the same scale.
    protected InstanceIdentifier identifier = new InstanceIdentifier();

    /*
     * Next four are to support subscales built as views of another
     */
    // originalCursor != null means we derive from a previous scale and are representing
    // one slice of it...
    private MultidimensionalCursor originalCursor = null;
    // ... identified by this offset...
    private long sliceOffset = -1;
    // ... along this dimension
    private int sliceDimension = -1;
    // the ID of the originating scale. If size() == 1, we can locate directly in it using the offset
    // below.
    private long originalScaleId = -1;
    // the offset in the original scale (only applies if originalScaleId > 0);
    long originalScaleOffset = -1;

    protected Scale() {
    }

    protected Scale(Collection<IExtent> extents) {
        for (IExtent e : extents) {
            mergeExtent(e);
        }
        sort();
    }

    private Scale(IExtent[] topologies, MultidimensionalCursor cursor, int sliceExtentIndex, long sliceExtentOffset)
            throws KlabException {

        originalCursor = cursor;
        sliceDimension = sliceExtentIndex;
        sliceOffset = sliceExtentOffset;

        for (IExtent e : topologies) {
            mergeExtent(e);
        }
    }

    /**
     * 1-sized scale localized to the position passed in the parent scale, and needing no sort. Used
     * as the return value of a scale iterator going through all states.
     * 
     * @param scale
     * @param offset
     */
    public Scale(Scale scale, long offset) {

        this.originalScaleOffset = offset;
        this.originalScaleId = scale.scaleId;

        long[] pos = scale.cursor.getElementIndexes(offset);
        for (int i = 0; i < scale.extents.size(); i++) {
            this.extents.add(scale.extents.get(i) instanceof Extent ? ((Extent) scale.extents.get(i)).getExtent(pos[i])
                    : scale.extents.get(i));
        }
        this.multiplicity = 1;
    }

    /**
     * Scale localizing one dimension to the position passed. If this determines a 1-sized scale,
     * quickly set parent scale and offset in super so this can be used as a quick locator in it.
     * 
     * @param scale
     * @param dimension
     * @param offsets
     */
    public Scale(Scale scale, Dimension.Type dimension, long... offsets) {

        // if only the passed dimension has size > 1, just set the offset and leave
        boolean simple = true;
        long[] pos = scale.cursor.getExtents();
        long expos = 0;
        int i = 0;
        for (IExtent extent : scale.extents) {
            if (extent.getType() != dimension && extent.size() > 1) {
                simple = false;
            }
            pos[i++] = extent.getType() == dimension ? (expos = ((AbstractExtent) extent).getOffset(offsets))
                    : extent.size();
        }

        for (IExtent extent : scale.extents) {
            if (extent.getType() == dimension) {
                extents.add(((Extent) extent).getExtent(expos));
            } else {
                extents.add(extent);
            }
            if (extent.getType() == Dimension.Type.SPACE) {
                space = (ISpace) extents.get(extents.size() - 1);
            } else if (extent.getType() == Dimension.Type.TIME) {
                time = (ITime) extents.get(extents.size() - 1);
            }
        }

        if (simple) {
            this.originalScaleOffset = expos;
            this.originalScaleId = scale.scaleId;
        }
    }

    /**
     * Create a scale like the passed one, adding the passed extents or substituting existing ones of
     * the same type.
     * 
     * @param scale
     * @param extents
     * @return a new scale
     */
    public static Scale createLike(IScale scale, IExtent... extents) {
        List<IExtent> exts = Arrays.asList(extents);
        for (IExtent existing : scale.getExtents()) {
            boolean add = true;
            for (IExtent added : exts) {
                if (added.getType() == existing.getType()) {
                    add = false;
                    break;
                }
            }
            if (add) {
                exts.add(((Extent) existing).copy());
            }
        }
        return create(exts);
    }

    /**
     * Like {@link #createLike(IScale, IExtent...)} with a collection.
     * 
     * @param scale
     * @param extents
     * @return a new scale
     */
    public static Scale createLike(IScale scale, Collection<IExtent> extents) {
        return createLike(scale, extents.toArray(new Extent[extents.size()]));
    }

    /**
     * Create a scale from an array of extents.
     * 
     * TODO this should be able to create a ICoverage when extents are partially specified.
     * 
     * @param extents
     * @return a new scale
     */
    public static Scale create(IExtent... extents) {
        Scale ret = new Scale();
        if (extents != null) {
            for (IExtent e : extents) {
                ret.mergeExtent(e);
            }
        }
        ret.sort();
        return ret;
    }

    /**
     * Create a scale from a collection of extents.
     * 
     * @param extents
     * @return a new scale
     */
    public static Scale create(Collection<IExtent> extents) {
        return create(extents.toArray(new IExtent[extents.size()]));
    }

    public List<IServiceCall> getKimSpecification() {
        List<IServiceCall> ret = new ArrayList<>();
        for (IExtent extent : extents) {
            ret.add(((Extent) extent).getKimSpecification());
        }
        return ret;
    }

    // /**
    // * Get an index to loop over one dimension (set as -1) given fixed position for all others, only
    // * considering the sliceIndex-th part of the field from a total number of slices = sliceNumber.
    // * Used for parallelization of loops.
    // *
    // * @param sliceIndex
    // * @param sliceNumber
    // * @param locators
    // *
    // * @return an iterator as requested
    // */
    // public final ICursor getCursor(int sliceIndex, int sliceNumber, Locator... locators) {
    //
    // int variableDimension = -1;
    // long[] exts = new long[getExtentCount()];
    // Arrays.fill(exts, Extent.GENERIC_LOCATOR);
    // int i = 0;
    // for (IExtent e : extents) {
    // for (Locator o : locators) {
    // long n = ((Extent) e).locate(o);
    // if (n != Extent.INAPPROPRIATE_LOCATOR) {
    // exts[i] = n;
    // break;
    // }
    // }
    // i++;
    // }
    //
    // /*
    // *
    // */
    // int nm = 0;
    // for (i = 0; i < exts.length; i++) {
    // if (exts[i] == Extent.GENERIC_LOCATOR) {
    // nm++;
    // variableDimension = i;
    // }
    // }
    //
    // if (nm > 1) {
    // throw new KlabRuntimeException("cannot iterate a scale along more than one dimensions");
    // }
    //
    // return new Cursor(extents,
    // cursor.getDimensionScanner(variableDimension, exts, sliceIndex, sliceNumber), cursor,
    // variableDimension);
    // }

    // @Override
    // public final ICursor getCursor(Locator... locators) {
    //
    // int variableDimension = -1;
    // long[] exts = new long[getExtentCount()];
    // Arrays.fill(exts, Extent.GENERIC_LOCATOR);
    // int i = 0;
    // for (IExtent e : extents) {
    // for (Locator o : locators) {
    // long n = ((Extent) e).locate(o);
    // if (n != Extent.INAPPROPRIATE_LOCATOR) {
    // exts[i] = n;
    // break;
    // }
    // }
    // i++;
    // }
    //
    // /*
    // *
    // */
    // int nm = 0;
    // for (i = 0; i < exts.length; i++) {
    // if (exts[i] == Extent.GENERIC_LOCATOR) {
    // nm++;
    // variableDimension = i;
    // }
    // }
    //
    // if (nm == 0) {
    // return new Cursor(cursor.getElementOffset(exts));
    // }
    //
    // if (nm > 1) {
    // throw new KlabRuntimeException("cannot iterate a scale along more than one dimensions");
    // }
    //
    // return new Cursor(extents, cursor.getDimensionScanner(variableDimension, exts), cursor,
    // variableDimension);
    // }

    private class ScaleIterator implements Iterator<IScale> {

        long offset = 0;

        @Override
        public boolean hasNext() {
            return offset < size() - 1;
        }

        @Override
        public IScale next() {
            IScale ret = new Scale(Scale.this, offset);
            this.offset++;
            return ret;
        }
    }

    public long getExtentOffset(IExtent extent, long overallOffset) {
        int n = 0;
        boolean found = false;
        for (IExtent e : extents) {
            if (e.getType().equals(extent.getType())) {
                found = true;
                break;
            }
            n++;
        }
        if (!found) {
            throw new IllegalArgumentException("cannot locate extent " + extent.getType() + " in scale");
        }
        return Scale.this.cursor.getElementIndexes(overallOffset)[n];
    }

    @Override
    public boolean isTemporallyDistributed() {
        return getTime() != null && getTime().size() > 1;
    }

    @Override
    public boolean isSpatiallyDistributed() {
        return getSpace() != null && getSpace().size() > 1;
    }

    protected void sort() {

        ArrayList<IExtent> order = new ArrayList<>(extents);

        /*
         * Is it fair to think that if two extent concepts have an ordering relationship, they should
         * know about each other? So that we can implement the ordering as a relationship between extent
         * observation classes? For now, all we care about is that time, if present, comes first.
         */
        Collections.sort(order, new Comparator<IExtent>() {

            @Override
            public int compare(IExtent o1, IExtent o2) {
                // neg if o1 < o2
                boolean o1t = o1 instanceof ITime;
                boolean o2t = o2 instanceof ITime;
                if (o1t && !o2t) {
                    return -1;
                }
                if (!o1t && o2t) {
                    return 1;
                }
                return 0;
            }
        });

        multiplicity = 1L;
        int idx = 0;
        for (IExtent e : order) {

            if (e.size() == INFINITE) {
                multiplicity = INFINITE;
            }
            if (e.getType() == Dimension.Type.TIME) {
                tIndex = idx;
                time = (ITime) e;
            } else if (e.getType() == Dimension.Type.SPACE) {
                sIndex = idx;
                space = (ISpace) e;
            }

            if (multiplicity != INFINITE)
                multiplicity *= e.size();

            idx++;
        }

        // better safe than sorry. Only time can be infinite so this should be pretty safe
        // as long as the comparator above works.
        if (multiplicity == INFINITE && extents.get(0).size() != INFINITE) {
            throw new KlabInternalErrorException("internal error: infinite dimension is not the first in scale");
        }

        // recompute strided offsets for quick extent access
        cursor = new MultidimensionalCursor();
        long[] dims = new long[multiplicity == INFINITE ? extents.size() - 1 : extents.size()];
        int n = 0;
        for (int i = multiplicity == INFINITE ? 1 : 0; i < extents.size(); i++) {
            dims[n++] = extents.get(i).size();
        }
        cursor.defineDimensions(dims);
        extents = order;
    }

    // public long locate(Locator... locators) {
    //
    // long[] loc = new long[getExtentCount()];
    // int i = 0;
    // for (IExtent e : extents) {
    // for (Locator l : locators) {
    // long idx = ((Extent) e).locate(l);
    // if (idx >= 0) {
    // loc[i++] = idx;
    // break;
    // }
    // }
    // }
    // return Scale.this.cursor.getElementOffset(loc);
    // }

    @Override
    public int getExtentCount() {
        return extents.size();
    }

    @Override
    public ISpace getSpace() {
        return space;
    }

    @Override
    public ITime getTime() {
        return time;
    }

    public boolean isCovered(long offset) {
        long[] oofs = getExtentIndex(offset);
        for (int i = 0; i < getExtentCount(); i++) {
            if (!((Extent) extents.get(i)).isCovered(oofs[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isConsistent() {
        for (int i = 0; i < getExtentCount(); i++) {
            if (!((Extent) extents.get(i)).isConsistent()) {
                return false;
            }
        }
        return true;
    }

    // @Override
    // public IExtent getExtent(int index) {
    // return extents.get(index);
    // }

    @Override
    public long size() {
        return multiplicity;
    }

    @Override
    public boolean contains(IScale scale) throws KlabException {

        if (!hasSameExtents(scale)) {
            return false;
        }

        for (IExtent e : extents) {
            if (!e.contains(((Scale) scale).getDimension(e.getType()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean overlaps(IScale scale) throws KlabException {

        if (!hasSameExtents(scale)) {
            return false;
        }

        for (IExtent e : extents) {
            if (!e.overlaps(((Scale) scale).getDimension(e.getType()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean intersects(IScale scale) throws KlabException {

        if (!hasSameExtents(scale)) {
            return false;
        }

        for (IExtent e : extents) {
            if (!e.intersects(((Scale) scale).getDimension(e.getType()))) {
                return false;
            }
        }
        return true;
    }

    // public IScale merge(ITopologicallyComparable<?> scale_, LogicalConnector how) {
    //
    // if (!(scale_ instanceof Scale)) {
    // return null;
    // }
    // Scale scale = (Scale) scale_;
    //
    // if (!hasSameExtents(scale)) {
    // return null;
    // }
    //
    // Scale ret = new Scale();
    // for (IExtent e : extents) {
    // ret.mergeExtent((IExtent) e.merge(((Scale) scale).getDimension(e.getType()), how));
    // }
    //
    // return ret;
    // }

    /**
     * Add a missing extent or use the custom merge() function to inherit the usable info from the
     * passed one. Do not confuse this with the ones from ITopology.
     * 
     * @param extent
     */
    public void mergeExtent(IExtent extent) {

        IExtent merged = null;
        int i = 0;
        for (IExtent e : extents) {
            if (e.getType().equals(extent.getType())) {
                merged = e.merge(extent);
                break;
            }
            i++;
        }

        if (merged != null) {
            extents.add(i, merged);
            ((AbstractExtent) merged).setScaleId(scaleId);
        } else {
            extents.add(extent);
            ((AbstractExtent) extent).setScaleId(scaleId);
        }

        sort();
    }

    private void mergeExtent(IExtent extent, LogicalConnector how) {

        IExtent merged = null;
        int i = 0;
        for (IExtent e : extents) {
            if (e.getType().equals(extent.getType())) {
                merged = e.merge(extent, how);
                break;
            }
            i++;
        }

        if (merged != null) {
            extents.add(i, merged);
        } else {
            extents.add(extent);
        }

        sort();
    }

    /**
     * Return a collection of scales with multiplicity 1, one per each combination of the extent
     * states we represent.
     *
     * @return disaggregated scales
     * @throws KlabException
     */
    public Collection<IScale> disaggregate() throws KlabException {

        ArrayList<IScale> ret = new ArrayList<>();

        long[] dims = new long[extents.size()];
        for (int i = 0; i < dims.length; i++) {
            dims[i] = extents.get(i).size();
        }

        MultidimensionalCursor cursor = new MultidimensionalCursor();
        cursor.defineDimensions(dims);

        for (int i = 0; i < cursor.getMultiplicity(); i++) {
            IExtent[] exts = new IExtent[dims.length];
            long[] idx = cursor.getElementIndexes(i);
            for (int j = 0; j < exts.length; j++) {
                exts[j] = ((Extent) extents.get(j)).getExtent(idx[j]);
            }
            ret.add(create(exts));
        }

        return ret;

    }

    /*
     * quick access to "current" T state index for given offset - not in the API for now.
     */
    public long getTimeIndex(long globalIndex) {
        return tIndex == -1 ? -1 : cursor.getElementIndexes(globalIndex)[tIndex];
    }

    /*
     * quick access to "current" S state index for given offset - not in the API for now.
     */
    public long getSpaceIndex(long globalIndex) {
        return sIndex == -1 ? -1 : cursor.getElementIndexes(globalIndex)[sIndex];
    }

    /*
     * quick access to "current" arbitrary state index for given offset - not in the API for now.
     */
    public long[] getExtentIndex(long globalIndex) {
        return cursor.getElementIndexes(globalIndex);
    }

    /*
     * true if the passed scale has the same extents as we do.
     */
    boolean hasSameExtents(IScale scale) {

        for (IExtent e : scale.getExtents()) {
            if (getDimension(e.getType()) == null) {
                return false;
            }
        }

        for (IExtent e : extents) {
            if (scale.getDimension(e.getType()) == null) {
                return false;
            }
        }
        return true;
    }

    // /*
    // * get the extent with the passed domain concept
    // */
    // @Override
    // public IExtent getExtent(IConcept domainConcept) {
    // for (IExtent e : extents) {
    // if (e.getDomainConcept().equals(domainConcept)) {
    // return e;
    // }
    // }
    // return null;
    // }

    // /**
    // * Scan all extents and return the properties and values, if any, that describe
    // their coverage for
    // search
    // * and retrieval of compatible extents.
    // *
    // * It works by asking each extent for its storage metadata and returning any
    // metadata that is indexed by
    // a
    // * known property and points to a topologically comparable object.
    // *
    // * Relies on the fact that each extent has only one topologically comparable storage
    // metadata. Throws an
    // * unchecked exception if not so.
    // *
    // * @return
    // * @throws ThinklabException
    // */
    // public List<Pair<IProperty, ITopologicallyComparable<?>>>
    // getCoverageProperties(IMonitor monitor)
    // throws ThinklabException {
    // ArrayList<Pair<IProperty, ITopologicallyComparable<?>>> ret = new
    // ArrayList<Pair<IProperty,
    // ITopologicallyComparable<?>>>();
    // for (IExtent ext : _extents) {
    // int ncov = 0;
    // if (ext instanceof IStorageMetadataProvider) {
    // Metadata md = new Metadata();
    // ((IStorageMetadataProvider) ext).addStorageMetadata(md, monitor);
    // for (String pid : md.getKeys()) {
    // if (Thinklab.get().getProperty(pid) != null
    // && md.get(pid) instanceof ITopologicallyComparable<?>) {
    //
    // if (ncov > 0) {
    //
    // /*
    // * this is an obscure one for sure, but it should not really happen unless the
    // * implementation is screwed up and untested.
    // */
    // throw new ThinklabRuntimeException(
    // "internal: extent provides more than one topologically comparable storage
    // metadata");
    // }
    //
    // ret.add(new Pair<IProperty, ITopologicallyComparable<?>>(Thinklab.p(pid),
    // (ITopologicallyComparable<?>) md.get(pid)));
    // ncov++;
    // }
    // }
    // }
    // }
    // return ret;
    // }

    //
    // /**
    // * Return the scale without time, self if we don't see time.
    // *
    // * @return
    // */
    // @Override
    // public IScale getNonDynamicScale() {
    //
    // if (getTime() == null) {
    // return this;
    // }
    //
    // int i = 0;
    // IExtent[] exts = new IExtent[_extents.size() - 1];
    // for (IExtent e : _extents) {
    // if (e.getDomainConcept().equals(Time.TIME_DOMAIN)) {
    // continue;
    // }
    // exts[i++] = e;
    // }
    // try {
    // return new Scale(exts);
    // } catch (ThinklabException e1) {
    // // shouldn't happen if we get as far as this.
    // throw new ThinklabRuntimeException(e1);
    // }
    // }

    public List<IExtent> getExtents() {
        return extents;
    }

    /**
     * Return the proportion of coverage of the extent that is covered the least by the corresponding
     * extent in the passed scale.
     *
     * @param context
     * @return coverage
     */
    public double getCoverage(IScale context) {
        // TODO Auto-generated method stub
        return 1.0;
    }

    /**
     * Return the proportion of coverage that the passed scale would add to the coverage of our own
     * extents.
     *
     * @param mcov
     * @return additional coverage
     */
    public double getAdditionalCoverage(Scale mcov) {
        // TODO Auto-generated method stub
        return 1.0;
    }

    // @Override
    // public IScale union(IScale other)
    // throws KlabException {
    //
    // if (!(other instanceof Scale)) {
    // throw new KlabValidationException(other + " intersected with a Scale");
    // }
    //
    // return merge((Scale) other, LogicalConnector.UNION, true);
    // }
    //
    // @Override
    // public ITopologicallyComparable<IScale> intersection(ITopologicallyComparable<?> other)
    // throws KlabException {
    //
    // if (!(other instanceof Scale)) {
    // throw new KlabValidationException(other + " intersected with a Scale");
    // }
    //
    // return merge((Scale) other, LogicalConnector.INTERSECTION, true);
    // }

    @Override
    public Scale merge(ITopologicallyComparable<?> coverage, LogicalConnector how) {

        if (coverage instanceof Scale) {

            Scale other = (Scale) coverage;
            Scale ret = new Scale();
            ArrayList<IExtent> common = new ArrayList<>();
            HashSet<Dimension.Type> commonConcepts = new HashSet<>();

            for (IExtent e : extents) {
                if (other.getDimension(e.getType()) != null) {
                    common.add(e);
                    commonConcepts.add(e.getType());
                } else {
                    ret.mergeExtent(e, how);
                }
            }

            // if (adopt) {
            for (IExtent e : other.getExtents()) {
                if (ret.getDimension(e.getType()) == null && !commonConcepts.contains(e.getType())) {
                    ret.mergeExtent(e, how);
                }
            }
            // }

            for (IExtent e : common) {
                IExtent oext = other.getDimension(e.getType());
                IExtent merged = (IExtent) e.merge(oext, how);
                ret.mergeExtent(merged);
            }

            return ret;
        }

        // TODO enable merging of coverages with partial extents
        throw new IllegalArgumentException("Scale merge() called with a non-scale parameter");

    }

    @Override
    public String toString() {
        String ss = "";
        for (IExtent e : extents) {
            ss += "<" + e.getType() + " # " + e.size() + ">";
        }
        return "Scale #" + extents.size() + " " + ss;
    }

    @Override
    public boolean isEmpty() {

        for (IExtent e : extents) {
            if (((Extent) e).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Take in another scale and complete what's left of our specs by merging in its details. E.g.,
     * we're a bounding box, we get a grid resolution without extent, and we become a grid in that
     * bounding box. Will only be called during resolution, so the queries should have selected
     * compatible scales, but throw an exception if anything is not compatible.
     * 
     * @param scale
     * @return harmonized scale
     * @throws KlabException
     */
    public IScale harmonize(IScale scale) throws KlabException {
        // TODO Auto-generated method stub
        return scale;
    }

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
    public IScale getSubscale(Dimension.Type extent, long offset) {

        int oridx = -1;
        ArrayList<IExtent> exts = new ArrayList<>();
        for (int i = 0; i < extents.size(); i++) {
            if (extents.get(i).getType().equals(extent)) {
                oridx = i;
                continue;
            }
            exts.add(extents.get(i));
        }

        if (oridx < 0) {
            return this;
        }

        return new Scale(exts.toArray(new IExtent[exts.size()]), cursor, oridx, offset);
    }

    public long getOriginalOffset(long subscaleOffset) {

        if (originalCursor == null) {
            return subscaleOffset;
        }

        long[] slcofs = cursor.getElementIndexes(subscaleOffset);
        long[] orgofs = new long[originalCursor.getDimensionsCount()];
        int on = 0;
        for (int i = 0; i < orgofs.length; i++) {
            orgofs[i] = i == sliceDimension ? sliceOffset : slcofs[on++];
        }
        return originalCursor.getElementOffset(orgofs);
    }

    public static IScale substituteExtent(IScale scale, IExtent extent) throws KlabException {

        List<IExtent> exts = new ArrayList<>();
        for (IExtent e : scale.getExtents()) {
            if (e.getType().equals(extent.getType())) {
                exts.add(extent);
            } else {
                exts.add(e);
            }
        }
        return create(exts.toArray(new IExtent[exts.size()]));
    }

    /**
     * Return a new scale with the passed domains collapsed into a 1-multiplicity extent.
     * 
     * @param domains
     * @return a new scale
     * @throws KlabException
     */
    public IScale collapse(Dimension.Type... domains) throws KlabException {
        ArrayList<IExtent> extents = new ArrayList<>();
        for (IExtent e : this.extents) {
            boolean found = false;
            for (Dimension.Type d : domains) {
                if (e.getType().equals(d)) {
                    found = true;
                    break;
                }
            }
            extents.add(found ? e.collapse() : ((Extent) e).copy());
        }
        return create(extents);
    }

    @Override
    public Iterator<IScale> iterator() {
        return new ScaleIterator();
    }

    @Override
    public IGeometry getChild() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Dimension> getDimensions() {
        List<Dimension> ret = new ArrayList<>();
        ret.addAll(extents);
        return ret;
    }

    @Override
    public Granularity getGranularity() {
        return Granularity.SINGLE;
    }

    @Override
    public boolean isScalar() {
        return size() == 1;
    }

    @Override
    public IScale at(ILocator locator) {
        if (locator.equals(ITime.INITIALIZATION)) {
            if (getTime() == null) {
                // I want you just the way you are
                return this;
            } else {
                // just remove time
            }
        } else if (locator instanceof IExtent) {
            if (((AbstractExtent) locator).isOwnExtent(this)) {
                // guarantees no mediation needed
            } else {
                // if we don't have this extent, illegal arg (or just return this?)
                // mediation may be needed
            }
        } else if (locator instanceof IScale) {
            if (((Scale) locator).scaleId == scaleId) {
                return this;
            }
            // all-around mediation possible
        } else {
            throw new IllegalArgumentException("cannot use " + locator + " as a scale locator");
        }
        return null;
    }

    @Override
    public long getOffset(ILocator index) {

        if (index instanceof Scale && ((Scale) index).originalScaleId == this.scaleId) {
            return ((Scale) index).originalScaleOffset;
        }

        /*
         * TODO other mediatable scale
         */

        /*
         * TODO single extent locator
         */
        if (index instanceof IExtent) {
            IExtent mext = getOnlyMultipleExtent(((IExtent) index).getType());
            if (mext != null) {
                // offset is the extent's offset in its extent
                return mext.getOffset(index);
            }

            /*
             * TODO
             */
        }
        if (index instanceof OffsetLocator) {
            return ((OffsetLocator) index).getOffset();
        }

        throw new IllegalArgumentException("cannot use " + index + " as a scale locator");
    }

    /**
     * 
     * @param type
     * @return
     */
    private IExtent getOnlyMultipleExtent(Type type) {
        IExtent ret = null;
        for (IExtent extent : extents) {
            if (extent.getType() != type && extent.size() > 1) {
                return null;
            }
            if (extent.getType() == type) {
                ret = extent;
            }
        }
        return ret;
    }

    @Override
    public IScale at(Dimension.Type dimension, long... offsets) {
        return new Scale(Scale.this, dimension, offsets);
    }

    @Override
    public IExtent getDimension(Type type) {
        for (IExtent extent : extents) {
            if (extent.getType() == type) {
                return extent;
            }
        }
        return null;
    }

    @Override
    public Iterable<ILocator> over(Type dimension) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] shape(Type dimension) {
        for (IExtent extent : extents) {
            if (extent.getType() == dimension) {
                return extent.shape();
            }
        }
        throw new IllegalArgumentException("this scale does not contain the dimension " + dimension);
    }

    @Override
    public String encode() {
        String ret = "";
        for (IExtent extent : extents) {
            ret += ((AbstractExtent) extent).encode();
        }
        return ret;
    }

    /**
     * Return the scale as the underlying non-semantic Geometry
     * 
     * @return the fully specified geometry underlying this scale
     */
    public Geometry asGeometry() {
        if (this.geometry == null) {
            this.geometry = Geometry.create(encode());
        }
        return this.geometry;
    }
}

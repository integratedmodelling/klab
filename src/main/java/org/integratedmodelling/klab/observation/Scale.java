package org.integratedmodelling.klab.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.ICursor;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITransition;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.InstanceIdentifier;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.MultidimensionalCursor.StorageOrdering;

public class Scale implements IScale {

    protected List<IExtent>          extents        = new ArrayList<>();
    protected long                   multiplicity   = 0;
    protected int                    sIndex         = -1;
    protected int                    tIndex         = -1;
    protected Time                   time           = null;
    protected Space                  space          = null;
    protected MultidimensionalCursor cursor;

    // this is copied to transitions so that we can quickly assess if two transitions
    // come from the same scale.
    protected InstanceIdentifier     identifier     = new InstanceIdentifier();

    /*
     * Next three are to support subscales built as views of another
     */
    // originalCursor != null means we derive from a previous scale and are representing
    // one slice of it...
    private MultidimensionalCursor   originalCursor = null;
    // ... identified by this offset...
    private long                     sliceOffset    = -1;
    // ... along this dimension
    private int                      sliceDimension = -1;

    protected Scale() {
    }

    private Scale(IExtent[] topologies, MultidimensionalCursor cursor, int sliceExtentIndex,
            long sliceExtentOffset) throws KlabException {

        originalCursor = cursor;
        sliceDimension = sliceExtentIndex;
        sliceOffset = sliceExtentOffset;

        for (IExtent e : topologies) {
            mergeExtent(e, true);
        }
    }

    /**
     * Create a scale from an array of extents.
     * 
     * @param extents
     * @return
     */
    public static Scale create(IExtent... extents) {
        Scale ret = new Scale();
        if (extents != null) {
            for (IExtent e : extents) {
                ret.mergeExtent(e, true);
            }
        }
        ret.sort();
        return ret;
    }

    /**
     * Create a scale from a collection of extents.
     * 
     * @param extents
     * @return
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

    @Override
    public final ICursor getIndex(int sliceIndex, int sliceNumber, Locator... locators) {

        int variableDimension = -1;
        long[] exts = new long[getExtentCount()];
        Arrays.fill(exts, Extent.GENERIC_LOCATOR);
        int i = 0;
        for (IExtent e : extents) {
            for (Locator o : locators) {
                long n = e.locate(o);
                if (n != Extent.INAPPROPRIATE_LOCATOR) {
                    exts[i] = n;
                    break;
                }
            }
            i++;
        }

        /*
         * 
         */
        int nm = 0;
        for (i = 0; i < exts.length; i++) {
            if (exts[i] == Extent.GENERIC_LOCATOR) {
                nm++;
                variableDimension = i;
            }
        }

        if (nm > 1) {
            throw new KlabRuntimeException("cannot iterate a scale along more than one dimensions");
        }

        return new Cursor(extents, cursor
                .getDimensionScanner(variableDimension, exts, sliceIndex, sliceNumber), cursor, variableDimension);
    }

    @Override
    public final ICursor getIndex(Locator... locators) {

        int variableDimension = -1;
        long[] exts = new long[getExtentCount()];
        Arrays.fill(exts, Extent.GENERIC_LOCATOR);
        int i = 0;
        for (IExtent e : extents) {
            for (Locator o : locators) {
                long n = e.locate(o);
                if (n != Extent.INAPPROPRIATE_LOCATOR) {
                    exts[i] = n;
                    break;
                }
            }
            i++;
        }

        /*
         * 
         */
        int nm = 0;
        for (i = 0; i < exts.length; i++) {
            if (exts[i] == Extent.GENERIC_LOCATOR) {
                nm++;
                variableDimension = i;
            }
        }

        if (nm == 0) {
            return new Cursor(cursor.getElementOffset(exts));
        }

        if (nm > 1) {
            throw new KlabRuntimeException("cannot iterate a scale along more than one dimensions");
        }

        return new Cursor(extents, cursor
                .getDimensionScanner(variableDimension, exts), cursor, variableDimension);
    }

    @Override
    public long getExtentOffset(IExtent extent, long overallOffset) {
        int n = 0;
        boolean found = false;
        for (IExtent e : extents) {
            if (e.getDomainConcept().equals(extent.getDomainConcept())) {
                found = true;
                break;
            }
            n++;
        }
        if (!found) {
            throw new KlabRuntimeException("cannot locate extent " + extent.getDomainConcept() + " in scale");
        }
        return Scale.this.cursor.getElementIndexes(overallOffset)[n];
    }

    @Override
    public boolean isTemporallyDistributed() {
        return getTime() != null && getTime().getMultiplicity() > 1;
    }

    @Override
    public boolean isSpatiallyDistributed() {
        return getSpace() != null && getSpace().getMultiplicity() > 1;
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

            if (e.getMultiplicity() == INFINITE) {
                multiplicity = INFINITE;
            }
            if (e instanceof Time) {
                tIndex = idx;
                time = (Time) e;
            } else if (e instanceof Space) {
                sIndex = idx;
                space = (Space) e;
            }

            if (multiplicity != INFINITE)
                multiplicity *= e.getMultiplicity();

            idx++;
        }

        // better safe than sorry. Only time can be infinite so this should be pretty safe
        // as long as the comparator above works.
        if (multiplicity == INFINITE && extents.get(0).getMultiplicity() != INFINITE) {
            throw new KlabRuntimeException("internal error: infinite dimension is not the first in scale");
        }

        // recompute strided offsets for quick extent access
        cursor = new MultidimensionalCursor(StorageOrdering.ROW_FIRST);
        long[] dims = new long[multiplicity == INFINITE ? extents.size() - 1 : extents.size()];
        int n = 0;
        for (int i = multiplicity == INFINITE ? 1 : 0; i < extents.size(); i++) {
            dims[n++] = extents.get(i).getMultiplicity();
        }
        cursor.defineDimensions(dims);
        extents = order;
    }

    @Override
    public long locate(Locator... locators) {

        long[] loc = new long[getExtentCount()];
        int i = 0;
        for (IExtent e : extents) {
            for (Locator l : locators) {
                long idx = e.locate(l);
                if (idx >= 0) {
                    loc[i++] = idx;
                    break;
                }
            }
        }
        return Scale.this.cursor.getElementOffset(loc);
    }

    @Override
    public int getExtentCount() {
        return extents.size();
    }

    @Override
    public Space getSpace() {
        return space;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public boolean isCovered(long offset) {
        long[] oofs = getExtentIndex(offset);
        for (int i = 0; i < getExtentCount(); i++) {
            if (!extents.get(i).isCovered(oofs[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isConsistent() {
        for (int i = 0; i < getExtentCount(); i++) {
            if (!extents.get(i).isConsistent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public IExtent getExtent(int index) {
        return extents.get(index);
    }

    @Override
    public long getMultiplicity() {
        return multiplicity;
    }

    @Override
    public boolean contains(IScale scale) throws KlabException {

        if (!hasSameExtents(scale)) {
            return false;
        }

        for (IExtent e : extents) {
            if (!e.contains(((Scale) scale).getExtent(e.getDomainConcept()))) {
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
            if (!e.overlaps(((Scale) scale).getExtent(e.getDomainConcept()))) {
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
            if (!e.intersects(((Scale) scale).getExtent(e.getDomainConcept()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public IScale intersection(ITopologicallyComparable<?> scale_) throws KlabException {

        if (!(scale_ instanceof Scale)) {
            return null;
        }
        Scale scale = (Scale) scale_;

        if (!hasSameExtents(scale)) {
            return null;
        }

        Scale ret = new Scale();
        for (IExtent e : extents) {
            ret.mergeExtent((IExtent) e.intersection(((Scale) scale).getExtent(e.getDomainConcept())), false);
        }

        return ret;
    }

    @Override
    public IScale union(ITopologicallyComparable<?> scale_) throws KlabException {

        if (!(scale_ instanceof Scale)) {
            return null;
        }
        Scale scale = (Scale) scale_;

        if (!hasSameExtents(scale)) {
            return null;
        }

        Scale ret = new Scale();
        for (IExtent e : extents) {
            ret.mergeExtent((IExtent) e.union(((Scale) scale).getExtent(e.getDomainConcept())), false);
        }

        return ret;
    }

    /**
     * @param extent
     * @param force
     */
    public void mergeExtent(IExtent extent, boolean force) {

        IExtent merged = null;
        int i = 0;
        for (IExtent e : extents) {
            if (e.getDomainConcept().equals(extent.getDomainConcept())) {
                try {
                    merged = e.merge(extent, force);
                } catch (KlabException e1) {
                    throw new KlabRuntimeException(e1);
                }
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
            dims[i] = extents.get(i).getMultiplicity();
        }

        MultidimensionalCursor cursor = new MultidimensionalCursor();
        cursor.defineDimensions(dims);

        for (int i = 0; i < cursor.getMultiplicity(); i++) {
            IExtent[] exts = new IExtent[dims.length];
            long[] idx = cursor.getElementIndexes(i);
            for (int j = 0; j < exts.length; j++) {
                exts[j] = extents.get(j).getExtent(idx[j]);
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
    @Override
    public long[] getExtentIndex(long globalIndex) {
        return cursor.getElementIndexes(globalIndex);
    }

    /*
     * true if the passed scale has the same extents as we do.
     */
    boolean hasSameExtents(IScale scale) {

        for (IExtent e : scale.getExtents()) {
            if (getExtent(e.getDomainConcept()) == null) {
                return false;
            }
        }

        for (IExtent e : extents) {
            if (((Scale) scale).getExtent(e.getDomainConcept()) == null) {
                return false;
            }
        }
        return true;
    }

    /*
     * get the extent with the passed domain concept
     */
    @Override
    public IExtent getExtent(IConcept domainConcept) {
        for (IExtent e : extents) {
            if (e.getDomainConcept().equals(domainConcept)) {
                return e;
            }
        }
        return null;
    }

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
    public double getCoveredExtent() {
        /*
         * TODO multiply extents of extents.
         */
        return 1;
    }

    @Override
    public IScale merge(IScale scale, LogicalConnector how, boolean adopt) throws KlabException {

        Scale other = (Scale) scale;
        Scale ret = new Scale();
        ArrayList<IExtent> common = new ArrayList<>();
        HashSet<IConcept> commonConcepts = new HashSet<>();

        for (IExtent e : extents) {
            if (other.getExtent(e.getDomainConcept()) != null) {
                common.add(e);
                commonConcepts.add(e.getDomainConcept());
            } else {
                ret.mergeExtent(e, true);
            }
        }

        if (adopt) {
            for (IExtent e : other.getExtents()) {
                if (adopt && ret.getExtent(e.getDomainConcept()) == null
                        && !commonConcepts.contains(e.getDomainConcept())) {
                    ret.mergeExtent(e, true);
                }
            }
        }

        for (IExtent e : common) {
            IExtent oext = other.getExtent(e.getDomainConcept());
            IExtent merged = null;
            if (how.equals(LogicalConnector.INTERSECTION)) {
                merged = (IExtent) e.intersection(oext);
            } else if (how.equals(LogicalConnector.UNION)) {
                merged = (IExtent) e.union(oext);
            } else {
                throw new KlabValidationException("extents are being merged with illegal operator" + how);
            }
            ret.mergeExtent(merged, true);
        }

        return ret;
    }

    @Override
    public String toString() {
        String ss = "";
        for (IExtent e : extents) {
            ss += "<" + e.getDomainConcept() + " # " + e.getMultiplicity() + ">";
        }
        return "Scale #" + extents.size() + " " + ss;
    }

    @Override
    public boolean isEmpty() {

        for (IExtent e : extents) {
            if (e.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IScale harmonize(IScale scale) throws KlabException {
        // TODO Auto-generated method stub
        return scale;
    }

    @Override
    public IScale getSubscale(IConcept extent, long offset) {

        int oridx = -1;
        ArrayList<IExtent> exts = new ArrayList<>();
        for (int i = 0; i < extents.size(); i++) {
            if (extents.get(i).getDomainConcept().equals(extent)) {
                oridx = i;
                continue;
            }
            exts.add(extents.get(i));
        }

        if (oridx < 0) {
            return this;
        }

        try {
            return new Scale(exts.toArray(new IExtent[exts.size()]), cursor, oridx, offset);
        } catch (KlabException e1) {
            // should never happen since we build it with previously accepted extents.
            throw new KlabRuntimeException(e1);
        }
    }

    @Override
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
            if (e.getDomainConcept().equals(extent.getDomainConcept())) {
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
     * @param domain
     * @return
     * @throws KlabException
     */
    public IScale collapse(IConcept... domains) throws KlabException {
        ArrayList<IExtent> extents = new ArrayList<>();
        for (IExtent e : this.extents) {
            boolean found = false;
            for (IConcept d : domains) {
                if (e.getDomainConcept().equals(d)) {
                    found = true;
                    break;
                }
            }
            extents.add(found ? e.collapse() : ((Extent) e).copy());
        }

        return create(extents);
    }

    @Override
    public Iterator<ITransition> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}

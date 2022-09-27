package org.integratedmodelling.klab.components.localstorage.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.engine.debugger.Debugger.Watcher;
import org.integratedmodelling.klab.engine.debugger.Statistics;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IModificationListener;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

import groovy.lang.GroovyObjectSupport;

/**
 * Smart storage using a configurable backend to store slices that are only created when the values
 * are different from others. This guarantees optimization of storage and scales from scalars to
 * huge datasets as long as the backend can provide slices.
 * 
 * TODO check if this all works if assuming millisecond offset to handle pseudo-continuous change
 * recording in states whose temporal resolution is unspecified, but are affected by temporally
 * heterogeneous processes. It probably should, although the start time should be recorded and all
 * offsets should be made relative to it.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public abstract class AbstractAdaptiveStorage<T> implements IDataStorage<T> {

    private NavigableMap<Long, Slice> slicesByEnd = new TreeMap<>();
    private NavigableMap<Long, Slice> slicesByStart = new TreeMap<>();
    private long highTimeOffset = -1;
    private long maxTimeOffset;
    private long sliceSize;
    private long slicesInBackend = 0;
    protected List<Consumer<ILocator>> listeners = Collections.synchronizedList(new ArrayList<>());
    private IState state;

    // FIXME this is only for debugging, remove when done.
    private IGeometry geometry;

    // if one value only, just store it here and forget about everything.
    private boolean isScalar = false;
    private Object scalarValue = null;

    // these get set/unset before writing by the inspectors and debuggers. Must be honored at
    // each put() if there.
    transient List<Statistics> debuggingStatistics = null;

    /*
     * If trivial, we have no time or just one time state, and can contain at most one slice.
     */
    private boolean trivial;
    private Map<String, Watcher> watches;

    /**
     * A slice is generated when there are values in a timestep. Within a slice, storage is
     * generated on the backend values are different from the previous slice stored or when the
     * values for the current timestep are no longer unique.
     */
    public class Slice extends GroovyObjectSupport {

        // if not using backend, < 0; otherwise the slice offset in it, 0 or <= time
        long sliceOffsetInBackend = -1l;

        // the timestep this slice refers to (shouldn't be used)
        private long timestep;
        long timestart;
        long timeend;
        int index;
        SummaryStatistics statistics = new SummaryStatistics();
        long nodata = 0;

        @Override
        public String toString() {
            return (timestart == 0 || timeend == 0)
                    ? "INIT [0/" + slicesByStart.size() + "] "
                    : ("TIME [" + index + "/" + slicesByStart.size() + "] " + new Date(timestart) + " to " + new Date(timeend));
        }

        public T getAt(long sliceOffset) {
            return getValueFromBackend(sliceOffset, this.sliceOffsetInBackend);
        }

        public StateSummary getStateSummary() {
            StateSummary ret = new StateSummary();
            ret.setMean(this.statistics.getMean());
            ret.setNodataPercentage(((double) this.nodata) / ((double) sliceSize));
            ret.setDegenerate(isEmpty());
            ret.setSingleValued(scalarValue != null);
            ret.setStandardDeviation(this.statistics.getStandardDeviation());
            ret.setVariance(this.statistics.getVariance());
            ret.setRange(Arrays.asList(this.statistics.getMin(), this.statistics.getMax()));
            ret.setSum(this.statistics.getSum());
            return ret;
        }

        public SummaryStatistics getRawStatistics() {
            return this.statistics;
        }

        // TODO synchronization here voids the parallelism in most functions. The
        // newSlice thing should be
        // put in the implementation and synchronized there, so that multiple put() may
        // happen.
        public void setAt(long sliceOffset, T value) {

            if (isEmpty()) {
                newSlice();
            }
            if (value instanceof Number) {
                if (Double.isNaN(((Number) value).doubleValue())) {
                    nodata++;
                } else {
                    statistics.addValue(((Number) value).doubleValue());
                }
            }

            setValueIntoBackend(value, sliceOffset, this.sliceOffsetInBackend);
        }

        public boolean isEmpty() {
            return sliceOffsetInBackend < 0;
        }

        private void newSlice() {
            this.sliceOffsetInBackend = slicesInBackend;
            slicesInBackend++;
            createBackendStorage(this.sliceOffsetInBackend, null);
        }

        Slice(long timestep, long timeStart, long timeEnd, Slice closest) {
            this.timestep = timestep;
            this.timestart = timeStart;
            this.timeend = timeEnd;
            this.index = slicesByEnd.size();
            if (closest != null) {
                if (closest.sliceOffsetInBackend >= 0) {
                    this.sliceOffsetInBackend = closest.sliceOffsetInBackend + 1;
                    duplicateBackendSlice(closest.sliceOffsetInBackend, this.sliceOffsetInBackend);
                }
            }
        }

        public boolean isInitialization() {
            return timestart == 0 && timeend == 0;
        }

        @Override
        public Object getProperty(String propertyName) {

            if ("mean".equals(propertyName)) {
                return this.statistics.getMean();
            } else if ("min".equals(propertyName)) {
                return this.statistics.getMin();
            } else if ("max".equals(propertyName)) {
                return this.statistics.getMax();
            } else if ("variance".equals(propertyName)) {
                return this.statistics.getVariance();
            } else if ("std".equals(propertyName)) {
                return this.statistics.getStandardDeviation();
            } else if ("count".equals(propertyName)) {
                return this.statistics.getN();
            } else if ("start".equals(propertyName)) {
                return this.timestart;
            } else if ("end".equals(propertyName)) {
                return this.timeend;
            } else if ("index".equals(propertyName)) {
                return this.index;
            } else if ("init".equals(propertyName)) {
                return this.isInitialization();
            }

            return super.getProperty(propertyName);
        }

        public void computeStatistics(Statistics ret) {
            for (long i = 0; i < sliceSize; i++) {
                Object o = getValueFromBackend(i, this.sliceOffsetInBackend);
                if (o == null) {
                    o = Double.NaN;
                }
                if (o instanceof Number) {
                    ret.addValue(((Number) o).doubleValue());
                }
            }
        }
    }

    /**
     * The time offset reached so far. We don't need to know the max time possible and it may be
     * infinite. We may not have slices that reach to this level, which means a value was assigned
     * but it was equal to the value in the last recorded slice.
     * 
     * @return
     */
    protected long getHighTimeOffset() {
        return highTimeOffset;
    }

    @Override
    public void touch(ITime time) {
        // TODO Auto-generated method stub

    }

    protected abstract void duplicateBackendSlice(long sliceToCopy, long newSliceIndex);

    /**
     * The maximum possible time offset or IGeometry.INFINITE. Only meaningful if not trivial. Only
     * for debugging and for the implementing classes to check so they can optimize storage when
     * it's finite.
     * 
     * @return
     */
    protected long getMaxTimeOffset() {
        return maxTimeOffset;
    }

    /**
     * Size of a non-temporal slice. All we need is linear storage of this size per each slice.
     * 
     * @return
     */
    protected long getSliceSize() {
        return sliceSize;
    }

    /**
     * Get the n-th slice.
     * 
     * @param index
     * @return
     */
    Slice getSlice(int index) {
        Iterator<Slice> it = slicesByEnd.values().iterator();
        while(index-- > 0) {
            it.next();
        }
        return it.next();
    }

    protected AbstractAdaptiveStorage(IGeometry geometry) {

        this.isScalar = geometry.size() == 1;
        this.geometry = geometry;
        Dimension time = geometry.getDimension(Type.TIME);
        if (time == null) {
            this.trivial = true;
            this.sliceSize = geometry.size();
        } else if (time.size() == IGeometry.INFINITE_SIZE) {
            this.maxTimeOffset = time.size();
            this.sliceSize = geometry.size();
        } else {
            this.maxTimeOffset = time.size();
            this.sliceSize = geometry.size() / time.size();
        }

    }

    /**
     * Create storage backend for the passed timestep. This would normally use a single backend for
     * all timesteps, created only at the first call, but we call it anyway at each new timestep so
     * that implementations are free to decide.
     * 
     * This can also be used to initialize the values from a previous timestep into the new one.
     * 
     * @param sliceOffsetInBackend the offset of the new slice. Calls will be ordered if the values
     *        are set in temporal order.
     * @param initialValue value with which to fill the slice. Null for nodata.
     */
    protected abstract void createBackendStorage(long sliceOffsetInBackend, T initialValue);

    /**
     * Get a value from the backend. The sliceOffsetInBackend is whatever we need to use for the
     * specific timestep; it's <= the real timestep.
     * 
     * @param offsetInSlice the offset within the slice specified by the second parameter
     * @param sliceOffsetInBackend the offset of the slice in the backend
     * @return
     */
    protected abstract T getValueFromBackend(long offsetInSlice, long sliceOffsetInBackend);

    /**
     * Write a value to the backend. The backendTimeSlice is whatever we need to use for the
     * specific timestep and it's <= the real timestep; if the writes are monotonic in time, this
     * will be called with values increasing by 0 or 1.
     * 
     * @param value
     * @param offsetInSlice
     * @param timestep
     */
    protected abstract void setValueIntoBackend(T value, long offsetInSlice, long backendTimeSlice);

    public void setState(IState state) {
        this.state = state;
    }

    @SuppressWarnings("unchecked")
    public synchronized T get(ILocator locator) {

        if (isScalar) {
            return (T) scalarValue;
        }

        if (slicesByEnd.isEmpty()) {
            return null;
        }

        Offset offsets = locator.as(Offset.class);

        if (offsets.length > geometry.getDimensions().size()) {
            // pick the dimension we're in
            offsets = offsets.reduceTo(geometry);
        } else if (offsets.length < geometry.getDimensions().size()) {
            throw new KlabInternalErrorException("locator has different dimensionality than observation: should never happen");
        }

        long timeOffset = trivial ? 0 : offsets.pos[0];
        long sliceOffset = product(offsets.pos, trivial ? 0 : 1);

        /*
         * To index the slice, use time end directly unless we're at initialization. Record start,
         * end and offset in state's scale in the slice.
         */
        long timeStart = 0;
        long timeEnd = 0;
        ITime time = null;
        boolean initialization = false;
        if (locator instanceof IScale) {
            time = ((IScale) locator).getTime();
            initialization = time == null || (time.getFocus() == null && time.getTimeType() == ITime.Type.INITIALIZATION);
        } else if (this.geometry instanceof IScale) {
            // redefine timeEnd based on the offset
            if (((IScale) this.geometry).getTime() != null) {
                ITime ext = (ITime) ((Extent) (((IScale) this.geometry).getTime())).getExtent(timeOffset);
                timeEnd = ext.getEnd().getMilliseconds();
            }
        } else {
            throw new KlabUnimplementedException("unexpected locator in mapped storage!");
        }

        Slice slice = getSlice(locator);

        /*
         * check for non-conformant time extent (!= to the extent of the slice): this means that the
         * requesting scale isn't the same as the native one, or that there have been in-between
         * timestep changes in the state due to processes or events operating at different scales.
         */
        // if (!initialization && sliceOffset >= 0 && slice != null &&
        // !slice.isInitialization()
        // && (slice.timestart != timeStart || slice.timeend != timeEnd)) {
        // /*
        // * TODO if needed, aggregate within the boundary of the requesting scale,
        // otherwise keep
        // * the latest value
        // */
        // NavigableMap<Long, Slice> aggregatable = slices.subMap(timeStart, false,
        // timeEnd, true);
        // if (aggregatable.isEmpty()) {
        // Slice theSlice = slices.get(timeStart);
        // // use state before start if existing, otherwise result is NaN
        // return theSlice == null ? (slice == null ? null : slice.getAt(sliceOffset)) :
        // theSlice.getAt(sliceOffset);
        // }
        // return aggregate(aggregatable, sliceOffset);
        // }

        return slice == null ? null : slice.getAt(sliceOffset);
    }

    @SuppressWarnings("unchecked")
    private T aggregate(NavigableMap<Long, Slice> map, long sliceOffset) {

        List<Pair<Object, Long>> values = new ArrayList<>();
        for (Slice slice : map.values()) {
            values.add(new Pair<>(slice.getAt(sliceOffset), slice.timeend));
        }

        if (values.size() == 1) {
            return (T) values.get(0).getFirst();
        } else if (values.size() == 0) {
            return null;
        }

        // System.out.println("AGGREGATE " + map.size() + " VALUES");

        return null;
    }

    private long getSliceOffset(ILocator locator) {
        if (isScalar) {
            return 0;
        }
        Offset offsets = locator.as(Offset.class);
        return product(offsets.pos, trivial ? 0 : 1);
    }

    public long put(T value, ILocator locator) {

        if (debuggingStatistics != null) {
            double d = Double.NaN;
            if (value != null && value instanceof Number) {
                d = ((Number) value).doubleValue();
            }
            for (Statistics stat : debuggingStatistics) {
                stat.addValue(d);
            }
        }

        if (isScalar) {
            scalarValue = value;
            return 0;
        }

        Offset offsets = locator.as(Offset.class);

        if (offsets.length > geometry.getDimensions().size()) {
            // pick the dimension we're in
            offsets = offsets.reduceTo(geometry);
        } else if (offsets.length < geometry.getDimensions().size()) {
            throw new KlabInternalErrorException("locator has different dimensionality than observation: should never happen");
        }

        /*
         * To index the slice, use time end directly unless we're at initialization. Record start,
         * end and offset in state's scale in the slice.
         */
        long timeStart = 0;
        long timeEnd = 0;
        ITime time = null;
        if (locator instanceof IScale) {
            time = ((IScale) locator).getTime();
        }

        if (time != null && time.getTimeType() != ITime.Type.INITIALIZATION && time.getStart() != null && time.getEnd() != null) {
            timeStart = time.getStart().getMilliseconds();
            timeEnd = time.getEnd().getMilliseconds();
        }

        long sliceOffset = product(offsets.pos, trivial ? 0 : 1);
        long timeOffset = trivial ? 0 : offsets.pos[0];
        boolean noData = Observations.INSTANCE.isNodata(value);

        synchronized (this) {

            if (noData && slicesByEnd.isEmpty()) {
                // everything's nodata so far, no need to store.
                return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
            }

            /*
             * record high offset for posterity
             */
            if (highTimeOffset < timeOffset) {
                highTimeOffset = timeOffset;
            }

            /*
             * find the closest slice for the time
             */
            Slice slice = getClosest(/* timeOffset */timeEnd, false);
            if (slice != null/* && slice.timestep != timeOffset */ && !slice.isEmpty()
                    && equals(slice.getAt(sliceOffset), value)) {
                // don't store anything until it's different from the previous slice.
                return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
            }

            /*
             * if we get here, we need to store in a slice of our own unless we found the exact
             * timestep.
             */
            if (slice == null || slice.timestep != timeOffset) {
                slice = addSlice(timeOffset, timeStart, timeEnd, slice);
                if (time != null && state != null) {
                    ((State) state).getScale().mergeTransition(time);
                }
                for (Consumer<ILocator> listener : this.listeners) {
                    listener.accept(locator);
                }
                if (this.state instanceof Observation && time != null) {
                    for (IModificationListener listener : ((Observation) state).getModificationListeners()) {
                        listener.onTemporalExtension(time);
                    }
                }
                if (this.watches != null) {
                    for (Watcher watch : watches.values()) {
                        // watch.newStateSlice(state, slice.timestart);
                    }
                }
            }

            slice.setAt(sliceOffset, value);
        }

        return trivial ? sliceOffset : (sliceOffset * (timeOffset + 1));
    }

    private long product(long[] offsets, int i) {
        long ret = offsets[i];
        for (int n = i + 1; n < offsets.length; n++) {
            ret *= offsets[n];
        }
        return ret;
    }

    private Slice addSlice(long timeOffset, long timeStart, long timeEnd, Slice closest) {

        // System.out.println(new TimeInstant(timeStart) + " to " + new TimeInstant(timeEnd));

        Slice slice = new Slice(timeOffset, timeStart, timeEnd, closest);
        slicesByEnd.put(timeEnd, slice);
        slicesByStart.put(timeStart, slice);
        if (state != null) {
            // may be null when this is called with a visiting data builder upstream, which only
            // milks resources
            state.getScope().notifyInspector(IInspector.Asset.STATE_SLICE, IInspector.Event.CREATION, slice, state);
        }
        return slice;
    }

    private boolean equals(Object valueAt, T value) {
        return (valueAt == null && value == null) || (valueAt != null && value != null && valueAt.equals(value));
    }

    private Slice getClosest(long timeSlice, boolean fromUpwards) {
        Map.Entry<Long, Slice> low = fromUpwards ? slicesByStart.floorEntry(timeSlice) : slicesByEnd.floorEntry(timeSlice);
        return low == null ? null : low.getValue();
    }

    protected int sliceCount() {
        return slicesByEnd.size();
    }

    @Override
    public Object getObject(ILocator locator) {
        return get(locator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public long putObject(Object value, ILocator locator) {
        return put((T) value, locator);
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public void addContextualizationListener(Consumer<ILocator> listener) {
        this.listeners.add(listener);
    }

    public String getInfo(int indent) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy-hh:mm");
        String spacer = StringUtil.spaces(indent);
        String ret = "";
        for (Long key : slicesByEnd.keySet()) {
            Slice slice = slicesByEnd.get(key);
            ret += spacer
                    + ((slice.timestart == 0 || slice.timeend == 0)
                            ? "Initialization"
                            : (slice.timestart + ": " + f.format(new Date(slice.timestart)) + " "
                                    + f.format(new Date(slice.timeend))))
                    + ": NODATA= + " + slice.nodata + '/' + geometry.getDimension(Type.SPACE).size() + " ["
                    + slice.statistics.getMin() + "-" + slice.statistics.getMax() + "]\n";
        }
        return ret;
    }

    public void setWatches(Map<String, Watcher> watches) {
        this.watches = watches;
    }

    public Object[] getTimeseries(ILocator locator) {
        List<Object> ret = new ArrayList<>();
        long sliceOffset = getSliceOffset(locator);
        for (Slice slice : slicesByEnd.values()) {
            ret.add(slice.getAt(sliceOffset));
        }
        return ret.toArray();
    }

    public List<ILocator> getTimesliceLocators() {
        List<ILocator> ret = new ArrayList<>();
        for (int i = 0; i < slicesByEnd.size(); i++) {
            Slice slice = getSlice(i);
            TimesliceLocator locator = new TimesliceLocator(this, slice, i);
            ret.add(locator);
        }
        return ret;
    }

    public Slice getSlice(ILocator locator) {

        if (locator instanceof TimesliceLocator) {
            return getSlice(((TimesliceLocator) locator).sliceIndex);
        } else if (locator instanceof Time && ((Time) locator).getLocatedTimeslice() >= 0) {
            return getSlice(((Time) locator).getLocatedTimeslice());
        } else if (locator instanceof Scale && ((Scale) locator).getTime() instanceof Time
                && ((Time) ((Scale) locator).getTime()).getLocatedTimeslice() >= 0) {
            // return getSlice(((Time) ((Scale) locator).getTime()).getLocatedTimeslice() + 1);
            return getClosest(((Scale) locator).getTime().getFocus().getMilliseconds(), true);
        } else if (locator instanceof ITime) {
            return ((Time) locator).getTimeType() == ITime.Type.INITIALIZATION
                    ? getSlice(0)
                    : getClosest(((Time) locator).getStart().getMilliseconds() + 1, true);
        }

        Offset offsets = locator.as(Offset.class);
        long timeOffset = trivial ? 0 : offsets.pos[0];

        long timeEnd = 0;
        ITime time = null;
        if (locator instanceof IScale) {
            time = ((IScale) locator).getTime();
        } else if (this.geometry instanceof IScale) {
            // redefine timeEnd based on the offset
            if (((IScale) this.geometry).getTime() != null) {
                ITime ext = (ITime) ((Extent) (((IScale) this.geometry).getTime())).getExtent(timeOffset);
                timeEnd = ext.getEnd().getMilliseconds();
            }
        } else {
            throw new KlabUnimplementedException("unexpected locator in mapped storage!");
        }

        boolean initialization = timeOffset <= 0;
        long timepoint = -1;
        if (time != null && time.getFocus() != null) {
            timepoint = time.getFocus().getMilliseconds();
        } else if (time != null && time.getStart() != null && time.getEnd() != null) {
            // timeStart = time.getStart().getMilliseconds();
            timeEnd = time.getEnd().getMilliseconds();
            initialization = time.getTimeType() == ITime.Type.INITIALIZATION;
        }

        Slice slice = null;
        if (timepoint >= 0) {
            /*
             * find the slice that describes the timepoint. By convention slice coverage end a unit
             * before the stated time end, but if we ask for the end point we get the slice that
             * touches it from the left, so we use <= for the end timepoint instead of <. Basically
             * if we ask for the state at Feb 1st we are asking for the state before Feb 1st
             * happens.
             */
            Slice lastSlice = null;
            for (Slice s : slicesByEnd.values()) {
                if (timepoint >= s.timestart && timepoint < s.timeend) {
                    slice = s;
                    break;
                }
                lastSlice = s;
            }

            if (slice == null) {
                // no changes relevant to that timepoint, get the latest or stay null
                slice = lastSlice;
            }

        } else {
            // can only be the closest at this point, unless there was no slice at all
            slice = getClosest(initialization ? 0 : timeEnd, false);
        }
        return slice;
    }

    @Override
    public long getTemporalOffset(ILocator locator) {
        Slice slice = getSlice(locator);
        return slice == null ? -1 : slice.index;
    }

    public IState getState() {
        return state;
    }

    public StateSummary getOverallSummary() {
        StateSummary ret = null;
        for (Slice slice : slicesByStart.values()) {
            if (ret == null) {
                ret = slice.getStateSummary();
            } else {
                ret.merge(slice.getStateSummary());
            }
        }
        return ret;
    }

    public List<Slice> getSlices() {
        return new ArrayList<>(slicesByStart.values());
    }

    public void setDebuggingStatistics(List<Statistics> stats) {
        this.debuggingStatistics = stats;
    }

    public Statistics computeStatistics(Object locator) {

        Statistics ret = new Statistics();
        if (locator instanceof String) {

            ITime time = state.getScale().getTime();
            if (time == null) {
                throw new KlabInternalErrorException("can't extract temporal slices from non-temporal states");
            }

            switch((String) locator) {
            case "start":
                time = time.earliest();
                break;
            case "end":
                time = time.latest();
                break;
            case "init":
                time = state.getScale().initialization().getTime();
                break;
            }

            Slice slice = getSlice(time);
            if (slice != null) {
                slice.computeStatistics(ret);
            }
        }

        return ret;
    }

}

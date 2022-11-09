package org.integratedmodelling.klab.components.runtime.observations;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.IReducible;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage.Slice;
import org.integratedmodelling.klab.components.localstorage.impl.KeyedStorage;
import org.integratedmodelling.klab.data.storage.DataIterator;
import org.integratedmodelling.klab.data.storage.LocatedState;
import org.integratedmodelling.klab.data.storage.MediatingState;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.debugger.Statistics;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.MapUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.Utils;

import groovy.lang.GroovyObjectSupport;
import groovy.transform.CompileStatic;

/**
 * A state is simply an Observation wrapper for one (or more) {@link IDataArtifact}s.
 * 
 * @author Ferd
 *
 */
public class State extends Observation implements IState, IKeyHolder {

    public static final String STATE_SUMMARY_METADATA_KEY = "metadata.keys.state_summary_";

    ValuePresentation valuePresentation = ValuePresentation.VALUE;
    Set<Pair<Long, Long>> timeCoverage;
    boolean numeric = false;

    Map<Integer, RelocatedState> stateProxies = Collections.synchronizedMap(new HashMap<>());

    /**
     * This is output by getProxy() and getStateProxy() which return proxies for a get() where the
     * locator can be modified through a << operator in Groovy, returning the state "at" the
     * modified locator. Used to handle the preprocessed @ operator in expressions and to pass
     * Groovy messages to entire state/state slices located at a particular time. In the latter
     * case, the proxies are indexed in the state using the scale's system ID to make it as fast as
     * possible to retrieve.
     * 
     * @author Ferd
     *
     */
    @CompileStatic
    public class RelocatedState extends GroovyObjectSupport {

        IScale locator;

        // TODO cache the stats more (in the top class)

        public RelocatedState(IScale locator) {
            this.locator = locator;
        }

        /*
         * this will be called by the
         */
        public Object leftShift(Object locatorModifier) {
            return get(Extensions.INSTANCE.relocate(locator, locatorModifier));
        }

        public double getMax() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getMax();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public double getMin() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getMin();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public double getMean() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getMean();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        // for backwards compatibility
        public double getAvg() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getMean();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public double getSum() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getSum();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public double getStd() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getStandardDeviation();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public double getVariance() {
            if (storage instanceof AbstractAdaptiveStorage) {
                return ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getRawStatistics().getVariance();
            }
            throw new KlabUnimplementedException("Groovy support for non-conventional states");
        }

        public RelocatedState invert() {
            if (getObservable().getArtifactType() == IArtifact.Type.NUMBER && storage instanceof AbstractAdaptiveStorage) {
                AbstractAdaptiveStorage<?>.Slice slice = ((AbstractAdaptiveStorage<?>) storage).getSlice(locator);
                if (slice != null) {
                    StateSummary summary = slice.getStateSummary();
                    if (!summary.isDegenerate()) {
                        for (ILocator loc : locator) {
                            Double d = get(loc, Double.class);
                            if (d != null && !Double.isNaN(d)) {
                                d = summary.getRange().get(1) - d + summary.getRange().get(0);
                                set(loc, d);
                            }
                        }
                    }
                }
            }
            return this;
        }

        public RelocatedState normalize() {
            if (getObservable().getArtifactType() == IArtifact.Type.NUMBER && storage instanceof AbstractAdaptiveStorage) {
                StateSummary summary = ((AbstractAdaptiveStorage<?>) storage).getSlice(locator).getStateSummary();
                if (!summary.isDegenerate()) {
                    for (ILocator loc : locator) {
                        Double d = get(loc, Double.class);
                        if (d != null && !Double.isNaN(d)) {
                            d = (d - summary.getRange().get(0)) / (summary.getRange().get(1) - summary.getRange().get(0));
                            set(loc, d);
                        }
                    }
                }
            }
            return this;
        }

    }

    public class StateListener implements Consumer<ILocator> {

        @Override
        public void accept(ILocator t) {
            ITime time = t instanceof ITime ? (ITime) t : null;
            if (t instanceof IScale) {
                time = ((IScale) t).getTime();
            }
            if (time != null && time.getStart() != null && time.getEnd() != null) {
                long tstart = time.getStart().getMilliseconds();
                long tend = time.getEnd().getMilliseconds();
                if (time.getTimeType() == ITime.Type.INITIALIZATION) {
                    tend = tstart;
                    tstart = 0;
                }
                timeCoverage.add(new Pair<>(tstart, tend));
            }
        }

    }

    IDataStorage<?> storage;
    IDataKey dataKey;
    Map<IArtifact.Type, IStorage<?>> layers = new HashMap<>();
    IStructuredTable<Number> table;

    /**
     * Statistics inserted from outside (by the inspector/debugger). If these are not null, we are
     * obliged to report our numeric put() to each object in them, even if they happen in a delegate
     * storage.
     */
    transient private List<Statistics> debuggingStatistics;

    public static State newArchetype(Observable observable, Scale scale, IRuntimeScope context) {
        return new State(observable, scale, context);
    }

    public RelocatedState getProxy(IScale locator) {
        return new RelocatedState(locator);
    }

    /**
     * For Groovy
     * 
     * @param scale
     * @return
     */
    public RelocatedState getStateProxy(IScale scale) {
        int id = System.identityHashCode(scale);
        RelocatedState ret = this.stateProxies.get(id);
        if (ret == null) {
            ret = new RelocatedState(scale);
            this.stateProxies.put(id, ret);
        }
        return ret;
    }

    @Override
    public ValuePresentation getValuePresentation() {
        return this.valuePresentation;
    }

    public void setValuePresentation(ValuePresentation vp) {
        this.valuePresentation = vp;
    }

    private State(Observable observable, Scale scale, IRuntimeScope context) {
        super(observable, scale, context);
        this.setArchetype(true);
    }

    public State(Observable observable, Scale scale, IRuntimeScope context, IDataStorage<?> data) {
        super(observable, scale, context);
        this.storage = data;
        this.numeric = observable.getArtifactType() == IArtifact.Type.NUMBER;
        if (data != null) {
            // can be null in some special-purpose mergers
            data.addContextualizationListener(new StateListener());
            this.layers.put(data.getType(), data);
        }
        this.timeCoverage = new LinkedHashSet<>();
        if (data instanceof AbstractAdaptiveStorage) {
            ((AbstractAdaptiveStorage<?>) data).setState(this);
            ((AbstractAdaptiveStorage<?>) data).setWatches(this.watches);
        } else if (data instanceof KeyedStorage) {
            ((KeyedStorage<?>) data).getBackend().setState(this);
            ((KeyedStorage<?>) data).getBackend().setWatches(this.watches);
        }
    }

    @Override
    public IState as(IArtifact.Type type) {

        if (isArchetype() || type == storage.getType() || type == IArtifact.Type.VALUE) {
            return this;
        }

        IStorage<?> layer = layers.get(type);
        if (layer == null) {
            layer = Klab.INSTANCE.getStorageProvider().createStorage(type, getScale());
            ((IDataStorage<?>) layer).addContextualizationListener(new StateListener());
            if (layer instanceof AbstractAdaptiveStorage) {
                ((AbstractAdaptiveStorage<?>) layer).setWatches(this.watches);
            }

            layers.put(type, layer);
        }

        IState ret = new StateLayer(this, (IDataStorage<?>) layer);
        if (layer instanceof AbstractAdaptiveStorage) {
            ((AbstractAdaptiveStorage<?>) layer).setState(ret);
        }
        // only for debugging
        ((StateLayer) ret).setLayerType(type);

        return ret;
    }

    public Object get(ILocator index) {
        Object ret = storage.get(index);
        return ret == null && numeric ? Double.NaN : ret;
    }

    public long set(ILocator index, Object value) {
        touch();
        if (value instanceof IReducible) {
            if (this.valuePresentation != ValuePresentation.VALUE
                    && ((IReducible) value).getValuePresentation() != this.valuePresentation) {
                // TODO allow? curse?
            }
            this.valuePresentation = ((IReducible) value).getValuePresentation();
        }
        if (dataKey != null && value != null) {
            value = dataKey.include(value);
        }

        // help the inspector out
        if (debuggingStatistics != null) {
            if (storage instanceof AbstractAdaptiveStorage) {
                ((AbstractAdaptiveStorage) storage).setDebuggingStatistics(debuggingStatistics);
            } else if (storage instanceof KeyedStorage) {
                ((KeyedStorage) storage).getBackend().setDebuggingStatistics(debuggingStatistics);
            }
        }

        return storage.putObject(value, index);
    }

    public long size() {
        return getGeometry().size();
    }

    @Override
    public <T> T get(ILocator index, Class<T> cls) {
        return Utils.asType(get(index), cls);
    }

    @Override
    public IArtifact.Type getType() {
        return isArchetype() ? IArtifact.Type.VOID : (storage == null ? getObservable().getArtifactType() : storage.getType());
    }

    @Override
    public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
        return DataIterator.create(this, getScale().at(index), cls);
    }

    @Override
    public IDataKey getDataKey() {
        if (dataKey == null && getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
            // may result from merging more states: build the datakey from the storage
            dataKey = storage instanceof IKeyHolder ? ((IKeyHolder) storage).getDataKey() : null;
        }
        return dataKey;
    }

    @Override
    public void setDataKey(IDataKey key) {
        this.dataKey = key;
        if (this.storage instanceof IKeyHolder) {
            ((IKeyHolder) this.storage).setDataKey(key);
        }
    }

    @Override
    public IState at(ILocator locator) {

        if (locator instanceof ITime && Observations.INSTANCE.occurs(this)) {

            /*
             * TODO accumulating/subsetting wrapper based on observable semantics if this state
             * "occurs", i.e. it belongs to an event or is created by a process. This will review
             * the input based on the difference between the passed time and ours
             */

        } else if (locator instanceof Scale) {

            /*
             * if the locator is a scale, this should not modify it at all, otherwise locate the
             * scale to the passed object.
             */
            Scale scale = (Scale) getScale().at(locator);

            /*
             * if the located scale is conformant (i.e. points to whole dimensions or unique points
             * on them), return a located instance of this, otherwise create a rescaled instance.
             */
            return scale.isConformant(getScale())
                    ? new LocatedState(this, (Scale) scale, getScope())
                    : new RescalingState(this, (Scale) scale, getScope());

        }

        return this;
    }

    public String getUpdateDescription() {
        String ret = "";
        for (Pair<Long, Long> ll : timeCoverage) {
            ret += (ret.isEmpty() ? "" : "; ");
            ret += ll.getFirst() == 0 ? "" : new Date(ll.getFirst());
            if (ll.getSecond() > 0) {
                ret += (ll.getFirst() == 0 ? "" : " to ") + new Date(ll.getSecond());
            }
        }
        return ret;
    }

    @Override
    public long getLastUpdate() {

        if (this.timeCoverage.size() > 0) {
            long ret = -1;
            for (Pair<Long, Long> ll : timeCoverage) {
                ret = ll.getSecond() > 0 ? ll.getSecond() : ll.getFirst();
            }
            if (ret > 0) {
                return ret;
            }
        }
        return super.getLastUpdate();
    }

    @Override
    public long[] getUpdateTimestamps() {
        List<Long> list = new ArrayList<>();
        if (this.timeCoverage.size() > 0) {
            for (Pair<Long, Long> ll : timeCoverage) {
                if (ll.getFirst() == 0) {
                    // skip initialization
                    continue;
                }
                long lo = ll.getSecond() > 0 ? ll.getSecond() : ll.getFirst();
                if (lo > 0) {
                    list.add(lo);
                }
            }
        }
        return Utils.toLongArray(list);
    }

    public IState in(String mediator) {
        IValueMediator unit = null;
        if (mediator.contains("@")) {
            unit = Currency.create(mediator);
        } else {
            unit = Unit.create(mediator);
        }
        return in(unit);
    }

    @Override
    public IState in(IValueMediator mediator) {
        return MediatingState.mediateIfNecessary(this, mediator);
    }

    public void distributeScalar(Object pod) {
        for (ILocator locator : getScale()) {
            set(locator, pod);
        }
    }

    public IDataStorage<?> getStorage() {
        return storage;
    }

    /*
     * Used through k.Actors reactor
     */
    public File export(String fileName, Map<?,?> options) {
        File file = Configuration.INSTANCE.getExportFile(fileName);
        Observations.INSTANCE.export(this, file, getMonitor(), MapUtils.unfold(options));
        return file;
    }
    
    @Override
    public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
        Object o = aggregate(geometry);
        return Utils.asType(o, cls);
    }

    @Override
    public Object aggregate(ILocator... locators) {
        if (getScale().size() == 1) {
            return get(getScale().initialization(), Utils.getClassForType(getType()));
        }
        if (locators == null) {
            List<Object> values = new ArrayList<>();
            for (ILocator locator : getScale()) {
                values.add(get(locator));
            }
            AggregationUtils.aggregate(values, AggregationUtils.getAggregation(getObservable()), getScope().getMonitor());
        }
        throw new KlabUnimplementedException("aggregation of rescaled states is unimplemented - please submit a request");
    }

    @Override
    public void fill(Object value) {
        for (ILocator locator : getScale().initialization()) {
            set(locator, value);
        }
    }

    @Override
    public void finalizeTransition(IScale scale) {

        Observations.INSTANCE.getStateSummary(this, scale);
        setContextualized(true);
        if (scale.getTime() != null && scale.getTime().getTimeType() != ITime.Type.INITIALIZATION) {
            setDynamic(true);
            if (scale.getTime().getEnd() != null) {
                if (updateTimestamps.size() == 0
                        || updateTimestamps.get(updateTimestamps.size() - 1) < scale.getTime().getEnd().getMilliseconds()) {
                    updateTimestamps.add(scale.getTime().getEnd().getMilliseconds());
                }
            }
        } else if (this.updateTimestamps.size() == 0) {
            updateTimestamps.add(0L);
        }
    }

    @Override
    public String dump() {
        return "";
    }

    // for debugging: return all values in all slices, ignoring time in the passed
    // locator.
    public Object[] getTimeseries(ILocator locator) {
        if (getStorage() instanceof AbstractAdaptiveStorage) {
            return ((AbstractAdaptiveStorage<?>) getStorage()).getTimeseries(locator);
        } else if (getStorage() instanceof KeyedStorage) {
            return ((KeyedStorage<?>) getStorage()).getTimeseries(locator);
        }
        return new Object[]{};
    }

    /**
     * For scripts and expressions: return all the categories represented in the state, or an empty
     * list if not categorical or empty.
     * 
     * @return
     */
    public Collection<IConcept> getCategories() {
        Set<IConcept> ret = new HashSet<>();
        if (this.getDataKey() != null) {
            ret.addAll(this.getDataKey().getConcepts());
        }
        return ret;
    }

    /**
     * For scripts and expressions: collect the entire area where the state has the passed value.
     * Localize the state before calling if time is distributed, or use the located version.
     * 
     * @param state
     * @return
     */
    public double getArea(Object state, String unit) {
        return getArea(state, unit, null);
    }

    /**
     * For scripts and expressions: collect the entire area where the state has the passed value.
     * 
     * @param state
     * @return
     */
    public double getArea(Object state, String unit, ILocator locator) {

        double ret = 0;
        if (getScale().getSpace() == null) {
            return ret;
        }

        // /*
        // * FIXME this really shouldn't be necessary
        // */
        // if (state instanceof org.integratedmodelling.klab.extensions.groovy.model.Concept) {
        // state = (IConcept) ((org.integratedmodelling.klab.extensions.groovy.model.Concept)
        // state).getConcept();
        // }

        for (ILocator loc : (locator == null ? getScale() : getScale().at(locator))) {

            Object value = get(loc);

            if ((Observations.INSTANCE.isData(value) && value.equals(state))
                    || (Observations.INSTANCE.isNodata(value) && Observations.INSTANCE.isNodata(state))) {
                ret += ((IScale) loc).getSpace().getStandardizedArea();
            }
        }

        if (unit != null) {
            ret = Unit.create(unit).convert(ret, Units.INSTANCE.SQUARE_METERS).doubleValue();
        }

        return ret;
    }

    /**
     * Get a list of time locators that point to all the temporal states where there are new data.
     * At minimum it should return initialization; bound to the specific implementations so on
     * non-typical states it may return an empty list.
     * 
     * @return
     */
    public List<ILocator> getSliceLocators() {
        if (getStorage() instanceof AbstractAdaptiveStorage) {
            return ((AbstractAdaptiveStorage<?>) getStorage()).getTimesliceLocators();
        } else if (getStorage() instanceof KeyedStorage) {
            return ((KeyedStorage<?>) getStorage()).getTimesliceLocators();
        }
        return new ArrayList<>();
    }

    /**
     * For debugging only. If there are no slices an empty array is returned. Keystores will return
     * the slices from the underlying storage. Each slice contains the start/end time instants and
     * content statistics.
     * 
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Slice> getSlices() {

        if (getStorage() instanceof AbstractAdaptiveStorage) {
            return ((AbstractAdaptiveStorage) getStorage()).getSlices();
        } else if (getStorage() instanceof KeyedStorage) {
            return ((KeyedStorage<?>) getStorage()).getSlices();
        }
        return new ArrayList<>();
    }

    public Map<IConcept, Double> getAreaHistogram(String unit) {
        return getAreaHistogram(unit, null);
    }

    public Map<IConcept, Double> getAreaHistogram(String unit, ILocator locator) {
        Map<IConcept, Double> ret = new HashMap<>();
        if (getDataKey() != null) {
            for (ILocator loc : (locator == null ? getScale() : getScale().at(locator))) {
                Object value = get(loc);
                if (value instanceof IConcept) {
                    double area = ((IScale) loc).getSpace().getStandardizedArea();
                    if (unit != null) {
                        area = Unit.create(unit).convert(area, Units.INSTANCE.SQUARE_METERS).doubleValue();
                    }
                    ret.put((IConcept) value, ret.containsKey(value) ? ret.get((IConcept) value) + area : area);
                }
            }
        }

        return ret;
    }

    public StateSummary getOverallSummary() {
        IStorage<?> stor = this.storage;
        if (stor instanceof KeyedStorage) {
            stor = ((KeyedStorage<?>) stor).getBackend();
        }
        if (stor instanceof AbstractAdaptiveStorage) {
            return ((AbstractAdaptiveStorage<?>) stor).getOverallSummary();
        }
        return null;
    }

    public void dumpStatistics() {
        for (Slice slice : getSlices()) {
            System.out.println(slice + "\n" + StringUtil.leftIndent(slice.getRawStatistics().toString(), 3));
        }
    }

    public Statistics computeStatistics(Object locator) {
        IStorage<?> stor = this.storage;
        if (stor instanceof KeyedStorage) {
            stor = ((KeyedStorage<?>) stor).getBackend();
        }
        if (stor instanceof AbstractAdaptiveStorage) {
            return ((AbstractAdaptiveStorage<?>) stor).computeStatistics(locator);
        }
        return null;
    }

    public void setStatistics(List<Statistics> stats) {
        this.debuggingStatistics = stats;
    }

}

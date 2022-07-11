package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.atteo.evo.inflector.English;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.ILocator.UniversalLocator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.INetwork;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IEnumeratedExtent;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.localstorage.impl.TimesliceLocator;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroupView;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.data.storage.MergingState;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ActionReference;
import org.integratedmodelling.klab.rest.DataSummary;
import org.integratedmodelling.klab.rest.Histogram;
import org.integratedmodelling.klab.rest.Histogram.Builder;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.ObservationReference.ValueType;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.ZipUtils;

public enum Observations implements IObservationService {

    INSTANCE;

    public static final String PRESENT_LABEL = "Present";
    public static final String NOT_PRESENT_LABEL = "Not present";

    Map<Long, Map<String, StateSummary>> summaryCache = new HashMap<>();

    private Observations() {
        Services.INSTANCE.registerService(this, IObservationService.class);
    }

    @Override
    public IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios)
            throws KlabException {
        return Resolver.create(null).resolve(urn, session, scenarios);
    }

    @Override
    public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios)
            throws KlabException {
        return Resolver.create(null).resolve(urn, context, scenarios);
    }

    // @Override
    public void releaseNamespace(String namespaceId, IMonitor monitor) throws KlabException {
        // TODO remove all artifacts from local kbox
    }

    @Override
    public void index(IObserver observer, IMonitor monitor) throws KlabException {
        if (monitor.getIdentity().getParentIdentity(IScript.class) == null) {
            Indexer.INSTANCE.index(observer.getStatement(), observer.getNamespace().getName());
        }
    }

    @Override
    public IState getStateView(IState state, IScale scale, IContextualizationScope scope) {
        return new RescalingState(state, (Scale) scale, (IRuntimeScope) scope);
    }

    @Override
    public IState getStateViewAs(IObservable observable, IState state, IScale scale,
            IContextualizationScope scope) {
        return new RescalingState(state, observable, (Scale) scale, (IRuntimeScope) scope);
    }

    /**
     * Return the summary for the data in a state, computing it if necessary. FIXME caching strategy
     * currently just fills up forever - must shed caches as transitions advance.
     * 
     * @param state a state
     * @param locator the subsetting locator for the wanted data, or null if global summaries are
     *        required.
     * @return the state summary
     */
    public StateSummary getStateSummary(IState state, ILocator locator) {

        long time = -1;

        if (locator == null) {
            if (state instanceof State) {
                return ((State) state).getOverallSummary();
            }
            locator = UniversalLocator.INSTANCE;
        }

        if (state instanceof State && !(state instanceof MergingState)) {
            time = ((State) state).getStorage().getTemporalOffset(locator);
        } else {
            if (locator instanceof IScale && ((IScale) locator).getTime() != null) {
                ITime timeExtent = ((IScale) locator).getTime();
                time = timeExtent.getStart().getMilliseconds();
                if (timeExtent.getFocus() != null && timeExtent.getFocus().isAfter(timeExtent.getStart())) {
                    time += (timeExtent.getEnd().getMilliseconds() - timeExtent.getStart().getMilliseconds())
                            / 2;
                }
            } else if (locator instanceof ITime) {
                ITime timeExtent = (ITime) locator;
                time = timeExtent.getStart().getMilliseconds();
                time = ((ITime) locator).getStart().getMilliseconds();
                if (timeExtent.getFocus() != null && timeExtent.getFocus().isAfter(timeExtent.getStart())) {
                    time += (timeExtent.getEnd().getMilliseconds() - timeExtent.getStart().getMilliseconds())
                            / 2;
                }
            }
        }

        StateSummary ret = null;
        Map<String, StateSummary> cached = summaryCache.get(time);
        if (cached != null && cached.containsKey(state.getId())
                && cached.get(state.getId()).getStateTimestamp() == ((Observation) state).getTimestamp()) {
            ret = cached.get(state.getId());
        } else {
            ret = computeStateSummary(state,
                    getTemporalLocator(state,
                            locator instanceof UniversalLocator ? state.getScale() : locator));
            if (cached == null) {
                cached = new HashMap<>();
                summaryCache.put(time, cached);
            }
            cached.put(state.getId(), ret);
        }
        return ret;
    }

    private StateSummary computeStateSummary(IState state, ILocator locator) {

        StateSummary ret = new StateSummary();

        long ndata = 0;
        long nndat = 0;

        ret.setStateTimestamp(((Observation) state).getTimestamp());
        double min, max;

        boolean isBoolean = state.getType() == IArtifact.Type.BOOLEAN;

        if (state.getDataKey() != null || isBoolean) {

            min = Double.MIN_VALUE;
            max = Double.MAX_VALUE;
            ret.setMean(Double.NaN);
            ret.setVariance(Double.NaN);
            ret.setStandardDeviation(Double.NaN);
            ret.setSingleValued(false);

            StateSummary summary = null;
            if (state instanceof State) {
                summary = ((State) state).getOverallSummary();
            }
            
            if (summary != null) {
                
                Builder histogram = Histogram.builder(summary.getRange().get(0), summary.getRange().get(1),
                        isBoolean ? 2 : state.getDataKey().size());
                for (ILocator ll : locator) {
                    ndata ++;
                    Object o = state.get(ll);
                    if (o instanceof Boolean && histogram != null) {
                        histogram.addToIndex((Boolean) o ? 1 : 0);
                    } else if (o != null && state.getDataKey() != null && histogram != null) {
                        histogram.addToIndex(state.getDataKey().reverseLookup(o));
                    } else {
                        nndat++;
                    }
                }

                ret.setHistogram(histogram.build());
                ret.setDegenerate(ndata == 0);
                ret.setNodataPercentage((double) nndat / (double) (ndata + nndat));
                ret.setRange(summary.getRange());
                ret.setValueCount(ndata + nndat);
            }

        } else {

            SummaryStatistics statistics = null;
            StateSummary summary = null;
            if (state instanceof State) {
                summary = ((State) state).getOverallSummary();
            }

            if (statistics == null) {

                statistics = new SummaryStatistics();
                Builder histogram = null;
                if (summary != null) {
                    // histogram min/max will reflect the entire range of the data as computed this
                    // far, not just the locator
                    histogram = Histogram.builder(summary.getRange().get(0), summary.getRange().get(1),
                            isBoolean ? 2 : (state.getDataKey() == null ? 10 : state.getDataKey().size()));
                }
                for (ILocator ll : locator) {
                    Object o = state.get(ll);
                    if (o instanceof Number) {
                        ndata++;
                        statistics.addValue(((Number) o).doubleValue());
                        if (histogram != null) {
                            if (state.getDataKey() != null) {
                                histogram.addToIndex(state.getDataKey().reverseLookup(o));
                            } else {
                                histogram.add(((Number) o).doubleValue());
                            }
                        }
                    } else if (o instanceof Boolean && histogram != null) {
                        histogram.addToIndex((Boolean) o ? 1 : 0);
                    } else if (o != null && state.getDataKey() != null && histogram != null) {
                        histogram.addToIndex(state.getDataKey().reverseLookup(o));
                    } else {
                        nndat++;
                    }
                }
                if (histogram != null) {
                    ret.setHistogram(histogram.build());
                }
            }

            min = statistics.getMin();
            max = statistics.getMax();

            if (state.getDataKey() != null) {
                Map<Integer, String> key = new HashMap<>();
                for (Pair<Integer, String> pair : state.getDataKey().getAllValues()) {
                    key.put(pair.getFirst(), pair.getSecond());
                }
                ret.setDataKey(key);
            }

            ret.setDegenerate(ndata == 0 || !Double.isFinite(statistics.getMax())
                    || !Double.isFinite(statistics.getMax()));
            ret.setNodataPercentage((double) nndat / (double) (ndata + nndat));
            ret.setRange(Arrays.asList(statistics.getMin(), statistics.getMax()));
            ret.setValueCount(ndata + nndat);
            ret.setMean(statistics.getMean());
            ret.setVariance(statistics.getVariance());
            ret.setStandardDeviation(statistics.getStandardDeviation());
            ret.setSingleValued(statistics.getMax() == statistics.getMin());
            ret.setSum(statistics.getSum());
        }

        ret.setRange(Arrays.asList(min, max));

        return ret;
    }

    private IScale getTemporalLocator(IState state, ILocator locator) {

        List<IExtent> extents = new ArrayList<>();
        if (locator instanceof IScale && ((IScale) locator).getTime() != null) {
            extents.add(((IScale) locator).getTime());
        } else if (locator instanceof ITime) {
            extents.add((ITime) locator);
        }

        if (state.getScale().getSpace() != null) {
            extents.add(state.getScale().getSpace());
        }

        return Scale.create(extents);
    }

    /*
     * Non-API - sync namespace. TODO check equivalent in Models.
     */
    public void registerNamespace(Namespace ns, Monitor monitor) {
        // TODO Auto-generated method stub
    }

    public ObservationReference createArtifactDescriptor(IObservation observation) {
        return createArtifactDescriptor(observation, null, 0, null, true);
    }

    public ObservationReference createArtifactDescriptor(IObservation observation, ILocator locator,
            int childLevel) {
        return createArtifactDescriptor(observation, locator, childLevel, null, false);
    }

    public ObservationReference createArtifactDescriptor(IObservation observation, ILocator locator,
            int childLevel,
            String viewId, boolean finalized) {

        ObservationReference ret = new ObservationReference();

        // change later if a state
        ret.setValueType(ValueType.VOID);
        ret.setEmpty(observation.isEmpty());

        // for now
        ret.setPrimary(true);

        if (observation instanceof ISubject) {
            ret.setObservationType(ObservationReference.ObservationType.SUBJECT);
        } else if (observation instanceof IState) {
            ret.setObservationType(ObservationReference.ObservationType.STATE);
        } else if (observation instanceof IProcess) {
            ret.setObservationType(ObservationReference.ObservationType.PROCESS);
        } else if (observation instanceof IEvent) {
            ret.setObservationType(ObservationReference.ObservationType.EVENT);
        } else if (observation instanceof IConfiguration) {
            ret.setObservationType(ObservationReference.ObservationType.CONFIGURATION);
        } else if (observation instanceof IRelationship) {
            ret.setObservationType(ObservationReference.ObservationType.RELATIONSHIP);
        } else if (observation instanceof ObservationGroup) {
            ret.setObservationType(ObservationReference.ObservationType.GROUP);
        } else if (observation instanceof ObservationGroupView) {
            ret.setObservationType(ObservationReference.ObservationType.VIEW);
            ret.setOriginalGroupId(((ObservationGroupView) observation).getOriginalGroup().getId());
        }

        ret.setCreationTime(observation.getTimestamp());
        ret.setLastUpdate(((Observation) observation).getLastUpdate());
        ret.setExportLabel(observation.getObservable().getName());
        ret.setContextualized(((Observation) observation).isContextualized());

        ISubject rootSubject = ((Observation) observation).getScope().getRootSubject();
        if (rootSubject != null) {
            ret.setRootContextId(rootSubject.getId());
        }

        IArtifact parent = observation.getScope().getParentOf(observation);
        IArtifact parentArtifact = observation.getScope().getParentArtifactOf(observation);

        ret.setId(observation.getId());
        ret.setContextId(((Observation) observation).getObservationContextId());
        ret.setUrn(observation.getUrn());
        ret.setParentId(parent == null ? null : parent.getId());
        ret.setParentArtifactId(parentArtifact == null ? null : parentArtifact.getId());
        ret.setAlive(((Observation) observation).isAlive());
        ret.setLabel(getDisplayLabel(observation));
        ret.setDynamic(((Observation) observation).isDynamic());
        ret.setObservable(observation.getObservable().getDefinition());

        if (ret.getObservable() == null) {
            ret.setObservable("Quantity has no semantics associated");
        }

        ret.setChildrenCount(
                observation instanceof IDirectObservation && !observation.isEmpty()
                        ? observation.getChildArtifacts().size()
                        : 0);
        ret.getSemantics().addAll(((Concept) observation.getObservable().getType()).getTypeSet());

        ISpace space = ((IScale) observation.getGeometry()).getSpace();
        ITime time = ((IScale) observation.getGeometry()).getTime();

        /*
         * Send full scale for any countables
         */
        if (observation.getObservable().is(Type.COUNTABLE)) {

            ScaleReference scaleReference = new ScaleReference();
            if (space instanceof IEnumeratedExtent) {

                scaleReference.setSpaceEnumerated(true);
                String description = "";
                for (IConcept c : ((IEnumeratedExtent) space).getExtension()) {
                    scaleReference.getSpaceExtension().add(c.getDefinition());
                    description += (description.isEmpty() ? "" : ", ") + Concepts.INSTANCE.getDisplayLabel(c);
                }
                scaleReference.setName(description);

                // TODO use convention-based metadata for bounding box, shape, rank etc., if any
                // are available (see which vocabulary to adopt).

            } else if (space != null) {

                IEnvelope envelope = space.getEnvelope();
                Grid grid = space instanceof Space ? (Grid) ((Space) space).getGrid() : null;
                Pair<Integer, String> resolution = ((Envelope) envelope).getResolutionForZoomLevel();
                Unit sunit = Unit.create(resolution.getSecond());
                int scaleRank = envelope.getScaleRank();
                scaleReference.setEast(envelope.getMaxX());
                scaleReference.setWest(envelope.getMinX());
                scaleReference.setNorth(envelope.getMaxY());
                scaleReference.setSouth(envelope.getMinY());

                if (grid == null) {

                    scaleReference.setSpaceUnit(resolution.getSecond());
                    scaleReference.setSpaceResolution(resolution.getFirst());
                    scaleReference.setSpaceResolutionConverted(
                            sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS).doubleValue());
                    scaleReference.setSpaceResolutionDescription(
                            sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS) + " "
                                    + resolution.getSecond());
                    scaleReference.setResolutionDescription(
                            sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS) + " "
                                    + resolution.getSecond());

                } else {

                    // use the grid.
                    Unit unit = Unit.create(resolution.getSecond());
                    double cw = unit.convert(grid.getCell(0).getStandardizedWidth(), Units.INSTANCE.METERS)
                            .doubleValue();
                    scaleReference.setSpaceUnit(unit.toString());
                    scaleReference.setSpaceResolution(grid.getCellWidth());
                    scaleReference.setSpaceResolutionConverted(cw);
                    scaleReference.setSpaceResolutionDescription(
                            String.format("%.1f", cw) + " " + resolution.getSecond());
                    scaleReference.setResolutionDescription(
                            String.format("%.1f", cw) + " " + resolution.getSecond());

                }
                scaleReference.setSpaceScale(scaleRank);
            }
            if (time != null) {

                scaleReference.setTimeScale(time.getScaleRank());
                scaleReference.setStart(time.getStart() == null ? 0 : time.getStart().getMilliseconds());
                scaleReference.setEnd(time.getEnd() == null ? 0 : time.getEnd().getMilliseconds());
                scaleReference.setTimeResolutionDescription(time.getResolution() == null
                        ? null
                        : time.getResolution().getMultiplier() + " "
                                + StringUtils.capitalize(time.getResolution().getType().name()));
            }

            ret.setScaleReference(scaleReference);
        }

        long[] updateTimestamps = ((Observation) observation).getUpdateTimestamps();
        if (updateTimestamps != null && updateTimestamps.length > 0) {
            ret.setTimeEvents(new ArrayList<>());
            for (long l : updateTimestamps) {
                ret.getTimeEvents().add(l);
            }
        }

        if (observation instanceof State) {
            String modTimes = ((State) observation).getUpdateDescription();
            if (!modTimes.isEmpty()) {
                ret.getMetadata().put("Temporal transitions", modTimes);
            }
        }

        // fill in spatio/temporal info and mode of visualization
        if (space instanceof IEnumeratedExtent) {

            // TODO!

        } else if (space != null) {

            ret.getMetadata().put("Total area",
                    NumberFormat.getInstance()
                            .format(space.getShape().getArea(Units.INSTANCE.SQUARE_KILOMETERS)) + " km2");

            /*
             * shapes can be huge and only make sense for direct observations, as states get the
             * same geometry as their parents. TODO fiddle with the parameters to
             * simplifyIfNecessary until interaction is smooth. TODO these should also be optional
             * settings.
             */
            if (observation instanceof IDirectObservation
                    && !(observation instanceof ObservationGroup
                            || observation instanceof ObservationGroupView)) {
                String shape = ((Shape) space.getShape()).simplifyIfNecessary(1000, 2000).getJTSGeometry()
                        .toString();
                ret.setEncodedShape(shape);
                ret.setSpatialProjection(space.getProjection().getSimpleSRS());
                ret.setShapeType(space.getShape().getGeometryType());
            }

            GeometryType gtype = GeometryType.SHAPE;
            if (observation instanceof IState && space.isRegular() && space.size() > 1) {

                gtype = GeometryType.RASTER;

                IGrid grid = ((Space) space).getGrid();

                ret.getMetadata().put("Grid size",
                        grid.getCellCount() + " (" + grid.getXCells() + " x " + grid.getYCells() + ") cells");
                ret.getMetadata().put("Cell size",
                        NumberFormat.getInstance().format(grid.getCell(0).getStandardizedWidth())
                                + " x "
                                + NumberFormat.getInstance().format(grid.getCell(0).getStandardizedHeight())
                                + " m");
            }

            /*
             * compute available export formats. This may change after an update so it's done also
             * in ObservationChange.
             */
            ret.getExportFormats().addAll(getExportFormats(observation));
            ret.getGeometryTypes().add(gtype);
        }

        for (String key : observation.getMetadata().keySet()) {
            Object value = observation.getMetadata().get(key);
            if (Utils.isPOD(value)) {
                ret.getMetadata().put(key, value.toString());
            }
        }

        if (time != null) {
            // TODO
        }

        if (observation instanceof IDirectObservation && !observation.isEmpty()
                && (childLevel < 0 || childLevel > 0)) {

            for (IArtifact child : observation.getChildArtifacts()) {
                if (child instanceof IObservation) {
                    ret.getChildren()
                            .add(createArtifactDescriptor((IObservation) child/* , observation */, locator,
                                    childLevel > 0 ? (childLevel - 1) : childLevel,
                                    observation instanceof ObservationGroupView ? observation.getId() : null,
                                    finalized));
                }
            }
        }

        if (observation instanceof IState) {

            StateSummary summary = getStateSummary((IState) observation, locator);

            /**
             * CHECK we should probably just use the artifact type, although it may be confusing for
             * API users.
             */
            switch(observation.getType()) {
            case NUMBER:
                ret.setValueType(ValueType.NUMBER);
                break;
            case BOOLEAN:
                ret.setValueType(ValueType.BOOLEAN);
                break;
            case CONCEPT:
                ret.setValueType(ValueType.CATEGORY);
                break;
            default:
                break;
            }

            ret.setValueCount(observation.getScale().size());
            if (observation.getScale().size() == 1) {
                Object value = ((IState) observation).get(observation.getScale().at(0));
                ret.setLiteralValue(formatValue(observation.getObservable(), value));
                ret.setOverallValue("" + value);
            } else if (observation.getScale().getSpace().size() > 1
                    && observation.getScale().getSpace().isRegular()) {
                if (!summary.isDegenerate()) {
                    ret.getGeometryTypes().add(GeometryType.COLORMAP);
                    if (observation.getType() == IArtifact.Type.NUMBER && summary.getVariance() == 0
                            && finalized) {
                        Object value = summary.getMean();
                        ret.setLiteralValue(formatValue(observation.getObservable(), value));
                        ret.setOverallValue("" + value);
                    }
                }
            }

            ret.setHistogram(summary.getHistogram());
            ret.setColormap(summary.getColormap());
            ret.setKey(summary.getDataKey());

            DataSummary ds = new DataSummary();

            ds.setNodataProportion(summary.getNodataPercentage());
            ds.setMinValue(summary.getRange().get(0));
            ds.setMaxValue(summary.getRange().get(1));
            ds.setMean(summary.getMean());
            if (summary.getHistogram() != null && !summary.isSingleValued()) {
                for (int bin : summary.getHistogram().getBins()) {
                    ds.getHistogram().add(bin);
                }
            }
            IDataKey dataKey = ((IState) observation).getDataKey();
            if (observation.getType() == IArtifact.Type.BOOLEAN) {
                ds.setCategorized(true);
                ds.getCategories().add(0, NOT_PRESENT_LABEL);
                ds.getCategories().add(1, PRESENT_LABEL);
            } else if (dataKey != null) {
                ds.setCategorized(true);
                ds.getCategories()
                        .addAll(dataKey.getAllValues().stream().map(key -> key.getSecond())
                                .collect(Collectors.toList()));
            } else {
                ds.setCategorized(false);
                double step = (summary.getRange().get(1) - summary.getRange().get(0))
                        / (double) ds.getHistogram().size();
                for (int i = 0; i < ds.getHistogram().size(); i++) {
                    // TODO use labels for categories
                    String lower = NumberFormat.getInstance().format(summary.getRange().get(0) + (step * i));
                    String upper = NumberFormat.getInstance()
                            .format(summary.getRange().get(0) + (step * (i + 1)));
                    ds.getCategories().add(lower + " to " + upper);
                }
            }
            ret.setDataSummary(ds);
        }

        /*
         * activity that generated us.
         */
        ret.setTaskId(((Artifact) observation).getGeneratorActivityId());

        ret.getActions().add(ActionReference.separator());
        ret.getActions().add(new ActionReference("Add to cache", "AddToCache"));

        return ret;
    }

    public List<ExportFormat> getExportFormats(IObservation observation) {

        Map<String, Pair<Triple<String, String, String>, String>> formats = new LinkedHashMap<>();
        List<ExportFormat> ret = new ArrayList<>();

        for (IResourceAdapter adapter : Resources.INSTANCE.getResourceAdapters()) {
            for (Triple<String, String, String> capabilities : adapter.getImporter()
                    .getExportCapabilities(observation)) {
                formats.put(capabilities.getFirst(), new Pair<>(capabilities, adapter.getName()));
            }
        }

        /*
         * add anything we don't have adapters for, at the moment just networks
         */
        if (observation instanceof IConfiguration && ((IConfiguration) observation).is(INetwork.class)) {
            INetwork network = ((IConfiguration) observation).as(INetwork.class);
            for (Triple<String, String, String> capabilities : network.getExportCapabilities(observation)) {
                formats.put(capabilities.getFirst(),
                        new Pair<>(capabilities,
                                org.integratedmodelling.klab.components.network.model.Network.ADAPTER_ID));
            }
        }

        /*
         * For now only one adapter is kept per format. Later we may offer the option by using a set
         * instead of a map and implementing equals() for ExportFormat to check all three.
         */
        for (String format : formats.keySet()) {
            Pair<Triple<String, String, String>, String> data = formats.get(format);
            ret.add(new ExportFormat(data.getFirst().getSecond(), format, data.getSecond(),
                    data.getFirst().getThird()));
        }

        return ret;
    }

    public String getDisplayLabel(IObservation observation) {

        String ret = null;
        if (observation instanceof ObservationGroup) {
            ret = StringUtils.capitalize(English.plural(observation.getObservable().getType().getName()));
        } else {

            ret = (observation instanceof IDirectObservation
                    ? ((IDirectObservation) observation).getName()
                    : observation.getObservable().getName());
            ret = StringUtils.capitalize(ret.replaceAll("_", " "));

            if (observation instanceof ObservationGroupView) {
                // pluralize the last word, then tell me I don't care for details.
                String[] sss = ret.split("\\s+");
                if (sss.length > 0) {
                    sss[sss.length - 1] = English.plural(sss[sss.length - 1]);
                    ret = StringUtils.join(sss, ' ');
                }
            }
        }

        /*
         * add any concrete identities that were missing in the name so far. This is meant to
         * capture information from observables that were named explicitly in abstract models that
         * were replicated to concretize identities or roles.
         */
        if (!observation.getObservable().getResolvedPredicates().isEmpty()) {
            String preds = "";
            for (IConcept pred : observation.getObservable().getResolvedPredicates().values()) {
                String pname = Concepts.INSTANCE.getDisplayName(pred);
                if (!ret.toLowerCase().contains(pname.toLowerCase())) {
                    preds += (preds.isEmpty() ? "" : ", ") + StringUtils.capitalize(pname);
                }
            }

            if (!preds.isEmpty()) {
                ret += " (" + preds + ")";
            }

        }

        if (observation.getObservable().getUnit() != null) {
            ret = ret + " in " + ((Unit) observation.getObservable().getUnit()).toUTFString();
        } else if (observation.getObservable().getCurrency() != null) {
            ret = ret + " in " + observation.getObservable().getCurrency();
        } else if (observation.getObservable().getRange() != null) {
            ret = ret + " " + observation.getObservable().getRange().getLowerBound() + " to "
                    + observation.getObservable().getRange().getUpperBound();
        }

        return ret;
    }

    public String formatValue(IObservable observable, Object object) {

        if (object instanceof IConcept) {
            object = Concepts.INSTANCE.getDisplayName((IConcept) object);
        } else if (object instanceof Number) {
            object = NumberFormat.getNumberInstance().format(((Number) object).doubleValue());
        }

        String ret = observable.getName() + ": " + object;

        if (observable.getUnit() != null) {
            ret += " " + observable.getUnit();
        } else if (observable.getCurrency() != null) {
            ret += " " + observable.getCurrency();
        }

        return ret;
    }

    public Observer makeROIObserver(final Shape shape, ITime time, Namespace namespace, String currentName,
            IMonitor monitor) {
        final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
        Session session = monitor.getIdentity().getParentIdentity(Session.class);
        observable.setName(Geocoder.INSTANCE.geocode(shape.getEnvelope(),
                session == null ? null : session.getState().getGeocodingStrategy(), currentName, monitor));
        observable.setOptional(true);
        if (namespace == null) {
            namespace = Namespaces.INSTANCE.getNamespace(observable.getNamespace());
        }
        return new Observer(shape, time, observable, (Namespace) namespace);
    }

    public Observer makeROIObserver(String name, IShape shape, ITime time, IMetadata metadata) {
        final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
        observable.setName(name);
        observable.setOptional(true);
        Observer ret = new Observer((Shape) shape, time, observable,
                Namespaces.INSTANCE.getNamespace(observable.getNamespace()));
        ret.getMetadata().putAll(metadata);
        return ret;
    }

    public Observer makeROIObserver(String name, IGeometry geometry, IMetadata metadata) {
        final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
        observable.setName(name);
        observable.setOptional(true);
        Observer ret = new Observer(name, Scale.create(geometry), observable,
                Namespaces.INSTANCE.getNamespace(observable.getNamespace()));
        ret.getMetadata().putAll(metadata);
        return ret;
    }

    public Observer makeObserver(IObservable observable, IGeometry geometry, IMetadata metadata) {
        ((Observable) observable).setOptional(true);
        Observer ret = new Observer(observable.getName(), Scale.create(geometry), (Observable) observable,
                Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace()));
        ret.getMetadata().putAll(metadata);
        return ret;
    }

    /**
     * True if scale has multiple values in each state of the passed extent type.
     * 
     * @param extentType
     */
    public boolean isDistributedOutside(IObservation state, IGeometry.Dimension.Type dimension) {

        long mul = state.getScale().size();
        IGeometry.Dimension ext = state.getScale().getDimension(dimension);
        long nex = ext == null ? 1 : ext.size();
        return (mul / nex) > 1;
    }

    /**
     * Produce a classification that discretizes the range of the passed numeric state. If the state
     * is all no-data, return null without error.
     * 
     * @param s
     * @param maxBins
     * @param locators
     * @return discretization of range
     */
    public IDataKey discretize(IState s, ILocator locator, int maxBins) {
        StateSummary summary = getStateSummary(s, locator);
        return new Discretization(Range.create(summary.getRange()), maxBins);
    }

    @Override
    public File export(IObservation observation, ILocator locator, File file, String outputFormat,
            @Nullable String adapterId,
            IMonitor monitor, Object... options) {

        IResourceAdapter adapter = null;
        if (adapterId == null) {
            for (IResourceAdapter a : Resources.INSTANCE.getResourceAdapters()) {
                for (Triple<String, String, String> capabilities : a.getImporter()
                        .getExportCapabilities(observation)) {
                    if (capabilities.getFirst().equals(outputFormat)) {
                        adapter = a;
                        break;
                    }
                }
                if (adapter != null) {
                    break;
                }
            }
            if (adapter == null) {
                throw new IllegalArgumentException("no adapter can handle the output format " + outputFormat);
            }

        } else {
            adapter = Resources.INSTANCE.getResourceAdapter(adapterId);
            if (adapter == null) {
                throw new IllegalArgumentException(
                        "unknown adapter " + adapterId + " to export output format " + outputFormat);
            }
        }

        IResourceImporter importer = adapter.getImporter();

        if (options != null && options.length > 0 && options.length % 2 == 0) {
            for (int i = 0; i < options.length; i++) {
                importer = importer.withOption(options[i].toString(), options[++i]);
            }
        }

        return importer.exportObservation(file, observation, locator, outputFormat, monitor);
    }

    public boolean isData(Object o) {
        return o != null && !(o instanceof Number && Double.isNaN(((Number) o).doubleValue()));
    }

    public boolean isNodata(Object o) {
        return !isData(o);
    }

    /**
     * Do your best to retrieve an area in square meters from the passed locator.
     * 
     * @param locator
     * @return
     */
    public double getArea(ILocator locator) {
        if (locator instanceof IScale) {
            return ((IScale) locator).getSpace().getStandardizedArea();
        } else if (locator instanceof ISpace) {
            return ((ISpace) locator).getStandardizedArea();
        }
        return 0;
    }

    /**
     * Do your best to retrieve an current time extent from the passed locator.
     * 
     * @param locator
     * @return
     */
    public ITime getTime(ILocator locator) {
        if (locator instanceof IScale) {
            return ((IScale) locator).getTime();
        } else if (locator instanceof ITime) {
            return (ITime) locator;
        }
        return null;
    }

    /**
     * Return whether the passed observation has a temporal identity. This applies to events,
     * processes, groups of events, and states that either belong to events or are affected by
     * processes.
     * 
     * @param observation
     * @return
     */
    public boolean occurs(IObservation observation) {
        return observation instanceof IProcess || observation instanceof IEvent
                || observation.getScope().getParentOf(observation) instanceof IEvent
                || ((IRuntimeScope) observation.getScope()).getStructure()
                        .getOwningProcess(observation) != null;
    }

    public String describeValue(Object value) {
        return value instanceof Number
                ? NumberFormat.getInstance().format(((Number) value).doubleValue())
                : (value instanceof IConcept
                        ? Concepts.INSTANCE.getDisplayLabel(((IConcept) value))
                        : (value instanceof Boolean
                                ? ((Boolean) value
                                        ? Observations.PRESENT_LABEL
                                        : Observations.NOT_PRESENT_LABEL)
                                : "No data"));
    }

    public File packObservations(List<Object> args, IMonitor monitor) {

        List<IObservation> artifacts = new ArrayList<>();
        for (Object arg : args) {
            addArtifacts(arg, artifacts);
        }

        File stagingArea = Configuration.INSTANCE.getScratchDataDirectory("download");

        // String is either Just the artifact name or artifact name/time label if
        // multiple
        // timeslices are available
        List<File> exports = new ArrayList<>();
        for (IObservation artifact : artifacts) {

            /*
             * export [each timeslice] to a file.
             */
            List<ExportFormat> formats = getExportFormats(artifact);
            if (formats.size() > 0) {

                ExportFormat format = formats.get(0);
                List<ILocator> states = new ArrayList<>();
                if (artifact instanceof State) {
                    states.addAll(((State) artifact).getSliceLocators());
                } else {
                    states.add(artifact.getScale());
                }

                for (ILocator locator : states) {

                    String outfile = Concepts.INSTANCE.getCodeName(artifact.getObservable().getType()) + "."
                            + format.getExtension();
                    if (locator instanceof TimesliceLocator) {
                        String timedir = ((TimesliceLocator) locator).getLabel().replaceAll("\\s+", "_")
                                .replaceAll(":", "-")
                                .replaceAll("/", "-").toLowerCase();
                        File dir = new File(stagingArea + File.separator + timedir);
                        dir.mkdir();
                        outfile = timedir + File.separator + outfile;
                    }
                    File output = new File(stagingArea + File.separator + outfile);
                    if (states.size() == 1) {
                        output = export(artifact, locator, output, format.getValue(), format.getAdapter(),
                                monitor);
                    } else {
                        output = export(artifact, locator, output, format.getValue(), format.getAdapter(),
                                monitor,
                                IResourceImporter.OPTION_DO_NOT_ZIP_MULTIPLE_FILES, true);
                    }
                    output.deleteOnExit();
                    exports.add(output);
                }
            }
        }

        /*
         * produce the final output. If one non-zip file, we have it; if one zip, we also have it;
         * if multiple files OR multiple zips, unpack any zips and make a final package.
         */
        if (exports.size() == 1) {
            return exports.get(0);
        } else if (exports.size() > 1) {
            try {
                File zipFile = File.createTempFile("out", ".zip");
                // must not exist or the zip thing will complain
                FileUtils.deleteQuietly(zipFile);
                ZipUtils.zip(zipFile, stagingArea, false, true);
                return zipFile;
            } catch (IOException e) {
                throw new KlabIOException(e);
            }
        }

        return null;
    }

    private void addArtifacts(Object arg, List<IObservation> artifacts) {
        if (arg instanceof IObservation) {
            artifacts.add((IObservation) arg);
        } else if (arg instanceof Collection) {
            for (Object o : ((Collection<?>) arg)) {
                addArtifacts(o, artifacts);
            }
        }

    }

}

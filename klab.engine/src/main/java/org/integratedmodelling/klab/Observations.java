package org.integratedmodelling.klab;

import java.io.File;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
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
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.processing.osm.Nominatim;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
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
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.Utils;

public enum Observations implements IObservationService {

	INSTANCE;

	Map<ILocator, Map<String, StateSummary>> summaryCache = new HashMap<>();

	private Observations() {
		Services.INSTANCE.registerService(this, IObservationService.class);
	}

	@Override
	public IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios) throws KlabException {
		return Resolver.INSTANCE.resolve(urn, session, scenarios);
	}

	@Override
	public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios) throws KlabException {
		return Resolver.INSTANCE.resolve(urn, context, scenarios);
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
	public IState getStateView(IState state, IScale scale, IComputationContext context) {
		return new RescalingState(state, (Scale) scale, (IRuntimeContext) context);
	}

	@Override
	public IState getStateViewAs(IObservable observable, IState state, IScale scale, IComputationContext context) {
		return new RescalingState(state, observable, (Scale) scale, (IRuntimeContext) context);
	}

	/**
	 * Return the summary for the data in a state, computing it if necessary.
	 * 
	 * FIXME caching strategy currently just fills up forever - must shed caches as
	 * transitions advance.
	 * 
	 * @param state
	 *            a state
	 * @param locator
	 *            the subsetting locator for the wanted data, or null if global
	 *            summaries are required.
	 * @return the state summary
	 */
	public StateSummary getStateSummary(IState state, ILocator locator) {

		StateSummary ret = null;
		Map<String, StateSummary> cached = summaryCache.get(locator);
		if (cached != null && cached.containsKey(state.getId())
				&& cached.get(state.getId()).getStateTimestamp() == ((Observation) state).getTimestamp()) {
			ret = cached.get(state.getId());
		} else {
			ret = computeStateSummary(state, locator);
			if (cached == null) {
				cached = new HashMap<>();
				summaryCache.put(locator, cached);
			}
			cached.put(state.getId(), ret);
		}
		return ret;
	}

	private StateSummary computeStateSummary(IState state, ILocator locator) {

		StateSummary ret = new StateSummary();

		long ndata = 0;
		long nndat = 0;
		long tdata = 0;

		ret.setStateTimestamp(((Observation) state).getTimestamp());

		SummaryStatistics statistics = new SummaryStatistics();

		for (Iterator<Double> it = state.iterator(locator, Double.class); it.hasNext();) {
			tdata++;
			Double d = it.next();
			if (d != null) {
				ndata++;
				statistics.addValue(d);
			} else {
				nndat++;
			}
		}

		ret.setDegenerate(ndata == 0 || !Double.isFinite(statistics.getMax()) || !Double.isFinite(statistics.getMax()));
		ret.setNodataPercentage((double) nndat / (double) tdata);
		ret.setRange(Arrays.asList(statistics.getMin(), statistics.getMax()));
		ret.setValueCount(ndata + nndat);
		ret.setMean(statistics.getMean());
		ret.setVariance(statistics.getVariance());
		ret.setStandardDeviation(statistics.getStandardDeviation());
		ret.setSingleValued(statistics.getMax() == statistics.getMin());

		if (ret.getNodataPercentage() < 1) {
			Builder histogram = Histogram.builder(statistics.getMin(), statistics.getMax(),
					state.getDataKey() == null ? 10 : state.getDataKey().size());
			for (Iterator<Double> it = state.iterator(locator, Double.class); it.hasNext();) {
				Double d = it.next();
				if (d != null) {
					histogram.add(d);
				}
			}
			ret.setHistogram(histogram.build());
		}

		return ret;
	}

	/*
	 * Non-API - sync namespace. TODO check equivalent in Models.
	 */
	public void registerNamespace(Namespace ns, Monitor monitor) {
		// TODO Auto-generated method stub
	}

	public static Aggregation getAggregator(IObservable observable) {
		Aggregation ret = Aggregation.MAJORITY;
		if (observable.getObservationType() == ObservationType.QUANTIFICATION) {
			ret = Aggregation.MEAN;
			if (observable.isExtensive(Concepts.c(NS.SPACE_DOMAIN))) {
				ret = Aggregation.SUM;
			}
		}
		return ret;
	}

	public ObservationReference createArtifactDescriptor(IObservation observation, IObservation parent,
			ILocator locator, int childLevel, boolean collapseSiblings, boolean isMain) {

		ObservationReference ret = new ObservationReference();

		ret.setEmpty(observation.isEmpty());

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
		}

		ret.setMain(isMain);
		ret.setCreationTime(observation.getTimestamp());
		ret.setPreviouslyNotified(((Artifact) observation).isNotified());
		ret.setLastUpdate(((Observation) observation).getLastUpdate());

		if (isMain && observation instanceof Observation && !((Observation) observation).isMain()) {
			((Observation) observation).setMain(true);
		} else if (((Observation) observation).isMain()) {
			ret.setMain(true);
		}

		if (locator != null) {
			observation = observation.at(locator);
		}

		ISubject rootSubject = ((Observation) observation).getRuntimeContext().getRootSubject();
		if (rootSubject != null) {
			ret.setRootContextId(rootSubject.getId());
		}

		ret.setId(observation.getId());
		ret.setUrn(observation.getUrn());
		ret.setParentId(parent == null ? null : parent.getId());

		ret.setLabel(observation instanceof IDirectObservation ? ((IDirectObservation) observation).getName()
				: observation.getObservable().getLocalName());
		ret.setLabel(StringUtils.capitalize(ret.getLabel().replaceAll("_", " ")));
		if (observation.getObservable().getUnit() != null) {
			ret.setLabel(ret.getLabel() + " [" + ((Unit) observation.getObservable().getUnit()).toUTFString() + "]");
		} else if (observation.getObservable().getCurrency() != null) {
			ret.setLabel(ret.getLabel() + " [" + observation.getObservable().getCurrency() + "]");
		} else if (observation.getObservable().getRange() != null) {
			ret.setLabel(ret.getLabel() + " [" + observation.getObservable().getRange().getLowerBound() + " to "
					+ observation.getObservable().getRange().getUpperBound() + "]");
		}

		ret.setObservable(observation.getObservable().getDefinition());
		if (ret.getObservable() == null) {
			ret.setObservable("Quantity has no semantics associated");
		}

		ret.setChildrenCount(observation instanceof IDirectObservation && !observation.isEmpty()
				? ((IDirectObservation) observation).getChildren(IObservation.class).size()
				: 0);
		ret.setSiblingCount(observation.groupSize());
		ret.getSemantics().addAll(((Concept) observation.getObservable().getType()).getTypeSet());

		ISpace space = ((IScale) observation.getGeometry()).getSpace();
		ITime time = ((IScale) observation.getGeometry()).getTime();

		/*
		 * This is a new context, which redefines the current scale.
		 */
		if (parent == null) {
			ScaleReference scaleReference = new ScaleReference();
			if (space != null) {
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
							sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS) + " " + resolution.getSecond());
					scaleReference.setResolutionDescription(
							sunit.convert(resolution.getFirst(), Units.INSTANCE.METERS) + " " + resolution.getSecond());

				} else {

					// use the grid.
					Unit unit = Unit.create(resolution.getSecond());
					double cw = unit.convert(grid.getCell(0).getStandardizedWidth(), Units.INSTANCE.METERS)
							.doubleValue();
					scaleReference.setSpaceUnit(unit.toString());
					scaleReference.setSpaceResolution(grid.getCellWidth());
					scaleReference.setSpaceResolutionConverted(cw);
					scaleReference.setSpaceResolutionDescription(
							NumberFormat.getInstance().format(cw) + " " + resolution.getSecond());
					scaleReference.setResolutionDescription(
							NumberFormat.getInstance().format(cw) + " " + resolution.getSecond());

				}
				scaleReference.setSpaceScale(scaleRank);
			}
			if (time != null) {
				// TODO time
			}
			ret.setScaleReference(scaleReference);
		}

		// fill in spatio/temporal info and mode of visualization
		if (space != null) {

			ret.getMetadata().put("Total area",
					NumberFormat.getInstance().format(space.getShape().getArea(Units.INSTANCE.SQUARE_KILOMETERS))
							+ " km2");

			/*
			 * shapes can be huge and only make sense for direct observations, as states get
			 * the same geometry as their parents.
			 * 
			 * TODO fiddle with the parameters to simplifyIfNecessary until interaction is
			 * smooth.
			 * 
			 * TODO these should also be optional settings.
			 */
			if (observation instanceof IDirectObservation) {
				String shape = ((Shape) space.getShape()).simplifyIfNecessary(1000, 2000).getJTSGeometry().toString();
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
						NumberFormat.getInstance().format(grid.getCellWidth()) + " x "
								+ NumberFormat.getInstance().format(grid.getCellHeight()) + " "
								+ grid.getProjection().getUnits());
			}

			/*
			 * check export formats from all adapters
			 */
			Map<String, Pair<Triple<String, String, String>, String>> formats = new LinkedHashMap<>();
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
					formats.put(capabilities.getFirst(), new Pair<>(capabilities,
							org.integratedmodelling.klab.components.network.model.Network.ADAPTER_ID));
				}
			}

			/*
			 * For now only one adapter is kept per format. Later we may offer the option by
			 * using a set instead of a map and implementing equals() for ExportFormat to
			 * check all three.
			 */
			for (String format : formats.keySet()) {
				Pair<Triple<String, String, String>, String> data = formats.get(format);
				ret.getExportFormats().add(new ExportFormat(data.getFirst().getSecond(), format, data.getSecond(),
						data.getFirst().getThird()));
			}

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

		if (observation instanceof IDirectObservation) {
			ret.setChildCount(observation.isEmpty() ? 0
					: ((IDirectObservation) observation).getChildren(IObservation.class).size());
		}

		if (observation instanceof IDirectObservation && !observation.isEmpty() && (childLevel < 0 || childLevel > 0)) {
			Set<ObservationGroup> groups = new HashSet<>();
			for (IObservation child : ((IDirectObservation) observation).getChildren(IObservation.class)) {

				/*
				 * if collapseSiblings, only add one representative sibling per type.
				 */
				if (collapseSiblings && ((Observation) child).getGroup() != null) {
					if (groups.contains(((Observation) child).getGroup())) {
						continue;
					} else {
						groups.add(((Observation) child).getGroup());
					}
				}

				ret.getChildren().add(createArtifactDescriptor(child, observation, locator,
						childLevel > 0 ? childLevel-- : childLevel, collapseSiblings, false));
			}
		}

		if (observation instanceof IState) {

			if (((IState) observation).getTable() != null) {
				ret.getGeometryTypes().add(GeometryType.TABLE);
			}

			StateSummary summary = getStateSummary((IState) observation, locator);

			ret.setValueCount(observation.getScale().size());
			if (observation.getScale().size() == 1) {
				ret.setLiteralValue(formatValue(observation.getObservable(),
						((IState) observation).get(observation.getScale().getLocator(0))));
			} else if (observation.getScale().getSpace().size() > 1 && observation.getScale().getSpace().isRegular()) {

				if (!summary.isDegenerate()) {
					ret.getGeometryTypes().add(GeometryType.COLORMAP);
				}
			}

			DataSummary ds = new DataSummary();

			ds.setNodataProportion(summary.getNodataPercentage());
			ds.setMinValue(summary.getRange().get(0));
			ds.setMaxValue(summary.getRange().get(1));
			if (summary.getHistogram() != null && !summary.isSingleValued()) {
				for (int bin : summary.getHistogram().getBins()) {
					ds.getHistogram().add(bin);
				}
			}
			double step = (summary.getRange().get(1) - summary.getRange().get(0)) / (double) ds.getHistogram().size();

			for (int i = 0; i < ds.getHistogram().size(); i++) {
				// TODO use labels for categories
				String lower = NumberFormat.getInstance().format(summary.getRange().get(0) + (step * i));
				String upper = NumberFormat.getInstance().format(summary.getRange().get(0) + (step * (i + 1)));
				ds.getCategories().add(lower + " to " + upper);
			}

			ret.setDataSummary(ds);
		}

		/*
		 * activity that generated us.
		 */
		if (observation.getGenerator() != null) {
			ret.setTaskId(observation.getGenerator().getId());
		}

		ret.getActions().add(ActionReference.separator());
		// ACTIONS diocan
		// ret.getActions().add(new ActionReference("Show metadata", "ShowMetadata"));

		return ret;
	}

	public String formatValue(IObservable observable, Object object) {

		if (object instanceof IConcept) {
			object = Concepts.INSTANCE.getDisplayName((IConcept) object);
		} else if (object instanceof Number) {
			object = NumberFormat.getNumberInstance().format(((Number) object).doubleValue());
		}

		String ret = observable.getLocalName() + ": " + object;

		if (observable.getUnit() != null) {
			ret += " " + observable.getUnit();
		} else if (observable.getCurrency() != null) {
			ret += " " + observable.getCurrency();
		}

		return ret;
	}

	public Observer makeROIObserver(final SpatialExtent regionOfInterest, Namespace namespace, IMonitor monitor) {
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName(Nominatim.INSTANCE.geocode(regionOfInterest));
		observable.setOptional(true);
		if (namespace == null) {
			namespace = Namespaces.INSTANCE.getNamespace(observable.getNamespace());
		}
		return new Observer(regionOfInterest, observable, (Namespace) namespace);
	}

	public Observer makeROIObserver(final Shape shape, Namespace namespace, IMonitor monitor) {
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName(Nominatim.INSTANCE.geocode(shape.getEnvelope()));
		observable.setOptional(true);
		if (namespace == null) {
			namespace = Namespaces.INSTANCE.getNamespace(observable.getNamespace());
		}
		return new Observer(shape, observable, (Namespace) namespace);
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
	 * Produce a classification that discretizes the range of the passed numeric
	 * state. If the state is all no-data, return null without error.
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
			@Nullable String adapterId, IMonitor monitor) {

		IResourceAdapter adapter = null;
		if (adapterId == null) {
			for (IResourceAdapter a : Resources.INSTANCE.getResourceAdapters()) {
				for (Triple<String, String, String> capabilities : a.getImporter().getExportCapabilities(observation)) {
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

		return adapter.getImporter().exportObservation(file, observation, locator, outputFormat, monitor);
	}

	public boolean isData(Object o) {
		return o != null && !(o instanceof Number && Double.isNaN(((Number) o).doubleValue()));
	}

	public boolean isNodata(Object o) {
		return !isData(o);
	}

}

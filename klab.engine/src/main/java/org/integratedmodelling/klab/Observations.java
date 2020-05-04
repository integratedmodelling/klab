package org.integratedmodelling.klab;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.atteo.evo.inflector.English;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
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
import org.integratedmodelling.klab.components.geospace.processing.osm.Geocoder;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroupView;
import org.integratedmodelling.klab.data.classification.Discretization;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
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
		return Resolver.create(null).resolve(urn, session, scenarios);
	}

	@Override
	public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios) throws KlabException {
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
	public IState getStateView(IState state, IScale scale, IContextualizationScope context) {
		return new RescalingState(state, (Scale) scale, (IRuntimeScope) context);
	}

	@Override
	public IState getStateViewAs(IObservable observable, IState state, IScale scale, IContextualizationScope context) {
		return new RescalingState(state, observable, (Scale) scale, (IRuntimeScope) context);
	}

	/**
	 * Return the summary for the data in a state, computing it if necessary.
	 * 
	 * FIXME caching strategy currently just fills up forever - must shed caches as
	 * transitions advance.
	 * 
	 * @param state   a state
	 * @param locator the subsetting locator for the wanted data, or null if global
	 *                summaries are required.
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

		for (Iterator<Number> it = state.iterator(locator, Number.class); it.hasNext();) {
			tdata++;
			Number d = it.next();
			if (d != null) {
				ndata++;
				statistics.addValue(d.doubleValue());
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
			for (Iterator<Number> it = state.iterator(locator, Number.class); it.hasNext();) {
				Number d = it.next();
				if (d != null) {
					histogram.add(d.doubleValue());
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

	public ObservationReference createArtifactDescriptor(IObservation observation, IObservation parent,
			ILocator locator, int childLevel) {
		return createArtifactDescriptor(observation, parent, locator, childLevel, null);
	}

	public ObservationReference createArtifactDescriptor(IObservation observation, IObservation parent,
			ILocator locator, int childLevel, String viewId) {

		ObservationReference ret = new ObservationReference();

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

		ISubject rootSubject = ((Observation) observation).getRuntimeScope().getRootSubject();
		if (rootSubject != null) {
			ret.setRootContextId(rootSubject.getId());
		}

		ret.setId(observation.getId());
		ret.setContextId(((Observation) observation).getObservationContextId());
		ret.setUrn(observation.getUrn());
		ret.setParentId(parent == null ? null : parent.getId());
		ret.setAlive(((Observation) observation).isAlive());
		ret.setLabel(getDisplayLabel(observation));
		ret.setDynamic(((Observation) observation).isDynamic());
		ret.setObservable(observation.getObservable().getDefinition());
		if (ret.getObservable() == null) {
			ret.setObservable("Quantity has no semantics associated");
		}

		ret.setChildrenCount(observation instanceof IDirectObservation && !observation.isEmpty()
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
					scaleReference
							.setSpaceResolutionDescription(String.format("%.1f", cw) + " " + resolution.getSecond());
					scaleReference.setResolutionDescription(String.format("%.1f", cw) + " " + resolution.getSecond());

				}
				scaleReference.setSpaceScale(scaleRank);
			}
			if (time != null) {

				scaleReference.setTimeScale(time.getScaleRank());
				scaleReference.setStart(time.getStart() == null ? 0 : time.getStart().getMilliseconds());
				scaleReference.setEnd(time.getEnd() == null ? 0 : time.getEnd().getMilliseconds());
				scaleReference.setTimeResolutionDescription(time.getResolution() == null ? null
						: time.getResolution().getMultiplier() + " "
								+ StringUtils.capitalize(time.getResolution().getType().name()));
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
			if (observation instanceof IDirectObservation
					&& !(observation instanceof ObservationGroup || observation instanceof ObservationGroupView)) {
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
						NumberFormat.getInstance().format(grid.getCell(0).getStandardizedWidth()) + " x "
								+ NumberFormat.getInstance().format(grid.getCell(0).getStandardizedWidth()) + " m");
			}

			/*
			 * compute available export formats. This may change after an update so it's done also in
			 * ObservationChange.
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

		if (observation instanceof IDirectObservation) {
			/*
			 * physical parent
			 */
			if (observation instanceof DirectObservation) {
				if (viewId != null) {
					ret.setParentArtifactId(viewId);
				} else {
					ret.setParentArtifactId(((DirectObservation) observation).getGroup() == null ? ret.getParentId()
							: ((DirectObservation) observation).getGroup().getId());
				}
			}
		}
		if (observation instanceof IDirectObservation && !observation.isEmpty() && (childLevel < 0 || childLevel > 0)) {

			for (IArtifact child : observation.getChildArtifacts()) {
				if (child instanceof IObservation) {
					ret.getChildren()
							.add(createArtifactDescriptor((IObservation) child, observation, locator,
									childLevel > 0 ? (childLevel - 1) : childLevel,
									observation instanceof ObservationGroupView ? observation.getId() : null));
				}
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
						((IState) observation).get(observation.getScale().at(0))));
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

			ret = (observation instanceof IDirectObservation ? ((IDirectObservation) observation).getName()
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

	public Observer makeROIObserver(final SpatialExtent regionOfInterest, Namespace namespace, IMonitor monitor) {
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName(Geocoder.INSTANCE.geocode(regionOfInterest));
		observable.setOptional(true);
		if (namespace == null) {
			namespace = Namespaces.INSTANCE.getNamespace(observable.getNamespace());
		}
		return new Observer(regionOfInterest, observable, (Namespace) namespace);
	}

	public Observer makeROIObserver(final Shape shape, Namespace namespace, IMonitor monitor) {
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName(Geocoder.INSTANCE.geocode(shape.getEnvelope()));
		observable.setOptional(true);
		if (namespace == null) {
			namespace = Namespaces.INSTANCE.getNamespace(observable.getNamespace());
		}
		return new Observer(shape, observable, (Namespace) namespace);
	}

	public Observer makeROIObserver(String name, IShape shape, IMetadata metadata) {
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName(Geocoder.INSTANCE.geocode(shape.getEnvelope()));
		observable.setOptional(true);
		Observer ret = new Observer((Shape) shape, observable,
				Namespaces.INSTANCE.getNamespace(observable.getNamespace()));
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

}

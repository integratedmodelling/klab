package org.integratedmodelling.klab;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
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
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
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
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ActionReference;
import org.integratedmodelling.klab.rest.DataSummary;
import org.integratedmodelling.klab.rest.Histogram;
import org.integratedmodelling.klab.rest.Histogram.Builder;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Utils;

public enum Observations implements IObservationService {

	INSTANCE;

	Map<ILocator, Map<String, StateSummary>> summaryCache = new HashMap<>();

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

	/**
	 * Return the summary for the data in a state, computing it if necessary.
	 * 
	 * FIXME caching strategy currently just fills up forever - must lose caches as
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

		int ndata = 0;
		int nndat = 0;

		ret.setStateTimestamp(((Observation) state).getTimestamp());

		SummaryStatistics statistics = new SummaryStatistics();

		for (Iterator<Double> it = state.iterator(locator, Double.class); it.hasNext();) {
			Double d = it.next();
			if (d != null) {
				ndata++;
				statistics.addValue(d);
			} else {
				nndat++;
			}
		}

		ret.setDegenerate(ndata == 0 || !Double.isFinite(statistics.getMax()) || !Double.isFinite(statistics.getMax()));
		ret.setNodataPercentage((double) nndat / (double) ndata);
		ret.setRange(Arrays.asList(statistics.getMin(), statistics.getMax()));
		ret.setValueCount(ndata + nndat);
		ret.setMean(statistics.getMean());
		ret.setVariance(statistics.getVariance());
		ret.setStandardDeviation(statistics.getStandardDeviation());

		if (ret.getNodataPercentage() > 0) {
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
			ret = Aggregation.AVERAGE;
			if (observable.isExtensive(Concepts.c(NS.SPACE_DOMAIN))) {
				ret = Aggregation.SUM;
			}
		}
		return ret;
	}

	public ObservationReference createArtifactDescriptor(IObservation observation, IObservation parent,
			ILocator locator, int childLevel, boolean isMain) {

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

		if (locator != null) {
			observation = observation.at(locator);
		}

		ret.setId(observation.getId());
		ret.setUrn(observation.getUrn());
		ret.setParentId(parent == null ? null : parent.getId());
		
		ret.setLabel(observation instanceof IDirectObservation ? ((IDirectObservation) observation).getName()
				: observation.getObservable().getLocalName());
		ret.setLabel(StringUtils.capitalize(ret.getLabel().replaceAll("_", " ")));
		if (observation.getObservable().getUnit() != null) {
			ret.setLabel(ret.getLabel() + " [" + ((Unit)observation.getObservable().getUnit()).toUTFString() + "]");
		} else if (observation.getObservable().getCurrency() != null) {
			ret.setLabel(ret.getLabel() + " [" + observation.getObservable().getCurrency() + "]");
		} else if (observation.getObservable().getRange() != null) {
			ret.setLabel(ret.getLabel() + " [" + observation.getObservable().getRange().getLowerBound() + " to "
					+ observation.getObservable().getRange().getUpperBound() + "]");
		}
		
		ret.setObservable(observation.getObservable().getDefinition());
		ret.setSiblingCount(observation.groupSize());
		ret.getSemantics().addAll(((Concept) observation.getObservable().getType()).getTypeSet());

		ISpace space = ((IScale) observation.getGeometry()).getSpace();
		ITime time = ((IScale) observation.getGeometry()).getTime();

		// fill in spatio/temporal info and mode of visualization
		if (space != null) {

			/*
			 * shapes can be huge and only make sense for direct observations, as states get
			 * the same geometry as their parents.
			 */
			if (observation instanceof IDirectObservation) {
				String shape = ((Shape) space.getShape()).getJTSGeometry().toString();
				ret.setEncodedShape(shape);
				ret.setSpatialProjection(space.getProjection().getSimpleSRS());
				ret.setShapeType(space.getShape().getGeometryType());
			}

			GeometryType gtype = GeometryType.SHAPE;
			if (observation instanceof IState && space.isRegular() && space.size() > 1) {
				gtype = GeometryType.RASTER;
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

		if (observation instanceof IDirectObservation && (childLevel < 0 || childLevel > 0)) {
			// children. No child is ever main.
			for (IObservation child : ((IDirectObservation) observation).getChildren(IObservation.class)) {
				ret.getChildren().add(createArtifactDescriptor(child, observation, locator,
						childLevel > 0 ? childLevel-- : childLevel, false));
			}
		}

		if (observation instanceof IState) {

			StateSummary summary = getStateSummary((IState) observation, locator);

			ret.setValueCount(observation.getScale().size());
			if (observation.getScale().size() == 1) {
				ret.setLiteralValue(formatValue(observation.getObservable(),
						((IState) observation).get(observation.getScale().getLocator(0))));
			} else if (observation.getScale().getSpace().size() > 1 && observation.getScale().getSpace().isRegular()) {
				ret.getActions().add(new ActionReference("Export as GeoTiff", "ExportGeotiff"));
			}

			DataSummary ds = new DataSummary();
			
			ds.setNodataProportion(summary.getNodataPercentage());
			ds.setMinValue(summary.getRange().get(0));
			ds.setMaxValue(summary.getRange().get(1));
			
			ret.setDataSummary(ds);

			// FIXME REMOVE this is just for testing
		}

		ret.getActions().add(ActionReference.separator());
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

	public Observer makeROIObserver(final SpatialExtent regionOfInterest, final Namespace namespace, IMonitor monitor) {
		// TODO use configured concept from worldview!
		final Observable observable = Observable.promote(Worldview.getGeoregionConcept());
		observable.setName("Region of interest");
		observable.setOptional(true);
		return new Observer(regionOfInterest, observable, (Namespace) namespace);
	}

}

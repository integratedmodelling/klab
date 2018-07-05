package org.integratedmodelling.klab;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.model.INamespace;
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
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IObservationService;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.Histogram;
import org.integratedmodelling.klab.rest.Histogram.Builder;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;

public enum Observations implements IObservationService {

	INSTANCE;

	@Override
	public IDataflow<IArtifact> resolve(String urn, ISession session, String[] scenarios) throws KlabException {
		return Resolver.INSTANCE.resolve(urn, session, scenarios);
	}

	@Override
	public IDataflow<IArtifact> resolve(String urn, ISubject context, String[] scenarios) throws KlabException {
		return Resolver.INSTANCE.resolve(urn, context, scenarios);
	}

	@Override
	public void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException {
		// TODO remove all artifacts from local kbox
	}

	@Override
	public void index(IObserver observer, IMonitor monitor) throws KlabException {
		// TODO
	}

	@Override
	public IState getStateView(IState state, IScale scale, IComputationContext context) {
		return new RescalingState(state, (Scale) scale, (IRuntimeContext) context);
	}

	/**
	 * Return the summary for the data in a state, computing it if necessary.
	 * 
	 * @param state
	 *            a state
	 * @param locator
	 *            the subsetting locator for the wanted data, or null if global
	 *            summaries are required.
	 * @return the state summary
	 */
	public StateSummary getStateSummary(IState state, ILocator locator) {

//		if (state.getMetadata().containsKey(State.STATE_SUMMARY_METADATA_KEY + locator)) {
//			return state.getMetadata().get(State.STATE_SUMMARY_METADATA_KEY, StateSummary.class);
//		}

		StateSummary ret = computeStateSummary(state, locator);

//		state.getMetadata().put(State.STATE_SUMMARY_METADATA_KEY + locator, ret);

		return ret;
	}

	private StateSummary computeStateSummary(IState state, ILocator locator) {

		StateSummary ret = new StateSummary();

		int ndata = 0;
		int nndat = 0;

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

		ret.setNodataPercentage((double) nndat / (double) ndata);
		ret.setRange(Arrays.asList(statistics.getMin(), statistics.getMax()));
		ret.setValueCount(ndata + nndat);

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
			ILocator locator, int childLevel) {

		ObservationReference ret = new ObservationReference();

		if (observation instanceof ISubject) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.SUBJECT);
		} else if (observation instanceof IState) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.STATE);
		} else if (observation instanceof IProcess) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.PROCESS);
		} else if (observation instanceof IEvent) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.EVENT);
		} else if (observation instanceof IConfiguration) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.CONFIGURATION);
		} else if (observation instanceof IRelationship) {
			ret.setObservationType(org.integratedmodelling.klab.rest.ObservationReference.ObservationType.RELATIONSHIP);
		}
		
		if (locator != null) {
			observation = observation.at(locator);
		}

		ret.setId(observation.getId());
		ret.setUrn(observation.getUrn());
		ret.setParentId(parent == null ? null : parent.getId());
		ret.setLabel(observation instanceof IDirectObservation ? ((IDirectObservation) observation).getName()
				: observation.getObservable().getLocalName());
		ret.setObservable(observation.getObservable().getType().getDefinition());
		ret.setSiblingCount(observation.groupSize());
		ret.getSemantics().addAll(((Concept) observation.getObservable().getType()).getTypeSet());

		ISpace space = ((IScale) observation.getGeometry()).getSpace();
		ITime time = ((IScale) observation.getGeometry()).getTime();

		// fill in spatio/temporal info and mode of visualization
		if (space != null) {

			ret.setShapeType(space.getShape().getGeometryType());

			String shape = space.getShape().toString();
			String pcode = null;
			if (shape.startsWith("EPSG:") || shape.startsWith("urn:")) {
				int n = shape.indexOf(' ');
				pcode = shape.substring(0, n);
				shape = shape.substring(n + 1);
			}
			ret.setEncodedShape(shape);
			ret.setSpatialProjection(pcode);

			GeometryType gtype = GeometryType.SHAPE;
			if (observation instanceof IState && space.isRegular() && space.size() > 1) {
				gtype = GeometryType.RASTER;
			}
			ret.getGeometryTypes().add(gtype);
		}

		if (time != null) {
			// TODO
		}

		if (observation instanceof IDirectObservation && (childLevel < 0 || childLevel > 0)) {
			for (IObservation child : ((IDirectObservation) observation).getChildren(IObservation.class)) {
				ret.getChildren().add(createArtifactDescriptor(child, observation, locator,
						childLevel > 0 ? childLevel-- : childLevel));
			}
		}

		if (observation instanceof IState && observation.getScale().size() == 1) {
			ret.setLiteralValue(formatValue(observation.getObservable(),
					((IState) observation).get(observation.getScale().getLocator(0))));
		}

		return ret;
	}
	

	public String formatValue(IObservable observable, Object object) {
		
		if (object instanceof IConcept) {
			object = Concepts.INSTANCE.getDisplayName((IConcept)object);
		}

		String ret = "" + object;
		
		if (observable.getUnit() != null) {
			ret += " " + observable.getUnit();
		} else if (observable.getCurrency() != null) {
			ret += " " + observable.getCurrency();
		}
		
		return ret;
	}

}

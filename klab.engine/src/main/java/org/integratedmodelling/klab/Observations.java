package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
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
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.scale.Scale;

public enum Observations implements IObservationService {

	INSTANCE;

	private static final String STATE_SUMMARY_METADATA_KEY = "metadata.keys.state_summary_";

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

		if (state.getMetadata().containsKey(STATE_SUMMARY_METADATA_KEY + locator)) {
			return state.getMetadata().get(STATE_SUMMARY_METADATA_KEY, StateSummary.class);
		}

		StateSummary ret = computeStateSummary(state, locator);

		state.getMetadata().put(STATE_SUMMARY_METADATA_KEY + locator, ret);

		return ret;
	}

	private StateSummary computeStateSummary(IState state, ILocator locator) {
		StateSummary ret = new StateSummary();
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
	
	public ObservationReference createArtifactDescriptor(IObservation observation, IObservation parent) {

		ObservationReference ret = new ObservationReference();
	
		ret.setId(observation.getId());
		ret.setUrn(observation.getUrn());
		ret.setParentId(parent == null ? null : parent.getId());
		ret.setLabel(observation instanceof IDirectObservation ? ((IDirectObservation) observation).getName()
				: observation.getObservable().getLocalName());
		ret.setObservable(observation.getObservable().getType().getDefinition());
		ret.setSiblingCount(observation.groupSize());

		ISpace space = ((IScale) observation.getGeometry()).getSpace();
		ITime time = ((IScale) observation.getGeometry()).getTime();

		// fill in spatio/temporal info and mode of visualization
		if (space != null) {
			ret.setShapeType(space.getShape().getGeometryType());
			ret.setEncodedShape(space.getShape().toString());
			GeometryType gtype = GeometryType.SHAPE;
			if (observation instanceof IState && space.isRegular() && space.size() > 1) {
				gtype = GeometryType.RASTER;
			}
			ret.getGeometryTypes().add(gtype);
		}

		if (time != null) {
			// TODO
		}

		return ret;
	}
}

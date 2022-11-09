package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

/**
 * The state we wrap has the desired semantics but a different scale. We use
 * {@link IScaleMediator mediators} to remap value setting and getting.
 * Mediators are built as needed.
 * 
 * @author Ferd
 *
 */
public class RescalingState extends Observation implements IState, DelegatingArtifact {

	IState delegate;
	Scale newScale;
	IScale originalScale;
	Geometry originalGeometry;
	String label; // for debugging
	List<IScaleMediator> mediators = null;
	boolean conformant = false;
	private IActivity.Description observationType;
	boolean redistribute = false;

	String localId;

	public void setLocalId(String observable) {
		this.localId = observable;
	}

	public RescalingState(IState state, Scale newScale, IRuntimeScope context) {
		super(new Observable((Observable) state.getObservable()), newScale, context);
		this.delegate = state;
		this.newScale = newScale.mergeContext(state.getScale());
		this.originalScale = state.getScale();
		this.originalGeometry = ((Scale) state.getScale()).asGeometry();
		this.observationType = state.getObservable().getDescriptionType();
		this.label = newScale.getSpace() == null ? "" : newScale.getSpace().getEnvelope().toString();
		// TODO check if we need to sum in aggregation. Depends on the observable and on
		// the relationship between the extents (e.g spatially distributed vs. not)
		// this.redistribute = ...
	}

	public RescalingState(IState state, IObservable newObservable, Scale newScale, IRuntimeScope context) {
		super(new Observable((Observable) newObservable), newScale, context);
		this.delegate = state;
		this.newScale = newScale.mergeContext(state.getScale());;
		this.originalScale = state.getScale();
		this.originalGeometry = ((Scale) state.getScale()).asGeometry();
		this.observationType = newObservable.getDescriptionType();
		this.label = newScale.getSpace() == null ? "" : newScale.getSpace().getEnvelope().toString();
		// TODO check if we need to sum in aggregation. Depends on the observable and on
		// the relationship between the extents (e.g spatially distributed vs. not)
		// this.redistribute = ...
	}

	public Object get(ILocator index) {

//		long offset = this.newScale.getOffset(index);
//
//		if (!this.newScale.isCovered(offset)) {
//			return null;
//		}

		// sets the conformant flag as a side-effect, call now
		if (mediators == null) {
			mediators = getMediators((Scale) this.delegate.getScale(), this.newScale);
		}

		if (conformant) {
//
//			long[] offsets = this.newScale.getExtentIndex(offset);
//			for (int i = 0; i < mediators.size(); i++) {
//				offsets[i] = mediators.get(i).mapConformant(offsets[i]);
//			}

			ILocator locator = originalScale.at(index);
			return locator == null ? null : delegate.get(locator);
		}

		return reduce(index, mediators);
	}

	private synchronized List<IScaleMediator> getMediators(Scale original, Scale target) {
		List<IScaleMediator> mediators = new ArrayList<>();

		conformant = true;
		for (IExtent originalExtent : original.getExtents()) {
			IExtent targetExtent = target.getDimension(originalExtent.getType());
			if (targetExtent != null) {
				IScaleMediator mediator = originalExtent.getMediator(targetExtent);
				if (!mediator.isConformant()) {
					conformant = false;
				}
				mediators.add(mediator);
			}
		}
		return mediators;
	}

	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	public long set(ILocator index, Object value) {

		// this may overwrite good data set previously, so we skip it.
		if (Observations.INSTANCE.isNodata(value)) {
			return -1;
		}
		
		// sets the conformant flag as a side-effect, call now
		if (mediators == null) {
			mediators = getMediators((Scale)originalScale, this.newScale);
		}

		if (conformant) {

			IScale loc = originalScale.at(index);
			if (loc != null) {
				return delegate.set(loc, value);
			} 
			
		} else {
			map(index, mediators, value);
		}

		return -1;
	}

	private void map(ILocator locator, List<IScaleMediator> mediators, Object value) {

		Propagator propagator = new Propagator();
		for (int i = 0; i < mediators.size(); i++) {
			for (Pair<Long, Double> mapped : mediators.get(i).map(newScale.getOffset(locator))) {
				propagator.add(i, mapped.getFirst(), mapped.getSecond());
			}
		}
		propagator.propagate(value);
	}

	private Object reduce(ILocator locator, List<IScaleMediator> mediators) {

		Aggregator aggregator = new Aggregator();
		for (int i = 0; i < mediators.size(); i++) {
			for (Pair<Long, Double> mapped : mediators.get(i).map(newScale.getOffset(locator))) {
				aggregator.add(i, mapped.getFirst(), mapped.getSecond());
			}
		}
		return aggregator.aggregate();

	}

	static class ExtentLocation {

		long offset;
		double weight;

		ExtentLocation(long offset, double weight) {
			this.offset = offset;
			this.weight = weight;
		}
	}

	/*
	 * Aggregate contents of original state at the cartesian product of the
	 * collected dimensions.
	 */
	class Aggregator {

		List<List<ExtentLocation>> locations = new ArrayList<>();

		double sum;
		Map<Object, Double> dominance = new HashMap<>();
		int nodata = 0;
		double count = 0;

		Aggregator() {
			for (int i = 0; i < delegate.getScale().getExtentCount(); i++) {
				locations.add(new ArrayList<>());
			}
		}

		Object aggregate() {

			for (CartesianProductIterator it = new CartesianProductIterator(locations); it.hasNext();) {
				Pair<long[], Double> index = it.next();
				if (index.getSecond() > 0) {
					// FIXIT the ridiculous extended form with the unnecessary cast is due to a likely JVM bug that calls
					// the locator version of at() instead of the varargs.
					ILocator loc = originalGeometry.at((long[])index.getFirst());
					double weight = index.getSecond();
					Object value = delegate.get(loc);
					addValue(value, weight);
				}
			}
			return computeAggregation();
		}

		private Object computeAggregation() {

			Object ret = null;

			switch (observationType) {
			case CATEGORIZATION:
			case VERIFICATION:
				// TODO handle weight
				double cur = 0;
				for (Object o : dominance.keySet()) {
					if (ret == null || dominance.get(o) > cur) {
						ret = o;
						cur = dominance.get(o);
					}
				}
				break;
			case QUANTIFICATION:
				// TODO handle weight
				ret = redistribute ? sum : sum / count;
				break;
			default:
				break;
			}

			return ret;
		}

		private void addValue(Object object, Double weight) {

			if (object == null || (object instanceof Number && Double.isNaN(((Number) object).doubleValue()))) {
				nodata++;
				return;
			}

			switch (observationType) {
			case CATEGORIZATION:
			case VERIFICATION:
				Double n = dominance.get(object);
				dominance.put(object, n == null ? weight : n + weight);
				break;
			case QUANTIFICATION:
				sum += ((Number) object).doubleValue() * weight;
				count += weight;
				break;
			default:
				return;
			}

		}

		public void add(int dimension, long offset, Double weight) {
			locations.get(dimension).add(new ExtentLocation(offset, weight));
		}
	}

	/*
	 * Propagate value to original state to the cartesian product of the collected
	 * dimensions.
	 */
	class Propagator {

		List<List<ExtentLocation>> locations = new ArrayList<>();

		Propagator() {
			for (int i = 0; i < delegate.getScale().getExtentCount(); i++) {
				locations.add(new ArrayList<>());
			}
		}

		void propagate(Object value) {
			for (CartesianProductIterator it = new CartesianProductIterator(locations); it.hasNext();) {
				Pair<long[], Double> index = it.next();
				if (index.getSecond() > 0) {
					propagateValue(value, originalGeometry.at(index.getFirst()), index.getSecond(), it.size());
				}
			}
		}

		private void propagateValue(Object value, ILocator locator, double weight, long valueCount) {
			// TODO propagate the n-th part of the value
		}

		public void add(int dimension, long offset, Double weight) {
			locations.get(dimension).add(new ExtentLocation(offset, weight));
		}
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return DataIterator.create(this, getScale().at(index), cls);
	}

	// Remaining functionality is delegated to original state

	@Override
	public long size() {
		return delegate.size();
	}

	@Override
	public IArtifact.Type getType() {
		return delegate.getType();
	}

	@Override
	public IState as(IArtifact.Type type) {
		if (delegate.getType() == type) {
			return this;
		}
		return new RescalingState(delegate.as(type), newScale, getScope());
	}

	@Override
	public IDataKey getDataKey() {
		return delegate.getDataKey();
	}

	@Override
	public IState at(ILocator locator) {
		return this; // TODO further rescaling! new RescalingState(delegate.at(locator),
						// rescaledNewScale, getRuntimeContext());
	}

	@Override
	public IState in(IValueMediator mediator) {
		return MediatingState.mediateIfNecessary(this, mediator);
	}

//	public ISubjectiveState reinterpret(IDirectObservation observers) {
//		return null;
//	}

	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		if (newScale.size() == 1) {
			return get(newScale.at(0), cls);
		}
		throw new KlabUnimplementedException(
				"aggregation of rescaled states is unimplemented - please submit a request");
	}

	@Override
	public Object aggregate(ILocator... locators) {
		if (newScale.size() == 1) {
			return get(newScale.at(0), Utils.getClassForType(delegate.getType()));
		}
		if (locators == null) {
			List<Object> values = new ArrayList<>();
			for (ILocator locator : getScale()) {
				values.add(get(locator));
			}
			AggregationUtils.aggregate(values, AggregationUtils.getAggregation(getObservable()),
					getScope().getMonitor());
		}
		throw new KlabUnimplementedException(
				"aggregation of rescaled states is unimplemented - please submit a request");
	}

	@Override
	public void fill(Object value) {
		if (newScale.size() == 1) {
			set(newScale.at(0), value);
		}
		for (ILocator locator : getScale()) {
			set(locator, value);
		}
	}

	public void summarize() {
		System.err.println("SUMMARY for rescaling state " + localId + " delegating to " + getObservable().getName());
		System.err.println("local scale:  " + newScale);
		System.err.println("target scale:  " + originalGeometry);
	}
	
	@Override
	public void finalizeTransition(IScale scale) {
		setContextualized(true);
		if (scale.getTime() != null && scale.getTime().getTimeType() != ITime.Type.INITIALIZATION) {
			setDynamic(true);
		}
	}

	@Override
	public String dump() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getDelegate() {
		return delegate;
	}

	@Override
	public long getTimestamp() {
		return delegate.getTimestamp();
	}

	@Override
	public long getLastUpdate() {
		return delegate.getLastUpdate();
	}

	@Override
	public long[] getUpdateTimestamps() {
		return delegate.getUpdateTimestamps();
	}
	
	

}

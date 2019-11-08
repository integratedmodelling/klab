package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
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
public class RescalingState extends Observation implements IState {

	IState delegate;
	Scale newScale;
	Geometry originalGeometry;

	List<IScaleMediator> mediators = null;
	boolean conformant = false;
	private IActivity.Description observationType;
	boolean redistribute = false;

	// debugging
//	AtomicInteger mediatorCalls = new AtomicInteger();
//	AtomicLong minLocalOffset = new AtomicLong();
//	AtomicLong maxLocalOffset = new AtomicLong();
//	AtomicLong minTargetOffset = new AtomicLong();
//	AtomicLong maxTargetOffset = new AtomicLong();
//	AtomicLong totalSetCalls = new AtomicLong();
//	AtomicLong workedSetCalls = new AtomicLong();
	String localId;

	public void setLocalId(String observable) {
		this.localId = observable;
	}

	public RescalingState(IState state, Scale newScale, IRuntimeScope context) {
		super(new Observable((Observable) state.getObservable()), newScale, context);
		this.delegate = state;
		this.newScale = newScale;
		this.originalGeometry = ((Scale) state.getScale()).asGeometry();
		this.observationType = state.getObservable().getDescription();
//		minLocalOffset.set(Long.MAX_VALUE);
//		minTargetOffset.set(Long.MAX_VALUE);
		// TODO check if we need to sum in aggregation. Depends on the observable and on
		// the relationship between the extents (e.g spatially distributed vs. not)
		// this.redistribute = ...
	}

	public RescalingState(IState state, IObservable newObservable, Scale newScale, IRuntimeScope context) {
		super(new Observable((Observable) newObservable), newScale, context);
		this.delegate = state;
		this.newScale = newScale;
		this.originalGeometry = ((Scale) state.getScale()).asGeometry();
		this.observationType = newObservable.getDescription();
		// TODO check if we need to sum in aggregation. Depends on the observable and on
		// the relationship between the extents (e.g spatially distributed vs. not)
		// this.redistribute = ...
	}

	public Object get(ILocator index) {

		long offset = this.newScale.getOffset(index);

//		if (minLocalOffset.get() > offset) {
//			minLocalOffset.set(offset);
//		}
//		if (maxLocalOffset.get() < offset) {
//			maxLocalOffset.set(offset);
//		}

		if (!this.newScale.isCovered(offset)) {
			return null;
		}

		if (mediators == null) {
			mediators = getMediators((Scale) this.delegate.getScale(), this.newScale);
		}

		if (conformant) {

			long[] offsets = this.newScale.getExtentIndex(offset);
			for (int i = 0; i < mediators.size(); i++) {
				offsets[i] = mediators.get(i).mapConformant(offsets[i]);
			}

			ILocator locator = originalGeometry.at(offsets);
//			long targetOffset = ((Offset) locator).linear;
//			if (minTargetOffset.get() > targetOffset) {
//				minTargetOffset.set(targetOffset);
//			}
//			if (maxTargetOffset.get() < targetOffset) {
//				maxTargetOffset.set(targetOffset);
//			}

			return delegate.get(locator);
		}

		return reduce(index, mediators);
	}

	private synchronized List<IScaleMediator> getMediators(Scale original, Scale target) {
		List<IScaleMediator> mediators = new ArrayList<>();

//		mediatorCalls.incrementAndGet();
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

//		totalSetCalls.incrementAndGet();
		long offset = this.newScale.getOffset(index);

		if (value == null) {
			return offset;
		}

//		if (minLocalOffset.get() > offset) {
//			minLocalOffset.set(offset);
//		}
//		if (maxLocalOffset.get() < offset) {
//			maxLocalOffset.set(offset);
//		}

		// may be covered by another state and have been assigned already!
		if (!this.newScale.isCovered(offset)) {
			return -1;
		}

//		workedSetCalls.incrementAndGet();

		if (mediators == null) {
			mediators = getMediators((Scale) this.delegate.getScale(), this.newScale);
		}

		if (conformant) {

			long[] offsets = this.newScale.getExtentIndex(offset);
			for (int i = 0; i < mediators.size(); i++) {
				offsets[i] = mediators.get(i).mapConformant(offsets[i]);
			}

			long targetOffset = delegate.set(originalGeometry.at(offsets), value);

//			if (minTargetOffset.get() > targetOffset) {
//				minTargetOffset.set(targetOffset);
//			}
//			if (maxTargetOffset.get() < targetOffset) {
//				maxTargetOffset.set(targetOffset);
//			}

		} else {
			map(index, mediators, value);
		}

		return offset;
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

	/**
	 * Generate the scale-wide cartesian product of the touched extent offsets,
	 * multiplying the individual weights.
	 * 
	 * @param data
	 * @return
	 */
	class CartesianProductIterator implements Iterator<Pair<long[], Double>> {

		List<List<ExtentLocation>> data;
		long[] ret;
		final MultidimensionalCursor cursor = new MultidimensionalCursor();

		long current = 0;

		CartesianProductIterator(List<List<ExtentLocation>> data) {
			this.data = data;
			this.ret = new long[data.size()];
			long[] sizes = new long[data.size()];
			for (int i = 0; i < data.size(); i++) {
				sizes[i] = data.get(i).size();
			}
			this.cursor.defineDimensions(sizes);
		}

		public long size() {
			return cursor.getMultiplicity();
		}

		@Override
		public boolean hasNext() {
			return current < cursor.getMultiplicity();
		}

		@Override
		public Pair<long[], Double> next() {

			long[] offsets = cursor.getElementIndexes(current);
			double weight = 1.0;

			for (int i = 0; i < offsets.length; i++) {
				ret[i] = data.get(i).get((int) offsets[i]).offset;
				weight *= data.get(i).get((int) offsets[i]).weight;
			}

			current++;
			return new Pair<>(ret, weight);
		}
	};

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
		return new RescalingState(delegate.as(type), newScale, getRuntimeScope());
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
		return MediatingState.getMediator(this, mediator);
	}

	@Override
	public ITable<Number> getTable() {
		// FIXME this should be recomputed from the values
		return null;
	}

	public ISubjectiveState reinterpret(IDirectObservation observers) {
		return null;
	}

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
					getRuntimeScope().getMonitor());
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
//		System.err.println("local offset range:  " + minLocalOffset.get() + " - " + maxLocalOffset.get());
//		System.err.println("target offset range: " + minTargetOffset.get() + " - " + maxTargetOffset.get());
//		System.err.println(
//				"total calls to set(): " + totalSetCalls.get() + " of which " + workedSetCalls.get() + " eventful");
//		System.err.println("total calls to getMediator(): " + mediatorCalls.get());
	}
}

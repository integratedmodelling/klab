package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
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

	List<IScaleMediator> mediatorsFrom = null;
	List<IScaleMediator> mediatorsTo = null;
	boolean conformant = false;

	public RescalingState(IState state, Scale newScale, IRuntimeContext context) {
		super(new Observable((Observable) state.getObservable()), newScale, context);
		this.delegate = state;
		this.newScale = newScale;
		this.originalGeometry = ((Scale) state.getScale()).asGeometry();
	}

	public Object get(ILocator index) {

		if (mediatorsFrom == null) {
			mediatorsFrom = getMediators((Scale) this.delegate.getScale(), this.newScale);
		}

		if (conformant) {

			long offset = this.newScale.getOffset(index);
			long[] offsets = this.newScale.getExtentIndex(offset);
			for (int i = 0; i < mediatorsFrom.size(); i++) {
				offsets[i] = mediatorsFrom.get(i).mapConformant(offsets[i]);
			}
			return delegate.get(originalGeometry.locate(offsets));
		}

		return reduce(index, mediatorsFrom);
	}

	private List<IScaleMediator> getMediators(Scale original, Scale target) {
		List<IScaleMediator> mediators = new ArrayList<>();
		conformant = true;
		for (IExtent originalExtent : original.getExtents()) {
			IExtent targetExtent = target.getDimension(originalExtent.getType());
			if (targetExtent != null) {
				IScaleMediator mediator = originalExtent.getMediator(targetExtent);
				if (mediator == null) {
					throw new KlabInternalErrorException("internal: extent.getMediator() returned null");
				}
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

		long ret = 0;

		if (mediatorsTo == null) {
			mediatorsTo = getMediators(this.newScale, (Scale) this.delegate.getScale());
		}

		long offset = this.newScale.getOffset(index);

		if (conformant) {

			long[] offsets = this.newScale.getExtentIndex(offset);
			for (int i = 0; i < mediatorsTo.size(); i++) {
				offsets[i] = mediatorsTo.get(i).mapConformant(offsets[i]);
			}
			ret = delegate.set(originalGeometry.locate(offsets), value);

		} else {
			map(index, mediatorsTo, value);
		}

		return offset;
	}

	public boolean isConstant() {
		return false;
	}

	private void map(ILocator locator, List<IScaleMediator> mediators, Object value) {

		Propagator propagator = new Propagator();
		for (int i = 0; i < mediators.size(); i++) {
			// for (Pair<Long, Double> val :
			// mediator.map(delegate.getScale().getOffset(locator))) {
			// }
		}
		propagator.propagate(value);
	}

	private Object reduce(ILocator locator, List<IScaleMediator> mediators) {

		Aggregator aggregator = new Aggregator();
		for (int i = 0; i < mediators.size(); i++) {
			// for (Pair<Long, Double> val :
			// mediators.get(i).map(delegate.getScale().getOffset(locator))) {
			// }
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
	Iterator<Pair<long[], Double>> cartesianProductIterator(List<List<ExtentLocation>> data) {

		long[] sizes = new long[data.size()];
		for (int i = 0; i < data.size(); i++) {
			sizes[i] = data.get(i).size();
		}
		final MultidimensionalCursor cursor = new MultidimensionalCursor();
		cursor.defineDimensions(sizes);

		return new Iterator<Pair<long[], Double>>() {

			long current = 0;
			long[] ret = new long[data.size()];

			@Override
			public boolean hasNext() {
				return current < (cursor.getMultiplicity() - 1);
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
	}

	/*
	 * Aggregate contents of original state at the cartesian product of the
	 * collected dimensions.
	 */
	class Aggregator {

		List<List<ExtentLocation>> locations = new ArrayList<>();

		Aggregator() {
			for (int i = 0; i < delegate.getScale().getExtentCount(); i++) {
				locations.add(new ArrayList<>());
			}
		}

		Object aggregate() {

			for (Iterator<Pair<long[], Double>> it = cartesianProductIterator(locations); it.hasNext();) {
				Pair<long[], Double> index = it.next();
				if (index.getSecond() > 0) {

				}
			}
			return null;
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
			for (Iterator<Pair<long[], Double>> it = cartesianProductIterator(locations); it.hasNext();) {
				Pair<long[], Double> index = it.next();
				if (index.getSecond() > 0) {

				}
			}
		}

		public void add(int dimension, long offset, Double weight) {
			locations.get(dimension).add(new ExtentLocation(offset, weight));
		}
	}

	// Remaining functionality is delegated to original state

	public boolean isDynamic() {
		return delegate.isDynamic();
	}

	@Override
	public long size() {
		return delegate.size();
	}

	@Override
	public IState as(IObservable observable) {
		return delegate.as(observable);
	}

}

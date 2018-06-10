package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

/**
 * The state we wrap has the desired semantics but a different scale. We use
 * {@link IScaleMediator mediators} to remap value set and get. Mediators are
 * built as needed.
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

	class Aggregator {

		Object aggregate() {
			return null;
		}

		public void add(int dimension, long offset, Double weight) {
			// TODO Auto-generated method stub

		}
	}

	class Propagator {

		Object propagate(Object value) {
			return null;
		}

		public void add(int dimension, long offset, Double weight) {
			// TODO Auto-generated method stub

		}
	}

	private Object map(ILocator locator, List<IScaleMediator> mediators, Object value) {

		Propagator propagator = new Propagator();
		for (int i = 0; i < mediators.size(); i++) {
			// for (Pair<Long, Double> val :
			// mediator.map(delegate.getScale().getOffset(locator))) {
			// }
		}
		return propagator.propagate(value);

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

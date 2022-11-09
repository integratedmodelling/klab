package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.AbstractExtent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * Wraps another state offering a read/write view with one or more located
 * dimensions.
 * 
 * @author Ferd
 *
 */
public class LocatedState extends Observation implements IState, DelegatingArtifact {

	IState delegate;
	Scale locatedScale;
	String localId;

	public void setLocalId(String observable) {
		this.localId = observable;
	}

	public LocatedState(IState state, Scale locatedScale, IRuntimeScope context) {
		super(new Observable((Observable) state.getObservable()), locatedScale, context);
		this.delegate = state;
		this.locatedScale = locatedScale;
	}

	public Offset getOffset(ILocator locator) {

		long[] offsets = new long[locatedScale.getExtentCount()];
		Arrays.fill(offsets, -1);

		Scale parent = locatedScale;
		Scale originalScale = locatedScale;
		while (parent != null) {
			originalScale = parent;
			for (int i = 0; i < offsets.length; i++) {
				if (offsets[i] < 0 && parent.getLocatedOffsets() != null && parent.getLocatedOffsets()[i] >= 0) {
					offsets[i] = parent.getLocatedOffsets()[i];
				}
			}
			parent = parent.getParentScale();
		}

		if (locator instanceof IScale) {
			// substitute any located extents
			int i = 0;
			for (IExtent extent : locatedScale.getExtents()) {
				IExtent ex = ((Scale)locator).getExtent(extent.getType());
				if (ex != null) {
					offsets[i] = ((AbstractExtent)ex).getLocatedOffset();
				}
				i++;
			}
		} else if (locator instanceof IExtent) {
			int i = 0;
			for (IExtent ext : locatedScale.getExtents()) {
				if (((IExtent) locator).getType() == ext.getType()
						&& ((AbstractExtent) locator).getLocatedOffset() >= 0) {
					offsets[i] = ((AbstractExtent) locator).getLocatedOffset();
					break;
				}
				i++;
			}
		}

		Offset ret = new Offset(originalScale);
		ret.pos = offsets;

		return ret;
	}

	public Object get(ILocator locator) {
		return delegate.get(getOffset(locator));
	}

	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	public long set(ILocator index, Object value) {
		return delegate.set(getOffset(index), value);
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		// mah
		return delegate.iterator(index, cls);
//		return DataIterator.create(this, getScale().at(index), cls);
	}

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
		return new LocatedState(delegate.as(type), locatedScale, getScope());
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

//	@Override
//	public IStructuredTable<Number> getTable() {
//		// FIXME this should be recomputed from the values
//		return null;
//	}

//	public ISubjectiveState reinterpret(IDirectObservation observers) {
//		return null;
//	}

	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		if (locatedScale.size() == 1) {
			return get(locatedScale.at(0), cls);
		}
		throw new KlabUnimplementedException(
				"aggregation of rescaled states is unimplemented - please submit a request");
	}

	@Override
	public Object aggregate(ILocator... locators) {
		if (locatedScale.size() == 1) {
			return get(locatedScale.at(0), Utils.getClassForType(delegate.getType()));
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
		if (locatedScale.size() == 1) {
			set(locatedScale.at(0), value);
		}
		for (ILocator locator : getScale()) {
			set(locator, value);
		}
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

}

package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.mediation.RecontextualizingUnit;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * The state we wrap has the desired semantics but its values must be converted.
 * 
 * @author Ferd
 *
 */
public class MediatingState extends Observation implements IState {

	IState delegate;
	IValueMediator from;
	IValueMediator to;
	IValueMediator rescalingTo = null;
	boolean convertScale = false;

	public MediatingState(IState state, RuntimeScope context, IValueMediator from, IValueMediator to) {
		super(new Observable((Observable) state.getObservable()), (Scale) state.getScale(), context);
		this.delegate = state;
		this.from = from;
		this.to = to;
		if (!this.to.isCompatible(this.from)) {
			convertScale = true;
		}
	}

	public Object get(ILocator index) {
		Object val = delegate.get(index);
		IValueMediator mediatorTo = rescalingTo == null ? to : rescalingTo;
		if (convertScale && rescalingTo == null) {
			mediatorTo = getScaleConverter(index);
		}
		if (val instanceof Number) {
			val = mediatorTo.convert((Number) val, from).doubleValue();
		}
		return val;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(ILocator index, Class<T> cls) {
		Object val = delegate.get(index, cls);
		IValueMediator mediatorTo = rescalingTo == null ? to : rescalingTo;
		if (convertScale && rescalingTo == null) {
			mediatorTo = getScaleConverter(index);
		} 
		if (val instanceof Number && (Number.class.isAssignableFrom(cls))) {
			val = mediatorTo.convert((Number) val, from).doubleValue();
		}
		return (T) val;
	}

	private IValueMediator getScaleConverter(ILocator index) {
		IUnit unit = from instanceof IUnit ? (IUnit) from : ((ICurrency) from).getUnit();
		IValueMediator ret = unit.getContextualizingUnit(this.getObservable(), this.getScale(),
				index);
		if (ret instanceof RecontextualizingUnit && !((RecontextualizingUnit)ret).variesByLocation()) {
			rescalingTo = ret;
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		Object val = delegate.aggregate(geometry, cls);
		return (T) (val instanceof Number ? to.convert(((Number) val).doubleValue(), from) : val);
	}

	@Override
	public Object aggregate(ILocator... locators) {
		Object val = delegate.aggregate(locators);
		return val instanceof Number ? to.convert(((Number) val).doubleValue(), from) : val;
	}

	public long set(ILocator index, Object value) {
		Object val = value instanceof Number ? from.convert(((Number) value).doubleValue(), to) : value;
		return delegate.set(index, val);
	}

	// Remaining functionality is delegated to original state

	public boolean isConstant() {
		return delegate.isConstant();
	}

	/**
	 * Must return the delegate's ID so that any request by ID will fetch the
	 * original content.
	 */
	@Override
	public String getId() {
		return delegate.getId();
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
		return new MediatingState(delegate.as(type), (RuntimeScope) getRuntimeScope(), from, to);
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return DataIterator.create(this, getScale().at(index), cls);
	}

	@Override
	public IDataKey getDataKey() {
		return delegate.getDataKey();
	}

	@Override
	public IState at(ILocator locator) {
		return new MediatingState((IState) delegate.at(locator), (RuntimeScope) getRuntimeScope(), from, to);
	}

	@Override
	public IState in(IValueMediator mediator) {
		if (mediator.equals(from)) {
			return delegate;
		}
		return getMediator(this, mediator);
	}

	public static IState getMediator(IState state, IValueMediator to) {

		IValueMediator from = state.getObservable().getUnit();
		if (from == null) {
			from = state.getObservable().getCurrency();
		}
		if (from == null) {
			from = state.getObservable().getCurrency();
		}
		if (from == null || !from.isCompatible(to)) {
			throw new IllegalArgumentException("cannot create a mediating state between "
					+ (from == null ? "nothing" : from.toString()) + " and " + to.toString());
		}

		return from.equals(to) ? state
				: new MediatingState(state, (RuntimeScope) ((Observation) state).getRuntimeScope(), from, to);
	}

	@Override
	public ITable<Number> getTable() {
		// FIXME SHOULD MEDIATE THE DELEGATE NUMBERS
		return delegate.getTable();
	}

	public ISubjectiveState reinterpret(IDirectObservation observers) {
		return null;
	}

	@Override
	public void fill(Object value) {
		for (ILocator locator : getScale()) {
			set(locator, value);
		}
	}

}

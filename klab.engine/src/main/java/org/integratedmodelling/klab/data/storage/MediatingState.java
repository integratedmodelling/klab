package org.integratedmodelling.klab.data.storage;

import java.util.Collection;
import java.util.Iterator;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeContext;
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

	public MediatingState(IState state, RuntimeContext context, IValueMediator from, IValueMediator to) {
		super(new Observable((Observable) state.getObservable()), (Scale) state.getScale(), context);
		this.delegate = state;
		this.from = from;
		this.to = to;
	}

	public Object get(ILocator index) {
		Object val = delegate.get(index);
		return val instanceof Number ? to.convert(((Number) val).doubleValue(), from) : val;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(ILocator index, Class<T> cls) {
		Object val = delegate.get(index, cls);
		return val instanceof Number && (Number.class.isAssignableFrom(cls))
				? (T) to.convert(((Number) val).doubleValue(), from)
				: (T) val;
	}

    @SuppressWarnings("unchecked")
    @Override
    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
        Object val = delegate.aggregate(geometry, cls);
        return (T)(val instanceof Number ? to.convert(((Number) val).doubleValue(), from) : val);
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
	 * Must return the delegate's ID so that any request by ID will fetch the original
	 * content.
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
		return new MediatingState(delegate.as(type), (RuntimeContext) getRuntimeContext(), from, to);
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
		return new MediatingState((IState) delegate.at(locator), (RuntimeContext) getRuntimeContext(), from, to);
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
		
		return from.equals(to)
				? state
				: new MediatingState(state, (RuntimeContext) ((Observation) state).getRuntimeContext(), from, to);
	}

	@Override
	public ITable<Number> getTable() {
		// FIXME SHOULD MEDIATE THE DELEGATE NUMBERS
		return delegate.getTable();
	}

	public ISubjectiveState reinterpret(IDirectObservation observers) {
		return null;
	}



}

package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IState;
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
	    super(new Observable((Observable)state.getObservable()), (Scale)state.getScale(), context);
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

	public long set(ILocator index, Object value) {
		Object val = value instanceof Number ? from.convert(((Number)value).doubleValue(), to) : value;
		return delegate.set(index, val);
	}

	// Remaining functionality is delegated to original state

	public boolean isConstant() {
		return delegate.isConstant();
	}

    @Override
    public long size() {
        return delegate.size();
    }

//    @Override
//    public IState as(IObservable observable) {
//        return delegate.as(observable);
//    }

	@Override
	public IArtifact.Type getType() {
		return delegate.getType();
	}

	@Override
	public IState as(IArtifact.Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return DataIterator.create(this, getScale().at(index), cls);
	}

	@Override
	public IDataKey getDataKey() {
		return delegate.getDataKey();
	}
}

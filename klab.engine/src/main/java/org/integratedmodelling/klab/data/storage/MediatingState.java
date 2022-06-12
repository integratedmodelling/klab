package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;

/**
 * The state we wrap has the desired semantics but its values must be converted and potentially
 * rescaled. Used in base runtime contextualizers that implement value mediation and as the return
 * value of {@link IState#in(IValueMediator)} to use in contextualizer implementations.
 */
public class MediatingState extends Observation implements IState, DelegatingArtifact {

    IState delegate;
    IValueMediator from;
    IValueMediator to;

    public MediatingState(IState state, RuntimeScope context, IValueMediator from, IValueMediator to) {
        super(new Observable((Observable) state.getObservable()).withMediator(to), (Scale) state.getScale(),
                context);
        this.delegate = state;
        this.from = from.contextualize(state.getObservable(), getScale());
        this.to = to.contextualize(this.getObservable(), getScale());
        this.setCreationTime(this.timestamp);
        this.setExitTime(-1);

    }

    public Object get(ILocator index) {
        Object val = delegate.get(index);
        if (val instanceof Number) {
            val = this.to.convert((Number) val, index).doubleValue();
        }
        return val;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ILocator index, Class<T> cls) {
        Object val = delegate.get(index, cls);
        if (val instanceof Number && (Number.class.isAssignableFrom(cls))) {
            val = to.convert((Number) val, index).doubleValue();
        }
        return (T) val;
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
        Object val = value instanceof Number
                ? from.convert(((Number) value).doubleValue(), index)
                : value;
        return delegate.set(index, val);
    }

    /**
     * Must return the delegate's ID so that any request by ID will fetch the original content.
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
        return new MediatingState(delegate.as(type), (RuntimeScope) getScope(), from, to);
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
        return new MediatingState((IState) delegate.at(locator), (RuntimeScope) getScope(), from, to);
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
                : new MediatingState(state, (RuntimeScope) ((Observation) state).getScope(), from, to);
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

    @Override
    public void finalizeTransition(IScale scale) {
        ((Observation) delegate).finalizeTransition(scale);
        setContextualized(true);
        if (scale.getTime() != null && scale.getTime().getTimeType() != ITime.Type.INITIALIZATION) {
            setDynamic(true);
        }
    }

    @Override
    public long getTimestamp() {
        return delegate.getTimestamp();
    }

    @Override
    public boolean isMain() {
        return ((Observation) delegate).isMain();
    }

    @Override
    public void setMain(boolean main) {
        ((Observation) delegate).setMain(main);
    }

    @Override
    public void setLastUpdate(long lastUpdate) {
        ((Observation) delegate).setLastUpdate(lastUpdate);
    }

    @Override
    public void setCreationTime(long creationTime) {
        if (delegate != null) {
            ((Observation) delegate).setCreationTime(creationTime);
        }
    }

    @Override
    public void setExitTime(long exitTime) {
        if (delegate != null) {
            ((Observation) delegate).setExitTime(exitTime);
        }
    }

    @Override
    public List<ObservationChange> getChangesAndReset() {
        return ((Observation) delegate).getChangesAndReset();
    }

    @Override
    public List<ObservationChange> getChangeset() {
        return ((Observation) delegate).getChangeset();
    }

    @Override
    public boolean isDynamic() {
        return delegate.isDynamic();
    }

    @Override
    public void setDynamic(boolean dynamic) {
        ((Observation) delegate).setDynamic(dynamic);
    }

    @Override
    public boolean isContextualized() {
        return ((Observation) delegate).isContextualized();
    }

    @Override
    public void setContextualized(boolean contextualized) {
        ((Observation) delegate).setContextualized(contextualized);
    }

    @Override
    public long[] getUpdateTimestamps() {
        return delegate.getUpdateTimestamps();
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

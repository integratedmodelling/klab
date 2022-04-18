package org.integratedmodelling.klab.data.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DelegatingArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
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
    IValueMediator contextualized;
    boolean contextualize = false;
    Map<IGeometry.Dimension.Type, IExtent> irregularExtents = new HashMap<>();

    public MediatingState(IState state, RuntimeScope context, IValueMediator from, IValueMediator to) {
        super(new Observable((Observable) state.getObservable()), (Scale) state.getScale(), context);
        this.delegate = state;
        this.from = from;
        this.to = to;
        if (!this.to.isCompatible(this.from)) {
            this.contextualize = true;
            for (IExtent extent : state.getScale().getExtents()) {
                if (!extent.isRegular()) {
                    irregularExtents.put(extent.getType(), null);
                }
            }
        }
        // do it now that delegate isn't null
        this.setCreationTime(
                /* context.getScheduler() != null ? context.getScheduler().getTime() : */ timestamp);
        this.setExitTime(-1);

    }

    public Object get(ILocator index) {
        Object val = delegate.get(index);
        if (val instanceof Number) {
            val = getContextualizedUnit(index).convert((Number) val, from).doubleValue();
        }
        return val;
    }

    IValueMediator getContextualizedUnit(ILocator locator) {
       
    	if (contextualize) {
            
        	if (!(locator instanceof IScale)) {
                throw new KlabUnimplementedException(
                        "cannot yet recontexualize a mediating state on anything other than a scale");
            }
            
            if (contextualized == null) {
                contextualized = to.contextualize(getObservable(), (IScale) locator);
            } else if (irregularExtents.isEmpty()) {
                return contextualized;
            } else {
                /*
                 * check which extent has changed
                 */
                boolean changed = false;
                for (IGeometry.Dimension.Type extType : irregularExtents.keySet()) {
                    if (!((IScale) locator).getDimension(extType).equals(irregularExtents.get(extType))) {
                        changed = true;
                        irregularExtents.put(extType, (IExtent) ((IScale) locator).getDimension(extType));
                    }
                }
                if (changed) {
                    contextualized = to.contextualize(getObservable(), (IScale) locator);
                }
            }
            
            return contextualized == null ? to : contextualized;
        }
    	
        return to;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ILocator index, Class<T> cls) {
        Object val = delegate.get(index, cls);
        if (val instanceof Number && (Number.class.isAssignableFrom(cls))) {
            val = getContextualizedUnit(index).convert((Number) val, from).doubleValue();
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
                ? getContextualizedUnit(index).backConvert(((Number) value).doubleValue(), to)
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

        return from.equals(to) ? state : new MediatingState(state, (RuntimeScope) ((Observation) state).getScope(), from, to);
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

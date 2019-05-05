package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.data.storage.DataIterator;
import org.integratedmodelling.klab.data.storage.MediatingState;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A state is simply an Observation wrapper for one (or more) {@link IDataArtifact}s. 
 * 
 * @author Ferd
 *
 */
public class State extends Observation implements IState, IKeyHolder {

	public static final String STATE_SUMMARY_METADATA_KEY = "metadata.keys.state_summary_";
	
	IDataArtifact storage;
	IDataKey dataKey;
	Map<IArtifact.Type, IDataArtifact> layers = new HashMap<>();
	ITable<Number> table;

	public State(Observable observable, Scale scale, IRuntimeContext context, IDataArtifact data) {
		super(observable, scale, context);
		this.storage = data;
		this.layers.put(data.getType(), data);
	}
	
	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public IState as(IArtifact.Type type) {

		if (type == storage.getType() || type == IArtifact.Type.VALUE) {
			return this;
		}
		
		IDataArtifact layer = layers.get(type);
		if (layer == null) {
			layers.put(type, layer = Klab.INSTANCE.getStorageProvider().createStorage(type, getScale(), getRuntimeContext()));
		}

		return new StateLayer(this, layer);
	}

	public Object get(ILocator index) {
		return storage.get(index);
	}

	public long set(ILocator index, Object value) {
		touch();
		return storage.set(index, value);
	}

	public IGeometry getGeometry() {
		return storage.getGeometry();
	}

	public IMetadata getMetadata() {
		return storage.getMetadata();
	}

	public long size() {
		return storage.size();
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return storage.get(index, cls);
	}

	@Override
	public IArtifact.Type getType() {
		return storage.getType();
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return DataIterator.create(this, getScale().at(index), cls);
	}

	@Override
	public IDataKey getDataKey() {
	    if (dataKey == null && getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
	        // may result from merging more states: build the datakey from the storage
	        dataKey = storage.getDataKey();
	    }
		return dataKey;
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
		if (this.storage instanceof IKeyHolder) {
			((IKeyHolder)this.storage).setDataKey(key);
		}
	}

	@Override
	public IState at(ILocator locator) {
		IScale scale = getScale().at(locator);
		return scale == getScale() ? this : new RescalingState(this, (Scale)scale, getRuntimeContext());
	}

	@Override
	public IState in(IValueMediator mediator) {
		return MediatingState.getMediator(this, mediator);
	}

	@Override
	public ITable<Number> getTable() {
		return table;
	}

	public void setTable(ITable<Number> table) {
		this.table = table;
	}
	
	public ISubjectiveState reinterpret(IDirectObservation observer) {
		return new SubjectiveState(this, observer);
	}

	public void distributeScalar(Object pod) {
		for (ILocator locator : getScale()) {
			set(locator, pod);
		}
	}

    @Override
    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
        return storage.aggregate(geometry, cls);
    }

    @Override
    public Object aggregate(ILocator... locators) {
        if (getScale().size() == 1) {
            return get(getScale().getLocator(0), Utils.getClassForType(getType()));
        }
        if (locators == null) {
            List<Object> values = new ArrayList<>();
            for (ILocator locator : getScale()) {
                values.add(get(locator));
            }
            AggregationUtils.aggregate(values, AggregationUtils.getAggregation(getObservable()), getRuntimeContext().getMonitor());
        }
        throw new KlabUnimplementedException("aggregation of rescaled states is unimplemented - please submit a request");
    }
	
}

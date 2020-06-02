package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.storage.DataIterator;
import org.integratedmodelling.klab.data.storage.LocatedState;
import org.integratedmodelling.klab.data.storage.MediatingState;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.AggregationUtils;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A state is simply an Observation wrapper for one (or more)
 * {@link IDataArtifact}s.
 * 
 * @author Ferd
 *
 */
public class State extends Observation implements IState, IKeyHolder {

	public static final String STATE_SUMMARY_METADATA_KEY = "metadata.keys.state_summary_";

	IDataStorage<?> storage;
	IDataKey dataKey;
	Map<IArtifact.Type, IStorage<?>> layers = new HashMap<>();
	ITable<Number> table;

	public static State newArchetype(Observable observable, Scale scale, IRuntimeScope context) {
		return new State(observable, scale, context);
	}

	private State(Observable observable, Scale scale, IRuntimeScope context) {
		super(observable, scale, context);
		this.setArchetype(true);
	}

	public State(Observable observable, Scale scale, IRuntimeScope context, IDataStorage<?> data) {
		super(observable, scale, context);
		this.storage = data;
		this.layers.put(data.getType(), data);
	}

	@Override
	public IState as(IArtifact.Type type) {

		if (isArchetype() || type == storage.getType() || type == IArtifact.Type.VALUE) {
			return this;
		}

		IStorage<?> layer = layers.get(type);
		if (layer == null) {
			layers.put(type,
					layer = Klab.INSTANCE.getStorageProvider().createStorage(type, getScale(), getScope()));
		}

		return new StateLayer(this, (IDataStorage<?>) layer);
	}

	public Object get(ILocator index) {
		return storage.get(index);
	}

	public long set(ILocator index, Object value) {
		touch();
		if (dataKey != null && value != null) {
			dataKey.include(value);
		}
		return storage.putObject(value, index);
	}

	public long size() {
		return getGeometry().size();
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	@Override
	public IArtifact.Type getType() {
		return isArchetype() ? IArtifact.Type.VOID : storage.getType();
	}

	@Override
	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return DataIterator.create(this, getScale().at(index), cls);
	}

	@Override
	public IDataKey getDataKey() {
		if (dataKey == null && getObservable().getArtifactType() == IArtifact.Type.CONCEPT) {
			// may result from merging more states: build the datakey from the storage
			dataKey = storage instanceof IKeyHolder ? ((IKeyHolder) storage).getDataKey() : null;
		}
		return dataKey;
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
		if (this.storage instanceof IKeyHolder) {
			((IKeyHolder) this.storage).setDataKey(key);
		}
	}

	@Override
	public IState at(ILocator locator) {

		/*
		 * if the locator is a scale, this should not modify it at all, otherwise locate
		 * the scale to the passed object.
		 */
		Scale scale = (Scale) getScale().at(locator);

		/*
		 * if the located scale is conformant (i.e. points to whole dimensions or unique
		 * points on them), return a located instance of this, otherwise create a
		 * rescaled instance.
		 */
		return scale.isConformant(getScale()) 
				? new LocatedState(this, (Scale) scale, getScope())
				: new RescalingState(this, (Scale) scale, getScope());
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

	public IDataStorage<?> getStorage() {
		return storage;
	}
	
	@Override
	public <T> T aggregate(ILocator geometry, Class<? extends T> cls) {
		Object o = aggregate(geometry);
		return Utils.asType(o, cls);
	}

	@Override
	public Object aggregate(ILocator... locators) {
		if (getScale().size() == 1) {
			return get(getScale().initialization(), Utils.getClassForType(getType()));
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
		for (ILocator locator : getScale()) {
			set(locator, value);
		}
	}

	@Override
	public void finalizeTransition(IScale scale) {
		// TODO store locally 
		Observations.INSTANCE.getStateSummary(this, scale);
		setContextualized(true);
		if (scale.getTime() != null && scale.getTime().getTimeType() != ITime.Type.INITIALIZATION) {
			setDynamic(true);
		}
	}
	
	

}

package org.integratedmodelling.klab.components.localstorage.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class KeyedDebugStorage<T> implements IDataStorage<T>, IKeyHolder {

	private IDataStorage<Integer> keyStore;
	private BiMap<T, Integer> conceptKey = Maps.synchronizedBiMap(HashBiMap.create());
	private IDataKey dataKey = null;
	private Class<? extends T> cls;
	private IScale geometry;

	public KeyedDebugStorage(IScale geometry, Class<? extends T> cls) {
		// TODO use a Short
		keyStore = new DebugStorage<>(geometry, Integer.class);
		this.cls = cls;
		this.geometry = geometry;
	}

	public void setDataKey(IDataKey dataKey) {
		this.dataKey = dataKey;
	}

	@Override
	public long put(T value, ILocator locator) {
		Integer cValue = null;
		if (value != null) {
			cValue = dataKey == null ? conceptKey.size() : dataKey.reverseLookup(value);
			if (conceptKey.containsKey(value)) {
				cValue = conceptKey.get(value);
			} else {
				if (dataKey == null) {
					cValue++;
				}
				if (!conceptKey.containsValue(cValue)) {
					conceptKey.put(value, cValue);
				}
			}
		}
		return keyStore.put(cValue, locator);
	}

	@Override
	public T get(ILocator locator) {
		Integer key = keyStore.get(locator);
		return key == null ? null : conceptKey.inverse().get(key);
	}

	@Override
	public void close() throws IOException {
		this.keyStore.close();
	}

	@Override
	public Type getType() {
		return Utils.getArtifactType(cls);
	}

	@Override
	public IDataKey getDataKey() {
		if (dataKey == null && conceptKey != null) {
			return new MapKey<T>(conceptKey);
		}
		return dataKey;
	}

	@Override
	public Object getObject(ILocator locator) {
		return get(locator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long putObject(Object value, ILocator locator) {
		return put((T) value, locator);
	}

	public static class MapKey<T> implements IDataKey {

		private BiMap<T, Integer> key;

		public MapKey(BiMap<T, Integer> conceptKey) {
			this.key = conceptKey;
		}

		@Override
		public int size() {
			return this.key.size();
		}

		@Override
		public int reverseLookup(Object value) {
			if (this.key.containsKey(value)) {
				return this.key.get(value);
			}
			return -1;
		}

		@Override
		public List<String> getLabels() {
			List<String> ret = new ArrayList<>();
			synchronized (key) {
				for (T value : this.key.keySet()) {
					ret.add(value instanceof IConcept ? Concepts.INSTANCE.getDisplayName((IConcept) value)
							: value.toString());
				}
			}
			return ret;
		}

		@Override
		public Object lookup(int index) {
			return this.key.inverse().get(index);
		}

		@Override
		public List<Pair<Integer, String>> getAllValues() {
			List<Pair<Integer, String>> ret = new ArrayList<>();
			synchronized (key) {
				for (T value : this.key.keySet()) {
					ret.add(new Pair<>(this.key.get(value),
							value instanceof IConcept ? Concepts.INSTANCE.getDisplayName((IConcept) value)
									: value.toString()));
				}
			}
			return ret;
		}

		@Override
		public boolean isOrdered() {
			// TODO if ordering, check as in Classification
			return false;
		}

		@Override
		public List<String> getSerializedObjects() {
			List<String> ret = new ArrayList<>();
			synchronized (key) {
				for (T value : this.key.keySet()) {
					ret.add(value instanceof IConcept ? ((IConcept) value).getDefinition() : value.toString());
				}
			}
			return ret;
		}

		@Override
		public List<IConcept> getConcepts() {
			List<IConcept> ret = new ArrayList<>();
			synchronized (key) {
				for (T value : this.key.keySet()) {
					if (!(value instanceof IConcept)) {
						return null;
					}
					ret.add((IConcept) value);
				}
			}
			return ret;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void include(Object value) {
			if (!this.key.containsKey((T)value)) {
				this.key.put((T)value, this.key.size());
			}
		}

	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public void touch(ITime time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContextualizationListener(Consumer<ILocator> listener) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public long getTemporalOffset(ILocator locator) {
        return keyStore.getTemporalOffset(locator);
    }

}

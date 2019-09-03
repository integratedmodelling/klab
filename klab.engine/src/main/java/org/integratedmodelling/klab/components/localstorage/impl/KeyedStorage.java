package org.integratedmodelling.klab.components.localstorage.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class KeyedStorage<T> implements IDataStorage<T>, IKeyHolder {

	private IStorage<Integer> keyStore;
	private BiMap<T, Integer> conceptKey = Maps.synchronizedBiMap(HashBiMap.create());
	private IDataKey dataKey = null;
	private Class<? extends T> cls;

	public KeyedStorage(IGeometry geometry, Class<? extends T> cls) {
		// TODO use a Short
		keyStore = new FileMappedStorage<>(geometry, Integer.class);
		this.cls = cls;
	}

	public void setDataKey(IDataKey dataKey) {
		this.dataKey = dataKey;
	}

	@Override
	public synchronized long put(T value, ILocator locator) {
		Integer cValue = null;
		if (value != null) {
			cValue = dataKey == null ? conceptKey.size() : dataKey.reverseLookup(value);
			if (conceptKey.containsKey(value)) {
				cValue = conceptKey.get(value);
			} else {
//				if (dataKey == null) {
//					cValue++;
//				}
				conceptKey.put(value, cValue);
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

	}

}

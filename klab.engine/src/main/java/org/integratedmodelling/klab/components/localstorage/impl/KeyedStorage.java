package org.integratedmodelling.klab.components.localstorage.impl;

import java.io.IOException;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class KeyedStorage<T> implements IStorage<T>, IKeyHolder {

	private IStorage<Integer> keyStore;
	private BiMap<T, Integer> conceptKey = Maps.synchronizedBiMap(HashBiMap.create());
	private IDataKey dataKey = null;
	private Class<? extends T> cls;

	public KeyedStorage(IGeometry geometry, Class<? extends T> cls) {
		keyStore = new FileMappedStorage<>(geometry, Integer.class);
		this.cls = cls;
	}

	public void setDataKey(IDataKey dataKey) {
		this.dataKey = dataKey;
	}

	@Override
	public long put(T value, ILocator locator) {
		Integer cValue = null;
		if (value != null) {
			cValue = dataKey == null ? conceptKey.size() : dataKey.reverseLookup(value.toString());
			if (conceptKey.containsKey(value.toString())) {
				cValue = conceptKey.get(value.toString());
			} else {
				if (dataKey == null) {
					cValue++;
				}
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
		return dataKey;
	}

}

package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TextStorage extends Storage implements IDataArtifact, IKeyHolder {

	BiMap<String, Integer> conceptKey = HashBiMap.create();
	int[] data;
	IDataKey dataKey;

	public TextStorage(IGeometry scale) {
		super(scale);
		// TODO switch to nd4j
		data = new int[(int) scale.size()];
	}

	@Override
	public long size() {
		return getGeometry().size();
	}

	@Override
	public String get(ILocator index) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnimplementedException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		int key = data[(int) offset];
		return key == Integer.MIN_VALUE ? null : conceptKey.inverse().get(key);
	}

	@Override
	public long set(ILocator index, Object value) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnimplementedException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		if (value == null) {
			data[(int) offset] = Integer.MIN_VALUE;
		} else {
			int cValue = dataKey == null ? conceptKey.size() : dataKey.reverseLookup(value.toString());
			if (conceptKey.containsKey(value.toString())) {
				cValue = conceptKey.get(value.toString());
			} else {
				if (dataKey == null) {
					cValue++;
				}
				conceptKey.put(value.toString(), cValue);
			}
			data[(int) offset] = cValue;
		} 

		return offset;
	}

	@Override
	protected void finalize() throws Throwable {
		// data.free();
		super.finalize();
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	@Override
	public Type getType() {
		return Type.TEXT;
	}

	@Override
	public IDataKey getDataKey() {
		return dataKey;
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
	}

}

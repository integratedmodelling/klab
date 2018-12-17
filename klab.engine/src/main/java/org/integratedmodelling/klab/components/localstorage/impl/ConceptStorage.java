package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ConceptStorage extends Storage implements IDataArtifact, IKeyHolder {

	BiMap<IConcept, Integer> conceptKey = HashBiMap.create();
	INDArray data;
	IDataKey dataKey;

	public ConceptStorage(IGeometry scale) {
		super(scale);
		data = Nd4j.valueArrayOf(scale.size(), 0);
	}

	@Override
	public long size() {
		return getGeometry().size();
	}

	@Override
	public IConcept get(ILocator index) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnimplementedException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		int key = data.getInt(new int[] { (int)offset });
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
			data.putScalar(offset, Integer.MIN_VALUE);
		} else if (value instanceof IConcept) {
			int cValue = dataKey == null ? conceptKey.size() : dataKey.reverseLookup(value);
			if (conceptKey.containsKey((IConcept) value)) {
				cValue = conceptKey.get(value);
			} else {
				if (dataKey == null) {
					cValue++;
				}
				conceptKey.put((IConcept) value, cValue);
			}
			data.putScalar(offset, cValue);
		} else {
			throw new KlabValidationException(
					"cannot set value of type " + value.getClass().getCanonicalName() + " into a concept storage");
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
		return Type.CONCEPT;
	}

	@Override
	public IDataKey getDataKey() {
		return dataKey;
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}

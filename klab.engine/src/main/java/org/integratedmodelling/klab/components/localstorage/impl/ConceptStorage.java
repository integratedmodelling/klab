package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class ConceptStorage extends Storage implements IDataArtifact, IKeyHolder {

	BiMap<IConcept, Integer> conceptKey = Maps.synchronizedBiMap(HashBiMap.create());
	// INDArray data;
	int[] data;
	IDataKey dataKey;

	public ConceptStorage(IGeometry scale) {
		super(scale);
		data = new int[(int) scale.size()]; // Nd4j.valueArrayOf(scale.size(), 0);
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
		int key = data[(int) offset]; // data.getInt(new int[] { (int)offset });
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
			data[(int) offset] = Integer.MIN_VALUE; // data.putScalar(offset, Integer.MIN_VALUE);
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
			data[(int) offset] = cValue; // data.putScalar(offset, cValue);
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
		if (dataKey == null) {
			// build a de facto data key from concept key. This happens when the state is
			// built by
			// aggregating multiple partitions, which may have different classification
			// encodings.
			dataKey = buildDataKey();
		}
		return dataKey;
	}

	private IDataKey buildDataKey() {

		return new IDataKey() {

			List<Integer> sortedKeys = null;

			@Override
			public int size() {
				return conceptKey.size();
			}

			private List<Integer> getSortedKeys() {
				if (sortedKeys == null) {
					sortedKeys = new ArrayList<>();
					sortedKeys.addAll(conceptKey.values());
					sortedKeys.sort(null);
				}
				return sortedKeys;
			}

			@Override
			public int reverseLookup(Object value) {
				if (value instanceof IConcept && conceptKey.containsKey(value)) {
					return conceptKey.get((IConcept) value);
				}
				return -1;
			}

			@Override
			public List<String> getLabels() {
				List<String> ret = new ArrayList<>();
				for (int key : getSortedKeys()) {
					ret.add(Concepts.INSTANCE.getDisplayName(conceptKey.inverse().get(key)));
				}
				return null;
			}

			@Override
			public Object lookup(int index) {
				return conceptKey.inverse().get(index);
			}

			@Override
			public List<Pair<Integer, String>> getAllValues() {
				List<Pair<Integer, String>> ret = new ArrayList<>();
				for (int key : getSortedKeys()) {
					ret.add(new Pair<>(key, Concepts.INSTANCE.getDisplayName(conceptKey.inverse().get(key))));
				}
				return ret;
			}

			@Override
			public boolean isOrdered() {
				// TODO
				return false;
			}

		};
	}

	@Override
	public void setDataKey(IDataKey key) {
		this.dataKey = key;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getChildArtifacts() {
		return new ArrayList<>();
	}
}

package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.BitSet;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Utils;

public class BooleanStorage extends Storage implements IDataArtifact {

	private BitSet data;
	private BitSet mask;

	public BooleanStorage(IGeometry scale) {
		super(scale);
		this.data = new BitSet((int)scale.size());
		this.mask = new BitSet((int)scale.size());
	}

	@Override
	public long size() {
		return getGeometry().size();
	}

	@Override
	public Boolean get(ILocator index) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		return mask.get((int)offset) ? data.get((int)offset) : null;
	}

	@Override
	public long set(ILocator index, Object value) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		if (value == null) {
			mask.set((int)offset, false);
		} else if (!(value instanceof Boolean)) {
			throw new IllegalArgumentException("cannot set a boolean state from value " + value);
		} else {
			data.set((int)offset, ((Boolean) value));
			mask.set((int)offset, true);
		}
		return offset;
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public IDataKey getDataKey() {
		return null;
	}
	
}

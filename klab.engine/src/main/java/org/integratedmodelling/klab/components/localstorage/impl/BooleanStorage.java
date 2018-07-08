package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.Iterator;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.data.storage.DataIterator;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Utils;

import xerial.larray.LBitArray;
import xerial.larray.japi.LArrayJ;

public class BooleanStorage extends Storage implements IDataArtifact {

	private LBitArray data;
	private LBitArray mask;

	public BooleanStorage(IGeometry scale) {
		super(scale);
		this.data = LArrayJ.newLBitArray(scale.size());
		this.mask = LArrayJ.newLBitArray(scale.size());
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
		return mask.apply(offset) ? data.apply(offset) : null;
	}

	@Override
	public long set(ILocator index, Object value) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		if (value == null) {
			mask.update(offset, false);
		} else if (!(value instanceof Boolean)) {
			throw new IllegalArgumentException("cannot set a boolean state from value " + value);
		} else {
			data.update(offset, ((Boolean) value));
			mask.update(offset, true);
		}
		return offset;
	}

	@Override
	protected void finalize() throws Throwable {
		data.free();
		mask.free();
		super.finalize();
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

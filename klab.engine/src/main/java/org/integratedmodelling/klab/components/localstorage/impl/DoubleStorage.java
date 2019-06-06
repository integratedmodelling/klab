package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Utils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * 
 * @author ferdinando.villa
 *
 */
public class DoubleStorage extends Storage implements IDataArtifact {

	private double[] idata;
	private INDArray mdata;
	boolean inMemory = false;

	public DoubleStorage(IGeometry scale) {
		super(scale);
		this.inMemory = Configuration.INSTANCE.useInMemoryStorage();
		if (inMemory) {
			this.idata = new double[(int) scale.size()]; // LArrayJ.newLFloatArray(scale.size());
			Arrays.fill(this.idata, Double.NaN);
		} else {
			this.mdata = Nd4j.valueArrayOf(scale.size(), Double.NaN);
		}
	}

	@Override
	public long size() {
		return getGeometry().size();
	}

	@Override
	public Double get(ILocator index) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		if (inMemory) {
			double ret = idata[(int) offset];
			return Double.isNaN(ret) ? null : (double) ret;
		}

		return mdata.getDouble(offset);
	}

	@Override
	public long set(ILocator index, Object value) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		if (inMemory) {
			idata[(int) offset] = value instanceof Number ? ((Number) value).floatValue()
					: (value == null ? Double.NaN : convert(value));
		} else {
			mdata.putScalar(offset, value instanceof Number ? ((Number) value).doubleValue()
					: (value == null ? Double.NaN : convert(value)));
		}
		return offset;
	}

	private double convert(Object value) {
		// TODO convert distributions and the like
		throw new IllegalArgumentException("cannot convert a " + value.getClass().getCanonicalName() + " to a number");
		// return Double.NaN;
	}

	@Override
	public void release() {
		if (!inMemory) {
			mdata.cleanup();
		}
	}

	@Override
	public <T> T get(ILocator index, Class<T> cls) {
		return Utils.asType(get(index), cls);
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IDataKey getDataKey() {
		return null;
	}

    @Override
    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
        
        if (size() == 1) {
            return Utils.asType(idata[0], cls);
        }

        return null;
    }

	@Override
	public Collection<IArtifact> getChildArtifacts() {
		return new ArrayList<>();
	}
}

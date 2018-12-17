package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
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

//	private double[] data;

	private INDArray data;

	public DoubleStorage(IGeometry scale) {
		super(scale);
		this.data = Nd4j.valueArrayOf(scale.size(), Double.NaN);
//		this.data = new double[(int) scale.size()]; // LArrayJ.newLFloatArray(scale.size());
//		Arrays.fill(this.data, Double.NaN);
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
		double ret = data.getDouble(offset);
		return Double.isNaN(ret) ? null : (double) ret;
	}

	@Override
	public long set(ILocator index, Object value) {
		long offset = getGeometry().getOffset(index);
		if (offset < 0) {
			// mediation needed
			throw new KlabUnsupportedFeatureException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
		}
		data.putScalar(offset, value instanceof Number ? ((Number) value).doubleValue()
				: (value == null ? Double.NaN : convert(value)));
		// data.update(offset, value instanceof Number ? ((Number) value).floatValue() :
		// convert(value));
		return offset;
	}

	private double convert(Object value) {
		// TODO convert distributions and the like
		throw new IllegalArgumentException("cannot convert a " + value.getClass().getCanonicalName() + " to a number");
		// return Double.NaN;
	}

	@Override
	public void release() {
		data.cleanup();
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

}

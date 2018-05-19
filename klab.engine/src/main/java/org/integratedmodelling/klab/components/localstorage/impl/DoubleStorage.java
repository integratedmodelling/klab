package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.utils.Utils;

/**
 * TODO/FIXME: Using Float arrays because LDoubleArray simply DOES NOT WORK (stores X and returns Y).
 * Of course this should be a good argument to avoid LArrayJ.
 * 
 * @author ferdinando.villa
 *
 */
public class DoubleStorage extends Storage implements IDataArtifact {

//  private LFloatArray data;
	private double[] data;
  public DoubleStorage(IGeometry scale) {
    super(scale);
    this.data = new double[(int)scale.size()]; //LArrayJ.newLFloatArray(scale.size());
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
    double ret = data[(int)offset]; // data.apply(offset);
    return Double.isNaN(ret) ? null : (double)ret;
  }

  @Override
  public long set(ILocator index, Object value) {
    long offset = getGeometry().getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabUnsupportedFeatureException("DIRECT SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    data[(int)offset] = value instanceof Number ? ((Number) value).doubleValue() : convert(value);
    // data.update(offset, value instanceof Number ? ((Number) value).floatValue() : convert(value));
    return offset;
  }

  private double convert(Object value) {
    // TODO convert distributions and the like
    return Double.NaN;
  }

  @Override
  protected void finalize() throws Throwable {
//    data.free();
    super.finalize();
  }

  @Override
  public <T> T get(ILocator index, Class<T> cls) {
    return Utils.asType(get(index), cls);
  }
  

}

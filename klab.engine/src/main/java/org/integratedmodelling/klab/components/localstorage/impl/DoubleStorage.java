package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.utils.Utils;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import xerial.larray.LFloatArray;
import xerial.larray.japi.LArrayJ;

/**
 * TODO/FIXME: Using Float arrays because LDoubleArray simply DOES NOT WORK (stores X and returns Y).
 * Of course this should be a good argument to avoid LArrayJ.
 * 
 * @author ferdinando.villa
 *
 */
public class DoubleStorage extends Storage implements IDataArtifact {

  private LFloatArray data;

  public DoubleStorage(IGeometry scale) {
    super(scale);
    this.data = LArrayJ.newLFloatArray(scale.size());
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
      throw new KlabUnsupportedFeatureException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    float ret = data.apply(offset);
    return Float.isNaN(ret) ? null : (double)ret;
  }

  @Override
  public long set(ILocator index, Object value) {
    long offset = getGeometry().getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabUnsupportedFeatureException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    data.update(offset, value instanceof Number ? ((Number) value).floatValue() : convert(value));
    return offset;
  }

  private float convert(Object value) {
    // TODO convert distributions and the like
    return Float.NaN;
  }

  @Override
  protected void finalize() throws Throwable {
    data.free();
    super.finalize();
  }

  @Override
  public <T> T get(ILocator index, Class<T> cls) {
    return Utils.asType(get(index), cls);
  }
  

}

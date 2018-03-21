package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.raw.IDataArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.observation.Scale;
import xerial.larray.LDoubleArray;
import xerial.larray.japi.LArrayJ;

public class DoubleStorage extends Storage implements IDataArtifact {

  private LDoubleArray data;

  public DoubleStorage(IObservable observable, IGeometry scale) {
    super(scale);
    this.data = LArrayJ.newLDoubleArray(scale.size());
  }

  @Override
  public long size() {
    return getGeometry().size();
  }

  @Override
  public Double get(ILocator index) {   
    long offset = ((Scale) getGeometry()).getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabRuntimeException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    return data.apply(offset);
  }

  @Override
  public void set(ILocator index, Object value) {
    long offset = ((Scale) getGeometry()).getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabRuntimeException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    data.update(offset, value instanceof Number ? ((Number) value).doubleValue() : convert(value));
  }

  private double convert(Object value) {
    // TODO convert distributions and the like
    return Double.NaN;
  }

  @Override
  protected void finalize() throws Throwable {
    data.free();
    super.finalize();
  }



}

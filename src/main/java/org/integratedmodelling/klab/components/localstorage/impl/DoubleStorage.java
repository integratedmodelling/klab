package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.AbstractObservationData;
import org.integratedmodelling.klab.observation.Scale;
import xerial.larray.LDoubleArray;
import xerial.larray.japi.LArrayJ;

public class DoubleStorage extends AbstractObservationData implements IStorage<Double> {

  private LDoubleArray data;
  private Scale scale;
  
  public DoubleStorage(IObservable observable, Scale scale) {
      super(observable);
      this.scale = scale;
      this.data = LArrayJ.newLDoubleArray(scale.size());
  }

  @Override
  public long size() {
    return scale.size();
  }

  @Override
  public Double get(ILocator index) {
    long offset = scale.getOffset(index); // ((AbstractLocator)index).getOffsetIn(scale);    
    return data.apply(offset);
  }

  @Override
  public void set(ILocator index, Object value) {
    long offset = 0; // ((AbstractLocator)index).getOffsetIn(scale);    
    data.update(offset, value instanceof Number ? ((Number)value).doubleValue() : Double.NaN);
  }
  
}

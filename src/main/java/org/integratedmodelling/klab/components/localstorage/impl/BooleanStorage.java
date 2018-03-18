package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.ILocator;
import org.integratedmodelling.klab.data.ObservationData;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.observation.Scale;
import xerial.larray.LBitArray;
import xerial.larray.japi.LArrayJ;

public class BooleanStorage extends ObservationData implements IStorage<Boolean> {

  private Scale     scale;
  private LBitArray data;
  private LBitArray mask;

  public BooleanStorage(IObservable observable, Scale scale) {
    super(observable, null);
    this.scale = scale;
    this.data = LArrayJ.newLBitArray(scale.size());
    this.mask = LArrayJ.newLBitArray(scale.size());
  }

  @Override
  public long size() {
    return scale.size();
  }

  @Override
  public Boolean get(ILocator index) {
    long offset = scale.getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabRuntimeException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    return mask.apply(offset) ? data.apply(offset) : null;
  }

  @Override
  public void set(ILocator index, Object value) {
    long offset = scale.getOffset(index);
    if (offset < 0) {
      // mediation needed
      throw new KlabRuntimeException("SCALE MEDIATION UNIMPLEMENTED - COME BACK LATER");
    }
    if (value == null) {
      mask.update(offset, false);
    } else if (!(value instanceof Boolean)) {
      throw new IllegalArgumentException("cannot set a boolean state from value " + value);
    } else {
      data.update(offset, ((Boolean) value));
      mask.update(offset, true);
    }
  }
  
  @Override
  protected void finalize() throws Throwable {
    data.free();
    mask.free();
    super.finalize();
  }
  

}

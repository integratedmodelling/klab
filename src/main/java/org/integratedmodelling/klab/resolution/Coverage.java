package org.integratedmodelling.klab.resolution;

import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Coverage implements ICoverage {

  public Coverage() {
    // TODO Auto-generated constructor stub
  }

  public static Coverage empty() {
    return null;
  }
  
  @Override
  public Double getCoverage() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICoverage or(ICoverage coverage) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICoverage and(ICoverage coverage) throws KlabException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isComplete() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRelevant() {
    // TODO Auto-generated method stub
    return false;
  }

}

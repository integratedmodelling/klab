package org.integratedmodelling.klab.resolution;

import java.util.List;
import java.util.Map;
import org.integratedmodelling.klab.api.resolution.IPrioritizer;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;

public class Prioritizer<T> implements IPrioritizer<T> {

  public Prioritizer() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public int compare(Object arg0, Object arg1) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Map<String, Object> computeCriteria(T model, IResolutionScope context) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, Double> getRanks(T md) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> listCriteria() {
    // TODO Auto-generated method stub
    return null;
  }

}
